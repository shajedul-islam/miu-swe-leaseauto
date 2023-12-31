import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IBooking, Booking } from '../booking.model';
import { BookingService } from '../service/booking.service';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { InvoiceService } from 'app/entities/invoice/service/invoice.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { IVehicle } from 'app/entities/vehicle/vehicle.model';
import { VehicleService } from 'app/entities/vehicle/service/vehicle.service';
import { differenceInDays, parseISO } from 'date-fns';
import { AccountService } from 'app/core/auth/account.service';
import { of } from 'rxjs';

@Component({
  selector: 'jhi-booking-update',
  templateUrl: './booking-update.component.html',
})
export class BookingUpdateComponent implements OnInit {
  isSaving = false;

  invoicesCollection: IInvoice[] = [];
  customersSharedCollection: ICustomer[] = [];
  vehiclesSharedCollection: IVehicle[] = [];

  editForm = this.fb.group({
    id: [],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    totalCost: [],
    location: [],
    invoice: [],
    customer: [],
    vehicle: [],
  });

  constructor(
    protected bookingService: BookingService,
    protected invoiceService: InvoiceService,
    protected customerService: CustomerService,
    protected vehicleService: VehicleService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ booking }) => {
      this.updateForm(booking);
      this.loadRelationshipsOptions();

      // Auto select the vehicle if vehicleId is present in the route
      this.activatedRoute.params.subscribe(params => {
        const vehicleId = params['vehicleId'];
        if (vehicleId) {
          this.vehicleService.find(vehicleId).subscribe(response => {
            const selectedVehicle: IVehicle = response.body!;
            this.vehiclesSharedCollection.push(selectedVehicle);
            this.editForm.patchValue({ vehicle: selectedVehicle });
          });
        }
      });
    });

    // Set the customer as the logged-in user
    // This assumes you have a method to get the logged-in user. If not, you'll need to implement that.
    const loggedInUser = this.getLoggedInUser().subscribe(user => {
      this.editForm.patchValue({ customer: loggedInUser });
    });

    this.autoCalculateTotalCost();
  }

  previousState(): void {
    window.history.back();
  }

  // This is a placeholder method, you need to implement getting the logged-in user.
  getLoggedInUser(): Observable<any> {
    if (this.accountService.isAuthenticated()) {
      return this.accountService.identity();
    }
    return of(null); // or return an observable of some default value if desired
  }

  save(): void {
    this.isSaving = true;
    const booking = this.createFromForm();
    if (booking.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingService.update(booking));
    } else {
      this.subscribeToSaveResponse(this.bookingService.create(booking));
    }
  }

  trackInvoiceById(_index: number, item: IInvoice): number {
    return item.id!;
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  trackVehicleById(_index: number, item: IVehicle): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBooking>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(booking: IBooking): void {
    this.editForm.patchValue({
      id: booking.id,
      startDate: booking.startDate,
      endDate: booking.endDate,
      totalCost: booking.totalCost,
      location: booking.location,
      invoice: booking.invoice,
      customer: booking.customer,
      vehicle: booking.vehicle,
    });

    this.invoicesCollection = this.invoiceService.addInvoiceToCollectionIfMissing(this.invoicesCollection, booking.invoice);
    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(
      this.customersSharedCollection,
      booking.customer
    );
    this.vehiclesSharedCollection = this.vehicleService.addVehicleToCollectionIfMissing(this.vehiclesSharedCollection, booking.vehicle);
  }

  protected loadRelationshipsOptions(): void {
    this.invoiceService
      .query({ filter: 'booking-is-null' })
      .pipe(map((res: HttpResponse<IInvoice[]>) => res.body ?? []))
      .pipe(
        map((invoices: IInvoice[]) => this.invoiceService.addInvoiceToCollectionIfMissing(invoices, this.editForm.get('invoice')!.value))
      )
      .subscribe((invoices: IInvoice[]) => (this.invoicesCollection = invoices));

    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) =>
          this.customerService.addCustomerToCollectionIfMissing(customers, this.editForm.get('customer')!.value)
        )
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));

    this.vehicleService
      .query()
      .pipe(map((res: HttpResponse<IVehicle[]>) => res.body ?? []))
      .pipe(
        map((vehicles: IVehicle[]) => this.vehicleService.addVehicleToCollectionIfMissing(vehicles, this.editForm.get('vehicle')!.value))
      )
      .subscribe((vehicles: IVehicle[]) => (this.vehiclesSharedCollection = vehicles));
  }

  protected createFromForm(): IBooking {
    return {
      ...new Booking(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      totalCost: this.editForm.get(['totalCost'])!.value,
      location: this.editForm.get(['location'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      vehicle: this.editForm.get(['vehicle'])!.value,
    };
  }

  private autoCalculateTotalCost(): void {
    this.editForm.get('startDate')?.valueChanges.subscribe(() => {
      this.calculateAndSetTotalCost();
    });

    this.editForm.get('endDate')?.valueChanges.subscribe(() => {
      this.calculateAndSetTotalCost();
    });

    this.editForm.get('vehicle')?.valueChanges.subscribe(() => {
      this.calculateAndSetTotalCost();
    });
  }

  private calculateAndSetTotalCost(): void {
    const startDate = this.editForm.get('startDate')?.value;
    const endDate = this.editForm.get('endDate')?.value;
    const vehicle = this.editForm.get('vehicle')?.value;

    if (startDate && endDate && vehicle) {
      const start = typeof startDate === 'string' ? parseISO(startDate) : startDate;
      const end = typeof endDate === 'string' ? parseISO(endDate) : endDate;
      const days = differenceInDays(end, start) + 1;
      const totalCost = days * vehicle.leaseRate;
      this.editForm.get('totalCost')?.setValue(totalCost);
    }
  }
}

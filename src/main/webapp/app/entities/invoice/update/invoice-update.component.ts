import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IInvoice, Invoice } from '../invoice.model';
import { InvoiceService } from '../service/invoice.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html',
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;

  customersSharedCollection: ICustomer[] = [];

  editForm = this.fb.group({
    id: [],
    totalAmount: [null, [Validators.required]],
    paymentStatus: [null, [Validators.required]],
    dueDate: [],
    customer: [],
  });

  constructor(
    protected invoiceService: InvoiceService,
    protected customerService: CustomerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      this.updateForm(invoice);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>): void {
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

  protected updateForm(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      totalAmount: invoice.totalAmount,
      paymentStatus: invoice.paymentStatus,
      dueDate: invoice.dueDate,
      customer: invoice.customer,
    });

    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(
      this.customersSharedCollection,
      invoice.customer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) =>
          this.customerService.addCustomerToCollectionIfMissing(customers, this.editForm.get('customer')!.value)
        )
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));
  }

  protected createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id'])!.value,
      totalAmount: this.editForm.get(['totalAmount'])!.value,
      paymentStatus: this.editForm.get(['paymentStatus'])!.value,
      dueDate: this.editForm.get(['dueDate'])!.value,
      customer: this.editForm.get(['customer'])!.value,
    };
  }
}

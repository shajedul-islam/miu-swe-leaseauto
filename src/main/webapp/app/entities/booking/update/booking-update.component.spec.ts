import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BookingService } from '../service/booking.service';
import { IBooking, Booking } from '../booking.model';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { InvoiceService } from 'app/entities/invoice/service/invoice.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { IVehicle } from 'app/entities/vehicle/vehicle.model';
import { VehicleService } from 'app/entities/vehicle/service/vehicle.service';

import { BookingUpdateComponent } from './booking-update.component';

describe('Booking Management Update Component', () => {
  let comp: BookingUpdateComponent;
  let fixture: ComponentFixture<BookingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bookingService: BookingService;
  let invoiceService: InvoiceService;
  let customerService: CustomerService;
  let vehicleService: VehicleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BookingUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(BookingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BookingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bookingService = TestBed.inject(BookingService);
    invoiceService = TestBed.inject(InvoiceService);
    customerService = TestBed.inject(CustomerService);
    vehicleService = TestBed.inject(VehicleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call invoice query and add missing value', () => {
      const booking: IBooking = { id: 456 };
      const invoice: IInvoice = { id: 85954 };
      booking.invoice = invoice;

      const invoiceCollection: IInvoice[] = [{ id: 54962 }];
      jest.spyOn(invoiceService, 'query').mockReturnValue(of(new HttpResponse({ body: invoiceCollection })));
      const expectedCollection: IInvoice[] = [invoice, ...invoiceCollection];
      jest.spyOn(invoiceService, 'addInvoiceToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      expect(invoiceService.query).toHaveBeenCalled();
      expect(invoiceService.addInvoiceToCollectionIfMissing).toHaveBeenCalledWith(invoiceCollection, invoice);
      expect(comp.invoicesCollection).toEqual(expectedCollection);
    });

    it('Should call Customer query and add missing value', () => {
      const booking: IBooking = { id: 456 };
      const customer: ICustomer = { id: 47612 };
      booking.customer = customer;

      const customerCollection: ICustomer[] = [{ id: 18828 }];
      jest.spyOn(customerService, 'query').mockReturnValue(of(new HttpResponse({ body: customerCollection })));
      const additionalCustomers = [customer];
      const expectedCollection: ICustomer[] = [...additionalCustomers, ...customerCollection];
      jest.spyOn(customerService, 'addCustomerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      expect(customerService.query).toHaveBeenCalled();
      expect(customerService.addCustomerToCollectionIfMissing).toHaveBeenCalledWith(customerCollection, ...additionalCustomers);
      expect(comp.customersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Vehicle query and add missing value', () => {
      const booking: IBooking = { id: 456 };
      const vehicle: IVehicle = { id: 12063 };
      booking.vehicle = vehicle;

      const vehicleCollection: IVehicle[] = [{ id: 72745 }];
      jest.spyOn(vehicleService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleCollection })));
      const additionalVehicles = [vehicle];
      const expectedCollection: IVehicle[] = [...additionalVehicles, ...vehicleCollection];
      jest.spyOn(vehicleService, 'addVehicleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      expect(vehicleService.query).toHaveBeenCalled();
      expect(vehicleService.addVehicleToCollectionIfMissing).toHaveBeenCalledWith(vehicleCollection, ...additionalVehicles);
      expect(comp.vehiclesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const booking: IBooking = { id: 456 };
      const invoice: IInvoice = { id: 79319 };
      booking.invoice = invoice;
      const customer: ICustomer = { id: 87814 };
      booking.customer = customer;
      const vehicle: IVehicle = { id: 77406 };
      booking.vehicle = vehicle;

      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(booking));
      expect(comp.invoicesCollection).toContain(invoice);
      expect(comp.customersSharedCollection).toContain(customer);
      expect(comp.vehiclesSharedCollection).toContain(vehicle);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Booking>>();
      const booking = { id: 123 };
      jest.spyOn(bookingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: booking }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(bookingService.update).toHaveBeenCalledWith(booking);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Booking>>();
      const booking = new Booking();
      jest.spyOn(bookingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: booking }));
      saveSubject.complete();

      // THEN
      expect(bookingService.create).toHaveBeenCalledWith(booking);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Booking>>();
      const booking = { id: 123 };
      jest.spyOn(bookingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ booking });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bookingService.update).toHaveBeenCalledWith(booking);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackInvoiceById', () => {
      it('Should return tracked Invoice primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackInvoiceById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCustomerById', () => {
      it('Should return tracked Customer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCustomerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackVehicleById', () => {
      it('Should return tracked Vehicle primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVehicleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});

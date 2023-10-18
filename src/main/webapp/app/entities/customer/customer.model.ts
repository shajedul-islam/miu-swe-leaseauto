import { IBooking } from 'app/entities/booking/booking.model';
import { IInvoice } from 'app/entities/invoice/invoice.model';

export interface ICustomer {
  id?: number;
  name?: string;
  email?: string;
  phoneNumber?: string | null;
  address?: string | null;
  drivingLicenseCredentials?: string;
  bookings?: IBooking[] | null;
  invoices?: IInvoice[] | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public phoneNumber?: string | null,
    public address?: string | null,
    public drivingLicenseCredentials?: string,
    public bookings?: IBooking[] | null,
    public invoices?: IInvoice[] | null
  ) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}

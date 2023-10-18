import dayjs from 'dayjs/esm';
import { IBooking } from 'app/entities/booking/booking.model';
import { ICustomer } from 'app/entities/customer/customer.model';

export interface IInvoice {
  id?: number;
  totalAmount?: number;
  paymentStatus?: string;
  dueDate?: dayjs.Dayjs | null;
  booking?: IBooking | null;
  customer?: ICustomer | null;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public totalAmount?: number,
    public paymentStatus?: string,
    public dueDate?: dayjs.Dayjs | null,
    public booking?: IBooking | null,
    public customer?: ICustomer | null
  ) {}
}

export function getInvoiceIdentifier(invoice: IInvoice): number | undefined {
  return invoice.id;
}

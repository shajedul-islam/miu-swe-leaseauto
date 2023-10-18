import dayjs from 'dayjs/esm';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { IVehicle } from 'app/entities/vehicle/vehicle.model';

export interface IBooking {
  id?: number;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  totalCost?: number | null;
  location?: string | null;
  invoice?: IInvoice | null;
  customer?: ICustomer | null;
  vehicle?: IVehicle | null;
}

export class Booking implements IBooking {
  constructor(
    public id?: number,
    public startDate?: dayjs.Dayjs,
    public endDate?: dayjs.Dayjs,
    public totalCost?: number | null,
    public location?: string | null,
    public invoice?: IInvoice | null,
    public customer?: ICustomer | null,
    public vehicle?: IVehicle | null
  ) {}
}

export function getBookingIdentifier(booking: IBooking): number | undefined {
  return booking.id;
}

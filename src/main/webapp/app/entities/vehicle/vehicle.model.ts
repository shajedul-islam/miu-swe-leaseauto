import { IBooking } from 'app/entities/booking/booking.model';

export interface IVehicle {
  id?: number;
  model?: string;
  make?: string;
  year?: number;
  leaseRate?: number;
  availabilityStatus?: string;
  bookings?: IBooking[] | null;
}

export class Vehicle implements IVehicle {
  constructor(
    public id?: number,
    public model?: string,
    public make?: string,
    public year?: number,
    public leaseRate?: number,
    public availabilityStatus?: string,
    public bookings?: IBooking[] | null
  ) {}
}

export function getVehicleIdentifier(vehicle: IVehicle): number | undefined {
  return vehicle.id;
}

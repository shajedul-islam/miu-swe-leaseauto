import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vehicle',
        data: { pageTitle: 'Vehicles' },
        loadChildren: () => import('./vehicle/vehicle.module').then(m => m.VehicleModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'Customers' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'booking',
        data: { pageTitle: 'Bookings' },
        loadChildren: () => import('./booking/booking.module').then(m => m.BookingModule),
      },
      {
        path: 'invoice',
        data: { pageTitle: 'Invoices' },
        loadChildren: () => import('./invoice/invoice.module').then(m => m.InvoiceModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

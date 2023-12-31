import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BookingComponent } from '../list/booking.component';
import { BookingDetailComponent } from '../detail/booking-detail.component';
import { BookingUpdateComponent } from '../update/booking-update.component';
import { BookingRoutingResolveService } from './booking-routing-resolve.service';

const bookingRoute: Routes = [
  {
    path: '',
    component: BookingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookingDetailComponent,
    resolve: {
      booking: BookingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookingUpdateComponent,
    resolve: {
      booking: BookingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new/:vehicleId', // <-- Added this new route for booking with vehicle ID
    component: BookingUpdateComponent,
    resolve: {
      booking: BookingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookingUpdateComponent,
    resolve: {
      booking: BookingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bookingRoute)],
  exports: [RouterModule],
})
export class BookingRoutingModule {}

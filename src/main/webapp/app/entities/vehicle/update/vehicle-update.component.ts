import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IVehicle, Vehicle } from '../vehicle.model';
import { VehicleService } from '../service/vehicle.service';

@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html',
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    model: [null, [Validators.required]],
    make: [null, [Validators.required]],
    year: [null, [Validators.required]],
    leaseRate: [null, [Validators.required]],
    availabilityStatus: [null, [Validators.required]],
  });

  constructor(protected vehicleService: VehicleService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vehicle }) => {
      this.updateForm(vehicle);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
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

  protected updateForm(vehicle: IVehicle): void {
    this.editForm.patchValue({
      id: vehicle.id,
      model: vehicle.model,
      make: vehicle.make,
      year: vehicle.year,
      leaseRate: vehicle.leaseRate,
      availabilityStatus: vehicle.availabilityStatus,
    });
  }

  protected createFromForm(): IVehicle {
    return {
      ...new Vehicle(),
      id: this.editForm.get(['id'])!.value,
      model: this.editForm.get(['model'])!.value,
      make: this.editForm.get(['make'])!.value,
      year: this.editForm.get(['year'])!.value,
      leaseRate: this.editForm.get(['leaseRate'])!.value,
      availabilityStatus: this.editForm.get(['availabilityStatus'])!.value,
    };
  }
}

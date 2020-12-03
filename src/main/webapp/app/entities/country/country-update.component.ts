import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICountry, Country } from 'app/shared/model/country.model';
import { CountryService } from './country.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';

@Component({
  selector: 'jhi-country-update',
  templateUrl: './country-update.component.html',
})
export class CountryUpdateComponent implements OnInit {
  isSaving = false;
  locations: ILocation[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    location: [],
  });

  constructor(
    protected countryService: CountryService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ country }) => {
      this.updateForm(country);

      this.locationService.query().subscribe((res: HttpResponse<ILocation[]>) => (this.locations = res.body || []));
    });
  }

  updateForm(country: ICountry): void {
    this.editForm.patchValue({
      id: country.id,
      name: country.name,
      location: country.location,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const country = this.createFromForm();
    if (country.id !== undefined) {
      this.subscribeToSaveResponse(this.countryService.update(country));
    } else {
      this.subscribeToSaveResponse(this.countryService.create(country));
    }
  }

  private createFromForm(): ICountry {
    return {
      ...new Country(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      location: this.editForm.get(['location'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountry>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ILocation): any {
    return item.id;
  }
}

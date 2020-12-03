import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGoldenBadge, GoldenBadge } from 'app/shared/model/golden-badge.model';
import { GoldenBadgeService } from './golden-badge.service';
import { IIdentifier } from 'app/shared/model/identifier.model';
import { IdentifierService } from 'app/entities/identifier/identifier.service';

@Component({
  selector: 'jhi-golden-badge-update',
  templateUrl: './golden-badge-update.component.html',
})
export class GoldenBadgeUpdateComponent implements OnInit {
  isSaving = false;
  identifiers: IIdentifier[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    iden: [null, Validators.required],
  });

  constructor(
    protected goldenBadgeService: GoldenBadgeService,
    protected identifierService: IdentifierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goldenBadge }) => {
      this.updateForm(goldenBadge);

      this.identifierService.query().subscribe((res: HttpResponse<IIdentifier[]>) => (this.identifiers = res.body || []));
    });
  }

  updateForm(goldenBadge: IGoldenBadge): void {
    this.editForm.patchValue({
      id: goldenBadge.id,
      name: goldenBadge.name,
      iden: goldenBadge.iden,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goldenBadge = this.createFromForm();
    if (goldenBadge.id !== undefined) {
      this.subscribeToSaveResponse(this.goldenBadgeService.update(goldenBadge));
    } else {
      this.subscribeToSaveResponse(this.goldenBadgeService.create(goldenBadge));
    }
  }

  private createFromForm(): IGoldenBadge {
    return {
      ...new GoldenBadge(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      iden: this.editForm.get(['iden'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoldenBadge>>): void {
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

  trackById(index: number, item: IIdentifier): any {
    return item.id;
  }
}

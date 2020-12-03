import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISilverBadge, SilverBadge } from 'app/shared/model/silver-badge.model';
import { SilverBadgeService } from './silver-badge.service';
import { IIdentifier } from 'app/shared/model/identifier.model';
import { IdentifierService } from 'app/entities/identifier/identifier.service';

@Component({
  selector: 'jhi-silver-badge-update',
  templateUrl: './silver-badge-update.component.html',
})
export class SilverBadgeUpdateComponent implements OnInit {
  isSaving = false;
  identifiers: IIdentifier[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    iden: [null, Validators.required],
  });

  constructor(
    protected silverBadgeService: SilverBadgeService,
    protected identifierService: IdentifierService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ silverBadge }) => {
      this.updateForm(silverBadge);

      this.identifierService.query().subscribe((res: HttpResponse<IIdentifier[]>) => (this.identifiers = res.body || []));
    });
  }

  updateForm(silverBadge: ISilverBadge): void {
    this.editForm.patchValue({
      id: silverBadge.id,
      name: silverBadge.name,
      iden: silverBadge.iden,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const silverBadge = this.createFromForm();
    if (silverBadge.id !== undefined) {
      this.subscribeToSaveResponse(this.silverBadgeService.update(silverBadge));
    } else {
      this.subscribeToSaveResponse(this.silverBadgeService.create(silverBadge));
    }
  }

  private createFromForm(): ISilverBadge {
    return {
      ...new SilverBadge(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      iden: this.editForm.get(['iden'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISilverBadge>>): void {
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

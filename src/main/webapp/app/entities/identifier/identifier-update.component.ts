import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIdentifier, Identifier } from 'app/shared/model/identifier.model';
import { IdentifierService } from './identifier.service';

@Component({
  selector: 'jhi-identifier-update',
  templateUrl: './identifier-update.component.html',
})
export class IdentifierUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
  });

  constructor(protected identifierService: IdentifierService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ identifier }) => {
      this.updateForm(identifier);
    });
  }

  updateForm(identifier: IIdentifier): void {
    this.editForm.patchValue({
      id: identifier.id,
      name: identifier.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const identifier = this.createFromForm();
    if (identifier.id !== undefined) {
      this.subscribeToSaveResponse(this.identifierService.update(identifier));
    } else {
      this.subscribeToSaveResponse(this.identifierService.create(identifier));
    }
  }

  private createFromForm(): IIdentifier {
    return {
      ...new Identifier(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIdentifier>>): void {
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
}

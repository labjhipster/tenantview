import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITheLabel, TheLabel } from 'app/shared/model/test-root/the-label.model';
import { TheLabelService } from './the-label.service';

@Component({
  selector: 'jhi-the-label-update',
  templateUrl: './the-label-update.component.html',
})
export class TheLabelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    labelName: [null, [Validators.required, Validators.minLength(3)]],
  });

  constructor(protected theLabelService: TheLabelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ theLabel }) => {
      this.updateForm(theLabel);
    });
  }

  updateForm(theLabel: ITheLabel): void {
    this.editForm.patchValue({
      id: theLabel.id,
      labelName: theLabel.labelName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const theLabel = this.createFromForm();
    if (theLabel.id !== undefined) {
      this.subscribeToSaveResponse(this.theLabelService.update(theLabel));
    } else {
      this.subscribeToSaveResponse(this.theLabelService.create(theLabel));
    }
  }

  private createFromForm(): ITheLabel {
    return {
      ...new TheLabel(),
      id: this.editForm.get(['id'])!.value,
      labelName: this.editForm.get(['labelName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITheLabel>>): void {
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

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITheLabel } from 'app/shared/model/test-root/the-label.model';
import { TheLabelService } from './the-label.service';

@Component({
  templateUrl: './the-label-delete-dialog.component.html',
})
export class TheLabelDeleteDialogComponent {
  theLabel?: ITheLabel;

  constructor(protected theLabelService: TheLabelService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.theLabelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('theLabelListModification');
      this.activeModal.close();
    });
  }
}

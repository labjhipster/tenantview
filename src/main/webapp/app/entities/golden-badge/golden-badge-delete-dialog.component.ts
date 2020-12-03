import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoldenBadge } from 'app/shared/model/golden-badge.model';
import { GoldenBadgeService } from './golden-badge.service';

@Component({
  templateUrl: './golden-badge-delete-dialog.component.html',
})
export class GoldenBadgeDeleteDialogComponent {
  goldenBadge?: IGoldenBadge;

  constructor(
    protected goldenBadgeService: GoldenBadgeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goldenBadgeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('goldenBadgeListModification');
      this.activeModal.close();
    });
  }
}

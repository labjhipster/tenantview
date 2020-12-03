import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISilverBadge } from 'app/shared/model/silver-badge.model';
import { SilverBadgeService } from './silver-badge.service';

@Component({
  templateUrl: './silver-badge-delete-dialog.component.html',
})
export class SilverBadgeDeleteDialogComponent {
  silverBadge?: ISilverBadge;

  constructor(
    protected silverBadgeService: SilverBadgeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.silverBadgeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('silverBadgeListModification');
      this.activeModal.close();
    });
  }
}

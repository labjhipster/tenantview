import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIdentifier } from 'app/shared/model/identifier.model';
import { IdentifierService } from './identifier.service';

@Component({
  templateUrl: './identifier-delete-dialog.component.html',
})
export class IdentifierDeleteDialogComponent {
  identifier?: IIdentifier;

  constructor(
    protected identifierService: IdentifierService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.identifierService.delete(id).subscribe(() => {
      this.eventManager.broadcast('identifierListModification');
      this.activeModal.close();
    });
  }
}

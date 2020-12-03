import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIdentifier } from 'app/shared/model/identifier.model';
import { IdentifierService } from './identifier.service';
import { IdentifierDeleteDialogComponent } from './identifier-delete-dialog.component';

@Component({
  selector: 'jhi-identifier',
  templateUrl: './identifier.component.html',
})
export class IdentifierComponent implements OnInit, OnDestroy {
  identifiers?: IIdentifier[];
  eventSubscriber?: Subscription;

  constructor(protected identifierService: IdentifierService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.identifierService.query().subscribe((res: HttpResponse<IIdentifier[]>) => (this.identifiers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIdentifiers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIdentifier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIdentifiers(): void {
    this.eventSubscriber = this.eventManager.subscribe('identifierListModification', () => this.loadAll());
  }

  delete(identifier: IIdentifier): void {
    const modalRef = this.modalService.open(IdentifierDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.identifier = identifier;
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISilverBadge } from 'app/shared/model/silver-badge.model';
import { SilverBadgeService } from './silver-badge.service';
import { SilverBadgeDeleteDialogComponent } from './silver-badge-delete-dialog.component';

@Component({
  selector: 'jhi-silver-badge',
  templateUrl: './silver-badge.component.html',
})
export class SilverBadgeComponent implements OnInit, OnDestroy {
  silverBadges?: ISilverBadge[];
  eventSubscriber?: Subscription;

  constructor(
    protected silverBadgeService: SilverBadgeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.silverBadgeService.query().subscribe((res: HttpResponse<ISilverBadge[]>) => (this.silverBadges = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSilverBadges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISilverBadge): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSilverBadges(): void {
    this.eventSubscriber = this.eventManager.subscribe('silverBadgeListModification', () => this.loadAll());
  }

  delete(silverBadge: ISilverBadge): void {
    const modalRef = this.modalService.open(SilverBadgeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.silverBadge = silverBadge;
  }
}

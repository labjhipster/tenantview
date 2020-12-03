import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoldenBadge } from 'app/shared/model/golden-badge.model';
import { GoldenBadgeService } from './golden-badge.service';
import { GoldenBadgeDeleteDialogComponent } from './golden-badge-delete-dialog.component';

@Component({
  selector: 'jhi-golden-badge',
  templateUrl: './golden-badge.component.html',
})
export class GoldenBadgeComponent implements OnInit, OnDestroy {
  goldenBadges?: IGoldenBadge[];
  eventSubscriber?: Subscription;

  constructor(
    protected goldenBadgeService: GoldenBadgeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.goldenBadgeService.query().subscribe((res: HttpResponse<IGoldenBadge[]>) => (this.goldenBadges = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGoldenBadges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGoldenBadge): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGoldenBadges(): void {
    this.eventSubscriber = this.eventManager.subscribe('goldenBadgeListModification', () => this.loadAll());
  }

  delete(goldenBadge: IGoldenBadge): void {
    const modalRef = this.modalService.open(GoldenBadgeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.goldenBadge = goldenBadge;
  }
}

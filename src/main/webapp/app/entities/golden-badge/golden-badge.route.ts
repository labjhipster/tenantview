import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGoldenBadge, GoldenBadge } from 'app/shared/model/golden-badge.model';
import { GoldenBadgeService } from './golden-badge.service';
import { GoldenBadgeComponent } from './golden-badge.component';
import { GoldenBadgeDetailComponent } from './golden-badge-detail.component';
import { GoldenBadgeUpdateComponent } from './golden-badge-update.component';

@Injectable({ providedIn: 'root' })
export class GoldenBadgeResolve implements Resolve<IGoldenBadge> {
  constructor(private service: GoldenBadgeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoldenBadge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((goldenBadge: HttpResponse<GoldenBadge>) => {
          if (goldenBadge.body) {
            return of(goldenBadge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoldenBadge());
  }
}

export const goldenBadgeRoute: Routes = [
  {
    path: '',
    component: GoldenBadgeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GoldenBadges',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoldenBadgeDetailComponent,
    resolve: {
      goldenBadge: GoldenBadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GoldenBadges',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoldenBadgeUpdateComponent,
    resolve: {
      goldenBadge: GoldenBadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GoldenBadges',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoldenBadgeUpdateComponent,
    resolve: {
      goldenBadge: GoldenBadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GoldenBadges',
    },
    canActivate: [UserRouteAccessService],
  },
];

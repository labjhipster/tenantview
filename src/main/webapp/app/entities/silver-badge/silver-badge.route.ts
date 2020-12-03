import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISilverBadge, SilverBadge } from 'app/shared/model/silver-badge.model';
import { SilverBadgeService } from './silver-badge.service';
import { SilverBadgeComponent } from './silver-badge.component';
import { SilverBadgeDetailComponent } from './silver-badge-detail.component';
import { SilverBadgeUpdateComponent } from './silver-badge-update.component';

@Injectable({ providedIn: 'root' })
export class SilverBadgeResolve implements Resolve<ISilverBadge> {
  constructor(private service: SilverBadgeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISilverBadge> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((silverBadge: HttpResponse<SilverBadge>) => {
          if (silverBadge.body) {
            return of(silverBadge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SilverBadge());
  }
}

export const silverBadgeRoute: Routes = [
  {
    path: '',
    component: SilverBadgeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SilverBadges',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SilverBadgeDetailComponent,
    resolve: {
      silverBadge: SilverBadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SilverBadges',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SilverBadgeUpdateComponent,
    resolve: {
      silverBadge: SilverBadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SilverBadges',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SilverBadgeUpdateComponent,
    resolve: {
      silverBadge: SilverBadgeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SilverBadges',
    },
    canActivate: [UserRouteAccessService],
  },
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITheLabel, TheLabel } from 'app/shared/model/test-root/the-label.model';
import { TheLabelService } from './the-label.service';
import { TheLabelComponent } from './the-label.component';
import { TheLabelDetailComponent } from './the-label-detail.component';
import { TheLabelUpdateComponent } from './the-label-update.component';

@Injectable({ providedIn: 'root' })
export class TheLabelResolve implements Resolve<ITheLabel> {
  constructor(private service: TheLabelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITheLabel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((theLabel: HttpResponse<TheLabel>) => {
          if (theLabel.body) {
            return of(theLabel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TheLabel());
  }
}

export const theLabelRoute: Routes = [
  {
    path: '',
    component: TheLabelComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'TheLabels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TheLabelDetailComponent,
    resolve: {
      theLabel: TheLabelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TheLabels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TheLabelUpdateComponent,
    resolve: {
      theLabel: TheLabelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TheLabels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TheLabelUpdateComponent,
    resolve: {
      theLabel: TheLabelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TheLabels',
    },
    canActivate: [UserRouteAccessService],
  },
];

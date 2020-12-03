import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIdentifier, Identifier } from 'app/shared/model/identifier.model';
import { IdentifierService } from './identifier.service';
import { IdentifierComponent } from './identifier.component';
import { IdentifierDetailComponent } from './identifier-detail.component';
import { IdentifierUpdateComponent } from './identifier-update.component';

@Injectable({ providedIn: 'root' })
export class IdentifierResolve implements Resolve<IIdentifier> {
  constructor(private service: IdentifierService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIdentifier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((identifier: HttpResponse<Identifier>) => {
          if (identifier.body) {
            return of(identifier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Identifier());
  }
}

export const identifierRoute: Routes = [
  {
    path: '',
    component: IdentifierComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Identifiers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IdentifierDetailComponent,
    resolve: {
      identifier: IdentifierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Identifiers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IdentifierUpdateComponent,
    resolve: {
      identifier: IdentifierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Identifiers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IdentifierUpdateComponent,
    resolve: {
      identifier: IdentifierResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Identifiers',
    },
    canActivate: [UserRouteAccessService],
  },
];

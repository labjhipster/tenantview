import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { navbarRoute } from './layouts/navbar/navbar.route';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/shared/constants/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'company-admin',
          data: {
            authorities: ['ROLE_COMPANY_ADMIN'],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./company-admin/company-admin-routing.module').then(m => m.CompanyAdminRoutingModule),
        },
        ...LAYOUT_ROUTES,
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
})
export class JhipsterSampleApplicationAppRoutingModule {}

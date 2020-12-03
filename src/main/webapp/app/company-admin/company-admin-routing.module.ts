import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

@NgModule({
  imports: [
    JhipsterSampleApplicationSharedModule,
    /* jhipster-needle-add-company-admin-module - JHipster will add Company admin modules here */
    RouterModule.forChild([
      {
        path: 'user-management',
        loadChildren: () => import('app/admin/user-management/user-management.module').then(m => m.UserManagementModule),
      },
      /* jhipster-needle-add-company-admin-route - JHipster will add Company admin routes here */
    ]),
  ],
})
export class CompanyAdminRoutingModule {}

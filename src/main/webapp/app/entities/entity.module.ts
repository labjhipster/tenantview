import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'bank-account-my-suffix',
        loadChildren: () =>
          import('./test-root/bank-account-my-suffix/bank-account-my-suffix.module').then(
            m => m.JhipsterSampleApplicationBankAccountMySuffixModule
          ),
      },
      {
        path: 'the-label',
        loadChildren: () => import('./test-root/the-label/the-label.module').then(m => m.JhipsterSampleApplicationTheLabelModule),
      },
      {
        path: 'operation',
        loadChildren: () => import('./test-root/operation/operation.module').then(m => m.JhipsterSampleApplicationOperationModule),
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.JhipsterSampleApplicationDepartmentModule),
      },
      {
        path: 'job-history',
        loadChildren: () => import('./job-history/job-history.module').then(m => m.JhipsterSampleApplicationJobHistoryModule),
      },
      {
        path: 'job',
        loadChildren: () => import('./job/job.module').then(m => m.JhipsterSampleApplicationJobModule),
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.JhipsterSampleApplicationEmployeeModule),
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.JhipsterSampleApplicationLocationModule),
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.JhipsterSampleApplicationTaskModule),
      },
      {
        path: 'admin/company',
        loadChildren: () => import('./../admin/company/company.module').then(m => m.JhipsterSampleApplicationCompanyModule),
      },
      {
        path: 'golden-badge',
        loadChildren: () => import('./golden-badge/golden-badge.module').then(m => m.JhipsterSampleApplicationGoldenBadgeModule),
      },
      {
        path: 'silver-badge',
        loadChildren: () => import('./silver-badge/silver-badge.module').then(m => m.JhipsterSampleApplicationSilverBadgeModule),
      },
      {
        path: 'identifier',
        loadChildren: () => import('./identifier/identifier.module').then(m => m.JhipsterSampleApplicationIdentifierModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.JhipsterSampleApplicationCountryModule),
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.JhipsterSampleApplicationRegionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplicationEntityModule {}

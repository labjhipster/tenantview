import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { GoldenBadgeComponent } from './golden-badge.component';
import { GoldenBadgeDetailComponent } from './golden-badge-detail.component';
import { GoldenBadgeUpdateComponent } from './golden-badge-update.component';
import { GoldenBadgeDeleteDialogComponent } from './golden-badge-delete-dialog.component';
import { goldenBadgeRoute } from './golden-badge.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(goldenBadgeRoute)],
  declarations: [GoldenBadgeComponent, GoldenBadgeDetailComponent, GoldenBadgeUpdateComponent, GoldenBadgeDeleteDialogComponent],
  entryComponents: [GoldenBadgeDeleteDialogComponent],
})
export class JhipsterSampleApplicationGoldenBadgeModule {}

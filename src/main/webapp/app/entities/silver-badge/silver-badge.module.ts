import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { SilverBadgeComponent } from './silver-badge.component';
import { SilverBadgeDetailComponent } from './silver-badge-detail.component';
import { SilverBadgeUpdateComponent } from './silver-badge-update.component';
import { SilverBadgeDeleteDialogComponent } from './silver-badge-delete-dialog.component';
import { silverBadgeRoute } from './silver-badge.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(silverBadgeRoute)],
  declarations: [SilverBadgeComponent, SilverBadgeDetailComponent, SilverBadgeUpdateComponent, SilverBadgeDeleteDialogComponent],
  entryComponents: [SilverBadgeDeleteDialogComponent],
})
export class JhipsterSampleApplicationSilverBadgeModule {}

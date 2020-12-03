import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { TheLabelComponent } from './the-label.component';
import { TheLabelDetailComponent } from './the-label-detail.component';
import { TheLabelUpdateComponent } from './the-label-update.component';
import { TheLabelDeleteDialogComponent } from './the-label-delete-dialog.component';
import { theLabelRoute } from './the-label.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(theLabelRoute)],
  declarations: [TheLabelComponent, TheLabelDetailComponent, TheLabelUpdateComponent, TheLabelDeleteDialogComponent],
  entryComponents: [TheLabelDeleteDialogComponent],
})
export class JhipsterSampleApplicationTheLabelModule {}

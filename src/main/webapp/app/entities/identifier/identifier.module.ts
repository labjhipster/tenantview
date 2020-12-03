import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { IdentifierComponent } from './identifier.component';
import { IdentifierDetailComponent } from './identifier-detail.component';
import { IdentifierUpdateComponent } from './identifier-update.component';
import { IdentifierDeleteDialogComponent } from './identifier-delete-dialog.component';
import { identifierRoute } from './identifier.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(identifierRoute)],
  declarations: [IdentifierComponent, IdentifierDetailComponent, IdentifierUpdateComponent, IdentifierDeleteDialogComponent],
  entryComponents: [IdentifierDeleteDialogComponent],
})
export class JhipsterSampleApplicationIdentifierModule {}

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SilverBadgeUpdateComponent } from 'app/entities/silver-badge/silver-badge-update.component';
import { SilverBadgeService } from 'app/entities/silver-badge/silver-badge.service';
import { SilverBadge } from 'app/shared/model/silver-badge.model';

describe('Component Tests', () => {
  describe('SilverBadge Management Update Component', () => {
    let comp: SilverBadgeUpdateComponent;
    let fixture: ComponentFixture<SilverBadgeUpdateComponent>;
    let service: SilverBadgeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SilverBadgeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SilverBadgeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SilverBadgeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SilverBadgeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SilverBadge(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SilverBadge();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

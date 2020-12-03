import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../../test.module';
import { TheLabelUpdateComponent } from 'app/entities/test-root/the-label/the-label-update.component';
import { TheLabelService } from 'app/entities/test-root/the-label/the-label.service';
import { TheLabel } from 'app/shared/model/test-root/the-label.model';

describe('Component Tests', () => {
  describe('TheLabel Management Update Component', () => {
    let comp: TheLabelUpdateComponent;
    let fixture: ComponentFixture<TheLabelUpdateComponent>;
    let service: TheLabelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TheLabelUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TheLabelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TheLabelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TheLabelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TheLabel(123);
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
        const entity = new TheLabel();
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

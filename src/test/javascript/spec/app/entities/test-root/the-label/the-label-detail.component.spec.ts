import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../../test.module';
import { TheLabelDetailComponent } from 'app/entities/test-root/the-label/the-label-detail.component';
import { TheLabel } from 'app/shared/model/test-root/the-label.model';

describe('Component Tests', () => {
  describe('TheLabel Management Detail Component', () => {
    let comp: TheLabelDetailComponent;
    let fixture: ComponentFixture<TheLabelDetailComponent>;
    const route = ({ data: of({ theLabel: new TheLabel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [TheLabelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TheLabelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TheLabelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load theLabel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.theLabel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

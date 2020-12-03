import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { GoldenBadgeDetailComponent } from 'app/entities/golden-badge/golden-badge-detail.component';
import { GoldenBadge } from 'app/shared/model/golden-badge.model';

describe('Component Tests', () => {
  describe('GoldenBadge Management Detail Component', () => {
    let comp: GoldenBadgeDetailComponent;
    let fixture: ComponentFixture<GoldenBadgeDetailComponent>;
    const route = ({ data: of({ goldenBadge: new GoldenBadge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [GoldenBadgeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GoldenBadgeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoldenBadgeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goldenBadge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goldenBadge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SilverBadgeDetailComponent } from 'app/entities/silver-badge/silver-badge-detail.component';
import { SilverBadge } from 'app/shared/model/silver-badge.model';

describe('Component Tests', () => {
  describe('SilverBadge Management Detail Component', () => {
    let comp: SilverBadgeDetailComponent;
    let fixture: ComponentFixture<SilverBadgeDetailComponent>;
    const route = ({ data: of({ silverBadge: new SilverBadge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SilverBadgeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SilverBadgeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SilverBadgeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load silverBadge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.silverBadge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

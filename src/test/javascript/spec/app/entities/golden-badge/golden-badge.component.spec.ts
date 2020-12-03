import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { GoldenBadgeComponent } from 'app/entities/golden-badge/golden-badge.component';
import { GoldenBadgeService } from 'app/entities/golden-badge/golden-badge.service';
import { GoldenBadge } from 'app/shared/model/golden-badge.model';

describe('Component Tests', () => {
  describe('GoldenBadge Management Component', () => {
    let comp: GoldenBadgeComponent;
    let fixture: ComponentFixture<GoldenBadgeComponent>;
    let service: GoldenBadgeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [GoldenBadgeComponent],
      })
        .overrideTemplate(GoldenBadgeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoldenBadgeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoldenBadgeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GoldenBadge(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.goldenBadges && comp.goldenBadges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

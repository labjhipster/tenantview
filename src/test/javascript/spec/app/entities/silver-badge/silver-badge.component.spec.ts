import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { SilverBadgeComponent } from 'app/entities/silver-badge/silver-badge.component';
import { SilverBadgeService } from 'app/entities/silver-badge/silver-badge.service';
import { SilverBadge } from 'app/shared/model/silver-badge.model';

describe('Component Tests', () => {
  describe('SilverBadge Management Component', () => {
    let comp: SilverBadgeComponent;
    let fixture: ComponentFixture<SilverBadgeComponent>;
    let service: SilverBadgeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [SilverBadgeComponent],
      })
        .overrideTemplate(SilverBadgeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SilverBadgeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SilverBadgeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SilverBadge(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.silverBadges && comp.silverBadges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

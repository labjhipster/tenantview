import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { CompanyDetailComponent } from 'app/entities/../admin/company/company-detail.component';
import { Company } from 'app/shared/model/../admin/company.model';

describe('Component Tests', () => {
  describe('Company Management Detail Component', () => {
    let comp: CompanyDetailComponent;
    let fixture: ComponentFixture<CompanyDetailComponent>;
    const route = ({ data: of({ company: new Company(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [CompanyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CompanyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load company on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.company).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

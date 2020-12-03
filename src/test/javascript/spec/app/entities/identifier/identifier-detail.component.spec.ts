import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { IdentifierDetailComponent } from 'app/entities/identifier/identifier-detail.component';
import { Identifier } from 'app/shared/model/identifier.model';

describe('Component Tests', () => {
  describe('Identifier Management Detail Component', () => {
    let comp: IdentifierDetailComponent;
    let fixture: ComponentFixture<IdentifierDetailComponent>;
    const route = ({ data: of({ identifier: new Identifier(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [IdentifierDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IdentifierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IdentifierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load identifier on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.identifier).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

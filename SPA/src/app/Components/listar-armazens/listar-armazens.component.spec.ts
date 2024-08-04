import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NgZone, NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { Armazem } from 'src/app/Model/armazem';
import { RouterTestingModule } from '@angular/router/testing';
import { ArmazemService } from '../../Services/armazem.service';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { ListarArmazensComponent } from './listar-armazens.component';
import { HeaderComponent } from 'src/app/header/header.component';
import { LoginComponent } from 'src/app/login/login.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ArmazemIdPipe } from 'src/app/Pipes/armazemIdFilter';
import { ArmazemDesignacaoPipe } from 'src/app/Pipes/armazemDesignacaoFilter';
import { isNull } from 'cypress/types/lodash';

fdescribe('ListarArmazensComponent', () => {

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<ArmazemService>(['getArmazens','addArmazem','getAllArmazens']);
    const armazemPipe = jasmine.createSpyObj<ArmazemIdPipe>(['transform']);
    const armazemPipe2 = jasmine.createSpyObj<ArmazemDesignacaoPipe>(['transform']);
    entregaServiceSpy.getAllArmazens.and.returnValue(of([]));
    entregaServiceSpy.getArmazens.and.returnValue(of([]));
    await TestBed.configureTestingModule({
      declarations: [ArmazemIdPipe, ArmazemDesignacaoPipe, ListarArmazensComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: ArmazemService, ArmazemIdPipe, ArmazemDesignacaoPipe, useValue: entregaServiceSpy,armazemPipe,armazemPipe2}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(ListarArmazensComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
    });

    it('should have as title', (() => {
      const fixture = TestBed.createComponent(ListarArmazensComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('title').textContent).not.toContain('Criar Armazém');
      }));

    it('should render title in a h1 tag', (() => {
      const fixture = TestBed.createComponent(ListarArmazensComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('h1').textContent).toContain('ARMAZÉNS');
    }));

})

fdescribe('ListarArmazemComponent', () => {
  let component: ListarArmazensComponent;
  let router: Router;
  let service: ArmazemService;
  let titleService: Title;
  let ngZone: NgZone;

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<ArmazemService>(['getArmazens','addArmazem','getAllArmazens','getArmazensAtivos','patchAtivo']);
    const armazemPipe = jasmine.createSpyObj<ArmazemIdPipe>(['transform']);
    const armazemPipe2 = jasmine.createSpyObj<ArmazemDesignacaoPipe>(['transform']);
    entregaServiceSpy.getAllArmazens.and.returnValue(of([]));
    entregaServiceSpy.getArmazens.and.returnValue(of([]));
    await TestBed.configureTestingModule({
      declarations: [ArmazemIdPipe, ArmazemDesignacaoPipe, ListarArmazensComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: ArmazemService, ArmazemIdPipe, ArmazemDesignacaoPipe, useValue: entregaServiceSpy,armazemPipe,armazemPipe2}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(inject([Router, ArmazemService, Title, NgZone], (r:Router, s:ArmazemService, t:Title, n: NgZone) => {
    router = r;
    service = s;
    titleService = t;
    ngZone = n;
    component = new ListarArmazensComponent(service, titleService);
  }));


  it('should not reload - ngOnInit', () => {
    spyOn(router, 'navigate');
    component.ngOnInit();
    expect(router.navigate).not.toHaveBeenCalledWith(['listar-armazens']);
  });

  it('should call getArmazens Service', () => {
    component.getArmazens();
    expect(service.getArmazens).toHaveBeenCalled();
  });
 
});

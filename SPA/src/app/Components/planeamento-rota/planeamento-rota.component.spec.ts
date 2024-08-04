import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { PlaneamentoService } from 'src/app/Services/planeamento.service';
import { ArmazemService } from 'src/app/Services/armazem.service';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { PlaneamentoRotaComponent } from './planeamento-rota.component';
import { HeaderComponent } from 'src/app/header/header.component';
import { LoginComponent } from 'src/app/login/login.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ArmazemIdPipe } from 'src/app/Pipes/armazemIdFilter';
import { ArmazemDesignacaoPipe } from 'src/app/Pipes/armazemDesignacaoFilter';
import { EntregaService } from 'src/app/Services/entrega.service';

fdescribe('PlaneamentoRotaComponent', () => {

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<ArmazemService>(['getArmazens','addArmazem','getAllArmazens']);
    const armazemPipe = jasmine.createSpyObj<ArmazemIdPipe>(['transform']);
    const armazemPipe2 = jasmine.createSpyObj<ArmazemDesignacaoPipe>(['transform']);
    entregaServiceSpy.getAllArmazens.and.returnValue(of([]));
    entregaServiceSpy.getArmazens.and.returnValue(of([]));
    await TestBed.configureTestingModule({
      declarations: [ArmazemIdPipe, ArmazemDesignacaoPipe, PlaneamentoRotaComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: ArmazemService, ArmazemIdPipe, ArmazemDesignacaoPipe, useValue: entregaServiceSpy,armazemPipe,armazemPipe2}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(PlaneamentoRotaComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
    });

    it('should have as title', (() => {
      const fixture = TestBed.createComponent(PlaneamentoRotaComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('title').textContent).not.toContain('SPA');
      }));

    it('should render title in a h1 tag', (() => {
      const fixture = TestBed.createComponent(PlaneamentoRotaComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('h1').textContent).toContain('PLANEAMENTO ROTA');
    }));

  
})

fdescribe('PlaneamentoRotaComponent', () => {
  let component: PlaneamentoRotaComponent;
  let router: Router;
  let entregaService: EntregaService;
  let service: PlaneamentoService;
  let titleService: Title;

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<PlaneamentoService>(['getHeuMock']);
    entregaServiceSpy.getHeuMock.and.returnValue(of([]));
    await TestBed.configureTestingModule({
      
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule,] ,
      providers: [{provide: PlaneamentoService, useValue: entregaServiceSpy}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(inject([Router, EntregaService, PlaneamentoService, Title], (r:Router,e: EntregaService, s:PlaneamentoService, t:Title) => {
    router = r;
    service = s;
    titleService = t;
    entregaService = e;
    component = new PlaneamentoRotaComponent(service,entregaService, titleService);
  }));

  it('should not reload - ngOnInit', () => {
    spyOn(router, 'navigate');
    component.ngOnInit();
    expect(router.navigate).not.toHaveBeenCalledWith(['planeamento-rota']);
  });

  it('should call Planeamento Service', () => {
    component.getMock();
    expect(service.getHeuMock).toHaveBeenCalled();
  });
 
});
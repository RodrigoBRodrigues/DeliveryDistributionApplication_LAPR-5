import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NgZone, NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { HeaderComponent } from 'src/app/header/header.component';
import { LoginComponent } from 'src/app/login/login.component';
import { CamiaoMatriculaPipe } from 'src/app/Pipes/camiaoMatriculaFilter';
import { ArmazemService } from 'src/app/Services/armazem.service';
import { CamiaoService } from 'src/app/Services/camiao.service';

import { ListarCamioesComponent } from './listar-camioes.component';

fdescribe('ListarCamiõesComponent', () => {

  beforeEach(async () => {
    const camiaoServiceSpy = jasmine.createSpyObj<CamiaoService>(['addCamiao','patchCamiao','getCamioes']);
    const camiaoPipe = jasmine.createSpyObj<CamiaoMatriculaPipe>(['transform']);
    camiaoServiceSpy.getCamioes.and.returnValue(of([]));
    //camiaoServiceSpy.getArmazens.and.returnValue(of([]));
    await TestBed.configureTestingModule({
      declarations: [CamiaoMatriculaPipe,  ListarCamioesComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: CamiaoService, CamiaoMatriculaPipe, useValue: camiaoServiceSpy,camiaoPipe}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(ListarCamioesComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
    });

    it('should have as title', (() => {
      const fixture = TestBed.createComponent(ListarCamioesComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('title').textContent).not.toContain('Criar Camião');
      }));

    it('should render title in a h1 tag', (() => {
      const fixture = TestBed.createComponent(ListarCamioesComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('h1').textContent).toContain('CAMIOES');
    }));

})

fdescribe('ListarCamiaoComponent', () => {
  let component: ListarCamioesComponent;
  let router: Router;
  let service: CamiaoService;
  let titleService: Title;
  let ngZone: NgZone;

  beforeEach(async () => {
    const camiaoServiceSpy = jasmine.createSpyObj<CamiaoService>(['addCamiao','patchCamiao','getCamioes']);
    const camiaoPipe = jasmine.createSpyObj<CamiaoMatriculaPipe>(['transform']);
    camiaoServiceSpy.getCamioes.and.returnValue(of([]));
    //entregaServiceSpy.getArmazens.and.returnValue(of([]));
    await TestBed.configureTestingModule({
      declarations: [CamiaoMatriculaPipe,  ListarCamioesComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: CamiaoService, CamiaoMatriculaPipe, useValue: camiaoServiceSpy,camiaoPipe}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  beforeEach(inject([Router, CamiaoService, Title, NgZone], (r:Router, s:CamiaoService, t:Title, n: NgZone) => {
    router = r;
    service = s;
    titleService = t;
    ngZone = n;
    component = new ListarCamioesComponent(service, titleService);
  }));


  it('should not reload - ngOnInit', () => {
    spyOn(router, 'navigate');
    component.ngOnInit();
    expect(router.navigate).not.toHaveBeenCalledWith(['listar-camioes']);
  });

  it('should call getCamioes Service', () => {
    component.getCamioes();
    expect(service.getCamioes).toHaveBeenCalled();
  });
 
});


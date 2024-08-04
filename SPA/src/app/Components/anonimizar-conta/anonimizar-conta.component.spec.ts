import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { HeaderComponent } from 'src/app/header/header.component';
import { LoginComponent } from 'src/app/login/login.component';
import { AuthService } from 'src/app/Services/auth.service';

import { AnonimizarContaComponent } from './anonimizar-conta.component';

fdescribe('AnonimizarContaComponent', () => {

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<AuthService>(['anonimizarConta', 'getUtilizadores']);
    entregaServiceSpy.anonimizarConta.and.returnValue(of());
    entregaServiceSpy.getUtilizadores.and.returnValue(of());
    await TestBed.configureTestingModule({
      declarations: [ AnonimizarContaComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: AuthService, useValue: entregaServiceSpy}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(AnonimizarContaComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
    });

    it('should have as title', () => {
      const fixture = TestBed.createComponent(AnonimizarContaComponent);
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('title').textContent).toContain('Utilizadores');
      });

    it('should render title in a h1 tag', (() => {
      const fixture = TestBed.createComponent(AnonimizarContaComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('h1').textContent).toContain('ANONIMIZAR');
    }));

});

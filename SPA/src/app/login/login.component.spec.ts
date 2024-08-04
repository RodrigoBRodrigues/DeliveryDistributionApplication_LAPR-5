import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NgZone, NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { HeaderComponent } from '../header/header.component';
import { AuthService } from '../Services/auth.service';

import { LoginComponent } from './login.component';

fdescribe('LoginComponent', () => {

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<AuthService>(['LoginWithGoogle']);
    entregaServiceSpy.LoginWithGoogle.and.returnValue(of());
    await TestBed.configureTestingModule({
      declarations: [ HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: AuthService, useValue: entregaServiceSpy}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(LoginComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
    });

    it('should have as title', () => {
      const fixture = TestBed.createComponent(LoginComponent);
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('title').textContent).toContain('Login');
      });
});

describe('LoginComponent', () => {
  let component: LoginComponent;
  let router: Router;
  let service: AuthService;
  let titleService: Title;
  let ngZone: NgZone;

  beforeEach(async() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      providers: [
        { provide: Router, useValue: router },
        { provide: AuthService, useValue: service },
        { provide: Title, useValue: titleService },
        { provide: NgZone, useValue: ngZone },
      ],
    })
    .compileComponents();
  });

  beforeEach(inject([Router, AuthService, Title, NgZone], (r:Router, s:AuthService, t:Title, n: NgZone) => {
    router = r;
    service = s;
    titleService = t;
    ngZone = n;
    component = new LoginComponent(router, service, ngZone, titleService);
  }));

  it('should login with correct username and password', () => {
    spyOn(router, 'navigate');
    component.login('armazem', 'password');
    expect(router.navigate).toHaveBeenCalledWith(['menu-gestor-armazem']);
  });

  it('should not login with incorrect username and password', () => {
    spyOn(router, 'navigate');
    component.login('invalid', 'password');
    expect(router.navigate).not.toHaveBeenCalled();
  });
});

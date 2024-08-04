import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { ArmazemService } from '../../Services/armazem.service';

import { CriarArmazemComponent } from './criar-armazem.component';
import { HeaderComponent } from 'src/app/header/header.component';
import { LoginComponent } from 'src/app/login/login.component';
import { MatToolbarModule } from '@angular/material/toolbar';


fdescribe('CriarArmazemComponent', () => {

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<ArmazemService>(['getAllArmazens']);
    entregaServiceSpy.getAllArmazens.and.returnValue(of([]));
    await TestBed.configureTestingModule({
      declarations: [ CriarArmazemComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: ArmazemService, useValue: entregaServiceSpy}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(CriarArmazemComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
    });

    it('should have as title', () => {
      const fixture = TestBed.createComponent(CriarArmazemComponent);
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('title').textContent).toContain('Criar Armazém')
      });

    it('should render title in a h1 tag', (() => {
      const fixture = TestBed.createComponent(CriarArmazemComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('h1').textContent).toContain('CRIAR ARMAZÉM');
    }));

    it('should have Identificador do Armazém', (() => {
      const fixture = TestBed.createComponent(CriarArmazemComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('b').textContent).toContain('Identificador do Armazém');
    }));

    it('should have button Criar', (() => {
      const fixture = TestBed.createComponent(CriarArmazemComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('button').textContent).toContain('Criar');
    }));
  
})

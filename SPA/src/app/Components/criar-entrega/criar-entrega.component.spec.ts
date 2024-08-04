import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { EntregaService } from '../../Services/entrega.service';

import { CriarEntregaComponent } from './criar-entrega.component';
import { HeaderComponent } from 'src/app/header/header.component';
import { LoginComponent } from 'src/app/login/login.component';
import { MatToolbarModule } from '@angular/material/toolbar';

fdescribe('CriarEntregaComponent', () => {
  let component: CriarEntregaComponent;
  let fixture: ComponentFixture<CriarEntregaComponent>;

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<EntregaService>(['getEntregas']);
    entregaServiceSpy.getEntregas.and.returnValue(of([]));
    await TestBed.configureTestingModule({
      declarations: [ CriarEntregaComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: EntregaService, useValue: entregaServiceSpy}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();

  });

  it('should create', () => {
    fixture = TestBed.createComponent(CriarEntregaComponent);
    component = fixture.componentInstance;
    expect(component).toBeTruthy();
  });


  it('should render title in a h1 tag', (() => {
      const fixture = TestBed.createComponent(CriarEntregaComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('h1').textContent).toContain('CRIAR ENTREGA');
    }));

    it('should have Identificador do Armazém', (() => {
      const fixture = TestBed.createComponent(CriarEntregaComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('b').textContent).toContain('Data');
    }));

    it('should have button Criar', (() => {
      const fixture = TestBed.createComponent(CriarEntregaComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('button').textContent).toContain('Criar');
    }));
});

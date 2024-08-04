import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { EntregaService } from '../../Services/entrega.service';

import { ListarEntregasComponent } from './listar-entregas.component';
import { HeaderComponent } from 'src/app/header/header.component';
import { LoginComponent } from 'src/app/login/login.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EntregaArmazemChegadaFilter } from 'src/app/Pipes/entregaArmazemChegadaFilter';
import { EntregaEntreDatasFilter } from 'src/app/Pipes/entregaEntreDatasFilter';

fdescribe('ListarEntregasComponent', () => {
  let component: ListarEntregasComponent;
  let fixture: ComponentFixture<ListarEntregasComponent>;

  beforeEach(async () => {
    const entregaServiceSpy = jasmine.createSpyObj<EntregaService>(['getEntregas','addEntrega']);
    const armazemPipe = jasmine.createSpyObj<EntregaArmazemChegadaFilter>(['transform']);
    const armazemPipe2 = jasmine.createSpyObj<EntregaEntreDatasFilter>(['transform']);
    entregaServiceSpy.getEntregas.and.returnValue(of([]));
      await TestBed.configureTestingModule({
      declarations: [EntregaArmazemChegadaFilter, EntregaEntreDatasFilter, ListarEntregasComponent,  HeaderComponent, LoginComponent],
      imports: [ RouterTestingModule, HttpClientTestingModule, MatToolbarModule, ],
      providers: [{provide: EntregaArmazemChegadaFilter, EntregaEntreDatasFilter, useValue: entregaServiceSpy,armazemPipe,armazemPipe2}],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
    })
    .compileComponents();
  });

  it('should create', () => {
    const fixture = TestBed.createComponent(ListarEntregasComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
    });


    it('should render title in a h1 tag', (() => {
      const fixture = TestBed.createComponent(ListarEntregasComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('h1').textContent).toContain('ENTREGAS');
    }));


})

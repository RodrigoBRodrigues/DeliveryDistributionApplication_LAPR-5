import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarPercursosComponent } from './listar-percursos.component';

describe('ListarPercursosComponent', () => {
  let component: ListarPercursosComponent;
  let fixture: ComponentFixture<ListarPercursosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarPercursosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarPercursosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

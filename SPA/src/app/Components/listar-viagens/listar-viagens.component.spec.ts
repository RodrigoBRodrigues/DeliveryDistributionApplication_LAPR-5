import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarViagensComponent } from './listar-viagens.component';

describe('ListarViagensComponent', () => {
  let component: ListarViagensComponent;
  let fixture: ComponentFixture<ListarViagensComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListarViagensComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarViagensComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

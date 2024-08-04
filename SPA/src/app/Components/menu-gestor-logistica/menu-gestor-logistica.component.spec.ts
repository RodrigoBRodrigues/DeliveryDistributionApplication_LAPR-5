import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuGestorLogisticaComponent } from './menu-gestor-logistica.component';

describe('MenuGestorLogisticaComponent', () => {
  let component: MenuGestorLogisticaComponent;
  let fixture: ComponentFixture<MenuGestorLogisticaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuGestorLogisticaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuGestorLogisticaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuGestorArmazemComponent } from './menu-gestor-armazem.component';

describe('MenuGestorArmazemComponent', () => {
  let component: MenuGestorArmazemComponent;
  let fixture: ComponentFixture<MenuGestorArmazemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuGestorArmazemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuGestorArmazemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

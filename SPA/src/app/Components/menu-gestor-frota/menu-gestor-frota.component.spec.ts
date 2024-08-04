import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuGestorFrotaComponent } from './menu-gestor-frota.component';

describe('MenuGestorFrotaComponent', () => {
  let component: MenuGestorFrotaComponent;
  let fixture: ComponentFixture<MenuGestorFrotaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuGestorFrotaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuGestorFrotaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

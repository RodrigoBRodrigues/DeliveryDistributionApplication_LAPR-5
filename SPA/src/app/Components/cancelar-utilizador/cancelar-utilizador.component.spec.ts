import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancelarUtilizadorComponent } from './cancelar-utilizador.component';

describe('cancelarUtilizadorComponent', () => {
  let component: CancelarUtilizadorComponent;
  let fixture: ComponentFixture<CancelarUtilizadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CancelarUtilizadorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancelarUtilizadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

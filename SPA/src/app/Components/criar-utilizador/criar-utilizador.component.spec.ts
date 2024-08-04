import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarUtilizadorComponent } from './criar-utilizador.component';

describe('CriarUtilizadorComponent', () => {
  let component: CriarUtilizadorComponent;
  let fixture: ComponentFixture<CriarUtilizadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CriarUtilizadorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarUtilizadorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

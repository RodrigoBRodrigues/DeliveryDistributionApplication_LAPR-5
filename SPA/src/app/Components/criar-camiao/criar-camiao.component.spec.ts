import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarCamiaoComponent } from './criar-camiao.component';

describe('CriarCamiaoComponent', () => {
  let component: CriarCamiaoComponent;
  let fixture: ComponentFixture<CriarCamiaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CriarCamiaoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarCamiaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

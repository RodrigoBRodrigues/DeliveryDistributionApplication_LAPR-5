import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MoverCamiaoComponent } from './mover-camiao.component';

describe('MoverCamiaoComponent', () => {
  let component: MoverCamiaoComponent;
  let fixture: ComponentFixture<MoverCamiaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MoverCamiaoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MoverCamiaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

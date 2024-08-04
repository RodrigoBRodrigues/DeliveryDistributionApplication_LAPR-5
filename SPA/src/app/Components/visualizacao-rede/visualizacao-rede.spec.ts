import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizacaoRedeComponent } from './visualizacao-rede.component';

describe('VisualizacaoRedeComponent', () => {
  let component: VisualizacaoRedeComponent;
  let fixture: ComponentFixture<VisualizacaoRedeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisualizacaoRedeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisualizacaoRedeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

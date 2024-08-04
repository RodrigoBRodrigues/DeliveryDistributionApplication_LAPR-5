import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarPercursoComponent } from './criar-percurso.component';

describe('CriarPercursoComponent', () => {
  let component: CriarPercursoComponent;
  let fixture: ComponentFixture<CriarPercursoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CriarPercursoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CriarPercursoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

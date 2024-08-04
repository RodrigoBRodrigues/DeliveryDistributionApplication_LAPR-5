import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Entrega } from 'src/app/Model/entrega';
import { EntregaService } from 'src/app/Services/entrega.service';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'app-listar-entregas',
  templateUrl: './listar-entregas.component.html',
  styleUrls: ['./listar-entregas.component.css'],
})
export class ListarEntregasComponent implements OnInit {
  displayedColumns: string[] = [
    'data',
    'massa',
    'armazemDesignacao',
    'tempoColocar',
    'tempoRetirar',
  ];
  entregasData: Entrega[] = [];
  entregaFilter1 = '';
  entregaFilter2 = '';
  entregaFilter3 = '';
  constructor(
    private entregaService: EntregaService,
    private titleService: Title
  ) {
    this.titleService.setTitle('Listar Entregas');
  }

  ngOnInit(): void {
    const req = this.getEntregas();
    req.subscribe((x) => {
      this.entregasData = x;
      //console.log(x);
    });
  }

  getEntregas(): Observable<Entrega[]> {
    return this.entregaService.getEntregas();
  }
}

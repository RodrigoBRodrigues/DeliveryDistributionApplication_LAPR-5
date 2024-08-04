import { Component, OnInit, Input, Output } from '@angular/core';
import { PlaneamentoService } from 'src/app/Services/planeamento.service';
import { Entrega } from 'src/app/Model/entrega';
import { EntregaService } from 'src/app/Services/entrega.service';
import { Observable } from 'rxjs';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'app-planeamento-rota',
  templateUrl: './planeamento-rota.component.html',
  styleUrls: ['./planeamento-rota.component.css'],
})
export class PlaneamentoRotaComponent implements OnInit {
  displayedColumns: string[] = [
    'data',
    'massa',
    'armazemDesignacao',
    'tempoColocar',
    'tempoRetirar',
  ];
  @Input() Camiao: string = '';
  @Input() Dia: string = new Date().toISOString();
  public data: string = '';
  rotaLista: string[] = [];
  limpar: string[] = [];
  entregasData: Entrega[] = [];
  rota: string = '';

  constructor(
    private service: PlaneamentoService,
    private entregaService: EntregaService,
    private titleService: Title
  ) {
    this.titleService.setTitle('Planeamento Rota');
  }

  ngOnInit(): void {}

  /*getPlaneamento():void {
    this.rotaLista = this.limpar;
    const a = this.service.getSolucaoOtima(this.Camiao, this.Dia);
    a.subscribe(sk => {
      console.log(sk);
      this.rotaLista = sk;
    });
  }*/

  getHeu1(): void {
    this.rotaLista = this.limpar;
    const d1 = this.Dia.replace('-', '');
    const data = d1.replace('-', '');
    console.log(data);
    const a = this.service.getHeu1(this.Camiao, data);
    a.subscribe((sk) => {
      console.log(sk);
      this.rotaLista = sk;
    });
    const opa = this.rotaLista.toString();
    this.rota = opa.replace(',', '->');
    console.log(this.Dia);
    const req = this.getEntregas(this.Dia);
    req.subscribe((x) => {
      this.entregasData = x;
      //console.log(x);
    });
  }

  getHeu2(): void {
    this.rotaLista = this.limpar;
    const d1 = this.Dia.replace('-', '');
    const data = d1.replace('-', '');
    console.log(data);
    const a = this.service.getHeu2(this.Camiao, data);
    a.subscribe((sk) => {
      console.log(sk);
      this.rotaLista = sk;
    });
    const opa = this.rotaLista.toString();
    this.rota = opa.replace(',', '->');
    const req = this.getEntregas(this.Dia);
    req.subscribe((x) => {
      this.entregasData = x;
      //console.log(x);
    });
  }

  getHeu3(): void {
    this.rotaLista = this.limpar;
    const d1 = this.Dia.replace('-', '');
    const data = d1.replace('-', '');
    console.log(data);
    const a = this.service.getHeu3(this.Camiao, data);
    a.subscribe((sk) => {
      console.log(sk);
      this.rotaLista = sk;
    });
    const opa = this.rotaLista.toString();
    this.rota = opa.replace(',', '->');
    const req = this.getEntregas(this.Dia);
    req.subscribe((x) => {
      this.entregasData = x;
      //console.log(x);
    });
  }

  getAG(): void {
    this.rotaLista = this.limpar;
    const d1 = this.Dia.replace('-', '');
    const data = d1.replace('-', '');
    console.log(data);
    const a = this.service.getAG(this.Camiao, data);
    a.subscribe((sk) => {
      console.log(sk);
      this.rotaLista = sk;
    });
    const opa = this.rotaLista.toString();
    this.rota = opa.replace(',', '->');
    const req = this.getEntregas(this.Dia);
    req.subscribe((x) => {
      this.entregasData = x;
      //console.log(x);
    });
  }

  getAG3(): void {
    this.rotaLista = this.limpar;
    const d1 = this.Dia.replace('-', '');
    const data = d1.replace('-', '');
    console.log(data);
    const a = this.service.getAG3(this.Camiao, data);
    a.subscribe((sk) => {
      console.log(sk);
      this.rotaLista = sk;
    });
    const req = this.getEntregas(this.Dia);
    req.subscribe((x) => {
      this.entregasData = x;
      //console.log(x);
    });
  }

  getMock(): void {
    this.rotaLista = this.limpar;
    const d1 = this.Dia.replace('-', '');
    const data = d1.replace('-', '');
    console.log(data);
    const a = this.service.getHeuMock(this.Camiao, data);

    a.subscribe((sk) => {
      console.log(sk);
      // this.rotaLista = sk;
      this.rotaLista = [
        'Matosinhos',
        'Porto',
        'Vila Nova de Gaia',
        'Gondomar',
        'Maia',
        'Matosinhos',
      ];
      console.log(this.rota);
    });
    const opa = this.rotaLista.toString();
    this.rota = opa.replace(',', '->');
    const req = this.getEntregas(this.Dia);
    req.subscribe((x) => {
      this.entregasData = x;
      //console.log(x);
    });
  }

  getEntregas(dia: string): Observable<Entrega[]> {
    return this.entregaService.getEntregasDia(dia);
  }
}

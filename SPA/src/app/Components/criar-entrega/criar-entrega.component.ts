import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import {Title} from "@angular/platform-browser";
import { Entrega } from 'src/app/Model/entrega';
import { Armazem } from 'src/app/Model/armazem';
import { ArmazemService } from 'src/app/Services/armazem.service';
import { EntregaService } from 'src/app/Services/entrega.service';

@Component({
  selector: 'app-criar-entrega',
  templateUrl: './criar-entrega.component.html',
  styleUrls: ['./criar-entrega.component.css']
})
export class CriarEntregaComponent implements OnInit {

  @Input() entrega: Entrega = {data: new Date(),
  massa: 0,
  armazemDesignacao:"",
  tempoColocar: 0, tempoRetirar: 0 }



  private minMassa: number = 0; private maxMassa: number = 100000;
  private minTempo: number = 0; private maxTempo: number = 1440;

  public armazens: Armazem[] = [];

  constructor(private entregaService : EntregaService,
    private armazemService : ArmazemService, private titleService:Title) {
      this.titleService.setTitle("Criar Entrega"); }

  ngOnInit(): void {
   this.getAllArmazens().subscribe(res => {
      this.armazens = res;
    });
  }

  addEntrega (): void {
    if (this.validarInfo()) {
      const req = this.entregaService.addEntrega(this.entrega);
        req.subscribe(() => {
          console.log(this.entrega);
          alert("Entrega Criada");
        });
    }
  }

  getAllArmazens (): Observable<Armazem[]> {
    return this.armazemService.getArmazensAtivos();
  }


  validarInfo ():boolean {
    var data = "";
    if (this.entrega.data == null) {
      data = data +  "Data Inválida - é necessário selecionar uma data.\n";
    }
    var massa = "";
    if (this.entrega.massa < this.minMassa || this.entrega.massa > this.maxMassa) {
      massa = massa + `Massa Inválida - este campo tem de ser menor que ${this.minMassa} e ${this.maxMassa}.\n`;
    }
    var armazem = "";
    if (this.entrega.armazemDesignacao.length == 0) {
      armazem = armazem + "Armazém Inválido - este campo é obrigatório.\n";
    }
    var tempoColocar = "";
    if (this.entrega.tempoColocar < this.minTempo || this.entrega.tempoColocar > this.maxTempo){
      tempoColocar = tempoColocar + `Tempo de colocar inválido - este campo tem de ser menor que ${this.minTempo} e ${this.maxTempo}.\n`;
    }

    var tempoRetirar = "";
    if (this.entrega.tempoRetirar < this.minTempo || this.entrega.tempoRetirar > this.maxTempo){
      tempoRetirar = tempoRetirar + `Tempo de retirar inválido - este campo tem de ser menor que ${this.minTempo} e ${this.maxTempo}.\n`;
    }

    var a = "";
    if (data.length != 0 || massa.length != 0 || armazem.length != 0 || tempoColocar.length != 0 || tempoRetirar.length != 0){
      a = a + data + massa + armazem + tempoColocar + tempoRetirar;
      alert(a);
      return false
    } else return true;
  }


}

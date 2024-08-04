import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Armazem } from 'src/app/Model/armazem';
import {Title} from "@angular/platform-browser";
import { Percurso } from 'src/app/Model/percurso';
import { ArmazemService } from 'src/app/Services/armazem.service';
import { PercursoService } from 'src/app/Services/percurso.service';

@Component({
  selector: 'app-criar-percurso',
  templateUrl: './criar-percurso.component.html',
  styleUrls: ['./criar-percurso.component.css']
})

export class CriarPercursoComponent implements OnInit {
  
  @Input() percurso: Percurso = {armazemPartida: "",
    armazemChegada: "",
    distancia: "",
    tempo: "",
    energiaGasta: "",
    tempoExtra: ""}


    
  private minDistancia: number = 0; private maxDistancia: number = 100000;
  private minTempo: number = 0; private maxTempo: number = 1440;
  private minEnergiaGasta: number = 0; private maxEnergiaGasta: number = 100000;
  armazensData:Armazem[] = []; 
  public armazensPartida: string[] = [];
  public armazensChegada: string[] = [];

  constructor(private percursoService : PercursoService, private armazemService : ArmazemService, private titleService:Title) { 
    this.titleService.setTitle("Criar Percurso");
    const req = this.getArmazens();
    req.subscribe(x=>{ 
      this.armazensData = x;
       console.log(x);
    } )
  }
  ngOnInit(): void{}
  getArmazens (): Observable<Armazem[]> {
    return this.armazemService.getArmazensAtivos();
  } 

  addPercurso (): void {
    if (this.validarInfo()) {
      const req = this.percursoService.addPercurso(this.percurso);
        req.subscribe(() => {
          console.log(this.percurso);
          alert("Percurso Criado");
        });
    }
  }

  validarInfo ():boolean {
    
    var armazemPartida = "";
    if (this.percurso.armazemPartida == null) {
      armazemPartida = armazemPartida +  "Armazem de Partida inválido - é necessário introduzir um id de armazém válido.\n";
    }
    var armazemChegada = "";
    if (this.percurso.armazemChegada == null) {
      armazemChegada = armazemChegada +  "Armazem de Chegada inválido - é necessário introduzir um id de armazém válido.\n";
    }

    var distancia = "";
    if (Number(this.percurso.distancia) < this.minDistancia || Number(this.percurso.distancia) > this.maxDistancia) {
      distancia = distancia + `Distância Inválida - este campo tem de ser menor que ${this.minDistancia} e ${this.maxDistancia}.\n`;
    }
    var tempo = "";
    if (Number(this.percurso.tempo) < this.minTempo || Number(this.percurso.tempo) > this.maxTempo){
      tempo = tempo + `Tempo de percurso inválido - este campo tem de ser menor que ${this.minTempo} e ${this.maxTempo}.\n`;
    }
    var energiaGasta = "";
    if (Number(this.percurso.energiaGasta) < this.minEnergiaGasta || Number(this.percurso.energiaGasta) > this.maxEnergiaGasta){
      energiaGasta = energiaGasta + `Energia Gasta inválido - este campo tem de ser menor que ${this.minEnergiaGasta} e ${this.maxEnergiaGasta}.\n`;
    }
    var tempoExtra = "";
    if (Number(this.percurso.tempoExtra) < this.minTempo || Number(this.percurso.tempoExtra) > this.maxTempo){
      tempoExtra = tempoExtra + `Tempo extra inválido - este campo tem de ser menor que ${this.minTempo} e ${this.maxTempo}.\n`;
    }

    var a = "";
    if (armazemPartida.length != 0 || armazemChegada.length != 0 || tempo.length != 0 || energiaGasta.length != 0 || distancia.length != 0 || tempoExtra.length != 0){
      a = a + armazemPartida + armazemChegada + distancia + tempo + energiaGasta + tempoExtra;
      alert(a);
      return false
    } else return true;
    
  }


}

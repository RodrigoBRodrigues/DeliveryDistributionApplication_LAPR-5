import { Component, Input, OnInit } from '@angular/core';
import { Camiao } from 'src/app/Model/camiao';
import { CamiaoService } from 'src/app/Services/camiao.service';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'app-criar-camiao',
  templateUrl: './criar-camiao.component.html',
  styleUrls: ['./criar-camiao.component.css'],
})
export class CriarCamiaoComponent implements OnInit {
  @Input() camiao: Camiao = {
    matricula: '',
    caracteristica: '',
    tara: 0,
    capacidade: 0,
    carga: 0,
    autonomia: 0,
    tempo: 0,
    ativo: true,
  };

  public camioes: Camiao[] = [];

  constructor(
    private camiaoService: CamiaoService,
    private titleService: Title
  ) {
    this.titleService.setTitle('Criar Camião');
  }

  ngOnInit(): void {}
  addCamiao(): void {
    if (this.validarInfo()) {
      const req = this.camiaoService.addCamiao(this.camiao);
      req.subscribe(() => {
        console.log(this.camiao);
        alert('Camião Criado!');
      });
    }
  }
  validarInfo(): boolean {
    var matricula = '';
    let regExp2021 = new RegExp('[A-Z]{2}[0-9]{2}[A-Z]{2}');
    let regExp2005 = new RegExp('[0-9]{2}-[A-Z]{2}-[0-9]{2}');
    let regExp1992 = new RegExp('[0-9]{2}-[0-9]{2}-[A-Z]{2}');
    let regExp1992Antes = new RegExp('[A-Z]{2}-[0-9]{2}-[0-9]{2}');

    if (
      regExp2021.test(this.camiao.matricula) == false &&
      regExp2005.test(this.camiao.matricula) == false &&
      regExp1992.test(this.camiao.matricula) == false &&
      regExp1992Antes.test(this.camiao.matricula) === false
    ) {
      matricula = matricula + 'Formato da matrícula inválido.\n';
    }
    var caracteristica = '';
    if (this.camiao.caracteristica.length == 0) {
      caracteristica =
        caracteristica +
        'Caracteristica Inválida - este campo é obrigatório.\n';
    }
    console.log('la tara ' + this.camiao.tara);
    var tara = '';
    if (this.camiao.tara <= 0) {
      tara = tara + 'Tara Inválida - deve ter um valor superior a 0.\n';
    }
    var capacidade = '';
    if (this.camiao.capacidade <= 0) {
      capacidade =
        capacidade + 'Capacidade Inválida - deve ter um valor superior a 0.\n';
    }
    var carga = '';
    if (this.camiao.carga <= 0) {
      carga = carga + 'Carga Inválida - deve ter um valor superior a 0.\n';
    }
    var autonomia = '';
    if (this.camiao.autonomia <= 0) {
      autonomia =
        autonomia + 'Autonomia Inválida - deve ter um valor superior a 0.\n';
    }
    var tempo = '';
    if (this.camiao.tempo <= 0) {
      tempo = tempo + 'Tempo Inválida - deve ter um valor superior a 0.\n';
    }
    var a = '';
    if (
      matricula.length != 0 ||
      caracteristica.length != 0 ||
      tara.length != 0 ||
      capacidade.length != 0 ||
      carga.length != 0 ||
      autonomia.length != 0 ||
      tempo.length != 0
    ) {
      a =
        a +
        matricula +
        caracteristica +
        tara +
        capacidade +
        carga +
        autonomia +
        tempo;
      alert(a);
      return false;
    } else return true;
  }
}

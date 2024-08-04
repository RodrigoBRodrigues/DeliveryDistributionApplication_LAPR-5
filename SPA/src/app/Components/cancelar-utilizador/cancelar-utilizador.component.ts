import { Component, OnInit, Input } from '@angular/core';
import { Utilizador } from 'src/app/Model/utilizador';
import { AuthService } from 'src/app/Services/auth.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-cancelar-utilizador',
  templateUrl: './cancelar-utilizador.component.html',
  styleUrls: ['./cancelar-utilizador.component.css'],
})
export class CancelarUtilizadorComponent implements OnInit {
  @Input() Funcao: string = '';
  checkbox: boolean = false;
  @Input() utilizador: Utilizador = {
    nome: '',
    utilizador: '',
    role: '',
    telemovel: '',
    email: '',
    ativo: true,
  };
  utilizadoresData : Utilizador[] = [];

  constructor(private service: AuthService, private titleService: Title) {
    this.titleService.setTitle('Cancelar Utilizador');
  }

  ngOnInit(): void {
    const req = this.service.getUtilizadores();
    req.subscribe((x) => {
      this.utilizadoresData = x;
      console.log(this.utilizadoresData)
      //console.log(x);
    });
    this.checkbox= false;}

  check(): void{
    if (this.checkbox == false) {
      this.checkbox = true;
      console.log(this.checkbox);
    } else {
      this.checkbox = false;
      console.log(this.checkbox);
    }
  }

  cancelarUtilizador(utilizador: Utilizador): void {
    console.log(utilizador);
    const req = this.service.cancelarUtilizador(utilizador);
    
  }


  validarInfo ():boolean {
    var utilizador = "";
    if (this.utilizador.utilizador == null) {
      utilizador = utilizador +  "Utilizador inválido.\n";
    }
    var a = "";
    if (utilizador.length != 0){
      a = a + utilizador;
      alert(a);
      return false
    } else return true;
  }
}

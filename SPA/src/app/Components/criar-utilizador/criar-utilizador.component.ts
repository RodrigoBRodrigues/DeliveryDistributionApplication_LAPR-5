import { Component, OnInit, Input } from '@angular/core';
import { Utilizador } from 'src/app/Model/utilizador';
import { AuthService } from 'src/app/Services/auth.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-criar-utilizador',
  templateUrl: './criar-utilizador.component.html',
  styleUrls: ['./criar-utilizador.component.css'],
})
export class CriarUtilizadorComponent implements OnInit {
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

  constructor(private service: AuthService, private titleService: Title) {
    this.titleService.setTitle('Criar Utilizador');
  }

  ngOnInit(): void {this.checkbox= false;}

  check(): void{
    if (this.checkbox == false) {
      this.checkbox = true;
      console.log(this.checkbox);
    } else {
      this.checkbox = false;
      console.log(this.checkbox);
    }
  }

  criarUtilizador(): void {
    console.log(this.utilizador);
    const req = this.service.criarUtilizador(this.utilizador);
    if (this.validarInfo() && this.checkbox == true) {
      req.subscribe(()=> {
        console.log(this.utilizador);
        alert("Utilizador Criado!");
      });;
    }
  }


  validarInfo ():boolean {
    var telemovel = "";
    if (this.utilizador.telemovel.length != 9) {
      telemovel = telemovel +  "Telemóvel Inválido - deve ser constituído por 9 caracteres.\n";
    }
    var nome = "";
    if (this.utilizador.nome.length == 0) {
      nome = nome + "Nome Inválido - este campo é obrigatório.\n";
    }
    var utilizador = "";
    if (this.utilizador.utilizador.length == 0) {
      utilizador = utilizador + "Utilizador Inválido - este campo é obrigatório.\n";
    }
    var email = "";
    if (this.utilizador.email.length == 0 || !this.utilizador.email.includes('@')|| !this.utilizador.email.includes('.')){
      email = email + "E-mail Inválido - deve ser do tipo x@x.x";
    }
    var a = "";
    if (telemovel.length != 0 || nome.length != 0 || utilizador.length != 0 || email.length != 0){
      a = a + nome + utilizador + telemovel + email;
      alert(a);
      return false
    } else return true;
  }
}

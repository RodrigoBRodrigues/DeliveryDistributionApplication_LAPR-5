import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { Utilizador } from 'src/app/Model/utilizador';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-anonimizar-conta',
  templateUrl: './anonimizar-conta.component.html',
  styleUrls: ['./anonimizar-conta.component.css'],
})
export class AnonimizarContaComponent implements OnInit {
  displayedColumns: string[] = [
    'utilizador',
    'nome',
    'role',
    'telemovel',
    'email',
    'ativo',
  ];
  utilizadoresData: Utilizador[] = [];

  constructor(private authservice: AuthService, private titleService: Title) {
    this.titleService.setTitle('Listar Utilizadores');

  }

  ngOnInit(): void {
    const req = this.getUtilizadores();
    req.subscribe((x) => {
      this.utilizadoresData = x;
      console.log(this.utilizadoresData)
      //console.log(x);
    });
  }

  getUtilizadores(): Observable<Utilizador[]> {
    return this.authservice.getUtilizadores();
  }

  inibirUtilizador (utilizador: Utilizador): void {
    console.log(utilizador.ativo);
    if (utilizador.ativo == true) {
      utilizador.ativo = false;
    }
    const req = this.authservice.anonimizarConta(utilizador.utilizador, utilizador.ativo);
    req.subscribe();
    this.ngOnInit();
  }
}

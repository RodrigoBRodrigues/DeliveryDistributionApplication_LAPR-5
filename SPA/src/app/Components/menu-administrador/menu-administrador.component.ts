import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
@Component({
  selector: 'app-menu-administrador',
  templateUrl: './menu-administrador.component.html',
  styleUrls: ['./menu-administrador.component.css'],
})
export class MenuAdministradorComponent implements OnInit {
  constructor(private router: Router, private titleService: Title) {
    this.titleService.setTitle('Menu Administrador');
  }

  ngOnInit(): void {}

  criarConta(): void {
    this.router.navigate(['criar-utilizador']);
  }
  
  anonimizarConta(): void {
    this.router.navigate(['anonimizar-utilizador']);
  }
}

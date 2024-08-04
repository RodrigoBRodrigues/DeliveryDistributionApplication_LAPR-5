import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'app-menu-gestor-logistica',
  templateUrl: './menu-gestor-logistica.component.html',
  styleUrls: ['./menu-gestor-logistica.component.css']
})
export class MenuGestorLogisticaComponent implements OnInit {

  constructor(private router: Router,private titleService:Title) {
    this.titleService.setTitle("Menu Gestor Logística"); }

  ngOnInit(): void {
  }

  criarPercurso():void {
    this.router.navigate(['criar-percurso']);
  }
  listarPercursos():void {
    this.router.navigate(['listar-percurso']);
  }
  listarViagens():void {
    this.router.navigate(['listar-viagens']);
  }
  planeamentoRota():void {
    this.router.navigate(['planeamento-rota']);
  }
  visualizacaoRede():void {
    this.router.navigate(['visualizacao-rede']);
  }
  moverCamiao():void{
    this.router.navigate(['mover-camiao']);
  }

}

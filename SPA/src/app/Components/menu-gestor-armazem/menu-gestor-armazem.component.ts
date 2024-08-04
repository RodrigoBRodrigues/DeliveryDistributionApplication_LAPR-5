import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Title} from "@angular/platform-browser";
@Component({
  selector: 'app-menu-gestor-armazem',
  templateUrl: './menu-gestor-armazem.component.html',
  styleUrls: ['./menu-gestor-armazem.component.css']
})
export class MenuGestorArmazemComponent implements OnInit {

  constructor(private router: Router,private titleService:Title) { 
    this.titleService.setTitle("Menu Gestor Armazém"); }

  ngOnInit(): void {
  }

  criarArmazem():void {
    this.router.navigate(['criar-armazem']);
  }
  listarArmazens():void {
    this.router.navigate(['listar-armazens']);
  }
  criarEntrega():void {
    this.router.navigate(['criar-entrega']);
  }
  listarEntregas():void {
    this.router.navigate(['listar-entregas']);
  }



}

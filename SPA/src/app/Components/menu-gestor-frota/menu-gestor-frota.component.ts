import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Title} from "@angular/platform-browser";
@Component({
  selector: 'app-menu-gestor-frota',
  templateUrl: './menu-gestor-frota.component.html',
  styleUrls: ['./menu-gestor-frota.component.css']
})
export class MenuGestorFrotaComponent implements OnInit {

  constructor(private router: Router,private titleService:Title) { 
    this.titleService.setTitle("Menu Gestor Frota"); }

  ngOnInit(): void {
  }

  criarCamiao():void {
    this.router.navigate(['criar-camiao']);
  }
  listarCamiao():void {
    this.router.navigate(['listar-camioes']);
  }
  

}

import { Component, Input, OnInit } from '@angular/core';
import {Title} from "@angular/platform-browser";
import { Armazem } from 'src/app/Model/armazem';
import { ArmazemService } from 'src/app/Services/armazem.service';


@Component({
  selector: 'app-criar-armazem',
  templateUrl: './criar-armazem.component.html',
  styleUrls: ['./criar-armazem.component.css']
})
export class CriarArmazemComponent implements OnInit {


  @Input() armazem: Armazem = {id:"",
                              designacao:"",
                              rua:"", codigoPostal:"",
                              latitude: 0, longitude: 0, altitude: 0, ativo: true }

  constructor(private armazensv : ArmazemService, private titleService:Title) { 
    this.titleService.setTitle("Criar Armazém");
  }

  ngOnInit(): void {  }

  addArmazem (): void {

  const req = this.armazensv.addArmazem(this.armazem);
  if (this.validarInfo()) {
    req.subscribe(()=> {
      console.log(this.armazem);
      alert("Armazém Criado!");
    });;
  }
  }

  validarInfo ():boolean {
    var id = "";
    if (this.armazem.id.length != 3) {
      id = id +  "Id Inválido - deve ser constituído por 3 caracteres.\n";
    }
    var designacao = "";
    if (this.armazem.designacao.length == 0) {
      designacao = designacao + "Designação Inválida - este campo é obrigatório.\n";
    }
    var rua = "";
    if (this.armazem.rua.length == 0) {
      rua = rua + "Rua Inválida - este campo é obrigatório.\n";
    }
    var codigoPostal = "";
    if (this.armazem.codigoPostal.length != 8 || this.armazem.codigoPostal.charAt(4)!= '-'){
      codigoPostal = codigoPostal + "Código-Postal Inválido - deve ser do tipo 0000-000.\n";
    }
    var latitude = "";
    if (this.armazem.latitude < 0 || this.armazem.latitude > 90) {
      latitude = latitude + "Latitude Inválida - deve pertencer ao intervalo [0-90].\n";
    }
    var longitude = "";
    if (this.armazem.longitude < 0 || this.armazem.longitude > 90) {
      longitude = longitude + "Longitude Inválida - deve pertencer ao intervalo [0-180].\n";
    }
    var altitude = "";
    if (this.armazem.altitude < 0) {
      altitude = altitude + "Altitude Inválida - deve ser um valor positivo.\n";
    }
    var a = "";
    if (id.length != 0 || designacao.length != 0 || rua.length != 0 || codigoPostal.length != 0){
      a = a + id + designacao + rua + codigoPostal + latitude + longitude + altitude;
      alert(a);
      return false
    } else return true;
  }

}



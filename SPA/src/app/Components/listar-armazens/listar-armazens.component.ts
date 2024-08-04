import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';
import { Armazem } from 'src/app/Model/armazem';
import { ArmazemService } from 'src/app/Services/armazem.service';
import { ArmazemDesignacaoPipe } from 'src/app/Pipes/armazemDesignacaoFilter';
import {Title} from "@angular/platform-browser";
import { forEach } from 'cypress/types/lodash';

@Component({
  selector: 'app-listar-armazens',
  templateUrl: './listar-armazens.component.html',
  styleUrls: ['./listar-armazens.component.css']
})

export class ListarArmazensComponent implements OnInit {
  displayedColumns: string[] = ['id','designacao','codigoPostal','rua','latitude','longitude','altitude','ativo'];
  armazensData:Armazem[] = []; 
  armazemFilter1="";
  armazemFilter2="";

  constructor(private armazemService : ArmazemService, private titleService:Title) { 
    this.titleService.setTitle("Listar Armazéns");
    const req = this.getArmazens();
    req.subscribe(x=>{ 
      this.armazensData = x;
       //console.log(x);
    } )
  }
  
  ngOnInit(): void {
    //this.armazemService.getArmazens();
  }

  getArmazens (): Observable<Armazem[]> {
    return this.armazemService.getArmazens();
  }

 
  inibirArmazem (armazem: Armazem): void {
    //console.log(armazem.ativo);
    if (armazem.ativo == true) {
      armazem.ativo = false;
    } else {
      armazem.ativo = true;
    }
    const req = this.armazemService.patchAtivo(armazem.id, armazem.ativo);
    req.subscribe();
  }

}

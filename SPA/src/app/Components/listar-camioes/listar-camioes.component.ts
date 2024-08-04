import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Camiao } from 'src/app/Model/camiao';
import { CamiaoService } from 'src/app/Services/camiao.service';
import {Title} from "@angular/platform-browser";
@Component({
  selector: 'app-listar-camioes',
  templateUrl: './listar-camioes.component.html',
  styleUrls: ['./listar-camioes.component.css']
})
export class ListarCamioesComponent {
  displayedColumns: string[] = ['matricula','caracteristica','tara','capacidade','carga','autonomia','tempo','ativo'];
  camioesData:Camiao[] = [];
  camiaoFilter1='';
  camiaoFilter2='';
  constructor(private camiaoService : CamiaoService,private titleService:Title) {
    this.titleService.setTitle("Listar Camiões"); }

  ngOnInit(): void {
    const req = this.getCamioes();
    req.subscribe(x=>{
      this.camioesData = x;
       //console.log(x);
    } )
  }

  getCamioes (): Observable<Camiao[]> {
    return this.camiaoService.getCamioes();
  }

  inibirCamiao (camiao: Camiao): void {
    console.log(camiao.ativo);
    if (camiao.ativo == true) {
      camiao.ativo = false;
    } else {
      camiao.ativo = true;
    }
    console.log("o ativo é "+ camiao.ativo);
    const req = this.camiaoService.patchCamiao(camiao.matricula, camiao);
    
    req.subscribe();
  }
}

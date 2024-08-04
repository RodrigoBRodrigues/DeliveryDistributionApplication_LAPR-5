import { AfterViewInit, Component,  ViewChild } from '@angular/core';
import { Percurso } from 'src/app/Model/percurso';
import { PercursoService } from 'src/app/Services/percurso.service';
import {Title} from "@angular/platform-browser";
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-listar-percursos',
  templateUrl: './listar-percursos.component.html',
  styleUrls: ['./listar-percursos.component.css']
})

export class ListarPercursosComponent implements AfterViewInit {
  displayedColumns: string[] = ['armazemPartida','armazemChegada','distancia','tempo','energiaGasta','tempoExtra'];

  dataSource: MatTableDataSource<Percurso>;
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  


  constructor(private percursoService : PercursoService, private titleService:Title) {}

  

  ngOnInit() {
    this.titleService.setTitle("Listar Percursos");
    this.percursoService.getPercursos().subscribe(percursos => {
       this.dataSource = new MatTableDataSource(percursos);
       this.dataSource.paginator = this.paginator;
       this.dataSource.filterPredicate = function (record,filter) {
        return record.armazemPartida.toLocaleLowerCase() == filter.toLocaleLowerCase() || record.armazemChegada.toLocaleLowerCase() == filter.toLocaleLowerCase() ;
      }
  });
  }
  
    ngAfterViewInit(): void {
    }

    applyFilter(event: Event) {
      const filterValue = (event.target as HTMLInputElement).value;
      this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}

import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { Entrega } from 'src/app/Model/entrega';
import { ViagemService } from 'src/app/Services/viagem-service.service';
import { Title } from '@angular/platform-browser';
import { Viagem } from 'src/app/Model/viagem';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
@Component({
  selector: 'app-listar-entregas',
  templateUrl: './listar-viagens.component.html',
  styleUrls: ['./listar-viagens.component.css'],
})
export class ListarViagensComponent implements OnInit {
  displayedColumns: string[] = ['dia', 'camiao', 'curso'];
  viagensData: Viagem[] = [];
  entregaFilter1 = '';
  entregaFilter2 = '';
  entregaFilter3 = '';
  datasource = new MatTableDataSource<Viagem>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private viagemService: ViagemService,
    private titleService: Title
  ) {
    this.titleService.setTitle('Listar Entregas');
  }

  ngOnInit(): void {
    const req = this.getViagens();
    req.subscribe((x) => {
      this.viagensData = x;
      this.datasource = new MatTableDataSource(this.viagensData);
      this.datasource.paginator = this.paginator;
      this.datasource.sort = this.sort;
      //console.log(x);
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue;
    console.log(event.target);

  }

  getViagens(): Observable<Viagem[]> {
    return this.viagemService.getViagens();
  }
}

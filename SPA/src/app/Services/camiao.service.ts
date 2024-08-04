import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Camiao } from '../Model/camiao';

@Injectable({
  providedIn: 'root',
})
export class CamiaoService {
  constructor(private http: HttpClient) {}

  private backUrl = environment.nodeUrl + '/api/camioes';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
    }), // constructs a new HTTP header object which accepts JSON data
  };

  addCamiao(camiao: Camiao): Observable<Camiao> {
    //create new Camiao
    console.log(
      'O camiao tem car = ' +
        camiao.caracteristica +
        'e matricula ' +
        camiao.matricula +
        'e tara ' +
        camiao.tara +
        ' e capacidade ' +
        camiao.capacidade +
        ' e carga ' +
        camiao.carga +
        ' e autonomia' +
        camiao.autonomia +
        ' e tempo ' +
        camiao.tempo
    );

    return this.http.post<Camiao>(this.backUrl, camiao, this.httpOptions);
  }

  getCamioes(): Observable<Camiao[]> {
    return this.http.get<Camiao[]>(this.backUrl, this.httpOptions);
  }

  updateCamiao(matricula: string, camiao: Camiao): Observable<Camiao> {
    const newUrl = this.backUrl + '/matriculas/' + matricula;
    console.log(newUrl);
    console.log(camiao);
    const Req = {
      caracteristica: camiao.caracteristica,
      tara: camiao.tara,
      capacidade: camiao.capacidade,
      carga: camiao.carga,
      autonomia: camiao.autonomia,
      tempo: camiao.tempo,
      ativo: camiao.ativo,
    };
    return this.http.put<Camiao>(newUrl, Req, this.httpOptions);
  }
  patchCamiao(matricula: string, camiao: Camiao): Observable<Camiao> {
    const newUrl = this.backUrl + '/matriculas/' + matricula;
    console.log(newUrl);
    console.log(camiao);
    const Req = {
      ativo: camiao.ativo,
    };
    return this.http.patch<Camiao>(newUrl, Req, this.httpOptions);
  }
}

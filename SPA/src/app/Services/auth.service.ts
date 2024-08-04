import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { Utilizador } from '../Model/utilizador';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public utilizador!: Utilizador;
  private path = environment.dotNetUrl;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
    }), // constructs a new HTTP header object which accepts JSON data
  };
  constructor(private httpClient: HttpClient) {}

  public signOutExternal = () => {
    localStorage.removeItem('token');
    console.log('token deleted');
  };

  LoginWithGoogle(credentials: string): Observable<any> {
    console.log(credentials);
    const header = new HttpHeaders().set('Content-type', 'application/json');
    return this.httpClient.post(
      this.path + '/api/auth/LoginWithGoogle',
      JSON.stringify(credentials),
      { headers: header }
    );
  }

  criarUtilizador(utilizador: Utilizador): Observable<Utilizador> {
    return this.httpClient.post<Utilizador>(
      this.path + '/api/auth',
      utilizador,
      this.httpOptions
    );
  }
  public token!: string;
  public buscarUtilizador(token: string): Utilizador {
    console.log(atob(token.split('.')[1]));
    console.log(JSON.parse(atob(token.split('.')[1])));
    this.token = token;
    this.utilizador = JSON.parse(atob(token.split('.')[1])) as Utilizador;
    return this.utilizador;
  }


  cancelarUtilizador(utilizador: Utilizador): Observable<Utilizador> {
    const cancelURL = this.path + '/api/auth/';
    const json = '{"ativo":' + false +'}"';
    return this.httpClient.put<Utilizador>(cancelURL, json, this.httpOptions);
  }

  getUtilizadores(): Observable<Utilizador[]> {
    return this.httpClient.get<Utilizador[]>(
      this.path + '/api/auth/',
      this.httpOptions
    );
  }

  anonimizarConta(id: string, ativo: boolean): Observable<Utilizador> {
    const newUrl = this.path + '/api/auth/' + id;
    console.log(newUrl);
    //console.log(newUrl);
    const json = '{"ativo":' + ativo + '}"';
    return this.httpClient.patch<Utilizador>(newUrl, json, this.httpOptions);
  }
}

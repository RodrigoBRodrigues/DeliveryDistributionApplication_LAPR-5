import { Component, NgZone, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { CredentialResponse, PromptMomentNotification } from 'google-one-tap';
import { environment } from 'src/environments/environment';
import { AuthService } from '../Services/auth.service';
import { Role } from '../../enums/role';
import { Utilizador } from '../Model/utilizador';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username = '';
  password = '';

  private clientId = environment.clientId;
  utilizador!: Utilizador;

  constructor(
    private router: Router,
    private service: AuthService,
    private _ngZone: NgZone,
    private titleService: Title
  ) {
    this.titleService.setTitle('Login');
  }

  ngOnInit(): void {
    // @ts-ignore
    window.onGoogleLibraryLoad = () => {
      // @ts-ignore
      google.accounts.id.initialize({
        client_id: this.clientId,
        callback: this.handleCredentialResponse.bind(this),
        auto_select: false,
        cancel_on_tap_outside: true,
      });
      // @ts-ignore
      google.accounts.id.renderButton(
        // @ts-ignore
        document.getElementById('buttonDiv'),
        { theme: 'outline', size: 'large', width: '100%' }
      );
      // @ts-ignore
      google.accounts.id.prompt((notification: PromptMomentNotification) => {});
    };
  }

  async handleCredentialResponse(response: CredentialResponse) {
    await this.service.LoginWithGoogle(response.credential).subscribe(
      (x: any) => {
        localStorage.setItem('token', x.token);
        this._ngZone.run(() => {
          this.utilizador = this.service.buscarUtilizador(x.token);
          if (this.utilizador.role.startsWith('Armazem')) {
            this.router.navigate(['/menu-gestor-armazem']);
          } else if (this.utilizador.role.startsWith('Logistica')) {
            this.router.navigate(['/menu-gestor-logistica']);
          } else if (this.utilizador.role.startsWith('Frota')) {
            this.router.navigate(['/menu-gestor-frota']);
          } else if (this.utilizador.role.startsWith('Logistica')) {
            this.router.navigate(['/login']);
          } else if (this.utilizador.role.startsWith('Administrador')) {
            this.router.navigate(['/menu-administrador']);
          }
        });
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  login(username: string, password: string): void {
    console.log(username);
    console.log(password);
    if (username == 'armazem') {
      this.router.navigate(['menu-gestor-armazem']);
    }
    if (username == 'administrador') {
      this.router.navigate(['menu-administrador']);
    }
    if (username == 'frota') {
      this.router.navigate(['menu-gestor-frota']);
    }
    if (username == 'logistica') {
      this.router.navigate(['menu-gestor-logistica']);
    }
  }
}

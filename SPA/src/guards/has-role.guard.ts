import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AppModule } from 'src/app/app.module';
import { LoginComponent } from 'src/app/login/login.component';
import { AuthService } from 'src/app/Services/auth.service';

let token!:string;

@Injectable({
  providedIn: 'root',
})
export class HasRoleGuard implements CanActivate {
  constructor(private login: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    let authorized;

    if (this.login.utilizador == null) {
      this.router.navigate(['/login']);
    }
    authorized = this.login.utilizador.role.includes(route.data.funcao);
    if (!authorized) {
      window.alert('Não tens permissão para aceder a este recurso.');
    }

    return authorized;
  }
}

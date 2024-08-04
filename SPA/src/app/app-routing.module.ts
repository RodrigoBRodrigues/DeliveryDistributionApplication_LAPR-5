import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { CriarEntregaComponent } from './Components/criar-entrega/criar-entrega.component';
import { CriarArmazemComponent } from './Components/criar-armazem/criar-armazem.component';
import { ListarArmazensComponent } from './Components/listar-armazens/listar-armazens.component';
import { VisualizacaoRedeComponent } from './Components/visualizacao-rede/visualizacao-rede.component';
import { ListarEntregasComponent } from './Components/listar-entregas/listar-entregas.component';
import { CriarCamiaoComponent } from './Components/criar-camiao/criar-camiao.component';
import { MenuGestorArmazemComponent } from './Components/menu-gestor-armazem/menu-gestor-armazem.component';
import { CriarPercursoComponent } from './Components/criar-percurso/criar-percurso.component';
import { ListarPercursosComponent } from './Components/listar-percursos/listar-percursos.component';
import { ListarCamioesComponent } from './Components/listar-camioes/listar-camioes.component';
import { PlaneamentoRotaComponent } from './Components/planeamento-rota/planeamento-rota.component';
import { MenuGestorFrotaComponent } from './Components/menu-gestor-frota/menu-gestor-frota.component';
import { MenuGestorLogisticaComponent } from './Components/menu-gestor-logistica/menu-gestor-logistica.component';
import { MenuAdministradorComponent } from './Components/menu-administrador/menu-administrador.component';
import { CriarUtilizadorComponent } from './Components/criar-utilizador/criar-utilizador.component';
import { Plane } from 'three';
import { MoverCamiaoComponent } from './Components/mover-camiao/mover-camiao.component';
import { LogoutComponent } from './logout/logout.component';
import { ListarViagensComponent } from './Components/listar-viagens/listar-viagens.component';
import { CancelarUtilizadorComponent } from './Components/cancelar-utilizador/cancelar-utilizador.component';
import { AnonimizarContaComponent } from './Components/anonimizar-conta/anonimizar-conta.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'criar-armazem', component: CriarArmazemComponent},
  { path: 'criar-entrega', component: CriarEntregaComponent },
  { path: 'listar-armazens', component: ListarArmazensComponent},
  { path: 'listar-entregas', component: ListarEntregasComponent},
  { path: 'listar-viagens', component: ListarViagensComponent},
  { path: 'visualizacao-rede', component: VisualizacaoRedeComponent },
  { path: 'mover-camiao', component: MoverCamiaoComponent },
  { path: 'criar-camiao', component: CriarCamiaoComponent },
  { path: 'criar-percurso', component: CriarPercursoComponent },
  { path: 'listar-percurso', component: ListarPercursosComponent},
  { path: 'listar-camioes', component:ListarCamioesComponent},
  { path: 'menu-gestor-armazem', component:MenuGestorArmazemComponent },
  { path: 'planeamento-rota',component:PlaneamentoRotaComponent},
  { path: 'menu-gestor-frota',component:MenuGestorFrotaComponent},
  { path: 'menu-gestor-logistica',component:MenuGestorLogisticaComponent},
  { path: 'menu-administrador',component:MenuAdministradorComponent},
  { path: 'criar-utilizador',component: CriarUtilizadorComponent},
  { path: 'cancelar-utilizador',component: CancelarUtilizadorComponent},
  { path: 'anonimizar-utilizador',component: AnonimizarContaComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
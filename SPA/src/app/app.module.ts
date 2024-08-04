import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { CriarEntregaComponent } from './Components/criar-entrega/criar-entrega.component';
import { CriarArmazemComponent } from './Components/criar-armazem/criar-armazem.component';
import { VisualizacaoRedeComponent } from './Components/visualizacao-rede/visualizacao-rede.component';
import { ListarArmazensComponent } from './Components/listar-armazens/listar-armazens.component';
import { MatTableModule } from '@angular/material/table';
import { ArmazemDesignacaoPipe } from './Pipes/armazemDesignacaoFilter';
import { ArmazemIdPipe } from './Pipes/armazemIdFilter';
import { ListarEntregasComponent } from './Components/listar-entregas/listar-entregas.component';
import { EntregaArmazemChegadaFilter } from './Pipes/entregaArmazemChegadaFilter';
import { EntregaEntreDatasFilter } from './Pipes/entregaEntreDatasFilter';
import { CriarCamiaoComponent } from './Components/criar-camiao/criar-camiao.component';
import { MenuGestorArmazemComponent } from './Components/menu-gestor-armazem/menu-gestor-armazem.component';
import { CriarPercursoComponent } from './Components/criar-percurso/criar-percurso.component';
import { ListarPercursosComponent } from './Components/listar-percursos/listar-percursos.component';
import { PercursoArmazemChegadaFilter } from './Pipes/percursoArmazemChegadaFilter';
import { PercursoArmazemPartidaFilter } from './Pipes/percursoArmazemPartidaFilter';
import { ListarCamioesComponent } from './Components/listar-camioes/listar-camioes.component';
import { PlaneamentoRotaComponent } from './Components/planeamento-rota/planeamento-rota.component';
import { CamiaoMatriculaPipe } from './Pipes/camiaoMatriculaFilter';
import { MenuGestorFrotaComponent } from './Components/menu-gestor-frota/menu-gestor-frota.component';
import { MenuGestorLogisticaComponent } from './Components/menu-gestor-logistica/menu-gestor-logistica.component';
import { MoverCamiaoComponent } from './Components/mover-camiao/mover-camiao.component';
import { LogoutComponent } from './logout/logout.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CriarUtilizadorComponent } from './Components/criar-utilizador/criar-utilizador.component';
import { HasRoleGuard } from 'src/guards/has-role.guard';
import { AuthService } from './Services/auth.service';
import { MenuAdministradorComponent } from './Components/menu-administrador/menu-administrador.component';
import { ListarViagensComponent } from './Components/listar-viagens/listar-viagens.component'; 
import { CancelarUtilizadorComponent } from './Components/cancelar-utilizador/cancelar-utilizador.component';
import { AnonimizarContaComponent } from './Components/anonimizar-conta/anonimizar-conta.component';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatToolbarModule,
    MatPaginatorModule,
    FormsModule,
    MatTableModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,


    /*// The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    HttpClientInMemoryWebApiModule.forRoot(
      InMemoryDataService, { dataEncapsulation: false }
    ),
     BrowserAnimationsModule*/
  ],
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    CriarEntregaComponent,
    CriarCamiaoComponent,
    CriarArmazemComponent,
    VisualizacaoRedeComponent,
    CriarArmazemComponent,
    ListarArmazensComponent,
    ArmazemDesignacaoPipe,
    ArmazemIdPipe,
    CamiaoMatriculaPipe,
    EntregaArmazemChegadaFilter,
    EntregaEntreDatasFilter,
    ListarEntregasComponent,
    CriarPercursoComponent,
    ListarPercursosComponent,
    PercursoArmazemPartidaFilter,
    PercursoArmazemChegadaFilter,
    MenuGestorArmazemComponent,
    PlaneamentoRotaComponent,
    MenuGestorArmazemComponent,
    ListarCamioesComponent,
    MenuGestorFrotaComponent,
    MenuGestorLogisticaComponent,
    MoverCamiaoComponent,
    MenuGestorLogisticaComponent,
    LogoutComponent,
    CriarUtilizadorComponent,
    CancelarUtilizadorComponent,
    MenuAdministradorComponent,
    ListarViagensComponent,
    AnonimizarContaComponent
  ],
  providers: [
    MatDatepickerModule,
    HasRoleGuard,
    AuthService,
    LoginComponent,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

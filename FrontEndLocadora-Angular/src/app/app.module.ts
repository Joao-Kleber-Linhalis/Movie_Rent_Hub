import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Para trabalhar com formulários no Angular 12
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

// Para realizar requisições HTTP
import { HttpClientModule } from '@angular/common/http';

// Imports para componentes do Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { ToastrModule } from 'ngx-toastr';
import { NgxMaskModule } from 'ngx-mask';


import { NavComponent } from './componets/nav/nav.component';
import { HomeComponent } from './componets/home/home.component';
import { AtorListComponent } from './componets/ator/ator-list/ator-list.component';
import { AtorCreateComponent } from './componets/ator/ator-create/ator-create.component';
import { DiretorListComponent } from './componets/diretor/diretor-list/diretor-list.component';
import { ClasseListComponent } from './componets/classe/classe-list/classe-list.component';
import { ClasseCreateComponent } from './componets/classe/classe-create/classe-create.component';
import { DiretorCreateComponent } from './componets/diretor/diretor-create/diretor-create.component';
import { AtorUpdateComponent } from './componets/ator/ator-update/ator-update.component';
import { DiretorUpdateComponent } from './componets/diretor/diretor-update/diretor-update.component';
import { ClasseUpdateComponent } from './componets/classe/classe-update/classe-update.component';
import { AtorStatusComponent } from './componets/ator/ator-status/ator-status.component';
import { DiretorStatusComponent } from './componets/diretor/diretor-status/diretor-status.component';
import { ClasseStatusComponent } from './componets/classe/classe-status/classe-status.component';
import { TituloCreateComponent } from './componets/titulo/titulo-create/titulo-create.component';
import { TituloListComponent } from './componets/titulo/titulo-list/titulo-list.component';
import { TituloUpdateComponent } from './componets/titulo/titulo-update/titulo-update.component';
import { TituloStatusComponent } from './componets/titulo/titulo-status/titulo-status.component';
import { ItemCreateComponent } from './componets/item/item-create/item-create.component';
import { ItemUpdateComponent } from './componets/item/item-update/item-update.component';
import { ItemListComponent } from './componets/item/item-list/item-list.component';
import { ItemStatusComponent } from './componets/item/item-status/item-status.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DATE_LOCALE, MatNativeDateModule } from '@angular/material/core';
import { MatMomentDateModule } from '@angular/material-moment-adapter';
import * as moment from 'moment';
import { ClienteListComponent } from './componets/cliente/cliente-list/cliente-list.component';
import { ClienteCreateComponent } from './componets/cliente/cliente-create/cliente-create.component';
import { ClienteUpdateComponent } from './componets/cliente/cliente-update/cliente-update.component';
import { ClienteStatusComponent } from './componets/cliente/cliente-status/cliente-status.component';
import { DependenteListComponent } from './componets/dependente/dependente-list/dependente-list.component';
import { DependenteCreateComponent } from './componets/dependente/dependente-create/dependente-create.component';
import { DependenteUpdateComponent } from './componets/dependente/dependente-update/dependente-update.component';
import { DependenteStatusComponent } from './componets/dependente/dependente-status/dependente-status.component';
import { LocacaoCreateComponent } from './componets/locacao/locacao-create/locacao-create.component';
import { LocacaoUpdateComponent } from './componets/locacao/locacao-update/locacao-update.component';
import { LocacaoListComponent } from './componets/locacao/locacao-list/locacao-list.component';
import { LocacaoDeleteComponent } from './componets/locacao/locacao-delete/locacao-delete.component';
import { LocacaoDevolucaoComponent } from './componets/locacao/locacao-devolucao/locacao-devolucao.component';
import { UsuarioCreateComponent } from './componets/usuario/usuario-create/usuario-create.component';
import { UsuarioDeleteComponent } from './componets/usuario/usuario-delete/usuario-delete.component';
import { UsuarioUpdateComponent } from './componets/usuario/usuario-update/usuario-update.component';
import { UsuarioListComponent } from './componets/usuario/usuario-list/usuario-list.component';
import { LoginComponent } from './componets/login/login.component';
import { GaleriaComponent } from './componets/galeria/galeria.component';
import {MatGridListModule} from '@angular/material/grid-list';


@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    HomeComponent,
    AtorListComponent,
    AtorCreateComponent,
    DiretorListComponent,
    ClasseListComponent,
    ClasseCreateComponent,
    DiretorCreateComponent,
    AtorUpdateComponent,
    DiretorUpdateComponent,
    ClasseUpdateComponent,
    AtorStatusComponent,
    DiretorStatusComponent,
    ClasseStatusComponent,
    TituloCreateComponent,
    TituloListComponent,
    TituloUpdateComponent,
    TituloStatusComponent,
    ItemCreateComponent,
    ItemUpdateComponent,
    ItemListComponent,
    ItemStatusComponent,
    ClienteListComponent,
    ClienteCreateComponent,
    ClienteUpdateComponent,
    ClienteStatusComponent,
    DependenteListComponent,
    DependenteCreateComponent,
    DependenteUpdateComponent,
    DependenteStatusComponent,
    LocacaoCreateComponent,
    LocacaoUpdateComponent,
    LocacaoListComponent,
    LocacaoDeleteComponent,
    LocacaoDevolucaoComponent,
    UsuarioCreateComponent,
    UsuarioDeleteComponent,
    UsuarioUpdateComponent,
    UsuarioListComponent,
    LoginComponent,
    GaleriaComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    // Forms
    FormsModule,
    ReactiveFormsModule,
    // Requisições http
    HttpClientModule,
    // Angular Material
    MatFormFieldModule,
    MatPaginatorModule,
    MatCheckboxModule,
    MatSnackBarModule,
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatSelectModule,
    MatInputModule,
    MatRadioModule,
    MatTableModule,
    MatIconModule,
    MatListModule,
    MatCardModule,
    MatDatepickerModule, 
    MatMomentDateModule,
    MatGridListModule,
    ToastrModule.forRoot({
      timeOut: 4000,
      closeButton: true,
      progressBar: true,
    }),
    NgxMaskModule.forRoot()
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

moment.locale('pt-BR');
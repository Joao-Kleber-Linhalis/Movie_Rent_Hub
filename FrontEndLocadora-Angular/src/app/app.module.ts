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
    TituloStatusComponent
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
    ToastrModule.forRoot({
      timeOut: 4000,
      closeButton: true,
      progressBar: true,
    }),
    NgxMaskModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

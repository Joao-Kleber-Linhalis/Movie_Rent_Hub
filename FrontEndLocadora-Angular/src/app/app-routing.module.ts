import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './componets/nav/nav.component';
import { HomeComponent } from './componets/home/home.component';
import { AtorListComponent } from './componets/ator/ator-list/ator-list.component';
import { AtorCreateComponent } from './componets/ator/ator-create/ator-create.component';
import { DiretorListComponent } from './componets/diretor/diretor-list/diretor-list.component';
import { DiretorCreateComponent } from './componets/diretor/diretor-create/diretor-create.component';
import { ClasseListComponent } from './componets/classe/classe-list/classe-list.component';
import { ClasseCreateComponent } from './componets/classe/classe-create/classe-create.component';
import { AtorUpdateComponent } from './componets/ator/ator-update/ator-update.component';
import { DiretorUpdateComponent } from './componets/diretor/diretor-update/diretor-update.component';
import { ClasseUpdateComponent } from './componets/classe/classe-update/classe-update.component';
import { AtorStatusComponent } from './componets/ator/ator-status/ator-status.component';
import { DiretorStatusComponent } from './componets/diretor/diretor-status/diretor-status.component';
import { ClasseStatusComponent } from './componets/classe/classe-status/classe-status.component';

const routes: Routes = [
  {
    path: '', component: NavComponent, children: [
      { path: 'home', component: HomeComponent },
      //Ator
      { path: 'atores', component: AtorListComponent },
      { path: 'atores/create', component: AtorCreateComponent },
      { path: 'atores/update/:id', component: AtorUpdateComponent },
      { path: 'atores/status/:id', component: AtorStatusComponent },
      //Diretor
      { path: 'diretores', component: DiretorListComponent },
      { path: 'diretores/create', component: DiretorCreateComponent },
      { path: 'diretores/update/:id', component: DiretorUpdateComponent },
      { path: 'diretores/status/:id', component: DiretorStatusComponent },
      //Classes
      { path: 'classes', component: ClasseListComponent },
      { path: 'classes/create', component: ClasseCreateComponent },
      { path: 'classes/update/:id', component: ClasseUpdateComponent },
      { path: 'classes/status/:id', component: ClasseStatusComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ator } from 'src/app/models/ator';
import { Classe } from 'src/app/models/classe';
import { Diretor } from 'src/app/models/diretor';
import { CategoriaEnum } from 'src/app/models/enums/categoriaFilme.enum';
import { Titulo } from 'src/app/models/titulo';
import { AtorService } from 'src/app/services/ator.service';
import { ClasseService } from 'src/app/services/classe.service';
import { DiretorService } from 'src/app/services/diretor.service';
import { TituloService } from 'src/app/services/titulo.service';

@Component({
  selector: 'app-titulo-status',
  templateUrl: './titulo-status.component.html',
  styleUrls: ['./titulo-status.component.css']
})
export class TituloStatusComponent implements OnInit {

  categoriaFilme = Object.values(CategoriaEnum);

  peloMenosUmaCategoriaSelecionada: boolean = false;

  titulo: Titulo = {
    id: '',
    nome: '',
    ano: null,
    sinopse: '',
    capa: null,
    categorias: [],
    items: [],
    diretor: null,
    classe: null,
    atores: [],
    ativo: true,
  }

  classesList: Classe[] = []
  diretoresList: Diretor[] = []
  atoresList: Ator[] = []
  fileSelected?: Blob;
  imageUrl?: string;
  oldImageUrl: any;

  
  categorias = new FormControl([], Validators.required);
  atores: FormControl = new FormControl(null);
  compareWith = (o1: Ator, o2: Ator) => o1.id == o2.id;
  compareCategorias(categoria: CategoriaEnum, categoriaSelecionada: string): boolean {
    return categoria === CategoriaEnum[categoriaSelecionada];
  }

  ngOnInit(): void {
    this.titulo.id = this.route.snapshot.paramMap.get('id');
    this.findById();
  }

  constructor(
    private classeService: ClasseService,
    private toast: ToastrService,
    private router: Router,
    private diretorService: DiretorService,
    private atorService: AtorService,
    private tituloService: TituloService,
    private sant: DomSanitizer,
    private route: ActivatedRoute,
  ) { }

  findById(): void {
    this.tituloService.findById(this.titulo.id).subscribe(
      resposta => {
        this.titulo = resposta;
        console.log(this.titulo.atores);
        console.log("this.titulo.capa:", this.titulo.capa);

        this.oldImageUrl = this.titulo.capa;
        console.log("oldImageUrl:", this.oldImageUrl);
      }
    )
  }


  verificarSelecaoCategorias() {
    this.peloMenosUmaCategoriaSelecionada = this.titulo.categorias.length > 0;
    console.log(this.titulo.categorias);

    // Você pode mostrar uma mensagem de erro ou tomar outra ação aqui
    if (!this.peloMenosUmaCategoriaSelecionada) {
      this.toast.info('Selecione pelo menos uma categoria.');
      // Ou definir uma variável de controle para mostrar uma mensagem de erro na interface do usuário, etc.
    }
  }



  able(): void{
    this.tituloService.able(this.titulo.id).subscribe(resposta => {
      this.toast.success('Título Ativado com sucesso', 'Ativar');
      this.router.navigate(["titulos"])
    }, ex => {
      console.log(ex);
      if (ex.error.errors) {
        ex.error.errors.array.forEach(element => {
          this.toast.error(element.message);
        })
      } else {
        this.toast.error(ex.error.message);
      }
    })
  }

  disable(): void{
    this.tituloService.disable(this.titulo.id).subscribe(resposta => {
      this.toast.success('Título Desativado com sucesso', 'Desativar');
      this.router.navigate(["titulos"])
    }, ex => {
      console.log(ex);
      if (ex.error.errors) {
        ex.error.errors.array.forEach(element => {
          this.toast.error(element.message);
        })
      } else {
        this.toast.error(ex.error.message);
      }
    })
  }
}

import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
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
  selector: 'app-titulo-create',
  templateUrl: './titulo-create.component.html',
  styleUrls: ['./titulo-create.component.css']
})
export class TituloCreateComponent implements OnInit {

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

  nome = new FormControl(null, Validators.required);
  ano = new FormControl(null, Validators.required);
  sinopse = new FormControl(null, Validators.required);
  capa = new FormControl(null);
  categorias = new FormControl([], Validators.required);
  diretor = new FormControl(null, Validators.required);
  classe = new FormControl(null, Validators.required);
  atores = new FormControl([]);

  ngOnInit(): void {
    this.findAllClassesAtivas();
    this.findAllDiretoresAtivos();
    this.findAllAtoresAtivos();
  }

  constructor(
    private classeService: ClasseService,
    private toast: ToastrService,
    private router: Router,
    private diretorService: DiretorService,
    private atorService: AtorService,
    private tituloService: TituloService,
    private sant: DomSanitizer
  ){}

  create(): void {
    this.titulo.categorias = this.titulo.categorias.map(categoria => this.removerAcentos(categoria))
    console.log(this.titulo.categorias);
    this.tituloService.create(this.titulo).subscribe(resposta => {
      this.toast.success('Titulo Cadastrado com sucesso', 'Cadastro');
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

  findAllClassesAtivas(): void{
    this.classeService.findAll(true).subscribe(resposta=>{
      this.classesList = resposta;
    })
  }

  findAllDiretoresAtivos(): void{
    this.diretorService.findAll(true).subscribe(resposta=>{
      this.diretoresList = resposta;
    })
  }

  findAllAtoresAtivos(): void{
    this.atorService.findAll(true).subscribe(resposta=>{
      this.atoresList = resposta;
    })
  }

  onSelectNewImage(event: any) {
    const target = event.target as HTMLInputElement;
    this.fileSelected = target!.files![0];
    this.imageUrl = this.sant.bypassSecurityTrustUrl(window.URL.createObjectURL(this.fileSelected)) as string

    this.convertFileToBase64();
  }

  convertFileToBase64() {
    let reader = new FileReader();
    reader.readAsDataURL(this.fileSelected as Blob);
    reader.onloadend = () => {
      this.titulo.capa = reader.result as string;
    }
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

  removerAcentos(texto: string): string {
    return texto
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .toUpperCase();
  }

  validarCampos(): boolean {
    return this.nome.valid &&
      this.ano.valid &&
      this.sinopse.valid &&
      this.diretor.valid &&
      this.classe.valid && this.peloMenosUmaCategoriaSelecionada
  }



}

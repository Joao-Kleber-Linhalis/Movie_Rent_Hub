import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TipoEnum } from 'src/app/models/enums/tipoItem.enum';
import { Item } from 'src/app/models/item';
import { Titulo } from 'src/app/models/titulo';
import { ItemService } from 'src/app/services/item.service';
import { TituloService } from 'src/app/services/titulo.service';
import * as moment from 'moment';

@Component({
  selector: 'app-item-create',
  templateUrl: './item-create.component.html',
  styleUrls: ['./item-create.component.css']
})
export class ItemCreateComponent implements OnInit{

  tipoEnum = Object.values(TipoEnum);

  moment = moment;

  item: Item = {
    id: '', //
    numSerie: null, //
    dtAquisicao: moment().format('DD/MM/YYYY'),
    titulo: null, // 
    tipoItem: null, //
    statusItem: 'DISPONIVEL',
    ativo: true //
  }

  numSerie = new FormControl(null, Validators.required); 
  dtAquisicao = new FormControl( moment(), [Validators.required]);
  titulo = new FormControl(null, Validators.required); 
  tipoItem = new FormControl(null, Validators.required); 

  tituloList: Titulo[] = [];

  constructor(
    private router: Router,
    private tituloService: TituloService,
    private toast: ToastrService,
    private itemService: ItemService
  ){}

  ngOnInit(): void {
    this.findAllTitulos();
  }

  create(): void {
    this.item.tipoItem = this.converterParaFormatoCorrigido(this.item.tipoItem);
    console.log(this.item)
    this.itemService.create(this.item).subscribe(resposta => {
      this.toast.success('Item Cadastrado com sucesso', 'Cadastro');
      this.router.navigate(["itens"])
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

  findAllTitulos(){
    this.tituloService.findAll(true).subscribe(resposta=>{
      // Armazene a resposta em uma variável temporária
    const titulos: any[] = resposta;

    // Ordene a lista com base na variável 'nome'
    titulos.sort((a, b) => a.nome.localeCompare(b.nome));

    // Atribua a lista ordenada a this.tituloList
    this.tituloList = titulos;
    })
  }

  onDateSelected(event: any) {
    if (event.value) {
      this.item.dtAquisicao = this.moment(event.value).format('DD/MM/YYYY');
      console.log('item.dtAquisicao:', this.item.dtAquisicao);
    } else {
      this.item.dtAquisicao = moment().format('DD/MM/YYYY');
    }
  }

  validarCampos():boolean{
    return this.numSerie.valid && this.dtAquisicao.valid && this.titulo.valid && this.tipoItem.valid
  }

  converterParaFormatoCorrigido(stringFormato: string): string {
    const formatoCorrigido = stringFormato.toLowerCase();
  
    // Tratamento especial para casos específicos
    if (formatoCorrigido === "digital") {
      return "DIGITAL";
    } else if (formatoCorrigido === "blu-ray") {
      return "BLU_RAY";
    } else if (formatoCorrigido === "outro") {
      return "OUTRO";
    }
  
    // Caso geral (outras strings): converter para letras maiúsculas
    return formatoCorrigido.toUpperCase();
  }

  printtest(){
    console.log(this.item)
  }
}

moment.locale('pt-BR');

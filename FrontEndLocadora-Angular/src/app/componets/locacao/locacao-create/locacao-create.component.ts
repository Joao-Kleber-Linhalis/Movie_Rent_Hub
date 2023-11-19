import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { Item } from 'src/app/models/item';
import { Locacao } from 'src/app/models/locacao';
import { ClienteService } from 'src/app/services/cliente.service';
import { ItemService } from 'src/app/services/item.service';
import { LocacaoService } from 'src/app/services/locacao.service';
import { TituloService } from 'src/app/services/titulo.service';

@Component({
  selector: 'app-locacao-create',
  templateUrl: './locacao-create.component.html',
  styleUrls: ['./locacao-create.component.css']
})
export class LocacaoCreateComponent implements OnInit {

  moment = moment;
  itemId: number;
  maxDate: Date;
  minDate: Date;

  locacao: Locacao = {
    id: '', //
    cliente: null, //
    dependente: null, //
    item: null, //
    dtLocacao: this.moment().format('DD/MM/YYYY'), //
    dtDevolucaoPrevista: '', //
    dtDevolucaoEfetiva: null, //
    valorCobrado: null, //
    multaCobrada: null, //
    total: null, //
  }

  clienteList: Cliente[] = [];

  cliente = new FormControl(null, Validators.required);
  item = new FormControl(null, Validators.required);
  dtLocacao = new FormControl(moment(), Validators.required);
  dtDevolucaoPrevista = new FormControl(null, Validators.required);
  valorCobrado = new FormControl(null, Validators.required);
dependente: any;

  validarCampos(): boolean {
    return this.cliente.valid && this.item.valid && this.dtLocacao.valid
      && this.dtDevolucaoPrevista.valid && this.valorCobrado.valid
  }

  constructor(
    private router: Router,
    private toast: ToastrService,
    private itemService: ItemService,
    private clienteService: ClienteService,
    private locacaoService: LocacaoService,
  ) { }

  ngOnInit(): void {
    this.maxDate = new Date();
    this.minDate = new Date();
    this.minDate.setDate(this.minDate.getDate() + 1);
    this.findAllCliente();
  }

  findAllCliente() {
    this.clienteService.findAll(true).subscribe(resposta => {
      // Armazene a resposta em uma variável temporária
      const clientes: any[] = resposta;

      // Ordene a lista com base na variável 'cliente.nome'
      clientes.sort((a, b) => a.nome.localeCompare(b.nome));

      // Atribua a lista ordenada a this.clienteList
      this.clienteList = clientes;
    },
      error => {
        this.toast.error("Erro no Carregamento de Clientes", "ERRO");
      });
  }

  findItemById() {

  }

  create(): void {
    this.locacaoService.create(this.locacao).subscribe(resposta => {
      this.toast.success('Locação Cadastrado com sucesso', 'Cadastro');
      this.router.navigate(["locacoes"])
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

  onDateSelected(event: any, dateControlName: string) {
    if (event.value) {
      this.locacao[dateControlName] = this.moment(event.value).format('DD/MM/YYYY');
      console.log("locacao" + this.locacao.dtLocacao);
      console.log("prevista" + this.locacao.dtDevolucaoPrevista);
    } else {
      this.locacao[dateControlName] = this.moment().format('DD/MM/YYYY');
    }
  }

  pesquisarItem() {
    if (this.itemId) {
      this.itemService.findById(this.itemId).subscribe(
        (item) => {
          this.locacao.item = item;
          this.item.setValue(item);
          this.valorCobrado.setValue(item.titulo.classe.valor);
          const prazoDevolucaoDias = item.titulo.classe.prazoDevolucao;
          // Use o moment para criar um objeto moment com a data de hoje.
          const dataHojeMoment = moment();
          // Adicione a quantidade de dias ao objeto moment da data de hoje.
          const dtDevolucaoPrevistaMoment = dataHojeMoment.add(prazoDevolucaoDias, 'days');
          // Defina o valor do controle com o objeto moment.
          this.locacao.dtDevolucaoPrevista = this.moment(dtDevolucaoPrevistaMoment).format('DD/MM/YYYY');
          this.dtDevolucaoPrevista.setValue(dtDevolucaoPrevistaMoment);

          this.locacao.valorCobrado = item.titulo.classe.valor;
        },
        (error) => {
          this.toast.error('Erro ao pesquisar item:', "ERRO");
          // Manipule o erro conforme necessário
        }
      );
    } else {
      this.toast.error('ID do item não fornecido');
      // Trate o caso em que o ID do item não é fornecido
    }
  }

  displayDependente(dependente: any): string {
    return dependente ? dependente.nome : 'Nenhum Dependente';
  }

}

moment.locale('pt-BR');

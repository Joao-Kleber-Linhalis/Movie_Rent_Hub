import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { Dependente } from 'src/app/models/dependente';
import { Locacao } from 'src/app/models/locacao';
import { ClienteService } from 'src/app/services/cliente.service';
import { LocacaoService } from 'src/app/services/locacao.service';

@Component({
  selector: 'app-locacao-devolucao',
  templateUrl: './locacao-devolucao.component.html',
  styleUrls: ['./locacao-devolucao.component.css']
})
export class LocacaoDevolucaoComponent implements OnInit {

  moment = moment;
  itemId: number;
  maxDate: Date;
  minDate: Date;
  diasAtraso: number;
  multa:number;

  locacao: Locacao = {
    id: '', //
    cliente: null, //
    dependente: null, //
    item: null, //
    dtLocacao: '', //
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
  multaCobrada = new FormControl(null, Validators.required);
  total = new FormControl(null, Validators.required);
  compareWithCliente = (o1: Cliente, o2: Cliente) => o1.id == o2.id;
  compareWithDependente = (o1: Dependente, o2: Dependente) => o1.id == o2.id;

  constructor(
    private router: Router,
    private toast: ToastrService,
    private clienteService: ClienteService,
    private locacaoService: LocacaoService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.locacao.id = this.route.snapshot.paramMap.get('id');
    this.findById();
    this.maxDate = new Date();
    this.minDate = new Date();
    this.minDate.setDate(this.minDate.getDate() + 1);
    this.findAllCliente();
  }

  validarCampos(): boolean{
    return this.multaCobrada.valid && this.total.valid
  }

  findById(): void {
    this.locacaoService.findById(this.locacao.id).subscribe(
      resposta => {
        this.locacao = resposta;
        this.item.setValue(this.locacao.item);
        this.itemId = this.locacao.item.id;
        this.valorCobrado.setValue(this.locacao.valorCobrado);
        this.findByIdCliente();
        console.log(this.locacao);
        const dtLocacaoString = this.locacao.dtLocacao;
        const dtDevolucaoPrevistaString = this.locacao.dtDevolucaoPrevista;

        // Use o moment para criar um objeto moment a partir da string.
        const dtLocacaoMoment = moment(dtLocacaoString, 'DD/MM/YYYY');
        const dtDevolucaoPrevistaMoment = moment(dtDevolucaoPrevistaString, 'DD/MM/YYYY');
        // Defina o valor do controle com o objeto moment.
        this.dtDevolucaoPrevista.setValue(dtDevolucaoPrevistaMoment);
        this.dtLocacao.setValue(dtLocacaoMoment);
        const hoje = moment(); // data atual
        const diasAtraso = Math.max(0, hoje.diff(dtDevolucaoPrevistaMoment, 'days'));
        this.diasAtraso = diasAtraso;
        console.log(diasAtraso);
        this.locacao.multaCobrada = diasAtraso * this.locacao.valorCobrado;
        this.locacao.total = this.locacao.valorCobrado + this.locacao.multaCobrada;
        this.total.setValue(this.locacao.total);
        this.multaCobrada.setValue(this.locacao.multaCobrada);
      }
    )
  }

  findByIdCliente(): void {
    this.clienteService.findById(this.locacao.cliente.id).subscribe(
      resposta => {
        this.locacao.cliente = resposta;
      }
    )
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

  devolution(): void {
    this.locacao.dtDevolucaoEfetiva = moment().format('DD/MM/YYYY');
    this.locacaoService.devolution(this.locacao).subscribe(resposta => {
      this.toast.success('Locação devolvida com sucesso', 'Devolução');
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

  recalculateTotal(): void {
    // Verifique se locacao.multaCobrada é um número antes de calcular
    this.multa = this.locacao.multaCobrada;

    if (!isNaN(this.multa)) {
      // Faça o cálculo do total com base na multa
      // Exemplo: total = diasAtraso * multa
      this.locacao.total = this.locacao.valorCobrado + this.multa;
    } else {
      // Caso a multa não seja um número válido, defina o total como 0 ou outro valor padrão
      this.locacao.total = this.locacao.valorCobrado;
    }
  }

}

moment.locale('pt-BR');


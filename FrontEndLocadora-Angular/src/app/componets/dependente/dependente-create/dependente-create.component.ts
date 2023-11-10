import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { Dependente } from 'src/app/models/dependente';
import { ClienteService } from 'src/app/services/cliente.service';
import { DependenteService } from 'src/app/services/dependente.service';

@Component({
  selector: 'app-dependente-create',
  templateUrl: './dependente-create.component.html',
  styleUrls: ['./dependente-create.component.css']
})
export class DependenteCreateComponent implements OnInit{

  moment = moment;

  dependente: Dependente = {
    id: '',
    nome: '',
    sexo: '',
    cliente: null,
    cpf: '',
    nascimento: '',
    ativo : true,
  }

  clienteList: Cliente[] = [];

  maxDate= new Date();


  nome = new FormControl(null, Validators.required);
  sexo = new FormControl(null, Validators.required);
  cliente = new FormControl(null, Validators.required); 
  cpf = new FormControl(null,[Validators.required, Validators.minLength(11)]);
  dtNascimento = new FormControl(null, [Validators.required]);

  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private toast: ToastrService,
    private dependenteService: DependenteService
  ) { }

  ngOnInit(): void {
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

  create(): void {
    this.dependenteService.create(this.dependente).subscribe(resposta => {
      this.toast.success('Dependente Cadastrado com sucesso', 'Cadastro');
      this.router.navigate(["dependentes"])
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

  onDateSelected(event: any) {
    if (event.value) {
      this.dependente.nascimento = this.moment(event.value).format('DD/MM/YYYY');
    } else {
      this.dependente.nascimento = moment().format('DD/MM/YYYY');
    }
  }

  validarCampos(): boolean {
    return this.nome.valid && this.dtNascimento.valid && this.cpf.valid &&
      this.cliente.valid && this.sexo.valid
  }

}

moment.locale('pt-BR');


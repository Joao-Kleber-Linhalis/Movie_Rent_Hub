import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { Dependente } from 'src/app/models/dependente';
import { ClienteService } from 'src/app/services/cliente.service';
import { DependenteService } from 'src/app/services/dependente.service';

@Component({
  selector: 'app-dependente-status',
  templateUrl: './dependente-status.component.html',
  styleUrls: ['./dependente-status.component.css']
})
export class DependenteStatusComponent  implements OnInit{

  moment = moment;
  maxDate= new Date();

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


  compareWith = (o1: Cliente, o2: Cliente) => o1.id == o2.id;

  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private toast: ToastrService,
    private dependenteService: DependenteService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.dependente.id = this.route.snapshot.paramMap.get('id');
    this.findById();
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

  findById(): void {
    this.dependenteService.findById(this.dependente.id).subscribe(
      resposta => {
        this.dependente = resposta;
      }
    )
  }

  able(): void{
    this.dependenteService.able(this.dependente.id).subscribe(resposta => {
      this.toast.success('Dependente Ativado com sucesso', 'Ativar');
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

  disable(): void{
    this.dependenteService.disable(this.dependente.id).subscribe(resposta => {
      this.toast.success('Dependente Desativado com sucesso', 'Desativar');
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

}

moment.locale('pt-BR');

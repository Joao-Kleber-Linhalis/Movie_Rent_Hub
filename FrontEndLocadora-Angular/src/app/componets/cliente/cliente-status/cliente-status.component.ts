import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-status',
  templateUrl: './cliente-status.component.html',
  styleUrls: ['./cliente-status.component.css']
})
export class ClienteStatusComponent implements OnInit {

  cliente: Cliente = {
    id: '',
    nome: '',
    endereco: '',
    telefone: '',
    sexo: '',
    cpf: '',
    nascimento: '',
    ativo: true,
  }


  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private toast: ToastrService,
    private route: ActivatedRoute,
    
  ) { }

  ngOnInit(): void {
    this.cliente.id = this.route.snapshot.paramMap.get('id');
    this.findById();
  }
  

  update(): void {
    this.clienteService.update(this.cliente).subscribe(resposta => {
      this.toast.success('Cliente atualizado com sucesso', 'Atualizar');
      this.router.navigate(["clientes"])
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

  findById(): void{
    this.clienteService.findById(this.cliente.id).subscribe(
      resposta =>{
        this.cliente = resposta;
      }
    )
  }

  able(): void{
    this.clienteService.able(this.cliente.id).subscribe(resposta => {
      this.toast.success('Cliente Ativado com sucesso', 'Ativar');
      this.router.navigate(["clientes"])
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
    this.clienteService.disable(this.cliente.id).subscribe(resposta => {
      this.toast.success('Cliente Desativado com sucesso', 'Desativar');
      this.router.navigate(["clientes"])
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
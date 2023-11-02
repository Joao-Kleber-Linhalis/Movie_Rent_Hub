import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-create',
  templateUrl: './cliente-create.component.html',
  styleUrls: ['./cliente-create.component.css']
})
export class ClienteCreateComponent implements OnInit {

  moment = moment;

  minDate: Date;
  maxDate: Date;

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

  nome = new FormControl(null, Validators.required);
  dtNascimento = new FormControl(null, [Validators.required]);
  endereco = new FormControl(null, Validators.required);
  telefone = new FormControl(null, [Validators.required, Validators.minLength(11)]);
  cpf = new FormControl(null,[Validators.required, Validators.minLength(11)]);
  sexo = new FormControl(null, Validators.required);

  constructor(
    private router: Router,
    private clienteService: ClienteService,
    private toast: ToastrService,
    
  ) { }

  ngOnInit(): void {
    // Calcule a data máxima com base na idade de 18 anos a partir da data atual.
    const today = new Date();
    const maxYear = today.getFullYear() - 18;
    const maxMonth = today.getMonth();
    const maxDay = today.getDate();
    this.maxDate = new Date(maxYear, maxMonth, maxDay);

    // Calcule a data mínima como 100 anos antes da data atual.
    const minYear = today.getFullYear() - 100;
    this.minDate = new Date(minYear, maxMonth, maxDay);
  }
  

  create(): void {
    this.clienteService.create(this.cliente).subscribe(resposta => {
      this.toast.success('Cliente Cadastrado com sucesso', 'Cadastro');
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

  onDateSelected(event: any) {
    if (event.value) {
      this.cliente.nascimento = this.moment(event.value).format('DD/MM/YYYY');
    } else {
      this.cliente.nascimento = moment().format('DD/MM/YYYY');
    }
  }

  validarCampos(): boolean {
    return this.nome.valid && this.dtNascimento.valid && this.cpf.valid &&
      this.endereco.valid && this.sexo.valid && this.telefone.valid
  }
}

moment.locale('pt-BR');

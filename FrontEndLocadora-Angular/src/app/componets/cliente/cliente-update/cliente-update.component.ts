import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-update',
  templateUrl: './cliente-update.component.html',
  styleUrls: ['./cliente-update.component.css']
})
export class ClienteUpdateComponent implements OnInit {

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
    private route: ActivatedRoute,
    
  ) { }

  ngOnInit(): void {
    this.cliente.id = this.route.snapshot.paramMap.get('id');
    this.findById();
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
        const dtAquisicaoString = this.cliente.nascimento;

        // Use o moment para criar um objeto moment a partir da string.
        const dtAquisicaoMoment = moment(dtAquisicaoString, 'DD/MM/YYYY');

        // Defina o valor do controle com o objeto moment.
        this.dtNascimento.setValue(dtAquisicaoMoment);

      }
    )
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

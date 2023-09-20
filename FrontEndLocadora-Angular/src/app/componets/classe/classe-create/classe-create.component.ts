import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Classe } from 'src/app/models/classe';
import { ClasseService } from 'src/app/services/classe.service';

@Component({
  selector: 'app-classe-create',
  templateUrl: './classe-create.component.html',
  styleUrls: ['./classe-create.component.css']
})
export class ClasseCreateComponent implements OnInit {

  classe: Classe = {
    id: '',
    nome: '',
    prazoDevolucao: null,
    valor: null,
    ativo: true,
  }
  // Para prazoDevolucao
  prazoDevolucao: FormControl = new FormControl(null, [
    Validators.required,
    Validators.min(1)]);

  // Para valor
  valor: FormControl = new FormControl(null, [
    Validators.required,
    Validators.min(0.01)
  ]);

  nome: FormControl = new FormControl(null, Validators.minLength(3));

  constructor(
    private service: ClasseService,
    private toast: ToastrService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  create(): void {
    this.service.create(this.classe).subscribe(resposta => {
      this.toast.success('Classe Cadastrado com sucesso', 'Cadastro');
      this.router.navigate(["classes"])
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

  validarCampos(): boolean {
    return this.nome.valid && this.prazoDevolucao.valid && this.valor.valid;
  }

}

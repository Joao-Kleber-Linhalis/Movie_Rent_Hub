import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Diretor } from 'src/app/models/diretor';
import { DiretorService } from 'src/app/services/diretor.service';

@Component({
  selector: 'app-diretor-create',
  templateUrl: './diretor-create.component.html',
  styleUrls: ['./diretor-create.component.css']
})
export class DiretorCreateComponent implements OnInit {

  ngOnInit(): void {
  }

  diretor: Diretor = {
    id: '',
    nome: '',
    ativo: true,
  }

  nome: FormControl = new FormControl(null, Validators.minLength(3));

  constructor(
    private service: DiretorService,
    private toast: ToastrService,
    private router: Router
  ) { }

  create(): void {
    this.service.create(this.diretor).subscribe(resposta => {
      this.toast.success('Diretor Cadastrado com sucesso', 'Cadastro');
      this.router.navigate(["diretores"])
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
    return this.nome.valid;
  }
}

import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Diretor } from 'src/app/models/diretor';
import { DiretorService } from 'src/app/services/diretor.service';

@Component({
  selector: 'app-diretor-update',
  templateUrl: './diretor-update.component.html',
  styleUrls: ['./diretor-update.component.css']
})
export class DiretorUpdateComponent implements OnInit {



  diretor: Diretor = {
    id: '',
    nome: '',
    ativo: true,
  }

  nome: FormControl = new FormControl(null, Validators.minLength(3));

  constructor(
    private service: DiretorService,
    private toast: ToastrService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.diretor.id = this.route.snapshot.paramMap.get('id');
    this.findById();
  }

  findById(): void {
    this.service.findById(this.diretor.id).subscribe(
      resposta => {
        this.diretor = resposta;
      }
    )
  }
  update(): void {
    this.service.update(this.diretor).subscribe(resposta => {
      this.toast.success('Diretor Atualizado com sucesso', 'Atualização');
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

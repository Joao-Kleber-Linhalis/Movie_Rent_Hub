import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ator } from 'src/app/models/ator';
import { AtorService } from 'src/app/services/ator.service';

@Component({
  selector: 'app-ator-update',
  templateUrl: './ator-update.component.html',
  styleUrls: ['./ator-update.component.css']
})
export class AtorUpdateComponent implements OnInit {
  ator: Ator = {
    id: '',
    nome: '',
    ativo: true,
  }

  nome: FormControl = new FormControl(null, Validators.minLength(3));

  constructor(
    private service: AtorService,
    private toast: ToastrService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.ator.id = this.route.snapshot.paramMap.get('id');
    this.findById();
  }

  findById(): void{
    this.service.findById(this.ator.id).subscribe(
      resposta =>{
        this.ator = resposta;
      }
    )
  }

  update(): void {
    this.service.update(this.ator).subscribe(resposta => {
      this.toast.success('Ator Atualizado com sucesso', 'Atualização');
      this.router.navigate(["atores"])
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

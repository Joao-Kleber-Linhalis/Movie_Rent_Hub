import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Classe } from 'src/app/models/classe';
import { ClasseService } from 'src/app/services/classe.service';

@Component({
  selector: 'app-classe-update',
  templateUrl: './classe-update.component.html',
  styleUrls: ['./classe-update.component.css']
})
export class ClasseUpdateComponent implements OnInit {

  
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
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.classe.id = this.route.snapshot.paramMap.get('id');
    this.findById();
  }

  findById(): void{
    this.service.findById(this.classe.id).subscribe(
      resposta =>{
        this.classe = resposta;
      }
    )
  }

  update(): void {
    this.service.update(this.classe).subscribe(resposta => {
      this.toast.success('Classe Atualizado com sucesso', 'Atualização');
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

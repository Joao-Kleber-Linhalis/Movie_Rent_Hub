import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Classe } from 'src/app/models/classe';
import { ClasseService } from 'src/app/services/classe.service';

@Component({
  selector: 'app-classe-status',
  templateUrl: './classe-status.component.html',
  styleUrls: ['./classe-status.component.css']
})
export class ClasseStatusComponent implements OnInit {

  
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

  able(): void{
    this.service.able(this.classe.id).subscribe(resposta => {
      this.toast.success('Classe Ativado com sucesso', 'Ativar');
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

  disable(): void{
    this.service.disable(this.classe.id).subscribe(resposta => {
      this.toast.success('Classe Desativado com sucesso', 'Desativar');
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

  

}

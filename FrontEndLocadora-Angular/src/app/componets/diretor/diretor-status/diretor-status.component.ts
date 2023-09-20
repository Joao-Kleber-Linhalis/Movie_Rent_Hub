import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Diretor } from 'src/app/models/diretor';
import { DiretorService } from 'src/app/services/diretor.service';

@Component({
  selector: 'app-diretor-status',
  templateUrl: './diretor-status.component.html',
  styleUrls: ['./diretor-status.component.css']
})
export class DiretorStatusComponent implements OnInit {



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
  
  able(): void{
    this.service.able(this.diretor.id).subscribe(resposta => {
      this.toast.success('Diretor Ativado com sucesso', 'Ativar');
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

  disable(): void{
    this.service.disable(this.diretor.id).subscribe(resposta => {
      this.toast.success('Diretor Desativado com sucesso', 'Desativar');
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
}

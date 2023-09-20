import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ator } from 'src/app/models/ator';
import { AtorService } from 'src/app/services/ator.service';

@Component({
  selector: 'app-ator-status',
  templateUrl: './ator-status.component.html',
  styleUrls: ['./ator-status.component.css']
})
export class AtorStatusComponent implements OnInit {

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

  findById(): void {
    this.service.findById(this.ator.id).subscribe(
      resposta => {
        this.ator = resposta;
      }
    )
  }

  able(): void{
    this.service.able(this.ator.id).subscribe(resposta => {
      this.toast.success('Ator Ativado com sucesso', 'Ativar');
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

  disable(): void{
    this.service.disable(this.ator.id).subscribe(resposta => {
      this.toast.success('Ator Desativado com sucesso', 'Desativar');
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

}

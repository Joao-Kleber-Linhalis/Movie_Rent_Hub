import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Titulo } from 'src/app/models/titulo';
import { TituloService } from 'src/app/services/titulo.service';

@Component({
  selector: 'app-galeria',
  templateUrl: './galeria.component.html',
  styleUrls: ['./galeria.component.css']
})
export class GaleriaComponent implements OnInit{

  tituloList: Titulo[];

  constructor(private service: TituloService,private toast: ToastrService){}

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.service.findAll(true).subscribe(resposta => {
      this.tituloList= resposta;
    },
    error=>{
      this.toast.error("Erro no Carregamento de Titulos","ERRO")
    })
  }
}

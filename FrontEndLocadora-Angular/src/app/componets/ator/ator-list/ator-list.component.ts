import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { Ator } from 'src/app/models/ator';
import { AtorService } from 'src/app/services/ator.service';

@Component({
  selector: 'app-ator-list',
  templateUrl: './ator-list.component.html',
  styleUrls: ['./ator-list.component.css']
})
export class AtorListComponent implements OnInit {

  private _ativo: boolean = true;

  private ativoChangeSubject: Subject<boolean> = new Subject<boolean>();


  get ativo(): boolean {
    return this._ativo;
  }

  set ativo(value: boolean) {
    this._ativo = value;
    this.ativoChangeSubject.next(this._ativo); // Notificar mudanças no valor de ativo
    console.log('Valor de ativo foi atualizado para:', this._ativo);
  }

  ELEMENT_DATA: Ator[] = []

  displayedColumns: string[] = ['position', 'name', 'acoes'];
  dataSource = new MatTableDataSource<Ator>(this.ELEMENT_DATA);
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private service: AtorService,
    private toast: ToastrService,
  ) { }

  ngOnInit() {
    this.ativoChangeSubject.subscribe(() => {
      this.findAll();
    });
    this.findAll(); // Chamar findAll() no início
  }

  findAll() {
    this.service.findAll(this.ativo).subscribe(resposta => {
      this.ELEMENT_DATA = resposta;
      this.dataSource = new MatTableDataSource<Ator>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    },
    error=>{
      this.toast.error("Erro no Carregamento de Atores/Atrizes","ERRO")
    })
  }



  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


}


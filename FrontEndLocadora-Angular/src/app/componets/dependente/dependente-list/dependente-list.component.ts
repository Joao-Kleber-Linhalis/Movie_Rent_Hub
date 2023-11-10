import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { Dependente } from 'src/app/models/dependente';
import { DependenteService } from 'src/app/services/dependente.service';

@Component({
  selector: 'app-dependente-list',
  templateUrl: './dependente-list.component.html',
  styleUrls: ['./dependente-list.component.css']
})
export class DependenteListComponent implements OnInit{

  constructor(
    private service: DependenteService,
    private toast: ToastrService
  ){}

  ELEMENT_DATA: Dependente[] = []

  private _ativo: boolean = true;

  private ativoChangeSubject: Subject<boolean> = new Subject<boolean>();

  displayedColumns: string[] = ['position','name','cpf','clienteNome','acoes'];
  dataSource = new MatTableDataSource<Dependente>(this.ELEMENT_DATA);
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit(): void {
    this.ativoChangeSubject.subscribe(() => {
      this.findAll();
    });
    this.findAll();
  }

  findAll() {
    this.service.findAll(this.ativo).subscribe(resposta => {
      this.ELEMENT_DATA = resposta;
      this.dataSource = new MatTableDataSource<Dependente>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    },
    error=>{
      this.toast.error("Erro no Carregamento de Dependentes","ERRO")
    })
  }

  get ativo(): boolean {
    return this._ativo;
  }
  set ativo(value: boolean) {
    this._ativo = value;
    this.ativoChangeSubject.next(this._ativo); // Notificar mudanças no valor de ativo
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}

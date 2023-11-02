import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { Cliente } from 'src/app/models/cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-list',
  templateUrl: './cliente-list.component.html',
  styleUrls: ['./cliente-list.component.css']
})
export class ClienteListComponent implements OnInit {

  constructor(
    private service: ClienteService,
    private toast: ToastrService
  ){}

  ELEMENT_DATA: Cliente[] = []

  private _ativo: boolean = true;

  private ativoChangeSubject: Subject<boolean> = new Subject<boolean>();

  displayedColumns: string[] = ['position','name','telefone','cpf','acoes'];
  dataSource = new MatTableDataSource<Cliente>(this.ELEMENT_DATA);
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
      this.dataSource = new MatTableDataSource<Cliente>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    },
    error=>{
      this.toast.error("Erro no Carregamento de Clientes","ERRO")
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

import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { Locacao } from 'src/app/models/locacao';
import { LocacaoService } from 'src/app/services/locacao.service';

@Component({
  selector: 'app-locacao-list',
  templateUrl: './locacao-list.component.html',
  styleUrls: ['./locacao-list.component.css']
})
export class LocacaoListComponent implements OnInit{

  constructor(private service: LocacaoService,private toast: ToastrService){}

  private _status: string = 'all';

  private statusChangeSubject: Subject<string> = new Subject<string>();
  
  get status(): string {
    return this._status;
  }
  
  set status(value: string) {
    this._status = value;
    this.statusChangeSubject.next(this._status);
  }

  ngOnInit(): void {
    this.statusChangeSubject.subscribe(() => {
      this.handleStatusChange();
    });
    // Chamar handleStatusChange() diretamente no início para evitar duplicação de código
    this.handleStatusChange();
  }
  
  private handleStatusChange(): void {
    if (this.status === 'all') {
      this.findAll();
    } else if (this.status === 'open') {
      this.findAllOpen();
    } else if (this.status === 'closed') {
      this.findAllClose();
    }
  }

  ELEMENT_DATA: Locacao[] = [];

  displayedColumns: string[] = ['position','cliente','id_item','nome_filme','dtDevolucaoPrevista', 'dtDevolucaoEfetiva', 'acoes'];
  dataSource = new MatTableDataSource<Locacao>(this.ELEMENT_DATA);
  @ViewChild(MatPaginator) paginator: MatPaginator;

  findAll() {
    this.service.findAll().subscribe(resposta => {
      this.ELEMENT_DATA = resposta;
      this.dataSource = new MatTableDataSource<Locacao>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    },
    error=>{
      this.toast.error("Erro no Carregamento de Locações","ERRO")
    })
  }

  findAllClose() {
    this.service.findAllClose().subscribe(resposta => {
      this.ELEMENT_DATA = resposta;
      this.dataSource = new MatTableDataSource<Locacao>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    },
    error=>{
      this.toast.error("Erro no Carregamento de Locações","ERRO")
    })
  }

  findAllOpen() {
    this.service.findAllOpen().subscribe(resposta => {
      this.ELEMENT_DATA = resposta;
      this.dataSource = new MatTableDataSource<Locacao>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    },
    error=>{
      this.toast.error("Erro no Carregamento de Locações","ERRO")
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}

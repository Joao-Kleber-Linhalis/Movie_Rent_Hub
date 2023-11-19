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
export class LocacaoListComponent implements OnInit {
  constructor(private service: LocacaoService, private toast: ToastrService) {}

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
  displayedColumns: string[] = ['position', 'cliente', 'id_item', 'nome_filme', 'dtDevolucaoPrevista', 'dtDevolucaoEfetiva', 'acoes'];
  dataSource = new MatTableDataSource<Locacao>(this.ELEMENT_DATA);
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  customFilterPredicate() {
    return (data: Locacao, filter: string) => {
      const searchString = filter.toLowerCase();
      return (
        data.id.toString().includes(searchString) ||
        (data.cliente && data.cliente.nome && data.cliente.nome.toLowerCase().includes(searchString)) ||
        data.item.numSerie.toString().includes(searchString) ||
        (data.item.titulo && data.item.titulo.nome && data.item.titulo.nome.toLowerCase().includes(searchString)) ||
        data.dtDevolucaoPrevista.toString().includes(searchString) ||
        (data.dtDevolucaoEfetiva ? data.dtDevolucaoEfetiva.toString().includes(searchString) : false)
      );
    };
  }

  findAll() {
    this.service.findAll().subscribe(
      (resposta) => {
        this.ELEMENT_DATA = resposta;
        this.dataSource = new MatTableDataSource<Locacao>(this.ELEMENT_DATA);
        this.dataSource.paginator = this.paginator;
        this.dataSource.filterPredicate = this.customFilterPredicate();
      },
      (error) => {
        this.toast.error('Erro no Carregamento de Locações', 'ERRO');
      }
    );
  }

  findAllClose() {
    this.service.findAllClose().subscribe(
      (resposta) => {
        this.ELEMENT_DATA = resposta;
        this.dataSource = new MatTableDataSource<Locacao>(this.ELEMENT_DATA);
        this.dataSource.paginator = this.paginator;
        this.dataSource.filterPredicate = this.customFilterPredicate();
      },
      (error) => {
        this.toast.error('Erro no Carregamento de Locações', 'ERRO');
      }
    );
  }

  findAllOpen() {
    this.service.findAllOpen().subscribe(
      (resposta) => {
        this.ELEMENT_DATA = resposta;
        this.dataSource = new MatTableDataSource<Locacao>(this.ELEMENT_DATA);
        this.dataSource.paginator = this.paginator;
        this.dataSource.filterPredicate = this.customFilterPredicate();
      },
      (error) => {
        this.toast.error('Erro no Carregamento de Locações', 'ERRO');
      }
    );
  }
}

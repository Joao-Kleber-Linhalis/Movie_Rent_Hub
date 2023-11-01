import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { Item } from 'src/app/models/item';
import { Titulo } from 'src/app/models/titulo';
import { ItemService } from 'src/app/services/item.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit{

  constructor(private service: ItemService,private toast: ToastrService){}

  ngOnInit(): void {
    this.ativoChangeSubject.subscribe(() => {
      this.findAll();
    });
    this.findAll();
  }

  ELEMENT_DATA: Item[] = [];

  displayedColumns: string[] = ['position','numSerie', 'name','ano','tipo', 'status', 'acoes'];
  dataSource = new MatTableDataSource<Item>(this.ELEMENT_DATA);
  @ViewChild(MatPaginator) paginator: MatPaginator;

  private _ativo: boolean = true;

  private ativoChangeSubject: Subject<boolean> = new Subject<boolean>();

  get ativo(): boolean {
    return this._ativo;
  }

  set ativo(value: boolean) {
    this._ativo = value;
    this.ativoChangeSubject.next(this._ativo); // Notificar mudanças no valor de ativo
  }

  findAll() {
    this.service.findAll(this.ativo).subscribe(resposta => {
      this.ELEMENT_DATA = resposta;
      this.dataSource = new MatTableDataSource<Item>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    },
    error=>{
      this.toast.error("Erro no Carregamento de Items","ERRO")
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}

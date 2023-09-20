import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subject } from 'rxjs';
import { Classe } from 'src/app/models/classe';
import { ClasseService } from 'src/app/services/classe.service';

@Component({
  selector: 'app-classe-list',
  templateUrl: './classe-list.component.html',
  styleUrls: ['./classe-list.component.css']
})
export class ClasseListComponent implements OnInit {

  constructor(
    private service: ClasseService
  ){}

  ELEMENT_DATA: Classe[] = []

  private _ativo: boolean = true;

  private ativoChangeSubject: Subject<boolean> = new Subject<boolean>();


  get ativo(): boolean {
    return this._ativo;
  }

  set ativo(value: boolean) {
    this._ativo = value;
    this.ativoChangeSubject.next(this._ativo); // Notificar mudanças no valor de ativo
  }


  displayedColumns: string[] = ['position', 'name','valor','prazoDevolucao', 'acoes'];
  dataSource = new MatTableDataSource<Classe>(this.ELEMENT_DATA);
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
      this.dataSource = new MatTableDataSource<Classe>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    })
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}

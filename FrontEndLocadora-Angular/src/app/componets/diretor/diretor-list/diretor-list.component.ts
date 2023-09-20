import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subject } from 'rxjs';
import { Diretor } from 'src/app/models/diretor';
import { DiretorService } from 'src/app/services/diretor.service';

@Component({
  selector: 'app-diretor-list',
  templateUrl: './diretor-list.component.html',
  styleUrls: ['./diretor-list.component.css']
})
export class DiretorListComponent implements OnInit {

  constructor(
    private service: DiretorService
  ) { }

  ELEMENT_DATA: Diretor[] = []
  
  private _ativo: boolean = true;

  private ativoChangeSubject: Subject<boolean> = new Subject<boolean>();

  get ativo(): boolean {
    return this._ativo;
  }

  set ativo(value: boolean) {
    this._ativo = value;
    this.ativoChangeSubject.next(this._ativo); // Notificar mudanças no valor de ativo
  }


  displayedColumns: string[] = ['position', 'name', 'acoes'];
  dataSource = new MatTableDataSource<Diretor>(this.ELEMENT_DATA);
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
      this.dataSource = new MatTableDataSource<Diretor>(this.ELEMENT_DATA);
      this.dataSource.paginator = this.paginator;
    })
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}

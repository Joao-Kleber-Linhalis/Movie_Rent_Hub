import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Classe } from '../models/classe';
import { API_CONFIG } from '../config/api.config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClasseService {

  constructor(private http: HttpClient) { }

  findById(id: any): Observable<Classe>{
    return this.http.get<Classe>(`${API_CONFIG.baseUrl}/classes/${id}`);
  }

  findAll(ativo: Boolean){
    if(ativo){
      return this.http.get<Classe[]>(`${API_CONFIG.baseUrl}/classes/?status=ativos`)
    }
    else{
      return this.http.get<Classe[]>(`${API_CONFIG.baseUrl}/classes/?status=inativos`)
    }
  }

  create(classe: Classe): Observable<Classe>{
    return this.http.post<Classe>(`${API_CONFIG.baseUrl}/classes`,classe)
  }

  update(classe: Classe): Observable<Classe>{
    return this.http.put<Classe>(`${API_CONFIG.baseUrl}/classes/${classe.id}`,classe)
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../config/api.config';
import { Ator } from '../models/ator';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class AtorService {


  

  constructor(private http: HttpClient) { }

  findById(id: any): Observable<Ator>{
    return this.http.get<Ator>(`${API_CONFIG.baseUrl}/atores/${id}`);
  }

  findAll(ativo: Boolean){
    if(ativo){
      return this.http.get<Ator[]>(`${API_CONFIG.baseUrl}/atores/?status=ativos`)
    }
    else{
      return this.http.get<Ator[]>(`${API_CONFIG.baseUrl}/atores/?status=inativos`)
    }
  }

  create(ator: Ator): Observable<Ator>{
    return this.http.post<Ator>(`${API_CONFIG.baseUrl}/atores`,ator)
  }

  update(ator: Ator): Observable<Ator>{
    return this.http.put<Ator>(`${API_CONFIG.baseUrl}/atores/${ator.id}`,ator)
  }

  disable(id: any): Observable<Ator> {
    return this.http.put<Ator>(`${API_CONFIG.baseUrl}/atores/desabilitar/${id}`,null)
  }
}

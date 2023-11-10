import { Injectable } from '@angular/core';
import { Dependente } from '../models/dependente';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class DependenteService {

  constructor(private http: HttpClient) { }

  findById(id: any): Observable<Dependente>{
    return this.http.get<Dependente>(`${API_CONFIG.baseUrl}/dependentes/${id}`);
  }

  findAll(ativo: Boolean){
    if(ativo){
      return this.http.get<Dependente[]>(`${API_CONFIG.baseUrl}/dependentes/?status=ativos`)
    }
    else{
      return this.http.get<Dependente[]>(`${API_CONFIG.baseUrl}/dependentes/?status=inativos`)
    }
  }

  create(Dependente: Dependente): Observable<Dependente>{
    return this.http.post<Dependente>(`${API_CONFIG.baseUrl}/dependentes`,Dependente)
  }

  update(Dependente: Dependente): Observable<Dependente>{
    return this.http.put<Dependente>(`${API_CONFIG.baseUrl}/dependentes/${Dependente.id}`,Dependente)
  }

  disable(id: any): Observable<Dependente> {
    return this.http.put<Dependente>(`${API_CONFIG.baseUrl}/dependentes/desabilitar/${id}`,null)
  }

  able(id: any): Observable<Dependente> {
    return this.http.put<Dependente>(`${API_CONFIG.baseUrl}/dependentes/habilitar/${id}`,null)
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Diretor } from '../models/diretor';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class DiretorService {

  constructor(private http: HttpClient) { }

  findById(id: any): Observable<Diretor>{
    return this.http.get<Diretor>(`${API_CONFIG.baseUrl}/diretores/${id}`);
  }

  findAll(ativo: Boolean){
    if(ativo){
      return this.http.get<Diretor[]>(`${API_CONFIG.baseUrl}/diretores/?status=ativos`)
    }
    else{
      return this.http.get<Diretor[]>(`${API_CONFIG.baseUrl}/diretores/?status=inativos`)
    }
  }

  create(diretor: Diretor): Observable<Diretor>{
    return this.http.post<Diretor>(`${API_CONFIG.baseUrl}/diretores`,diretor)
  }

  update(diretor: Diretor): Observable<Diretor>{
    return this.http.put<Diretor>(`${API_CONFIG.baseUrl}/diretores/${diretor.id}`,diretor)
  }

  disable(id: any): Observable<Diretor> {
    return this.http.put<Diretor>(`${API_CONFIG.baseUrl}/diretores/desabilitar/${id}`,null)
  }

  able(id: any): Observable<Diretor> {
    return this.http.put<Diretor>(`${API_CONFIG.baseUrl}/diretores/habilitar/${id}`,null)
  }
}

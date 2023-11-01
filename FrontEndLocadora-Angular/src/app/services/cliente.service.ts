import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cliente } from '../models/cliente';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private http: HttpClient) { }

  findById(id: any): Observable<Cliente>{
    return this.http.get<Cliente>(`${API_CONFIG.baseUrl}/clientes/${id}`);
  }

  findAll(ativo: Boolean){
    if(ativo){
      return this.http.get<Cliente[]>(`${API_CONFIG.baseUrl}/clientes/?status=ativos`)
    }
    else{
      return this.http.get<Cliente[]>(`${API_CONFIG.baseUrl}/clientes/?status=inativos`)
    }
  }

  create(cliente: Cliente): Observable<Cliente>{
    return this.http.post<Cliente>(`${API_CONFIG.baseUrl}/clientes`,cliente)
  }

  update(cliente: Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(`${API_CONFIG.baseUrl}/clientes/${cliente.id}`,cliente)
  }

  disable(id: any): Observable<Cliente> {
    return this.http.put<Cliente>(`${API_CONFIG.baseUrl}/clientes/desabilitar/${id}`,null)
  }

  able(id: any): Observable<Cliente> {
    return this.http.put<Cliente>(`${API_CONFIG.baseUrl}/clientes/habilitar/${id}`,null)
  }
}

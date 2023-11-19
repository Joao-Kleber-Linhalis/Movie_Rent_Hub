import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Locacao } from '../models/locacao';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class LocacaoService {

  constructor(private http: HttpClient) { }

  findById(id: any): Observable<Locacao> {
    return this.http.get<Locacao>(`${API_CONFIG.baseUrl}/locacoes/${id}`);
  }

  findAll() {
    return this.http.get<Locacao[]>(`${API_CONFIG.baseUrl}/locacoes/?status=all`)
  }

  findAllOpen() {
    return this.http.get<Locacao[]>(`${API_CONFIG.baseUrl}/locacoes/?status=open`)
  }

  findAllClose() {
    return this.http.get<Locacao[]>(`${API_CONFIG.baseUrl}/locacoes/?status=closed`)
  }

  create(locacao: Locacao): Observable<Locacao> {
    return this.http.post<Locacao>(`${API_CONFIG.baseUrl}/locacoes`, locacao)
  }

  update(locacao: Locacao): Observable<Locacao> {
    return this.http.put<Locacao>(`${API_CONFIG.baseUrl}/locacoes/${locacao.id}`, locacao)
  }

  devolution(locacao: Locacao): Observable<void> {
    return this.http.put<void>(`${API_CONFIG.baseUrl}/locacoes/devolution/${locacao.id}`, locacao)
  }

  delete(locacao: Locacao): Observable<void> {
    return this.http.delete<void>(`${API_CONFIG.baseUrl}/locacoes/${locacao.id}`)
  }
}

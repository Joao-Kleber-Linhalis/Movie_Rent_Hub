import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Item } from '../models/item';
import { API_CONFIG } from '../config/api.config';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor(private http: HttpClient) { }

  findById(id: any):Observable<Item>{
    return this.http.get<Item>(`${API_CONFIG.baseUrl}/itens/${id}`);
  }

  findByIdAndDisponivel(id: any):Observable<Item>{
    return this.http.get<Item>(`${API_CONFIG.baseUrl}/itens/disponivel/${id}`);
  }

  findAll(ativo: Boolean){
    if(ativo){
      return this.http.get<Item[]>(`${API_CONFIG.baseUrl}/itens/?status=ativos`)
    }
    else{
      return this.http.get<Item[]>(`${API_CONFIG.baseUrl}/itens/?status=inativos`)
    }
  }

  

  create(item: Item):Observable<Item> {
    return this.http.post<Item>(`${API_CONFIG.baseUrl}/itens`,item)
  }

  update(item: Item): Observable<Item>{
    return this.http.put<Item>(`${API_CONFIG.baseUrl}/itens/${item.id}`,item)
  }

  changeAtivo(id: any): Observable<Item>{
    return this.http.put<Item>(`${API_CONFIG.baseUrl}/itens/alterar/${id}`, null)
  }
}

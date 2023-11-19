import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { API_CONFIG } from '../config/api.config';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { } 

  findById(id: any): Observable<Usuario> {
    return this.http.get<Usuario>(`${API_CONFIG.baseUrl}/usuarios/${id}`);
  }
  
  findAll() {
    return this.http.get<Usuario[]>(`${API_CONFIG.baseUrl}/usuarios`)
  }


  create(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(`${API_CONFIG.baseUrl}/usuarios`, usuario)
  }

  update(usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${API_CONFIG.baseUrl}/usuarios/${usuario.id}`, usuario)
  }

  delete(usuario: Usuario): Observable<void> {
    return this.http.delete<void>(`${API_CONFIG.baseUrl}/usuarios/${usuario.id}`)
  }
}

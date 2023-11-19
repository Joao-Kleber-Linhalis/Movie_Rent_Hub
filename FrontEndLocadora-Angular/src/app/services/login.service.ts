import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario';
import { API_CONFIG } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient
  ) { }

  login(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(`${API_CONFIG.baseUrl}/usuarios/login`, usuario);
  }

  sucessFullLogin(usuario: Usuario) {
    localStorage.setItem('usuario', JSON.stringify(usuario));
  }

  logout() {
    localStorage.clear();
  }

}

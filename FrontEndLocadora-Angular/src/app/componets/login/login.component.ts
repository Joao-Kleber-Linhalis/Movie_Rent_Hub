import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Usuario } from 'src/app/models/usuario';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent  implements OnInit {

  creds: Usuario = {
    nome: '',
    senha: ''
  }

  nome = new FormControl(null, Validators.minLength(3));
  senha = new FormControl(null, Validators.minLength(3));

  constructor(
    private toast: ToastrService,
    private service: LoginService,
    private router: Router) { }

  ngOnInit(): void {
  }

  logar() {
    this.service.login(this.creds).subscribe(resposta =>{
      this.service.sucessFullLogin(resposta);
      this.router.navigate([''])
    },() => {
      this.toast.error("Usuário e/ou senha inválidos")
    })
  }

  validaCampos(): boolean {
    return this.nome.valid && this.senha.valid
  }

}

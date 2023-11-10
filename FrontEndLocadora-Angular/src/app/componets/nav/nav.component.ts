import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  constructor(private router: Router,
    //private authService: AuthService,
    private toast: ToastrService) {
  }

  ngOnInit(): void {
    this.router.navigate(['dependentes'])
  }

  isMenuOpen: boolean = false;
  controleClientesOpen = false;
  controleAcervoOpen = false;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  toggleControleClientes() {
    this.controleClientesOpen = !this.controleClientesOpen;
    // Feche o Controle de Acervo se estiver aberto
    this.controleAcervoOpen = false;
  }

  toggleControleAcervo() {
    this.controleAcervoOpen = !this.controleAcervoOpen;
    // Feche o Controle de Clientes se estiver aberto
    this.controleClientesOpen = false;
  }

  logout() {
    this.router.navigate(['login']);
    //this.authService.logout();
    this.toast.info("Logout realizado com sucesso","Logout",{timeOut:7000})
}

}

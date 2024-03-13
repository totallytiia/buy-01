import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-navigationbar',
  standalone: true,
  imports: [RouterModule, RouterOutlet, CommonModule],
  templateUrl: './navigationbar.component.html',
  styleUrl: './navigationbar.component.css'
})
export class NavigationbarComponent implements OnInit {

  ngOnInit(): void {
    this.isClient = this.authService.isClient();
    this.isSeller = this.authService.isSeller();
    this.isLoggedIn = this.authService.isAuthenticated();
  }

  authService = inject(AuthService);
  productService = inject(ProductService);
  router = inject(Router);

  isLoggedIn: boolean = false;
  isClient: boolean = false;
  isSeller: boolean = false;

  handleLogin() {
    this.router.navigate(['login']);
  }

  handleMedia() {
    this.router.navigate(['media']);
  }

  handleRegister() {
    this.router.navigate(['register']);
  }

  handleLogout() {
    this.authService.logout();
  }

  handleProfile() {
    this.router.navigate(['profile']);
  }

  handleHome() {
    this.router.navigate(['home']);
  }

  handleProducts() {
    this.productService.handleProducts();
  }

  checkFunction() {
    if (!this.isLoggedIn) {
      // Logic for when user is not logged in
      return true;
    } else {
      // Logic for when user is logged in
      return false;
    }
  }
}


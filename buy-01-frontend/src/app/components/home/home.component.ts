import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../common/user';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { NavigationbarComponent } from '../navigationbar/navigationbar.component';
import { RegisterComponent } from '../register/register.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NavigationbarComponent, RegisterComponent, CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{

  ngOnInit(): void {
    this.isClient = this.authService.isClient();
    this.isSeller = this.authService.isSeller();
    this.isLoggedIn = this.authService.isAuthenticated();
    if (this.isLoggedIn) {
      this.currentUser = this.getCurrentUser();
    }
  }

  currentUser: User = new User();
  router = inject(Router);
  authService = inject(AuthService);
  userService = inject(UserService);

  isLoggedIn: boolean = false;
  isClient: boolean = false;
  isSeller: boolean = false;

  handleRegister(): void {
    this.router.navigate(['/register']);
  }

  handleLogin(): void {
    this.router.navigate(['/login']);
  }

  getCurrentUser(): User {
    return this.userService.getCurrentUser();
  }

}

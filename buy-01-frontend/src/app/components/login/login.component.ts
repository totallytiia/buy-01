import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthRequest } from '../../common/auth-request';
import { AuthService } from '../../services/auth.service';
import { NavigationbarComponent } from '../navigationbar/navigationbar.component';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NavigationbarComponent, FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  router = inject(Router);
  authRequest = new AuthRequest;
  authService = inject(AuthService);

  redirectRegister(): void {
    this.router.navigate(['/register']);
  }

  loginUser(): void {
    this.authService.setAuthRequest(this.authRequest);
    this.authService.login();
  }
}

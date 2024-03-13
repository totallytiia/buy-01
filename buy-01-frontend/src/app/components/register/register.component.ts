import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../../common/user';
import { UserService } from '../../services/user.service';
import { NavigationbarComponent } from '../navigationbar/navigationbar.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [NavigationbarComponent, FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  router = inject(Router);
  userService = inject(UserService);
  user = new User;

  redirectLogin(): void {
    this.router.navigate(['/login']);
  }

  registerUser(): void {
    this.userService.setUser(this.user);
    this.userService.registerUser();
  }


}

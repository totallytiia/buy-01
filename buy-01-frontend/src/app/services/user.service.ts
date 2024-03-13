import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { User } from '../common/user';

var targetURL = 'http://localhost:8080/api/users';

type UserData = {
  name: string | undefined;
  email: string | undefined;
  password: string | undefined;
  avatar?: string;
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }

  user = new User;
  router = inject(Router);
  targetRole = '';

  setUser(user: User): void {
    this.user = user;
  }

  getUser(): User {
    return this.user;
  }

  getCurrentUser(): User {
    var userStr = localStorage.getItem('currentUser');
    if (userStr) {
      var user = JSON.parse(userStr);
    }
    return user;
  }


  async registerUser(): Promise<void> {
    if (this.user.role === 'Seller') {
      this.targetRole = 'seller';
    } else if (this.user.role === 'Client') {
      this.targetRole = 'client';
    }
    const userData: UserData = {
      name: this.user.name,
      email: this.user.email,
      password: this.user.password,
    };
  
    if (this.user.role === 'Seller' && this.user.avatar) {
      userData.avatar = this.user.avatar;
    }

    await axios.post(`${targetURL}/register/${this.targetRole}`, userData)
    .then(() => {
      this.router.navigate(['/login']);
    })
    .catch((error) => {
      if (error.response.status === 409) {
        alert('Email already exists!');
      } else {
      alert('User registration failed!');
      }
    });
  }

}

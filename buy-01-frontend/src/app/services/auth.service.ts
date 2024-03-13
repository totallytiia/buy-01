import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { AuthRequest } from '../common/auth-request';
import { User } from '../common/user';


var targetURL = 'http://localhost:8080/api/users';


export var authInterceptor = axios.interceptors.request.use(
  (config) => {
    var authToken = localStorage.getItem('authToken');
    if (authToken) {
      config.headers.Authorization = authToken;
    }
    return config;
  }
);

export var errorInterceptor = axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      localStorage.clear();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authRequest = new AuthRequest;
  authUser = new User;
  router = inject(Router);

  constructor() { }

  setAuthRequest(tempRequest: AuthRequest): void {
    this.authRequest = tempRequest;
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }


  isClient(): boolean {
    var returnValue = false;
    var role = localStorage.getItem('role');
    if (role === 'Client') {
      returnValue = true;
    }
    return returnValue;
  }

  isSeller(): boolean {
    var returnValue = false;
    var role = localStorage.getItem('role');
    if (role === 'Seller') {
      returnValue = true;
    }
    return returnValue;
  }

  isAuthenticated(): boolean {
    var returnValue = false;
    var authToken = localStorage.getItem('authToken');
    if (authToken) {
      returnValue = true;
    }
    return returnValue;
  }

  async login(): Promise<void> {
    await
    axios.post(`${targetURL}/login`, {
      username: this.authRequest.username,
      password: this.authRequest.password,
      })

    .then((response) => {
     // localStorage.setItem('currentUser', JSON.stringify(this.authRequest));
      this.authUser = response.data;
      var token = this.authUser.authToken;
      var authToken = '';
      if (token) {
        authToken = `Bearer ${token}`;
        localStorage.setItem('authToken', authToken);
      }
      var role = this.authUser.role;
      if (role) {
        localStorage.setItem('role', role);
      }
      localStorage.setItem('currentUser', JSON.stringify(this.authUser));

      this.router.navigate(['/home']);
    })

    .catch(() => {
      alert('Login failed!');
      });

    return;
  }
}

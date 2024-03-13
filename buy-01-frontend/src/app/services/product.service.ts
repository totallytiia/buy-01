import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import axios from 'axios';
import { Product } from '../common/product';
import { AuthService } from './auth.service';
import { UserService } from './user.service';

var targetURL = 'http://localhost:8080/api/products';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor() { }

  router = inject(Router);
  authService = inject(AuthService);
  userService = inject(UserService);
  product = new Product();

  setProduct(product: Product): void {
    this.product = product;
  }

  handleProducts(): void {
    this.router.navigate(['products']);
  }

  collectAllProducts(): Promise<any> {
   // var headers = this.authService.getHeaders();
    return axios.get(`${targetURL}/allProducts`)
  }

  collectProductsByUserId(userId: any): Promise<any> {
  //  var headers = this.authService.getHeaders();
    return axios.get(`${targetURL}/allProducts/${userId}`)
  }

  async storeProduct(): Promise<void> { {
   // var headers = this.authService.getHeaders();
    return await
    axios.post(`${targetURL}/storeProduct`, {
      name: this.product.name,
      description: this.product.description,
      price: this.product.price,
      quantity: this.product.quantity,
    })
    }
  }

  async updateProduct(): Promise<void> {
    return await
    axios.put(`${targetURL}/updateProduct/${this.product.id}`, {
      name: this.product.name,
      description: this.product.description,
      price: this.product.price,
      quantity: this.product.quantity,
    })
  }


  collectProductById(productId: any): Promise<any> {
    // var headers = this.authService.getHeaders();
    return axios.get(`${targetURL}/productDetails/${productId}`)
  }

  redirectProductDetails(id: any): void {
    this.router.navigate([`products/${id}`]);
  }

  async deleteProductById(productId: any): Promise<void> {
    // var headers = this.authService.getHeaders();
    return await axios.delete(`${targetURL}/removeProduct/${productId}`)
  }

  // async storeProduct(): Promise<void> { {
  //   var headers = this.authService.getHeaders();
  //   await
  //   axios.post(`${targetURL}/storeProduct`, {
  //     name: this.product.name,
  //     description: this.product.description,
  //     price: this.product.price,
  //     quantity: this.product.quantity,
  //   }, {headers})
  //   .then(() => {
  //     // update the products list
      

  //     this.router.navigate(['profile']);
  //   })
  //   .catch(() => {
  //     alert('Product registration failed!');
  //     });
  //   }
  }



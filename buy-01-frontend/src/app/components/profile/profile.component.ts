import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Media } from '../../common/media';
import { Product } from '../../common/product';
import { AuthService } from '../../services/auth.service';
import { MediaService } from '../../services/media.service';
import { ProductService } from '../../services/product.service';
import { UserService } from '../../services/user.service';
import { NavigationbarComponent } from '../navigationbar/navigationbar.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NavigationbarComponent, CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  ngOnInit(): void {
    this.isClient = this.authService.isClient();
    this.isSeller = this.authService.isSeller();
    this.isLoggedIn = this.authService.isAuthenticated();
    if (this.isLoggedIn) {
      this.getProductsBySeller();
    }
    this.listAllMedia();
  }

  router = inject(Router);
  mediaService = inject(MediaService);
  authService = inject(AuthService);
  userService = inject(UserService);
  productService = inject(ProductService);
  product = new Product();
  user = this.userService.getCurrentUser();

  isModalOpen: boolean = false;
  products:Product[] = [];
  mediaList: Media[] = [];
  mediaListByProductId: Media[] = [];
  isLoggedIn: boolean = false;
  isClient: boolean = false;
  isSeller: boolean = false;


  toggleModal(): void {
    this.isModalOpen = !this.isModalOpen;
  }

  deleteMedia(id: any): void {
    this.mediaService.deleteMedia(id).then(() => {
      this.listAllMedia();
    }).catch(() => {
      alert('Error deleting media');
    });
  }

  deleteProduct(productId: any): void {
    this.productService.deleteProductById(productId).then(() => {
      this.router.navigate(['profile']);
    }).catch(() => {
      alert('Error deleting product');
    });
  }

  listAllMedia(): void {
    this.mediaService.getAllMedia().then((response) => {
      setTimeout(() => {
      this.mediaList = response.data;
      });
    });
  }

  getMediaByProductId(productId: any): Media[] {
    this.mediaListByProductId = this.mediaList.filter(media => media.productId === productId);
    return this.mediaListByProductId;
  }

  hasMedia(productId: string): boolean {
    return this.mediaList.some(media => media.productId === productId);
  }

  previewMedia(media: Media): String {
    return this.mediaService.previewMedia(media);
  }

  getProductsBySeller(): void {
    var user = this.userService.getCurrentUser();
    this.productService.collectProductsByUserId(user.id).then((response) => {
      setTimeout(() => {
        this.products = [...response.data].reverse();
      });
    });
    }

    handleStoreProduct(): void {
      this.productService.setProduct(this.product);
      this.productService.storeProduct().then(() => {
        this.getProductsBySeller();
        this.router.navigate(['profile']);
      }).catch(() => {
        alert('Error storing product');
      });
    }

    redirectProductDetails(productId: any) {
      this.productService.redirectProductDetails(productId);
      }

}

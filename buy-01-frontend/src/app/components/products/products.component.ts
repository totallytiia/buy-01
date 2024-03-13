import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Media } from '../../common/media';
import { Product } from '../../common/product';
import { AuthService } from '../../services/auth.service';
import { MediaService } from '../../services/media.service';
import { ProductService } from '../../services/product.service';
import { UserService } from '../../services/user.service';
import { NavigationbarComponent } from '../navigationbar/navigationbar.component';


@Component({
  selector: 'app-products',
  standalone: true,
  imports: [NavigationbarComponent, CommonModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit {

  products:Product[] = [];

  productService = inject(ProductService);
  mediaService = inject(MediaService);

  ngOnInit(): void {
    this.listAllProducts();
    this.listAllMedia();
    this.isClient = this.authService.isClient();
    this.isSeller = this.authService.isSeller();
    this.isLoggedIn = this.authService.isAuthenticated();
  }

  router = inject(Router);
  authService = inject(AuthService);
  userService = inject(UserService);

  isLoggedIn: boolean = false;
  isClient: boolean = false;
  isSeller: boolean = false;
  mediaList: Media[] = [];
  mediaListByProductId: Media[] = [];
  currentSlideIndex: number = 0;

  listAllProducts(): void {
    this.productService.collectAllProducts().then((response) => {
      setTimeout(() => {
        this.products = [...response.data].reverse();
        
      });
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

  handleProfile(): void {
    this.router.navigate(['profile']);
  }

  redirectProductDetails(productId: any) {
    this.productService.redirectProductDetails(productId);
    }
  

}

import { Component, OnInit, inject } from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { Media } from '../../common/media';
import { Product } from '../../common/product';
import { MediaService } from '../../services/media.service';
import { ProductService } from '../../services/product.service';
import { UserService } from '../../services/user.service';



@Component({
  selector: 'app-product-details',
  standalone: false,
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.css'
})
export class ProductDetailsComponent implements OnInit {

constructor() { }


isModalOpen: boolean = false;
route = inject(ActivatedRoute);
productService = inject(ProductService);
product = new Product;
router = inject(Router);
mediaList: Media[] = [];
mediaListByProductId: Media[] = [];
productCopy = new Product;
selectedFile: File | null = null;
previewImage: string | ArrayBuffer | null = null;
userService = inject(UserService);
user = this.userService.getCurrentUser();
mediaService = inject(MediaService);
media = new Media();
imageURLs: SafeUrl[] = [];
imageUrl: SafeUrl | undefined;
imageDataUrl: string | undefined;
currentSlide = 0;


ngOnInit(): void {
  this.getProductById(this.route.snapshot.paramMap.get('id'));
  this.listAllMedia();
}

toggleModal(): void {
  this.isModalOpen = !this.isModalOpen;
  this.productCopy = Object.assign({}, this.product);
}

onFileSelected(event: any): void {
  this.selectedFile = event.target.files[0] || null;
  if (this.selectedFile) {
    const reader = new FileReader();
    reader.onload = e => this.previewImage = reader.result;
    reader.readAsDataURL(this.selectedFile);
  }
}

listAllMedia(): void {
  this.mediaService.getAllMedia().then((response) => {
    setTimeout(() => {
    this.mediaList = response.data;
    });
  });
}


  next() {
    if (this.currentSlide < this.getMediaByProductId(this.product.id).length - 1) {
      this.currentSlide++;
    } else {
      this.currentSlide = 0;
    }
  }

  previous() {
    if (this.currentSlide > 0) {
      this.currentSlide--;
    } else {
      this.currentSlide = this.getMediaByProductId(this.product.id).length - 1;
    }
  }

  getMediaByProductId(productId: any): Media[] {
    this.mediaListByProductId = this.mediaList.filter(media => media.productId === productId);
    return this.mediaListByProductId;
  }



handleProducts(): void {
  this.router.navigate(['products']);
}


getProductById(id: any): void {
  this.productService.collectProductById(id).then((response) => {
    setTimeout(() => {
      this.product = response.data;
    });
  });
}

deleteProduct(productId: any): void {
  this.productService.deleteProductById(productId).then(() => {
    this.router.navigate(['profile']);
  }).catch(() => {
    alert('Error deleting product');
  });
}

previewMedia(media: Media): String {
  return this.mediaService.previewMedia(media);
}

handleUpdateProduct(): void {
  Object.assign(this.product, this.productCopy); // Update the original product object
  this.productService.setProduct(this.product);
  this.productService.updateProduct().then(() => {
    this.productService.redirectProductDetails(this.product.id);
    this.isModalOpen = !this.isModalOpen;
  }).catch(() => {
    alert('Error updating product');
  });
}


handleStoreMedia(): void {
  if (this.selectedFile) {
    this.mediaService.storeMedia(this.selectedFile, this.product.id).then(() => {
      this.listAllMedia();
      this.productService.redirectProductDetails(this.product.id);
      this.previewImage = null;
    }).catch(() => {
      alert('Error storing media');
    });
  }
}

}

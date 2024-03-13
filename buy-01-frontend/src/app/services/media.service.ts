import { Injectable, inject } from '@angular/core';
import axios from 'axios';

import { Media } from '../common/media';
import { AuthService } from './auth.service';
import { ProductService } from './product.service';



var targetURL = 'http://localhost:8080/api/media';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  
  authService = inject(AuthService);
  media = new Media();
  productService = inject(ProductService);


  async deleteMedia(id : any): Promise<any> {
    return await axios.delete(`${targetURL}/removeMedia/${id}`);
  }

  async deleteProductById(productId: any): Promise<void> {
    // var headers = this.authService.getHeaders();
    return await axios.delete(`${targetURL}/removeProduct/${productId}`)
  }

  getAllMediaByProductId(productId: any): Promise<any> {
    return axios.get(`${targetURL}/allMedia/${productId}`);
  }
  
  
  getAllMedia(): Promise<any> {
    return axios.get(`${targetURL}/allMedia`);
  }

  setMedia(media: Media): void {
    this.media = media;
  }

  getMediaId(media: Media) : any {
    return this.media.id;
  }


  previewMedia(media: Media): String {
    return `data:image/jpg;base64,${media.data}`;
  }

  async storeMedia(file: File, productId: any): Promise<void> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('productId', productId);

    try {
      await axios.post(`${targetURL}/storeMedia`, formData);
    } catch (error) {
      console.error('Error uploading image:', error);
      throw error;
    }
}

}

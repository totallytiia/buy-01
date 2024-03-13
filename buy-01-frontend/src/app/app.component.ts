import { CommonModule } from '@angular/common';
import { Component, HostBinding, NgModule, effect, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterOutlet } from '@angular/router';
import { NavigationbarComponent } from './components/navigationbar/navigationbar.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';

  

@NgModule({
  declarations: [ProductDetailsComponent],
  imports: [
    BrowserModule,
    NavigationbarComponent, CommonModule, FormsModule],
  exports: [
    BrowserModule,
    NoopAnimationsModule,
    NavigationbarComponent, CommonModule, FormsModule,],
})
export class AppModule {}


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ RouterOutlet,
    CommonModule,],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  title = 'my-angular-buy01-frontend';

  darkMode = signal<boolean>(
    JSON.parse(window.localStorage.getItem('darkMode') ?? 'false')
  );

  @HostBinding('class.dark') get mode() {
    return this.darkMode();
  }

  constructor() {
    effect(() => {
      window.localStorage.setItem('darkMode', JSON.stringify(this.darkMode()));
    });
  }
}

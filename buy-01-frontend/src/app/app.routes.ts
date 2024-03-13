import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component'; // Import the ProductsDetailsComponent
import { ProductsComponent } from './components/products/products.component';
import { ProfileComponent } from './components/profile/profile.component';
import { RegisterComponent } from './components/register/register.component';
import { authGuard } from './guards/auth.guard';
import { unidentifiedGuard } from './guards/unidentified.guard';


export const routes: Routes = [
	{ path: 'home', component: HomeComponent, canActivate: [authGuard] },
	{ path: 'login', component: LoginComponent, canActivate: [unidentifiedGuard]},
	{ path: 'register', component: RegisterComponent, canActivate: [unidentifiedGuard]},
	{ path: 'profile', component: ProfileComponent, canActivate: [authGuard]},
	{ path: 'products', component: ProductsComponent, canActivate: [authGuard]},
	{ path: 'products/:id', component: ProductDetailsComponent, canActivate: [authGuard]},
	{ path: '**', redirectTo: 'home', pathMatch: 'full' }
];


import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {HttpClientModule} from '@angular/common/http'
import { FormsModule } from '@angular/forms';
import { AdminComponent } from './components/admin/admin.component';
import { AdminAddRestaurantComponent } from './components/admin-add-restaurant/admin-add-restaurant.component';
import { AdminAddWaiterComponent } from './components/admin-add-waiter/admin-add-waiter.component';
import { AdminRegistrationRequestsComponent } from './components/admin-registration-requests/admin-registration-requests.component';
import { RegisterComponent } from './components/register/register.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { HomePageUnregistredComponent } from './components/home-page-unregistred/home-page-unregistred.component';
import { AdminLoginComponent } from './components/admin-login/admin-login.component';
import { UpdateUserComponent } from './components/update-user/update-user.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminComponent,
    AdminAddRestaurantComponent,
    AdminAddWaiterComponent,
    AdminRegistrationRequestsComponent,
    RegisterComponent,
    ChangePasswordComponent,
    HomePageUnregistredComponent,
    AdminLoginComponent,
    UpdateUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
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
import { GuestComponent } from './components/guest/guest.component';
import { RestauranGuestComponent } from './components/restauran-guest/restauran-guest.component';
import { CommonModule } from '@angular/common';
import { RestaurantInfoComponent } from './components/restaurant-info/restaurant-info.component';
import { LeafletMapComponent } from './components/leaflet-map/leaflet-map.component';
import { CartComponent } from './components/cart/cart.component';
import { ReservationsComponent } from './components/reservations/reservations/reservations.component';
import { DeliveriesComponent } from './components/deliveries/deliveries.component';
import { WaiterComponent } from './components/waiter/waiter.component';
import { WaiterReservationsComponent } from './components/waiter-reservations/waiter-reservations.component';
import { NgxKonvaModule } from 'ngx-konva';
import { NgChartsModule } from 'ng2-charts';
import { DeliveryComponent } from './components/delivery/delivery.component';
import { LineChartComponent } from './components/line-chart/line-chart.component';


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
    UpdateUserComponent,
    GuestComponent,
    RestauranGuestComponent,
    RestaurantInfoComponent,
    LeafletMapComponent,
    CartComponent,
    ReservationsComponent,
    DeliveriesComponent,
    WaiterComponent,
    WaiterReservationsComponent,
    DeliveryComponent,
    LineChartComponent,
  ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule,
      CommonModule ,
      NgxKonvaModule,
      NgChartsModule
      ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [NO_ERRORS_SCHEMA]  // Add this line
})
export class AppModule { }

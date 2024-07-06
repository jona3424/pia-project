import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
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
import { RestaurantInfoComponent } from './components/restaurant-info/restaurant-info.component';
import { CartComponent } from './components/cart/cart.component';
import { ReservationsComponent } from './components/reservations/reservations/reservations.component';
import { DeliveriesComponent } from './components/deliveries/deliveries.component';
import { WaiterComponent } from './components/waiter/waiter.component';
import { WaiterReservationsComponent } from './components/waiter-reservations/waiter-reservations.component';
import { DeliveryComponent } from './components/delivery/delivery.component';
import { LineChartComponent } from './components/line-chart/line-chart.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'line', component: LineChartComponent},

  {
    path: 'admin',
    component: AdminComponent,
    children: [
      { path: 'registration-requests', component: AdminRegistrationRequestsComponent,children:[
        {path: 'update-user', component:UpdateUserComponent}
      ] },
      { path: 'add-restaurant', component: AdminAddRestaurantComponent },
      { path: 'add-waiter', component: AdminAddWaiterComponent },
      { path: 'login', component: AdminLoginComponent}
    ]
  },
  {path: 'register', component: RegisterComponent},
  {path: 'change-password', component: ChangePasswordComponent},
  {path: 'gost', component: GuestComponent,
  children:[
    {path: 'update-user', component:UpdateUserComponent},
    {path:'cart',component:CartComponent},
    {path:'reservations',component:ReservationsComponent},
    {path:'deliveries',component:DeliveriesComponent},
    {path: 'restaurant-guest', component:RestauranGuestComponent, children:[
      {path: 'restaurant-info', component:RestaurantInfoComponent}
    ]
    }
  ]
  },
  {path: 'konobar', component: WaiterComponent, children
  :[  
    {path: 'update-user', component:UpdateUserComponent},
    {path:'reservations',component:WaiterReservationsComponent},
    {path:'deliveries',component:DeliveryComponent},
  ]
  },
  {path: '', component: HomePageUnregistredComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { Component, OnInit } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { OrderService } from 'src/app/service/Order/order.service';
import { ReservationsService } from 'src/app/service/Reservations/reservations.service';

@Component({
  selector: 'app-delivery',
  templateUrl: './delivery.component.html',
  styleUrls: ['./delivery.component.css']
})
export class DeliveryComponent implements OnInit {
  orders: any[] = [];
  estimatedTimeOptions = ['20-30 minutes', '30-40 minutes', '50-60 minutes'];
  selectedOrderId: number | null = null;
  selectedEstimatedTime: string  = '20-30 minutes';
  currentUser = JSON.parse(localStorage.getItem('user') || '{}');
  restaurantId = JSON.parse(localStorage.getItem('restaurantId') || '{}');
  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  async loadOrders() {
    this.orders = await firstValueFrom(this.orderService.getOrdersForRestaurant(this.restaurantId.restaurantId));
    console.log(this.orders);
  }

  async updateOrderStatus(orderId: number, status: string) {
    const estimatedTime = status === 'Confirmed' ? this.selectedEstimatedTime : '20-30 minutes';
    await firstValueFrom(this.orderService.changeOrderStatus(orderId, status, estimatedTime));
    this.loadOrders();
    this.selectOrder(orderId);
  }

  selectOrder(orderId: number) {
    if (this.selectedOrderId === orderId) {
      this.selectedOrderId = null;
    }else { 
    this.selectedOrderId = orderId;
    }
  }

  setEstimatedTime(time: string) {
    this.selectedEstimatedTime = time;
  }
}

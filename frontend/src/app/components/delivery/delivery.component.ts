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
  selectedEstimatedTime: string | null = null;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  async loadOrders() {
    // this.orders = await firstValueFrom(this.orderService.getAllOrders());
  }

  async updateOrderStatus(orderId: number, status: string) {
    const estimatedTime = status === 'Confirmed' ? this.selectedEstimatedTime : null;
    // await firstValueFrom(this.orderService.updateOrderStatus(orderId, status, estimatedTime));
    this.loadOrders();
  }

  selectOrder(orderId: number) {
    this.selectedOrderId = orderId;
  }

  setEstimatedTime(time: string) {
    this.selectedEstimatedTime = time;
  }
}

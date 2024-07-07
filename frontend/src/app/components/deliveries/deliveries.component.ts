import { Component, OnInit } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { OrderService } from 'src/app/service/Order/order.service';

@Component({
  selector: 'app-deliveries',
  templateUrl: './deliveries.component.html',
  styleUrls: ['./deliveries.component.css']
})
export class DeliveriesComponent implements OnInit {
  currentDeliveries: any[] = [];
  archivedDeliveries: any[] = [];
  userId: number = 0;

  constructor(private deliveryService: OrderService) { }

  ngOnInit(): void {
    const user = JSON.parse(localStorage.getItem('user')!);
    this.userId = user.userId;
    this.loadCurrentDeliveries();
    this.loadArchivedDeliveries();
  }

 async loadCurrentDeliveries() {
   this.currentDeliveries=await firstValueFrom(this.deliveryService.getCurrentDeliveries(this.userId));
  }

  async loadArchivedDeliveries() {
    this.archivedDeliveries=await firstValueFrom(this.deliveryService.getArchivedDeliveries(this.userId));
  }

  formatEstimatedTime(estimatedTime: any): string {
    const timeParts = estimatedTime.split(':');
    const minutes = parseInt(timeParts[1]);
    switch (minutes) {
      case 30:
        return '20-30 minutes';
      case 40:
        return '30-40 minutes';
      case 50:
        return '50-60 minutes';
      default:
        return 'N/A';
    }
  }
}

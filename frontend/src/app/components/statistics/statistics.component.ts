import { Component, OnInit } from '@angular/core';
import { ReservationsService } from 'src/app/service/Reservations/reservations.service';
import { firstValueFrom } from 'rxjs';
import { ChartType } from 'chart.js';
@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {
  chartlabels1: string[] = [];
  data1: number[] = [];
  label1: string = '';
  chartType1: ChartType='bar';
  currentUser = JSON.parse(localStorage.getItem('user') || '{}');
  restaurantId = JSON.parse(localStorage.getItem('restaurantId') || '{}');


  chartlabels2: string[] = [];
  data2: number[] = [];
  label2: string = '';
  chartType2: ChartType='pie';

  chartlabels3: string[] = [];
  data3: number[] = [];
  label3: string = 'Average Reservations Per Day';
  chartType3: ChartType = 'bar';

  constructor(private reservationService: ReservationsService) { }

  async ngOnInit() {
    let res = await firstValueFrom(this.reservationService.getWaiterStatisticsPerDay(this.currentUser.userId));
    this.label1 = 'guests per day';
    this.chartlabels1 = res.map(a => a.day);
    this.data1 = res.map(a => a.broj);

    let res1 = await firstValueFrom(this.reservationService.getTotalGuestsPerWaiterInRestaurant(this.restaurantId.restaurantId));
    this.label2 = 'guests per waiter';
    this.chartlabels2 = res1.map(a => 'id: ' +a.waiter + ' ' + a.waiterName);
    this.data2 = res1.map(a => a.totalGuests);

    let res2 = await firstValueFrom(this.reservationService.getAverageReservationsPerDay(this.restaurantId.restaurantId));
    this.chartlabels3 = res2.map(a => a.day);
    this.data3 = res2.map(a => a.averageReservations);
  }
}

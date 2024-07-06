import { Component, OnInit, AfterViewInit } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { ReservationsService } from 'src/app/service/Reservations/reservations.service';

@Component({
  selector: 'app-waiter-reservations',
  templateUrl: './waiter-reservations.component.html',
  styleUrls: ['./waiter-reservations.component.css']
})
export class WaiterReservationsComponent implements OnInit, AfterViewInit {
  unprocessedReservations: any[] = [];
  selectedReservation: any = null;
  tableId: number = 0;
  comment: string = '';
  allTables: any[] = []; // Ensure this is declared
  tables: any[] = [];
  currentUser = JSON.parse(localStorage.getItem('user') || '{}');
  restaurantId = JSON.parse(localStorage.getItem('restaurantId') || '{}');
  canvas: any;
  ctx: any;
  waiterReservations: any[] = [];
  ReturnMsgUpdateStatus="";
  constructor(private reservationService: ReservationsService) {}

  ngOnInit(): void {
    this.loadUnprocessedReservations();
    this.loadWaiterReservations();
  }

  ngAfterViewInit(): void {
    this.canvas = document.getElementById('tableCanvas');
    this.ctx = this.canvas.getContext('2d');
    this.canvas.addEventListener('click', this.changeColorOnClick.bind(this));
  }

  async loadUnprocessedReservations() {
    this.unprocessedReservations = await firstValueFrom(this.reservationService.getUnprocessedReservations(this.restaurantId));
  }
  async loadWaiterReservations() {
    this.waiterReservations = await firstValueFrom(this.reservationService.getWaiterReservations(this.currentUser.userId));
    console.log(this.waiterReservations);
  }

  isReservationStatusChangable(reservation:any):boolean{
    let datPlus30Minutes = new Date(reservation.reservationDate);
    datPlus30Minutes.setMinutes(datPlus30Minutes.getMinutes() + 30);
    const now = new Date();
    return now > datPlus30Minutes;

  }
  async updateReservationStatus(reservation:any, status:string){
    let datPlus30Minutes = new Date(reservation.reservationDate);
    datPlus30Minutes.setMinutes(datPlus30Minutes.getMinutes() + 30);
    const now = new Date();
    if(now < datPlus30Minutes){
      this.ReturnMsgUpdateStatus="You can only change the status of a reservation 30 minutes after the reservation time";
      return;
    }
    else{
    let res= await firstValueFrom(this.reservationService.updateReservationStatus(reservation.reservationId, status));
    this.loadWaiterReservations();
    this.ReturnMsgUpdateStatus=res;

    setTimeout(() => {
      this.ReturnMsgUpdateStatus = "";
    }, 2500);
    }
  }


  async confirmReservation(reservationId: number) {
    const request = {
      reservationId: reservationId,
      tableId: this.tableId,
      waiterId: this.currentUser.userId
    };

    await firstValueFrom(this.reservationService.confirmReservation(request));
    this.loadUnprocessedReservations();
    this.toggleOrSelect(this.selectedReservation);
  }

  async rejectReservation(reservationId: number) {
    const request = {
      reservationId: reservationId,
      comment: this.comment
    };

    await firstValueFrom(this.reservationService.rejectReservation(request));
    this.loadUnprocessedReservations();
    this.toggleOrSelect(this.selectedReservation);
  }

  async toggleOrSelect(reservation: any) {
    this.tables = []; // Clear the canvas
    if (this.selectedReservation === reservation) {
      this.selectedReservation = null;
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height); 
    } else {
      this.selectedReservation = reservation;
      await this.loadAllTables(this.restaurantId.restaurantId, reservation.reservationDate, reservation.numberOfGuests);
    }
  }

  async loadAllTables(restaurantId: number, reservationDate: Date, numberOfGuests: number) {
    this.allTables = await firstValueFrom(this.reservationService.getAllTables(restaurantId, reservationDate, numberOfGuests));
    console.log(this.allTables);
    this.updateCanvasWidth();
    this.drawTables();
  }

  updateCanvasWidth() {
    const minWidth = this.allTables.length * 140; // Adjust based on your table spacing
    this.canvas.width = Math.max(minWidth, 400); // Ensure minimum width
  }

  drawTables() {
    this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height); // Clear the canvas
    if (this.tables.length != 0) {
      console.log(this.tables);
      this.tables.forEach(table => this.drawCircle(table));
    }
    else{
      this.tables = this.allTables.map((table, index) => ({
        id: table.tableId,
        capacity: table.capacity,
        x: 70 + index * 140, // Adjust as necessary
        y: 200, // Adjust as necessary
        radius: 60,
        fill: table.unavailable ? 'red' : (table.overCapacity ? 'yellow' : 'green'),
        stroke: 'black',
        clickable: table.available
      }));
      this.tables.forEach(table => this.drawCircle(table));
    }
  }

  drawCircle(table: any) {
    this.ctx.beginPath();
    this.ctx.arc(table.x, table.y, table.radius, 0, Math.PI * 2, false);
    this.ctx.fillStyle = table.fill;
    this.ctx.fill();
    this.ctx.strokeStyle = table.stroke;
    this.ctx.stroke();
    this.ctx.closePath();

    // Draw the table ID
    this.ctx.fillStyle = 'black';
    this.ctx.font = '12px Arial';
    this.ctx.textAlign = 'center';
    this.ctx.textBaseline = 'middle';
    this.ctx.fillText(`ID: ${table.id}`, table.x, table.y - 10);
    this.ctx.fillText(`Cap: ${table.capacity}`, table.x, table.y + 10);
  }

  changeColorOnClick(event: any) {
    const rect = this.canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    this.tables.forEach(table => {
      if (Math.sqrt((x - table.x) ** 2 + (y - table.y) ** 2) < table.radius && table.clickable) {
        this.tableId = table.id;
        this.onTableSelect();
      }
    });

    this.drawTables();
  }

  onTableSelect() {
    this.tables.forEach(table => {
      if (table.id === this.tableId) {
        table.fill = 'red';
      } 
      else{
        for (let i = 0; i < this.allTables.length; i++) {
          if (this.allTables[i].tableId === table.id) {
            table.fill = this.allTables[i].unavailable ? 'red' : (this.allTables[i].overCapacity ? 'yellow' : 'green');
          }
        }
      }
    });
    console.log(this.tables);
    this.drawTables();
  }
}

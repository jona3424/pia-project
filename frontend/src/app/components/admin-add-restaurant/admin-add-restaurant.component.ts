import { Component, OnInit, AfterViewInit } from '@angular/core';
import { RestaurantService } from 'src/app/service/Restaurant/restaurant.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-admin-add-restaurant',
  templateUrl: './admin-add-restaurant.component.html',
  styleUrls: ['./admin-add-restaurant.component.css']
})
export class AdminAddRestaurantComponent implements OnInit, AfterViewInit {
  restaurant = {
    name: '',
    type: '',
    address: '',
    description: '',
    contactPerson: '',
    openingTime: '' || null,
    closingTime: '' || null,
    layoutJson: '',
    phoneNumber: '',
  };
  selectedFile: File | null = null;
  restaurants: any[] = [];
  selectedRestaurant: any = null;
  canvas: any;
  ctx: any;

  restaurantTypes = ['Chinese', 'Indian', 'Japanese', 'Local Cuisine'];

  constructor(private restaurantService: RestaurantService) { }

  ngOnInit(): void {
    this.loadRestaurants();
  }

  ngAfterViewInit(): void {
    this.canvas = document.getElementById('restaurantCanvas');
    this.ctx = this.canvas.getContext('2d');
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  async onSubmit(): Promise<void> {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = async (event: any) => {
        this.restaurant.layoutJson = event.target.result;
        try {
          const response = await firstValueFrom(this.restaurantService.addRestaurant(this.restaurant));
          console.log('Restaurant added successfully', response);
          this.addTablesFromJson(response['restaurantId']);
          this.resetForm();
          this.loadRestaurants(); // Reload the list after adding a restaurant
        } catch (error) {
          console.error('Error adding restaurant', error);
        }
      };
      reader.readAsText(this.selectedFile);
    } else {
      try {
        const response = await firstValueFrom(this.restaurantService.addRestaurant(this.restaurant));
        console.log('Restaurant added successfully', response);
        this.resetForm();
        this.loadRestaurants(); // Reload the list after adding a restaurant
      } catch (error) {
        console.error('Error adding restaurant', error);
      }
    }
  }

  resetForm(): void {
    this.restaurant = {
      name: '',
      type: '',
      address: '',
      description: '',
      contactPerson: '',
      openingTime: '' || null,
      closingTime: '' || null,
      layoutJson: '',
      phoneNumber: '',
    };
    this.selectedFile = null;
  }

  async loadRestaurants() {
    this.restaurants = await firstValueFrom(this.restaurantService.getAllRestaurants());
  }

  selectRestaurant(restaurant: any): void {
    if (this.selectedRestaurant==restaurant) {
      this.selectedRestaurant = null;
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height); // Clear the canvas
    }
    else{
      this.selectedRestaurant = restaurant;
      this.drawLayout();
    }


  }

  async updateWorkingHours(): Promise<void> {
    if (this.selectedRestaurant) {
      this.selectedRestaurant.openingTime = this.restaurant.openingTime;
      this.selectedRestaurant.closingTime = this.restaurant.closingTime;
      this.restaurant.openingTime = null;
      this.restaurant.closingTime = null;
      console.log(this.selectedRestaurant.openingTime, this.selectedRestaurant.closingTime);
      try {
        await firstValueFrom(this.restaurantService.updateRestaurant(this.selectedRestaurant.restaurantId, this.selectedRestaurant));
        console.log('Working hours updated successfully');
      } catch (error) {
        console.error('Error updating working hours', error);
      }
    }
  }

  drawLayout(): void {
    if (this.selectedRestaurant && this.selectedRestaurant.layoutJson) {
      const layout = JSON.parse(this.selectedRestaurant.layoutJson);
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height); // Clear the canvas

      // Draw tables
      layout.tables.forEach((table: any) => {
        this.drawCircle(table.x, table.y, table.radius, 'green', 'black', `Table ${table.id}\nCapacity: ${table.capacity}`);
      });

      // Draw kitchen
      this.drawRect(layout.kitchen.x, layout.kitchen.y, layout.kitchen.width, layout.kitchen.height, 'blue', 'Kitchen');

      // Draw toilet
      this.drawRect(layout.toilet.x, layout.toilet.y, layout.toilet.width, layout.toilet.height, 'yellow', 'Toilet');
    }
  }

  drawCircle(x: number, y: number, radius: number, fillColor: string, strokeColor: string, text: string): void {
    this.ctx.beginPath();
    this.ctx.arc(x, y, radius, 0, Math.PI * 2, false);
    this.ctx.fillStyle = fillColor;
    this.ctx.fill();
    this.ctx.strokeStyle = strokeColor;
    this.ctx.stroke();
    this.ctx.closePath();

    this.ctx.fillStyle = 'black';
    this.ctx.font = '12px Arial';
    this.ctx.textAlign = 'center';
    this.ctx.textBaseline = 'middle';
    const lines = text.split('\n');
    lines.forEach((line, i) => {
      this.ctx.fillText(line, x, y - (lines.length - 1 - i) * 10);
    });
  }

  drawRect(x: number, y: number, width: number, height: number, fillColor: string, text: string): void {
    this.ctx.fillStyle = fillColor;
    this.ctx.fillRect(x, y, width, height);

    this.ctx.strokeStyle = 'black';
    this.ctx.strokeRect(x, y, width, height);

    this.ctx.fillStyle = 'black';
    this.ctx.font = '12px Arial';
    this.ctx.textAlign = 'center';
    this.ctx.textBaseline = 'middle';
    this.ctx.fillText(text, x + width / 2, y + height / 2);
  }

  addTablesFromJson(restaurantId: number): void {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = async (event: any) => {
        const layout = JSON.parse(event.target.result);
        for (const table of layout.tables) {
          const tableData = {
            restaurantId: restaurantId,
            maxSeats: table.capacity,
            tableShape: 'circle'
          };
          try {
            await firstValueFrom(this.restaurantService.addTable(tableData));
            console.log('Table added successfully');
          } catch (error) {
            console.error('Error adding table', error);
          }
        }
      };
      reader.readAsText(this.selectedFile);
    }
  }
}

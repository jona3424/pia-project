import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/entities/User';
import { DataSharingService } from 'src/app/service/Datasharing/data-sharing.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  activeButton: string = 'login';
  private dataSubscription!: Subscription;

  user:any =null;
  constructor(private router: Router,private dataSharingService: DataSharingService) {}
  ngOnInit(): void {
    this.router.navigate([`/admin/${this.activeButton}`]);
    this.dataSubscription=this.dataSharingService.currentData.subscribe(data => {
      this.user = data;
        this.dataSubscription.unsubscribe();
        this.router.navigate(['/admin/registration-requests']);
    }
    );

  }

  selectComponent(component: string): void {
    if (this.activeButton === component) {
      this.activeButton = '';
      this.router.navigate(['/admin']);
    } else {
      this.activeButton = component;
      this.router.navigate([`/admin/${component}`]);
    }
  }
}

import { Component } from '@angular/core';
import { UserService } from '../../service/User/user.service';
import { firstValueFrom } from 'rxjs';
import { User } from 'src/app/entities/User';
import { Router } from '@angular/router';
@Component({
  selector: 'app-admin-registration-requests',
  templateUrl: './admin-registration-requests.component.html',
  styleUrls: ['./admin-registration-requests.component.css']
})
export class AdminRegistrationRequestsComponent {
  registrationRequests: Array<User> = [];
  existingUsers: Array<User> = [];
  activeButton: string = '';
  constructor(private adminService: UserService,private router:Router) { }

  ngOnInit(): void {
    this.loadRegistrationRequests();
    this.loadExistingUsers();
  }

  async loadRegistrationRequests()  {
    this.registrationRequests= await firstValueFrom(this.adminService.getLoginRequests());
      
  }

  async loadExistingUsers()  {
    this.existingUsers= await firstValueFrom(this.adminService.getExsistingUsers('gost'));  
  }


  async approveRequest(id: Number){
      await firstValueFrom(this.adminService.approveRequest(id));
      alert("Request approved successfully");
      this.ngOnInit();
  }

  async rejectRequest(id: Number) {
    await firstValueFrom(this.adminService.rejectRequest(id));
    this.loadRegistrationRequests();
    alert("Request rejected successfully");
    
  }

  async blockUser(id: Number) {

    let response= await firstValueFrom(this.adminService.blockUser(id));

    if(response=="User blocked successfully"){
      alert("User blocked successfully");
      this.ngOnInit();
    }
    else{
      alert("User is already blocked");
    }
  }
  selectComponent(component: string,userId:Number): void {
    if (this.activeButton === component) {
      this.activeButton = '';
      this.router.navigate(['/admin/registration-requests']);
    } else {
      this.activeButton = component;
      this.router.navigate([`admin/registration-requests/${component}`,{ data: userId }]);
    }
  }
}

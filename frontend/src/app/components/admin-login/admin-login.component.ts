import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { DataSharingService } from 'src/app/service/Datasharing/data-sharing.service';
import { UserService } from 'src/app/service/User/user.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent {
  constructor(private servis: UserService,
    private router: Router,private dataSharingService: DataSharingService){}

  username: string = ""
  password: string = ""

  async login(){
    try{
      const res = await firstValueFrom(this.servis.login(this.username, this.password))
      if(res){
        if(res.role.toLocaleLowerCase() == "admin"){
          this.dataSharingService.changeData(res); }
        else
          alert("You are not an admin. You need to use different interface.")
      }
      else{
        alert("Login failed. Please check your username and password.")
      }
    }
    catch (err: any) {
      if (err.status === 404 && err.error.message) {
          alert(err.error.message);
      } else {
          alert("An unexpected error occurred. Please try again later.");
      }
    }
  }
}

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { User } from 'src/app/entities/User';
import { UserService } from 'src/app/service/User/user.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private servis: UserService,
    private router: Router){}

  username: string = ""
  password: string = ""

  async login(){
    try{
      const res = await firstValueFrom(this.servis.login(this.username, this.password))
      if(res){
        alert("Login successful" 
        + "\n" + "Welcome " + res.firstName+ "!"
        )
        if(res.role.toLocaleLowerCase() == "admin"){
          alert("You are an admin. You need to use different interface.")
        }
        else{
          this.router.navigate(["/" + res.role.toLowerCase()])
          localStorage.setItem("user", JSON.stringify(res))
        }
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

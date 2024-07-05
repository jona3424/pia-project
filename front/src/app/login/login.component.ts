import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

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

  login(){
    this.servis.login(this.username, this.password).subscribe(
      data=>{
        if(data==null) alert("Nema korisnika")
        else {
          localStorage.setItem("logged", data.username)
          this.router.navigate(['books'])
        }
      }
    )
  }
}

import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { User } from '../models/user';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(private servis: UserService){}

  user: User = new User()

  register(){
    this.servis.register(this.user).subscribe(
      data=>{
        if(data.message=="ok") alert("Dodato")
      }
    )
  }
}

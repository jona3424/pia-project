import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { UserService } from 'src/app/service/User/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  step = 1;
  oldPassword: string='';
  newPassword: string='';
  confirmNewPassword: string='';
  username: string='';
  securityQuestion: String='';
  securityAnswer: string='';
  constructor(private changePasswordService: UserService, private router: Router) {}


  async onSubmitOldPassword() {
    if (this.username==='' || this.oldPassword === '' || this.newPassword === '' || this.confirmNewPassword === '') {
      alert('Please fill in all fields');
      return;
    }

    if (this.newPassword !== this.confirmNewPassword) {
      alert('New passwords do not match');
      return;
    }
    
    const passwordRegex = /^(?=.*[a-z]{3})(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,10}$/;
    if (!passwordRegex.test(this.newPassword)) {
      alert('Password must be between 6 and 10 characters long, start with a letter, contain at least 3 lowercase letters, 1 uppercase letter, 1 number, and 1 special character.');
      return;
    }

    let response=await firstValueFrom(this.changePasswordService.changePasswordWithOldPassword(this.oldPassword, this.newPassword,this.username))
    if(response==='Password changed successfully'){
      alert(response);
      this.router.navigate(['/login']);
    }
    else{
      alert(response);
    }    
  }

  async onSubmitUsername() {

    if (this.username === '') {
      alert('Please fill in username');
      return;
    }
    let response=await firstValueFrom(this.changePasswordService.getSecurityQuestion(this.username));
    if(response==='User not found'){
      alert(response);
    }else{
      this.securityQuestion = response;
      this.step = 3;
    }

  }

  async onSubmitSecurityAnswer() {
    if (this.securityAnswer === '') {
      alert('Please fill in security answer');
      return;
    }
    let response=await firstValueFrom(this.changePasswordService.verifySecurityAnswer(this.username, this.securityAnswer));
    if(response==='Security answer is correct'){
      this.step = 4;
    }
    else{
      alert(response);
    }
  }

  async onSubmitNewPassword() {
    if (this.newPassword === '' || this.confirmNewPassword === '') {
      alert('Please fill in all fields');
      return;
    }
    if (this.newPassword !== this.confirmNewPassword) {
      alert('New passwords do not match');
      return;
    }
    const passwordRegex = /^(?=.*[a-z]{3})(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,10}$/;
    if (!passwordRegex.test(this.newPassword)) {
      alert('Password must be between 6 and 10 characters long, start with a letter, contain at least 3 lowercase letters, 1 uppercase letter, 1 number, and 1 special character.');
      return;
    }
    let response=await firstValueFrom(this.changePasswordService.changePasswordWithSecurityAnswer(this.username, this.newPassword));
    if(response==='Password changed successfully'){
      alert(response);
      this.router.navigate(['/login']);
    }
    else{
      alert(response);
    }
    
  }
}

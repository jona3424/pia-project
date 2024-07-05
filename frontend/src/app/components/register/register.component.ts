import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { first, firstValueFrom } from 'rxjs';
import { User } from 'src/app/entities/User';
import { UserService } from 'src/app/service/User/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: any = {
    username: '',
    password: '',
    confirmPassword: '',
    securityQuestion: '',
    securityAnswer: '',
    firstName: '',
    lastName: '',
    gender: '',
    address: '',
    phone: '',
    email: '',
    profilePictureBase64: null,
    creditCard: ''
  };
  imageError: string | null = null;
  profilePictureInvalid: boolean = false; // Allow registration without profile picture
  maxFileSize = 2 * 1024 * 1024; // 2MB

  constructor(private registerService: UserService, private router: Router) {}

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];

      // Provera veličine fajla
      if (file.size > this.maxFileSize) {
        this.imageError = 'Image size must not exceed 2MB.';
        this.profilePictureInvalid = true;
        return;
      }

      const img = new Image();
      img.src = URL.createObjectURL(file);

      img.onload = () => {
        const width = img.width;
        const height = img.height;

        if (width < 100 || height < 100) {
          this.imageError = 'Image dimensions must be at least 100x100 pixels.';
          this.profilePictureInvalid = true;
        } else if (width > 300 || height > 300) {
          this.imageError = 'Image dimensions must not exceed 300x300 pixels.';
          this.profilePictureInvalid = true;
        } else {
          this.imageError = null;
          this.profilePictureInvalid = false;

          const reader = new FileReader();
          reader.onload = () => {
            this.user.profilePicture = (reader.result as string).split(',')[1]; // Remove the base64 prefix
          };
          reader.readAsDataURL(file);
        }
      };
    } else {
      this.profilePictureInvalid = false;
      this.user.profilePicture = null; // Ensure profilePicture is null if no file is selected
    }
  }

 async onSubmit() {
    if (this.user.password !== this.user.confirmPassword) {
      alert('Passwords do not match');
      return;
    }
    
    const passwordRegex = /^(?=.*[a-z]{3})(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,10}$/;
    if (!passwordRegex.test(this.user.password)) {
      alert('Password must be between 6 and 10 characters long, start with a letter, contain at least 3 lowercase letters, 1 uppercase letter, 1 number, and 1 special character.');
      return;
    }


    if (this.imageError) {
      alert(this.imageError);
      return;
    }
    let response=await firstValueFrom(this.registerService.register(this.user));
    if(response){
      alert('Registration successful');
      this.router.navigate(['/login']);
    }
    else{
      alert('Registration failed');
    }
  }
}

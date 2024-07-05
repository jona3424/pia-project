import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { User } from 'src/app/entities/User';
import { UserService } from 'src/app/service/User/user.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent {
  user: any = {
    username: '',
    password: '',
    securityQuestion: '',
    securityAnswer: '',
    firstName: '',
    lastName: '',
    gender: '',
    address: '',
    phone: '',
    email: '',
    profilePicture: null,
    profilePictureBase64: null,
    creditCard: ''
  };
  userId: any;
  imageError: string | null = null;
  profilePictureInvalid: boolean = false;
  maxFileSize = 2 * 1024 * 1024; // 2MB

  constructor(private userService: UserService, private route: ActivatedRoute, private router: Router) {}

  async ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('data');
    if (this.userId != null) {
      this.user = await firstValueFrom(this.userService.findbyId(parseInt(this.userId)));
     
      console.log(this.user);
    }
  }

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
            this.user.profilePictureBase64 = (reader.result as string).split(',')[1]; // Remove the base64 prefix
          };
          reader.readAsDataURL(file);
        }
      };
    } 
  }

  async onSubmit() {
    let res = await firstValueFrom(this.userService.updateUser(this.user, parseInt(this.userId))) as User;
    if (res != null) {
      this.router.navigate(['/admin/registration-requests']);
    } else {
      alert("Error updating user!");
    }
  }
}
// Purpose: Model for User entity.
export class User {
    userId: number = 0;
    username: string = "";
    password: string = "";
    securityQuestion: string = "";
    securityAnswer: string = "";
    firstName: string  = "";
    lastName: string = "";
    gender: string = "";
    address: string = "";
    phoneNumber: string = "";
    email: string = "";
    profilePicture: string = "";
    creditCardNumber: string = "";
    role: string = "";
    isActive: boolean = false;
    createdAt: Date = new Date();

}
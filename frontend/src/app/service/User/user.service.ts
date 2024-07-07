import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../../entities/User';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string){
    const data={
      username: username,
      password: password
    }
    console.log(data)
    return this.http.post<User>("http://localhost:8080/users/login", data)
  }

  register(data:any){
    return this.http.post<User>("http://localhost:8080/users/register", data)
  }

  getLoginRequests(){
    return this.http.get<Array<User>>("http://localhost:8080/users/login-requests")
  }

  approveRequest(id: Number){
    return this.http.post<User>(`http://localhost:8080/users/approve-login-request/${id}`,null)
  }

  rejectRequest(id: Number){
    return this.http.post<User>(`http://localhost:8080/users/reject-login-request/${id}`,null)
  }


  changePasswordWithOldPassword(oldPassword: string, newPassword: string,username: string)  {
    return this.http.post<String>(`http://localhost:8080/users/with-old-password`, { oldPassword, newPassword, username},{responseType: 'text' as 'json'});
  }

  getSecurityQuestion(username: string) {
    return this.http.get<String>(`http://localhost:8080/users/security-question/${username}`,{responseType: 'text' as 'json'});
  }

  verifySecurityAnswer(username: string, securityAnswer: string) {
    return this.http.post<String>(`http://localhost:8080/users/verify-security-answer`, { username, securityAnswer },{responseType: 'text' as 'json'});
  }

  changePasswordWithSecurityAnswer(username: string, newPassword: string) {
    return this.http.post<String>(`http://localhost:8080/users/with-security-answer`, { username, newPassword },{responseType: 'text' as 'json'});
  }

  getExsistingUsers(role: String){
    return this.http.get<Array<User>>(`http://localhost:8080/users/existing-users/${role}`)
  }

  blockUser(id: Number){
    return this.http.post<String>(`http://localhost:8080/users/block-user/${id}`,null ,{responseType: 'text' as 'json'})
  }

  getNumberOfGuests(){
    return this.http.get<Number>(`http://localhost:8080/users/number-of-guests`)
  }
  findbyId(id: Number){
    return this.http.get<any>(`http://localhost:8080/users/${id}`)
  }
  updateUser(user: any,id: Number){
    return this.http.post<User>(`http://localhost:8080/users/update/${id}`,user)
  }
  createWaiter(data: any){
    console.log(data)
    return this.http.post<User>("http://localhost:8080/users/create-waiter", data)
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user';
import { Message } from '../models/message';
import { Book } from '../models/book';


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
    return this.http.post<User>("http://localhost:4000/users/login", data)
  }

  register(user: User){
    return this.http.post<Message>("http://localhost:4000/users/register",
     user)
  }

  addToFavourites(user: string, book: Book){
    return this.http.post<Message>("http://localhost:4000/users/addToFavourites",
    {user: user, name: book.name, author: book.author});
  }


  changeFavourite(user: string, bookname: string){
    return this.http.post<Message>(
      "http://localhost:4000/users/changeFavourite",
    {user: user, bookname: bookname});
  }

  getUserByUsername(username: string){
    return this.http.get<User>(
      `http://localhost:4000/users/getUserByUsername/${username}`)
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../models/book';
import { Message } from '../models/message';


@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient) { }

  getAll(){
    return this.http.
    get<Book[]>("http://localhost:4000/books/getAll");
  }

  deleteBook(book: Book){
    return this.http.
    post<Message>("http://localhost:4000/books/deleteBook",
    {name: book.name});

  }

  updateBook(name: string, pages: number){
    return this.http.
    post<Message>("http://localhost:4000/books/updateBook",
    {name: name, pages: pages});

  }
}

import { Component, OnInit } from '@angular/core';
import { Book } from '../models/book';
import { BookService } from '../services/book.service';
import { UserService } from '../services/user.service';
import { User } from '../models/user';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {
  allBooks: Book[]=[]
  constructor(private bookService: BookService,
    private userService: UserService){}
  ngOnInit(): void {
    this.bookService.getAll().subscribe(
      data=>this.allBooks = data
    )

    let logged = localStorage.getItem("logged")
    if(logged){
      this.userService.getUserByUsername(logged).subscribe(user=>{
        this.loggedUser = user;
        this.loggedUser.favourites.forEach(fav=>{
          fav.date = new Date(fav.date)
        })
      })
    }

  }

  deleteFav(bookname: string){
    this.userService.changeFavourite(this.loggedUser.username,
      bookname).subscribe(msg=>{
        alert(msg.message)
      })
  }

  loggedUser: User = new User()

  deleteBook(book: Book){
    this.bookService.deleteBook(book).subscribe(
      data=>{
        alert(data.message)
        this.bookService.getAll().subscribe(
          data=>this.allBooks = data
        )
      }
    )
  }

  name: string = "";
  pages: number = 0;

  updateBook(){
    this.bookService.updateBook(this.name, this.pages).subscribe(
      data=>{
        alert(data.message)
      }
    )
  }

  message: string = '';

  addToFavs(book: Book){
    let user = localStorage.getItem("logged");
    if(user==null) return;

    else{
      this.userService.addToFavourites(user, book).subscribe(
        data=>{
          this.message = data.message;
        }
      );
    }

  }


}

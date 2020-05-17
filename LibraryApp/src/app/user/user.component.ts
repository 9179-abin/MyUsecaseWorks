import { Component, OnInit } from '@angular/core';
import { Books } from '../models/books';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { UserBooks } from '../models/userbooks';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  usertype:string;
  userid:number;
  books:Books[];
  userbooks:UserBooks[];
  username : string;
  constructor(private userService: UserService, private router:Router) { }
  placeOrder(book:Books){
    this.userService.placeOrder(this.userid,book.bookid).subscribe(data=>{
      if(data!=null){
        console.log(data);
        alert("Order Placed");
      }
    })
  }
  return(book:UserBooks){
    this.userService.returnBooks(book.tableid).subscribe(data=>{
      if(data!=null){
        alert("Return is Issued");
        window.location.reload();
        
      }
    })
  }
  ngOnInit() {
    this.usertype = sessionStorage.getItem("usertype");
    this.username = sessionStorage.getItem("username");
    this.userid = +sessionStorage.getItem("userid");
    this.userService.getBooks().subscribe(data=>{
      this.books =data;
    });
    this.userService.showUserBooks(this.userid).subscribe(data=>{
      this.userbooks = data;
    })
  }

}

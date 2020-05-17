import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from '../services/admin.service';
import { Users } from '../models/users';
import { Books } from '../models/books';
import { Orders } from '../models/orders';

@Component({
  selector: 'app-librarian',
  templateUrl: './librarian.component.html',
  styleUrls: ['./librarian.component.css']
})
export class LibrarianComponent implements OnInit {
  usertype:string;
  users:Users[];
  books:Books[];
  orders:Orders[];
  rorders:Orders[];
  order1:Orders[];
  constructor(private router:Router,private service:AdminService) { }
  accept(o:Orders){
    this.service.acceptOrder(o.orderid).subscribe(data=>{
      if(data!=null){
        alert("Order accepted");
        window.location.reload();
      }
    });
  }
  return(o:Orders){
    this.service.acceptReturn(o.orderid).subscribe(data=>{
      if(data!=null){
        alert("Successfully Returned");
        window.location.reload();
      }
    })
  }
  ngOnInit() {
    this.usertype = sessionStorage.getItem("usertype");
    this.service.getAllBooks().subscribe(data=>{
      this.books = data;
    });
    this.service.showAllOrders().subscribe(data=>{
      this.orders=data;
    });
    this.service.returnList().subscribe(data=>{
      this.rorders=data;
    })
    this.service.getAllUsers().subscribe(data=>{
      this.users=data;
    })
    this.service.borrowedList().subscribe(data=>{
      this.order1=data;
    })

  }

}

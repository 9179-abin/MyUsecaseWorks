import { Injectable, Inject } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Users } from '../models/users';
import { Books } from '../models/books';
import { Orders } from '../models/orders';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private httpClient:HttpClient) { }
  getAllUsers(): Observable<Users[]>{
    return this.httpClient.get<Users[]>(environment.host+'/librarian/getallusers');
  }
  getAllBooks(): Observable<Books[]>{
    return this.httpClient.get<Books[]>(environment.host+'/librarian/show-bookstocks');
  }
  showAllOrders():Observable<Orders[]>{
    return this.httpClient.get<Orders[]>(environment.host+'/librarian/allorders');
  }
  acceptOrder(orderid:number){
    return this.httpClient.get<Orders>(environment.host+'/librarian/accept-order/'+orderid);
  }
  returnList():Observable<Orders[]>{
    return this.httpClient.get<Orders[]>(environment.host+'/librarian/return-list');
  }
  acceptReturn(orderid:number){
    return this.httpClient.get(environment.host+'/librarian/accept-return/'+orderid);
  }
  borrowedList():Observable<Orders[]>{
    return this.httpClient.get<Orders[]>(environment.host+'/librarian/books-borrowed');
  }
}

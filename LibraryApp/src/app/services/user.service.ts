import { Injectable, Inject } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Users} from '../models/users';
import {Login} from '../models/login';
import { environment } from 'src/environments/environment';
import { Books } from '../models/books';
import { Orders } from '../models/orders';
import { UserBooks } from '../models/userbooks';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private httpClient:HttpClient) { }

  userLogin(login:Login){
    return this.httpClient.post<Users>(environment.host+'/user/login',login);
  }
  userRegister(user:Users){
    return this.httpClient.post(environment.host+'/user/register',user);
  }
  getBooks(): Observable<Books[]>{
    return this.httpClient.get<Books[]>(environment.host+'/user/getbooks');
  }
  placeOrder(userid:number,bookid:number){
    return this.httpClient.get<Orders>(environment.host+'/user/placeorder/'+userid+'/'+bookid);
  }
  showUserBooks(userid:number): Observable<UserBooks[]>{
    return this.httpClient.get<UserBooks[]>(environment.host+'/user/show-userbooks/'+userid);
  }
  returnBooks(tableid:number){
    return this.httpClient.get(environment.host+'/user/return/'+tableid);
  }
}

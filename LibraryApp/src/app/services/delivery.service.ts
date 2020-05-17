import { Injectable, Inject } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import { Delivery } from '../models/delivery';
@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  constructor(private httpClient:HttpClient) { }

  getPendingTasks():Observable<Delivery[]>{
    return this.httpClient.get<Delivery[]>(environment.host+'/deliveryboy/pending-tasks');
  }
  deliver(orderid:number){
    return this.httpClient.get<string>(environment.host+'/deliveryboy/deliver/'+orderid);
  }

}

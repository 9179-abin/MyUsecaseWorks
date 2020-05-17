import { Component, OnInit } from '@angular/core';
import { Delivery } from '../models/delivery';
import { DeliveryService } from '../services/delivery.service';

@Component({
  selector: 'app-deliveryboy',
  templateUrl: './deliveryboy.component.html',
  styleUrls: ['./deliveryboy.component.css']
})
export class DeliveryboyComponent implements OnInit {
  usertype:string;
  delivery : Delivery[];
  constructor(private service:DeliveryService) { }

  deliver(d:Delivery){
    this.service.deliver(d.orderid).subscribe(data=>{
      console.log(data);
      if(data!=null){
        alert("Successfully Delivered");
        window.location.reload();
      }
    })
  }

  ngOnInit() {
    this.usertype = sessionStorage.getItem("usertype");
    this.service.getPendingTasks().subscribe(data=>{
      this.delivery=data;
    })

  }

}

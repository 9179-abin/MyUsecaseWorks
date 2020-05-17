export class Delivery{
    orderid : number;
    userid : number;
    bookid : number;
    seatno : string;
    deliverystatus : boolean;
    deliverytype : string;
    
    constructor(orderid:number, userid:number, bookid:number,seatno:string, deliverystatus:boolean, deliverytype:string){
        this.orderid = orderid;
        this.bookid = bookid;
        this.seatno = seatno;
        this.deliverystatus = deliverystatus;
        this.deliverytype = deliverytype;
        this.userid = userid;
    }
}
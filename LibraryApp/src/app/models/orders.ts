export class Orders{
    orderid : number;
    bookid : number;
    userid : number;
    requeststatus : boolean;
    returnstatus : boolean;
    constructor(orderid:number,bookid:number,userid:number,requeststatus:boolean,returnstatus:boolean){
        this.orderid=orderid;
        this.bookid=bookid;
        this.userid=userid;
        this.requeststatus=requeststatus;
        this.returnstatus = returnstatus;
    }
}
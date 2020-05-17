export class Users {
    userid: number;
    seatno : string;
    username : string;
    password : string;
    usertype : string;
    constructor(userid:number,seatno:string, username:string, password:string,usertype:string){
        this.userid = userid;
        this.password = password;
        this.seatno = seatno;
        this.username = seatno;
        this.usertype = usertype;
    }



}
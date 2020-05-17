export class UserBooks {
    tableid : number;
    userid : number;
    bookid : number;
    bookname : string;

    constructor(tableid:number,userid:number,bookid:number,bookname:string){
        this.bookid = bookid;
        this.tableid = tableid;
        this.userid = userid;
        this.bookname = bookname;
    }
}
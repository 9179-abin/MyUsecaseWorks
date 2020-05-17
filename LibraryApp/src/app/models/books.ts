export class Books {
    bookid : number;
    bookname : string;
    stock : number;
    constructor(bookid:number, bookname:string, stock:number){
        this.bookid = bookid;
        this.bookname=bookname;
        this.stock = stock;
    }
}
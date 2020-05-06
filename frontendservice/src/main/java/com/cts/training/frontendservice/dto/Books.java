package com.cts.training.frontendservice.dto;



public class Books {
	
	int bookid;
	String bookname;
	int stock;
	public Books() {}
	public Books(int bookid, String bookname, int stock) {
		super();
		this.bookid = bookid;
		this.bookname = bookname;
		this.stock = stock;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	@Override
	public String toString() {
		return "Books [bookid=" + bookid + ", bookname=" + bookname + ", stock=" + stock + "]";
	}
	
	

}
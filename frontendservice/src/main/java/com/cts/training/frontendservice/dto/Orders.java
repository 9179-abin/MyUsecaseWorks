package com.cts.training.frontendservice.dto;



public class Orders {
	

	int orderid;
	int bookid;
	int userid;
	boolean requeststatus;
	boolean returnstatus;
	public Orders() {}
	public Orders(int orderid, int bookid, int userid, boolean requeststatus, boolean returnstatus) {
		super();
		this.orderid = orderid;
		this.bookid = bookid;
		this.userid = userid;
		this.requeststatus = requeststatus;
		this.returnstatus = returnstatus;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public boolean isRequeststatus() {
		return requeststatus;
	}
	public void setRequeststatus(boolean requeststatus) {
		this.requeststatus = requeststatus;
	}
	public boolean isReturnstatus() {
		return returnstatus;
	}
	public void setReturnstatus(boolean returnstatus) {
		this.returnstatus = returnstatus;
	}
	@Override
	public String toString() {
		return "Orders [orderid=" + orderid + ", bookid=" + bookid + ", userid=" + userid + ", requeststatus="
				+ requeststatus + ", returnstatus=" + returnstatus + "]";
	}
	
	

}

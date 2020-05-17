package com.cts.training.frontendservice.dto;



public class Delivery {
	

	int orderid;
	int userid;
	int bookid;
	String seatno;
	boolean deliverystatus;
	String deliverytype;
	
	public Delivery() {}
	public Delivery(int orderid, int userid, int bookid, String seatno, boolean deliverystatus, String deliverytype) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.bookid = bookid;
		this.seatno = seatno;
		this.deliverystatus = deliverystatus;
		this.deliverytype = deliverytype;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public String getSeatno() {
		return seatno;
	}

	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}

	public boolean isDeliverystatus() {
		return deliverystatus;
	}

	public void setDeliverystatus(boolean deliverystatus) {
		this.deliverystatus = deliverystatus;
	}

	public String getDeliverytype() {
		return deliverytype;
	}

	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	@Override
	public String toString() {
		return "Delivery [orderid=" + orderid + ", userid=" + userid + ", bookid=" + bookid + ", seatno=" + seatno
				+ ", deliverystatus=" + deliverystatus + ", deliverytype=" + deliverytype + "]";
	}

}


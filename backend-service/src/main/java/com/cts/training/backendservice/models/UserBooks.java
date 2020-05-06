package com.cts.training.backendservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userbooks")
public class UserBooks {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int tableid;
	int userid;
	int bookid;
	
	public UserBooks() {}

	public UserBooks(int tableid, int userid, int bookid) {
		super();
		this.tableid = tableid;
		this.userid = userid;
		this.bookid = bookid;
	}

	public int getTableid() {
		return tableid;
	}

	public void setTableid(int tableid) {
		this.tableid = tableid;
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

	@Override
	public String toString() {
		return "UserBooks [tableid=" + tableid + ", userid=" + userid + ", bookid=" + bookid + "]";
	}
	
	

	
}

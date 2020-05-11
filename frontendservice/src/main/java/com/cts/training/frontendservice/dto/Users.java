package com.cts.training.frontendservice.dto;


public class Users {

	int userid;
	String seatno;
	String username;
	String password;
	String usertype;
	public Users() {}
	public Users(int userid, String seatno, String username, String password, String usertype) {
		super();
		this.userid = userid;
		this.seatno = seatno;
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getSeatno() {
		return seatno;
	}
	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	@Override
	public String toString() {
		return "Users [userid=" + userid + ", seatno=" + seatno + ", username=" + username + ", password=" + password
				+ ", usertype=" + usertype + "]";
	}
	
	
}
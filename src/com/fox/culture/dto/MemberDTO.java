package com.fox.culture.dto;

import java.sql.Date;

public class MemberDTO {
	String mid;
	String mpw;
	String mname;
	String mbirth;
	String maddr;
	String mhp;
	Date mdate;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMpw() {
		return mpw;
	}

	public void setMpw(String mpw) {
		this.mpw = mpw;
	}

	@Override
	public String toString() {
		return "MemberVO [mid=" + mid + ", mpw=" + mpw + ", mname=" + mname + ", mbirth=" + mbirth + ", maddr=" + maddr
				+ ", mhp=" + mhp + ", mdate=" + mdate + "]";
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getMbirth() {
		return mbirth;
	}

	public void setMbirth(String mbirth) {
		this.mbirth = mbirth;
	}

	public String getMaddr() {
		return maddr;
	}

	public void setMaddr(String maddr) {
		this.maddr = maddr;
	}

	public String getMhp() {
		return mhp;
	}

	public void setMhp(String mhp) {
		this.mhp = mhp;
	}

	public java.sql.Date getMdate() {
		return mdate;
	}

	public void setMdate(java.sql.Date mdate) {
		this.mdate = mdate;
	}

	public MemberDTO() {
		super();
		this.mid = mid;
		this.mpw = mpw;
		this.mname = mname;
		this.mbirth = mbirth;
		this.maddr = maddr;
		this.mhp = mhp;
		this.mdate = mdate;
	}
}

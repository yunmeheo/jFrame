package com.fox.culture.dto;


public class RegisterDTO {
	
	private String rid;
	private int rcode;
	private String rclass;
	private String rst;
	private String rcost;
	private int rmax;
	private String rdate;
	private String rname;
	
	public RegisterDTO() {
		super();
	}
	
	public RegisterDTO(String rid, int rcode, String rclass, String rst, String rcost,
			int rmax, String rdate, String rname) {
		super();
		this.rid = rid;
		this.rcode = rcode;
		this.rclass = rclass;
		this.rst = rst;
		this.rcost = rcost;
		this.rmax = rmax;
		this.rdate = rdate;
		this.rname = rname;
		
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public int getRcode() {
		return rcode;
	}

	public void setRcode(int rcode) {
		this.rcode = rcode;
	}

	public String getRclass() {
		return rclass;
	}

	public void setRclass(String rclass) {
		this.rclass = rclass;
	}

	public String getRst() {
		return rst;
	}

	public void setRst(String rst) {
		this.rst = rst;
	}

	public String getRcost() {
		return rcost;
	}

	public void setRcost(String rcost) {
		this.rcost = rcost;
	}

	public int getRmax() {
		return rmax;
	}

	public void setRmax(int rmax) {
		this.rmax = rmax;
	}

	@Override
	public String toString() {
		return "RegisterDTO [rid=" + rid + ", rcode=" + rcode + ", rclass=" + rclass + ", rst=" + rst + ", rcost="
				+ rcost + ", rmax=" + rmax + ", rdate=" + rdate + ", rname=" + rname + "]";
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}
}

	

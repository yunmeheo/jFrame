package com.fox.culture.dto;


public class CourseDTO {
	private int csCode;
	private String csClass;
	private String csTea;
	private String csField;
	private String csTarget;
	private String csPeriod;
	private String csOpen;
	private int csCost;
	private int csMax;
	private String csRoom;
	
	public CourseDTO() {
		super();
	}

	public CourseDTO(String csClass, String csTea, String csField, String csTarget, String csPeriod, String csOpen,
			int csCost, int csMax, String csRoom) {
		super();
		this.csClass = csClass;
		this.csTea = csTea;
		this.csField = csField;
		this.csTarget = csTarget;
		this.csPeriod = csPeriod;
		this.csOpen = csOpen;
		this.csCost = csCost;
		this.csMax = csMax;
		this.csRoom = csRoom;
	}

	public CourseDTO(int csCode, String csClass, String csTea, String csField, String csTarget, String csPeriod,
			String csOpen, int csCost, int csMax, String csRoom) {
		super();
		this.csCode = csCode;
		this.csClass = csClass;
		this.csTea = csTea;
		this.csField = csField;
		this.csTarget = csTarget;
		this.csPeriod = csPeriod;
		this.csOpen = csOpen;
		this.csCost = csCost;
		this.csMax = csMax;
		this.csRoom = csRoom;
	}

	public int getCsCode() {
		return csCode;
	}

	public void setCsCode(int csCode) {
		this.csCode = csCode;
	}

	public String getCsClass() {
		return csClass;
	}

	public void setCsClass(String csClass) {
		this.csClass = csClass;
	}

	public String getCsTea() {
		return csTea;
	}

	public void setCsTea(String csTea) {
		this.csTea = csTea;
	}

	public String getCsField() {
		return csField;
	}

	public void setCsField(String csField) {
		this.csField = csField;
	}

	public String getCsTarget() {
		return csTarget;
	}

	public void setCsTarget(String csTarget) {
		this.csTarget = csTarget;
	}

	public String getCsPeriod() {
		return csPeriod;
	}

	public void setCsPeriod(String csPeriod) {
		this.csPeriod = csPeriod;
	}

	public String getCsOpen() {
		return csOpen;
	}

	public void setCsOpen(String csOpen) {
		this.csOpen = csOpen;
	}

	public int getCsCost() {
		return csCost;
	}

	public void setCsCost(int csCost) {
		this.csCost = csCost;
	}

	public int getCsMax() {
		return csMax;
	}

	public void setCsMax(int csMax) {
		this.csMax = csMax;
	}

	public String getCsRoom() {
		return csRoom;
	}

	public void setCsRoom(String csRoom) {
		this.csRoom = csRoom;
	}
	
	
}

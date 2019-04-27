package com.example.mybox.bean;

import java.util.List;

public class SchoolInfo {
	
	private String school;
	private String resultCode;
	private String resultMessage;
	private List<StuClzInfo> clazz;
	public SchoolInfo() {
		
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public List<StuClzInfo> getClazz() {
		return clazz;
	}
	public void setClazz(List<StuClzInfo> clazz) {
		this.clazz = clazz;
	}
	

}

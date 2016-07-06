package com.app.example.baidutranslateapp.bean;

import java.util.List;

public class JsonData {
	String from;
	String to;
	List<trans_result> trans_result ;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<trans_result> getTrans_result() {
		return trans_result;
	}
	public void setTrans_result(List<trans_result> trans_result) {
		this.trans_result = trans_result;
	}
	
}

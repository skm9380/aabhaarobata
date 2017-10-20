package com.aabhaarobata.web.forms;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ImageProcessForm {

	private String w1 = "";
	private String x11 = "";
	private String x21 = "";
	private String y11 = "";
	private String y21 = "";
	private String h = "";
	private String h1 = "";
	private String w = "";
	
	private String w2 = "";
	private String x12 = "";
	private String x22 = "";
	private String y12 = "";
	private String y22 = "";
	private String h2 = "";
	
	@NotNull
	private String x1 = "";
	private String x2 = "";
	private String y1 = "";
	private String y2 = "";
	public String getW1() {
		return w1;
	}
	public void setW1(String w1) {
		this.w1 = w1;
	}
	public String getX11() {
		return x11;
	}
	public void setX11(String x11) {
		this.x11 = x11;
	}
	public String getX21() {
		return x21;
	}
	public void setX21(String x21) {
		this.x21 = x21;
	}
	public String getY11() {
		return y11;
	}
	public void setY11(String y11) {
		this.y11 = y11;
	}
	public String getY21() {
		return y21;
	}
	public void setY21(String y21) {
		this.y21 = y21;
	}
	public String getH() {
		return h;
	}
	public void setH(String h) {
		this.h = h;
	}
	public String getH1() {
		return h1;
	}
	public void setH1(String h1) {
		this.h1 = h1;
	}
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w = w;
	}
	public String getX1() {
		return x1;
	}
	public void setX1(String x1) {
		this.x1 = x1;
	}
	public String getX2() {
		return x2;
	}
	public void setX2(String x2) {
		this.x2 = x2;
	}
	public String getY1() {
		return y1;
	}
	public void setY1(String y1) {
		this.y1 = y1;
	}
	public String getY2() {
		return y2;
	}
	public void setY2(String y2) {
		this.y2 = y2;
	}
	
	public String getW2() {
		return w2;
	}
	public void setW2(String w2) {
		this.w2 = w2;
	}
	public String getX12() {
		return x12;
	}
	public void setX12(String x12) {
		this.x12 = x12;
	}
	public String getX22() {
		return x22;
	}
	public void setX22(String x22) {
		this.x22 = x22;
	}
	public String getY12() {
		return y12;
	}
	public void setY12(String y12) {
		this.y12 = y12;
	}
	public String getY22() {
		return y22;
	}
	public void setY22(String y22) {
		this.y22 = y22;
	}
	public String getH2() {
		return h2;
	}
	public void setH2(String h2) {
		this.h2 = h2;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this); 
	}
}
package com.luxoft.studentinfo.model;

public class Student extends Entry{
	
	private String name;
	private Group group;
	private String adress;
	private String city;
	private int result;
	
	public Student(String name, Group group, String adress, String city, int result) {
		this.name = name;
		this.group = group;
		this.adress = adress;
		this.city = city;
		this.result = result;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
	@Override
	public Group getParent() {
		return group;
	}
	
	
}

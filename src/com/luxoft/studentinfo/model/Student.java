package com.luxoft.studentinfo.model;

public class Student extends Entry{
	
	private String name;
	private Group group;
	private String adress;
	private String city;
	private int result;
	private String photoPath;
	
	public Student(String name, Group group, String adress, String city, int result, String photoPath) {
		this.name = name;
		this.group = group;
		this.adress = adress;
		this.city = city;
		this.result = result;
		this.photoPath = photoPath;
	}
	
	public Student() {
		
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
	
	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
	@Override
	public Group getParent() {
		return group;
	}

	@Override
	public void removeEntry(Entry entry) {
	
	}
	
}

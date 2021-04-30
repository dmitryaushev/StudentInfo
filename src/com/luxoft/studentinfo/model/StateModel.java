package com.luxoft.studentinfo.model;

import java.util.ArrayList;
import java.util.List;

public class StateModel {

	private Group root;
	private Folder folder;
	private List<Group> groups;
	
	public StateModel() {
		root = new Group(null, "root");
		folder = new Folder(root, "Folder");
		root.addEntry(folder);
		groups = new ArrayList<>();
		//populate();
	}

	public Group getRoot() {
		return root;
	}

	public void setGroup(Group group) {
		root = group;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	private void populate() {
		Group group1 = new Group(folder, "Group 1");
		folder.addEntry(group1);
		group1.addEntry(new Student("dima", group1, "ukraine", "kiev", 1,
				"C:\\Users\\DAushev\\eclipse-workspace_3\\com.luxoft.studentInfo\\photos\\dima.jpg"));
		group1.addEntry(new Student("anya", group1, "ukraine", "kherson", 2,
				"C:\\Users\\DAushev\\eclipse-workspace_3\\com.luxoft.studentInfo\\photos\\anya.jpg"));

		Group group2 = new Group(folder, "Group 2");
		folder.addEntry(group2);
		group2.addEntry(new Student("denis", group2, "ukraine", "khemelnitskiy", 3,
				"C:\\Users\\DAushev\\eclipse-workspace_3\\com.luxoft.studentInfo\\photos\\denis.jpg"));
		group2.addEntry(new Student("lera", group2, "ukraine", "kiev", 34,
				"C:\\Users\\DAushev\\eclipse-workspace_3\\com.luxoft.studentInfo\\photos\\lera.jpg"));
	}
}

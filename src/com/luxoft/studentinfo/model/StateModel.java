package com.luxoft.studentinfo.model;

import java.util.ArrayList;
import java.util.List;

public class StateModel {

	private Group root;
	private List<Group> groups;
	
	public StateModel() {
		root = new Group(null, "root");
		Folder folder = new Folder(root, "Folder");
		root.addEntry(folder);
		groups = new ArrayList<>();
	}

	public Group getRoot() {
		return root;
	}

	public void setGroup(Group group) {
		root = group;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public Folder getFolder() {
		Folder folder;
		if (root.getEntries().length != 0) {
			folder = (Folder) root.getEntries()[0];
		} else {
			folder = new Folder(root, "Folder");
			root.addEntry(folder);
		}
		return folder;
	}
}

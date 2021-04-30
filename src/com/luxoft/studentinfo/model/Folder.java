package com.luxoft.studentinfo.model;

import java.util.ArrayList;
import java.util.List;

public class Folder extends Entry {

	private Group parent;
	private List entries;
	private String name;
	
	public Folder(Group parent, String name) {
		this.parent = parent;
		this.name = name;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addEntry(Entry entry) {
		if (entries == null) {
			entries = new ArrayList();
		}
		entries.add(entry);
	}
	
	public void removeEntry(Entry entry) {
		if (entries != null) {
			entries.remove(entry);
			if (entries.isEmpty())
				entries = null;
		}
	}
	
	public Entry[] getEntries() {
		if (entries != null)
			return (Entry[]) entries.toArray(new Entry[entries.size()]);
		return new Entry[0];
	}

	public void clearEnties() {
		entries.clear();
	}
}

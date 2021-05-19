package com.luxoft.studentinfo.model;

import java.util.ArrayList;
import java.util.List;

public class Group extends Entry {

	private Entry parent;
	private List<Entry> entries = new ArrayList<>();
	private String name;

	public Group(Entry parent, String name) {
		this.parent = parent;
		this.name = name;
	}

	public Entry getParent() {
		return parent;
	}

	public void setParent(Entry parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addEntry(Entry entry) {
		entries.add(entry);
	}

	public void removeEntry(Entry entry) {
		entries.remove(entry);
	}

	public List<Entry> getEntries() {
		return entries;
	}
}

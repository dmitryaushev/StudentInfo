package com.luxoft.studentinfo.model;

public abstract class Entry {
	
	public abstract String getName();
	
	public abstract Entry getParent();

	public abstract void removeEntry(Entry entry);
}

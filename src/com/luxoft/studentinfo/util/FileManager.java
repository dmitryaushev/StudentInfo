package com.luxoft.studentinfo.util;

import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.Student;

public class FileManager {

	public static Group populate() {
		Group root = new Group(null, "root");

		Folder folder = new Folder(root, "Folder");
		root.addEntry(folder);

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

		return root;
	}
}

package com.luxoft.studentinfo.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.luxoft.studentinfo.model.Entry;
import com.luxoft.studentinfo.model.Folder;
import com.luxoft.studentinfo.model.Group;
import com.luxoft.studentinfo.model.ModelManager;
import com.luxoft.studentinfo.model.Student;
import com.luxoft.studentinfo.model.StudentDto;

public class FileManager {

	private static Folder folder;
	private static List<Group> groups = ModelManager.getInstance().getStateModel().getGroups();

	public static void saveToFile(String path) {
		folder = ModelManager.getInstance().getStateModel().getFolder();
		List<Entry> entries = folder.getEntries();
		List<StudentDto> studentDtos = new ArrayList<>();

		for (Entry entry : entries) {
			Group group = (Group) entry;
			List<Entry> students = group.getEntries();
			for (Entry s : students) {
				Student student = (Student) s;
				StudentDto studentDto = toDto(student);
				studentDtos.add(studentDto);
			}
		}
		String json = new Gson().toJson(studentDtos);
		try {
			Files.write(Paths.get(path), json.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readFromFile(String path) {
		folder = ModelManager.getInstance().getStateModel().getFolder();
		try {
			groups.clear();
			String json = Files.readString(Paths.get(path));
			Type listType = new TypeToken<List<StudentDto>>() {
			}.getType();
			List<StudentDto> studentDtos = new Gson().fromJson(json, listType);
			if (studentDtos != null) {
				studentDtos.forEach(studentDto -> fromDto(studentDto));
				folder.clearEnties();
				groups.forEach(group -> folder.addEntry(group));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	private static StudentDto toDto(Student student) {
		return new StudentDto(student.getName(), student.getGroup().getName(), student.getAdress(), student.getCity(),
				student.getResult(), student.getPhotoPath());
	}

	private static void fromDto(StudentDto studentDto) {
		String groupName = studentDto.getGroup();
		Group group = null;
		for (Group g : groups) {
			if (g.getName().equals(groupName)) {
				group = g;
				break;
			}
		}
		if (groups.isEmpty()) {
			group = new Group(folder, groupName);
			folder.addEntry(group);
			groups.add(group);
		}
		if (group == null) {
			group = new Group(folder, groupName);
			groups.add(group);
		}
		Student student = new Student(studentDto.getName(), group, studentDto.getAdress(), studentDto.getCity(),
				studentDto.getResult(), studentDto.getPhotoPath());
		group.addEntry(student);
	}
}

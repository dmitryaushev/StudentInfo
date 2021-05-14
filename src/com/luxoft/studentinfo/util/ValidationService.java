package com.luxoft.studentinfo.util;

public class ValidationService {

	public static void validateInput(String name, String group, String adress, String city, String result) {
		if (name.isEmpty() || group.isEmpty() || adress.isEmpty() || city.isEmpty() || result.isEmpty()) {
			throw new IllegalArgumentException("All fields must be filled");
		}
	}
}

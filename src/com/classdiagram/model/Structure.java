package com.classdiagram.model;

public class Structure {
	
	private String name;
	
	public enum Visibility {
		DEFAULT, PUBLIC, PRIVATE, PROTECTED
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	};
	
}

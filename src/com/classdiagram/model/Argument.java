package com.classdiagram.model;

import com.classdiagram.model.ClassDiagram.Type;

public class Argument {

	private String name;
	
	private Type type;

	public Argument() {}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}

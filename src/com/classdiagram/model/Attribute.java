package com.classdiagram.model;

import com.classdiagram.model.ClassDiagram.Type;

public class Attribute extends Structure {
	
	private Visibility v;
	
	private boolean isStatic;
	
	private Type type;
	
	public Attribute() {
		isStatic = false;
		v = Visibility.DEFAULT;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic() {
		this.isStatic = true;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Visibility getV() {
		return v;
	}

	public void setV(Visibility v) {
		this.v = v;
	}
	
}

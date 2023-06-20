package com.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

import com.classdiagram.model.ClassDiagram.Type;

public class Methode extends Structure {
	
	private Visibility v; 

	private boolean isStatic;
	
	private boolean isAbstract;
	
	private boolean isVoid;
	
	private List<Argument> arguments;
	
	private Type returnType;
	
	public Methode() {
		isStatic = false;
		isAbstract = false;
		isVoid = false;
		setArguments(new ArrayList<Argument>());
		v = Visibility.DEFAULT;
	}
	
	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic() {
		this.isStatic = true;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract() {
		this.isAbstract = true;
	}

	public boolean isVoid() {
		return isVoid;
	}

	public void setVoid() {
		this.isVoid = true;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	public Visibility getV() {
		return v;
	}

	public void setV(Visibility v) {
		this.v = v;
	}

	public List<Argument> getArguments() {
		return arguments;
	}

	public void setArguments(List<Argument> arguments) {
		this.arguments = arguments;
	}
}

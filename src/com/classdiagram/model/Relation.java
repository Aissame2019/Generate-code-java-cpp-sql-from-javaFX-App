package com.classdiagram.model;

import com.classdiagram.model.ClassDiagram.TypeRelation;

public class Relation {
	
	private String name;
	
	private TypeRelation type;
	
	private Class firstClass;
	private Class secondClass;
	
	public enum FirstToSecond {one, n};
	
	private FirstToSecond multiplicity;
	
	public Relation() {
		
		type = TypeRelation.Association_simple;
		//firstClass = new Class();
		//secondClass = new Class();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(Class firstClass) {
		this.firstClass = firstClass;
	}

	public Class getSecondClass() {
		return secondClass;
	}

	public void setSecondClass(Class secondClass) {
		this.secondClass = secondClass;
	}

	public TypeRelation getType() {
		return type;
	}

	public void setType(TypeRelation type) {
		this.type = type;
	}

	public FirstToSecond getMultiplicity() {
		return multiplicity;
	}

	public void setMultiplicity(FirstToSecond multiplicity) {
		this.multiplicity = multiplicity;
	}
}

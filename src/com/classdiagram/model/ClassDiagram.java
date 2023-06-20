package com.classdiagram.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassDiagram {
	
	private String name;
	private List <Class> classes;
	private List <Interface> interfaces;
	private List <Relation> relations;
	
	public enum Type {
		BOOLEAN, STRING, CHAR, FLOAT, INT
	};
	
	public enum TypeRelation {
		Association_simple, Composition, Agregation
	};
	
	public ClassDiagram() {
		classes = new ArrayList<Class>();
		interfaces = new ArrayList<Interface>();
		relations = new ArrayList<Relation>();
	}

	public List <Class> getClasses() {
		return classes;
	}

	public List <Interface> getInterfaces() {
		return interfaces;
	}


	public List <Relation> getRelations() {
		return relations;
	}
	
	public void addClass(Class nouveau) {
		classes.add(nouveau);
	}
	public void addInterface(Interface nouveau) {
		interfaces.add(nouveau);
	}
	public void addRelation(Relation nouveau) {
		relations.add(nouveau);
	}
	
	public Class getClassByName(String nameClass) {
		for (Iterator<Class> iterator = classes.iterator(); iterator.hasNext();) {
			Class curr = iterator.next();
			
			if (curr.getName().equals(nameClass)) {
				return curr;
			}
		}
		return null;
	}
	public Interface getInterfaceByName(String nameInterface) {
		for (Iterator<Interface> iterator = interfaces.iterator(); iterator.hasNext();) {
			Interface curr = iterator.next();
			if (curr.getName().equals(nameInterface)) {
				return curr;
			}
		}
		return null;
	}
	public Relation getRelationByName(String nameRelation) {
		for (Iterator<Relation> iterator = relations.iterator(); iterator.hasNext();) {
			Relation curr = iterator.next();
			if (curr.getName().equals(nameRelation)) {
				return curr;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

package com.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

public class ContainerStructure extends Structure {
	
	private List<Attribute> attributes;
	private List<Methode> methodes;
	
	ContainerStructure() {
		attributes = new ArrayList<Attribute>();
		methodes = new ArrayList<Methode>();
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public List<Methode> getMethodes() {
		return methodes;
	}
	
	public void addAttribute(Attribute a) {
		attributes.add(a);
	}

	public void addMethode(Methode m) {
		methodes.add(m);
	}
	
}

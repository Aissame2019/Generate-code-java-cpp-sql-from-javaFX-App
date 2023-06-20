package com.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

import com.classdiagram.model.Structure.Visibility;

public class Interface extends ContainerStructure {
	
	private List<Interface> interfaces;
	
	private Visibility v;
	
	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public Interface(String name, List<Interface> interfaces) {
		this.setName(name);
		this.interfaces = interfaces;
		v = Visibility.PUBLIC;
	}
	
	public void addInterface(Interface nouveau) {
		interfaces.add(nouveau);
	}

	public Visibility getV() {
		return v;
	}

	public void setV(Visibility v) {
		this.v = v;
	}
	
}

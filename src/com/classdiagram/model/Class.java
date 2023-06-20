package com.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

public class Class extends ContainerStructure  {
	
	private Visibility v;
	
	private boolean isAbstract;
	
	private Class superClass;
	
	private List<Interface> interfaces;
	
	public Class() {
		
	}
	
	public Class(String name, Class parent, boolean isPrivate, List<Interface> interfaces) {
		super();
		this.setName(name);
		this.isAbstract = false;
		this.superClass = parent;
		this.interfaces = interfaces;
		setV(Visibility.PUBLIC);
		if(isPrivate) {
			setV(Visibility.PRIVATE);
		}
		
	}

	public boolean isAbstract() {
		return isAbstract;
	}
	
	public void turnAbstract() {
		isAbstract = true;
	}

	public Class getSuperClass() {
		return superClass;
	}
	
	public void setSuperClass(Class superC) {
		superClass = superC;
	}

	public List<Interface> getInterfaces() {
		return interfaces;
	}

	public void implementsInterface(Interface nouveau) {
		interfaces.add(nouveau);
	}

	public Visibility getV() {
		return v;
	}

	public void setV(Visibility v) {
		this.v = v;
	}


	
	

}

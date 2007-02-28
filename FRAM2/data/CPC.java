package data;

import java.io.Serializable;
import java.util.ArrayList;

public class CPC implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9034461918811470582L;
	private Aspect parent;
	
	public static boolean typeExists(String type) {
		for(String t : CPC_TYPES) {
			if(t.equals(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static final String[] CPC_TYPES = new String[]{
		"Available resources",
		"Training experience",
		"Quality communication",
		"HMI and operational support",
		"Access procedures and methods",
		"Conditions of work",
		"Number of goals and conflict resolution",
		"Available time and time preasure",
		"Circadian rytm, stress",
		"Crew collaboration quality",
		"Quality and support of organization"
	};
	
	private ArrayList<CPCAttribute> list;
	
	public CPC(Aspect parent) {
		this.parent = parent;
		list = new ArrayList<CPCAttribute>();
	}
	public void setAttribute(String type, String value, String comment){
		if(hasAttribute(type)) {
			CPCAttribute attrib = getAttribute(type);
			attrib.setValue(value);
			attrib.setComment(comment);
		}
		else {
			list.add(new CPCAttribute(type, value, comment));
		}
	}
	
	public boolean hasAttribute(String type) {
		for(CPCAttribute attrib : list) {
			if(attrib.getType().equals(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	public CPCAttribute getAttribute(String type) {
		for(CPCAttribute attrib : list) {
			if(attrib.getType().equals(type)) {
				return attrib;
			}
		}
		
		return null;
	}
	
	public Aspect getParent() {
		return parent;
	}

}

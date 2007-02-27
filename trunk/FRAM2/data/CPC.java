package data;

import java.io.Serializable;
import java.util.ArrayList;

public class CPC implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9034461918811470582L;
	
	public static boolean typeExists(String type) {
		for(String t : CPC_TYPES) {
			if(t.equals(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static final String[] CPC_TYPES = new String[]{
		"Available_resources",
		"Training_experience",
		"Quality_communication",
		"HMI_operational_support",
		"Access_procedures_methods",
		"Conditions_of_work",
		"Number_of_goals_and_conflict_resolution",
		"Available_time_and_time_preasure",
		"Circadian_rytm_stress",
		"Crew_collaboration_quality",
		"Quality_and_support_of_organization"
	};
	
	private ArrayList<CPCAttribute> list;
	
	public CPC() {
		list = new ArrayList<CPCAttribute>();
	}
	public void setAttribute(String type, String value, String comment){
		if(hasAttribute(type)) {
			CPCAttribute attrib = getAttribute(type);
			attrib.setValue(value);
			attrib.setComment(comment);
		}
		
		list.add(new CPCAttribute(type, value, comment));
	}
	
	public boolean hasAttribute(String type) {
		for(CPCAttribute attrib : list) {
			if(attrib.getType().equals(type)) {
				return false;
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

}

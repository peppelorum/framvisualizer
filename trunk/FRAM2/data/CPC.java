package data;

import java.util.ArrayList;

public class CPC {
	private ArrayList<String[]> list;
	
	public CPC() {
		list = new ArrayList<String[]>();
		add("Available_resources", "","");
		add("Training_experience", "","");
		add("Quality_communication", "","");
		add("HMI_operational_support", "","");
		add("Access_procedures_methods", "","");
		add("Conditions_of_work", "","");
		add("Number_of_goals_and_conflict_resolution", "","");
		add("Available_time_and_time_preasure", "","");
		add("Circadian_rytm_stress", "","");
		add("Crew_collaboration_quality", "","");
		add("Quality_and_support_of_organization", "","");
	}
	public void add(String type, String value, String comment){
		list.add(new String[] {type, value, comment});
	}
	
	public String getValueOfCPC(String cpc){
		
		for(String[] a : list){
			if (a[0].equals(cpc)) {
				return a[1];
			}
		}
		return "";
	}
	
	public ArrayList<String[]> getCPC() {
		return list;
	}

}

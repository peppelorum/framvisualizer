package data;

import data.FramNode.connectionPoints;

public class RelationInfo {

	private FramNode function;
	private connectionPoints connectionType; //input, output, resources 

	public RelationInfo(FramNode function, String connectionType){
		this.function = function;
		this.connectionType = connectionPoints.valueOf(connectionType);
	}
		
	public String getFunctionName(){
		return function.getName();
	}
	
	public FramNode getNode() {
		return function;
	}
	
	public connectionPoints getConnectionPort(){
		return connectionType;
	}
	
	
	public boolean compareTo(RelationInfo comparee) {
		
		return ((comparee.getConnectionPort().equals(this.getConnectionPort())) &&
			(comparee.getFunctionName().equals(this.getFunctionName())));
	}
}
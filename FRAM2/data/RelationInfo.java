package data;

import data.FramNode.NodePort;

public class RelationInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282137696278770529L;
	private FramNode function;
	private NodePort connectionType; //input, output, resources 

	public RelationInfo(FramNode function, String connectionType){
		this.function = function;
		this.connectionType = NodePort.valueOf(connectionType);
	}
		
	public String getFunctionName(){
		return function.getName();
	}
	
	public FramNode getNode() {
		return function;
	}
	
	public NodePort getConnectionPort(){
		return connectionType;
	}
	
	
	public boolean compareTo(RelationInfo comparee) {
		
		return ((comparee.getConnectionPort().equals(this.getConnectionPort())) &&
			(comparee.getFunctionName().equals(this.getFunctionName())));
	}
}

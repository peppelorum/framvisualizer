package data;

public class ConnectionInfo {

	private RelationInfo from;
	private RelationInfo to;
	private String aspect = "";
	private boolean visibility = true;
	
	
	public ConnectionInfo(RelationInfo from, RelationInfo to){
		this.from = from;
		this.to = to;
	}
	
	public ConnectionInfo(RelationInfo from, RelationInfo to, String aspect){
		this.from = from;
		this.to = to;
		this.aspect = aspect;
	}
	
	public RelationInfo getFrom(){
		return from;
	}
	public RelationInfo getTo(){
		return to;
	}
	public String getAspect(){
		return aspect;
	}
	public boolean getVisibility(){
		return visibility;
	}
	public void setAspect(String aspect){
		this.aspect = aspect;
	}
	public void setVisibility(boolean visibility){
		this.visibility = visibility;
	}

	public boolean equals(Object otherObj){
		ConnectionInfo object2 =(ConnectionInfo)otherObj;
		
		return this.getAspect().equals(object2.getAspect()) && 
		this.getFrom().compareTo(object2.getFrom()) && this.getTo().compareTo(object2.getTo());
	}
	
}

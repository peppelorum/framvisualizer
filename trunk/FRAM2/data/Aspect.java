package data;

public class Aspect {

	private String value;
	private String comment;
	
	public Aspect(String value){
		this.value = value;
		this.comment = "";
	}
	
	public Aspect(String value, String comment){
		this.value = value;
		this.comment = comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getComment() {
		return comment;
	}
	public String getValue() {
		return value;
	}
}

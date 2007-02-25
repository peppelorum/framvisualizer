package data;

public class Aspect implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8030263320025000436L;
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
	
	public boolean equals(Aspect aspect) {
		if(aspect == null) {
			return false;
		}
		
		if(!aspect.getComment().equals(this.getComment())) {
			return false;
		}

		if(!aspect.getValue().equals(this.getValue())) {
			return false;
		}
		
		return true;
	}
}

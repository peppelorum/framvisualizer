package data;

import java.io.Serializable;

public class CPCAttribute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5591754827614386943L;
	private String type;
	private String value;
	private String comment;

	public CPCAttribute(String type, String value, String comment) {
		setType(type);
		setValue(value);
		setComment(comment);
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public String getComment() {
		return comment;
	}

	public void setType(String val) {
		if(!CPC.typeExists(val)) {
			throw new Error("CPC type doesn't exist");
		}
		this.type = val;
	}

	public void setValue(String val) {
		this.value = val;
	}

	public void setComment(String val) {
		this.comment = val;
	}

	public String[] toArray() {
		return new String[] { getType(), getValue(), getComment() };
	}
}

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

	public void setType(String value) {
		if(!CPC.typeExists(value)) {
			throw new Error("CPC type doesn't exist");
		}
		type = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setComment(String value) {
		comment = value;
	}

	public String[] toArray() {
		return new String[] { getType(), getValue(), getComment() };
	}
}

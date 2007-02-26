package data;

import java.io.Serializable;

import data.FramNode.connectionPoints;
import data.FramNode.stegTvaAttribut;

public class FramStegTvaMappning implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8204382094793634394L;
	
	private stegTvaAttribut attribute;
	private connectionPoints conn;
	private String text;
	
	
	public FramStegTvaMappning(stegTvaAttribut attribute, connectionPoints conn, String text) {
		setAttribute(attribute);
		setConnectionPoint(conn);
		setText(text);
	}
	
	public stegTvaAttribut getAttribute() {
		return attribute;
	}
	
	public connectionPoints getConnectionPoint() {
		return conn;
	}
	
	public String getText() {
		return text;
	}
	
	public void setAttribute(stegTvaAttribut value) {
		attribute = value;
	}

	public void setConnectionPoint(connectionPoints value) {
		conn = value;
	}
	
	public void setText(String value) {
		text = value;
	}
	
}

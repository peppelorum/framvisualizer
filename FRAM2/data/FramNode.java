package data;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * FramNode represent the functions in FRAM.
 * For each connectionplace(input, output) there is a ArrayList with the outgoing or ingoing attribut
 * @author Jonas Haraldsson
 *
 */
public class FramNode {
	private FramNodeList list;
	
	private String name;
	private ArrayList<String> input;
	private ArrayList<String> output;
	private ArrayList<String> resources;
	private ArrayList<String> time;
	private ArrayList<String> control;
	private ArrayList<String> preconditions;
	private ArrayList<ActionListener> nodeChangedRecipients = new ArrayList<ActionListener>();
	
	private Point position = new Point(0, 0);
	private int size = 50;
	
	public static enum connectionPoints { Time, Control, Output, Resources, Preconditions, Input};


	public FramNode(){
		input = new ArrayList<String>(); 
		output = new ArrayList<String>(); 
		resources = new ArrayList<String>(); 
		time = new ArrayList<String>(); 
		control = new ArrayList<String>(); 
		preconditions = new ArrayList<String>(); 
	}
	public FramNode(String name){
		this.name = name;
		
		input = new ArrayList<String>(); 
		output = new ArrayList<String>(); 
		resources = new ArrayList<String>(); 
		time = new ArrayList<String>(); 
		control = new ArrayList<String>(); 
		preconditions = new ArrayList<String>(); 
		
	}
	
	public FramNodeList getList() {
		return list;
	}
	
	public void setList(FramNodeList value) {
		this.list = value;
	} 
	
	public void setName(String value){
		name = value;
		nodeChanged("NameChanged");
	}
	public void addInput(String value){
		input.add(value);
		nodeChanged("InputAdded");
	}
	public void addOutput(String value){
		output.add(value);
		nodeChanged("OutputAdded");
	}	
	public void addResources(String value){
		resources.add(value);
		nodeChanged("ResourcesAdded");
	}
	public void addTime(String value){
		time.add(value);
		nodeChanged("TimeAdded");
	}
	public void addControl(String value){
		control.add(value);
		nodeChanged("ControlAdded");
	}
	public void addPrecondition(String value){
		preconditions.add(value);
		nodeChanged("PreconditionsAdded");
	}
	
	
	public String getName(){
		return name;
	}
	
	public ArrayList<String> getInput(){
		return input;
	}
	public ArrayList<String> getOutput(){
		return output;
	}	
	public ArrayList<String> getResources(){
		return resources;
	}
	public ArrayList<String> getTime(){
		return time;
	}
	public ArrayList<String> getControl(){
		return control;
	}
	public ArrayList<String> getPrecondition(){
		return preconditions;
	}
	
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point value) {
		position.x = value.x;
		position.y = value.y;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int value) {
		size = value;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(
				getPosition().x,
				getPosition().y,
				getSize(),
				getSize());
	}
	
	
	public String[] getAllEntities() {
		ArrayList<String> allEntities = new ArrayList<String>();
		
		allEntities.addAll(getInput());
		allEntities.addAll(getOutput());
		allEntities.addAll(getResources());
		allEntities.addAll(getTime());
		allEntities.addAll(getControl());
		allEntities.addAll(getPrecondition());
			
		String[] allEntitiesArray = new String[allEntities.size()];
		
		allEntities.toArray(allEntitiesArray);
		
		return allEntitiesArray;
	}
	
	/**
	 *  Returns an arraylist with all the connections in this node
	 *  TypeofConnection|attribute e.g.
	 *  input[0]|newspapers[1]
	 *  output[0]|pappershred[1]
	 *  
	 * @param list Array with 2 slots [0]=port [1]=aspect
	 * @return Arraylist with an string-array for each connection. 
	 */
	
	
	
	public ArrayList<String[]> getAllAspects(){
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		for(String readAttribute : input){
			String[] attribute =  {connectionPoints.Input.toString(), readAttribute};
			list.add(attribute);
		}
		for(String readAttribute : output){
			String[] attribute =  {connectionPoints.Output.toString(), readAttribute};
			list.add(attribute);
		}
		for(String readAttribute : resources){
			String[] attribute =  {connectionPoints.Resources.toString(), readAttribute};
			list.add(attribute);
		}
		for(String readAttribute : control){
			String[] attribute = {connectionPoints.Control.toString(), readAttribute};
			list.add(attribute);
		}
		for(String readAttribute : time){
			String[] attribute = {connectionPoints.Time.toString(), readAttribute};
			list.add(attribute);
		}
		for(String readAttribute : preconditions){
			String[] attribute = {connectionPoints.Preconditions.toString(), readAttribute};
			list.add(attribute);

		}
			
		
		return list;
	}
	
	
	
	public ArrayList<String> getAttribute(connectionPoints type) {
		if(type == connectionPoints.Input) {
			return this.getInput();
		}
		else if(type == connectionPoints.Output) {
			return this.getOutput();
		}
		else if(type == connectionPoints.Resources) {
			return this.getResources();
		}
		else if(type == connectionPoints.Control) {
			return this.getControl();
		}
		else if(type == connectionPoints.Time) {
			return this.getTime();
		}
		else if(type == connectionPoints.Preconditions) {
			return this.getPrecondition();
		}
		else {
			return null;
		}
	}
	
	public void setAttribute(connectionPoints type, ArrayList<String> value) {
		if(type == connectionPoints.Input) {
			this.input = value;
			nodeChanged("InputChanged");
		}
		else if(type == connectionPoints.Output) {
			this.output = value;
			nodeChanged("OutputChanged");
		}
		else if(type == connectionPoints.Resources) {
			this.resources = value;
			nodeChanged("ResourcesChanged");
		}
		else if(type == connectionPoints.Control) {
			this.control = value;
			nodeChanged("ControlChanged");
		}
		else if(type == connectionPoints.Time) {
			this.time = value;
			nodeChanged("TimeChanged");
		}
		else if(type == connectionPoints.Preconditions) {
			this.preconditions = value;
			nodeChanged("PreconditionsChanged");
		}
	}

	public void addNodeChangedListener(ActionListener listener) {
		this.nodeChangedRecipients.add(listener);
	}
	
	public void removeNodeChangedListener(ActionListener listener) {
		this.nodeChangedRecipients.remove(listener);
	}
	
	private void nodeChanged(String action) {
		ActionEvent event = new ActionEvent(this, 0, action);
		
		for(ActionListener listener : this.nodeChangedRecipients) {
			listener.actionPerformed(event);
		}
	}

}

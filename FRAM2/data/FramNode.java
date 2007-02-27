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
public class FramNode implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4081702271683596160L;

	private FramNodeList list;
	
	private String name;
	private String comment;
	private ArrayList<Aspect> input;
	private ArrayList<Aspect> output;
	private ArrayList<Aspect> resources;
	private ArrayList<Aspect> time;
	private ArrayList<Aspect> control;
	private ArrayList<Aspect> preconditions;
	private ArrayList<FramStegTvaMappning> steg2Mappings;
	transient private ArrayList<ActionListener> nodeChangedRecipients;
	
	private Point position = new Point(0, 0);
	private int size = 50;
	
	public static enum connectionPoints { Time, Control, Output, Resources, Preconditions, Input};
	public static enum stegTvaAttribut { AttributEtt, AttribTva, AttribTre };

	public FramNode(){
		init();
	}
	public FramNode(String name){
		init();
		
		setName(name);
	}
	
	public void init() {
		nodeChangedRecipients = new ArrayList<ActionListener>();
		
		input = new ArrayList<Aspect>(); 
		output = new ArrayList<Aspect>(); 
		resources = new ArrayList<Aspect>(); 
		time = new ArrayList<Aspect>(); 
		control = new ArrayList<Aspect>(); 
		preconditions = new ArrayList<Aspect>(); 
		
		steg2Mappings = new ArrayList<FramStegTvaMappning>();
		
		setComment("");
		setName("");
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
	public void setComment(String value){
		comment = value;
		nodeChanged("CommentChanged");
	}
	public void addInput(String value, String comment){
		input.add(new Aspect(value, comment));
		nodeChanged("InputAdded");
	}
	public void addOutput(String value, String comment){
		output.add(new Aspect(value, comment));
		nodeChanged("OutputAdded");
	}	
	public void addResources(String value, String comment){
		resources.add(new Aspect(value, comment));
		nodeChanged("ResourcesAdded");
	}
	public void addTime(String value, String comment){
		time.add(new Aspect(value, comment));
		nodeChanged("TimeAdded");
	}
	public void addControl(String value, String comment){
		control.add(new Aspect(value, comment));
		nodeChanged("ControlAdded");
	}
	public void addPrecondition(String value, String comment){
		preconditions.add(new Aspect(value, comment));
		nodeChanged("PreconditionsAdded");
	}
	
	public void addInput(String value){
		input.add(new Aspect(value));
		nodeChanged("InputAdded");
	}
	public void addOutput(String value){
		output.add(new Aspect(value));
		nodeChanged("OutputAdded");
	}	
	public void addResources(String value){
		resources.add(new Aspect(value));
		nodeChanged("ResourcesAdded");
	}
	public void addTime(String value){
		time.add(new Aspect(value));
		nodeChanged("TimeAdded");
	}
	public void addControl(String value){
		control.add(new Aspect(value));
		nodeChanged("ControlAdded");
	}
	public void addPrecondition(String value){
		preconditions.add(new Aspect(value));
		nodeChanged("PreconditionsAdded");
	}
	
	
	public String getName(){
		return name;
	}
	
	public String getComment(){
		return comment;
	}
	
	public ArrayList<Aspect> getInput(){
		return input;
	}
	public ArrayList<Aspect> getOutput(){
		return output;
	}	
	public ArrayList<Aspect> getResources(){
		return resources;
	}
	public ArrayList<Aspect> getTime(){
		return time;
	}
	public ArrayList<Aspect> getControl(){
		return control;
	}
	public ArrayList<Aspect> getPrecondition(){
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
		
		for(Aspect asp : getInput()){
			allEntities.add(asp.getValue());
		}
		for(Aspect asp : getOutput()){
			allEntities.add(asp.getValue());
		}
		for(Aspect asp : getResources()){
			allEntities.add(asp.getValue());
		}
		for(Aspect asp : getTime()){
			allEntities.add(asp.getValue());
		}
		for(Aspect asp : getControl()){
			allEntities.add(asp.getValue());
		}
		for(Aspect asp : getPrecondition()){
			allEntities.add(asp.getValue());
		}
			
		String[] allEntitiesArray = new String[allEntities.size()];
		
		allEntities.toArray(allEntitiesArray);
		
		return allEntitiesArray;
	}
	
	/**
	 *  Returns an arraylist with all the connections in this node
	 *  TypeofConnection|aspect|comment
	 *  
	 * @param list Array with 2 slots [0]=port [1]=aspect [2]=comment
	 * @return Arraylist with an string-array for each connection. 
	 */
	
	public ArrayList<String[]> getAllAspects(){
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		for(Aspect readAspect : input){
			String[] attribute =  {connectionPoints.Input.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : output){
			String[] attribute =  {connectionPoints.Output.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : resources){
			String[] attribute =  {connectionPoints.Resources.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : control){
			String[] attribute = {connectionPoints.Control.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : time){
			String[] attribute = {connectionPoints.Time.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : preconditions){
			String[] attribute = {connectionPoints.Preconditions.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);

		}
			
		
		return list;
	}
	
	/**
	 * returns the connections for a specified port
	 * @param type
	 * @param aspect
	 */
	
	public ArrayList<Aspect> getAttribute(connectionPoints type) {
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
	
	
	public void setAttribute(connectionPoints type, ArrayList<Aspect> aspect) {
		if(type == connectionPoints.Input) {
			
			this.input = aspect;
			nodeChanged("InputChanged");
		}
		else if(type == connectionPoints.Output) {
			this.output = aspect;
			nodeChanged("OutputChanged");
		}
		else if(type == connectionPoints.Resources) {
			this.resources = aspect;
			nodeChanged("ResourcesChanged");
		}
		else if(type == connectionPoints.Control) {
			this.control = aspect;
			nodeChanged("ControlChanged");
		}
		else if(type == connectionPoints.Time) {
			this.time = aspect;
			nodeChanged("TimeChanged");
		}
		else if(type == connectionPoints.Preconditions) {
			this.preconditions = aspect;
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

	public boolean equals(FramNode node) {
		if(node == null) {
			return false;
		}
		
		if(!node.getName().equals(this.getName())) {
			return false;
		}
		
		
		for(connectionPoints conn : connectionPoints.values()) {
			ArrayList<Aspect> myAttribs = this.getAttribute(conn);
			ArrayList<Aspect> otherAttribs = node.getAttribute(conn);
			
			for(int i = 0; i < myAttribs.size(); i++){ 
				if(!myAttribs.get(i).equals(otherAttribs.get(i))) {
					return false;
				}
			}
		}
		
		return true;
	}
}

/**

 	A visualizer for FRAM (Functional Resonance Accident Model).
 	This tool helps modelling the the FRAM table and visualize it.
	Copyright (C) 2007  Peppe Bergqvist <peppe@peppesbodega.nu>, Fredrik Gustafsson <fregu808@student.liu.se>,
	Jonas Haraldsson <haraldsson@gmail.com>, Gustav Ladén <gusla438@student.liu.se>
	http://sourceforge.net/projects/framvisualizer/
	
	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; either version 2
	of the License, or (at your option) any later version.
	
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  
 **/

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
	transient private ArrayList<ActionListener> nodeChangedRecipients;
	
	private Point position = new Point(0, 0);
	private int size = 50;
	
	public static String[] stepTwoDefaultValues = new String[] {
		"",
		"Adequate",
		"Inadequate",
		"Efficient",
		"Inefficient",
		"Compatible",
		"Fewer than capacity",
		"Matching current capacity",
		"More than capacity",
		"Temporarily inadequate",
		"Adjusted (day time)"
	};
	
	public static enum NodePort { Time, Control, Output, Resources, Preconditions, Input};

	public FramNode(){
		init();
		
		input = new ArrayList<Aspect>(); 
		output = new ArrayList<Aspect>(); 
		resources = new ArrayList<Aspect>(); 
		time = new ArrayList<Aspect>(); 
		control = new ArrayList<Aspect>(); 
		preconditions = new ArrayList<Aspect>(); 
				
		setComment("");
		setName("");
	}
	public FramNode(String name){
		init();
				
		input = new ArrayList<Aspect>(); 
		output = new ArrayList<Aspect>(); 
		resources = new ArrayList<Aspect>(); 
		time = new ArrayList<Aspect>(); 
		control = new ArrayList<Aspect>(); 
		preconditions = new ArrayList<Aspect>(); 
				
		setComment("");
		setName(name);
	}
	
	public void init() {
		nodeChangedRecipients = new ArrayList<ActionListener>();
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
	
	/**
	 * 
	 * 
	 * 
	 * @return All unique values for each aspect in a node
	 */
	public String[] getAllEntities() {
		ArrayList<String> allEntities = new ArrayList<String>();
		
		for(FramNode.NodePort conn : FramNode.NodePort.values()) {
			for(Aspect asp : this.getAttributes(conn)){
				allEntities.add(asp.getValue());
			}
		}
			
		String[] allEntitiesArray = new String[allEntities.size()];
		
		allEntities.toArray(allEntitiesArray);
		
		return allEntitiesArray;
	}
	
	/**
	 *  Returns an arraylist with all the aspects in this node
	 *  TypeofPort|value|comment
	 *  
	 * @param list Array with 3 slots [0]=port [1]=value [2]=comment
	 * @return Arraylist with an string-array for each connection. 
	 */
	
	public ArrayList<String[]> getAllAspects(){
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		for(Aspect readAspect : input){
			String[] attribute =  {NodePort.Input.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : output){
			String[] attribute =  {NodePort.Output.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : resources){
			String[] attribute =  {NodePort.Resources.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : control){
			String[] attribute = {NodePort.Control.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : time){
			String[] attribute = {NodePort.Time.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);
		}
		for(Aspect readAspect : preconditions){
			String[] attribute = {NodePort.Preconditions.toString(), readAspect.getValue(), readAspect.getComment()};
			list.add(attribute);

		}
			
		
		return list;
	}
	
	/**
	 * returns the connections for a specified port
	 * @param type
	 * @param aspect
	 */
	
	public ArrayList<Aspect> getAttributes(NodePort type) {
		if(type == NodePort.Input) {
			return input;
		}
		else if(type == NodePort.Output) {
			return output;
		}
		else if(type == NodePort.Resources) {
			return resources;
		}
		else if(type == NodePort.Control) {
			return control;
		}
		else if(type == NodePort.Time) {
			return time;
		}
		else if(type == NodePort.Preconditions) {
			return preconditions;
		}
		else {
			return null;
		}
	}
	
	
	public void setAttributes(NodePort type, ArrayList<Aspect> aspects) {
		for(Aspect a : aspects) {
			a.setParent(this);
		}
		
		if(type == NodePort.Input) {
			
			this.input = aspects;
			nodeChanged("InputChanged");
		}
		else if(type == NodePort.Output) {
			this.output = aspects;
			nodeChanged("OutputChanged");
		}
		else if(type == NodePort.Resources) {
			this.resources = aspects;
			nodeChanged("ResourcesChanged");
		}
		else if(type == NodePort.Control) {
			this.control = aspects;
			nodeChanged("ControlChanged");
		}
		else if(type == NodePort.Time) {
			this.time = aspects;
			nodeChanged("TimeChanged");
		}
		else if(type == NodePort.Preconditions) {
			this.preconditions = aspects;
			nodeChanged("PreconditionsChanged");
		}
	}

	public Aspect getAspect(String type, String name) {		
		for(Aspect a : this.getAttributes(NodePort.valueOf(type))) {
			if(a.getValue().equals(name)) {
				return a;
			}
		}
		
		return null;
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
		
		
		for(NodePort conn : NodePort.values()) {
			ArrayList<Aspect> myAttribs = this.getAttributes(conn);
			ArrayList<Aspect> otherAttribs = node.getAttributes(conn);
			
			for(int i = 0; i < myAttribs.size(); i++){ 
				if(!myAttribs.get(i).equals(otherAttribs.get(i))) {
					return false;
				}
			}
		}
		
		return true;
	}
}

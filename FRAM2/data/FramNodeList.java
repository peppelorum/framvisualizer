/**

 	A visualizer for FRAM (Functional Resonance Accident Model).
 	This tool helps modelling the the FRAM table and visualize it.
	Copyright (C) 2007  Peppe Bergqvist <peppe@peppesbodega.nu>, Fredrik Gustafsson <fregu808@student.liu.se>,
	Jonas Haraldsson <haraldsson@gmail.com>, Gustav Lad�n <gusla438@student.liu.se>
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

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import data.FramNode.NodePort;


/**
 * FramNodeList is an ArrayList which store all the nodes/functions in the FRAM-network.
 * It also contains functionality as how to find connections in the network, filter these connections,
 * search for nodes that contains certain values, and fileloader.
 * 
 * @author Jonas Haraldsson
 *
 */

public class FramNodeList extends ArrayList<FramNode> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -935987379291262564L;
	transient private ArrayList<ActionListener> listChangedRecipients;
	
	private String name;
	private ArrayList<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();
	
	public ArrayList<ConnectionInfo> getConnections() {
		return connections;
	}
	
	public FramNodeList(String name){
		init();
		
		this.name = name;
	}
	
	public void init() {
		listChangedRecipients = new ArrayList<ActionListener>();
		
		for(FramNode node : this) {
			node.init();
		}
	}
	
	public void setName(String name) {
		this.name = name;
		listChanged("NameChanged");
	}
	public String getName(){
		return name;
	}
	
	public void moveUpNode (FramNode node){
		int curPos = 0;
		int newPos = 0;
		
		for(int i = 0; i < this.size(); i++){
			if (this.get(i).getName().equalsIgnoreCase(node.getName())) {
				curPos = i;
				break;
			}
		}
		newPos = curPos -1;
		
		System.out.println(curPos);
		System.out.println(newPos);
		if (newPos > 0) {
			
			this.set(curPos, this.get(newPos));
			this.set(newPos, node);
		}
	}
	
	public void moveDownNode (FramNode node){
		int curPos = 0;
		int newPos = 0;
		
		for(int i = 0; i < this.size(); i++){
			if (this.get(i).getName().equalsIgnoreCase(node.getName())) {
				curPos = i;
				break;
			}
		}
		newPos = curPos +1;
		
		System.out.println(curPos);
		System.out.println(newPos);
		if (newPos < this.size()) {
			
			this.set(curPos, this.get(newPos));
			this.set(newPos, node);
		}
	}
	
	public boolean isPositionFree(FramNode sender, Rectangle rect) {		
		for(FramNode n : this) {
			if(n != sender && n.getRectangle(true).intersects(rect)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean add(FramNode o) {
		
		if(o.getName() == "") {
			int i = 1;
			String name;
			do{
				name = "Ny Nod" + " " + i;
				i++;
			}while(this.getAllNames().contains(name));
			
	//		framNodeEditorList.add(new FramNode(name));
			
			o.setName(name);
		}
		
		boolean result = super.add(o);
		if(result) {
			o.setList(this);
			o.setPosition(new Point(0, 0));
		}
		
		this.listChanged("NodeAdded");
		
		o.addNodeChangedListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(contains(e.getSource())) {
					listChanged("NodeChanged");
				}
			}
						
		});
		
		return result;
	}
	
	
	/**
	 * A function that overrides arraylists own set function, not used for the moment
	 * @param pos
	 * @param o
	 * @return
	 */
	public FramNode set(int pos, FramNode o) {
				
		FramNode result = super.set(pos, o);
		//if(result.getList()) {
			o.setList(this);
			o.setPosition(new Point(0, 0));
		//}
		
		this.listChanged("NodeAdded");
		
		o.addNodeChangedListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(contains(e.getSource())) {
					listChanged("NodeChanged");
				}
			}
						
		});
		
		return result;
	}

	public boolean remove(FramNode o) {
		boolean result = super.remove(o);
		
		removeAllConnectionsForNode(o);
		
		this.listChanged("NodeRemoved");
		
		return result;
	}
	
	/**
	 * Removes all connections in the list of all connections for a certain node, 
	 * operates on the whole network
	 * 
	 * @param node FramNode
	 */
	
	private void removeAllConnectionsForNode(FramNode node) {
		ArrayList<ConnectionInfo> toRemove = new ArrayList<ConnectionInfo>(); 
		
		for(ConnectionInfo connInfo : connections) {
			if(connInfo.getFrom().getNode() == node || 
					connInfo.getTo().getNode() == node) {
				toRemove.add(connInfo);
			}
		}
		
		for(ConnectionInfo connInfo : toRemove) {
			connections.remove(connInfo);
		}
	}
	
	/**
	 * getAllNames
	 * 
	 * @return A ArrayList with the names of all the nodes
	 */
	public ArrayList<String> getAllNames(){
		//String[] allNames = new String[this.size()];
		ArrayList<String> allNames = new ArrayList<String>();
		
		for(int i =0; i< this.size(); i++){
			allNames.add(this.get(i).getName());			
		}
		return allNames;
		
	}
	/**
	 * LoadFile
	 * @param filename
	 * @return FramNodeList created from the file
	 */
	public static FramNodeList LoadFile(String filename){
		FramNodeList list = (FramNodeList)Filemanager.loadFile(filename);
		list.init();
		return list;
		
	}
	/**
	 * 
	 * @param filename 
	 */
	public void SaveFile(String filename){
		Filemanager.saveFile(this, filename);
	}
	
	public void SaveXMLFile(String filename){
		Filemanager.saveXMLFile(this, filename);
	}
	
	/**
	 * Returns an arraylist<string> with all the currently used aspects in the network
	 * 
	 * @return arraylist<string>
	 */
	
	public String[] getAllAspects() {
		return getAllAspects(false);
	}
	
	public String[] getAllAspects(boolean emptyFirst) {
		ArrayList<String> allEntities = new ArrayList<String>();
		
		if(emptyFirst) {
			allEntities.add("");
		}
		
		for(int i =0; i< this.size(); i++){
			String[] nodeEntities = this.get(i).getAllEntities();
			for(int j = 0; j < nodeEntities.length; j++) {
				if(!allEntities.contains(nodeEntities[j])) {
					allEntities.add(nodeEntities[j]);			
				}
			}
		}
			
		String[] allEntitiesArray = new String[allEntities.size()];
		
		allEntities.toArray(allEntitiesArray);
		
		return allEntitiesArray;
	}
	
	/**
	 * Used in the filter/search box
	 * 
	 * Returns an FramNodeList with all the nodes that contains a name, aspect or comment that you've searched for
	 * 
	 * @return FramNodeList filtered result 
	 */
	
	public void setVisibilityFilter(String searchedFor) {
	
		for(FramNode node : this){
			
			String name = node.getName();
			boolean visible = false;
			
			if(name.toLowerCase().startsWith(searchedFor.toLowerCase())){
				visible = true;
			}
			ArrayList<String[]> aspects = node.getAllAspects();
			for(int j = 0; j < aspects.size(); j++) {
				if (aspects.get(j)[1].toString().toLowerCase().startsWith(searchedFor.toLowerCase())){
					visible = true;
					break;
				}
				if (aspects.get(j)[2].toString().toLowerCase().startsWith(searchedFor.toLowerCase())){
					visible = true;
					break;
				}
			}
			
			node.setFilterVisible(visible);
		}
		
	}
	
	
	/**
	 * Finds all the connections in the network and creates an arraylist with objects containing info as function name, connection port and aspect
	 * 
	 * Get the list of all potentiall connections for the first node. It takes the first aspect and searches all other nodes. For each other 
	 * found port that have the same aspectvalue it is saved to an arraylist. Already searched aspects is ignored.
	 * Pseduconnections with goes from a port in a node to the same port on that same node is ignored.
	 * 
	 * After it searched all the aspects in the first node it continues and does the same thing with the next node.
	 *
	 * Then it checks how the new search result diff from an earlier and updates it.
	 */
	
	public void updateConnections(){
		FramNode node;
		String searchValue;
		ArrayList<ConnectionInfo> foundConnections = new ArrayList<ConnectionInfo>();
		ArrayList<String> alreadyFound = new ArrayList<String>();
		
			
		for(int i=0;i<this.size();i++){
			node = this.get(i);
			for(int currentFromAspect=0; currentFromAspect<node.getAllAspects().size();currentFromAspect++){
				String[] connectionFrom = node.getAllAspects().get(currentFromAspect);
				
				searchValue = connectionFrom[1];   //connectionFrom[0] contains the port
				
					alreadyFound.add(searchValue);

					for(int j=i;j<this.size();j++){			
						for(String[] connectionTo : this.get(j).getAllAspects() ){
							if(searchValue.equals(connectionTo[1])){
								RelationInfo fromNode = new RelationInfo(node, connectionFrom[0]);
								RelationInfo toNode = new RelationInfo(this.get(j), connectionTo[0]);
								if(!searchValue.equals("") && 												//Removes matches for "" searches
										!(fromNode.getFunctionName().equals(toNode.getFunctionName()) &&
										fromNode.getConnectionPort().equals(toNode.getConnectionPort()))){   //Filters out the connection to itself at the same port
									
								foundConnections.add(new ConnectionInfo(fromNode,toNode, searchValue));
								}
							}
						}	
				}
			}	
		}
		

		
		//This part is so we can save set variables like visibilty for a certain connection between several searches
		ArrayList<ConnectionInfo> temp = new ArrayList<ConnectionInfo>();
		
		//Find the new links
		for(int i = 0;i<foundConnections.size();i++){
			if(!connections.contains(foundConnections.get(i))){
				temp.add(foundConnections.get(i));
			}
		}
		//Removes invaild links in the old search result
		for(int i = 0;i<connections.size();i++){
			if(!foundConnections.contains(connections.get(i))){
				connections.remove(i);
			}
		}
		
		connections.addAll(temp);
		filterConnections();
		removeDuplicates();
		
	}
	
	/**
	 * 	Removes duplicate hits within a node, such as
	 *	Output (value) -> Input (value)
	 *	Input (value) -> Output(value) <--- duplicate
	 *
	 */
	private void removeDuplicates(){
		for(int i=0;i<connections.size();i++){
			ConnectionInfo cInfo = connections.get(i);
			for(int j=i+1;j<connections.size();j++){
				ConnectionInfo cInfo2 = connections.get(j);

				if(cInfo.getAspect().equals(cInfo.getAspect()) &&
						cInfo.getFrom().getConnectionPort().equals(cInfo2.getTo().getConnectionPort()) &&
						cInfo.getTo().getConnectionPort().equals(cInfo2.getFrom().getConnectionPort()))
				{
					connections.remove(j);
				}
			}
		}
	}
	
	/**
	 * Filters the connections for a node
	 *Connections that are considered invaild is
	 *Output - Output
	 *Input - input
	 *These connections are marked as lightgrey, and no longer hidden or removed to not confuse the user
	 */
	
	private void filterConnections(){
		//No output-output
		boolean filterOutputOutput = true;
		//No input - input
		boolean filterInputInput = true;
		//Only output can connect to precondition
		boolean filterNonePreCOutput = false;
		
		for(int i=0;i<connections.size();i++){
			
			if(filterOutputOutput){	
				if(connections.get(i).getFrom().getConnectionPort() == NodePort.Output &&
						connections.get(i).getTo().getConnectionPort() == NodePort.Output){
					
					connections.get(i).setLineColor(Color.LIGHT_GRAY);
				}
			}
			if(filterInputInput){
				if(connections.get(i).getFrom().getConnectionPort() == NodePort.Input &&
						connections.get(i).getTo().getConnectionPort() == NodePort.Input){
					connections.get(i).setLineColor(Color.LIGHT_GRAY);
				}				
			}
			if(filterNonePreCOutput){
				if((connections.get(i).getFrom().getConnectionPort() == NodePort.Preconditions ||
					connections.get(i).getTo().getConnectionPort() == NodePort.Preconditions) &&
						!(connections.get(i).getTo().getConnectionPort() == NodePort.Output ||
						connections.get(i).getFrom().getConnectionPort() == NodePort.Output	)){
					connections.get(i).setLineColor(Color.LIGHT_GRAY);
//					connections.get(i).setVisibility(false); 
					// Do not hide these lines untill the complete gui to toggle rules on and off is done
				}				
			}			
			
		}		
	}
	
	
	
	public void addListChangedListener(ActionListener listener) {
		listChangedRecipients.add(listener);
		
	}
	
	public void removeListChangedListener(ActionListener listener) {
		listChangedRecipients.remove(listener);
	}
	
	private void listChanged(String action) {
		updateConnections();
		
		for(FramNode n : this){
			n.updateSize();
		}
		
		ActionEvent event = new ActionEvent(this, 0, action);
		
		for(ActionListener listener : this.listChangedRecipients) {
			listener.actionPerformed(event);
		}
	}

	/**
	 * Compares the current list with another list and checkes if they are identical
	 * 
	 * @param Object
	 */
	
	public boolean equals(Object obj) {
		FramNodeList list;
		
		if(obj instanceof FramNodeList){
			list = (FramNodeList)obj;
		}
		else{
			return false;
		}
		if(list == null) {
			return false;
		}
		
		if(!list.getName().equals(this.getName())) {
			return false;
		}
		
		if(list.size() != this.size()) {
			return false;
		}
		
		for(int i = 0; i < this.size(); i++) {
			if(!this.get(i).equals(list.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void printConnections(ArrayList<ConnectionInfo> connections) {
		for(ConnectionInfo cInfo : connections){
			System.out.println(cInfo.getFrom().getFunctionName() + ":" + cInfo.getFrom().getConnectionPort() +" - " + cInfo.getAspect() + " - " + cInfo.getTo().getFunctionName() +":"+ cInfo.getTo().getConnectionPort() +
					"   (" + (cInfo.getVisibility() ? "visible" : "hidden") + ")");
		}
	}
}

package data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * FramNodeList is an ArrayList which store all the nodes/functions in the FRAM-network
 * 
 * @author Jonas Haraldsson
 *
 */

public class FramNodeList extends ArrayList<FramNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -935987379291262564L;
	private ArrayList<ActionListener> listChangedRecipients = new ArrayList<ActionListener>();
	
	private String name;
	private ArrayList<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();
	
	public ArrayList<ConnectionInfo> getConnections() {
		return connections;
	}
	
	public FramNodeList(String name){
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
		listChanged("NameChanged");
	}
	public String getName(){
		return name;
	}
	
	public boolean add(FramNode o) {
		boolean result = super.add(o);
		if(result) {
			o.setList(this);
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

	public boolean remove(FramNode o) {
		boolean result = super.remove(o);
		
		this.listChanged("NodeRemoved");
		
		return result;
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
		return Filemanager.loadFile(filename);
		
	}
	/**
	 * 
	 * @param list The FramNodeList which is to be saved
	 * @param filename 
	 */
	public void SaveFile(String filename){
		Filemanager.saveFile(this, filename);
	}
	/**
	 * Returns an arraylist<string> with all the currently used aspects in the network
	 * 
	 * @return arraylist<string>
	 */
	
	public String[] getAllAspects() {
		ArrayList<String> allEntities = new ArrayList<String>();
		
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
	 * Finds all the connections in the network and creates an arraylist with objects containing info as function name, connection port and aspect
	 * 
	 * Get the list of all potentiall connections for the first node. It takes the first aspect and searches all other nodes. For each other 
	 * found port that have the same aspectvalue it is saved to an arraylist. Already searched aspects is ignored.
	 * Pseduconnections with goes from a port in a node to the same port on that same node is ignored.
	 * 
	 * After it searched all the aspects in the first node it continues and does the same thing with the next node.
	 *
	 *Then it checks how the new search result diff from an earlier and updates it.
	 */
	
	public ArrayList<ConnectionInfo> searchConnections(){
		FramNode node;
		String searchValue;
		ArrayList<ConnectionInfo> foundConnections = new ArrayList<ConnectionInfo>();
		ArrayList<String> alreadyFound = new ArrayList<String>();
		
			
		for(int i=0;i<this.size();i++){
			node = this.get(i);
			for(String[] connectionFrom : node.getAllAspects()){
				searchValue = connectionFrom[1];   //connectionFrom[0] contains the port
				
				if(!alreadyFound.contains(searchValue)){		//check if the aspect already been searched for
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
				
		return connections ;
	}
	
	public void addListChangedListener(ActionListener listener) {
		listChangedRecipients.add(listener);
		
	}
	
	public void removeListChangedListener(ActionListener listener) {
		listChangedRecipients.remove(listener);
	}
	
	private void listChanged(String action) {
		ActionEvent event = new ActionEvent(this, 0, action);
		
		for(ActionListener listener : this.listChangedRecipients) {
			listener.actionPerformed(event);
		}
	}

}

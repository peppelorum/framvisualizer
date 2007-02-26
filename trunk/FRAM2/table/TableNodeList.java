package table;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.BoxLayout;

import data.FramNode;
import data.FramNodeList;


public class TableNodeList extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1011531247926476853L;
	
	private FramNodeList list;
	
	private ActionListener listChangedListener;
	
	
	public TableNodeList(FramNodeList list) {
		listChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateElements();
			}
		};
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.setList(list);
		
	}
	
	public TableNodeList(){
		listChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateElements();
				
			}
			
		};
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.setList(new FramNodeList("ny"));
	}
	
	public void add(FramNode item){
		list.add(item);
	}
	
	public FramNodeList getList() {
		return list;
	}
	public void setList(FramNodeList value) {
		cleanUp();
		
		list = value;
		
		list.addListChangedListener(listChangedListener);
		
		updateElements();
	}
	
	
	public void updateElements() {
		for(Component c : this.getComponents()) {
			FramGuiNode guiNode = (FramGuiNode)c;
			if(!list.contains(guiNode.getNode())) {
				guiNode.cleanUp();
				remove(guiNode);
			}
		}
		
		for(FramNode node : list) {
			boolean isHere = false;
			for(Component c : this.getComponents()) {
				FramGuiNode guiNode = (FramGuiNode)c;
				if(node == guiNode.getNode()) {
					isHere = true;
				}
			}
			if(!isHere) {
				add(new FramGuiNode(node));
			}
		}
		
		validate();
	}
	
	private void cleanUp() {
		if(list != null) {
			list.removeListChangedListener(listChangedListener);
		}
	}

}

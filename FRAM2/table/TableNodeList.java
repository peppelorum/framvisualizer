package table;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;

import data.Aspect;
import data.FramNode;
import data.FramNodeList;


public class TableNodeList extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1011531247926476853L;
	
	private FramNodeList list;
	
	private ActionListener listChangedListener;
	private boolean stepTwoVisible;
	
	private Aspect selectedAspect;
	
	private ArrayList<ActionListener> selectedAspectChangedRecipients;
	
	public TableNodeList(FramNodeList list) {
		selectedAspectChangedRecipients = new ArrayList<ActionListener>();
		listChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateElements();
			}
		};
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.setList(list);
		
	}
	
	public TableNodeList(){
		selectedAspectChangedRecipients = new ArrayList<ActionListener>();
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
	
	
	private void updateElements() {
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
		
		setStepTwoVisible(getStepTwoVisible());
		refresh();
	}
	
	public Component add(Component c) {
		if(c instanceof FramGuiNode) {
			FramGuiNode guiNode = (FramGuiNode)c;
			guiNode.getTableNode().addSelectedChangedListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					TableNode tableNode = (TableNode)e.getSource();
					setSelectedAspect(tableNode.getSelectedAspect());
					
				}
				
			});
		}
		
		return super.add(c);
	}
	
	private void cleanUp() {
		if(list != null) {
			list.removeListChangedListener(listChangedListener);
		}
	}

	public void setStepTwoVisible (boolean value) {
		stepTwoVisible = value;
		
		for(Component c : this.getComponents()) {
			FramGuiNode guiNode = (FramGuiNode)c;
			guiNode.setStepTwoVisible(stepTwoVisible);
		}
		
		refresh();
	}
	
	public boolean getStepTwoVisible() {
		return stepTwoVisible;
	}
	
	private void refresh() {
		validate();
		repaint();
	}
	
	private void setSelectedAspect(Aspect value) {
		this.selectedAspect = value;
		selectedAspectChanged();
	}
	
	public Aspect getSelectedAspect() {
		return selectedAspect;
	}
		
	public void addSelectedAspectChangedListener(ActionListener listener) {
		this.selectedAspectChangedRecipients.add(listener);
	}
	
	public void removeSelectedAspectChangedListener(ActionListener listener) {
		this.selectedAspectChangedRecipients.remove(listener);
	}
	
	public void selectedAspectChanged() {
		for(ActionListener listener : this.selectedAspectChangedRecipients) {
			listener.actionPerformed(new ActionEvent(this, 0, ""));
		}
	}
}

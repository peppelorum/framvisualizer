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


public class FramNodeEditorList extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1011531247926476853L;
	
	private FramNodeList list;
	private ActionListener listChangedListener;
	private Aspect selectedAspect;
	private ArrayList<ActionListener> selectedAspectChangedRecipients;
	private FramNodeEditor selectedNodeEditor;
	
	public FramNodeEditorList(FramNodeList list) {
		selectedAspectChangedRecipients = new ArrayList<ActionListener>();
		listChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateElements();
			}
		};
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.setList(list);
		
	}
	
	public FramNodeEditorList(){
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
			FramNodeEditor guiNode = (FramNodeEditor)c;
			if(!list.contains(guiNode.getNode())) {
				guiNode.cleanUp();
				remove(guiNode);
			}
		}
		
		for(FramNode node : list) {
			boolean isHere = false;
			for(Component c : this.getComponents()) {
				FramNodeEditor guiNode = (FramNodeEditor)c;
				if(node == guiNode.getNode()) {
					isHere = true;
				}
			}
			if(!isHere) {
				add(new FramNodeEditor(node, this));
				
			}
		}
		
		refresh();
	}
	
	public Component add(Component c) {
		if(c instanceof FramNodeEditor) {
			FramNodeEditor guiNode = (FramNodeEditor)c;
			guiNode.getTableNode().addSelectedChangedListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					FramAspectTable tableNode = (FramAspectTable)e.getSource();
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
	
	private void refresh() {
		validate();
		repaint();
	}
	
	private void setSelectedAspect(Aspect value) {
		this.selectedAspect = value;
		fireAction("Selected aspect changed");
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
	
	public void fireAction(String action) {
		for(ActionListener listener : this.selectedAspectChangedRecipients) {
			listener.actionPerformed(new ActionEvent(this, 0, action));
		}
	}
	
	public FramNode getSelectedNode() {
		if(getSelectedNodeEditor() != null) {
			return getSelectedNodeEditor().getNode();
		}
		
		return null;
	}
	
	public FramNodeEditor getSelectedNodeEditor() {
		return selectedNodeEditor;
	}
		
	public void setSelectedNodeEditor(FramNodeEditor value) {
		if(selectedNodeEditor != null) {
			selectedNodeEditor.deSelected();
			selectedNodeEditor = null;
		}
		
		if(selectedNodeEditor != value) {
			selectedNodeEditor = value;
			fireAction("Selected node changed");
			value.selected();
		}
	}
	
	public void setSelectedNode(FramNode node) {
		setSelectedNodeEditor(getEditorForNode(node));
	}
	
	public FramNodeEditor getEditorForNode(FramNode node) {
		for(Component c : this.getComponents()) {
			if(c instanceof FramNodeEditor) {
				FramNodeEditor e = (FramNodeEditor)c;
				if(e.getNode() == node) {
					return e;
				}
			} 
		}
		return null;
	}
}

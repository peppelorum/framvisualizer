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

import gui.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.SpringLayout;
import javax.swing.table.TableColumnModel;

import org.freehep.swing.layout.FlowScrollLayout;

import data.Aspect;
import data.FramFunction;
import data.FramFunctionList;


/**
 * Holds a list of framnodeeditors (which are jpanels)
 * @author petbe082
 *
 */
public class FramNodeEditorList extends JComponent {

	private static final long serialVersionUID = 1011531247926476853L;

	private FramFunctionList list;
	private ActionListener listChangedListener;
	private Aspect selectedAspect;
	private ArrayList<ActionListener> selectedAspectChangedRecipients;
	private FramNodeEditor selectedNodeEditor;
	private int prev;
	private TableColumnModel columnModel;
	private int[] widthOfCols;
	private GUI parent;

	public FramNodeEditorList(FramFunctionList list) {
		selectedAspectChangedRecipients = new ArrayList<ActionListener>();
		listChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateElements();
			}
		};

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		this.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
		
//		FlowScrollLayout flowScrollLayout = new FlowScrollLayout();
//		flowScrollLayout.setAlignment(FlowLayout.LEFT);
//		flowScrollLayout.setHgap(2);
//		this.setLayout(flowScrollLayout);
		
//		GridLayout experimentLayout = new GridLayout(0, 1, 2, 0);
//		this.setLayout(experimentLayout);
		
		
//        SpringLayout layout = new SpringLayout();
//        this.setLayout(layout);
		
		
//		this.setPreferredSize(new Dimension(33, 55));

		this.setList(list);
		this.prev = 0;

	}

//	public void setColumModel(TableColumnModel columnModel) {
//		this.columnModel = columnModel;
//	}
	
//	public void updateColumModel() {
////		System.out.println("APA!!!");
//		for(Component c : this.getComponents()) {
//			FramNodeEditor guiNode = (FramNodeEditor)c;
//			guiNode.setColumnModel(columnModel);
//		}
//	}
	
	public void updateColumWidth() {
		for(Component c : this.getComponents()) {
			FramNodeEditor guiNode = (FramNodeEditor)c;
			guiNode.getTableNode().forceWidthOfCols(widthOfCols);
		}
	}
	
	
	

	public void setWidthOfCols(int[] widthOfCols){
		this.widthOfCols = widthOfCols;
	}

	public FramNodeEditorList(){		
		this(new FramFunctionList("new"));
	}
	
	public FramNodeEditorList(GUI parent){
		this.parent = parent;
		new FramNodeEditorList();
	}
	
	public void setParent(GUI parent){
		this.parent = parent;
	}

	public void add(FramFunction item){	
		list.add(item);
	}

	/*
	 * Get the list with data
	 * */
	public FramFunctionList getList() {
		return list;
	}

	/*
	 * Set list (data class)
	 * */
	public void setList(FramFunctionList value) {
		cleanUp();

		list = value;

		list.addListChangedListener(listChangedListener);

		updateElements();
//		this.updateColumModel();
	}

	/*
	 * Ensure that all elements are in sync with the data class
	 * */
	private void updateElements() {
		for(Component c : this.getComponents()) {
			FramNodeEditor guiNode = (FramNodeEditor)c;
			if(!list.contains(guiNode.getNode())) {
				guiNode.cleanUp();
				remove(guiNode);
			} else {
//				guiNode.getTableNode().setWidthOfCols(widthOfCols);
//				guiNode.setColumnModel(this.columnModel);
			}
//			guiNode.setColumnModel(this.columnModel);
		}

		for(FramFunction node : list) {
			boolean isHere = false;
			for(Component c : this.getComponents()) {
				FramNodeEditor guiNode = (FramNodeEditor)c;
				/**
				 * TODO
				 */
				//guiNode.getTableNode().setCellsNonFocused();
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
//			guiNode.setColumnModel(this.columnModel);
		}

		return super.add(c);
	}

	/*
	 * Remove listeners to data class
	 * */
	private void cleanUp() {
		if(list != null) {
			list.removeListChangedListener(listChangedListener);
		}
	}

	/*
	 * Validate and repaint
	 * */
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

	public void addActionListener(ActionListener listener) {
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

	public FramFunction getSelectedNode() {
		if(getSelectedNodeEditor() != null) {
			return getSelectedNodeEditor().getNode();
		}

		return null;
	}

	public FramNodeEditor getSelectedNodeEditor() {
		return selectedNodeEditor;
	}

	/*
	 * Set selected editor
	 * */
	public void setSelectedNodeEditor(FramNodeEditor value) {
//		if(selectedNodeEditor != null && selectedNodeEditor != value) {
//		//selectedNodeEditor.deSelected();
//		selectedNodeEditor = null;
//		}

		if(selectedNodeEditor != value) {
			selectedNodeEditor = value;
			fireAction("Selected node changed");
//			value.selected();
		}

		// Scroll editor into visible position
		if(selectedNodeEditor != null) {
			this.scrollRectToVisible(selectedNodeEditor.getBounds());
		}
		refresh();

	}

	public void setSelectedNode(FramFunction node) {
		setSelectedNodeEditor(getEditorForNode(node));
	}

	/*
	 * Find the editor for the input node
	 * */
	public FramNodeEditor getEditorForNode(FramFunction node) {
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

	public void notifyOfUpdate() {
		// TODO Auto-generated method stub
		
		System.out.println("framnodeeditorlist got an update");
//		parent.notifyOfUpdate();
		updateColumWidth();
		
		
	}


}

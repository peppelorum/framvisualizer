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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import data.Aspect;
import data.FramNode;
import data.FramNodeList;

public class FramAspectTable extends JTable {

	private FramNode node;
	private FramNodeList list;
	private ActionListener nodeChangedListener;
	private Aspect selectedAspect;
	private ArrayList<ActionListener> selectedChangedRecipients;
	private FramNodeEditor parent;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3518691723356609315L;

	public FramAspectTable() {
		
		this(new FramNode(""), new FramNodeList(""), null);
	}
	
	public FramAspectTable(FramNode node, FramNodeList listInput, FramNodeEditor parent) {
		this.parent = parent;
		list = listInput;
		selectedChangedRecipients = new ArrayList<ActionListener>(); 
		
		nodeChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		};
		
		setNode(node);
		
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				selectedChanged();
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent arg0) {
				select();
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public FramNode getNode() {
		return node;
	}
	
	public void setNode(FramNode newNode) {
		cleanUp();
		
		node = newNode;

		setModel(new FramAspectTableModel(node));
		node.addNodeChangedListener(nodeChangedListener);
		
		updateTable();
		
		
	}
	
	public void setCellsNonFocused() {
		
		
		
		//for(int i = 0;i<this.getColumnCount();i++){
		
		
		
//		for(int j = 0;j<this.getRowCount();j++){       	
		
		System.out.println(this.getEditingColumn());
		System.out.println(this.getEditingRow());
		
		//System.out.println(j);
		
		//this.getCellEditor(j, i).cancelCellEditing();
		//this.getCellEditor(j, i).stopCellEditing();
		if (this.getEditingColumn() > -1 && this.getEditingRow() > -1) {
			this.getCellEditor(this.getEditingRow(), this.getEditingColumn()).stopCellEditing();
		}
		
	}
	
	private void updateTable() {     
		
        for(int i = 0;i<this.getColumnCount();i++){
        	//Sets the cell renderers
        	if (i == 3) {
        		this.getColumnModel().getColumn( i ).setCellRenderer(new ButtonRenderer());
        	} else {
            	this.getColumnModel().getColumn( i ).setCellRenderer(new CustomCellRenderer ());        		
        	}
 
        }
        
        this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn col = this.getColumnModel().getColumn(0);
        int width = 90;
        col.setMinWidth(width);
        col.setMaxWidth(width);
        col.setPreferredWidth(width);
	}
	
	public void cleanUp() {
		if(node != null) {
			node.removeNodeChangedListener(nodeChangedListener);
			FramAspectTableModel model = (FramAspectTableModel)getModel();
			model.cleanUp();
		}
	}
	
	
	public TableCellEditor getCellEditor(int row, int col)
	{
		//System.out.println(this.getValueAt(row, 0).toString());
		if (col == 3 && row == 0){
			
//			TableCellEditor editor = new ButtonEditor(new JCheckBox());
//			return editor;
			
			TableCellEditor editor = super.getCellEditor(row,col);
			return new ButtonEditor(editor, list, node);
		} else if (col == 0 || row == 0) {
			TableCellEditor editor = super.getCellEditor(row,col);
			return editor;
		} else if (col == 2){
			TableCellEditor editor = super.getCellEditor(row,col);
			return editor;
		} else if (col == 3){
			TableCellEditor editor = super.getCellEditor(row,col);
			return new ButtonInTableCell(editor);
		} else if(col == 1){
			ComboBoxAutoComplete combo = new ComboBoxAutoComplete(node.getList().getAllAspects(true));
			combo.setEditable(true);
			return new ComboBoxCellEditor(combo);
		} else {
			TableCellEditor editor = super.getCellEditor(row,col);
			return editor;
		}
		
		
		
//		if(col == 0) {
//			TableCellEditor editor = super.getCellEditor(row,col);
//			return editor;
//			
//		}
//		if(this.getValueAt(row, 0).toString().equalsIgnoreCase("Name") || col != 1) {
//			return new ButtonInTableCell(super.getCellEditor(row,col));			
//		}
//		else {
//			ComboBoxAutoComplete combo = new ComboBoxAutoComplete(node.getList().getAllAspects(true));
//			combo.setEditable(true);
//			return new ButtonInTableCell(new ComboBoxCellEditor(combo));
//		}

	}
	
	public void addSelectedChangedListener(ActionListener listener) {
		this.selectedChangedRecipients.add(listener);
	}
	
	public void removeSelectedChangedListener(ActionListener listener) {
		this.selectedChangedRecipients.remove(listener);
	}
	
	private void selectedChanged() {
		String type = (String)this.getValueAt(this.getSelectedRow(), 0);
		String name = (String)this.getValueAt(this.getSelectedRow(), 1);
			
		if (this.getSelectedRow() != 0) {
			Aspect aspect = node.getAspect(type, name);
			setSelectedAspect(aspect);
		}
		
		ActionEvent event = new ActionEvent(this, 0, "");
		
		for(ActionListener listener : this.selectedChangedRecipients) {
			listener.actionPerformed(event);
		}
	}
	
	private void setSelectedAspect(Aspect value) {
		this.selectedAspect = value;
	}
	
	public Aspect getSelectedAspect() {
		return selectedAspect;
	}
	
	public void select() {
		if(parent != null) {
			parent.select();
		}
	}
	
}

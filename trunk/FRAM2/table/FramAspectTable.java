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

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import data.Aspect;
import data.FramFunction;
import data.FramFunctionList;


/**
 * Holds the jtable for the aspects, uses FramAspectTableModel as its model 
 * @author petbe082
 *
 */

public class FramAspectTable extends JTable {
	
	private FramFunction node;
	private FramFunctionList list;
	private ActionListener nodeChangedListener;
	private Aspect selectedAspect;
	private ArrayList<ActionListener> selectedChangedRecipients;
	private FramNodeEditor parent;
	private FramAspectTableModel model;

	private static final long serialVersionUID = 3518691723356609315L;
	
	public FramAspectTable() {
		this(new FramFunction(""), new FramFunctionList(""), null);
	}
	
	/*
	 * Set the the second and fourth as non editable for the first row
	 * @see javax.swing.JTable#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int column) {
		if(row == 0) {
			if (column == 1 || column == 3) {
				return false;
			}
		}
		return super.isCellEditable(row, column);
	}
	
	public FramAspectTable(FramFunction node, FramFunctionList listInput, FramNodeEditor parent) {
		
		model = new FramAspectTableModel(node);
		
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
				Cursor cursor = new Cursor(Cursor. MOVE_CURSOR);
				setCursor(cursor);
				select();
			}
			
			public void mouseReleased(MouseEvent arg0) {
				Cursor cursor = new Cursor(Cursor. DEFAULT_CURSOR);
				setCursor(cursor);
			}
		});
	}
	
	public FramFunction getNode() {
		return node;
	}
	
	public void setNode(FramFunction newNode) {
		cleanUp();
		
		node = newNode;

		setModel(model);
		node.addNodeChangedListener(nodeChangedListener);
		
		updateTable();
	}
	
	private void updateTable() {
		
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn col = this.getColumnModel().getColumn(0);
		int width = 90;
		col.setMinWidth(width);
		col.setMaxWidth(width);
		col.setPreferredWidth(width);
		
		col = this.getColumnModel().getColumn(3);
		width = 30;
		col.setMinWidth(width);
		col.setMaxWidth(width);
		col.setPreferredWidth(width);
	}
	
	public void cleanUp() {
		if(node != null) {
			node.removeNodeChangedListener(nodeChangedListener);
			model = (FramAspectTableModel)getModel();
			model.cleanUp();
		}
	}
	
	/**
	 * Overrides the default celleditor, makes it possible to set editor for individual cells
	 */
	public TableCellEditor getCellEditor(int row, int col) {		
		if (col == 3 && row > 0){			
			TableCellEditor editor = super.getCellEditor(row,col);
			return new ButtonEditor(model, editor, list, node);
		} else if(col == 1){
			ComboBoxAutoComplete combo = new ComboBoxAutoComplete(node.getList().getAllAspects(true));
			combo.setEditable(true);
			return new ComboBoxCellEditor(combo);
		} else {
			return super.getCellEditor(row,col);
		}
	}
	
	
	/**
	 * Overrides the default cellrenderer, makes it possible to set renderer for individual cells
	 */
	public TableCellRenderer getCellRenderer(int row, int col) {
		
		if (col == 3 && row > 0){
			return new ButtonRenderer();
		} else if(row == 0){
			return new SelectedRowRenderer ();
		} else if (row > 0){
			return new SelectedRowRenderer ();
		}else {
			return super.getCellRenderer(row,col);
		}
	}
	
	public void addSelectedChangedListener(ActionListener listener) {
		this.selectedChangedRecipients.add(listener);
	}
	
	public void removeSelectedChangedListener(ActionListener listener) {
		this.selectedChangedRecipients.remove(listener);
	}
	
	private void selectedChanged() {
		if(this.getSelectedRow() > 0) {
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
	
	public boolean isSelected() {
		return parent.isSelected();
	}
	
	public FramFunctionList getList() {
		return list;
	}
}

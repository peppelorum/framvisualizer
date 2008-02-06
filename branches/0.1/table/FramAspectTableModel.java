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
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import data.Aspect;
import data.FramFunction;
import data.FramFunctionList;
import data.FramFunction.NodePort;


public class FramAspectTableModel extends DefaultTableModel {
	
	private ActionListener nodeChangedListener;
	
	
	/**
	 * Listener for changes in a node table, this listener propagates changes from the jtable back to the framnode and framnodelist
	 */
	private TableModelListener currentTableModelListener = new TableModelListener() {

		public void tableChanged(TableModelEvent e) {
			if(node != null) {
			
				int changedRow = e.getFirstRow();
				String changedLabel = getValueAt(changedRow, 0).toString();
				String newName = getValueAt(changedRow, 1).toString();
				String newComment = getValueAt(changedRow, 2).toString();
				
				System.out.println("tableChanged: "+ newName);
						
				if(changedRow == 0) {
					node.setName(newName);
					node.setComment(newComment);
				}
				else {
					FramFunction.NodePort conn = NodePort.valueOf(changedLabel);
					ArrayList<Aspect> newValList = new ArrayList<Aspect>();
					
					for(int i = 0; i < getRowCount(); i++) {
						if(getValueAt(i, 0).toString().equals(conn.toString())) {
							newValList.add(new Aspect(
									getValueAt(i, 1).toString(), 
									getValueAt(i,2).toString()));
						}
					}					
					node.setAttributes(conn, newValList);
				}
			}
		}
		
	};
	private static final long serialVersionUID = 9008215613909069521L;
	FramFunction node;
	
	public FramAspectTableModel() {
		this(new FramFunction(""));
	}
	
	public FramAspectTableModel(FramFunction node) {
		nodeChangedListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == getNode()) {
				
					generateCells();
				}
			}
			
		};
		
		String[] colNames = {"Port", "Aspect", "Comment", "Actions"};
		this.setColumnIdentifiers(colNames);
		
		setNode(node);
		
		generateCells();
	}
	
		
	public void setNode(FramFunction newNode) {
		// TODO: remove list changed listener
		
		cleanUp();
		
		node = newNode;
		
		node.addNodeChangedListener(nodeChangedListener);
	}
		
	private void generateCells() {
		// Remove double listeners
		this.removeTableModelListener(currentTableModelListener);
		
		this.setColumnCount(4);
		
		for(int i = 0; i < this.getRowCount(); i++) {
			this.dataVector.clear();
		}
		
		Object[] rows = createNodeCells(node);
		for(Object row : rows) {
			this.addRow((Object[])row);
		}
		
		this.addTableModelListener(currentTableModelListener);
	}

	
	public Object[] createNodeCells(FramFunction node) {
	
		ArrayList<Object> rows = new ArrayList<Object>();
		rows.add(new Object[] { "Name", node.getName(), node.getComment()});
		
		NodePort[] cPoints = { 
				NodePort.Input, 
				NodePort.Output,
				NodePort.Preconditions,
				NodePort.Time,
				NodePort.Resources,
				NodePort.Control };
		
		for(NodePort cPoint : cPoints) {
			ArrayList<Aspect> aspects = node.getAttributes(cPoint);
			for(Aspect aspect : aspects) {
				String attr = aspect.getValue();
				String comment = aspect.getComment();
				rows.add(new Object[] { cPoint.toString(), attr, comment});
			}
			if(aspects.size() == 0) {
				rows.add(new Object[] { cPoint.toString(), "", ""});
			}
		}
		
		return rows.toArray();
		
	}
	
	
	public FramFunction getNode() {
		return node;
	}
	
	public FramFunctionList getList() {
		if(node==null) {
			return null;
		}
		else {
			return node.getList();
		}
	}
	
	public void cleanUp() {
		if(node != null) {
			node.removeNodeChangedListener(nodeChangedListener);
		}
	}

	
}

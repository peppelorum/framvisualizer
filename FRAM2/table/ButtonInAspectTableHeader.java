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

package table;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import data.FramFunction;
import data.FramFunctionList;

public class ButtonInAspectTableHeader implements ActionListener, TableCellEditor{
	public final Icon PLUS_ICON = new ImageIcon(getClass().getResource("plus.GIF")); 
	private TableCellEditor editor; 
	private JButton customEditorButton = new JButton(PLUS_ICON);
	private JButton moveUp = new JButton(PLUS_ICON);
	private JButton moveDown = new JButton(PLUS_ICON);
	protected JTable table; 
	protected int row, column; 
	
	private FramFunctionList list;
	private FramFunction node;
	
	public ButtonInAspectTableHeader(TableCellEditor editor, FramFunctionList lista, FramFunction nodea){ 
		this.editor = editor; 
		this.list = lista;
		this.node = nodea;
		customEditorButton.addActionListener(this); 
		
		// ui-tweaking 
		customEditorButton.setFocusable(false); 
		customEditorButton.setFocusPainted(false); 
		customEditorButton.setMargin(new Insets(0, 0, 0, 0));
		customEditorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("NEW NODE! NEW NODE READY!");
				addFunction(table, row, column);
				list.add(new FramFunction());
			}
		});
		
		moveUp.setFocusable(false); 
		moveUp.setFocusPainted(false); 
		moveUp.setMargin(new Insets(0, 0, 0, 0)); 
		moveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Move on up! And keep on wishing!");
				list.moveUpNode(node);
			}
		});
		
		moveDown.setFocusable(false); 
		moveDown.setFocusPainted(false); 
		moveDown.setMargin(new Insets(0, 0, 0, 0)); 
	} 
	
	public Component getTableCellEditorComponent(JTable table, Object value
			, boolean isSelected, int row, int column){ 
		JPanel panel = new JPanel(new FlowLayout()); 
		editor.getTableCellEditorComponent(table, value, true, row, column); 
		panel.add(customEditorButton);
		panel.add(moveUp);
		panel.add(moveDown);
		this.table = table; 
		this.row = row; 
		this.column = column; 
		return panel; 
	} 
	
	public Object getCellEditorValue(){ 
		return editor.getCellEditorValue(); 
	} 
	
	public boolean isCellEditable(EventObject anEvent){ 
//		if(editor !=null){
//		return editor.isCellEditable(anEvent); 
//		}
		return true;
	} 
	
	public boolean shouldSelectCell(EventObject anEvent){ 
		return editor.shouldSelectCell(anEvent); 
	} 
	
	public boolean stopCellEditing(){ 
		return editor.stopCellEditing(); 
	} 
	
	public void cancelCellEditing(){ 
		editor.cancelCellEditing(); 
	} 
	
	public void addCellEditorListener(CellEditorListener l){ 
		editor.addCellEditorListener(l); 
	} 
	
	public void removeCellEditorListener(CellEditorListener l){ 
		editor.removeCellEditorListener(l); 
	} 
	
	public final void actionPerformed(ActionEvent e){ 
		editor.cancelCellEditing(); 
	} 
	
	protected void addFunction(JTable table, int row, int column){ 
		JTextArea textArea = new JTextArea(10, 50); 
		Object value = table.getValueAt(row, column); 
		
		if(value!=null){ 
		textArea.setText((String)value); 
		textArea.setCaretPosition(0); 
		} 
		
		
		
		FramAspectTable tablenode = (FramAspectTable)table;
		System.out.println(tablenode.getName());
		
		System.out.println(row);
		
		//FramNode.NodePort conn = FramNode.NodePort.valueOf(table.getValueAt(row, 0).toString());
		
		//FramNode node = tablenode.getNode();
		//ArrayList<Aspect> aspList = node.getAttributes(conn);
		//aspList.add(new Aspect(""));
		//node.setAttributes(conn, aspList);
		
	}
}


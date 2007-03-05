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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;

import data.Aspect;
import data.FramNode;
import data.FramNodeList;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton editora;
	
//	Is it possible to avoid the permanent reference to the value???
//	(i.e. after editing has been stopped/cancelled?)
	private Object value;
	
//	public ButtonEditor(JButton b)
//	{
//		editora = b;
//		
//		editora.setContentAreaFilled(true);
//	}
	 
	private TableCellEditor editor; 
	private JButton addButton = new JButton(new ImageIcon(getClass().getResource("/icons/plus.GIF")));
	private JButton removeButton = new JButton(new ImageIcon(getClass().getResource("/icons/minus.GIF")));
	
	protected JTable table; 
	protected int row, column; 
		
	public ButtonEditor(TableCellEditor editor, FramNodeList lista, FramNode nodea){ 
		this.editor = editor; 

		Border empty;
		empty = BorderFactory.createEmptyBorder();
		
		// ui-tweaking 
		addButton.setFocusable(false); 
		addButton.setFocusPainted(false); 
		addButton.setMargin(new Insets(0, 0, 0, 0));
		addButton.setBorder(empty);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAspect(table, row, column);
			}
		});
		
		removeButton.setFocusable(false); 
		removeButton.setFocusPainted(false); 
		removeButton.setMargin(new Insets(0, 0, 0, 0)); 
		removeButton.setBorder(empty);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAspect(table, row, column);
				//list.moveUpNode(node);
			}
		});
	} 
	
	public JButton editor()
	{
		return editora;
	}
		
	public Component getTableCellEditorComponent(JTable table, Object value
			, boolean isSelected, int row, int column){ 
		JPanel panel = new JPanel(); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS)); 
		editor.getTableCellEditorComponent(table, value, isSelected, row, column); 
		panel.add(addButton);
		panel.add(removeButton);

		this.table = table; 
		this.row = row; 
		this.column = column; 
		return panel; 
	} 
	
	public Object getCellEditorValue()
	{
		return value;
	}
		
    protected void addAspect(JTable table, int row, int column){
    	
        JTextArea textArea = new JTextArea(10, 50);
        Object value = table.getValueAt(row, column);
       
        if(value!=null){
            textArea.setText((String)value);
            textArea.setCaretPosition(0);
        }
        
        FramAspectTable tablenode = (FramAspectTable)table;
    	
    	FramNode.NodePort conn = FramNode.NodePort.valueOf(table.getValueAt(row, 0).toString());
    	
    	FramNode node = tablenode.getNode();
    	ArrayList<Aspect> aspList = node.getAttributes(conn);
    	if (aspList.size() > 1){
    		aspList.add(row, new Aspect(""));
    	} else {
    		aspList.add(new Aspect(""));
    	}
    	
    	node.setAttributes(conn, aspList);
    }
    
    protected void removeAspect(JTable table, int row, int column){
    	
        JTextArea textArea = new JTextArea(10, 50);
        Object value = table.getValueAt(row, column);
       
        if(value!=null){
            textArea.setText((String)value);
            textArea.setCaretPosition(0);
        }
        
        FramAspectTable tablenode = (FramAspectTable)table;
    	
    	FramNode.NodePort conn = FramNode.NodePort.valueOf(table.getValueAt(row, 0).toString());
    	
    	FramNode node = tablenode.getNode();
    	ArrayList<Aspect> aspList = node.getAttributes(conn);
    	if (aspList.size() > 1){
    		aspList.remove(row-1);
    	}
    	
    	node.setAttributes(conn, aspList);
    }
}
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
import data.FramFunction;
import data.FramFunctionList;

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
	
	
	private FramAspectTableModel model;
	protected JTable table; 
	protected int row, column; 
		
	public ButtonEditor(FramAspectTableModel model, TableCellEditor editor, FramFunctionList lista, FramFunction nodea){ 
		this.editor = editor; 
		this.model = model;

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
			}
		});
	} 
	
	public TableCellEditor editor()
	{
		return editor;
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
        String aspect = table.getValueAt(row, 0).toString();
       
        if(value!=null){
            textArea.setText((String)value);
            textArea.setCaretPosition(0);
        }
        
        FramAspectTable tablenode = (FramAspectTable)table;
    	FramFunction.NodePort conn = FramFunction.NodePort.valueOf(aspect);

    	FramFunction node = tablenode.getNode();
    	ArrayList<Aspect> aspList = node.getAttributes(conn);
    	
    	
    	if (aspList.size() == 0){
    		aspList.add(new Aspect(""));
    		aspList.add(new Aspect(""));
    		node.setAttributes(conn, aspList);
    	} else {
    		model.insertRow(row + 1, new Object[] {aspect , "", ""});
    	}
    }
    
    protected void removeAspect(JTable table, int row, int column){
    	
        JTextArea textArea = new JTextArea(10, 50);
        Object value = table.getValueAt(row, column);
       
        if(value!=null){
            textArea.setText((String)value);
            textArea.setCaretPosition(0);
        }
        
        FramAspectTable tablenode = (FramAspectTable)table;
    	
        FramFunction.NodePort conn = FramFunction.NodePort.valueOf(table.getValueAt(row, 0).toString());
    	
    	
    	System.out.println(table.getValueAt(row, 0).toString());
    	System.out.println("Remove: "+ row);
    	System.out.println("ModelRows: "+ model.getRowCount());
    	
    	System.out.println(model.getValueAt(row, 1));
    	
    	model.removeRow(row);
    	
//    	model.removeRow(row);
//    	
//    	FramFunction node = tablenode.getNode();
//    	ArrayList<Aspect> aspList;
//
//    	int l = 0;
//    	for(FramFunction.NodePort port : FramFunction.NodePort.values()){
//    		if (port.equals(conn)) {
//    			break;
//    		}
//    		aspList = node.getAttributes(port);
//    		l += aspList.size();
//    		System.out.println("l: "+ l);
//    	}
//    	
//    	
////    	
//    	aspList = node.getAttributes(conn);
////    	
////    	
//    	System.out.println("Remove: "+ row);
//    	System.out.println(aspList.size());
//    	System.out.println(l);
//    	if (aspList.size() > 1){
////    		aspList.remove(row - l);
//    	}
////    	
//    	node.setAttributes(conn, aspList);
    }
}
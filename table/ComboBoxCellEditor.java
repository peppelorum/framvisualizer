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
import java.io.Serializable;
import javax.swing.*;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class ComboBoxCellEditor extends AbstractCellEditor implements ActionListener, TableCellEditor, Serializable {
    
    private JComboBox comboBox;
   
    
    public ComboBoxCellEditor(JComboBox comboBox) {
        this.comboBox = comboBox;
        this.comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
        // hitting enter in the combo box should stop cellediting (see below)
        this.comboBox.addActionListener(this);
        // remove the editor's border - the cell itself already has one
        ((JComponent) comboBox.getEditor().getEditorComponent()).setBorder(null);
    }
    
    private void setValue(Object value) {
        comboBox.setSelectedItem(value);
    }
    
    // Implementing ActionListener
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // Selecting an item results in an actioncommand "comboBoxChanged".
        // We should ignore these ones.
        
        // Hitting enter results in an actioncommand "comboBoxEdited"
        if(e.getActionCommand().equals("comboBoxEdited")) {
            stopCellEditing();
        }
    }
    
    // Implementing CellEditor
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }
    
    public boolean stopCellEditing() {
        if (comboBox.isEditable()) {
            // Notify the combo box that editing has stopped (e.g. User pressed F2)
            comboBox.actionPerformed(new ActionEvent(this, 0, ""));
        }
        fireEditingStopped();
        return true;
    }
    
    // Implementing TableCellEditor
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {   	
    	setValue(value);
        return comboBox;
    }
    
    // Implementing TreeCellEditor
//    public java.awt.Component getTreeCellEditorComponent(javax.swing.JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
//        String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);
//        setValue(stringValue);
//        return comboBox;
//    }    
}
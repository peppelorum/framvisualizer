package table;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;

import data.Aspect;
import data.FramNode;
import data.FramNodeList;

public class CheckboxEditor extends AbstractCellEditor implements TableCellEditor{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	Is it possible to avoid the permanent reference to the value???
//	(i.e. after editing has been stopped/cancelled?)
	private Object value;
	
	private JTable table; 
	private int row, col;
	
    final String DESELECTED_LABEL = "Deselected";
    final String SELECTED_LABEL = "Selected";
		
	public CheckboxEditor(JTable tab, int rowa, int cola){ 
		this.table = tab;
		this.row = rowa; 
		this.col = cola; 
	}
	
	public Component getTableCellEditorComponent(JTable tab, Object value
			, boolean isSelected, int rowa, int cola){ 
		JCheckBox panel = new JCheckBox(); 

		table = tab; 
		this.row = rowa; 
		this.col = cola; 
		panel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//addAspect(table, row, column);


				AbstractButton abstractButton = (AbstractButton) e
				.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				String newLabel = (selected ? SELECTED_LABEL : DESELECTED_LABEL);
				abstractButton.setText(newLabel);
								
				if (selected) {
					table.getModel().setValueAt(true, row, col);
				} else{
					table.getModel().setValueAt(false, row, col);
				}
				
//				System.out.println(newLabel);
				//System.out.println("New:" +table.getModel().getValueAt(row, col));
			}
		});
		return panel; 
	} 
	
	public Object getCellEditorValue()
	{
		return value;
	}
		
    
}
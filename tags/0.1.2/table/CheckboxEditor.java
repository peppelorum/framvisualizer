package table;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * Shows a checkbox in a jtable cell
 * @author petbe082
 *
 */
public class CheckboxEditor extends AbstractCellEditor implements TableCellEditor{
	
	private static final long serialVersionUID = 1L;
	
	private Boolean selected;
	
	private JTable table; 
	private int row, col;
	
	public CheckboxEditor(JTable tab, int rowa, int cola){ 
		super();
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
		
		panel.addItemListener(new ItemListener(){
			
			public void itemStateChanged(ItemEvent e) {			
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				boolean selecteda = abstractButton.getModel().isSelected();
				abstractButton.setSelected(selecteda);
				
				if (selecteda) {
					selected = true;
				} else {
					selected = false;
				}
				
				table.getModel().setValueAt(selected, row, col);
			}
		});
		return panel; 
	} 
	
	public Boolean getCellEditorValue()
	{
		return selected;
	}
}
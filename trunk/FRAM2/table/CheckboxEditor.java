package table;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class CheckboxEditor extends AbstractCellEditor implements TableCellEditor{

	private static final long serialVersionUID = 1L;

//	Is it possible to avoid the permanent reference to the value???
//	(i.e. after editing has been stopped/cancelled?)
	private Boolean selected;

	private JTable table; 
	private int row, col;

	final String DESELECTED_LABEL = "Deselected";
	final String SELECTED_LABEL = "Selected";

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

				//String newLabel = (selecteda ? SELECTED_LABEL : DESELECTED_LABEL);
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
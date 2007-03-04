package table;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class CheckboxRenderer extends Component implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	private Boolean selected;

	public CheckboxRenderer(Boolean sel){
		this.selected = sel;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean selecteda, boolean focused, int row, int col) {	

		JCheckBox panel = new JCheckBox();

		if(selected instanceof Boolean) {
			if (selected){
				panel.setSelected(true);
			} else {
				panel.setSelected(false);
			}
		}

		return panel; 
	}
	
	protected void setValue(Object value){
		selected = (Boolean)value;
	}
} 

package table;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class CheckboxRenderer extends Component implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	private boolean selected;

	public CheckboxRenderer(boolean sel){
		this.selected = sel;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean selecteda, boolean focused, int row, int col) {	

		JCheckBox panel = new JCheckBox();
//		if (table.getModel().getColumnName(col).equals("I") ) {
//			if (table.getModel().getValueAt(row, col)) {
			panel.setSelected(selected);
//			}
//		}
 
		return panel; 
	}
} 

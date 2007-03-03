package table;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class CheckboxRenderer extends Component implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	private boolean focused;
	private boolean selected;

	public CheckboxRenderer(boolean sel){
		this.selected = sel;
	}

	// Maybe it works ... it seems
	// (of course this is a cheat, but this isn't a real component anyway)
	public boolean hasFocus()
	{
		return focused;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean selecteda, boolean focused, int row, int col) {	

		
//		System.out.println(row);
//		System.out.println(col);
//		System.out.println(table.getModel().getColumnName(col));
		JCheckBox panel = new JCheckBox();
//		if (table.getModel().getColumnName(col).equals("I") ) {
//			if (table.getModel().getValueAt(row, col)) {
			panel.setSelected(selected);
//			}
//		}
 
		return panel; 
	}
} 

package table;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import data.FramNode;

public class FramCPCTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7411835284939157285L;

	public TableCellEditor getCellEditor(int row, int col)
	{
		if(col == 0) {
			TableCellEditor editor = super.getCellEditor(row,col);
			return editor;
			
		}
		else {
			ComboBoxAutoComplete combo = new ComboBoxAutoComplete(FramNode.stepTwoDefaultValues);
			combo.setEditable(true);
			return new ComboBoxCellEditor(combo);
		}
		
	}
}

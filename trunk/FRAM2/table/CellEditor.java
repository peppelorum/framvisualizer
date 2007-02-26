package table;

import javax.swing.table.TableCellEditor;

public class CellEditor {
	private int row;
	private int col;
	private TableCellEditor e;

	public CellEditor(int row, int col, TableCellEditor e){
		this.row=row;
		this.col =col;
		this.e = e;
	}
	
	public int getCol() {
		return col;
	}
	public int getRow() {
		return row;
	}

	public TableCellEditor getEditor() {
		return e;
	}

	public void setEditor(TableCellEditor e) {
		this.e = e;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public void setRow(int row) {
		this.row = row;
	}
}

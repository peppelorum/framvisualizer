package table;
import javax.swing.*;
import javax.swing.table.*;

import java.util.ArrayList;
import java.util.Vector;

@SuppressWarnings("serial")
public class JTableX extends JTable {
	protected RowEditorModel rm;
	protected CellEditorModel cellm;
	
	public JTableX()
	{
		super();
		rm = null;
		cellm = null;
	}
	
	public JTableX(TableModel tm)
	{
		super(tm);
		rm = null;
		cellm = null;
	}
	
	public JTableX(TableModel tm, TableColumnModel cm)
	{
		super(tm,cm);
		rm = null;
		cellm = null;
	}
	
	public JTableX(TableModel tm, TableColumnModel cm,
			ListSelectionModel sm)
	{
		super(tm,cm,sm);
		rm = null;
		cellm = null;
	}
	
	public JTableX(int rows, int cols)
	{
		super(rows,cols);
		rm = null;
		cellm = null;
	}
	
	public JTableX(final Vector rowData, final Vector columnNames)
	{
		super(rowData, columnNames);
		rm = null;
		cellm = null;
	}
	
	public JTableX(final Object[][] rowData, final Object[] colNames)
	{
		super(rowData, colNames);
		rm = null;
		cellm = null;
	}
	
	// new constructor
	public JTableX(TableModel tm, RowEditorModel rm)
	{
		super(tm,null,null);
		this.rm = rm;
		cellm = null;
	}
	public JTableX(TableModel tm, CellEditorModel cellm)
	{
		super(tm,null,null);
		this.cellm = cellm;
		this.rm = null;
	}
	
	
	public void setCellEditorModel(CellEditorModel cellm)
	{
		this.cellm = cellm;
	}
	
	public CellEditorModel getCellEditorModel()
	{
		return cellm;
	}
	
	public void setRowEditorModel(RowEditorModel rm)
	{
		this.rm = rm;
	}
	
	public RowEditorModel getRowEditorModel()
	{
		return rm;
	}
	
	public TableCellEditor getCellEditor(int row, int col)
	{
		int modelColumn = convertColumnIndexToModel( col );
		AddRowCellEditor addRow = new AddRowCellEditor(rm.getEditor(row));
//		if(modelColumn==2){
//			return addRow;
//		}
		
		if (!(modelColumn == 1) || (modelColumn == 2)){
			return super.getCellEditor(row,col);
		}
		
		
		TableCellEditor tmpEditor = null;
		if (rm!=null)
			tmpEditor = rm.getEditor(row);
		if (tmpEditor!=null)
			return tmpEditor;

		return super.getCellEditor(row,col);
	}
}

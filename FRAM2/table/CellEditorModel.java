package table;

import javax.swing.table.*;
import java.util.*;
public class CellEditorModel
{
	private ArrayList<CellEditor> data;
	
	public CellEditorModel()
	{
		data = new ArrayList<CellEditor>();
	}
	public void addEditor(int row, int col, TableCellEditor e )
	{
		data.add(new CellEditor(row,col, e));
	}
	public void removeEditor(int row, int col)
	{
		for(int i=0;i<data.size();i++){
			if(data.get(i).getCol() == col && data.get(i).getRow() == row){
				data.remove(i);
			}
		}
		System.out.println("vad?");
	}
	public TableCellEditor getEditor(int row, int col)
	{
		for(int i=0;i<data.size();i++){
			if(data.get(i).getCol() == col && data.get(i).getRow() == row){
				return data.get(i).getEditor();
			}
		}
		return null;
	}
}


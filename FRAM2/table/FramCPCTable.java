/**

 	A visualizer for FRAM (Functional Resonance Accident Model).
 	This tool helps modelling the the FRAM table and visualize it.
	Copyright (C) 2007  Peppe Bergqvist <peppe@peppesbodega.nu>, Fredrik Gustafsson <fregu808@student.liu.se>,
	Jonas Haraldsson <haraldsson@gmail.com>, Gustav Ladén <gusla438@student.liu.se>
	http://sourceforge.net/projects/framvisualizer/
	
	This program is free software; you can redistribute it and/or
	modify it under the terms of the GNU General Public License
	as published by the Free Software Foundation; either version 2
	of the License, or (at your option) any later version.
	
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  
 **/

package table;

import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import data.CPC;
import data.FramNode;

public class FramCPCTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7411835284939157285L;
	
	private CPC cpc;
	
	public FramCPCTable(){
		
	}
	
	public FramCPCTable(CPC cpc){
		setCPC(cpc);
	}

	public void setCPC(CPC value) {
		this.cpc = value;
		
		if(cpc == null) {
			if(this.getColumnModel().getColumnCount() > 0) {
				this.getColumnModel().getColumn(0).setHeaderValue("");
			}
			this.setVisible(false);
		} 
		else {
			this.setVisible(true);
			setModel(new FramCPCTableModel(cpc));
		}
	}
	
	public TableCellEditor getCellEditor(int row, int col)
	{
		if(col != 1) {
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

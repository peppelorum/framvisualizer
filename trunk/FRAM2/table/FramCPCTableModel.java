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

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import data.CPC;
import data.CPCAttribute;

@SuppressWarnings("serial")
public class FramCPCTableModel extends DefaultTableModel {
	
	private CPC cpc;
	
	public FramCPCTableModel(CPC cpc){
	
		this.cpc = cpc;
		
		String title = "CPC attributes for '" + 
		cpc.getParent().getName() + 
			"' on node '" + cpc.getParent().getName() + "'";
		
		String[] colNames = {title, "Value", "Comment", "I", "O", "P", "R", "T", "C"};
		this.setColumnIdentifiers(colNames);
		this.setColumnCount(9);
		
		CPCAttribute cpcAttrib;		
		String[] row;
		for(String cpcType : CPC.CPC_TYPES) {
			cpcAttrib = cpc.getAttribute(cpcType);
			if(cpcAttrib != null) {
				row = cpcAttrib.toArray();
			}
			else {
				row = new String[] { cpcType, "", "" };
			}
			
			this.addRow(row);
		}
	
		this.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent arg0) {
				int row = arg0.getFirstRow();
				
				String type = (String)getValueAt(row, 0);
				String value = (String)getValueAt(row, 1);
				String comment = (String)getValueAt(row, 2);
				
				getCPC().setAttribute(type, value, comment);
			}
			
		});
	}
	
	public CPC getCPC() {
		return cpc;
	}

}

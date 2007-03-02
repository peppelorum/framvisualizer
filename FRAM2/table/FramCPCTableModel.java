/**

 	A visualizer for FRAM (Functional Resonance Accident Model).
 	This tool helps modelling the the FRAM table and visualize it.
	Copyright (C) 2007  Peppe Bergqvist <peppe@peppesbodega.nu>, Fredrik Gustafsson <fregu808@student.liu.se>,
	Jonas Haraldsson <haraldsson@gmail.com>, Gustav Lad�n <gusla438@student.liu.se>
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

import java.util.ArrayList;

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
		
		String title = "CPC attributes for node '" + cpc.getParent().getName() + "'";
		
		String[] colNames = {title, "Value", "Comment", "I", "O", "P", "R", "T", "C"};
		this.setColumnIdentifiers(colNames);
		this.setColumnCount(9);
		
		CPCAttribute cpcAttrib;		
		String[] row;
		Object[] row2;
		for(String cpcType : CPC.CPC_TYPES) {
			cpcAttrib = cpc.getAttribute(cpcType);
			if(cpcAttrib != null) {
				row2 = cpcAttrib.toArray();
				
				System.out.println(cpcType);
				
				ArrayList<Boolean> a = this.getCPC().getAttribute(cpcType).getCpcForAspects();
				
				
				System.out.println(a.size());
				
				for(int i = 0; i<a.size(); i++){
					System.out.println(i);
					
				}
				
			}
			else {
				row = new String[] { cpcType, "", "" };
				row2 = new Object[]{ cpcType, "", "", false, false, false, false, false, false};
			}
			
			this.addRow(row2);
			

			
			//this.setValueAt(aValue, row, column)
		}
	
		this.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent arg0) {
				int row = arg0.getFirstRow();
				
				String type = (String)getValueAt(row, 0);
				String value = (String)getValueAt(row, 1);
				String comment = (String)getValueAt(row, 2);
				
				ArrayList<Boolean> tmp;
				tmp = new ArrayList<Boolean>(6);
				
				tmp.add(0, (Boolean)getValueAt(row, 3));
				tmp.add(1, (Boolean)getValueAt(row, 4));
				tmp.add(2, (Boolean)getValueAt(row, 5));
				tmp.add(3, (Boolean)getValueAt(row, 6));
				tmp.add(4, (Boolean)getValueAt(row, 7));
				tmp.add(5, (Boolean)getValueAt(row, 8));
				
				
				//System.out.println(row);
				
//				for(int i = 3; i<8; i++){
//					
//					//System.out.print(getValueAt(row, i));
//					//System.out.println();
//					
//					System.out.println(i);
//					
////					tmp.add(i, (Boolean)getValueAt(row, i));
//					
//				}

				getCPC().setAttribute(type, value, comment, tmp);
			}
			
		});
	}
	
	public CPC getCPC() {
		return cpc;
	}

}

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

import java.awt.Component;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer extends DefaultTableCellRenderer 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 565106872194092865L;



	public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
		if(table instanceof FramAspectTable) {
			Component cell = super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
			if(row == 0){
				cell.setFont(new Font("Dialog",Font.BOLD,12));
				cell.setBackground(Color.lightGray);
	
				FramAspectTable aspectTable = (FramAspectTable)table;
				if(aspectTable.isSelected()) {
					cell.setBackground(Color.getHSBColor(0.6f, 0.3f, 0.9f));
				}
	
			} else{
				cell.setBackground(Color.white);
			}
		}

		return this;
	}
}
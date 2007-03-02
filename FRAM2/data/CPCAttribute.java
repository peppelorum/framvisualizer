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
package data;

import java.io.Serializable;
import java.util.ArrayList;

public class CPCAttribute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5591754827614386943L;
	private String type;
	private String value;
	private String comment;
	private ArrayList<Boolean> cpcForAspects;

	public CPCAttribute(String type, String value, String comment, ArrayList<Boolean> list) {
		setType(type);
		setValue(value);
		setComment(comment);
		cpcForAspects =  new ArrayList<Boolean>(6);
		cpcForAspects.add(false);
		cpcForAspects.add(false);
		cpcForAspects.add(false);
		cpcForAspects.add(false);
		cpcForAspects.add(false);
		cpcForAspects.add(false);
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public String getComment() {
		return comment;
	}
	
	public void setCpcForAspects(ArrayList<Boolean> list){
		cpcForAspects = list;
	}
	
	public ArrayList<Boolean> getCpcForAspects(){
		return cpcForAspects;
	}

	public void setType(String val) {
		if(!CPC.typeExists(val)) {
			throw new Error("CPC type doesn't exist");
		}
		this.type = val;
	}

	public void setValue(String val) {
		this.value = val;
	}

	public void setComment(String val) {
		this.comment = val;
	}

	public String[] toArray() {
		return new String[] { getType(), getValue(), getComment() };
	}
}

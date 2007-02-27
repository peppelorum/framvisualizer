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

package data;

/***
 * 
 * Stores and handles the value and comment of an aspect.
 * An aspect is the value connected to a certain port.
 * 
 * @author Jonas Haraldsson
 *
 */

public class Aspect implements java.io.Serializable {

	private static final long serialVersionUID = 8030263320025000436L;
	private String value;
	private String comment;
	private CPC cpc;
	
	public Aspect(String value){
		this.value = value;
		this.comment = "";
		cpc = new CPC();
	}
	
	public Aspect(String value, String comment){
		this.value = value;
		this.comment = comment;
		cpc = new CPC();
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getComment() {
		return comment;
	}
	public String getValue() {
		return value;
	}
	public CPC getCPC() {
		return cpc;
	}
	
	public boolean equals(Aspect aspect) {
		if(aspect == null) {
			return false;
		}
		
		if(!aspect.getComment().equals(this.getComment())) {
			return false;
		}

		if(!aspect.getValue().equals(this.getValue())) {
			return false;
		}
		
		return true;
	}
}

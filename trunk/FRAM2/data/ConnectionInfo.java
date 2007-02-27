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

import java.awt.Rectangle;

/**
 * 
 * Stores and handles the connections between two nodes.
 * 
 * @author Jonas Haraldsson
 *
 */

public class ConnectionInfo implements java.io.Serializable {

	private static final long serialVersionUID = -7708319713475166319L;
	private RelationInfo from;
	private RelationInfo to;
	private String aspect = "";
	private boolean visibility = true;
	
	
	public ConnectionInfo(RelationInfo from, RelationInfo to){
		this.from = from;
		this.to = to;
	}
	
	public ConnectionInfo(RelationInfo from, RelationInfo to, String aspect){
		this.from = from;
		this.to = to;
		this.aspect = aspect;
	}
	
	public RelationInfo getFrom(){
		return from;
	}
	public RelationInfo getTo(){
		return to;
	}
	public String getAspect(){
		return aspect;
	}
	public boolean getVisibility(){
		return visibility;
	}
	public void setAspect(String aspect){
		this.aspect = aspect;
	}
	public void setVisibility(boolean visibility){
		this.visibility = visibility;
	}

	public boolean equals(Object otherObj){
		ConnectionInfo object2 =(ConnectionInfo)otherObj;
		
		return this.getAspect().equals(object2.getAspect()) && 
		this.getFrom().compareTo(object2.getFrom()) && this.getTo().compareTo(object2.getTo());
	}
	
}

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

import data.FramFunction.NodePort;

public class RelationInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5282137696278770529L;
	private FramFunction function;
	private NodePort connectionType; //input, output, resources 

	public RelationInfo(FramFunction function, String connectionType){
		this.function = function;
		this.connectionType = NodePort.valueOf(connectionType);
	}
		
	public String getFunctionName(){
		return function.getName();
	}
	
	public FramFunction getNode() {
		return function;
	}
	
	public NodePort getConnectionPort(){
		return connectionType;
	}
	
	
	public boolean compareTo(RelationInfo comparee) {
		
		return ((comparee.getConnectionPort().equals(this.getConnectionPort())) &&
			(comparee.getFunctionName().equals(this.getFunctionName())));
	}
}

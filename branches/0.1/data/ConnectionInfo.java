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

import java.awt.Color;
import java.awt.Point;

import graph.GraphLine;

/**
 * 
 * Stores and handles the connections between two nodes. It also stores the information that
 * controls GraphLine. GrapLine contains the code handling the actually GUI drawing. 
 * 
 * @author Jonas Haraldsson
 *
 */

public class ConnectionInfo implements java.io.Serializable {

	private static final long serialVersionUID = -7708319713475166319L;
	private RelationInfo from;
	private RelationInfo to;
	private String aspect = "";
	
	//Graphics
	private static boolean showAll = false;
	private Point position = new Point(0, 0);
	private boolean moved = false;
	private Color lineColor = Color.black;
	private boolean visibility = true;
	private boolean filterVisible = true;
	
	transient GraphLine graphLine;
		
	public static void setShowAll(boolean showAll) {
		ConnectionInfo.showAll = showAll;
	}
	public static boolean isShowAll() {
		return showAll;
	}
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

	public void setAspect(String aspect){
		this.aspect = aspect;		
	}
	
	public void setGraphLine(GraphLine line) {
		this.graphLine = line;
	}
	
	public GraphLine getGraphLine() {
		return graphLine;
	}


	public boolean equals(Object otherObj){
		ConnectionInfo object2 =(ConnectionInfo)otherObj;
		
		return this.getAspect().equals(object2.getAspect()) && 
		this.getFrom().compareTo(object2.getFrom()) && this.getTo().compareTo(object2.getTo());
	}
	
	public boolean getVisibility(){
		return visibility;
	}
	
	/**
	 * Set if the line/label will be shown at all
	 * @param visibility
	 */
	public void setVisibility(boolean visibility){
		this.visibility = visibility;
	}
	
	public Point getPosition() {
		return position;
	}
	
	/**
	 * Set the position of the label in the middle of the line, used by GraphLine
	 * @param value
	 */
	public void setPosition(Point value) {
		position.x = value.x;
		position.y = value.y;
	}
	
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	public boolean isMoved() {
		return moved;
	}
	
	/**
	 * Sets the color of the connection line
	 * @param lineColor
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	public Color getLineColor() {
		return lineColor;
	}

	public boolean isFilterVisible() {
		return filterVisible;
	}
	
	/**
	 *Handles the filtering of nodes, controlled by the search box.
	 *If the node don't match the search this is set to false 
	 *
	 * @param val
	 */
	public void setFilterVisible(boolean val) {
		filterVisible = val;
	}
}

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

package graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;

import data.Aspect;
import data.FramNode;
import data.FramNode.NodePort;


public class GraphNode extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452948217399228756L;
	private FramNode node;
	private Visualizer parent;

	private Color nodeColorSel = Color.getHSBColor(0.6f, 0.3f, 0.7f);


	public GraphNode(FramNode node, Visualizer parent) {
		this.node = node;
		this.parent = parent;
	}

	public boolean isSelected() {
		return parent.getSelectedNode() == node;
	}

	public boolean isHovered() {
		return parent.getHoveredNode() == node;
	}

	public FramNode getNode() {
		return node;
	}

	public Point getCenter() {
		return node.getCenter();
	}

	public Polygon getPolygon() {
		return node.getPolygon();
	}

	public Point getCloserToCenter(Point input, int distance) {
		Point modified = (Point)input.clone();
		Point center = getCenter();

		if(modified.x > center.x) {
			modified.x -= distance;
		}
		else if(modified.x < center.x) {
			modified.x += distance;
		}

		if(modified.y > center.y) {
			modified.y -= distance;
		}
		else if(modified.y < center.y) {
			modified.y += distance;
		}

		return modified;
	}
	
	


	public void paintComponent(Graphics g) { 

		Color bgColor = node.getColor();

		if(isSelected()) {
			bgColor = nodeColorSel;				
		}

		g.setColor(bgColor);

		Polygon poly = getPolygon();

		g.fillPolygon(poly);

		g.setFont(new Font("Arial", 1, 10));
		
		for(NodePort conn : FramNode.NodePort.values()) {
			Rectangle portRect = node.getPortRectangle(conn);
			
			g.setColor(bgColor);
			g.drawLine(
					getCenter().x,
					getCenter().y, 
					portRect.x + node.getPortSize() / 2, 
					portRect.y + node.getPortSize() / 2);
			
			if(isSelected() && parent.getSelectedPort() == conn) {
				g.setColor(Color.white);
			}
			else {
				g.setColor(bgColor);
			}
			
			g.fillOval(portRect.x, portRect.y, portRect.width, portRect.height);

			if(isSelected() && parent.getSelectedPort() == conn) {
				g.setColor(bgColor);
				g.drawOval(portRect.x, portRect.y, portRect.width, portRect.height);
			}

			if(isSelected() && parent.getSelectedPort() == conn) {
				g.setColor(Color.black);
			}
			else {
				g.setColor(Color.white);
			}
			
			Point loc = getCloserToCenter(node.getPortLocation(conn), 2);
			loc.x -= 3;
			loc.y += 3;
			g.drawString(conn.toString().substring(0, 1), loc.x, loc.y);
		}			


	}
	/**
	 * Draw the name inside the node
	 * @param g
	 */
	public void paintName(Graphics g){
		int fontSize = 10;
		g.setFont(new Font("Arial", 1, fontSize));
		g.setColor(Color.BLACK);
		String name = node.getName();

		//crop the name if its too long
		while(name.length()>1 && g.getFontMetrics().stringWidth(name)> node.getSize()-5){
			name = name.substring(0,name.length()-1);	

		}
		if(name.length() != node.getName().length()){
			name = name.substring(0,name.length()-1);	
			name = name.trim();
			name = name + "...";	
		}
		g.drawString(
				name, 
				(node.getPosition().x+node.getSize()/2-(g.getFontMetrics().stringWidth(name))/2),
				node.getPosition().y+node.getSize()/2);
	}

	/**
	 * Draws the bubble with the node name
	 * @param g
	 */
	public void paintNameBubble(Graphics g) {
		int margin = 15;
		int bubbleHeight = 20;
		int bubbleRounded = 10;
			
		//Sets the name to the node name
		g.setFont(new Font("Arial", 1, 12));
		String nameString = node.getName();		
		//if a port is selected a new namestring is set
		if(parent.getSelectedPort() != null && isSelected()) {	
			nameString += " : " + parent.getSelectedPort().toString();	
		}
		
		//Sets the width of the bubble so it contains the text
		node.setBubbleWidth(margin+5 + g.getFontMetrics().stringWidth(nameString));
		
		//Checks if the info when a port is selected need to enlarge the bubble
		//This info is value and comments for each port, it also increase the height
		if(isSelected() && parent.getSelectedPort() != null) {
			g.setFont(new Font("Arial", 1, 10));
			for(Aspect asp : node.getAttributes(parent.getSelectedPort())) {	
				String s = asp.getValue() + (asp.getComment() != "" ? " \"" + asp.getComment() + "\"" : "");
				if(g.getFontMetrics().stringWidth(nameString) < g.getFontMetrics().stringWidth(s)){
					node.setBubbleWidth(margin+5 + g.getFontMetrics().stringWidth(s));
				}
				//Increases the height of the bubble when more aspects are added
				bubbleHeight += g.getFontMetrics().getHeight()-1;	
			}
		}
		g.setFont(new Font("Arial", 1, 12));	
	
		int bubbleWidth = node.getBubbleWidth();
		
		Point bubblePoint = getCenter();

		if(isSelected()) {
			//bubbleHeight = 100;
			if(parent.getSelectedPort() != null) {
				bubblePoint = node.getPortLocation(parent.getSelectedPort());
			}
		}

		// define coordinates for bubble
		Rectangle bubbleRect = new Rectangle(
				bubblePoint.x + (node.getSize()/3),
				bubblePoint.y - (node.getSize()-node.getSize()/3),
				bubbleWidth,
				bubbleHeight);

		// define the triangle
		Polygon triAngle = new Polygon();
		triAngle.addPoint(bubbleRect.x+1, bubbleRect.y + 3);
		triAngle.addPoint(bubblePoint.x, bubblePoint.y);
		triAngle.addPoint(bubbleRect.x+1, bubbleRect.y + (node.getSize()/4));

		// fill bubble background
		g.setColor(Color.white);
		g.fillRoundRect(
				bubbleRect.x, 
				bubbleRect.y, 
				bubbleRect.width, 
				bubbleRect.height, 
				bubbleRounded, 
				bubbleRounded);

		// draw bubble outline
		g.setColor(Color.black);
		g.drawRoundRect(
				bubbleRect.x, 
				bubbleRect.y, 
				bubbleRect.width, 
				bubbleRect.height, 
				bubbleRounded, 
				bubbleRounded);


		// fill and draw triangle
		g.setColor(Color.white);
		g.fillPolygon(triAngle);
		g.setColor(Color.black);
		g.drawLine(
				triAngle.xpoints[0], 
				triAngle.ypoints[0], 
				triAngle.xpoints[1], 
				triAngle.ypoints[1]);
		g.drawLine(
				triAngle.xpoints[2], 
				triAngle.ypoints[2], 
				triAngle.xpoints[1], 
				triAngle.ypoints[1]);

		// draw name
//		g.setFont(new Font("Arial", 1, 12));
		
		g.drawString(
				nameString, 
				bubbleRect.x + margin,
				bubbleRect.y + margin);
	
		if(isSelected()) {
			g.setFont(new Font("Arial", 1, 10));
			
			if(parent.getSelectedPort() != null) { 
				String[] cpcs = node.getCPCtext(parent.getSelectedPort());
				
				int counter = 0;
				for(Aspect asp : node.getAttributes(parent.getSelectedPort())) {
					g.drawString(
							asp.getValue() + 
								(asp.getComment() != "" ? " \"" + asp.getComment() + "\"" : ""),
							bubbleRect.x + margin,
							bubbleRect.y + margin*2 + g.getFont().getSize() * counter);
					counter++;
				}		
				counter++;
				
				for(int i = 0; i < cpcs.length; i++) {
					g.drawString(
							cpcs[i], 
							bubbleRect.x + margin,
							bubbleRect.y + margin*2 + g.getFont().getSize() * (i + counter));
				}
			}
			else {
				g.drawString(
						node.getComment(), 
						bubbleRect.x + margin,
						bubbleRect.y + margin*2);
			}

			
		}

	}




}
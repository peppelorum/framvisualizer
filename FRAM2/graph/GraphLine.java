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
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;

import javax.swing.JComponent;

import data.ConnectionInfo;

/**
 * Graphline defines how the lines between nodes are drawn and how the label in the middle looks
 * It relies on information in the ConnectionInfo class to know which size the label should have and where to put it
 *
 */
public class GraphLine extends JComponent {
	private static final long serialVersionUID = 4452948217399228756L;
	private ConnectionInfo connection;
	private Visualizer parent;

	public GraphLine(ConnectionInfo connection, Visualizer parent) {
		this.connection = connection;
		this.parent = parent;
	}

	public boolean isSelected() {
		return parent.getSelectedLine() == connection;
	}
	
	public ConnectionInfo getConnection() {
		return connection;
	}

	public Point getCenter() {		
		//connection.setMoved(false);
		if(connection.isMoved()){
			return connection.getPosition();

		}else{
			GeneralPath line = getLine();
			
			Point middle = new Point(
					(int)line.getBounds().getCenterX(), 
					(int)line.getBounds().getCenterY());
			connection.setPosition(middle);
			return middle;
		}
	}

	public GeneralPath getLine() {
		GraphNode nodeFrom = parent.getGuiNode(connection.getFrom().getNode());
		GraphNode nodeTo = parent.getGuiNode(connection.getTo().getNode());
		
		Point pointFrom = nodeFrom.getPort(connection.getFrom().getConnectionPort());
		Point pointTo = nodeTo.getPort(connection.getTo().getConnectionPort());	
		Point middle = connection.getPosition();
		
		
		//polyline
		float xPoints[] = {(float)pointFrom.getX(),(float)middle.getX(),(float)pointTo.getX()};
		float yPoints[] = {(float)pointFrom.getY(),(float)middle.getY(),(float)pointTo.getY()};
		
		GeneralPath polyline = 
			new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);
		
		polyline.moveTo(xPoints[0], yPoints[0]);

		for (int index = 1; index < xPoints.length; index++) {
		 	 polyline.lineTo(xPoints[index], yPoints[index]);
		}
		
		return polyline;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(!connection.getVisibility()) {
			return;
		}
		
		g.setColor(Color.getHSBColor(0.1F, 0.6F, 0.1F));
		if(isSelected()) {
			g.setColor(Color.BLUE);				
		}
		
		getCenter();
		GeneralPath line = getLine();
		
		g2.draw(line);
		}
	
	
	public void paintNameBubble(Graphics g) {
		if(!connection.getVisibility()) {
			return;
		}
		// calculate length of name
		g.setFont(new Font("Arial", 1, 9));
		connection.setBubbleWidth(8 + g.getFontMetrics().stringWidth(connection.getAspect()));

		
		int bubbleHeight = connection.getBubbleHeight();
		int bubbleWidth = connection.getBubbleWidth();
		int bubbleRounded = 5;
		
		// define coordinates for bubble
		Rectangle bubbleRect = new Rectangle(
				(int)connection.getPosition().getX() - bubbleWidth/2,
				(int)connection.getPosition().getY()- bubbleHeight/2,
				bubbleWidth,
				bubbleHeight);
		
		// fill bubble background
		if(isSelected()){
			g.setColor(Color.GRAY);
		}else{
			g.setColor(Color.white);
		}
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
		
		//draw name
		g.drawString(
				connection.getAspect(), 
				bubbleRect.x+5,
				bubbleRect.y+8);
	
	}	
	
}
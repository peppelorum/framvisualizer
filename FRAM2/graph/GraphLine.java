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
import data.FramFunction;

/**
 * Graphline defines how the lines between nodes are drawn and how the label in the middle looks
 * It relies on information in the ConnectionInfo class to know which size the label should have and where to put it
 *
 */
public class GraphLine extends JComponent {
	private static final long serialVersionUID = 4452948217399228756L;
	private ConnectionInfo connection;
	private Visualizer parent;
	
	private int bubbleHeight = 10;
	private int bubbleWidth = 40;

	private boolean showBubbles = true;
	
	
	public boolean getVisibility(){
		return connection.getVisibility();
	}
	
	public void setVisibility(boolean visibility){
		connection.setVisibility(visibility);
	}
	
	//Graphics
	public Point getPosition() {
		return connection.getPosition();
	}
	
	public void setPosition(Point value) {
		Point position = connection.getPosition();
		
		position.x = value.x;
		position.y = value.y;
	}
	
	public void setMoved(boolean moved) {
		connection.setMoved(moved);
	}
	public boolean isMoved() {
		return connection.isMoved();
	}
	
	public void setLineColor(Color lineColor) {
		connection.setLineColor(lineColor);
	}
	public Color getLineColor() {
		return connection.getLineColor();
	}
	
	public int getBubbleHeight() {
		return bubbleHeight;
	}
	public int getBubbleWidth() {
		return bubbleWidth;
	}
	public void setBubbleWidth(int bubbleWidth) {
		this.bubbleWidth = bubbleWidth;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(
				getPosition().x- bubbleWidth/2,
				getPosition().y- bubbleHeight/2,
				getBubbleWidth(),
				getBubbleHeight());
	}

	
	public void setShowBubbles(boolean value) {
		
		showBubbles = value;
		setBubbleWidth(2);
	}
	
	public boolean isShowBubbles() {
		return showBubbles;
	}
	
	public GraphLine(ConnectionInfo connection, Visualizer parent) {
		this.connection = connection;
		this.parent = parent;
		
		setPosition(getCenter());
	}

	public boolean isSelected() {
		return parent.getSelectedLine() == connection;
	}
	
	public ConnectionInfo getConnection() {
		return connection;
	}

	public Point getCenter() {		
		//connection.setMoved(false);
		if(isMoved()){
			return getPosition();

		}else{
			GeneralPath line = getLine();
			
			Point middle = new Point(
					(int)line.getBounds().getCenterX(), 
					(int)line.getBounds().getCenterY());
			setPosition(middle);
			return middle;
		}
	}

	public GeneralPath getLine() {
		FramFunction nodeFrom = connection.getFrom().getNode();
		FramFunction nodeTo = connection.getTo().getNode();
		
		Point pointFrom = nodeFrom.getPortLocation(connection.getFrom().getConnectionPort());
		Point pointTo = nodeTo.getPortLocation(connection.getTo().getConnectionPort());	
		Point middle = getPosition();
		
		
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
		if(!getVisibility() && ConnectionInfo.isShowAll()) {
			g.setColor(Color.PINK);
		}else if (!getVisibility()){
			return;
		}		
		else {
			g.setColor(getLineColor());			
		}
		//		g.setColor(Color.getHSBColor(0.1F, 0.6F, 0.1F));
		
		if(isSelected()) {
			g.setColor(Color.BLUE);				
		}
		
		getCenter();
		GeneralPath line = getLine();
		
		g2.draw(line);
		}
	
	
	public void paintNameBubble(Graphics g) {
		if (!((ConnectionInfo.isShowAll() || getVisibility()) && showBubbles)) {
			return;
		}
		// calculate length of name
		g.setFont(new Font("Arial", 1, 9));
		setBubbleWidth(8 + g.getFontMetrics().stringWidth(connection.getAspect()));

		
		int bubbleHeight = getBubbleHeight();
		int bubbleWidth = getBubbleWidth();
		int bubbleRounded = 5;
		
		// define coordinates for bubble
		Rectangle bubbleRect = new Rectangle(
				(int)getPosition().getX() - bubbleWidth/2,
				(int)getPosition().getY()- bubbleHeight/2,
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
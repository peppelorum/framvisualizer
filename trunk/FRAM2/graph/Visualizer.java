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

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JComponent;

import data.ConnectionInfo;
import data.FramNode;
import data.FramNodeList;


public class Visualizer extends JComponent {



	private FramNodeList list;
	private FramNode selectedNode;
	private ConnectionInfo selectedLine;
	private ArrayList<GraphNode> guiNodeList;
	private ArrayList<GraphLine> guiLineList;
	private Point mouseDownPoint;
	private Point nodeOriginalPoint;

	private Point offset = new Point(50, 50);
	private Point originalOffset;
	
	
	private Point connectionOriginalPoint;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3214964902776275054L;

	public Visualizer() {
		setList(new FramNodeList("empty"));
		
		init();
	}
	
	public Visualizer(FramNodeList list) {
		setList(list);
		
		init();
	}
	
	public FramNode getNodeAt(Point position) {
		FramNode node = null;
				
		for(FramNode n : list) {
			if(n.getRectangle().contains(position)) {
				node = n;
				break;
			}
		}
		
		return node;
	}
	
	public ConnectionInfo getConnectionAt(Point position){
		ConnectionInfo cInfo = null;
		
		for(ConnectionInfo c : list.getConnections()){
			if(c.getRectangle().contains(position)){
				cInfo = c;
				break;
			}
		}
		
		return cInfo;
	}
	
	private void selectNode(FramNode node) {
		if(node == null) {
			selectedNode = null;
			nodeOriginalPoint = null;
		}
		else {
			selectedLine = null;
			selectedNode = node;
			nodeOriginalPoint = (Point)node.getPosition().clone();
		}
	}
	private void selectConnection(ConnectionInfo cInfo) {
		if(cInfo == null) {
			selectedLine = null;
			selectedNode = null;
			nodeOriginalPoint = null;
		}
		else {
			selectedNode = null;
			selectedLine = cInfo;
			connectionOriginalPoint = (Point)cInfo.getPosition().clone();
		}
	}
	
	public FramNode getSelectedNode() {
		return selectedNode;
	}
	
	public ConnectionInfo getSelectedLine() {
		return selectedLine;
	}
		
	private void init() {
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				FramNode node = getSelectedNode();
				ConnectionInfo cInfo = getSelectedLine();
				
				Point newLocation = addOffset(arg0.getPoint());
				
				int xDiff = newLocation.x - mouseDownPoint.x;
				int yDiff = newLocation.y - mouseDownPoint.y;
				
				if(node != null) {					
					node.setPosition(new Point(
							nodeOriginalPoint.x + xDiff,
							nodeOriginalPoint.y + yDiff));
					
					repaint();
					
				}else if(cInfo != null){
					
					cInfo.setPosition(new Point(
							connectionOriginalPoint.x + xDiff,
							connectionOriginalPoint.y + yDiff));
					cInfo.setMoved(true);
					repaint();
				}
				else {
										
					mouseDownPoint.x -= offset.x;
					mouseDownPoint.y -= offset.y;
					
					offset.x = originalOffset.x + xDiff;
					offset.y = originalOffset.y + yDiff;
					
					mouseDownPoint.x += offset.x;
					mouseDownPoint.y += offset.y;
					
					repaint();
				}
				
			}

			public void mouseMoved(MouseEvent arg0) {
				
				
			}
			
		});
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				
				
			}

			public void mouseEntered(MouseEvent arg0) {
				
				
			}

			public void mouseExited(MouseEvent arg0) {
				
				
			}

			public void mousePressed(MouseEvent arg0) {
				mouseDownPoint = addOffset(arg0.getPoint());
				FramNode node = getNodeAt(removeOffset(removeOffset(mouseDownPoint)));
				ConnectionInfo cInfo = getConnectionAt(removeOffset(removeOffset(mouseDownPoint)));
				
				originalOffset = (Point)offset.clone();
				
					if(cInfo != null){
						selectConnection(cInfo);
						selectedNode = null;
					}else if(node != null){
						selectNode(node);
						selectedLine =null;
					}else{
						selectedLine =null;
						selectedNode = null;
					}
				
				repaint();
				
			}

			public void mouseReleased(MouseEvent arg0) {
				
			}
			
		});
	}
	

	public void setList(FramNodeList list) {
		this.list = list;
		
		list.addListChangedListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				generateGuiNodes();

			}
		});

		generateGuiNodes();
		 
	}

	private void generateGuiNodes() {
		guiNodeList = new ArrayList<GraphNode>();
		guiLineList = new ArrayList<GraphLine>();
		
		int spacer = 100;
		
		Point p = new Point();
		p.x = 0;
		p.y = 0;
		int maxX = (int)Math.floor(Math.sqrt(list.size())) * spacer;
		for(FramNode node : list) {
			guiNodeList.add(new GraphNode(node, this));
			if(node.getPosition().x == 0 &&
					node.getPosition().y == 0) {
				node.setPosition(p);
			}
			if(p.x >= maxX) {
				p.x = 0;
				p.y+= spacer;
			}
			else {
				p.x+= spacer;
			}
		}
		
		for(ConnectionInfo connection : list.searchConnections()) {
			guiLineList.add(new GraphLine(connection, this));
		}
		
		repaint();
	}

	public void paintComponent(Graphics g) {
		g.translate(offset.x, offset.y);
		
		if(list.size() > 0) {
			
			for(GraphNode guinode : guiNodeList) {
				guinode.paintComponent(g);
			}
			
			for(GraphLine guiline : guiLineList) {
				guiline.paintComponent(g);
			}			

			for(GraphLine guiline : guiLineList) {
				guiline.paintNameBubble(g);
			}
			
			for(GraphNode guinode : guiNodeList) {
				guinode.paintNameBubble(g);
			}
		
		}
	}

	public GraphNode getGuiNode(FramNode node) {
		for(GraphNode guinode : guiNodeList) {
			if(node == guinode.getNode()) {
				return guinode;
			}
		}

		return null;
	}
	
	public Point addOffset(Point input) {
		Point changed = (Point)input.clone();
		
		changed.x += offset.x;
		changed.y += offset.y;
		
		return changed;
	}
	
	public Point removeOffset(Point input) {
		Point changed = (Point)input.clone();
		
		changed.x -= offset.x;
		changed.y -= offset.y;
		
		return changed;
	}
}

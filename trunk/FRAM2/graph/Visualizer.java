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
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import data.ConnectionInfo;
import data.FramNode;
import data.FramNodeList;

/**
 * Visualize the nodes and connections, also handles mouse captures (panning, moving etc)
 * 
 *
 */
public class Visualizer extends JComponent {



	private FramNodeList list;
	private FramNode selectedNode;
	private FramNode hoveredNode;
	private FramNode.NodePort selectedPort;
	private ArrayList<FramNode> connectedToSelectedNode = new ArrayList<FramNode>();
	private ConnectionInfo selectedLine;
	private ArrayList<GraphNode> guiNodeList;
	private ArrayList<GraphLine> guiLineList;
	private Point mouseDownPoint;
	private Point nodeOriginalPoint;
	private boolean showHiddenLines;

	private boolean showWallpaper;
	
	private Point offset = new Point(100, 100);
	private Point originalOffset;
	private float zoomFactor = 1;
	
	private ArrayList<ActionListener> selectedChangedRecipients = new ArrayList<ActionListener>();
	
	private Point connectionOriginalPoint;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3214964902776275054L;

	public boolean isWallpaperVisible() {
		return showWallpaper;
	}
	
	public void setWallpaperVisible(boolean val) {
		showWallpaper = val;
	}
	
	public Visualizer() {
		this(new FramNodeList("empty"));
	}
	
	public Visualizer(FramNodeList list) {		
		setList(list);
		
		init();
	}
	
	public FramNode getNodeAt(Point position) {
		FramNode node = null;
				
		for(FramNode n : list) {
			if(n.getRectangle(true).contains(position)) {
				node = n;
				break;
			}
		}
		
		return node;
	}
	
	public FramNode.NodePort getPortAt(Point position, FramNode node) {
		if(node != null) {
			for(FramNode.NodePort port : FramNode.NodePort.values()) {
				if(node.getPortRectangle(port).contains(position)) {
					return port;
				}
			}
			return null;
		}
		else {
			return null;
		}
	}
	
	public ConnectionInfo getConnectionAt(Point position){
		ConnectionInfo cInfo = null;
		
		for(ConnectionInfo c : list.getConnections()){
			if(c.getGraphLine().getRectangle().contains(position)){
				cInfo = c;
				break;
			}
		}
		
		return cInfo;
	}
	
	private void updateConnectedToSelectedList() {
		connectedToSelectedNode.clear();
		
		FramNode selected = getSelectedNode();
		if(selected != null) {

			for(FramNode n : selected.getConnectedNodes()) {
				connectedToSelectedNode.add(n);
			}
		}

		repaint();
	}
	
	public void selectNode(FramNode node, FramNode.NodePort port) {
		boolean triggerEvent = true;
		if(node == selectedNode) {
			triggerEvent = false;
		}
		
		if(node == null) {
			selectedNode = null;
			selectedPort = null;
			nodeOriginalPoint = null;
		}
		else {
			selectedLine = null;
			selectedNode = node;
			selectedPort = port;
			nodeOriginalPoint = (Point)node.getPosition().clone();
		}
		
		if(triggerEvent) {
			triggerSelectedChanged();
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
			connectionOriginalPoint = (Point)cInfo.getGraphLine().getPosition().clone();
		}
		
		triggerSelectedChanged();
	}
	
	public FramNode getSelectedNode() {
		return selectedNode;
	}
	
	public FramNode.NodePort getSelectedPort() {
		return selectedPort;
	}
	
	public ConnectionInfo getSelectedLine() {
		return selectedLine;
	}
		
	public FramNode getHoveredNode() {
		return hoveredNode;
	}
	
	public void setHoveredNode(FramNode val) {
		boolean refresh = false;
		if(val != hoveredNode) {
			refresh = true;
		}
		
		hoveredNode = val;
		
		if(refresh) {
			repaint();
		}
	}
	
	private void init() {
		this.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent arg0) {
				float newZoom = getZoomFactor();
				newZoom += arg0.getWheelRotation() * -0.05;
			
				Point centerPoint;
				
				if(newZoom < getZoomFactor()) {
					centerPoint = new Point(getWidth()/2, getHeight()/2);
					//centerPoint.x += arg0.getX();
					//centerPoint.y += arg0.getY();
				}
				else {
					centerPoint = arg0.getPoint();
				}
				
				setZoomFactor(newZoom, centerPoint);
				
				repaint();

			}
			
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				FramNode node = getSelectedNode();
				ConnectionInfo cInfo = getSelectedLine();
				
				Point newLocation = removeZoom(arg0.getPoint());
			
				int xDiff = newLocation.x - mouseDownPoint.x;
				int yDiff = newLocation.y - mouseDownPoint.y;
				
				//Move node
				if(node != null) {					
					node.setPosition(new Point(
							nodeOriginalPoint.x + xDiff,
							nodeOriginalPoint.y + yDiff));
					
				}
				//move connection label
				else if(cInfo != null){
					
					cInfo.getGraphLine().setPosition(new Point(
							connectionOriginalPoint.x + xDiff,
							connectionOriginalPoint.y + yDiff));
					cInfo.getGraphLine().setMoved(true);
				}
				//panning
				else {					
					offset.x = originalOffset.x + xDiff;
					offset.y = originalOffset.y + yDiff;
				}
				repaint();
				
			}

			public void mouseMoved(MouseEvent arg0) {
				FramNode n = getNodeAt(removeZoomOffset(arg0.getPoint()));
				setHoveredNode(n);
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
				mouseDownPoint = removeZoom(arg0.getPoint());
				FramNode node = getNodeAt(removeZoomOffset(arg0.getPoint()));
				FramNode.NodePort port = getPortAt(removeZoomOffset(arg0.getPoint()), node);
				ConnectionInfo cInfo = getConnectionAt(removeZoomOffset(arg0.getPoint()));
				
				originalOffset = (Point)offset.clone();
				
					if(cInfo != null){
						selectConnection(cInfo);
						selectedNode = null;
					}else if(node != null){
						selectNode(node, port);
						selectedLine = null;
					}else{
						selectNode(null, null);
						selectConnection(null);
						//selectNode(node);
						//selectedNode = null;
						//selectedLine = null;
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
		
		//int spacer = 100;
		
		Point p = new Point();
		p.x = 0;
		p.y = 0;
//		int maxX = (int)Math.floor(Math.sqrt(list.size())) * spacer;
		for(FramNode node : list) {
			guiNodeList.add(new GraphNode(node, this));
//			if(node.getPosition().x == 0 &&
//					node.getPosition().y == 0) {
//				node.setPosition(p);
//			}
//			if(p.x >= maxX) {
//				p.x = 0;
//				p.y+= spacer;
//			}
//			else {
//				p.x+= spacer;
//			}
		}
		
		for(ConnectionInfo connection : list.getConnections()) {
			GraphLine graphLine;
			graphLine = new GraphLine(connection, this);
			connection.setGraphLine(graphLine);
			guiLineList.add(graphLine);
		}
		
		updateConnectedToSelectedList();
		repaint();
	}

	public float getZoomFactor() {
		return zoomFactor;
	}
	
	public void setZoomFactor(float val, Point position) {
		if(val < 0.1f) {
			val = 0.1f;
		}
		else if(val > 2) {
			val = 2;
		}
		
		float zoomDifference = val - zoomFactor;
		Point corr = removeZoom(position);
	
		corr.x *= zoomDifference / val;
		corr.y *= zoomDifference / val;
						
		offset.x -= corr.x;
		offset.y -= corr.y;
		
		zoomFactor = val;
	}
	
	public void paintComponent(Graphics g) {		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(showWallpaper) {
			URL url;
			try {
				url = new URL("http://www.wright.edu/isap/erik_hollnagel.jpg");
				ImageIcon img = new ImageIcon(url);
				for(int i = 0; i < 5; i++) {
					for(int j = 0; j < 5; j++) {
						img.paintIcon(this, g, i * img.getIconWidth(), j * img.getIconHeight());
					}
				}
				//g.drawImage(img, 0, 0, null);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//"http://www.wright.edu/isap/erik_hollnagel.jpg");
		
		
		
		
		java.awt.Graphics2D g2d = (java.awt.Graphics2D)g;
		g2d.scale(getZoomFactor(), getZoomFactor());
		g.translate(offset.x, offset.y);
		
		
//		Point thisPosition = addZoomOffset(new Point(0,0));
//		Dimension thisSize = this.getSize();
//		thisSize.width *= getZoomFactor();
//		thisSize.height *= getZoomFactor();
//		Rectangle thisRect = new Rectangle(thisPosition, thisSize);
		
		if(list.size() > 0) {
			
			for(GraphNode guinode : guiNodeList) {
				if(g.getClipBounds().intersects(guinode.getNode().getRectangle())) {
					if(guinode.getNode().isFilterVisible()) {
						guinode.paintComponent(g);
						guinode.paintName(g);
					}
				}
			}
			
			for(GraphLine guiline : guiLineList) {
				if(guiline.getVisibility() || ConnectionInfo.isShowAll()) {
					if(guiline.getConnection().isFilterVisible()) {
						guiline.paintComponent(g);
						guiline.paintNameBubble(g);
					}
				}
			}
			
			for(GraphNode guinode : guiNodeList) {
				if(g.getClipBounds().intersects(guinode.getNode().getRectangle())) {
					if(guinode.getNode().isFilterVisible()) {
						if(guinode.isHovered() 
								|| guinode.isSelected()
								|| connectedToSelectedNode.contains(guinode.getNode())) {
							guinode.paintNameBubble(g);
						}
					}
				}
			}
			
			if(getSelectedNode() != null) {
				GraphNode selectedGuiNode = getGuiNode(getSelectedNode());
				if(selectedGuiNode != null) {
					selectedGuiNode.paintNameBubble(g);
				}
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
	
	
	
	
	public Point addZoom(Point input) {
		Point changed = (Point)input.clone();

		changed.x *= getZoomFactor();
		changed.y *= getZoomFactor();
	
		return changed;
	}
	
	public Point removeZoom(Point input) {
		Point changed = (Point)input.clone();
		
		changed.x /= getZoomFactor();
		changed.y /= getZoomFactor();
		
		return changed;
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
	
	
	public Point addZoomOffset(Point input) {
		return addOffset(addZoom(input));
	}
	
	public Point removeZoomOffset(Point input) {		
		return removeOffset(removeZoom(input));
	}
	
	public void addSelectedChangedListener(ActionListener listener) {
		selectedChangedRecipients.add(listener);
	}
	
	public void removeSelectedChangedListener(ActionListener listener) {
		selectedChangedRecipients.remove(listener);
	}
	
	public void triggerSelectedChanged() {
		updateConnectedToSelectedList();
		
		ActionEvent event = new ActionEvent(this, 0, "");
		
		for(ActionListener listener : selectedChangedRecipients) {
			listener.actionPerformed(event);
		}
		
		repaint();
	}
	
	public ArrayList<GraphLine> getGuiLineList() {
		return guiLineList;
	}
	
	public boolean isShowHiddenLines() {
		return this.showHiddenLines;
	}
	
	public void setShowHiddenLines(boolean val) {
		this.showHiddenLines = val;
	}
}

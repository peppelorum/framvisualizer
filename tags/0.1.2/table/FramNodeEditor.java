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

package table;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import data.FramFunction;

/**
 * A panel that holds the aspecttable, handles mouse drag etc for a function
 * @author petbe082
 *
 */

public class FramNodeEditor extends JPanel {

	private static final long serialVersionUID = 197489600332691308L;

	private FramFunction node;
	private FramAspectTable tableNode;
	private FramNodeEditorList editorParent;
	
	/**
	 * Calculates the box new locatin when dragging
	 * @param xRelative
	 * @param yRelative
	 */
	
	private void moveBox(int xRelative, int yRelative) {
		int x = xRelative + getX();
		int y = yRelative + getY();
		
		if(getParent().getComponentAt(x, y)
				!= this) {
			if(y <= getY()) {
				moveBox(-1);
			}
			else {
				moveBox(1);
			}
		}
	}

	/**
	 * Moves the box one step up/down
	 * @param direction
	 */
	public void moveBox(int direction) {
		int position = 0;
		for(int i = 0; i < getParent().getComponentCount(); i++) {
			if(getParent().getComponent(i) == this) {
				position = i;
				break;
			}
		}
		int newPosition = position + direction;
		if(newPosition < getParent().getComponentCount() && newPosition >= 0) {
			getParent().add(this, newPosition);
			getParent().validate();
			getParent().repaint();
		}
	}

	public FramNodeEditor(FramFunction node, FramNodeEditorList editorList) {

		this.editorParent = editorList;

		this.setVisible(true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(200, 100);
		
		this.node = node;
		node.addNodeChangedListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getActionCommand().equals("VisibilityChanged")) {
					setVisible(getNode().isFilterVisible());
				}
				
			}
			
		});
		tableNode = new FramAspectTable(node, editorList.getList(), this);

		tableNode.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				moveBox(arg0.getX(), arg0.getY());
			}

			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		this.add(tableNode);
		deSelected();
	}

	public FramFunction getNode() {
		return node;
	}


	public boolean getVisibility(){
		return this.isVisible();	
	}

	public void cleanUp() {
		tableNode.cleanUp();
	}

	public FramAspectTable getTableNode() {
		return tableNode;
	}

	public boolean isSelected() {
		return editorParent.getSelectedNodeEditor() == this;
	}

	public void select() {
		editorParent.setSelectedNodeEditor(this);
	}

	/**
	 * Stops the cell editing in the table and closes the dropdownboxes
	 *
	 */
	public void deSelected() {
		if(tableNode.getCellEditor() != null) {
			tableNode.getCellEditor().stopCellEditing();
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
	}
}

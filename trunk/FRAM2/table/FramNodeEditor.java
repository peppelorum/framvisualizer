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

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import data.FramNode;
import data.FramNodeList;

public class FramNodeEditor extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 197489600332691308L;

	private FramNode node;
	private FramAspectTable tableNode;
	
	//private boolean visible = true;
	
	private FramNodeEditorList editorParent;

	public FramNodeEditor(FramNode node, FramNodeEditorList editorList) {
		
		this.editorParent = editorList;
		
		this.setVisible(true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.node = node;
		tableNode = new FramAspectTable(node, editorList.getList(), this);

		this.add(tableNode);

		deSelected();
	}

	public FramNode getNode() {
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
	
	public void selected() {
		this.setBorder(BorderFactory.createLineBorder (Color.blue, 2));
	}
	
	public void deSelected() {
		this.setEnabled(false);
		this.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0f,0f,0.9f), 2));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}
}

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

package gui;

import graph.GraphLine;
import graph.Visualizer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import table.FramCPCTable;
import table.FramNodeEditorList;

import data.Aspect;
import data.ConnectionInfo;
import data.FramNode;
import data.FramNodeList;

public class GUI extends JFrame implements ActionListener{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2294328777692036637L;
	private final JFileChooser fc = new JFileChooser();
    private JButton newNode;
    
    private FramNodeEditorList framNodeEditorList = new FramNodeEditorList();
    private Visualizer framVisualizer = new Visualizer();
    private FramCPCTable framCPCTable = new FramCPCTable();
    private JSplitPane tableAndGraph;
    private JSplitPane split2;
    private Container tableContainer = new Container();
        
	public GUI(){
		framNodeEditorList.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				FramNodeEditorList nodeList = (FramNodeEditorList)e.getSource();
				
				if(e.getActionCommand() == "Selected aspect changed") {
					Aspect a = nodeList.getSelectedAspect();
					if(a != null) {
						framCPCTable.setCPC(a.getCPC());
						//framVisualizer.selectNode(a.getParent());
					}
					else {
						framCPCTable.setCPC(null);
					}
				}
				
				framVisualizer.selectNode(nodeList.getSelectedNode());
			}
			
		});
		
		framVisualizer.addSelectedChangedListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Visualizer visualizer = (Visualizer)e.getSource();
				
				ConnectionInfo line = visualizer.getSelectedLine();
				if(line != null) {
					FramNode n = line.getFrom().getNode();
					Aspect a = n.getAspect(line.getFrom().getConnectionPort().toString(), line.getAspect());
					if(a != null) {
						framCPCTable.setCPC(a.getCPC());
					}
					else {
						framCPCTable.setCPC(null);
					}
				}
				else {
					framCPCTable.setCPC(null);
				}
				
				framNodeEditorList.setSelectedNode(framVisualizer.getSelectedNode());
				
			}
			
		});
		
		this.setSize(900, 600);
		
		Container contentPane = getContentPane();
		
		
		contentPane.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();	
		JTextField searchField = new JTextField(20);
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				FramNodeEditorList searchedTableList = new FramNodeEditorList();
				JTextField textfield = (JTextField)e.getSource();
				String search = textfield.getText();
				
//				System.out.println(search);
//				System.out.println(search.length());
				FramNodeList searchedList = framNodeEditorList.getList().getAllAspectsAndComments(search);
				
				if (search.length() == 0){
					tableContainer.remove(0);
					tableContainer.add(framNodeEditorList);
					framVisualizer.setList(framNodeEditorList.getList());
				} else {
					searchedTableList.setList(searchedList);
					
					tableContainer.remove(0);
					tableContainer.add(searchedTableList);
					framVisualizer.setList(searchedList);
				}
				//Rectangle r = tableContainer.getBounds();
				validate();
				repaint();

			}
		});
		
		
		
		
		tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
				
		menuBar.add(createFileMenu());
		menuBar.add(createHelpMenu());
		setJMenuBar(menuBar);

		framVisualizer.setList(framNodeEditorList.getList());

		tableContainer.add(framNodeEditorList);
				
		tableAndGraph = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
		tableAndGraph.setDividerLocation(450);
		tableAndGraph.setLeftComponent(new JScrollPane(tableContainer));
		
		split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split2.setDividerLocation(300);
		split2.setTopComponent(framVisualizer);
		split2.setBottomComponent(new JScrollPane(framCPCTable));
		
		tableAndGraph.setRightComponent(split2);
		
		//tableAndGraph.setRightComponent(framVisualizer);		
		
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		
		buttonsPanel.add(searchField);
		buttonsPanel.add(createNewNodeButton());
		buttonsPanel.add(createDeleteButton());
		buttonsPanel.add(createShowLabelsButton());
		
		contentPane.add(buttonsPanel, BorderLayout.PAGE_START);	
		contentPane.add(tableAndGraph, BorderLayout.CENTER);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void deleteSelectedNode() {
		framNodeEditorList.getList().remove(framVisualizer.getSelectedNode());
	}
	

	private JButton createDeleteButton() {
		JButton buttonDelete = new JButton();
		buttonDelete.setText("Delete node");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSelectedNode();
			}
		});
		
		return buttonDelete;
	}
	
	private JButton createNewNodeButton(){
        newNode = new JButton();
        newNode.setText("New node");
        newNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				framNodeEditorList.add(new FramNode());
			}
        });
        return newNode;
	}
	private JButton createShowLabelsButton() {
		JButton buttonShowLabels = new JButton();
		buttonShowLabels.setText("Toggle labels");
		buttonShowLabels.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GraphLine.isShowBubbles()){
					GraphLine.setShowBubbles(false);
				}else{
					GraphLine.setShowBubbles(true);
				}
				repaint();
			}
		});
		
		return buttonShowLabels;
	}
	
	 /**
     * Captures the thrown actions
     *      
     */
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand()== "Save"){
    		//fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    		fc.setFileFilter(new XMLfilter());
    		fc.setAcceptAllFileFilterUsed(false);
    		
    		int returnVal = fc.showSaveDialog(this);
   		 
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            framNodeEditorList.getList().SaveFile(file.getPath());
	            //saveToNodeList(model).SaveFile(file.getName());
	        }	
    	}else if(e.getActionCommand()=="Load"){
    		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    		fc.setFileFilter(new XMLfilter());
    		
    		int returnVal = fc.showOpenDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
    			File file = fc.getSelectedFile();
    			framNodeEditorList.setList(FramNodeList.LoadFile(file.getPath()));
    			framVisualizer.setList(framNodeEditorList.getList());

    		}
    	}else if(e.getActionCommand()=="About"){
    		JFrame aboutFrame = new JFrame();
    		JPanel panel = new JPanel();
    		aboutFrame.add(panel);
    		JLabel text = new JLabel("<html><h3>FRAM Visualizer</h3>" +
    				"<p>FRAM Visualizer is an open-source tool to help model <br>system with FRAM (Functional Resonance Accident Model). </p>" +
    				"<p><br>First developed at Linköping University, Sweden by:</p><p>Peppe Bergqvist<br>Fredrik Gustafsson<br>Jonas Haraldsson<br>Gustav Ladén<br></p>" +
    				"<p><br>FRAM Visualizer is licensed under GNU General Public License (GPL)</p>"+
    				"<p>https://sourceforge.net/projects/framvisualizer/</p>" +
    				"<p><br>Copyright (C) 2007  Peppe Bergqvist peppe@peppesbodega.nu, <br>" +
    				"Fredrik Gustafsson fregu808@student.liu.se,<br>" +
    				"Jonas Haraldsson haraldsson@gmail.com, <br> " +
    				"Gustav Ladén gusla438@student.liu.se</p></html>");

    		panel.add(text);
    		aboutFrame.setSize(150, 200);
    		aboutFrame.pack();
    		aboutFrame.setVisible(true);
    		aboutFrame.setFocusable(true);
    	}
    }
    
    
    /**
     * Creates a filter to get .xml for the filechooser dialog
     * @author Jonas Haraldsson
     *
     */
    class XMLfilter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
        }
        public String getDescription() {
            return "XML files";
        }
    }   
    
	/**
	 * Creates the Filemenu
	 * @return The File menu
	 */
	private JMenu createFileMenu(){
		JMenu menu;
		JMenuItem menuItem;				
		menu = new JMenu("File");		
		menuItem = new JMenuItem("Load");
		menuItem.setMnemonic('L');
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		return menu;
	}
	private JMenu createHelpMenu(){
		JMenu menu;
		JMenuItem menuItem;	
		menu = new JMenu("Help");		
		menuItem = new JMenuItem("About");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		return menu;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new GUI();
	}
}

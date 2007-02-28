package gui;

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
		framNodeEditorList.addSelectedAspectChangedListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				FramNodeEditorList nodeList = (FramNodeEditorList)e.getSource();
				Aspect a = nodeList.getSelectedAspect();
				if(a != null) {
					framCPCTable.setCPC(a.getCPC());
				}
				else {
					framCPCTable.setCPC(null);
				}
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
				
				System.out.println(search);
				System.out.println(search.length());
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new GUI();
	}
}

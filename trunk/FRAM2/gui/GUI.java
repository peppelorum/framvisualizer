package gui;

import graph.Visualizer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
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
import javax.swing.JTextField;
import javax.swing.UIManager;

import table.TableNodeList;

import data.FramNode;
import data.FramNodeList;

public class GUI extends JFrame implements ActionListener{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2294328777692036637L;
	private final JFileChooser fc = new JFileChooser();
    private JButton newNode;
    private JButton repaintButton;
    
    private TableNodeList table = new TableNodeList();
    private Visualizer tableVisualizer = new Visualizer();
    private JPanel tableAndGraph = new JPanel(new GridLayout(0,2));
    private Container tableContainer = new Container();
    
	public GUI(){
		
		this.setSize(600, 500);
		
		Container contentPane = getContentPane();
		
		
		contentPane.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();	
		JTextField searchField = new JTextField(20);
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TableNodeList searchedTableList = new TableNodeList();
				JTextField textfield = (JTextField)e.getSource();
				String search = textfield.getText();
				
				System.out.println(search);
				System.out.println(search.length());
				FramNodeList searchedList = table.getList().getAllAspectsAndComments(search);
				
				if (search.length() == 0){
					tableContainer.remove(0);
					tableContainer.add(table);
					tableVisualizer.setList(table.getList());
				} else {
					searchedTableList.setList(searchedList);
					
					tableContainer.remove(0);
					tableContainer.add(searchedTableList);
					tableVisualizer.setList(searchedList);
				}
				//Rectangle r = tableContainer.getBounds();
				validate();
				repaint();

			}
		});
		
		
		
		
		tableContainer.setLayout(new BoxLayout(tableContainer, BoxLayout.Y_AXIS));
				
		menuBar.add(createFileMenu());
		setJMenuBar(menuBar);

		tableVisualizer.setList(table.getList());

		tableContainer.add(table);
		
		tableAndGraph.add(new JScrollPane(tableContainer));
		tableAndGraph.add(tableVisualizer);
		
		
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		
		buttonsPanel.add(searchField);
		buttonsPanel.add(createNewNodeButton());
		buttonsPanel.add(createRepaintButton());
		
		contentPane.add(buttonsPanel, BorderLayout.PAGE_START);	
		contentPane.add(tableAndGraph, BorderLayout.CENTER);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private JButton createRepaintButton(){
	
		repaintButton = new JButton();
		repaintButton.setText("repaint");
		repaintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validate();
				
			}
		});
		return repaintButton;
	
	}
	
	private JButton createNewNodeButton(){
        newNode = new JButton();
        newNode.setText("New node");
        newNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 1;
				String name;
				do{
					name = "Ny Nod" + " " + i;
					i++;
				}while(table.getList().getAllNames().contains(name));
				
				table.add(new FramNode(name));
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
	            table.getList().SaveFile(file.getPath());
	            //saveToNodeList(model).SaveFile(file.getName());
	        }	
    	}else if(e.getActionCommand()=="Load"){
    		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    		fc.setFileFilter(new XMLfilter());
    		
    		int returnVal = fc.showOpenDialog(this);
    		if (returnVal == JFileChooser.APPROVE_OPTION) {
    			File file = fc.getSelectedFile();
    			table.setList(FramNodeList.LoadFile(file.getPath()));
    			tableVisualizer.setList(table.getList());

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
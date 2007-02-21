package table;


import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class ComboBoxAutoComplete extends JComboBox	implements JComboBox.KeySelectionManager
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3571587786729616007L;
	
	public class CBDocument extends PlainDocument
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8367276909010234390L;
		
		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
		{
			if (str==null) return;
			super.insertString(offset, str, a);
			if(!isPopupVisible() && str.length() != 0) fireActionEvent();
		}
	}
	public ComboBoxAutoComplete(Object[] items)
	{
		super(items);
		
		setKeySelectionManager(this);
		JTextField tf;
		if(getEditor() != null)
		{
			tf = (JTextField)getEditor().getEditorComponent();
			if(tf != null)
			{
				tf.setDocument(new CBDocument());
				
				addActionListener(new ActionListener()
						{
					public void actionPerformed(ActionEvent evt)
					{
						JTextField tf = (JTextField)getEditor().getEditorComponent();
						String text = tf.getText();
						String orgText = text;
						
						ComboBoxModel aModel = getModel();
						String current;
						for(int j = 0; j < aModel.getSize(); j++) {
							current = aModel.getElementAt(j).toString();
							if(current.toLowerCase().startsWith(text.toLowerCase())){
								text = current;									
								break;
								
							}
						}
						tf.setText(text);		
						
						tf.setSelectionStart(orgText.length());
						tf.setSelectionEnd(text.length());
						
					}
						});
			}
		}
	}
	public int selectionForKey(char aKey, ComboBoxModel aModel)
	{
		return -1;
	}
	public void fireActionEvent()
	{
		super.fireActionEvent();
	}
	
//	public static void main(String arg[])
//	{
//		JFrame f = new JFrame("AutoCompleteComboBox");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setSize(200,300);
//		Container cp= f.getContentPane();
//		cp.setLayout(null);
//
//		Locale[] locales = Locale.getAvailableLocales();//
//		JComboBox cBox= new ComboBoxAutoComplete(locales);
//		cBox.setBounds(50,50,100,21);
//		cBox.setEditable(true);
//		cp.add(cBox);
//		f.setVisible(true);
//	}
}



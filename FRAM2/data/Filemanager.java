package data;

import java.io.BufferedReader;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * The class Filemanger handles the saving and loading to/from XML
 * It is not called direct, but through the FramNodeList class
 * 
 * @author Jonas Haraldsson
 *
 */
public class Filemanager {


	/**
	 * Creates an XML-file represeting a FramNodeList
	 * 
	 * @param nodeList List to be saved
	 * @param filename 
	 */
	protected static void saveFile(FramNodeList nodeList, String filename){
		PrintStream MyOutput = null;
		try {
		       MyOutput = new PrintStream(new FileOutputStream(filename));
		   } 
		catch (IOException e)
		   {
		      System.out.println("Can't create file");
		   }
		

		if (MyOutput != null) {
			MyOutput.println("<Framlist>");
			MyOutput.println("\t<ListName>" + nodeList.getName() + "</ListName>");
			MyOutput.println("\t<Nodes>");
			for(FramNode node : nodeList){
				MyOutput.println("\t\t<FramNode>");
				MyOutput.println("\t\t\t<Name>" + node.getName() + "</Name>");

				for (Object s : node.getInput()){
					MyOutput.println("\t\t\t<Input>" + (String)s + "</Input>");
				}
				for (Object s : node.getOutput()){
					MyOutput.println("\t\t\t<Output>" + (String)s + "</Output>");
				}
				for (Object s : node.getResources()){
					MyOutput.println("\t\t\t<Resources>" + (String)s + "</Resources>");
				}
				for (Object s : node.getTime()){
					MyOutput.println("\t\t\t<Time>" + (String)s + "</Time>");
				}
				for (Object s : node.getControl()){
					MyOutput.println("\t\t\t<Control>" + (String)s + "</Control>");
				}
				for (Object s : node.getPrecondition()){
					MyOutput.println("\t\t\t<Precondition>" + (String)s + "</Precondition>");
				}
				MyOutput.println("\t\t</FramNode>");
			}

			MyOutput.println("\t</Nodes>");
			MyOutput.print("</Framlist>");
			MyOutput.close();
		} else {
			System.out.println("No output file written");
		}
	}

	/**
	 * Creates FramNodeList from an xml-file
	 * 
	 * @param filename 
	 * @return FrameNodeList
	 */
	protected static FramNodeList loadFile(String filename){
		FramNodeList lista = new FramNodeList("Mock up");
		
		try { 
			FileReader fr = new FileReader(filename); 
			BufferedReader br = new BufferedReader(fr);
			String record = br.readLine();
			
			while(record != null){
				if(record.contains("<ListName>")){
					lista = new FramNodeList(cutString(record));

					while(false == record.contains("</FramList>")){		

						if(record.contains("<FramNode>")){
							record = br.readLine();
							record = cutString(record);
							FramNode node = new FramNode(record);
							lista.add(node);

							while(false == record.contains("</FramNode>")){
								if( record.contains("<Input>") ) {
									node.addInput(cutString(record));
								}else if (record.contains("<Output>")){
									node.addOutput(cutString(record));
								}else if (record.contains("<Resources>")){
									node.addResources(cutString(record));
								}else if (record.contains("<Control>")){
									node.addControl(cutString(record));
								}else if (record.contains("<Time>")){
									node.addTime(cutString(record));
								}else if (record.contains("<Precondition>")){
									node.addPrecondition(cutString(record));
								}
								record = br.readLine();
							}
						}				
						record = br.readLine();
					}
				}
				record = br.readLine();
			}
			
		}catch (Exception e){

		}

		return lista;

	}
	/**
	 * Takes a string e.g. <Input>value</Input> and returns the value
	 * 
	 * @param string Single line from xml-file
	 * @return The value between the xml-tags
	 */
	private static String cutString(String string){
		int kapa = string.indexOf('>');
		int kapa2 = string.indexOf('/');
		string = string.substring(kapa+1, kapa2-1);
		
		return string;
	}
		
}



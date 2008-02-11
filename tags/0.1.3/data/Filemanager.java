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

package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	 * Serialize an object to file
	 * 
	 * @param nodeList List to be saved
	 * @param filename 
	 */
	protected static void saveFile(Object obj, String filename){

		if(!filename.toLowerCase().endsWith(".fram")){
			filename = filename + ".fram";
		}

		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return;
	}

	/**
	 * Load a serialized object from file
	 * 
	 * @param filename 
	 * @return FrameNodeList
	 */
	protected static Object loadFile(String filename){
		Object obj;

		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);

			obj = ois.readObject();

			ois.close();
			fis.close();

			return obj;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Creates an XML-file represeting a FramNodeList
	 * 
	 * @param nodeList List to be saved
	 * @param filename 
	 */
	protected static void saveXMLFile(FramFunctionList nodeList, String filename){
		PrintStream MyOutput = null;
		if(!filename.toLowerCase().endsWith(".xml")){
			filename = filename + ".xml";
		}

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
			for(FramFunction node : nodeList){
				MyOutput.println("\t\t<FramNode>");
				MyOutput.println("\t\t\t<Name>" + node.getName() + "</Name>");
				MyOutput.println("\t\t\t<Comment>" + node.getComment() + "</Comment>");

				for(String[] aspect : node.getAllAspects()){
					MyOutput.println("\t\t\t<" + aspect[0] +">");
					MyOutput.println("\t\t\t\t<Value>" + aspect[1] + "</Value>");
					MyOutput.println("\t\t\t\t<Comment>" + aspect[2] + "</Comment>");
					MyOutput.println("\t\t\t</" + aspect[0] +">");
				}

				for(String cpc : node.getCPC().getCPCTypes()){
					if (node.getCPC().hasAttribute(cpc)) {
						CPCAttribute a = node.getCPC().getAttribute(cpc);
						MyOutput.println("\t\t\t<CPC>");
						MyOutput.println("\t\t\t\t<Type "+ "value=\""+a.getValue() +"\" comment=\"" + a.getComment() + "\">" + a.getType() + "</Type>");

						MyOutput.println("\t\t\t\t<CPCForAspects>");

						boolean[] f = a.getCpcForAspects();
						MyOutput.println("\t\t\t\t<I>" + f[0] + "</I>");
						MyOutput.println("\t\t\t\t<O>" + f[1] + "</O>");
						MyOutput.println("\t\t\t\t<P>" + f[2] + "</P>");
						MyOutput.println("\t\t\t\t<R>" + f[3] + "</R>");
						MyOutput.println("\t\t\t\t<T>" + f[4] + "</T>");
						MyOutput.println("\t\t\t\t<C>" + f[5] + "</C>");
						MyOutput.println("\t\t\t\t</CPCForAspects>");
						MyOutput.println("\t\t\t</CPC>");
					}
				}
				MyOutput.println("\t\t</FramNode>");
			}

			MyOutput.println("\t</Nodes>");
			MyOutput.println("<Connections>");
			for(ConnectionInfo cInfo : nodeList.getConnections()){
				MyOutput.println("\t<Connection>");
				MyOutput.println("\t\t<From port=\"" + cInfo.getFrom().getConnectionPort() + "\" >" + cInfo.getFrom().getFunctionName() + "</From>");

				MyOutput.println("\t\t<To port=\"" + cInfo.getTo().getConnectionPort() + "\" >" + cInfo.getTo().getFunctionName() + "</To>");

				MyOutput.println("\t\t<Value>" + cInfo.getAspect() + "</Value>");

				MyOutput.println("\t</Connection>");

			}

			MyOutput.println("</Connections>");
			MyOutput.print("</Framlist>");
			MyOutput.close();
		} else {
			System.out.println("No output file written");
		}
	}

	/**
	 * Creates an a file with format 1
	 * 
	 * @param nodeList List to be saved
	 * @param filename 
	 */
	protected static void saveToFormat1(FramFunctionList nodeList, String filename){
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
			for(FramFunction node : nodeList){
				MyOutput.println("\t\t<FramNode>");
				MyOutput.println("\t\t\t<Name>" + node.getName() + "</Name>");
				MyOutput.println("\t\t\t<Comment>" + node.getComment() + "</Comment>");

				for(String[] aspect : node.getAllAspects()){
					MyOutput.println("\t\t\t<" + aspect[0] +">");
					MyOutput.println("\t\t\t\t<Value>" + aspect[1] + "</Value>");
					MyOutput.println("\t\t\t\t<Comment>" + aspect[2] + "</Comment>");
					MyOutput.println("\t\t\t</" + aspect[0] +">");
				}

				for(String cpc : node.getCPC().getCPCTypes()){
					if (node.getCPC().hasAttribute(cpc)) {
						CPCAttribute a = node.getCPC().getAttribute(cpc);
						MyOutput.println("\t\t\t<CPC>");
						MyOutput.println("\t\t\t\t<Type "+ "value=\""+a.getValue() +"\" comment=\"" + a.getComment() + "\">" + a.getType() + "</Type>");

						MyOutput.println("\t\t\t\t<CPCForAspects>");

						boolean[] f = a.getCpcForAspects();
						MyOutput.println("\t\t\t\t<I>" + f[0] + "</I>");
						MyOutput.println("\t\t\t\t<O>" + f[1] + "</O>");
						MyOutput.println("\t\t\t\t<P>" + f[2] + "</P>");
						MyOutput.println("\t\t\t\t<R>" + f[3] + "</R>");
						MyOutput.println("\t\t\t\t<T>" + f[4] + "</T>");
						MyOutput.println("\t\t\t\t<C>" + f[5] + "</C>");
						MyOutput.println("\t\t\t\t</CPCForAspects>");
						MyOutput.println("\t\t\t</CPC>");
					}
				}
				MyOutput.println("\t\t</FramNode>");
			}

			MyOutput.println("\t</Nodes>");
			MyOutput.println("<Connections>");
			for(ConnectionInfo cInfo : nodeList.getConnections()){
				MyOutput.println("\t<Connection>");
				MyOutput.println("\t\t<From port=\"" + cInfo.getFrom().getConnectionPort() + "\" >" + cInfo.getFrom().getFunctionName() + "</From>");

				MyOutput.println("\t\t<To port=\"" + cInfo.getTo().getConnectionPort() + "\" >" + cInfo.getTo().getFunctionName() + "</To>");

				MyOutput.println("\t\t<Value>" + cInfo.getAspect() + "</Value>");

				MyOutput.println("\t</Connection>");

			}

			MyOutput.println("</Connections>");
			MyOutput.print("</Framlist>");
			MyOutput.close();
		} else {
			System.out.println("No output file written");
		}
	}

	/**
	 * Creates an a file with format 2
	 * 
	 * @param nodeList List to be saved
	 * @param filename 
	 */
	protected static void saveToFormat2(FramFunctionList nodeList, String filename){
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
			for(FramFunction node : nodeList){
				MyOutput.println("\t\t<FramNode>");
				MyOutput.println("\t\t\t<Name>" + node.getName() + "</Name>");
				MyOutput.println("\t\t\t<Comment>" + node.getComment() + "</Comment>");

				for(String[] aspect : node.getAllAspects()){
					MyOutput.println("\t\t\t<" + aspect[0] +">");
					MyOutput.println("\t\t\t\t<Value>" + aspect[1] + "</Value>");
					MyOutput.println("\t\t\t\t<Comment>" + aspect[2] + "</Comment>");
					MyOutput.println("\t\t\t</" + aspect[0] +">");
				}

				for(String cpc : node.getCPC().getCPCTypes()){
					if (node.getCPC().hasAttribute(cpc)) {
						CPCAttribute a = node.getCPC().getAttribute(cpc);
						MyOutput.println("\t\t\t<CPC>");
						MyOutput.println("\t\t\t\t<Type "+ "value=\""+a.getValue() +"\" comment=\"" + a.getComment() + "\">" + a.getType() + "</Type>");

						MyOutput.println("\t\t\t\t<CPCForAspects>");

						boolean[] f = a.getCpcForAspects();
						MyOutput.println("\t\t\t\t<I>" + f[0] + "</I>");
						MyOutput.println("\t\t\t\t<O>" + f[1] + "</O>");
						MyOutput.println("\t\t\t\t<P>" + f[2] + "</P>");
						MyOutput.println("\t\t\t\t<R>" + f[3] + "</R>");
						MyOutput.println("\t\t\t\t<T>" + f[4] + "</T>");
						MyOutput.println("\t\t\t\t<C>" + f[5] + "</C>");
						MyOutput.println("\t\t\t\t</CPCForAspects>");
						MyOutput.println("\t\t\t</CPC>");
					}
				}
				MyOutput.println("\t\t</FramNode>");
			}

			MyOutput.println("\t</Nodes>");
			MyOutput.println("<Connections>");
			for(ConnectionInfo cInfo : nodeList.getConnections()){
				MyOutput.println("\t<Connection>");
				MyOutput.println("\t\t<From port=\"" + cInfo.getFrom().getConnectionPort() + "\" >" + cInfo.getFrom().getFunctionName() + "</From>");

				MyOutput.println("\t\t<To port=\"" + cInfo.getTo().getConnectionPort() + "\" >" + cInfo.getTo().getFunctionName() + "</To>");

				MyOutput.println("\t\t<Value>" + cInfo.getAspect() + "</Value>");

				MyOutput.println("\t</Connection>");

			}

			MyOutput.println("</Connections>");
			MyOutput.print("</Framlist>");
			MyOutput.close();
		} else {
			System.out.println("No output file written");
		}
	}

	/**
	 * Creates an a file with format 3
	 * 
	 * @param nodeList List to be saved
	 * @param filename 
	 */
	protected static void saveToFormat3(FramFunctionList nodeList, String filename){
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
			for(FramFunction node : nodeList){
				MyOutput.println("\t\t<FramNode>");
				MyOutput.println("\t\t\t<Name>" + node.getName() + "</Name>");
				MyOutput.println("\t\t\t<Comment>" + node.getComment() + "</Comment>");

				for(String[] aspect : node.getAllAspects()){
					MyOutput.println("\t\t\t<" + aspect[0] +">");
					MyOutput.println("\t\t\t\t<Value>" + aspect[1] + "</Value>");
					MyOutput.println("\t\t\t\t<Comment>" + aspect[2] + "</Comment>");
					MyOutput.println("\t\t\t</" + aspect[0] +">");
				}

				for(String cpc : node.getCPC().getCPCTypes()){
					if (node.getCPC().hasAttribute(cpc)) {
						CPCAttribute a = node.getCPC().getAttribute(cpc);
						MyOutput.println("\t\t\t<CPC>");
						MyOutput.println("\t\t\t\t<Type "+ "value=\""+a.getValue() +"\" comment=\"" + a.getComment() + "\">" + a.getType() + "</Type>");

						MyOutput.println("\t\t\t\t<CPCForAspects>");

						boolean[] f = a.getCpcForAspects();
						MyOutput.println("\t\t\t\t<I>" + f[0] + "</I>");
						MyOutput.println("\t\t\t\t<O>" + f[1] + "</O>");
						MyOutput.println("\t\t\t\t<P>" + f[2] + "</P>");
						MyOutput.println("\t\t\t\t<R>" + f[3] + "</R>");
						MyOutput.println("\t\t\t\t<T>" + f[4] + "</T>");
						MyOutput.println("\t\t\t\t<C>" + f[5] + "</C>");
						MyOutput.println("\t\t\t\t</CPCForAspects>");
						MyOutput.println("\t\t\t</CPC>");
					}
				}
				MyOutput.println("\t\t</FramNode>");
			}

			MyOutput.println("\t</Nodes>");
			MyOutput.println("<Connections>");
			for(ConnectionInfo cInfo : nodeList.getConnections()){
				MyOutput.println("\t<Connection>");
				MyOutput.println("\t\t<From port=\"" + cInfo.getFrom().getConnectionPort() + "\" >" + cInfo.getFrom().getFunctionName() + "</From>");

				MyOutput.println("\t\t<To port=\"" + cInfo.getTo().getConnectionPort() + "\" >" + cInfo.getTo().getFunctionName() + "</To>");

				MyOutput.println("\t\t<Value>" + cInfo.getAspect() + "</Value>");

				MyOutput.println("\t</Connection>");

			}

			MyOutput.println("</Connections>");
			MyOutput.print("</Framlist>");
			MyOutput.close();
		} else {
			System.out.println("No output file written");
		}
	}

}



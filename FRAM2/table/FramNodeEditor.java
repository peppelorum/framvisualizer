package table;

import java.awt.Container;
import javax.swing.BoxLayout;
import data.FramNode;
import data.FramNodeList;

public class FramNodeEditor extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 197489600332691308L;

	private FramNode node;
	private FramAspectTable tableNode;


	public FramNodeEditor(FramNode node, FramNodeList list) {

		this.setVisible(true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.node = node;

		tableNode = new FramAspectTable(node, list);

		this.add(tableNode);

	}

	public FramNode getNode() {
		return node;
	}

	public void cleanUp() {
		tableNode.cleanUp();
	}

	public FramAspectTable getTableNode() {
		return tableNode;
	}
}

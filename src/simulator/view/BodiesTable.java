package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable  extends JPanel {

	private static final long serialVersionUID = 1L;

	public BodiesTable(Controller ctrl) {
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2),"Bodies", TitledBorder.LEFT, TitledBorder.TOP));
		
		//(1) create an instance of BodiesTableModel and pass it to a corresponding JTable;
		JTable bodiesTable = new JTable(new BodiesTableModel(ctrl));
		
		//(2) add the JTable to the panel (this) with a JScrollPane.
		add(new JScrollPane(bodiesTable));
		this.setPreferredSize(new Dimension(800, 150));
		}
		
	


}

package simulator.view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {


	private static final long serialVersionUID = 1L;

	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
		}

	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		ControlPanel controlPanel = new ControlPanel(_ctrl);
		StatusBar sBar = new StatusBar(_ctrl);
		BodiesTable bTable = new BodiesTable(_ctrl);
		Viewer view = new Viewer(_ctrl);
		
		setContentPane(mainPanel);
		//  (1) place the control panel at the PAGE_START of mainPanel panel;
			mainPanel.add(controlPanel, BorderLayout.PAGE_START);
			
		//  (2) place the status bar at the PAGE_END of mainPanel;
			mainPanel.add(sBar, BorderLayout.PAGE_END);
			
		/*  (3) create a new panel with BoxLayout (with BoxLayout.Y_AXIS) 
		 * and place it at the CENTER of mainPanel, then add the bodies 
		 * table and the viewer to this new panel
		*/
			JPanel box = new JPanel();
			BoxLayout boxLayout = new BoxLayout(box, BoxLayout.Y_AXIS);
			box.setLayout(boxLayout);
			mainPanel.add(box, BorderLayout.CENTER);
			box.add(bTable);
			box.add(view);
			
		
		
		//In order to control the initial size of the different components you can use method setPreferredSize. You will also need to make the window visible, etc.
			this.setVisible(true);
			this.setBounds(400, 0, 800, 800);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(650, 750);
			
			
			
		}
	
	
	
}

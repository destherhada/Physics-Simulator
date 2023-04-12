package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.awt.Frame;

import javax.swing.*;



import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {

	private static final long serialVersionUID = 1L;
	private JButton fileBut = new JButton();
	private JFileChooser fc = new JFileChooser();
	private JButton forceBut = new JButton();
	private JButton runBut = new JButton();
	private JButton stopBut = new JButton();
	private JButton exitBut = new JButton();
	private JLabel stepsLabel = new JLabel(); 
	private JLabel dTimeLabel = new JLabel();
	private JTextField dTimeField = new JTextField(8);
	SpinnerModel value = new SpinnerNumberModel(10,0,100000,10);
	JSpinner stepSpinner = new JSpinner(value);
	private Controller _ctrl;
	private boolean _stopped;

	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
		}

	
	
	//OBSERVER METHODS:

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				dTimeField.setText(String.valueOf(dt));
			}
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				dTimeField.setText(String.valueOf(dt));
			}
		});
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				dTimeField.setText(String.valueOf(dt));
			}
		});
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {

		
	}


	
	

	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
		try {
			_ctrl.run(1);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
			
			_stopped = true;
			enableDisableButtons(_stopped);
			return;
		}
		
		SwingUtilities.invokeLater( new Runnable() {
			
		@Override
		public void run() {
		run_sim(n-1);
		}
		});
		} else {
		_stopped = true;
		enableDisableButtons(_stopped);
		}
		}
	
	
	
	
	private void initGUI() {
		
		//FILE OPENER BUTTON        
		fileBut.setActionCommand("open");
		fileBut.setToolTipText("Select file for loading to the simulator");
		fileBut.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				fileOpenerListener();
			
		}});
		fileBut.setIcon(new ImageIcon("resources/icons/open.png")); 
		this.add(fileBut);
		
		
		
		
		//PHYSICS BUTTON
		forceBut.setActionCommand("select");
		forceBut.setToolTipText("Select one of the available force laws");
		forceBut.addActionListener((e)->{
			
			ForceLawsDialog dialog = new ForceLawsDialog(_ctrl, (Frame) SwingUtilities.getWindowAncestor(this));
			
			if(dialog.open() == 1) {
				
				_ctrl.setForcesLaws(dialog.getJSON());
			}
			
			});
		
		forceBut.setIcon(new ImageIcon("resources/icons/physics.png"));
		this.add(forceBut);
		
		
		
		
		//RUN BUTTON
		runBut.setActionCommand("run");
		runBut.setToolTipText("Runs physics simulator");
		runBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				runListener();
			
			}});
		runBut.setIcon(new ImageIcon("resources/icons/run.png"));
		this.add(runBut);
		
		
		
		
		//STOP BUTTON
		stopBut.setActionCommand("stop");
		stopBut.setToolTipText("Stops physics simulator");
		stopBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = true;
				
			}
			
		});
		stopBut.setIcon(new ImageIcon("resources/icons/stop.png"));
		this.add(stopBut);
		
		
		
		
		//STEPS SPINNER
		stepsLabel.setText("Steps:");
		
		try {
		    stepSpinner.commitEdit();
		} catch ( java.text.ParseException e ) {
			
		}
		this.add(stepsLabel);
		this.add(stepSpinner);
		
		
		
		
		
		//DELTA TIME TEXT FIELD
		dTimeLabel.setText("Delta-Time:");
		this.add(dTimeLabel);
		this.add(dTimeField);
		
		
		
		
		
		//EXIT BUTTON
		exitBut.setActionCommand("exit");
		exitBut.setToolTipText("Exits the physics simulator");
		exitBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(new JOptionPane(), "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null);
				if (n == 0) {
					System.exit(0);
				}
				
			}
			
		});
		exitBut.setIcon(new ImageIcon("resources/icons/exit.png"));
		this.add(exitBut);
		
		
		}
	

	
	public void enableDisableButtons(Boolean enable) {
		fileBut.setEnabled(enable);
		forceBut.setEnabled(enable);
		runBut.setEnabled(enable);
		exitBut.setEnabled(enable);
	}
	
	

	
	//LISTENERS METHODS:
	
	public void fileOpenerListener() {
		File file;
		
		//(1) ask the user to select a file using a JFileChooser;	
			int returnVal = fc.showOpenDialog(fileBut);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file =  fc.getSelectedFile();
				///delete all the system print, err from file
				
				//System.out.println("Loading: " + file);

			
				//(2) reset the simulator using _ctrl.reset();
				_ctrl.reset();
				InputStream iFile;
				
				try {
					// (3) load the selected file into the simulator by 	calling _ctrl.loadBodies(...).
						iFile = new FileInputStream(file);
						_ctrl.loadBodies(iFile);
			
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
		  
	
	}
	}
	
	
	public void runListener() {
		// (1) disable all buttons, except the stop button  and set the value of _stopped to false 
		_stopped = false;
		enableDisableButtons(_stopped);
		
		//(2) set the current delta-time of the simulator to the one specified in the corresponding text field
			_ctrl.setDeltaTime(Double.parseDouble(dTimeField.getText())); 
		
		//(3) call method run_sim with the current value of steps as specified in the JSpinner.
		run_sim((Integer)stepSpinner.getValue());
	}


	
	
}

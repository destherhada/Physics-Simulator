package simulator.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver{


	private static final long serialVersionUID = 1L;
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for force laws
	private JLabel _numOfBodies; // for number of bodies

	/*
	 * Note that fields _currTime, _numberOfBodies and _currLaws are labels where the corresponding information is stored.
	 *  In the observer methods, if any of these information is changed you should modify the corresponding JLabel.
	 */
	
	public StatusBar(Controller ctrl) {
		this._currLaws = new JLabel();
		this._currTime = new JLabel("0.00");
		this._numOfBodies = new JLabel("0");
		initGUI();
		ctrl.addObserver(this);
		}

	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));

		this.setForeground(Color.GRAY);
		
		JLabel timeLabel = new JLabel("Time: ");
		this.add(timeLabel);
		this.add(_currTime);
		
		this.add(Box.createHorizontalStrut(3));
		this.add(separator());
		
		JLabel nBodiesLabel = new JLabel("Bodies: ");
		this.add(nBodiesLabel);
		this.add(_numOfBodies);
		
		this.add(Box.createHorizontalStrut(3));
		this.add(separator());
		
		JLabel	lawsLabel = new JLabel("Laws: ");
		this.add(lawsLabel);
		this.add(_currLaws);
		
		}

	
	
	private JLabel separator() {
		JLabel separator = new JLabel(" | ");
		separator.setForeground(Color.gray);
		return separator;
	}
	
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currTime.setText(Double.toString(time));
		_currLaws.setText(fLawsDesc);
		_numOfBodies.setText(Integer.toString(bodies.size()));
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currTime.setText(Double.toString(time));
		_currLaws.setText(fLawsDesc);
		_numOfBodies.setText(Integer.toString(bodies.size()));
		
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_numOfBodies.setText(Integer.toString((bodies.size()/2) + 1));
		//parche
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_currTime.setText(Double.toString(time));
		
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {

		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		_currLaws.setText(fLawsDesc);
		
	}

}

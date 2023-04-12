package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver{


	private static final long serialVersionUID = 1L;
	private List<Body> _bodies = new ArrayList<>();
	private String[] colName = {"Id", "Mass", "Position", "Velocity", "Force"};
	
	public BodiesTableModel(Controller ctrl) {
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		int rows = 0;
		if (_bodies != null) {
			rows =  _bodies.size();
		}
		return rows;
	}

	@Override
	public int getColumnCount() {
		return colName.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return colName[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	Body body = _bodies.get(rowIndex);
	Object value = null;
	
	switch(columnIndex + 1) {
		case 1: 
			value = body.getId();
		break;
		
		case 2:
			value = body.getMass();
		break;
		
		case 3:
			value = body.getPosition();
		break;
		
		case 4:
			value = body.getVelocity();
		break;
		
		case 5:
			value = body.getForce();
		break;
	
	}
	
	return value;
	}
	
	

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodies =new ArrayList<>(bodies);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_bodies =new ArrayList<>(bodies);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				fireTableStructureChanged();
			}
		});
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		
		_bodies.add(b);
	 
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				fireTableStructureChanged();
			}
		});
		
		
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		
		fireTableStructureChanged();
		
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
	
		
	}
	


}

package simulator.model;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	protected double deltaTime;
	protected ForceLaws laws;
	protected double currentTime = 0.0;
	protected List<Body> bodies = new ArrayList<Body>();
	protected List<SimulatorObserver> observers = new ArrayList<SimulatorObserver>();
	
	public PhysicsSimulator(double deltaTime, ForceLaws laws) {
		this.deltaTime = deltaTime;
		this.laws = laws;
		if(deltaTime < 0) {
			throw new IllegalArgumentException("Delta time value not correct");
		}
		if ( laws == null) {
			throw new IllegalArgumentException("No laws given");
		}
		
		
	}
	
	public void advance() {
		//1.- CALLS METHOD RESETFORCE OF EACH BODY
		for(Body b: bodies) {
			b.resetForce();
		}
		
		//2.-CALLS METHOD APPLY OF FORCE LAWS 
			laws.apply(bodies);
		
		//3.-CALLS MOVE(DT) DT = REAL TIME PER STEP
		for(Body b: bodies) {
			b.move(deltaTime);
		}
	
		//4.-INCREMENTS THE CURRENT TIME BY DT SECONDS
		currentTime += deltaTime;
		notifyOnAdvance();
		
	}

	public void addBody(Body b){
	
		if(bodies.contains(b)) {
		
			throw new IllegalArgumentException("Body already exists in list");
		}
		
		//add the body b to the simulator
		else {
			bodies.add(b);
			notifyOnBodyAdded(b);
		}
		
		
	}

	//returns {"time": t, "bodies" : [json1,json2,...] }
	public JSONObject getState() {
		
		JSONObject state = new JSONObject();
		JSONArray bod = new JSONArray();
		
		for(Body b: bodies) {
			bod.put(b.getState());
		}
		

		state.put("bodies", bod);		
		state.put("time", currentTime);
		

		return state;
		
		
	}
	
	public String toString() {
		return getState().toString();
	}
	
	
	//clears the list of bodies and sets the current time to 0.0.
	public void reset() {
		bodies = new ArrayList<>();
		currentTime = 0.0;
		notifyOnReset();
	}
	
	

	/*changes the current value of the delta-time (i.e.,
	the real time per step) to dt. It should throw an IllegalArgumentException exception
	exception if the value is not valid. */
	public void setDeltaTime(double dt) {
		deltaTime = dt;
		if(deltaTime < 0) {
			throw new IllegalArgumentException("Delta time value not correct");
		}
		
		notifyOnDeltaChanged();
	}
	

	/*changes the force laws of the simulator to forceLaws. It should throw an IllegalArgumentException if the value is not
	valid (i.e., null). */
	public void setForceLaws(ForceLaws forceLaws) {
		laws = forceLaws;
		if ( laws == null) {
			throw new IllegalArgumentException("No laws given");
		}
		notifyOnForceLawsChanged();
	}
	


	//add o to the list of observers, if it is not there already
	public void addObserver(SimulatorObserver o) {
		if(observers.contains(o)) {
			
			throw new IllegalArgumentException("Observer is already in the list");
		}
		
		else {
				observers.add(o);
				notifyOnRegister(observers.get(observers.size() - 1));
		}
		
	
	}


	/*SENDING NOTIFICATIONS
	
 	At the end of method addObserver, send an onRegister notification only to the new observer in order to pass it the current state of the simulator.

	At the end of method reset, send an onReset notification to all observers.
	
	At the end of method addBody, send an onBodyAdded notification to all observers.
	
	At the end of method advance, send an onAdvance notification to all observers.
	
	At the end of method setDeltaTime, send an onDeltaTimeChanged notification to all observers.
	
	At the end of method setForceLaws, send an onForceLawsChanged notification to all Observers
 
 */
	
	
	private void notifyOnAdvance() {
		for(SimulatorObserver s: observers) {
			s.onAdvance(bodies, currentTime);
		}
		
	}
	
	
	
	private void notifyOnBodyAdded(Body b) {
		for(SimulatorObserver s: observers) {
			s.onBodyAdded(bodies, b);
		}
		
	}

		private void notifyOnReset() {
			for(SimulatorObserver s: observers) {
				s.onReset(bodies, currentTime, deltaTime,  laws.toString());
			}
		
	}

		private void notifyOnForceLawsChanged() {
			for(SimulatorObserver s: observers) {
				s.onForceLawsChanged(laws.toString());
			}
		
	}
		
	private void notifyOnRegister(SimulatorObserver simulatorObserver) {
	
		simulatorObserver.onRegister(bodies, currentTime, deltaTime , laws.toString());
		
		
	}	
	
	private void notifyOnDeltaChanged() {
		for(SimulatorObserver s: observers) {
			s.onDeltaTimeChanged(deltaTime);
		}
		
	}

}

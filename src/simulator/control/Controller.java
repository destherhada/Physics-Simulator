package simulator.control;



import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {

	protected PhysicsSimulator simulator;
	protected Factory<Body> bodiesFactory;
	protected Factory<ForceLaws> forceLawsFactory;
	
	public Controller(PhysicsSimulator sim,  Factory<Body> bodiesFactory, Factory<ForceLaws> forceLawsFactory) {
		this.simulator = sim;
		this.bodiesFactory = bodiesFactory;
		this.forceLawsFactory = forceLawsFactory;
		
		}
	
	
	public void loadBodies(InputStream in){
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		//get the array of bodies from input jsonobject
		JSONArray body = jsonInput.getJSONArray("bodies");	
		Body aux;
		
		for(int i = 0; i < body.length(); i++) {
			//creates corresponding body using bodies factory
			aux = bodiesFactory.createInstance(body.getJSONObject(i));
			//add the body to the simulator
			simulator.addBody(aux);  
		}
	}
	
	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws  ExpectedException {
		PrintStream p = new PrintStream(out);	
		JSONObject expectedOut = null;
		JSONArray expectedOutArray = null;
		
		if (expOut != null) {
			expectedOut = new JSONObject(new JSONTokener(expOut));		
			expectedOutArray = expectedOut.getJSONArray("states");
		}

	
		p.println("{");
		p.println("\"states\" : [");

		
		//for state 0
		p.println(simulator.toString());
	
			if(expectedOut != null){
				if(!cmp.equal(expectedOutArray.getJSONObject(0), simulator.getState())) {
			
			throw new ExpectedException(expectedOutArray.getJSONObject(0), simulator.getState(), n);
		}
			}
		
		//for the other states
		for(int i = 1; i < n + 1 ; i++) {
			simulator.advance();
			p.println(",");
			
			if(expectedOut != null){
				
					
				if(!cmp.equal(expectedOutArray.getJSONObject(i), simulator.getState())) {
					
					throw new ExpectedException(expectedOutArray.getJSONObject(i), simulator.getState(), n);
				}
			}
				
			//this is for testing in the viewer html
			if(i == n-1) {
				p.println(simulator.toString());
			}
			else {
				p.println(simulator.toString() + ",");
			}
		
}
		p.println("]");
		p.println("}");
		
		
}
	
	//calls reset of simulator
	public void reset() {
		simulator.reset();
	}
	
	//calls setDeltaTime of the simulator
	public void setDeltaTime  (double dt) {
		simulator.setDeltaTime(dt);
	}
	
	// calls addObserver of the simulator
	public void addObserver(SimulatorObserver o) {
		simulator.addObserver(o);
	}
	
	//runs the simulator for n steps, without printing anything to the console.
	public void run(int n) {
		for(int i = 0; i < n ; i++) {
			simulator.advance();
		}
			
	}
	
	//returns the list returned by calling getInfo() of the force laws factory. This will be used in the GUI to show the available force laws and allow changing them.
	public List<JSONObject> getForceLawsInfo() {
		return forceLawsFactory.getInfo();
		
	}
	
	
	//uses the current force laws factory to create a new force laws object as indicated in info, and then changes the simulator’s force laws to the new one.
	public void setForcesLaws(JSONObject info) {

		simulator.setForceLaws(forceLawsFactory.createInstance(info));
		
	}
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}

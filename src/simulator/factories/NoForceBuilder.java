package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	
	public NoForceBuilder() {
		super("nf", "No Force");
	}
	
	@Override
	ForceLaws createTheInstance(JSONObject info) {
		return new NoForce();

	}
	
	

	protected JSONObject getData() {
		JSONObject jo = new JSONObject();
		return jo;
	}

	

}

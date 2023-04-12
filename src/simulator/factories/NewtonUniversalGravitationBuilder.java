package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	public static final double G = 6.674E-11;


	
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton's law of universal gravitation");
	}
	
	
	@Override
	ForceLaws createTheInstance(JSONObject info) {
		
		if(info.has("G")){
			return new NewtonUniversalGravitation(info.getDouble("G"));

		}
		else {
			return new NewtonUniversalGravitation(G);

		}
	}
	
	
	

	public JSONObject getData() {

		JSONObject data = new JSONObject();
		
		
		data.put("G",  "the gravitational constant (a number)");
		
		
		return data;
		
	}

}

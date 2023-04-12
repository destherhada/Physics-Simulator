package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{
	
	private  double g = 9.81;
	private  Vector2D c = new Vector2D();
	

	public MovingTowardsFixedPointBuilder() {
		super( "mtfp","Moving Towards Fixed Point Force Law");
	}
	
	

	@Override
	ForceLaws createTheInstance(JSONObject info) {
				
		  if (info.has("g")) {
			  g = info.getDouble("g");
		  }
		  if (info.has("c")) {
			  c = new Vector2D(info.getJSONArray("c").getDouble(0), info.getJSONArray("c").getDouble(1));
		  }
		  
		  return new MovingTowardsFixedPoint(c, g);

	}
	

	public JSONObject getData() {


		JSONObject data = new JSONObject();

		data.put("c", "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0,50.0])");
		data.put("g", "the length of the acceleration vector (a number)");
	
		
		return data;
		
	}

}

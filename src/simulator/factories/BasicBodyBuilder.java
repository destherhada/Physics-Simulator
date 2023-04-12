package simulator.factories;

import org.json.JSONObject;
import simulator.misc.Vector2D;

import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {

	
	public BasicBodyBuilder() {
		super("basic", "Basic body");
	
	}
	
	
	@Override
	Body createTheInstance(JSONObject info) {
		
		Vector2D vv = new Vector2D(info.getJSONArray("v").getDouble(0), info.getJSONArray("v").getDouble(1));
		Vector2D pp = new Vector2D(info.getJSONArray("p").getDouble(0), info.getJSONArray("p").getDouble(1));

		
		return new Body(info.getString("id"), vv, pp, info.getDouble("m"));
	}
	
	
	@Override
	protected JSONObject createData() {
		//createData should just used some default value or descriptions
		JSONObject data = new JSONObject();
		
		data.put("id", new String());
		data.put("p", new Vector2D().asJSONArray());
		data.put("v", new Vector2D().asJSONArray());
		data.put("m", 0.0);
		
		return data;
	
		
	}
	
	

}

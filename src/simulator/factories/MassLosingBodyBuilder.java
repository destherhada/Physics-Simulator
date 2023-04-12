package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;


public class MassLosingBodyBuilder extends Builder<Body>{
	

	
	public MassLosingBodyBuilder() {
		super("mlb","Mass Losing body");

	}
	
	
	@Override
	Body createTheInstance(JSONObject info) {
		
		return new MassLossingBody(info.getString("id"), new Vector2D(info.getJSONArray("v").getDouble(0), info.getJSONArray("v").getDouble(1)), new Vector2D(info.getJSONArray("p").getDouble(0), info.getJSONArray("p").getDouble(1)), info.getDouble("m"), info.getDouble("factor"),info.getDouble("freq") );
	
	}
	
	@Override
	protected JSONObject createData() {
		//createData should just used some default value or descriptions
		JSONObject data = new JSONObject();
		
		data.put("id", new String());
		data.put("p", new Vector2D().asJSONArray());
		data.put("v",  new Vector2D().asJSONArray());
		data.put("m", 0.0);
		data.put("freq", 0.0);
		data.put("factor", 0.0);

		return data;
		
	}

}

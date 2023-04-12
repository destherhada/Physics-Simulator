package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {

	
	protected String type, description;
	
	public Builder(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public T createInstance(JSONObject info) {

	T t = null;	
	
	if(type != null) {
		if(type.equals(info.getString("type"))) {
			t = createTheInstance(info.getJSONObject("data"));
		}
	}

	return t;
		
	}
	
	
	
	public JSONObject getBuilderInfo() {
		JSONObject json = new JSONObject();

		json.put("type", type);
		json.put("data", getData());
		json.put("desc", description);

		return json;
		
	}
	
	abstract T createTheInstance(JSONObject info);
	
	
	
	protected JSONObject createData() {
		return new JSONObject();
		
	}

	protected JSONObject getData() {
	
		return new JSONObject();
	}
	

}

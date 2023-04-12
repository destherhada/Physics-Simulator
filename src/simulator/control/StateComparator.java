package simulator.control;

import org.json.JSONObject;

public interface StateComparator {
	public boolean equal(JSONObject s1, JSONObject s2);
}

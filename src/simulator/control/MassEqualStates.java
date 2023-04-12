package simulator.control;

import org.json.JSONObject;
import org.json.JSONArray;


public class MassEqualStates implements StateComparator {
	
	public MassEqualStates() {
		
	}

	
	public boolean equal(JSONObject s1, JSONObject s2) {
		boolean eq = false;
		
		JSONArray body1 = s1.getJSONArray("bodies"); //json array of bodies in s1
		JSONArray body2 = s2.getJSONArray("bodies"); //json array of bodies in s2

		//the value of their key "time is equal
		if(s1.getDouble("time") == s2.getDouble("time")) {			//the i-th bodies in the lists in s1 and s2 have the same values for keys id and mass
			int i = 0;
				do {
				if(body2.getJSONObject(i).getString("id").equals(body1.getJSONObject(i).getString("id"))) {
				
					if(body2.getJSONObject(i).getDouble("m") == body1.getJSONObject(i).getDouble("m")) {
						eq = true;
					}
				}
		
				
				i++;
			}while(i < s2.length() && i < s1.length() && eq == true);
		
		}

		
		
		return eq;
	}
}

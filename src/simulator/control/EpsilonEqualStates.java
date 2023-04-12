package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {
	
	protected double eps;
	
	
	public EpsilonEqualStates(double epsilon) {
		this.eps = epsilon;
	}

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		boolean eq = false;
	
		//two states s1 and s2 are equal if:
		JSONArray bodies1 = s1.getJSONArray("bodies");
		JSONArray bodies2 = s2.getJSONArray("bodies");
		

		//-the value of their "time" key is equal
		if(s1.getDouble("time") == s2.getDouble("time")) {
			
				int i = 0;
				do {
				
				//-the i-th bodies in the lists of bodies in s1 and s2 must have equal values for key “id”		
				if(bodies1.getJSONObject(i).getString("id").equals(bodies2.getJSONObject(i).getString("id"))) {
					
					//-and eps-equal values for “m”, “p”, “v” and “f”
					if(Math.abs(bodies1.getJSONObject(i).getDouble("m") - bodies2.getJSONObject(i).getDouble("m")) <= eps) {
						
						Vector2D p1 =  new Vector2D(bodies1.getJSONObject(i).getJSONArray("p").getDouble(0), bodies1.getJSONObject(i).getJSONArray("p").getDouble(1));
						Vector2D p2 =  new Vector2D(bodies2.getJSONObject(i).getJSONArray("p").getDouble(0), bodies2.getJSONObject(i).getJSONArray("p").getDouble(1));

						if(p1.distanceTo(p2) <= eps) {
							
							Vector2D v1 =  new Vector2D(bodies1.getJSONObject(i).getJSONArray("v").getDouble(0), bodies1.getJSONObject(i).getJSONArray("v").getDouble(1));
							Vector2D v2 =  new Vector2D(bodies2.getJSONObject(i).getJSONArray("v").getDouble(0), bodies2.getJSONObject(i).getJSONArray("v").getDouble(1));
							
							if(v1.distanceTo(v2) <= eps) {
								
								Vector2D f1 =  new Vector2D(bodies1.getJSONObject(i).getJSONArray("f").getDouble(0), bodies1.getJSONObject(i).getJSONArray("f").getDouble(1));
								Vector2D f2 =  new Vector2D(bodies2.getJSONObject(i).getJSONArray("f").getDouble(0), bodies2.getJSONObject(i).getJSONArray("f").getDouble(1));
								
								if(f1.distanceTo(f2) <= eps){
									
									eq = true;
									
								}
								
							}
						
						}
						
					}
					
					}
			
				
				i++;
			}while(i < s2.length() && i < s1.length() && eq == true);
				
						
			
		
		}
		
		else {eq = false;}
		
		return eq;	
}

	
	}


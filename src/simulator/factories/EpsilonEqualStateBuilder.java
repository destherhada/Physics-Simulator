package simulator.factories;
import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStateBuilder extends Builder<StateComparator> {


	private final double eps = 0.0;

	
	public EpsilonEqualStateBuilder() {
		super("espeq","Epsilon Equal State Comparator");
	}
	
	
	@Override
	StateComparator createTheInstance(JSONObject info) {
	
		
		if(!info.has("eps")) {
			return new EpsilonEqualStates(eps);
		}
		else {
			return new EpsilonEqualStates(info.getDouble("eps"));

		}
		
	}
	
	
	@Override
	protected JSONObject createData() {
		//createData should just used some default value or descriptions
		//here default value for eps
		 JSONObject data = new JSONObject();
		
		data.put("eps", eps);
		
		
		return data;
	}
	

}

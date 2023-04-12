package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStateBuilder extends Builder<StateComparator>{

	public MassEqualStateBuilder() {
		super("masseq", "Mass Equal States Comparator");
	}

	@Override
	StateComparator createTheInstance(JSONObject info) {
		return new  MassEqualStates();
	}
	
	
}

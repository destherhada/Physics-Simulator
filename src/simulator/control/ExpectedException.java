package simulator.control;

import org.json.JSONObject;

public class ExpectedException extends Exception {

	protected JSONObject state1, state2;
	protected int n;
	
	public ExpectedException(JSONObject state1, JSONObject state2, int exStep) {
		this.state1 = state1;
		this.state2 = state2;
		this.n = exStep;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public String getMessage() {
		String total;
		total = "State expected and simulated are not the same." + System.lineSeparator() + 
				"State expected: " + state1 + System.lineSeparator() + 
				"State simulated: " + state2 + System.lineSeparator() + 
				"Number of execution step: " + n; 
		return total;
	}


}

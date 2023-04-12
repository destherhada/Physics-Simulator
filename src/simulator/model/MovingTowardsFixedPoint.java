package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	protected Vector2D vectorC;
	protected double g;
	
	
	public MovingTowardsFixedPoint(Vector2D vectorC, double g){
	this.vectorC = vectorC;
	this.g = g;
	
	}
	
	
	@Override
	public void apply(List<Body> bodies) { 
		Vector2D forceVector;
		Vector2D directionVector;
		
		for(int i = 0; i <bodies.size(); i++) {
			directionVector = vectorC.minus(bodies.get(i).getPosition()).direction(); //direction of c-p
			forceVector = ( directionVector.scale((bodies.get(i).getMass() * g))); //m*g * direction
			bodies.get(i).addForce(forceVector);
		
		}
		
	}
	
	
	public String toString() {
		return "Moving towards " + vectorC.toString() + " with  constant acceleration " + g;
	}

}
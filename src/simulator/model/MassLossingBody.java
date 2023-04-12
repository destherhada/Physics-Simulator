package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body {

	protected double lossFactor; //number between 0-1 indicating the mass loss factor
	protected double lossFrequency; //positive number indicating time interval (in s) after which the object losses mass
	protected double counter = 0.0; //counter initialized to 0
	
	
	public MassLossingBody(String id, Vector2D velocityVector, Vector2D positionVector, double mass, double lossFactor, double lossFrequency) {
		super(id, velocityVector, positionVector, mass);
		this.lossFactor = lossFactor;
		this.lossFrequency = lossFrequency;
	}
	
	@Override
	void move(double t) {
		
		super.move(t);
		
		counter += t;
		
		if(counter >= lossFrequency) {
			counter = 0.0;
			mass = mass * (1- lossFactor);
		}
		
	}
	
	

}
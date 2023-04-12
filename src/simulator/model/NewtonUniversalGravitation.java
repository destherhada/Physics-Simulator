package simulator.model;
import simulator.misc.Vector2D;
import java.util.List;

public class NewtonUniversalGravitation implements ForceLaws{

	protected double G;
	
	public NewtonUniversalGravitation(double G) {
		this.G = G;
	}
	
	
	
	@Override
	public void apply(List<Body> bodies) {
		 Vector2D directionVector;
		 double force;
		 Vector2D ForceVector;
		 double aux;
	
	//force applied by all the bodies in the list to each body
		for(int j= 0; j <bodies.size(); j++) {
			for(int i = 0; i < bodies.size(); i++) {
				if(i != j) {
					
				//direction vector 
				directionVector = bodies.get(i).getPosition().minus(bodies.get(j).getPosition()).direction();
				
				//force 
				aux = bodies.get(i).getPosition().distanceTo(bodies.get(j).getPosition());  // |pj - pi|
				
				if( Math.pow(aux, 2) > 0) {
					force = G * bodies.get(i).getMass() *  bodies.get(j).getMass() / Math.pow(aux, 2);
	
				}
				
				else {
					force = 0.0;
				}
				
				//Force vector direction vector scaled by force
				
				ForceVector = directionVector.scale(force);

				
				bodies.get(j).addForce(ForceVector);
		}
			}
		}

		
	}
	
	public String toString() {
		return "Newton's Universal Gravitation with G = " + G;
	}
	


}
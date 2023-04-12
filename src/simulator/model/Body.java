package simulator.model;
import simulator.misc.Vector2D;
import java.lang.Math;

import org.json.JSONObject;

public class Body {
	
	
	protected String id;
	protected Vector2D velocityVector;
	protected Vector2D forceVector= new Vector2D();
	protected Vector2D positionVector;
	protected double mass;
	
	public Body(String id, Vector2D velocityVector, Vector2D positionVector, double mass) {
		this.id = id;
		this.velocityVector = velocityVector;
		this.positionVector = positionVector;
		this.mass = mass;
	
	}
	
	
	//returns the bodys identifier
	public String getId() {
		return this.id;
	}
	
	//returns the velocity vector
	public Vector2D getVelocity() {
		return this.velocityVector;
	}
	
	
	//returns the position vector
	public Vector2D getPosition() {
		return this.positionVector;
	}
	
	//returns the force vector
	public Vector2D getForce() {
		return this.forceVector;
	}
	
	//returns the mass
	public double getMass() {
		return this.mass;
	}
	
	
	/*adds the force f to the vector of the body
	using the method plus of Vector2D*/
	void  addForce(Vector2D f) {
		this.forceVector = forceVector.plus(f);
	}
	
	
	//sets the force vector of the body to (0,0)
	void resetForce() {
		this.forceVector = new Vector2D();
	}
	
	
	
	/*moves the body for t seconds as follows:
	
	*1-compute a using newtons second law, if m = 0 then set a to (0,0)
	*
	*2-change position to: p+v *t + 1/2 *a*t^2
	*
	*3-change the velocity to: v + a *t
	*/
	

	void move(double t) {
		Vector2D acceleration = new Vector2D(); //initialised to 0,0


		
		if (mass != 0) {
			acceleration = forceVector.scale(1/mass); 
		}
		
		//first change position then velocity
		positionVector = positionVector.plus(velocityVector.scale(t)).plus((acceleration.scale(0.5).scale(Math.pow(t,2))));
		
		velocityVector = velocityVector.plus(acceleration.scale(t));
		
	
		
		
		
	}
	
	
	/*returns the following JSON structure that includes the body's information
	 * {"id": id, "m": m, "p": p, "v": v, "f": f}
	 */
	public JSONObject getState() {
		
		JSONObject state = new JSONObject();
		
		state.put("id", id);
		state.put("m", mass);
		state.put("p", positionVector.asJSONArray());
		state.put("v",velocityVector.asJSONArray());
		state.put("f", forceVector.asJSONArray());
		
		return  state;
	}
	

	
	
	//returns what is returned by getState().toString()
	public String toString() {
		return getState().toString();
	}
	
	
	//define equal that compares id for using contains in physiscSimulator class
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
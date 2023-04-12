package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;


public class BuilderBasedFactory<T> implements Factory<T>{
	
	protected List<Builder<T>> builderList;
	protected List<JSONObject> jsonList;
	
	public BuilderBasedFactory(List<Builder<T>> builderList) {
		
		this.builderList = new ArrayList<>(builderList);
		jsonList = new ArrayList<JSONObject>();
		
		for(Builder<T> b : builderList) {
			 jsonList.add(b.getBuilderInfo()) ;
		}
		
	}

	@Override
	public T createInstance(JSONObject info){
		
		for(Builder<T> b : builderList) {
			T t = b.createInstance(info);	
			
			if((t != null)) {
				
			return t;
			
		}
		}
		
return null;
		
	}

	@Override
	public List<JSONObject> getInfo() {
		return jsonList;
	
	}

}

package rinde.sim.core.model.virtual;

/**
 * Het type veld, met hoe groot de radius & sterkte van het veld is
 */
public enum FieldType{

	ATTRACTIVE(1,20000), REPULSIVE(1,20000);
	
	private float strength;
	private float radius;
	FieldType(float strength1, float radius1){
		this.strength = strength1;
		this.radius = radius1;
	}
	
	public float getStrength(){
		return this.strength;
	}
	
	public float getRadius(){
		return this.radius;
	}
}
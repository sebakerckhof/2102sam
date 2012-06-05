package rinde.sim.gradientfields.model.virtual;

public interface FieldData {
	public FieldType getType();
	public VirtualEntity getEntity();
	public float getRadius();
	public float getStrength();
}

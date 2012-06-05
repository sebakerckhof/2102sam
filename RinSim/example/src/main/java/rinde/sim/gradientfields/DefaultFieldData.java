package rinde.sim.gradientfields;

import rinde.sim.gradientfields.model.virtual.FieldData;
import rinde.sim.gradientfields.model.virtual.FieldType;
import rinde.sim.gradientfields.model.virtual.VirtualEntity;

/**
 * Implementatie van de field data
 */
public class DefaultFieldData implements FieldData{
	protected final FieldType type;
	protected final VirtualEntity entity;
	protected final float strengthMultiplier;
	
	public DefaultFieldData(FieldType type, VirtualEntity entity, float strengthMultiplier){
		this.type = type;
		this.entity = entity;
		this.strengthMultiplier = strengthMultiplier;
	}

	@Override
	public FieldType getType() {
		return type;
	}

	@Override
	public VirtualEntity getEntity() {
		return entity;
	}

	@Override
	public float getRadius() {
		return type.getRadius();
	}

	@Override
	public float getStrength() {
		return type.getStrength() * this.strengthMultiplier;
	}
	
}

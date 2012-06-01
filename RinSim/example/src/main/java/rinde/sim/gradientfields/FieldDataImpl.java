package rinde.sim.gradientfields;

import rinde.sim.gradientfields.model.virtual.FieldData;
import rinde.sim.gradientfields.model.virtual.FieldType;
import rinde.sim.gradientfields.model.virtual.VirtualEntity;

/**
 * Implementatie van de field data
 */
public class FieldDataImpl implements FieldData{
	protected final FieldType type;
	protected final VirtualEntity entity;
	
	public FieldDataImpl(FieldType type, VirtualEntity entity){
		this.type = type;
		this.entity = entity;
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
	
}

package rinde.sim.lab.session2.gradient_field_exercise;

import rinde.sim.core.model.virtual.FieldData;
import rinde.sim.core.model.virtual.FieldType;
import rinde.sim.core.model.virtual.VirtualEntity;

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

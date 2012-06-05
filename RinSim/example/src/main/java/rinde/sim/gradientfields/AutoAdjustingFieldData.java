package rinde.sim.gradientfields;

import rinde.sim.gradientfields.model.virtual.FieldType;
import rinde.sim.gradientfields.model.virtual.VirtualEntity;
import rinde.sim.gradientfields.packages.PassengerAgent;


public class AutoAdjustingFieldData extends DefaultFieldData{
	

	public AutoAdjustingFieldData(FieldType type, PassengerAgent entity, float strengthMultiplier) {
		super(type, entity, strengthMultiplier);
	}

	public float getStrenth(){
		return super.getStrength() * ((PassengerAgent) entity).getMultiplier();
	}

}

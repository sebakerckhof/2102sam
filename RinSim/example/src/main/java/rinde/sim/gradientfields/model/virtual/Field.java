package rinde.sim.gradientfields.model.virtual;

public class Field {
	
	private FieldData fieldData;
	private double distance;
	
	public Field(FieldData fieldData, double distance) {
		this.fieldData = fieldData;
		this.distance = distance;
	}

	public FieldData getFieldData() {
		return fieldData;
	}

	public double getDistance() {
		return distance;
	}

}

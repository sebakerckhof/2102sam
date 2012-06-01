package rinde.sim.gradientfields.model.virtual;


import java.util.Collection;

import rinde.sim.core.graph.Point;

public interface GradientFieldAPI {

	public Collection<VirtualEntity> getEntities();

	public Collection<Field> getFields(Point point);

	public Collection<Field> getFieldsFrom(VirtualEntity entity);

	public Collection<Field> getSimpleFields(Point point);
}

package rinde.sim.gradientfields;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.gradientfields.model.virtual.Field;
import rinde.sim.gradientfields.model.virtual.GradientFieldModel;
import rinde.sim.gradientfields.model.virtual.VirtualEntity;
import rinde.sim.gradientfields.trucks.TruckAgent;
import rinde.sim.ui.renderers.Renderer;
import rinde.sim.ui.renderers.UiSchema;

/**
 * @author Rinde van Lon (rinde.vanlon@cs.kuleuven.be)
 * @author Bartosz Michalik <bartosz.michalik@cs.kuleuven.be> changes in handling colors
 * 
 */
public class FieldRenderer implements Renderer {
	
	protected GradientFieldModel gfm;
	private UiSchema uiSchema;

	public FieldRenderer(GradientFieldModel gfm) {
		this(gfm, new UiSchema(false));
		
	}

	public FieldRenderer(GradientFieldModel gfm, UiSchema schema) {
		if(schema == null) schema = new UiSchema(false);
		this.gfm = gfm;

		this.uiSchema = schema;
	}

	@Override
	public void render(GC gc, double xOrigin, double yOrigin, double minX, double minY, double m) {
		final int radius = 2;
		uiSchema.initialize();
		gc.setBackground(uiSchema.getDefaultColor());
		
		ColorRegistry colorRegistry = new ColorRegistry();
		colorRegistry.put("green", new RGB(0, 255,0));
		colorRegistry.put("red", new RGB(255,0,0));
		
		Collection<VirtualEntity> entities = gfm.getEntities();
		synchronized (entities) {
			for (VirtualEntity e : entities) {
			
				//dirty hack, but works
				if(e instanceof TruckAgent){
					TruckAgent agent = (TruckAgent) e;
					Collection<Field> fields = gfm.getFieldsFrom(agent);
					final int x1 = (int) (xOrigin + (e.getPosition().x - minX) * m) - radius;
					final int y1 = (int) (yOrigin + (e.getPosition().y - minY) * m) - radius;
					for(Field f : fields){
						final int x2 = (int) (xOrigin + (f.getFieldData().getEntity().getPosition().x - minX) * m) - radius;
						final int y2 = (int) (yOrigin + (f.getFieldData().getEntity().getPosition().y - minY) * m) - radius;
						
						switch(f.getFieldData().getType()){
						case ATTRACTIVE:
							//gc.setBackground(colorRegistry.get("green"));
							gc.setForeground(colorRegistry.get("green"));
							break;
						case REPULSIVE:
							//gc.setBackground(colorRegistry.get("green"));
							gc.setForeground(colorRegistry.get("red"));
							break;
						}
						gc.drawLine(x1,y1,x2,y2);
					}
				}
				
			}
		}
	}

}

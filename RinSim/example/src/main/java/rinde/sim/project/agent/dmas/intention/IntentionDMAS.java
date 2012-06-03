package rinde.sim.project.agent.dmas.intention;

import java.util.List;

import rinde.sim.project.agent.PackageAgent;
import rinde.sim.project.model.DMAS;

public class IntentionDMAS extends DMAS{
	
	protected List<PackageAgent> intention;
	
	public void setIntention(List<PackageAgent> packages){
		intention = packages;
	}
	
	@Override
	public void execute(){
		IntentionAnt a = new IntentionAnt();
		model.deploy(a);
	}
}

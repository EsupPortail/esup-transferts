package org.esupportail.transferts.web.scheduler;

public class QuartzJob {
	private BusinessManager businessManager;

	public void execute(){
		businessManager.runAction();
	}
 
	public void setBusinessManager(BusinessManager businessManager) {
		this.businessManager = businessManager;
	}
}


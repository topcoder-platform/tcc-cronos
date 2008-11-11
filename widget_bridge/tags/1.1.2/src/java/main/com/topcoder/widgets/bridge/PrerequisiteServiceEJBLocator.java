package com.topcoder.widgets.bridge;

import com.topcoder.service.prerequisite.PrerequisiteService;

/**
 * Locator for the EJB for Prerequisite Service
 * 
 * @author Cucu
 *
 */
public class PrerequisiteServiceEJBLocator extends EJB3Locator<PrerequisiteService>{

	private static final String DEFAULT_JNDI_NAME = "PrerequisiteServiceBean/remote";
	
	private String jndiName;
	
	public PrerequisiteServiceEJBLocator(String jndiName) {
		super("");
		this.jndiName = jndiName;
	}

	public PrerequisiteServiceEJBLocator() {
		this(DEFAULT_JNDI_NAME);
	}

	@Override
	protected String getJndiName() {
		return jndiName;
	}

}

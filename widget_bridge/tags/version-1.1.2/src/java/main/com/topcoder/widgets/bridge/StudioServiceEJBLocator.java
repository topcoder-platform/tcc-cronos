package com.topcoder.widgets.bridge;

import com.topcoder.service.studio.StudioService;

/**
 * Locator for the Studio Service EJB
 * 
 * @author Cucu
 *
 */
public class StudioServiceEJBLocator extends EJB3Locator<StudioService>{

	private static final String DEFAULT_JNDI_NAME = "StudioServiceBean/remote";
	
	private String jndiName;
	
	public StudioServiceEJBLocator(String jndiName) {
		super("");
		this.jndiName = jndiName;
	}

	public StudioServiceEJBLocator() {
		this(DEFAULT_JNDI_NAME);
	}

	@Override
	protected String getJndiName() {
		return jndiName;
	}

}

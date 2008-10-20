package com.topcoder.widgets.bridge;

import com.topcoder.service.project.ProjectService;

/**
 * Locator for the Project Service EJB
 * 
 * @author Cucu
 *
 */
public class ProjectServiceEJBLocator extends EJB3Locator<ProjectService>{

	private static final String DEFAULT_JNDI_NAME = "ProjectServiceBean/remote";
	
	private String jndiName;
	
	public ProjectServiceEJBLocator(String jndiName) {
		super("");
		this.jndiName = jndiName;
	}

	public ProjectServiceEJBLocator() {
		this(DEFAULT_JNDI_NAME);
	}

	@Override
	protected String getJndiName() {
		return jndiName;
	}

}

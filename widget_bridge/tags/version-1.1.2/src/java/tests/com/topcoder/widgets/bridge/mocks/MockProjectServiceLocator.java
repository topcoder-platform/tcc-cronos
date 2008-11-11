/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.widgets.bridge.mocks;

import com.topcoder.service.project.ProjectService;
import com.topcoder.widgets.bridge.ServiceLocator;

/**
 * <p>
 * Mock project service locator.
 * </p>
 *
 * @author BeBetter
 * @version 1.0
 */
public class MockProjectServiceLocator implements ServiceLocator<ProjectService> {

    /**
     * <p>
     * Returns mock project service.
     * </p>
     *
     * @return created project service
     * @throws Exception if any error occurs
     */
    public ProjectService getService() throws Exception {
        return new MockProjectService();
    }

}

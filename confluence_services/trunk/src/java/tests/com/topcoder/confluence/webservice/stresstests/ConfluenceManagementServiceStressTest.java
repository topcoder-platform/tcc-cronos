/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice.stresstests;

import junit.framework.TestCase;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.webservice.delegate.ConfluenceManagerWebServiceDelegate;

/**
 * Stress test class for this component.
 *
 * @author zjuysp
 * @version 1.0
 */
public class ConfluenceManagementServiceStressTest extends TestCase {
    /**
     * <p>
     * The times for stress test.
     * </p>
     */
    private static final int TIMES = 100;

    /**
     * <p>
     * Stress test for this component.
     * </p>
     *
     * @throws Exception into JUnit
     */
    @SuppressWarnings("unused")
    public void testConfluenceManagementService() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            // create a ConfluenceManagerWebServiceDelegate using the default constructor:
            ConfluenceManagerWebServiceDelegate confluenceManager = new ConfluenceManagerWebServiceDelegate();

            // log in to Confluence and retrieve the needed token to perform the operations
            String token = confluenceManager.login("username", "password");

            // create a new page in confluence
            ConfluencePageCreationResult confluencePageCreationResult = confluenceManager
                    .createPage(token, "Erebus Token Web Services", "1.0",
                            ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.JAVA,
                            ComponentType.CUSTOM);

            // create another page in confluence
            confluencePageCreationResult = confluenceManager.createPage(token,
                    "Erebus Payment Web Services", "1.0", ConfluenceAssetType.COMPONENT_DESIGN,
                    ConfluenceCatalog.JAVA, "12345");

            // retrieve a page
            Page page = confluenceManager.retrievePage(token, "Erebus Token Web Services", "1.0",
                    ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.JAVA,
                    ComponentType.CUSTOM);

            // log out from the Confluence:
            confluenceManager.logout(token);
        }

    }
}

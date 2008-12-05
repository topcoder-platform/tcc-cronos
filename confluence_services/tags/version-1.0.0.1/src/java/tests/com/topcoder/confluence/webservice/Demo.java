/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.confluence.webservice;

import java.net.URL;

import junit.framework.TestCase;

import com.topcoder.confluence.entities.ComponentType;
import com.topcoder.confluence.entities.ConfluenceAssetType;
import com.topcoder.confluence.entities.ConfluenceCatalog;
import com.topcoder.confluence.entities.ConfluencePageCreationResult;
import com.topcoder.confluence.entities.Page;
import com.topcoder.confluence.webservice.client.ConfluenceManagementServiceClient;
import com.topcoder.confluence.webservice.delegate.ConfluenceManagerWebServiceDelegate;

/**
 * <p>
 * The demo usage of this component.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

    /**
     * <p>
     * The demo usage of the <code>ConfluenceManagerWebServiceDelegate</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unused")
    public void test_demo_usage1() throws Exception {

        // create a ConfluenceManagerWebServiceDelegate using the constructor with filename and namespace
        ConfluenceManagerWebServiceDelegate confluenceManager =
            new ConfluenceManagerWebServiceDelegate(ConfluenceManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                "demo");

        // log in to Confluence and retrieve the needed token to perform the operations
        String token = confluenceManager.login("user", "password");
        // if the user or password are not valid in Confluence, an appropriate exception will be thrown

        // create a new page in confluence with CompontType - CUSTOM
        ConfluencePageCreationResult confluencePageCreationResult =
            confluenceManager.createPage(token, "Erebus Token Web Services", "1.0",
                ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);

        // create a new page in confluence with application code
        confluencePageCreationResult =
            confluenceManager.createPage(token, "Erebus Payment Web Services", "1.0",
                ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.JAVA, "12345");

        // create another page in confluence with page
        confluencePageCreationResult = confluenceManager.createPage(token, new Page());

        // retrieve a page with ComponentType
        Page page =
            confluenceManager.retrievePage(token, "Erebus Token Web Services", "1.0",
                ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.DOT_NET, ComponentType.CUSTOM);

        // retrieve a page with applicationCode
        page =
            confluenceManager.retrievePage(token, "Erebus Token Web Services", "1.0",
                ConfluenceAssetType.COMPONENT_DESIGN, ConfluenceCatalog.JAVA, "12345");

        // log out from the Confluence:
        confluenceManager.logout(token);
        // if the token is not valid in Confluence, an appropriate exception will
        // be thrown
    }

    /**
     * <p>
     * The demo usage of the <code>ConfluenceManagementServiceClient</code>.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("unused")
    public void test_demo_usage2() throws Exception {
        String address =
            "http://127.0.0.1:8080/confluence_services-confluence_services-ejb/ConfluenceManagementServiceBean?wsdl";
        // create a ConfluenceManagementServiceClient using the default qualified name and a specific address of
        // the service, and obtain a proxy:
        URL url = new URL(address);
        ConfluenceManagementServiceClient client = new ConfluenceManagementServiceClient(url);
        ConfluenceManagementService service = client.getConfluenceManagementServicePort();
    }
}
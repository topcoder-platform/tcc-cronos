/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import org.apache.struts2.StrutsSpringTestCase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.GenericXmlContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * <p>
 * The base class for all test classes which test actions using Spring/Struts environment. It
 * extends <code>StrutsSpringTestCase</code>.
 * </p>
 * @author zju_jay
 * @version 1.0
 */
@ContextConfiguration(locations = {"/applicationContext.xml" })
public class BaseStrutsSpringTestCase extends StrutsSpringTestCase {

    /**
     * Makes sure that beans are only loaded from spring one time in order to prevent any memory
     * leaks and speed up unit testing.
     * @throws Exception to JUnit
     */
    @Override
    protected void setupBeforeInitDispatcher() throws Exception {
        // only load beans from spring once
        if (applicationContext == null) {
            GenericXmlContextLoader xmlContextLoader = new GenericXmlContextLoader();
            applicationContext = xmlContextLoader.loadContext(getContextLocations());
        }

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
            applicationContext);
    }

}

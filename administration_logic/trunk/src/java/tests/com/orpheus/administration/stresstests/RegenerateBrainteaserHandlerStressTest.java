/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.orpheus.administration.AdministrationManager;
import com.orpheus.administration.handlers.RegenerateBrainteaserHandler;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link RegenerateBrainteaserHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class RegenerateBrainteaserHandlerStressTest extends AbstractHandlerStressTest {

    protected void setUp() throws Exception {
        super.setUp();

        request = new MockHttpRequest();
        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        ((MockHttpRequest) request).setParameter("slotId", "1");
        ((MockHttpRequest) request).getSession(false).getServletContext().setAttribute("adminMgr",
            new AdministrationManager("com.orpheus.administration.AdministrationManager"));
    }

    protected void assertAfterExecute() {
        super.assertAfterExecute();

        assertEquals("null should be returned.", null, result);
    }

    /**
     * <p>
     * Get the handler instance to be tested.
     * </p>
     * @return the handler to be test.
     * @throws Exception
     *             If fail to get.
     */
    protected Handler getHandler() throws Exception {
        String xml = "<handler type=\"regenerateBrainteaser\">" + "<game-data-jndi-name>"
            + StressTestHelper.GAME_DATA_JNDI_NAME + "</game-data-jndi-name>"
            + "<slot-id-request-param>slotId</slot-id-request-param>"
            + "<admin-mgr-app-attr>adminMgr</admin-mgr-app-attr >"
            + "<fail-result>Failed</fail-result><fail-request-attribute>fail" + "</fail-request-attribute></handler>";
        return new RegenerateBrainteaserHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "RegenerateBrainteaserHandler";
    }

}

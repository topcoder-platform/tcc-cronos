/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.orpheus.administration.handlers.DomainApprovalHandler;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link com.orpheus.administration.handlers.DomainApprovalHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class DomainApprovalHandlerStressTest extends AbstractHandlerStressTest {

    protected void setUp() throws Exception {
        super.setUp();

        ((MockHttpServletRequest) request).setupAddParameter("DomainId", "1");
        ((MockHttpServletRequest) request).setupAddParameter("sponsorId", "1");
    }

    protected void assertAfterExecute() {
        super.assertAfterExecute();

        assertNull("should return null", result);
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
        String xml = "<handler type=\"domainApproval\"><object-factory-ns>"
            + "objFactoryNS</object-factory-ns><admin-data-jndi-name>" + StressTestHelper.ADMIN_DATA_JNDI_NAME
            + "</admin-data-jndi-name><game-data-jndi-name>" + StressTestHelper.GAME_DATA_JNDI_NAME
            + "</game-data-jndi-name><domain-id-request-param>"
            + "DomainId</domain-id-request-param><sponsor-id-request-param>"
            + "sponsorId</sponsor-id-request-param><fail-result>Failed"
            + "</fail-result><fail-request-attribute>fail</fail-request-attribute>" + "</handler>";

        return new DomainApprovalHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "DomainApprovalHandler";
    }

}

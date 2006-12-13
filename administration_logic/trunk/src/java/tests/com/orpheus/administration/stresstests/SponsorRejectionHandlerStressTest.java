/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.orpheus.administration.handlers.SponsorRejectionHandler;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link com.orpheus.administration.handlers.SponsorRejectionHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class SponsorRejectionHandlerStressTest extends AbstractHandlerStressTest {
    protected void setUp() throws Exception {
        super.setUp();

        ((MockHttpServletRequest) request).setupAddParameter("sponsorId", "2");
    }

    protected void assertAfterExecute() {
        super.assertAfterExecute();

        assertNull("should return null.", result);
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
        String xml = "<handler type=\"sponsorApproval\">" + "<object-factory-ns>objFactoryNS</object-factory-ns>"
            + "<sponsor-id-request-param>sponsorId</sponsor-id-request-param>"
            + "<fail-result>Failed</fail-result><fail-request-attribute>fail" + "</fail-request-attribute></handler>";

        return new SponsorRejectionHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "SponsorRejectionHandler";
    }

}

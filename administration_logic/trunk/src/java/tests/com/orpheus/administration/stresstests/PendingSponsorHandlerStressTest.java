/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import com.orpheus.administration.entities.SponsorDomain;
import com.orpheus.administration.handlers.PendingSponsorHandler;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Stress test for <code>{@link com.orpheus.administration.handlers.PendingSponsorHandler}</code> class.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class PendingSponsorHandlerStressTest extends AbstractHandlerStressTest {

    protected void assertAfterExecute() {
        super.assertAfterExecute();

        assertNull("should return null.", result);

        assertTrue("atrribute not set.", request.getAttribute("SponsorDomains") instanceof SponsorDomain[]);
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
        // the xml string used for test
        String xml = "<handler type=\"pendingSponsorDomain\">" + "<object-factory-ns>objFactoryNS</object-factory-ns>"
            + "<game-data-jndi-name>" + StressTestHelper.GAME_DATA_JNDI_NAME + "</game-data-jndi-name>"
            + "<sponsor-domain-request-attribute>SponsorDomains</sponsor-domain-request-attribute>"
            + "<fail-result>Failed</fail-result><fail-request-attribute>" + "fail</fail-request-attribute></handler>";
        return new PendingSponsorHandler(StressTestHelper.loadXmlString(xml));
    }

    /**
     * <p>
     * Get the the handler class name to be tested.
     * </p>
     * @return the handler class name
     */
    protected String getHandlerName() {
        return "PendingSponsorHandler";
    }

}

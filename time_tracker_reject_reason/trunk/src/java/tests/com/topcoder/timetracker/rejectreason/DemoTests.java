/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.ejb.RejectEmailManager;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;


/**
 * This class demonstrates the general usages of this component.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class DemoTests extends TestHelper {
    /**
     * Sets up the test environment. We bind DataSource to JNDI context, add necessary environment variables, and load
     * configurations.
     *
     * @throws Exception pass to JUnit.
     */
    protected void setUp() throws Exception {
        loadConfig();
        setUpSessionBeanEnvironment();
    }

    /**
     * Demonstrates how to manage RejectReason entity.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRejectReason() throws Exception {
        // The user
        String username = "topcoder";

        // Create RejectReasonManager
        RejectReasonManager reasonManager = new RejectReasonManager(RejectReasonManager.class.getName());

        // Create RejectReason with audit
        long companyId = 1;
        String description = "something";
        boolean active = true;
        RejectReason rejectReason1 = new RejectReason();
        rejectReason1.setCompanyId(companyId);
        rejectReason1.setDescription(description);
        rejectReason1.setActive(active);

        boolean isAudit = true;
        long rejectReason1Id = reasonManager.createRejectReason(rejectReason1, username, isAudit).getId();

        // Create another RejectReason without audit
        RejectReason rejectReason2 = new RejectReason();
        rejectReason2.setCompanyId(2);
        rejectReason2.setDescription("another_thing");
        rejectReason2.setActive(true);
        isAudit = false;
        reasonManager.createRejectReason(rejectReason2, username, isAudit).getId();

        // Retrieve RejectReason
        RejectReason rejectReason = reasonManager.retrieveRejectReason(rejectReason1Id);

        // Update RejectReason
        rejectReason.setDescription("new_thing");
        isAudit = true;
        reasonManager.updateRejectReason(rejectReason, username, isAudit);

        // Delete RejectReason
        reasonManager.deleteRejectReason(rejectReason1, username, isAudit);
        reasonManager.deleteRejectReason(rejectReason2, username, isAudit);

        // List RejectReason
        reasonManager.listRejectReasons();

        // Search RejectReason
        // Create RejectReasonSearchBuilder
        RejectReasonSearchBuilder rejectReasonSearchBuilder = new RejectReasonSearchBuilder();

        // Simple search
        Filter filter = rejectReasonSearchBuilder.createdByUserFilter(username);
        reasonManager.searchRejectReasons(filter);

        // complex search;
        Filter filter1 = rejectReasonSearchBuilder.createdByUserFilter(username);
        Filter filter2 = rejectReasonSearchBuilder.not(filter1);
        filter = rejectReasonSearchBuilder.and(filter1, filter2);

        // NOTE: this search should return an empty result set
        reasonManager.searchRejectReasons(filter);
    }

    /**
     * Demonstrates how to manage RejectEmail entity.
     *
     * @throws Exception pass to JUnit.
     */
    public void testRejectEmail() throws Exception {
        // The user
        String username = "topcoder";

        // Create RejectEmailManager
        RejectEmailManager emailManager = new RejectEmailManager(RejectEmailManager.class.getName());

        // Create RejectEmail with audit
        long companyId = 1;
        String body = "something";
        RejectEmail rejectEmail1 = new RejectEmail();
        rejectEmail1.setCompanyId(companyId);
        rejectEmail1.setBody(body);

        boolean isAudit = true;
        long rejectEmail1Id = emailManager.createRejectEmail(rejectEmail1, username, isAudit).getId();

        // Create another RejectEmail without audit
        RejectEmail rejectEmail2 = new RejectEmail();
        rejectEmail2.setCompanyId(2);
        rejectEmail2.setBody("another_thing");
        isAudit = false;
        emailManager.createRejectEmail(rejectEmail2, username, isAudit).getId();

        // Retrieve RejectEmail
        RejectEmail rejectEmail = emailManager.retrieveRejectEmail(rejectEmail1Id);

        // Update RejectEmail
        rejectEmail.setBody("new_thing");
        isAudit = true;
        emailManager.updateRejectEmail(rejectEmail, username, isAudit);

        // Delete RejectEmail
        emailManager.deleteRejectEmail(rejectEmail1, username, isAudit);
        emailManager.deleteRejectEmail(rejectEmail2, username, isAudit);

        // List RejectEmail
        emailManager.listRejectEmails();

        // Search RejectEmail
        // Create RejectEmailSearchBuilder
        RejectEmailSearchBuilder rejectEmailSearchBuilder = new RejectEmailSearchBuilder();

        // Simple search
        Filter filter = rejectEmailSearchBuilder.createdByUserFilter(username);
        emailManager.searchRejectEmails(filter);

        // Complex search;
        Filter filter1 = rejectEmailSearchBuilder.createdByUserFilter(username);
        Filter filter2 = rejectEmailSearchBuilder.not(filter1);
        filter = rejectEmailSearchBuilder.and(filter1, filter2);

        // NOTE: this search should return an empty result set
        emailManager.searchRejectEmails(filter);
    }
}

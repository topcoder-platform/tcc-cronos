/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.accuracytests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;

import junit.framework.JUnit4TestAdapter;

import org.apache.struts2.ServletActionContext;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.AcceptRejectGroupInvitationAction;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;

/**
 * <p>
 * Accuracy test for AcceptRejectGroupInvitationAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ AcceptRejectGroupInvitationAction.class, ServletActionContext.class })
public class AcceptRejectGroupInvitationActionAccuracyTest {
    /**
     * Represents the instance of AcceptRejectGroupInvitationAction used in
     * test.
     */
    private AcceptRejectGroupInvitationAction instance;

    /**
     * Sets up for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new AcceptRejectGroupInvitationAction();
    }

    /**
     * Tears down for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
        MockGroupInvitationService.RECORDS.clear();
        MockGroupAuditService.RECORDS.clear();
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AcceptRejectGroupInvitationActionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for AcceptRejectGroupInvitationAction(). The instance
     * should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("Instance should be created.", instance);
    }

    /**
     * <p>
     * Accuracy test for {@link AcceptRejectGroupInvitationAction#execute()}.
     * When the invitation is accepted.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_execute() throws Exception {
        doExecute(true, 1L);
    }

    /**
     * <p>
     * Accuracy test for {@link AcceptRejectGroupInvitationAction#execute()}.
     * When the invitation is rejected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void test_execute2() throws Exception {
        doExecute(false, 2L);
    }

    /**
     * Do the execute test.
     *
     * @param isAccepted
     *            the invitation is accepted or not.
     * @param id
     *            the invitation id.
     *
     * @throws Exception
     *             to JUnit
     */
    private void doExecute(boolean isAccepted, long id) throws Exception {
        instance.setAccepted(isAccepted);
        instance.setInvitationId(id);

        GroupInvitationService giService = new MockGroupInvitationService();
        instance.setGroupInvitationService(giService);

        GroupAuditService auditService = new MockGroupAuditService();
        instance.setAuditService(auditService);

        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress").anyTimes();

        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request).anyTimes();

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(request);

        assertEquals("The returned value is incorrect.", Action.SUCCESS, instance.execute());

        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);

        assertEquals("The GroupInvitation should be updated.", 1,
            MockGroupInvitationService.RECORDS.size());
        assertEquals("The GroupInvitation should be updated.",
            isAccepted ? InvitationStatus.APPROVAL_PENDING : InvitationStatus.REJECTED,
            MockGroupInvitationService.RECORDS.get(id).getStatus());
        assertEquals("The GroupInvitation should be updated.", id,
            MockGroupInvitationService.RECORDS.get(id).getId());
        assertTrue(
            "The GroupInvitation should be updated.",
            Math.abs(System.currentTimeMillis()
                - MockGroupInvitationService.RECORDS.get(id).getAcceptedOrRejectedOn().getTime()) < 1000);
        assertEquals("The audit record should be correct.", 1,
            MockGroupInvitationService.RECORDS.size());
    }
}
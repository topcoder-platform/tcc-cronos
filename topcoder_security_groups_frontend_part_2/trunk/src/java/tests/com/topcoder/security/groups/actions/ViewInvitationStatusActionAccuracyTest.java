/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.JUnit4TestAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.ViewInvitationStatusAction;
import com.topcoder.security.groups.actions.accuracytests.MockGroupAuditService;
import com.topcoder.security.groups.actions.accuracytests.MockGroupInvitationService;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * Accuracy test for ViewInvitationStatusAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewInvitationStatusActionAccuracyTest {
    /** Represents the instance used to test again. */
    private ViewInvitationStatusAction instance;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        instance = new ViewInvitationStatusAction();
        instance.setPage(7);
        instance.setPageSize(10);
        instance.getCriteria().setGroupName("g");
        instance.getCriteria().setPermissions(new ArrayList<GroupPermissionType>());
    }

    /**
     * Tears down test environment.
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
        return new JUnit4TestAdapter(ViewInvitationStatusActionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for ViewInvitationStatusAction(). The instance should be
     * created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("The instance should be created.", instance);
    }

    /**
     * <p>
     * Accuracy test for validate(). When group name is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate1() throws Exception {
        instance.getCriteria().setGroupName("1234567890123456789012345678901234567890123456");
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.groupName"));
    }

    /**
     * <p>
     * Accuracy test for validate(). When group name is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate2() throws Exception {
        instance.getCriteria().setGroupName(" ");
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.groupName"));
    }

    /**
     * <p>
     * Accuracy test for validate(). When Permissions contains null.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate3() throws Exception {
        instance.getCriteria().getPermissions().add(null);
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.permissions"));
    }

    /**
     * <p>
     * Accuracy test for validate(). When Permissions contains duplicated.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate4() throws Exception {
        instance.getCriteria().getPermissions().add(GroupPermissionType.FULL);
        instance.getCriteria().getPermissions().add(GroupPermissionType.FULL);
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.permissions"));
    }

    /**
     * <p>
     * Accuracy test for validate(). When criteria.acceptedDateFrom and
     * criteria.acceptedDateTo is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate5() throws Exception {
        instance.getCriteria().setAcceptedDateFrom(new Date());
        instance.getCriteria().setAcceptedDateTo(new Date(0));
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.acceptedDate"));
    }

    /**
     * <p>
     * Accuracy test for validate(). When criteria.sendDateFrom and
     * criteria.sendDateTo is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate6() throws Exception {
        instance.getCriteria().setSentDateFrom(new Date());
        instance.getCriteria().setSentDateTo(new Date(0));
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
        assertTrue("validate is incorrect.",
            instance.getFieldErrors().containsKey("criteria.sendDate"));
    }

    /**
     * <p>
     * Accuracy test for validate(). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate7() throws Exception {
        instance.setPageSize(-1);
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for validate(). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testValidate8() throws Exception {
        instance.setPage(-1);
        instance.validate();
        assertEquals("validate is incorrect.", 1, instance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewInvitationStatusAction#execute()}.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        instance.setCriteria(criteria);

        GroupInvitationService groupInvitationService = new MockGroupInvitationService();
        instance.setGroupInvitationService(groupInvitationService);

        assertEquals("The returned value is incorrect.", Action.SUCCESS, instance.execute());

        PagedResult<GroupInvitation> pr = instance.getInvitations();
        assertEquals("The returned value is incorrect.", 7, pr.getPage());
        assertEquals("The returned value is incorrect.", 100, pr.getTotal());
    }
}
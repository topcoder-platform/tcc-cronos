/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;

import junit.framework.JUnit4TestAdapter;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.InvitationStatus;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * Unit tests for the {@link ViewInvitationStatusAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ViewInvitationStatusActionTest {
    /** Represents the instance used to test again. */
    private ViewInvitationStatusAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new ViewInvitationStatusAction();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ViewInvitationStatusActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link ViewInvitationStatusAction#ViewInvitationStatusAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testViewInvitationStatusAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>ViewInvitationStatusAction</code>
     * subclasses <code>GroupInvitationSearchBaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "ViewInvitationStatusAction does not subclass GroupInvitationSearchBaseAction.",
                ViewInvitationStatusAction.class.getSuperclass() == GroupInvitationSearchBaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ViewInvitationStatusAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate1() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setGroupName("test");
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewInvitationStatusAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate2() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setPermissions(Arrays.asList(GroupPermissionType.FULL,
                GroupPermissionType.FULL));
        criteria.setSentDateFrom(new Date(0));
        criteria.setSentDateTo(new Date(1));
        criteria.setAcceptedDateFrom(new Date(0));
        criteria.setAcceptedDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewInvitationStatusAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate3() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setPermissions(Arrays.asList(GroupPermissionType.FULL,
                GroupPermissionType.FULL));
        criteria.setSentDateFrom(new Date(2));
        criteria.setSentDateTo(new Date(1));
        criteria.setAcceptedDateFrom(new Date(2));
        criteria.setAcceptedDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 3", 3, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewInvitationStatusAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate4() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setGroupName(" ");
        criteria.setPermissions(Arrays.asList(GroupPermissionType.FULL));
        criteria.setSentDateFrom(new Date(2));
        criteria.setSentDateTo(new Date(1));
        criteria.setAcceptedDateFrom(new Date(2));
        criteria.setAcceptedDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 3", 3, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewInvitationStatusAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate5() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setGroupName(" ");
        criteria.setPermissions(Arrays.asList(GroupPermissionType.FULL));
        criteria.setSentDateFrom(null);
        criteria.setSentDateTo(new Date(1));
        criteria.setAcceptedDateFrom(null);
        criteria.setAcceptedDateTo(new Date(1));
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link ViewInvitationStatusAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        testInstance.setCriteria(criteria);

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);
        PagedResult<GroupInvitation> pagedResult = new PagedResult<GroupInvitation>();
        Capture<Long> id = new Capture<Long>();
        Capture<Integer> page = new Capture<Integer>();
        EasyMock.expect(
                giService.search(EasyMock
                        .capture(new Capture<InvitationSearchCriteria>()),
                        EasyMock.capture(id), EasyMock.capture(page), EasyMock
                                .capture(page))).andReturn(pagedResult)
                .anyTimes();

        testInstance.setGroupInvitationService(giService);

        EasyMock.replay(giService);
        testInstance.execute();
        EasyMock.verify(giService);
    }

    /**
     * <p>
     * Failure test for {@link ViewInvitationStatusAction#execute()}.
     * </p>
     * <p>
     * Fail to do the search, SecurityGroupsActionException will throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail1() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        testInstance.setCriteria(criteria);

        GroupInvitationService giService = EasyMock
                .createNiceMock(GroupInvitationService.class);
        GroupInvitation invitation = new GroupInvitation();
        InvitationStatus status = InvitationStatus.APPROVAL_ACCEPTED;
        invitation.setStatus(status);

        Capture<Long> id = new Capture<Long>();
        Capture<Integer> page = new Capture<Integer>();
        EasyMock.expect(
                giService.search(EasyMock
                        .capture(new Capture<InvitationSearchCriteria>()),
                        EasyMock.capture(id), EasyMock.capture(page), EasyMock
                                .capture(page))).andThrow(
                new SecurityGroupException("test"));

        testInstance.setGroupInvitationService(giService);

        EasyMock.replay(giService);
        testInstance.execute();
        EasyMock.verify(giService);
    }

}
/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.GroupAuditService;

import junit.framework.JUnit4TestAdapter;

/**
 * <p>
 * Unit tests for the {@link HelperUtility}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { HelperUtility.class, ServletActionContext.class })
public class HelperUtilityTest {
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(HelperUtilityTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link HelperUtilityTest#audit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testAudit() throws Exception {
        PowerMock.mockStatic(ServletActionContext.class);
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        EasyMock.expect(request.getRemoteAddr()).andReturn("remoteAddress")
                .anyTimes();
        EasyMock.expect(ServletActionContext.getRequest()).andReturn(request)
                .anyTimes();

        GroupAuditService auditService = EasyMock
                .createNiceMock(GroupAuditService.class);
        Capture<GroupAuditRecord> g = new Capture<GroupAuditRecord>();
        EasyMock.expect(auditService.add(EasyMock.capture(g))).andReturn(1L);

        PowerMock.replay(ServletActionContext.class);
        EasyMock.replay(request);
        EasyMock.replay(auditService);

        HelperUtility.audit(auditService, null, null);
        EasyMock.verify(auditService);
        PowerMock.verify(ServletActionContext.class);
        EasyMock.verify(request);
    }
}

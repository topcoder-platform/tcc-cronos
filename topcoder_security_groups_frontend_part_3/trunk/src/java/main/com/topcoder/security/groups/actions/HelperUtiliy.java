/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * Helper class for this component.
 * </p>
 *
 * @author progloco
 * @version 1.0
 */
final class HelperUtiliy {

    /**
     * Default empty constructor.
     */
    private HelperUtiliy() {

    }

    /**
     * Check the list.
     *
     * @param list to check.
     * @param name of list
     * @throws SecurityGroupsActionException if fail to pass the check.
     */
    static void checkSingleElementList(List<?> list, String name)
        throws SecurityGroupsActionException {
        ValidationUtility.checkNotNull(list, name,
                SecurityGroupsActionException.class);
        if (list.size() != 1) {
            throw new SecurityGroupsActionException(name
                    + " list should only contain a single element.");
        }
        ValidationUtility.checkNotNull(list.get(0), "element in " + name,
                SecurityGroupsActionException.class);
    }

    /**
     * Perform the audit.
     *
     * @param auditService the audit service.
     * @param previousValue the previous value.
     * @param newValue the new value.
     * @throws SecurityGroupException if fail to perform the audit.
     */
    static void audit(GroupAuditService auditService, String previousValue,
            String newValue) throws SecurityGroupException {
        // Create and populate audit record
        TCSubject subject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        ValidationUtility.checkNotNull(subject, "subject",
                SecurityGroupException.class);
        ValidationUtility.checkNotNull(subject.getPrincipals(),
                "subject.principals", SecurityGroupException.class);
        ValidationUtility.checkNotEmpty(subject.getPrincipals(),
                "subject.principals", SecurityGroupException.class);
        String handle = ((TCPrincipal) subject.getPrincipals().iterator()
                .next()).getName();
        HttpServletRequest httpServletRequest = ServletActionContext
                .getRequest();
        ValidationUtility.checkNotNull(httpServletRequest,
                "httpServletRequest", SecurityGroupException.class);
        String ipAddress = httpServletRequest.getRemoteAddr();
        GroupAuditRecord auditRecord = new GroupAuditRecord();
        auditRecord.setDate(new Date());
        auditRecord.setHandle(handle);
        auditRecord.setIpAddress(ipAddress);
        auditRecord.setPreviousValue(previousValue);
        auditRecord.setNewValue(newValue);
        // Add audit record to persistence
        auditService.add(auditRecord);
    }
}

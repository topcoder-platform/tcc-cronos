/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.Date;

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
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class HelperUtility {

    /**
     * Default empty constructor.
     */
    private HelperUtility() {

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
        try {
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
            // Add audit record to persistence.
            auditService.add(auditRecord);
        } catch (ClassCastException e) {
            throw new SecurityGroupException("Fail to perform the audit.", e);
        }
    }
}

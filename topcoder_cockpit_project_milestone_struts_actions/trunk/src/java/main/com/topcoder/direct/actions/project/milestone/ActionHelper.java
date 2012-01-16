/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.actions.project.milestone;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.direct.services.project.milestone.AuthorizationException;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>
 * This class defines helper methods used by the component classes.
 * </p>
 *
 * @author fish_ship
 * @version 1.0
 */
final class ActionHelper {
    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private ActionHelper() {
        // Does nothing
    }

    /**
     * <p>
     * Log the entrance information.
     * </p>
     *
     * @param logger
     *            the logger to perform log
     * @param signature
     *            the signature of method
     *
     * @return the current date.
     */
    static Date logEntrance(Logger logger, String signature) {
        Date date = new Date();
        StringBuilder sb = new StringBuilder("Enter into ");
        sb.append(signature).append(" method, the timeStamp is ").append(date).append(".");
        logger.debug(sb.toString());
        return date;
    }

    /**
     * <p>
     * Log the exit information.
     * </p>
     *
     * @param logger
     *            the logger to perform log
     * @param signature
     *            the signature of method
     * @param startDate
     *            the time of entering into this method
     */
    static void logExit(Logger logger, String signature, Date startDate) {
        Date date = new Date();
        StringBuilder sb = new StringBuilder("Exit ");
        sb.append(signature).append(" method, the timeStamp is ").append(date).append(", the duration time is ")
                .append(date.getTime() - startDate.getTime()).append(".");
        logger.debug(sb.toString());
    }

    /**
     * <p>
     * Check whether the list contains duplicated element.
     * </p>
     *
     * @param list
     *            the list to check
     * @param name
     *            the name of the list
     * @param <T>
     *            the type of the value.
     *
     * @throws IllegalArgumentException
     *             if the list contains duplicated element.
     */
    static <T> void checkDuplicated(List<T> list, String name) {
        if (list != null) {
            Set<T> set = new HashSet<T>();
            for (T t : list) {
                if (set.contains(t)) {
                    StringBuilder sb = new StringBuilder("the ");
                    sb.append(name).append(" can not contain duplicated element");
                    throw new IllegalArgumentException(sb.toString());
                }
                set.add(t);
            }
        }
    }

    /**
     * <p>
     * Check the milestones and write permission.
     * </p>
     *
     * @param milestones
     *            the milestones to check
     * @param tCSubject
     *            the current user
     * @param action
     *            the action to check
     *
     * @throws IllegalArgumentException
     *             if the milestones is null or empty, contains null element, milestone.getName() and
     *             milestone.getDescription() is null or empty, milestone.getDueDate() and milestone.getStatus() is
     *             null, milestone.getOwners() can not be empty, can not contain null element,
     *             milestone.getOwners()[i].getName() is null or empty.
     * @throws AuthorizationException
     *             if the current user has not write permission to the given project or fail to get permission
     */
    static void checkMilestones(List<Milestone> milestones, TCSubject tCSubject, BaseDirectStrutsAction action)
        throws AuthorizationException {
        // check the milestones
        ParameterCheckUtility.checkNotNullNorEmpty(milestones, "milestones");
        ParameterCheckUtility.checkNotNullElements(milestones, "milestones");
        // check each milestone in milestones
        for (Milestone milestone : milestones) {
            ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(milestone.getName(), "milestone.getName()");
            ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(milestone.getDescription(),
                    "milestone.getDescription()");
            ParameterCheckUtility.checkNotNull(milestone.getDueDate(), "milestone.getDueDate()");
            ParameterCheckUtility.checkNotNull(milestone.getStatus(), "milestone.getStatus()");
            // check the owners
            ParameterCheckUtility.checkNotEmpty(milestone.getOwners(), "milestone.getOwners()");
            ParameterCheckUtility.checkNotNullElements(milestone.getOwners(), "milestone.getOwners()");
            if (milestone.getOwners() != null) {
                // check each owner in owners
                for (ResponsiblePerson person : milestone.getOwners()) {
                    ParameterCheckUtility.checkNotNullNorEmptyAfterTrimming(person.getName(), "person.getName()");
                }
            }
            // validate the write permission.
            validatePermission(tCSubject, milestone.getProjectId(), action);
        }
    }

    /**
     * <p>
     * Validate the write permission.
     * </p>
     *
     * @param tCSubject
     *            the current user
     * @param projectId
     *            the id of project
     * @param action
     *            the action to check
     * @throws AuthorizationException
     *             if the current user has not write permission to the given project or fail to get permission
     */
    static void validatePermission(TCSubject tCSubject, long projectId, BaseDirectStrutsAction action)
        throws AuthorizationException {
        try {
            // validate the write permission.
            if (!DirectUtils.hasProjectWritePermission(action, tCSubject, projectId)) {
                throw new AuthorizationException("the current user has not write permission to the given project");
            }
        } catch (PermissionServiceException e) {
            throw new AuthorizationException("fail to get permission", e);
        }
    }
}

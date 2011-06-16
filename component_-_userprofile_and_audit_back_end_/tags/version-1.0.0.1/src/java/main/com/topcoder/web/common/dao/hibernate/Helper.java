/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.dao.hibernate;

import org.hibernate.Session;

import com.topcoder.commons.utils.ParameterCheckUtility;

/**
 * <p>
 * A Helper class used by this component.
 * </p>
 * @author kalc
 * @version 1.0
 */
public final class Helper {

    /**
     * <p>
     * A private constructor to prevent instantiation of this class.
     * </p>
     */
    private Helper() {
        // to prevent instantiation
    }

    /**
     * <p>
     * A private helper method to check the session parameter for null.
     * </p>
     * @param session
     *            the Session to be checked
     * @return the Session object
     */
    public static Session checkParam(Session session) {
        ParameterCheckUtility.checkNotNull(session, "session");
        return session;
    }

}

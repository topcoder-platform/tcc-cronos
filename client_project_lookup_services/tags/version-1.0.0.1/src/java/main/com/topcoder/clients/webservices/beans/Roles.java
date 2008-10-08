/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

/**
 * <p>
 * This interface is a holder for all of the available roles. Defines two String variables containing the role
 * names: USER and ADMIN.
 * </p>
 * <p>
 * Thread safety: constant interface is thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public interface Roles {
    /**
     * <p>
     * This static final String field represents the 'USER' role of the Roles interface. It is initialized to
     * a default value: "User" String during runtime. Accessed directly, it is public. Can not be changed. It
     * is constant.
     * </p>
     */
    public static final String USER = "User";

    /**
     * <p>
     * This static final String field represents the 'ADMIN' role of the Roles interface. It is initialized to
     * a default value: "Admin" String during runtime. Accessed directly, it is public. Can not be changed. It
     * is constant.
     * </p>
     */
    public static final String ADMIN = "Admin";
}

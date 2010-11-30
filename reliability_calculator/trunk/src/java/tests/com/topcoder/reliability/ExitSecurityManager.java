/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability;

import java.security.Permission;

/**
 * <p>
 * An implementation of <code>SecurityManager</code>. Used to test System.exit.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
public class ExitSecurityManager extends SecurityManager {
    /**
     * <p>
     * Checks the permission.
     * </p>
     *
     * @param permission
     *            the permission.
     *
     * @throws SecurityException
     *             if System.exit called.
     */
    public void checkPermission(Permission permission) {
        if ("exitVM".equals(permission.getName())) {
            throw new SecurityException("System.exit called.");
        }
    }

    /**
     * <p>
     * Checks the permission.
     * </p>
     *
     * @param perm
     *            the permission.
     * @param context
     *            the context.
     *
     * @throws SecurityException
     *             if System.exit called.
     */
    public void checkPermission(Permission perm, Object context) {
        checkPermission(perm);
    }

    /**
     * <p>
     * Check Exit.
     * </p>
     *
     * @param status
     *            the exit code.
     *
     * @throws SecurityException
     *             if System.exit called.
     */
    public void checkExit(int status) {
        throw new SecurityException("System.exit called.");
    }

}
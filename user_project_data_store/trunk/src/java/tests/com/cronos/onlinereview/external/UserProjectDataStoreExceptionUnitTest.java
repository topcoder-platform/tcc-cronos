/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * User Project Data Store 1.0
 */
package com.cronos.onlinereview.external;

import junit.framework.TestCase;

/**
 * <p>Tests the UserProjectDataStoreException class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UserProjectDataStoreExceptionUnitTest extends TestCase {

    /**
     * <p>The Default Exception Message.</p>
     */
    private final String defaultExceptionMessage = "DefaultExceptionMessage";

    /**
     * <p>The Default Throwable Message.</p>
     */
    private final String defaultThrowableMessage = "DefaultThrowableMessage";

    /**
     * <p>An UserProjectDataStoreException instance for testing.</p>
     */
    private UserProjectDataStoreException defaultException = null;

    /**
     * <p>Initialization.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        defaultException = new UserProjectDataStoreException();
    }

    /**
     * <p>Set defaultException to null.</p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        defaultException = null;
    }

    /**
     * <p>Tests the ctor().</p>
     * <p>The UserProjectDataStoreException instance should be created successfully.</p>
     */
    public void testCtor() {

        assertNotNull("UserProjectDataStoreException should be accurately created.", defaultException);
        assertTrue("defaultException should be instance of UserProjectDataStoreException.",
                defaultException instanceof UserProjectDataStoreException);
    }

    /**
     * <p>Tests the ctor(String).</p>
     * <p>The UserProjectDataStoreException instance should be created successfully.</p>
     */
    public void testCtor_String() {

        defaultException = new UserProjectDataStoreException(defaultExceptionMessage);

        assertNotNull("UserProjectDataStoreException should be accurately created.", defaultException);
        assertTrue("defaultException should be instance of UserProjectDataStoreException.",
                defaultException instanceof UserProjectDataStoreException);
        assertTrue("UserProjectDataStoreException should be accurately created with the same Exception "
                + "message.",
                defaultException.getMessage().indexOf(defaultExceptionMessage) >= 0);
    }

    /**
     * <p>Tests the ctor(String, Throwable).</p>
     * <p>The UserProjectDataStoreException instance should be created successfully.</p>
     */
    public void testCtor_StringThrowable() {

        defaultException = new UserProjectDataStoreException(defaultExceptionMessage,
                new Throwable(defaultThrowableMessage));

        assertNotNull("UserProjectDataStoreException should be accurately created.", defaultException);
        assertTrue("defaultException should be instance of UserProjectDataStoreException.",
                defaultException instanceof UserProjectDataStoreException);
        assertTrue("UserProjectDataStoreException should be accurately created with the same Exception "
                + "message.",
                defaultException.getMessage().indexOf(defaultExceptionMessage) >= 0);
        assertTrue("UserProjectDataStoreException should be accurately created with the same Throwable "
                + "message.",
                defaultException.getMessage().indexOf(defaultThrowableMessage) >= 0);
    }
}

/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.dbhandler;

import junit.framework.TestCase;


/**
 * This class contains the unit tests for {@link DBHandlerNotFoundException}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DBHandlerNotFoundExceptionTest extends TestCase {

    /**
     * This method tests the constructors of {@link DBHandlerNotFoundException} using valid values and checks the state
     * of the instances created is as expected.
     */
    public void testConstructors() {
        final String message = "dummy";
        final String causeMessge = "xy";
        final RuntimeException cause = new RuntimeException(causeMessge);
        //this message is generated automatically by BaseException constructor from cause parameter
        final String baseExceptionGeneratedCauseMessage = ", caused by " + causeMessge;
        DBHandlerNotFoundException dbHandlerNotFoundException;

        //design does specify null as valid message and as valid cause
        dbHandlerNotFoundException = new DBHandlerNotFoundException(null);
        assertNull(dbHandlerNotFoundException.getMessage());
        assertNull(dbHandlerNotFoundException.getCause());

        dbHandlerNotFoundException = new DBHandlerNotFoundException(message);
        assertEquals(message, dbHandlerNotFoundException.getMessage());
        assertNull(dbHandlerNotFoundException.getCause());

        dbHandlerNotFoundException = new DBHandlerNotFoundException(null, null);
        assertNull(dbHandlerNotFoundException.getMessage());
        assertNull(dbHandlerNotFoundException.getCause());

        dbHandlerNotFoundException = new DBHandlerNotFoundException(message, null);
        assertEquals(message, dbHandlerNotFoundException.getMessage());
        assertNull(dbHandlerNotFoundException.getCause());

        dbHandlerNotFoundException = new DBHandlerNotFoundException(null, cause);
        assertEquals("null" + baseExceptionGeneratedCauseMessage, dbHandlerNotFoundException.getMessage());
        assertEquals(cause, dbHandlerNotFoundException.getCause());

        dbHandlerNotFoundException = new DBHandlerNotFoundException(message, cause);
        assertEquals(message + baseExceptionGeneratedCauseMessage, dbHandlerNotFoundException.getMessage());
        assertEquals(cause, dbHandlerNotFoundException.getCause());
    }

}

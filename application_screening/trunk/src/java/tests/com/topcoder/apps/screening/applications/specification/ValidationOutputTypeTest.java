/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ValidationOutputTypeTest.java
 */
package com.topcoder.apps.screening.applications.specification;

import com.topcoder.util.collection.typesafeenum.Enum;
import junit.framework.TestCase;

import java.util.List;

/**
 * <p>
 * Unit tests for <code>ValidationOutputType</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ValidationOutputTypeTest extends TestCase {

    /**
     * <p>
     * Tests ValidationOutputType class.
     * </p>
     */
    public void testDatabaseDataEntitlementType() {
        List values = Enum.getEnumList(ValidationOutputType.class);

        //there must be 2 value
        assertEquals("Must be 2 values.", 2, values.size());

        //must contain these values
        assertTrue("Must contain this value.", values.contains(ValidationOutputType.ERROR));
        assertTrue("Must contain this value.", values.contains(ValidationOutputType.REPORT));

        assertEquals("Must be equal.", "error", ValidationOutputType.ERROR.getType());
        assertEquals("Must be equal.", "report", ValidationOutputType.REPORT.getType());
    }
}
/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test for TaskTypeCriteria class.
 * </p>
 *
 * @author arylio
 * @version 2.0
 */
public class V2TaskTypeCriteriaUnitTest extends TestCase {
    /**
     * <p>
     * Tests the static final field COMPANY_ID.
     * </p>
     */
    public void testCOMPANY_ID() {
        assertEquals("The static final field should be correct.", "comp_task_type.company_id",
                TaskTypeCriteria.DESCRIPTION.getName());
    }

    /**
     * <p>
     * Tests the static final field DESCRIPTION.
     * </p>
     */
    public void testDESCRIPTION() {
        assertEquals("The static final field should be correct.", "task_type.description",
                TaskTypeCriteria.DESCRIPTION.getName());
    }

    /**
     * <p>
     * Tests the static final field ACTIVE.
     * </p>
     */
    public void testACTIVE() {
        assertEquals("The static final field should be correct.", "task_type.active",
                TaskTypeCriteria.ACTIVE.getName());
    }

    /**
     * <p>
     * Tests the static final field CREATION_DATE.
     * </p>
     */
    public void testCREATION_DATE() {
        assertEquals("The static final field should be correct.", "task_type.creation_date",
                TaskTypeCriteria.CREATION_DATE.getName());
    }

    /**
     * <p>
     * Tests the static final field CREATION_DATE.
     * </p>
     */
    public void testMODIFICATION_DATE() {
        assertEquals("The static final field should be correct.", "task_type.modification_date",
                TaskTypeCriteria.MODIFICATION_DATE.getName());
    }

    /**
     * <p>
     * Tests the static final field CREATION_USER.
     * </p>
     */
    public void testCREATION_USER() {
        assertEquals("The static final field should be correct.", "task_type.creation_user",
                TaskTypeCriteria.CREATION_USER.getName());
    }

    /**
     * <p>
     * Tests the static final field MODIFICATION_USER.
     * </p>
     */
    public void testMODIFICATION_USER() {
        assertEquals("The static final field should be correct.", "task_type.modification_user",
                TaskTypeCriteria.MODIFICATION_USER.getName());
    }
}

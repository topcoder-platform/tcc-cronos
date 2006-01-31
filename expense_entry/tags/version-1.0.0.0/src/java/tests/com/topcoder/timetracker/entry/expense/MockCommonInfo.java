/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

/**
 * <p>
 * Defines a mock common information class. It introduces no additional properties.
 * </p>
 *
 * @author visualage
 * @version 1.0
 */
public class MockCommonInfo extends CommonInfo {
    /**
     * <p>
     * Creates a new instance of <code>MockCommonInfo</code> class. The ID is -1.
     * </p>
     */
    public MockCommonInfo() {
    }

    /**
     * <p>
     * Creates a new instance of <code>MockCommonInfo</code> class. The ID is the given ID.
     * </p>
     *
     * @param id the ID of this instance.
     *
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public MockCommonInfo(int id) {
        super(id);
    }
}







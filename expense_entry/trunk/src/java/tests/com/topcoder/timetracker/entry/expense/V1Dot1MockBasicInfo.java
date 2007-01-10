/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

/**
 * <p>
 * Defines a mock common information class. It introduces no additional properties.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1MockBasicInfo extends BasicInfo {
    /**
     * <p>
     * Creates a new instance of <code>V1Dot1MockBasicInfo</code> class. The ID is -1.
     * </p>
     */
    public V1Dot1MockBasicInfo() {
    }

    /**
     * <p>
     * Creates a new instance of <code>V1Dot1MockBasicInfo</code> class. The ID is the given ID.
     * </p>
     *
     * @param id the ID of this instance.
     *
     * @throws IllegalArgumentException if <code>id</code> is -1.
     */
    public V1Dot1MockBasicInfo(int id) {
        super(id);
    }
}

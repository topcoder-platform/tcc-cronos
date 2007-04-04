/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

/**
 * <p>
 * This class gives some pre-defined audit type.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is thread safe by being immutable.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface AuditType {
    /** The type for insert. */
    static int INSERT = 1;

    /** The type of update. */
    static int UPDATE = 2;

    /** The type of delete. */
    static int DELETE = 3;
}

/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * A convenient enumeration of currently supported phase statuses (as defined in DB phase_status_lu
 * table). PhaseStatus is an Enum and thus poses no thread-safety issues.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PhaseStatusEnum extends Enum {

    /**
     * <p>
     * Represents the scheduled status for a phase as mapped to the data base (uses id of 1 as
     * defined in phase_status_lu table).
     * </p>
     */
    public static final PhaseStatusEnum SCHEDULED = new PhaseStatusEnum("Scheduled", 1);

    /**
     * <p>
     * Represents the open status for a phase as mapped to the data base (uses id of 2 as defined in
     * phase_status_lu table).
     * </p>
     */
    public static final PhaseStatusEnum OPEN = new PhaseStatusEnum("open", 2);

    /**
     * <p>
     * Represents the closed status for a phase as mapped to the data base (uses id of 3 as defined
     * in phase_status_lu table).
     * </p>
     */
    public static final PhaseStatusEnum CLOSED = new PhaseStatusEnum("closed", 3);

    /**
     * <p>
     * Represents the name of the phase status for this instance. It will contain "scheduled",
     * "open", or "closed" only, as currently defined in phase_status_lu table.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Represents the id of the record in teh data store for teh specific phase. It will contain 1,
     * 2, or 3 as currently defined in phase_status_lu table..
     * </p>
     */
    private int id;

    /**
     * <p>
     * Private constructor to prevent instantiation 'from the outside' When called, it will assign
     * the name to this.name and id to this.id.
     * </p>
     * <p>
     * Exception Handling
     * @throws IllegalArgumentException if name is null or empty (should never happen though)
     *             </p>
     * @param name name of the phase status
     * @param id store id of the phase (i.e. PK in teh lookup table)
     */
    private PhaseStatusEnum(String name, int id) {
        // your code here
        this.name = name;
        this.id = id;
    }

    /**
     * <p>
     * Getter for the name of this phase status. .
     * </p>
     * @return name
     */
    public String getName() {
        // your code here
        return this.name;
    }

    /**
     * <p>
     * Getter for the id of this phase status. .
     * </p>
     * @return -
     */
    public int getId() {
        // your code here
        return this.id;
    }
}

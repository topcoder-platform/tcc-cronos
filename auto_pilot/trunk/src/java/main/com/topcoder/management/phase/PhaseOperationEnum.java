/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * A convenient enumeration of phase operations as defined by the Phase Manager API which currently
 * allows operations of "start", "end", and "cancel" This is provided as a utility to the user when
 * they need to identify the phase operation when registering a phase handler with a manager. This
 * is used when creating HandlerRegistryInfo instances for handler registrations (used as keys to id
 * handlers), which need to know what operation the handler will handle (as well as which phase
 * status it will deal with ¨C which is covered by PhaseStatusEnum) PhaseOperation is an Enum and
 * thus poses no thread-safety issues.
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class PhaseOperationEnum extends Enum {

    /**
     * <p>
     * Represents the start operation for a phase. This is used to map PhaseHandlers to specific
     * operations.
     * </p>
     */
    public static final PhaseOperationEnum START = new PhaseOperationEnum("start");

    /**
     * <p>
     * Represents the end operation for a phase. This is used to map PhaseHandlers to specific
     * operations.
     * </p>
     */
    public static final PhaseOperationEnum END = new PhaseOperationEnum("end");

    /**
     * <p>
     * Represents the cancel operation for a phase. This is used to map PhaseHandlers to specific
     * operations.
     * </p>
     */
    public static final PhaseOperationEnum CANCEL = new PhaseOperationEnum("cancel");

    /**
     * <p>
     * Represents the name of the operation for this instance. It will contain "start", "end", or
     * "cancel" only.
     * </p>
     */
    private final String name;

    /**
     * <p>
     * Privite constructor to prevent instntiation 'from teh outside' When called will assign the
     * name to this.name.
     * </p>
     * <p>
     * @throws IllegalArgumentException if name is null or empty (should never
     * happen though)
     * </p>
     * @param name name of operation.
     */
    private PhaseOperationEnum(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Getter for the name of this operation. .
     * </p>
     * @return name of operation.
     */
    public String getName() {
        // your code here
        return null;
    }
}

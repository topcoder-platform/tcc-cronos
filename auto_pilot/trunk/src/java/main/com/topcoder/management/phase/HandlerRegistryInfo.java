/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.phase;

import com.topcoder.project.phases.PhaseType;

/**
 * <p>
 * Optional pluggable phase handling mechanism can be configured per phase type/operation. The
 * handler will provide the decision of whether the start, end or cancel operations can be performed
 * as well as extra logic when the phase is starting, ending or canceling. Notice that the status
 * and timestamp persistence is still handled by the component. When registering such a handler we
 * need a composite key based on both type and operation. Any PhaseManager implementation that needs
 * ti have the ability register each such handler in a unique manner based on both type and
 * operation. This class represents a composite key used to represent a handler in the map of
 * handlers within any PhaseManager implementation. HandlerRegistryInfo is thread safe since it is
 * immutable
 * </p>
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class HandlerRegistryInfo {

    /**
     * <p>
     * Represents the PhaseType portion of this composite key. initializaed through constructor and
     * never changed after that. - Can not be null.
     * </p>
     */
    private final PhaseType type;

    /**
     * <p>
     * Represents the PhaseOperation portion of this composite key. initializaed through constructor
     * and never changed after that. - Can not be null.
     * </p>
     */
    private final PhaseOperationEnum operation;

    /**
     * <p>
     * Simple constructor which takes the two parameters and initailizes them.
     * </p>
     * <p>
     * @throws IllegalArgumentException if any argument is null.
     * </p>
     * @param type phase type to initialize with
     * @param operation phase operation (START, END< CALNCEL) to initialize with
     */
    public HandlerRegistryInfo(PhaseType type, PhaseOperationEnum operation) {
        this.type = type;
        this.operation = operation;
        // your code here
    }

    /**
     * <p>
     * A simple getter for type. - Simply return this.type;
     * </p>
     * @return Phase type
     */
    public PhaseType getType() {
        // your code here
        return null;
    }

    /**
     * <p>
     * A simple getter for operation. - Simply return this.operation;
     * </p>
     * @return operation under which the handler was registered
     */
    public com.topcoder.management.phase.PhaseOperationEnum getOperation() {
        // your code here
        return null;
    }

    /**
     * <p>
     * Tests if another instance of this object is actually equal to another.
     * </p>
     * <p>
     * Implementation details - ensure that the object is instaceof HandlerRegistryInfo, if not
     * return false - if type.getId() is the same and their corresponding
     * operation.getName().equals(the other) we do consider them equal
     * </p>
     * <p>
     * Exception Handling None.
     * </p>
     * @param obj object to compare
     * @return true if the two are the same; false otherwise.
     */
    public boolean equals(Object obj) {
        // your code here
        return true;
    }

    /**
     * <p>
     * Calculates a hashcode for this instance..
     * </p>
     * <p>
     * Implementation details return type.hashcode() ^ operation.getName().hashcode();
     * </p>
     * <p>
     * Exception Handling None.
     * </p>
     * @return hashcode
     */
    public int hashCode() {
        // your code here
        return 0;
    }
}

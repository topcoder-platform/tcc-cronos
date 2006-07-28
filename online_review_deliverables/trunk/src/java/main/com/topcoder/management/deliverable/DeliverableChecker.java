package com.topcoder.management.deliverable;

import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;

/**
 * <p>
 * The DeliverableChecker interface is responsible for deciding if a deliverable is complete. If so, it sets the
 * completion date of the deliverable. Only a single method exists in this interface. No concrete implementation of
 * this interface is required in this component.
 * </p>
 *
 * <p>
 * Implementations of this interface are not required to be thread safe.
 * </p>
 *
 *
 */
public interface DeliverableChecker {
    /**
     * check: Checks the given deliverable to see if it is complete. If it is complete, the checker marks the
     * deliverable as complete by calling the setCompletionDate method.
     *
     *
     * @param deliverable The deliverable to check
     * @throws IllegalArgumentException If deliverable is null
     */
    public void check(Deliverable deliverable) throws DeliverableCheckingException;
}

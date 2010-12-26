/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker;

import java.util.List;

/**
 * <p>
 * This interface represents a late deliverables retriever. It provides a single method
 * for retrieving a full list of late deliverables. This interface extends Configurable
 * interface to support configuration via Configuration API component.
 * </p>
 * <p>
 * Thread Safety: Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
public interface LateDeliverablesRetriever extends Configurable {
    /**
     * Retrieves information about all late deliverables. Returns an empty list if no
     * deliverables are late.
     *
     * @return the list with information about all late deliverables (not
     *         <code>null</code>, doesn't contain <code>null</code>).
     * @throws LateDeliverablesRetrievalException
     *             if some error occurred when retrieving a list of late deliverables.
     */
    public List<LateDeliverable> retrieve() throws LateDeliverablesRetrievalException;
}

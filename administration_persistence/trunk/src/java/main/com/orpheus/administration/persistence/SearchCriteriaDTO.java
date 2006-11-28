/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import java.io.Serializable;

/**
 * <p>Interface specifying the contract for a search criteria class to be used as the data transport
 * object. Implementations will be simple serializable transfer beans. They will transport the data between the
 * client and the DAO layers. It is assembled at both of those ends. The EJB layer does not operate on
 * it.
 *
 * <p><strong>Thread Safety</strong>: Implementations are expected to be thread-safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface SearchCriteriaDTO extends Serializable {
    /**
     * <p>Returns the filter representing the search criteria.</p>
     *
     * @return the filter representing the search criteria
     */
    public Filter getSearchFilter();
}



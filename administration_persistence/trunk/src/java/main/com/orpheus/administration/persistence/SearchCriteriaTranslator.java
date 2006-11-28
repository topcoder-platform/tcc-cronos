/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.rssgenerator.SearchCriteria;

/**
 * <p>Interface specifying the contract for translating between {@link SearchCriteria SearchCriteria} instances and
 * their transport equivalents -- {@link SearchCriteriaDTO SearchCriteriaDTO}. The former are value objects used in
 * the outside world and a DTO is an entity this component uses to conduct information between the clients and the
 * DAOs. Implementations will constrain the data types they support.
 *
 * <p><strong>Thread Safety</strong>: Implementations are expected to be thread-safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface SearchCriteriaTranslator {
    /**
     * <p>Assembles the <code>SearchCriteriaDTO</code> from a <code>SearchCriteria</code>. The
     * <code>SearchCriteria</code> represents a object that is used outside this component. The DTO is used as the
     * custom transfer object inside this component to ensure serializability.</p>
     *
     * @param searchCriteria the search criteria
     * @return the search criteria data transfer object
     * @throws IllegalArgumentException if <code>searchCriteria</code> is <code>null</code>
     * @throws TranslationException if a problem occurs during the translation
     */
    public SearchCriteriaDTO assembleSearchCriteriaDTO(SearchCriteria searchCriteria) throws TranslationException;
}



/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.SearchCriteriaDTO;
import com.orpheus.administration.persistence.SearchCriteriaTranslator;

import com.orpheus.administration.persistence.Filter;

import com.orpheus.administration.persistence.impl.filter.AndFilter;
import com.orpheus.administration.persistence.impl.filter.BetweenFilter;
import com.orpheus.administration.persistence.impl.filter.EqualToFilter;
import com.orpheus.administration.persistence.impl.filter.GreaterThanFilter;
import com.orpheus.administration.persistence.impl.filter.GreaterThanOrEqualToFilter;
import com.orpheus.administration.persistence.impl.filter.InFilter;
import com.orpheus.administration.persistence.impl.filter.LessThanFilter;
import com.orpheus.administration.persistence.impl.filter.LessThanOrEqualToFilter;
import com.orpheus.administration.persistence.impl.filter.LikeFilter;
import com.orpheus.administration.persistence.impl.filter.NotFilter;
import com.orpheus.administration.persistence.impl.filter.OrFilter;

import com.topcoder.util.rssgenerator.SearchCriteria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>The implementation of <code>SearchCriteriaTranslator</code> to convert from search criteria value objects to
 * data transfer objects. Also includes a method to convert from the DTO back into the VO which is used by {@link
 * SQLServerMessageDAO SQLServerMessageDAO}.
 *
 * <p><strong>Thread Safety</strong>: This class has no state and is therefore thread safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class RSSSearchCriteriaTranslator implements SearchCriteriaTranslator {
    /**
     * <p>Empty default constructor.</p>
     */
    public RSSSearchCriteriaTranslator() {
    }

    /**
     * Converts the specified <i>Search Builder</i> search criteria (the <i>VO</i>) into the representation used
     * internally by this component for remote method invocations (the <i>DTO</i>).
     *
     * @param searchCriteria the search criteria VO
     * @return the search criteria DTO
     * @throws IllegalArgumentException if <code>searchCriteria</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>searchCriteria</code> is not a recognized filter type
     */
    public SearchCriteriaDTO assembleSearchCriteriaDTO(SearchCriteria searchCriteria) {
        if (searchCriteria == null) {
            throw new IllegalArgumentException("search criteria must not be null");
        }

        return new SearchCriteriaDTOImpl(convertFilter(searchCriteria.getSearchFilter()));
    }

    /**
     * Converts the specified internal filter class back into the corresponding search builder filter.
     *
     * @param filter the internal filter
     * @return the search builder filter
     * @throws IllegalArgumentException if the filter class is not recognized
     */
    static com.topcoder.search.builder.filter.Filter convertFilter(Filter filter) {
        if (filter instanceof LessThanFilter) {
            LessThanFilter ltf = (LessThanFilter) filter;
            return new com.topcoder.search.builder.filter.LessThanFilter(ltf.getName(), ltf.getUpperThreshold());
        }
        if (filter instanceof LessThanOrEqualToFilter) {
            LessThanOrEqualToFilter ltoef = (LessThanOrEqualToFilter) filter;
            return new com.topcoder.search.builder.filter.LessThanOrEqualToFilter(ltoef.getName(),
                                                                                  ltoef.getUpperThreshold());
        }
        if (filter instanceof GreaterThanFilter) {
            GreaterThanFilter gtf = (GreaterThanFilter) filter;
            return new com.topcoder.search.builder.filter.GreaterThanFilter(gtf.getName(), gtf.getLowerThreshold());
        }
        if (filter instanceof GreaterThanOrEqualToFilter) {
            GreaterThanOrEqualToFilter gtoef = (GreaterThanOrEqualToFilter) filter;
            return new com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter(gtoef.getName(),
                                                                                     gtoef.getLowerThreshold());
        }
        if (filter instanceof EqualToFilter) {
            EqualToFilter etf = (EqualToFilter) filter;
            return new com.topcoder.search.builder.filter.EqualToFilter(etf.getName(), etf.getValue());
        }
        if (filter instanceof BetweenFilter) {
            BetweenFilter bf = (BetweenFilter) filter;
            return new com.topcoder.search.builder.filter.BetweenFilter(bf.getName(), bf.getUpperThreshold(),
                                                                        bf.getLowerThreshold());
        }
        if (filter instanceof InFilter) {
            InFilter inf = (InFilter) filter;
            return new com.topcoder.search.builder.filter.InFilter(inf.getName(), inf.getList());
        }
        if (filter instanceof AndFilter) {
            return convertAndFilter((AndFilter) filter);
        }
        if (filter instanceof OrFilter) {
            OrFilter of = (OrFilter) filter;
            List filters = new ArrayList();
            for (Iterator it = of.getFilters().iterator(); it.hasNext();) {
                filters.add(convertFilter((Filter) it.next()));
            }
            com.topcoder.search.builder.filter.OrFilter ret = new com.topcoder.search.builder.filter.OrFilter(filters);
            return ret;

        }
        if (filter instanceof NotFilter) {
            NotFilter nf = (NotFilter) filter;
            return new com.topcoder.search.builder.filter.NotFilter(convertFilter(nf.getFilter()));
        }
        if (filter instanceof LikeFilter) {
            LikeFilter lf = (LikeFilter) filter;
            return new com.topcoder.search.builder.filter.LikeFilter(lf.getName(), lf.getValue(),
                                                                     lf.getEscapeCharacter());
        }

        // shouldn't get here
        throw new IllegalArgumentException("unrecgonized filter class '" + filter.getClass().getName() + "'");
    }

    /**
     * Converts an <code>AndFilter</code> into the corresponding search builder filter. This method mainly exists
     * to get around the TopCoder coding conventions limitation of 30 executable statements per method.
     *
     * @param af the and filter
     * @return the corresponding search builder filter
     */
    private static com.topcoder.search.builder.filter.Filter convertAndFilter(AndFilter af) {
        List filters = new ArrayList();
        for (Iterator it = af.getFilters().iterator(); it.hasNext();) {
            filters.add(convertFilter((Filter) it.next()));
        }
        com.topcoder.search.builder.filter.AndFilter ret = new com.topcoder.search.builder.filter.AndFilter(filters);
        return ret;

    }

    /**
     * Converts the specified search builder filter into the corresponding administration persistence internal
     * filter class.
     *
     * @param filter the filter to convert
     * @return the corresponding internal filter class
     * @throws IllegalArgumentException if the filter type is unknown
     */
    private static Filter convertFilter(com.topcoder.search.builder.filter.Filter filter) {
        if (filter instanceof com.topcoder.search.builder.filter.LessThanFilter) {
            com.topcoder.search.builder.filter.LessThanFilter ltf =
                (com.topcoder.search.builder.filter.LessThanFilter) filter;
            return new LessThanFilter(ltf.getName(), ltf.getUpperThreshold());
        }
        if (filter instanceof com.topcoder.search.builder.filter.LessThanOrEqualToFilter) {
            com.topcoder.search.builder.filter.LessThanOrEqualToFilter ltoef =
                (com.topcoder.search.builder.filter.LessThanOrEqualToFilter) filter;
            return new LessThanOrEqualToFilter(ltoef.getName(), ltoef.getUpperThreshold());
        }
        if (filter instanceof com.topcoder.search.builder.filter.GreaterThanFilter) {
            com.topcoder.search.builder.filter.GreaterThanFilter gtf =
                (com.topcoder.search.builder.filter.GreaterThanFilter) filter;
            return new GreaterThanFilter(gtf.getName(), gtf.getLowerThreshold());
        }
        if (filter instanceof com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter) {
            com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter gtoef =
                (com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter) filter;
            return new GreaterThanOrEqualToFilter(gtoef.getName(), gtoef.getLowerThreshold());
        }
        if (filter instanceof com.topcoder.search.builder.filter.EqualToFilter) {
            com.topcoder.search.builder.filter.EqualToFilter ef =
                (com.topcoder.search.builder.filter.EqualToFilter) filter;
            return new EqualToFilter(ef.getName(), ef.getValue());
        }
        if (filter instanceof com.topcoder.search.builder.filter.BetweenFilter) {
            com.topcoder.search.builder.filter.BetweenFilter bf =
                (com.topcoder.search.builder.filter.BetweenFilter) filter;
            return new BetweenFilter(bf.getName(), bf.getUpperThreshold(), bf.getLowerThreshold());
        }
        if (filter instanceof com.topcoder.search.builder.filter.InFilter) {
            com.topcoder.search.builder.filter.InFilter inf = (com.topcoder.search.builder.filter.InFilter) filter;
            return new InFilter(inf.getName(), inf.getList());
        }
        if (filter instanceof com.topcoder.search.builder.filter.AndFilter) {
            com.topcoder.search.builder.filter.AndFilter af = (com.topcoder.search.builder.filter.AndFilter) filter;
            List filters = new ArrayList();
            for (Iterator it = af.getFilters().iterator(); it.hasNext();) {
                filters.add(convertFilter((com.topcoder.search.builder.filter.Filter) it.next()));
            }
            AndFilter andFilter = new AndFilter(filters);
            return andFilter;
        }
        if (filter instanceof com.topcoder.search.builder.filter.OrFilter) {
            com.topcoder.search.builder.filter.OrFilter of = (com.topcoder.search.builder.filter.OrFilter) filter;
            List orFilters = new ArrayList();
            for (Iterator it = of.getFilters().iterator(); it.hasNext();) {
                orFilters.add(convertFilter((com.topcoder.search.builder.filter.Filter) it.next()));
            }
            OrFilter orFilter = new OrFilter(orFilters);
            return orFilter;
        }
        if (filter instanceof com.topcoder.search.builder.filter.NotFilter) {
            com.topcoder.search.builder.filter.NotFilter nf = (com.topcoder.search.builder.filter.NotFilter) filter;
            return new NotFilter(convertFilter(nf.getFilter()));
        }
        if (filter instanceof com.topcoder.search.builder.filter.LikeFilter) {
            com.topcoder.search.builder.filter.LikeFilter lf = (com.topcoder.search.builder.filter.LikeFilter) filter;
            return new LikeFilter(lf.getName(), lf.getValue(), lf.getEscapeCharacter());
        }

        // shouldn't get here
        throw new IllegalArgumentException("unknown filter type '" + filter + "'");
    }
}

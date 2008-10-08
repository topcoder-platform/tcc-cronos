/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import com.topcoder.clients.webservices.filter.FilterAdapter;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * The request wrapper used for the <code>searchXXX(Filter)</code> methods.
 * </p>
 *
 * <p>
 * It is used to adapt the <code>Filter</code> interface by using
 * "@XmlElementRef(type = FilterAdapter.class)".
 * </p>
 *
 * <p>
 * It will be automatically used by the web services to handle the request of the related operation,
 * for this reason the checking is not performed.
 * </p>
 *
 * <p>
 *   <strong>Thread Safety:</strong>
 *   This class is not thread safe because it's highly mutable.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlRootElement(name = "searchRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchRequest {

    /**
     * <p>
     * The filter. It use "@XmlElementRef(type = FilterAdapter.class)" to refer to
     * the <code>FilterAdapter</code> class.
     * </p>
     */
    @XmlElementRef(type = FilterAdapter.class)
    private Filter filter;

    /**
     * <p>
     * Get the filter property.
     * </p>
     *
     * @return the filter
     */
    Filter getFilter() {
        return filter;
    }

    /**
     * <p>
     * Set the filter property.
     * </p>
     *
     * @param filter the filter to set
     */
    void setFilter(Filter filter) {
        this.filter = filter;
    }
}

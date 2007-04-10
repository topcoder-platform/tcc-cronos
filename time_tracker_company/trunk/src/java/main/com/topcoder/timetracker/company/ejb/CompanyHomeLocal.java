/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.ejb;

import javax.ejb.CreateException;


/**
 * <p>
 * The local interface of the Company EJB.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author bramandia, argolite, TCSDEVELOPER
 * @version 3.2
 */
public interface CompanyHomeLocal extends javax.ejb.EJBLocalHome {
    /**
     * <p>
     * Creates the ejb. Initializes any required resources.
     * </p>
     *
     * @return local interface
     *
     * @throws CreateException If any error occurs while instantiating.
     */
    CompanyLocal create() throws CreateException;
}

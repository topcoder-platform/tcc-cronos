/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;


/**
 * <p>
 * The local home interface of the Basic Expense Entry EJB. It contains one, no-argument create method to create an
 * instance of the local Basic Expense Entry.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>The container assumes all responsibility for thread-safety of these implementations.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public interface BasicExpenseEntryLocalHome extends EJBLocalHome {
    /**
     * <p>
     * Creates the ejb. Initializes any required resources.
     * </p>
     *
     * @return local interface
     *
     * @throws CreateException If any error occurs while instantiating
     */
    BasicExpenseEntryLocal create() throws CreateException;
}

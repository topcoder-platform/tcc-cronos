/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * <p>
 * This represent home interface for ServiceDetailBean. Not used in current design. This extends from EJBLocalHome
 * interface
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public interface LocalServiceDetailHome extends EJBLocalHome {
    /**
     * <p>
     * This method is used to create new instance of LocalService. This is used by clients of session bean.
     * </p>
     *
     * @return LocalServiceDetail instance, null impossible
     * @throws CreateException
     *             to indicate a failure caused by a system-level error
     */
    public LocalServiceDetail create() throws CreateException;
}

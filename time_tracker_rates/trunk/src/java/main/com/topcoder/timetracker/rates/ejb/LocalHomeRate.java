/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.ejb;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

/**
 * <p>This is a simple local home interface used to create a local bean for the Rate persistence functionality.</p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 3.2
  */
public interface LocalHomeRate extends EJBLocalHome {

    /**
     * <p>Create a LocalRate instance.</p>
     *
     * @return LocalRate instance
     *
     * @throws CreateException if there was an issue with creation (we re-throw)
     */
    public LocalRate create() throws CreateException;
}

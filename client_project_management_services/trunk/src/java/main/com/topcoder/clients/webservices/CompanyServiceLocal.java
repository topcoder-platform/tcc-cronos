/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.ejb.Local;

/**
 * <p>
 *  This interface represents the CompanyService local interface of the session bean.
 *  Defines a static String variable containing the JNDI name of the local interface.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  Implementations of this interface should be thread safe.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
@Local
public interface CompanyServiceLocal extends CompanyService {

    /**
     * <p>
     *  This static final String field represents the 'JNDI_NAME' identifier of
     *  the CompanyServiceLocal interface.
     *  Represents the JNDI name of this local interface.
     * </p>
     */
    public static final String JNDI_NAME = CompanyService.BEAN_NAME + "/local";
}


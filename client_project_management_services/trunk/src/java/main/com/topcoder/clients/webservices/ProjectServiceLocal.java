/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

import javax.ejb.Local;

/**
 * <p>
 *  This interface represents the ProjectService local interface of the session bean.
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
public interface ProjectServiceLocal extends ProjectService {

    /**
     * This static final String field represents the 'JNDI_NAME'
     * identifier of the ProjectServiceLocal interface.
     * Represents the JNDI name of this local interface.
     */
    public static final String JNDI_NAME = ProjectService.BEAN_NAME + "/local";
}


/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices;

/**
 * <p>
 * This interface represents the <code>ProjectStatusService</code> remote interface of the session bean.
 * Defines a static String variable containing the JNDI name of the remote interface. See base interface for
 * the available operations.
 * </p>
 * <p>
 * Thread safety: Implementations of this interface should be thread safe. Thread safety should be provided
 * technically or by EJB container.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public interface ProjectStatusServiceRemote extends ProjectStatusService {
    /**
     * <p>
     * This static final String field represents the 'JNDI_NAME' identifier of the ProjectStatusServiceRemote
     * interface. Represents the JNDI name of this remote interface. It is initialized to a default value:
     * ProjectStatusService.BEAN_NAME + "/remote" String during runtime. Accessed directly, it is public:
     * ProjectStatusServiceRemote.JNDI_NAME. Can not be changed. It is constant.
     * </p>
     */
    public static final String JNDI_NAME = ProjectStatusService.BEAN_NAME + "/remote";
}

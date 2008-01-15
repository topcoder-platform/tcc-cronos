/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import javax.ejb.Local;

/**
 * <p>
 * This is the EJB local interface for the stateless session bean.
 * </p>
 * <p>
 * It must have the "@Local" annotation. This interface is supposed to be used by classes/application that stay in the
 * same JVM as the EJB container, such as a Servlet.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations of this interface should be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@Local
public interface ClientAssociationServiceLocal extends ClientAssociationService {
}

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import javax.ejb.Remote;

/**
 * <p>
 * This is the EJB remote interface for the stateless session bean.
 * </p>
 * <p>
 * It must have the "@Remote" annotation. This interface is supposed to be used by classes/application that don't stay
 * in the same JVM as the EJB container, generally this means invocation via RMI.
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface should be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface ClientAssociationServiceRemote extends ClientAssociationService {
}

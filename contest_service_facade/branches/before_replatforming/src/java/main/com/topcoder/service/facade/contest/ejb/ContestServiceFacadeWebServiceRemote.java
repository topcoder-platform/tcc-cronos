/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.ejb;

import javax.ejb.Remote;

import com.topcoder.service.facade.contest.ContestServiceFacadeWebService;
/**
 * <p>
 * An interface providing remote access to {@link ContestServiceFacadeWebService}
 * implementation available within the remote application instance or outside of
 * the <code>EJB</code> container.
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> The implementations of this interface must operate in a
 * thread-safe manner to be used inside the <code>EJB</code> container.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
@Remote
public interface ContestServiceFacadeWebServiceRemote extends ContestServiceFacadeWebService {
}

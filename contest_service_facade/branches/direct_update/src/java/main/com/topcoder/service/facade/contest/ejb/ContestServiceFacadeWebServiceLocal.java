/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.ejb;

import javax.ejb.Local;

import com.topcoder.service.facade.contest.ContestServiceFacadeWebService;
/**
 * <p>
 * An interface providing local access to {@link ContestServiceFacadeWebService}
 * implementation available within the same application instance or
 * <code>EJB</code> container.
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
@Local
public interface ContestServiceFacadeWebServiceLocal extends ContestServiceFacadeWebService {
}

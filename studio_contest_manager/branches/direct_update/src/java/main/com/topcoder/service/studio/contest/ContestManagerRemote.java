/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import javax.ejb.Remote;


/**
 * <p>
 * This EJB Remote interface extends the <code>ContestManager</code> interface, and it is
 * marked with @Remote annotation to indicate it's an EJB Remote interface.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * Implementations of this interface need to be stateless session bean, and should be
 * thread-safe when used in EJB container.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface ContestManagerRemote extends ContestManager {
}

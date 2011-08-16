/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import javax.ejb.Local;


/**
 * <p>
 * This EJB Local interface extends the <code>ContestManager</code> interface, and it is
 * marked with @Local annotation to indicate it's an EJB Local interface.
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
@Local
public interface ContestManagerLocal extends ContestManager {
}

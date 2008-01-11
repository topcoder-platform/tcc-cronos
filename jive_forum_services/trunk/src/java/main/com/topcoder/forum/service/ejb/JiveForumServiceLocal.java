/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.ejb;

import javax.ejb.Local;


/**
 * <p>
 * This EJB Local interface extends the <code>JiveForumService</code> interface, and it is
 * marked with <code>Local</code> annotation to indicate it's an EJB Local interface.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong>
 * Implementations of it needs to be stateless session bean, and should be thread-safe when
 * used in EJB container.
 * </p>
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
@Local
public interface JiveForumServiceLocal extends JiveForumService {
}

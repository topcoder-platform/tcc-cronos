/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.forum.service.ejb;

import javax.ejb.Remote;


/**
 * <p>
 * This EJB Remote interface extends the <code>JiveForumService</code> interface, and it is
 * marked with <code>Remote</code> annotation to indicate it's an EJB Remote interface.
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
@Remote
public interface JiveForumServiceRemote extends JiveForumService {
}

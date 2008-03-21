/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.ejb;

import javax.ejb.Local;

import com.topcoder.service.prerequisite.document.DocumentManager;

/**
 * <p>
 * This is the local interface for DocumentManagerBean. When the bean is deployed, the local interface can be looked up
 * via "DocumentManagerBean/local".
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface are not required to be thread safety.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
@Local
public interface DocumentManagerLocal extends DocumentManager {
}

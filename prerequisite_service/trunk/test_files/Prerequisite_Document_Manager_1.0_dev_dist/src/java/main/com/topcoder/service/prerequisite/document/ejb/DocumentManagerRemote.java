/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.ejb;

import javax.ejb.Remote;

import com.topcoder.service.prerequisite.document.DocumentManager;

/**
 * <p>
 * This is the remote interface for DocumentManagerBean. When the bean is deployed, the remote interface can be looked
 * up via "DocumentManagerBean/remote".
 * </p>
 * <p>
 * <b>Thread Safety</b>: Implementations of this interface are not required to be thread safety.
 * </p>
 *
 * @author biotrail, TCSDEVELOPER
 * @version 1.0
 */
@Remote
public interface DocumentManagerRemote extends DocumentManager {
}

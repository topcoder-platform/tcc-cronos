/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track;

import javax.ejb.Remote;


/**
 * <p>
 * This EJB Remote interface extends the <code>DigitalRunTrackManager</code>, and it is marked with @Remote annotation
 * to indicate it's an EJB Remote interface.
 * </p>
 *
 * <p>
 * Implementation will run in an EJB container and thread safety depends on the container. Generally the implementation
 * is thread safe when it's running inside the container.
 * </p>
 * @author DanLazar, waits
 * @version 1.0
 */
@Remote
public interface DigitalRunTrackManagerRemote extends DigitalRunTrackManager {
}

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.ejb;

import javax.ejb.EJBLocalObject;

import com.topcoder.timetracker.entry.time.TimeStatusManager;

/**
 * <p>
 * This is the local interface for <code>TimeStatusManager</code>.
 * </p>
 *
 * <p>
 * It contains exactly the same methods as <code>TimeStatusManager</code> interface.
 * </p>
 *
 * <p>
 * Thread Safety: Implementation will be generated by EJB container and thread-safety
 * is dependent on the container.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface TimeStatusManagerLocal extends TimeStatusManager, EJBLocalObject {
}

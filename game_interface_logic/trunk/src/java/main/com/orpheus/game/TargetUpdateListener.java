/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.DomainTarget;
import com.topcoder.util.web.sitestatistics.TextStatistics;


/**
 * <p>
 * This interface is an observer of a target change on hosting sites. Implementations do not have to
 * be thread-safe since a single thread is guaranteed to trigger this notification.
 * </p>
 *
 * @author George1
 * @version 1.0
 */
public interface TargetUpdateListener {

    /**
     * <p>
     * This is a notification method that tells the listening observer that a target has changed on
     * some hosting site. It is implied that implementing class will find the new target and return
     * an object containing information about that target to the calling method.
     * </p>
     *
     * @return implementations should return <code>DomainTarget</code> object containing
     *         information about new target.
     * @param foundTextStats text statistics collected from some page on the hosting site.
     *            Implementations of this method may use this information to construct new target
     *            object.
     * @param obsoleteTarget a reference to the target object which now has become obsolete.
     *            Implementations of this method may use this information to construct new target
     *            object.
     */
    public DomainTarget targetUpdated(TextStatistics[] foundTextStats, DomainTarget obsoleteTarget);
}

/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.service.studio.contest.ContestManager;
import com.topcoder.util.cache.Cache;
import com.topcoder.util.log.Log;

import javax.ejb.Remote;
import java.util.Map;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
@Remote
public interface CockpitPhaseManagerInterface extends PhaseManager {

    /**
     * <p>
     * Returns the contest manager which is configured or set in this manager.
     * </p>
     * @return the contest manager in this manager.
     */
    public ContestManager getContestManager();

    /**
     * <p>
     * Sets the contest manager to be used in this manager.
     * </p>
     * @param contestManager the contestManager to set.
     * @throws IllegalArgumentException if <code>contestManager</code> is <code>null</code>.
     */
    public void setContestManager(ContestManager contestManager);

    /**
     * <p>
     * Returns the log used by this manager.
     * </p>
     * @return the possibly <code>null</code> log used by this manager.
     */
    public Log getLog();

    /**
     * <p>
     * Sets the log. It can be null which means no logging is performed.
     * </p>
     * @param log the log to set, can be <code>null</code>.
     */
    public void setLog(Log log);

    /**
     * <p>
     * Sets the log by the name. If <code>logName</code> is not <code>null</code>, then a log is
     * retrieved from <code>LogManager</code> by the name, otherwise no logging is performed in this
     * manager.
     * </p>
     * @param logName the name of log.
     * @throws IllegalArgumentException if <code>logName</code> is empty.
     */
    public void setLog(String logName);

    /**
     * <p>
     * Returns the cache used for ContestStatus instances. If it is not null and not empty, the cache will
     * contain 1 object with key "contestStatuses".
     * </p>
     * @return the possibly <code>null</code> cache used for ContestStatuses instances.
     */
    public Cache getCachedContestStatuses();

    /**
     * <p>
     * Sets the cache used for ContestStatus instances.
     * </p>
     * @param cachedContestStatuses the cache used for ContestStatus instances, can be <code>null</code>.
     */
    public void setCachedContestStatuses(Cache cachedContestStatuses);

    /**
     * <p>
     * Returns the handlers map. A shallow copy is returned.
     * </p>
     * @return the handlers.
     */
    public Map<HandlerRegistryInfo, PhaseHandler> getHandlers();

    /**
     * <p>
     * Set the handlers. A shallow copy is used.
     * </p>
     * @param handlers the handlers to set.
     * @throws IllegalArgumentException if <code>handlers</code> is <code>null</code>, or if
     *         <code>handlers</code> contains <code>null</code> keys or values.
     */
    public void setHandlers(Map<HandlerRegistryInfo, PhaseHandler> handlers);
}

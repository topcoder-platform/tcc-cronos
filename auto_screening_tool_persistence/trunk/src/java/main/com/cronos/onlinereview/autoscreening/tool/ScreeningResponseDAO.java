/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

/**
 * <p>
 * This interface is used to provide a Data Access Object for the datastore of
 * Screening Responses.
 * </p>
 * <p>
 * A minimalistic interface is provided to accomplish only the tasks that are
 * needed by this component. (for actual DAO management, the Screening
 * Management component is suggested)
 * </p>
 * <p>
 * This interface is provided in order to support the logging of
 * ScreeningResposne into the datastore (in conjunction with DAOLogger).
 * </p>
 * <p>
 * Thread Safety: Implementations are not required to be thread-safe, but it is
 * recommended to create them in such a way that multiple concurrent invocations
 * can be performed safely.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public interface ScreeningResponseDAO {
    /**
     * <p>
     * Records a screening response within the data store.
     * </p>
     * @param screeningResponse
     *            the screening response to create within the data store.
     * @throws IllegalArgumentException
     *             if the screeningResponse is null.
     * @throws DAOException
     *             if a problem occurs while accessing the datastore.
     */
    public void createScreeningResponse(ScreeningResponse screeningResponse) throws DAOException;
}

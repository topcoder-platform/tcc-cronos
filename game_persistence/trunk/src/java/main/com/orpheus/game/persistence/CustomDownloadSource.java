/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.topcoder.web.frontcontroller.results.DownloadData;
import com.topcoder.web.frontcontroller.results.DownloadDataRetrievalException;
import com.topcoder.web.frontcontroller.results.DownloadSource;


/**
 * <p>
 * This is the download source client to the EJB layer. It implements the DonwnloadSource. It is built to work with
 * EJBs, and this class leaves it to implementations to specify the EJBs. This is purpose of the abstract ejbXXX
 * methods. The public methods defer to these for actual persistence calls.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong>This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class CustomDownloadSource implements DownloadSource {
    /**
     * Empty constructor.
     */
    protected CustomDownloadSource() {
    }

    /**
     * <p>
     * Determines and returns the download data and metadata associated with the specified ID string, or returns null
     * if it cannot associate a download with the specified ID.
     * </p>
     *
     * @param id The id
     *
     * @return DownloadData The download data for the id
     *
     * @throws DownloadDataRetrievalException if this method fails because of an internal checked exception
     */
    public DownloadData getDownloadData(String id) throws DownloadDataRetrievalException {
        try {
            return this.ejbGetDownloadData(id);
        } catch (Exception e) {
            throw new DownloadDataRetrievalException("Error in getDownloadData from EJB.", e);
        }
    }

    /**
     * <p>
     * Determines and returns the download data and metadata associated with the specified ID string from persistence
     * using the applicable EJB, or returns null if it cannot associate a download with the specified ID.
     * </p>
     *
     * @param id The id
     *
     * @return DownloadData The download data for the id
     *
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    protected abstract DownloadData ejbGetDownloadData(String id)
        throws PersistenceException;
}

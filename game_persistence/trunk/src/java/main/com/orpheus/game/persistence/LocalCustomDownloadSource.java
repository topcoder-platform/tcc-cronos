/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.topcoder.web.frontcontroller.results.DownloadData;

import javax.naming.InitialContext;


/**
 * <p>
 * Implements the abstract ejbXXX method to work with the local game data EJB. Simply defers all calls to the EJB. It
 * uses the ConfigManager and Object Factory to initialize the JNDI EJB reference to obtain the handle to the EJB
 * interface itself.
 * </p>
 * <p>This class has five configuration parameters.
 *
 * <ul>
 *   <li><strong>jndiEjbReference</strong> (required): The JNDI reference for the EJB</li>
 * </ul>
 * </p>
 * 
 * <p>
 * <b>Thread Safety:</b>This class is immutable and thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class LocalCustomDownloadSource extends CustomDownloadSource {
    /**
     * <p>
     * Represents the local ejb instance used for all calls. Created in the consructor, will not be null, and will
     * not change.
     * </p>
     */
    private final GameDataLocal gameDataEJB;

    /**
     * <p>
     * Instantiates new LocalCustomDownloadSource instance from the given namespace.
     * </p>
     *
     * @param namespace configuration namespace
     *
     * @throws InstantiationException If there is an error with construction
     * @throws IllegalArgumentException If namespace is null or empty
     */
    public LocalCustomDownloadSource(String namespace)
        throws InstantiationException {
        Helper.checkNotNullOrEmpty(namespace, "namespace");

        String ejbReference = Helper.getString(namespace, "jndiEjbReference", true);

        try {
            InitialContext ic = new InitialContext();
            GameDataLocalHome home = (GameDataLocalHome) ic.lookup(ejbReference);
            gameDataEJB = home.create();
        } catch (Exception e) {
            throw new InstantiationException("Error in instantiate the LocalCustomDownloadSource.", e);
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
     * @throws NumberFormatException the id is not of long type
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    protected DownloadData ejbGetDownloadData(String id)
        throws PersistenceException {
        return gameDataEJB.getDownloadData(Long.parseLong(id));
    }
}

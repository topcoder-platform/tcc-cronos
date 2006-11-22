/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.topcoder.web.frontcontroller.results.DownloadData;

import java.rmi.RemoteException;

import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;


/**
 * <p>
 * Implements the abstract ejbXXX method to work with the remote game data EJB. Simply defers all calls to the EJB.
 * It uses the ConfigManager and Object Factory to initialize the JNDI EJB reference to obtain the handle to the EJB
 * interface itself.
 * </p>
 * <p>This class has five configuration parameters.
 *
 * <ul>
 *   <li><strong>jndiEjbReference</strong> (required): The JNDI reference for the EJB</li>
 * </ul>
 * </p>
 * <p>
 * <b>Thread Safety:</b>This class is immutable and thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class RemoteCustomDownloadSource extends CustomDownloadSource {
    /** Respresents the prop name in config file. */
    private static final String JNDI_EJB_REFERENCE = "jndiEjbReference";

    /**
     * <p>
     * Represents the remote ejb instance used for all calls. Created in the consructor, will not be null, and will
     * not change.
     * </p>
     */
    private final GameData gameDataEJB;

    /**
     * <p>
     * Instantiates new RemoteCustomDownloadSource instance from the given namespace.
     * </p>
     *
     * @param namespace configuration namespace
     *
     * @throws InstantiationException If there is an error with construction
     * @throws IllegalArgumentException If namespace is null or empty
     */
    public RemoteCustomDownloadSource(String namespace) throws InstantiationException {
        Helper.checkNotNullOrEmpty(namespace, "namespace");

        String ejbReference = Helper.getString(namespace, JNDI_EJB_REFERENCE, true);

        try {
            InitialContext ic = new InitialContext();
            Object lookup = ic.lookup(ejbReference);
            GameDataHome home = (GameDataHome) PortableRemoteObject.narrow(lookup, GameDataHome.class);
            gameDataEJB = home.create();
        } catch (Exception e) {
            throw new InstantiationException("Error in instantiate the RemoteCustomDownloadSource.", e);
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
     * @throws PersistenceException If there is any problem in the persistence layer
     * @throws NumberFormatException the id is not of long type
     */
    protected DownloadData ejbGetDownloadData(String id) throws PersistenceException {
        try {
            return gameDataEJB.getDownloadData(Long.parseLong(id));
        } catch (PersistenceException e) {
            throw e;
        } catch (RemoteException e) {
            throw new PersistenceException("Error in get download data from EJB.", e);
        }
    }
}

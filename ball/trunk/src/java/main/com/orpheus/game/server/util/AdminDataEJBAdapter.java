/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataLocal;
import com.orpheus.administration.persistence.PersistenceException;
import com.topcoder.util.puzzle.PuzzleData;

import java.rmi.RemoteException;

/**
 * <p>An adapter for <code>Admin Data EJB</code> allowing to access the EJB in a way independent of the type of
 * interface (remote or local) specified by the configuration settings.</p>
 *
 * <p>The methods provided by the adapter mimic the methods provided by the EJB and simply delegate to EJB instance
 * passed at construction time.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminDataEJBAdapter {

    /**
     * <p>A <code>boolean</code> indicating whether a remote or local interface is used for accessing <code>Admin Data
     * EJB</code>.</p>
     */
    private final boolean remoteInterface;

    /**
     * <p>A <code>AdminData</code> providing a remote view for <code>Admin Data EJB</code>.</p>
     */
    private final AdminData adminDataRemote;

    /**
     * <p>A <code>AdminDataLocal</code> providing a local view for <code>Admin Data EJB</code>.</p>
     */
    private final AdminDataLocal adminDataLocal;

    /**
     * <p>Constructs new <code>AdminDataEJBAdapter</code> instance to be used for accessing <code>Admin Data EJB</code>
     * through remote view.</p>
     *
     * @param adminDataEJB a <code>AdminData</code> providing remote view for the <code>Admin Data EJB</code>.
     * @throws IllegalArgumentException if specified <code>adminDataEJB</code> is <code>null</code>.
     */
    public AdminDataEJBAdapter(AdminData adminDataEJB) {
        if (adminDataEJB == null) {
            throw new IllegalArgumentException("The parameter [adminDataEJB] is NULL");
        }
        this.remoteInterface = true;
        this.adminDataRemote = adminDataEJB;
        this.adminDataLocal = null;
    }


    /**
     * <p>Constructs new <code>AdminDataEJBAdapter</code> instance to be used for accessing <code>Admin Data EJB</code>
     * through local view.</p>
     *
     * @param adminDataEJB a <code>AdminDataLocal</code> providing local view for the <code>Admin Data EJB</code>.
     * @throws IllegalArgumentException if specified <code>adminDataEJB</code> is <code>null</code>.
     */
    public AdminDataEJBAdapter(AdminDataLocal adminDataEJB) {
        if (adminDataEJB == null) {
            throw new IllegalArgumentException("The parameter [adminDataEJB] is NULL");
        }
        this.remoteInterface = false;
        this.adminDataRemote = null;
        this.adminDataLocal = adminDataEJB;
    }

    /**
     * <p>Records the specified PuzzleData objects in the application's database, returning an array of the unique
     * IDs assigned to them (in the same order as the puzzles appear in the argument).</p>
     *
     * @param puzzles the puzzles to store.
     * @return the IDs of the stored puzzles.
     * @throws IllegalArgumentException if <code>puzzles</code> is <code>null</code> or empty or contains <code>null
     *         </code> elements.
     * @throws PersistenceException if any persistence error occurs.
     * @throws RemoteException if there is a network issue.
     */
    public long[] storePuzzles(PuzzleData[] puzzles) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.adminDataRemote.storePuzzles(puzzles);
        } else {
            return this.adminDataLocal.storePuzzles(puzzles);
        }
    }

    /**
     * <p>Creates new Ball color with specified name and associated with the specified image providing the Ball color
     * icon.</p>
     *
     * @param colorName a <code>String</code> providing the name for the new Ball color.
     * @param imageId a <code>long</code> providing the ID of an image associated with new Ball color.
     * @return a <code>long</code> providing the unique ID for the create Ball color.
     * @throws PersistenceException if an error occurs while accessing the persistent storage.
     * @throws RemoteException if there is a network issue
     */
    public long createBallColor(String colorName, long imageId) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.adminDataRemote.createBallColor(colorName, imageId);
        } else {
            return this.adminDataLocal.createBallColor(colorName, imageId);
        }
    }
}

/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.util;

import com.orpheus.game.persistence.EntryNotFoundException;
import com.orpheus.game.persistence.PersistenceException;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.DuplicateEntryException;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.InvalidEntryException;
import com.orpheus.game.persistence.ImageInfo;
import com.topcoder.web.frontcontroller.results.DownloadData;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;

/**
 * <p>An adapter for <code>Game Data EJB</code> allowing to access the EJB in a way independent of the type of interface
 * (remote or local) specified by the configuration settings.</p>
 *
 * <p>The methods provided by the adapter mimic the methods provided by the EJB and simply delegate to EJB instance
 * passed at construction time.</p>
 *
 * @author isv
 * @version 1.0
 */
public class GameDataEJBAdapter {

    /**
     * <p>A <code>boolean</code> indicating whether a remote or local interface is used for accessing <code>Game Data
     * EJB</code>.</p>
     */
    private final boolean remoteInterface;

    /**
     * <p>A <code>GameData</code> providing a remote view for <code>Game Data EJB</code>.</p>
     */
    private final GameData gameDataRemote;

    /**
     * <p>A <code>GameData</code> providing a local view for <code>Game Data EJB</code>.</p>
     */
    private final GameDataLocal gameDataLocal;

    /**
     * <p>Constructs new <code>GameDataEJBAdapter</code> instance to be used for accessing <code>Game Data EJB</code>
     * through remote view.</p>
     *
     * @param gameDataEJB a <code>GameData</code> providing remote view for the <code>Game Data EJB</code>.
     * @throws IllegalArgumentException if specified <code>gameDataEJB</code> is <code>null</code>. 
     */
    public GameDataEJBAdapter(GameData gameDataEJB) {
        if (gameDataEJB == null) {
            throw new IllegalArgumentException("The parameter [gameDataEJB] is NULL");
        }
        this.remoteInterface = true;
        this.gameDataRemote = gameDataEJB;
        this.gameDataLocal = null;
    }


    /**
     * <p>Constructs new <code>GameDataEJBAdapter</code> instance to be used for accessing <code>Game Data EJB</code>
     * through local view.</p>
     *
     * @param gameDataEJB a <code>GameData</code> providing local view for the <code>Game Data EJB</code>.
     * @throws IllegalArgumentException if specified <code>gameDataEJB</code> is <code>null</code>.
     */
    public GameDataEJBAdapter(GameDataLocal gameDataEJB) {
        if (gameDataEJB == null) {
            throw new IllegalArgumentException("The parameter [gameDataEJB] is NULL");
        }
        this.remoteInterface = false;
        this.gameDataRemote = null;
        this.gameDataLocal = gameDataEJB;
    }

    /**
     * <p>Finds the first HostingSlot in the hosting sequence for the specified game that is assigned the specified
     * domain and has not yet been completed by the specified player.</p>
     *
     * @param gameId the game id.
     * @param playerId the player id.
     * @param domain the domain.
     * @return hosting slot.
     * @throws EntryNotFoundException If there is no domain with the given name, or no player or game with such an id
     *         in persistence.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws IllegalArgumentException if domain is null/empty.
     */
    public HostingSlot findSlotForDomain(long gameId, long playerId, String domain) throws PersistenceException,
                                                                                           RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findSlotForDomain(gameId, playerId, domain);
        } else {
            return this.gameDataLocal.findSlotForDomain(gameId, playerId, domain);
        }
    }

    /**
     * <p>Records the completion of the specified slot by the specified player at the specified date and time, and
     * generates a key for the player to associate with the completion.</p>
     *
     * @param playerId the player id.
     * @param slotId the slot id.
     * @param date date of completion.
     * @return the slot completion ent.ity
     * @throws RemoteException if a commun.ication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws DuplicateEntryException If the slot has already been completed for this player.
     * @throws EntryNotFoundException If playerID or slotId is not in persistence.
     * @throws IllegalArgumentException if date is <code>null</code>.
     */
    public SlotCompletion recordSlotCompletion(long playerId, long slotId, Date date) throws RemoteException,
                                                                                             PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.recordSlotCompletion(playerId, slotId, date);
        } else {
            return this.gameDataLocal.recordSlotCompletion(playerId, slotId, date);
        }
    }

    /**
     * <p>Retrieves a Game object representing the Game having the specified ID.</p>
     *
     * @param gameId the game id.
     * @return the game.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If gameId is not in persistence.
     */
    public Game getGame(long gameId) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.getGame(gameId);
        } else {
            return this.gameDataLocal.getGame(gameId);
        }
    }

    /**
     * <p>Records a binary object in the database, such as might later be retrieved by the custom DownloadSource. The ID
     * assigned to the binary object is returned.</p>
     *
     * @param name the name of the object.
     * @param mediaType the media type.
     * @param content the content as a byte array.
     * @return the binary object.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If name or mediaType is null/empty, or content is null.
     */
    public long recordBinaryObject(String name, String mediaType, byte[] content) throws RemoteException,
                                                                                         PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.recordBinaryObject(name, mediaType, content);
        } else {
            return this.gameDataLocal.recordBinaryObject(name, mediaType, content);
        }
    }

    /**
     * <p>Looks up all distinct domains hosting any slot in any active game, and returns an array of Domain objects
     * representing the results.</p>
     *
     * @return array of active domains.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public Domain[] findActiveDomains() throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findActiveDomains();
        } else {
            return this.gameDataLocal.findActiveDomains();
        }
    }

    /**
     * <p>Retrieves a HostingSlot object representing the slot having the specified ID.</p>
     *
     * @param slotId the slot id.
     * @return the slot.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If slotId is not in persistence
     */
    public HostingSlot getSlot(long slotId) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.getSlot(slotId);
        } else {
            return this.gameDataLocal.getSlot(slotId);
        }
    }

    /**
     * <p>Provides information about all ball colors available to the application.</p>
     *
     * @return array of ball colors.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public BallColor[] findAllBallColors() throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findAllBallColors();
        } else {
            return this.gameDataLocal.findAllBallColors();
        }
    }

    /**
     * <p>Retrieves game information for games meeting the specified game status criteria.</p>
     *
     * @param isStarted a Boolean having value true to restrict to games that have started or false to restrict to
     *        games that have not yet started; null to ignore whether games have started.
     * @param isEnded a Boolean having value true to restrict to games that have ended or false to restrict to games
     *        that have not yet ended; null to ignore whether games have ended.
     * @return an array of Game objects representing the games found.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If isStarted == false and isEnded == true (impossible combo).
     */
    public Game[] findGames(Boolean isStarted, Boolean isEnded) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findGames(isStarted, isEnded);
        } else {
            return this.gameDataLocal.findGames(isStarted, isEnded);
        }
    }

    /**
     * <p>Looks up all recorded completion events that have the specified hosting slot in the specified game.</p>
     *
     * @param gameId the game id.
     * @param slotId the slot id.
     * @return array of slot competition events.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws InvalidEntryException If slotId is not part of the game indicated by gameId.
     * @throws EntryNotFoundException If gameId or slotId is not in persistence.
     */
    public SlotCompletion[] findSlotCompletions(long gameId, long slotId) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findSlotCompletions(gameId, slotId);
        } else {
            return this.gameDataLocal.findSlotCompletions(gameId, slotId);
        }
    }

    /**
     * <p>Looks up all hosting slots completed by any player in the specified game, and returns the results as an array
     * of HostingSlot objects. Returned slots are in ascending order by first completion time, or equivalently, in
     * ascending order by hosting block sequence number and hosting slot sequence number.</p>
     *
     * @param gameId the game id.
     * @return array of hosting slots.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If gameId is not in persistence.
     */
    public HostingSlot[] findCompletedSlots(long gameId) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findCompletedSlots(gameId);
        } else {
            return this.gameDataLocal.findCompletedSlots(gameId);
        }
    }

    /**
     * <p>Looks up all the games for which the specified player is registered, and returns an array of their IDs.</p>
     *
     * @param playerId the player id.
     * @return the array of game ids.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If playerId is not in persistence.
     */
    public long[] findGameRegistrations(long playerId) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findGameRegistrations(playerId);
        } else {
            return this.gameDataLocal.findGameRegistrations(playerId);
        }
    }

    /**
     * <p>Looks up all the games for which the specified player has already completed, and returns an array of their
     * IDs.</p>
     *
     * @param playerId the player id.
     * @return the array of game ids.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If playerId is not in persistence.
     */
    public long[] findCompletedGameIds(long playerId) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findCompletedGameIds(playerId);
        } else {
            return this.gameDataLocal.findCompletedGameIds(playerId);
        }
    }

    /**
     * <p>
     * Updates the persistent hosting slot information for the existing slots represented by the specified
     * HostingSlot objects, so that the persistent representation matches the provided objects. Nested DomainTarget
     * objects may or may not already be recorded in persistence; the component assumes that DomainTarget's with
     * null IDs are new, and that others already exist in the database. The component will assign IDs for new
     * DomainTargets as needed. This method will also update the following additional HostingSlot properties (only):
     * sequence number, hosting start, hosting end, brain teaser IDs, puzzle ID. It will return an array containing
     * the revised hosting slots.
     * </p>
     *
     * @param slots the hosting slots to update
     *
     * @return the updated hosting slots
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If HsotingSlot.id or DomainTarget.id, if not null, is not in persistence
     * @throws IllegalArgumentException If slots is null
     */
    public HostingSlot[] updateSlots(HostingSlot[] slots) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.updateSlots(slots);
        } else {
            return this.gameDataLocal.updateSlots(slots);
        }
    }

    /**
     * <p>
     * Updates the persistent domain information for the specified Domain object to match the Domain object, where
     * the appropriate persistent record is identified by the Domain's ID.
     * </p>
     *
     * @param domain domain to update
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws IllegalArgumentException If domain is null
     * @throws EntryNotFoundException If Domain.id or ImageInfo.id, if not null, is not in persistence
     */
    public void updateDomain(Domain domain) throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            this.gameDataRemote.updateDomain(domain);
        } else {
            this.gameDataLocal.updateDomain(domain);
        }
    }

    /**
     * <p>Returns the statistics for downloaded plugins. </p>
     *
     * @return a mapping from plugin name to number of plugin downloads.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws RemoteException if a communication error occurs between client and EJB container
     */
    public Map getPluginDownloadStats() throws RemoteException, PersistenceException {
        if (this.remoteInterface) {
            return this.gameDataRemote.getPluginDownloadStats();
        } else {
            return this.gameDataLocal.getPluginDownloadStats();
        }
    }

    /**
     * <p>Retrieves the DownloadData object corresponding to the specified ID.</p>
     *
     * @param downloadId the download id.
     * @return the download data.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If id is not in persistence.
     */
    public DownloadData getDownloadData(long downloadId) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.getDownloadData(downloadId);
        } else {
            return this.gameDataLocal.getDownloadData(downloadId);
        }
    }

    /**
     * <p>Sets the time of completion of the specified game to specified date. </p>
     *
     * @param gameId the ID of a game to complete.
     * @param endDate the time of game completion.
     * @throws EntryNotFoundException if specified game does not exist.
     * @throws PersistenceException if there is any problem in the persistence layer.
     * @throws RemoteException if a communication error occurs between client and EJB container
     */
    public void completeGame(long gameId, Date endDate) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            this.gameDataRemote.completeGame(gameId, endDate);
        } else {
            this.gameDataLocal.completeGame(gameId, endDate);
        }
    }

    /**
     * <p> Looks up all domains associated with the specified sponsor and returns an array of Domain objects
     * representing them.</p>
     *
     * @param sponsorId the sponsor id.
     * @return array of domains.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If sponsorId is not in persistence.
     */
    public Domain[] findDomainsForSponsor(long sponsorId) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.findDomainsForSponsor(sponsorId);
        } else {
            return this.gameDataLocal.findDomainsForSponsor(sponsorId);
        }
    }

    /**
     * <p>Creates a new game entity in the persistent store, along with associated hosting blocks. Any game or block IDs
     * that are null will be automatically assigned acceptable values. No hosting slots are created for the game at
     * this time. The returned Game object will represent the persisted data, including any IDs assigned to the game
     * and blocks.</p>
     *
     * @param game the game.
     * @return the game with the id.
     * @throws RemoteException if a communication error occurs between client and EJB container.
     * @throws PersistenceException if there is any problem in the persistence layer.
     * @throws EntryNotFoundException if game.ballColor.id is not found in persistence.
     * @throws IllegalArgumentException if game is null.
     * @throws DuplicateEntryException if game.id is not null but already exists in persistence.
     */
    public Game createGame(Game game) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.createGame(game);
        } else {
            return this.gameDataLocal.createGame(game);
        }
    }

    /**
     * <p>Creates hosting slots associates with the specified Bid IDs in the specified hosting block. </p>
     *
     * @param blockId the block id.
     * @param bidIds the bid ids.
     * @return array of hosting slots,
     * @throws RemoteException if a communication error occurs between client and EJB container,
     * @throws PersistenceException if there is any problem in the persistence layer.
     * @throws EntryNotFoundException if blockId or any bidId doesn't exist in the persistence.
     * @throws InvalidEntryException if any bidId does not belong to the blockId.
     * @throws IllegalArgumentException if bidIds is null.
     */
    public HostingSlot[] createSlots(long blockId, long[] bidIds) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.createSlots(blockId, bidIds);
        } else {
            return this.gameDataLocal.createSlots(blockId, bidIds);
        }
    }

    /**
     * <p>
     * Creates specified hosting slots. This method will persist the slots in hosting_slot table and return the
     * appropiate hosting slots. The domain targets provided with the specified slots will be persisted in target_object
     * table.
     * </p>
     *
     * @param slots a list of slots to create.
     * @return array of hosting slots.
     * @throws EntryNotFoundException if blockId or any bidId doesn't exist in the persistence
     * @throws InvalidEntryException if any bidId does not belong to the blockId
     * @throws PersistenceException if there is any problem in the persistence layer.
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws IllegalArgumentException if bidIds is null
     */
    public HostingSlot[] createSlots(HostingSlot[] slots) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.createSlots(slots);
        } else {
            return this.gameDataLocal.createSlots(slots);
        }
    }

    /**
     * <p>
     * Creates a new persistent domain representation with the data from the provided Domain object and its nested
     * ImageInfo objects. Any null Domain or ImageIndo IDs are assigned appropriate values. The returned Domain will
     * reflect the persistent representation, including any automatically assigned IDs.
     * </p>
     *
     * @param domain the domain.
     * @return the domain with id.
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If imageInfo.downloadId doesn't exist in persistence
     * @throws DuplicateEntryException If id or imageInfo.id is not null but already exists in persistence
     * @throws IllegalArgumentException If domain is null
     */
    public Domain createDomain(Domain domain) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.createDomain(domain);
        } else {
            return this.gameDataLocal.createDomain(domain);
        }
    }

    /**
     * <p>
     * Retrieves a Domain object representing the domain corresponding to the specified ID.
     * </p>
     *
     * @param domainId the domain id
     *
     * @return the domain
     *
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws EntryNotFoundException If domainId is not in persistence
     */
    public Domain getDomain(long domainId) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.getDomain(domainId);
        } else {
            return this.gameDataLocal.getDomain(domainId);
        }
    }

    /**
     * <p>
     * Retrieves a ImageInfo object representing the image corresponding to the specified ID.
     * </p>
     *
     * @param imageId the image id.
     * @return the image.
     * @throws EntryNotFoundException If imageId is not in persistence.
     * @throws RemoteException if a communication error occurs between client and EJB container
     * @throws PersistenceException If there is any problem in the persistence layer.
     */
    public ImageInfo getImage(long imageId) throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.getImage(imageId);
        } else {
            return this.gameDataLocal.getImage(imageId);
        }
    }

    /**
     * <p>
     * Looks up all approved domains and returns an array of Domain objects representing them.
     * </p>
     *
     * @return array of domains.
     * @throws EntryNotFoundException If sponsorId is not in persistence
     * @throws PersistenceException If there is any problem in the persistence layer.
     * @throws RemoteException if a communication error occurs between client and EJB container
     */
    public Domain[] getApprovedDomains() throws PersistenceException, RemoteException {
        if (this.remoteInterface) {
            return this.gameDataRemote.getApprovedDomains();
        } else {
            return this.gameDataLocal.getApprovedDomains();
        }
    }
}

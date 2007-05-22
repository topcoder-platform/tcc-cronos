/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * <code>GameDataHelper</code> class encapsulates all EJB calls to the Game
 * Data Persistence Interface, so that the handler implementations don't have to
 * interact with the EJB interfaces directly, which makes them simpler and
 * easier to maintain. Several configurable values are supported by this class
 * through the <code>ConfigManager</code> component in order to make it more
 * flexible: The use_remote_home configurable property is used to control
 * whether to use remote or local home interface of Game Data Persistence EJB.
 * The game_data_jndi_name configurable property is the jndi name used to lookup
 * the home interface object. And the jndi_context_name is used by the
 * JNDIUtility component, so that we can make the created InitialContext more
 * configurable.
 * 
 * Thread-safety: All variables are immutable, all methods call methods from EJB
 * interface, and no state is maintained. As the Game Data Interface component
 * is thread-safety, so this class is thread-safe too.
 * </p>
 * 
 * @author Standlove, mittu
 * @version 1.0
 */
class GameDataHelper {
    /**
     * <p>
     * Represents the namespace used to load configuration values from the
     * <code>ConfigManager</code>. It is initialized in place with
     * <code>GameDataHelper</code> class' full qualified name, and never
     * changed afterwards. Used in static initializer.
     * </p>
     */
    private static final String NAMESPACE = GameDataHelper.class.getName();

    /**
     * <p>
     * Represents the singleton instance, initialized in the getInstance only
     * once, and never changed afterwards.
     * </p>
     */
    private static GameDataHelper instance;

    /**
     * <p>
     * Represents a flag indicating either the remote interface or local
     * interface of the persistence component should be used. It is initialized
     * in the constructor, and never changed afterwards. It is used in all
     * methods to determine which interface to use. If it is not configured
     * (through ConfigManager), it is default to true - remote interface is
     * used.
     * </p>
     */
    private final boolean useRemoteHome;

    /**
     * <p>
     * Represents the jndi name to lookup the Game Persistence's local interface
     * or remote interface. It is initialized in the constructor, and never
     * changed afterwards. It must be non-null, non-empty string. It is used in
     * all methods to get the local interface or remote interface depending on
     * the useRemoteHome's value.
     * </p>
     */
    private final String gameDataJNDIName;

    /**
     * <p>
     * Represents the context name used to get the <code>Context</code> object
     * from the JNDIUtils class. It is initialized in the constructor, and never
     * changed afterwards. It must be non-null, non-empty string. It is default
     * to "default" if not configured (through ConfigManager). It is used in all
     * methods to get the Context object used to lookup the game data's local or
     * remote interface.
     * </p>
     */
    private final String jndiContextName;

    /**
     * <p>
     * Represents the cached context which is used to get the ebj object. The
     * JNDI lookup maybe slow, anyway it speeds thing up if we cache the result.
     * </p>
     */
    private final Context context;

    /**
     * <p>
     * Initialize all static variables to ensure methods in this helper class
     * work well.
     * </p>
     * 
     * @throws GameDataConfigurationException
     *             if any value is not configured properly, wraps exceptions
     *             from ConfigManager.
     */
    private GameDataHelper() {
        String tmp = getValue("jndi_context_name", true);
        // if "jndi_context_name" is missing or empty use "default".
        if (tmp != null && tmp.trim().length() != 0) {
            jndiContextName = tmp;
        } else {
            jndiContextName = "default";
        }
        gameDataJNDIName = getValue("game_data_jndi_name", false);
        tmp = getValue("use_remote_home", true);
        // if "use_remote_home" is false then false, otherwise true.
        if (tmp != null && tmp.trim().length() != 0 && tmp.equals("false")) {
            useRemoteHome = false;
        } else {
            useRemoteHome = true;
        }
        context = getContext();
    }

    /**
     * Helper method to get the key value of the specified key from the
     * <code>ConfigManager</code>. If any error happens
     * <code>GameDataConfigurationException</code> will be thrown.
     * 
     * @param key
     *            The key to be used.
     * @param optional
     *            Whether the key value is optional.
     * @return value specified by the key.
     * @throws GameDataConfigurationException
     *             If any error occurs.
     */
    private static String getValue(String key, boolean optional) {
        try {
            String value = ConfigManager.getInstance()
                    .getString(NAMESPACE, key);
            if (!optional) {
                ImplementationHelper.checkStringValid(value, key);
            }
            return value;
        } catch (UnknownNamespaceException unknownNamespaceException) {
            throw new GameDataConfigurationException("Failed to get the key '"
                    + key + "' specified from config file",
                    unknownNamespaceException);
        } catch (IllegalArgumentException exception) {
            throw new GameDataConfigurationException("Failed to get the key '"
                    + key + "' specified from config file", exception);
        }
    }

    /**
     * <p>
     * Return the singleton instance, if the instance variable is null,
     * initialize it to call the private constructor, otherwise, return it
     * directly. This method is synchronized.
     * </p>
     * 
     * @return the singleton instance
     * @throws GameDataConfigurationException
     *             is thrown is any value is not configured properly, and it is
     *             also used to wrap exceptions from ConfigManager.
     */
    public static synchronized GameDataHelper getInstance() {
        if (instance == null) {
            instance = new GameDataHelper();
        }
        return instance;
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get an
     * array of active games. An empty array could be returned if there are no
     * active games.
     * </p>
     * 
     * @return an array of active Game objects.
     * @throws HandlerExecutionException
     *             if fail to get active games. It is used to wrap underlying
     *             exceptions.
     */
    public Game[] getActiveGames() throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game[] games;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject
                        .narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // get the active games from game data.
                games = gameData.findGames(Boolean.TRUE, Boolean.FALSE);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context
                        .lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // get the active games from game data local.
                games = gameDataLocal.findGames(Boolean.TRUE, Boolean.FALSE);
            }
            return games;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    gameDataException);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get an
     * array of games registered by the given player id. An empty array could be
     * returned if there are no registered games. NOTE: Argument check is left
     * to concrete Game Persistence Interface implementation, none would be done
     * here.
     * </p>
     * 
     * @param playerId
     *            the player id.
     * @return an array of Game objects registered by the given player.
     * @throws HandlerExecutionException
     *             if fail to get registered games. It is used to wrap
     *             underlying exceptions.
     */
    public Game[] getRegisteredGames(long playerId)
            throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game[] games;
            long[] gameids;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject
                        .narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds the game ids of the game registrations
                gameids = gameData.findGameRegistrations(playerId);
                games = new Game[gameids.length];
                for (int i = 0; i < gameids.length; i++) {
                    // get the corresponding game from persistence.
                    games[i] = gameData.getGame(gameids[i]);
                }
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context
                        .lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds the game ids of the game registrations
                gameids = gameDataLocal.findGameRegistrations(playerId);
                games = new Game[gameids.length];
                for (int i = 0; i < gameids.length; i++) {
                    // get the corresponding game from persistence.
                    games[i] = gameDataLocal.getGame(gameids[i]);
                }
            }
            return games;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException(
                    "Failed to get registered games.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException(
                    "Failed to get registered games.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException(
                    "Failed to get registered games.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException(
                    "Failed to get registered games.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException(
                    "Failed to get registered games.", gameDataException);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get the
     * corresponding Game object to the given game id.
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface
     * implementation, none would be done here.
     * </p>
     * 
     * @param gameId
     *            the game id.
     * @return the corresponding Game object.
     * @throws HandlerExecutionException
     *             if fail to get specific game. It is used to wrap underlying
     *             exceptions.
     */
    public Game getGame(long gameId) throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game game;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject
                        .narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // gets the game from persistence.
                game = gameData.getGame(gameId);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context
                        .lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // gets the game from persistence.
                game = gameDataLocal.getGame(gameId);
            }
            return game;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to get active games.",
                    castException);
        } catch (GameDataException gameDataException) {
            // ignore. this will return a null from this method.
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
        return null;
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to Finds
     * the first HostingSlot in the hosting sequence for the specified game that
     * is assigned the specified domain and has not yet been completed by the
     * specified player.
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface
     * implementation, none would be done here.
     * </p>
     * 
     * @param gameId
     *            the game id.
     * @param playerId
     *            the player (user) id.
     * @param domain
     *            the domain name.
     * @return the matched HostingSlot object.
     * @throws HandlerExecutionException
     *             if fail to find the slot. It is used to wrap underlying
     *             exceptions.
     */
    public HostingSlot findSlotForDomain(long gameId, long playerId,
            String domain) throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            HostingSlot hostingSlot;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject
                        .narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds the hosting slot for the domain based on gameid and
                // player id.
                hostingSlot = gameData.findSlotForDomain(gameId, playerId,
                        domain);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context
                        .lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds the hosting slot for the domain based on gameid and
                // player id.
                hostingSlot = gameDataLocal.findSlotForDomain(gameId, playerId,
                        domain);
            }
            return hostingSlot;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException(
                    "Failed to find slot for domain.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException(
                    "Failed to find slot for domain.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException(
                    "Failed to find slot for domain.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException(
                    "Failed to find slot for domain.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException(
                    "Failed to find slot for domain.", gameDataException);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get an
     * array of unlocked Domains, an empty array could be returned if there is
     * no such domains.
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface
     * implementation, none would be done here.
     * </p>
     * 
     * @param gameId
     *            the game id used to get unlocked domains.
     * @return an array of unlocked domains.
     * @throws HandlerExecutionException
     *             if fail to unlocked domains. It is used to wrap underlying
     *             exceptions.
     */
    public Domain[] getUnlockedDomains(long gameId)
            throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            HostingSlot[] hostingSlots;
            Domain[] domains;

            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject
                        .narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds the completed slots.
                hostingSlots = gameData.findCompletedSlots(gameId);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context
                        .lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds the completed slots.
                hostingSlots = gameDataLocal.findCompletedSlots(gameId);
            }

            domains = new Domain[hostingSlots.length];

            // gets the domains without adding duplicates based on domain id.
            for (int i = 0; i < hostingSlots.length; i++) {
                domains[i] = hostingSlots[i].getDomain();
            }

            return domains;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException(
                    "Failed to get unlocked domains.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException(
                    "Failed to get unlocked domains.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException(
                    "Failed to get unlocked domains.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException(
                    "Failed to get unlocked domains.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException(
                    "Failed to get unlocked domains.", gameDataException);
        } catch (EJBException exception) {
            throw new HandlerExecutionException(
                    "Failed to get unlocked domains.", exception);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get an
     * array of upcoming Domains, an empty array could be returned if there is
     * no such domains.
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface
     * implementation, none would be done here.
     * </p>
     * 
     * @param gameId
     *            the game id.
     * @return an array of upcoming Domain objects.
     * @throws HandlerExecutionException
     *             HandlerExecutionException if fail to upcoming domains. It is
     *             used to wrap underlying exceptions.
     */
    public Domain[] getUpcomingDomains(long gameId)
            throws HandlerExecutionException {
        try {
            Game game;
            HostingSlot[] completedSlots;
            Set completedSlotIds;

            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject
                        .narrow(object, GameDataHome.class);
                GameData gameData = gameDataHome.create();

                try {
                    // finds the completed slots for this game id.
                    completedSlots = gameData.findCompletedSlots(gameId);
                    // finds the game for this game id.
                    game = gameData.getGame(gameId);
                } finally {
                    try {
                        gameData.remove();
                    } catch (RemoteException re) {
                        // ignore it
                    } catch (RemoveException re) {
                        // ignore it
                    }
                }
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context
                        .lookup(gameDataJNDIName);
                GameDataLocal gameDataLocal = gameDataLocalHome.create();

                try {
                    // finds the completed slots for this game id.
                    completedSlots = gameDataLocal.findCompletedSlots(gameId);
                    // finds the game for this game id.
                    game = gameDataLocal.getGame(gameId);
                } finally {
                    try {
                        gameDataLocal.remove();
                    } catch (RemoveException re) {
                        // ignore it
                    }
                }
            }

            completedSlotIds = new HashSet();
            for (int i = 0; i < completedSlots.length; i++) {
                completedSlotIds.add(completedSlots[i].getId());
            }

            // sort the hosting block based on sequence number (shouldn't be
            // necessary, but it doesn't hurt)
            List temp = Arrays.asList(game.getBlocks());
            Collections.sort(temp, new SequenceNumberComparator());
            // blocks should be sorted based on the ascending order of sequence
            // number.
            HostingBlock[] hostingBlocks = (HostingBlock[]) temp
                    .toArray(new HostingBlock[temp.size()]);
            HostingBlock currentBlock = null;

            // finds the first valid block.
            find_current_block: for (int i = 0; i < hostingBlocks.length; i++) {
                HostingSlot[] hostingSlots = hostingBlocks[i].getSlots();

                for (int j = 0; j < hostingSlots.length; j++) {
                    if (hostingSlots[j].getHostingStart() != null
                            && hostingSlots[j].getHostingEnd() == null) {
                        currentBlock = hostingBlocks[i];
                        break find_current_block;
                    }
                }
            }

            if (currentBlock == null) {
                // if no block return an empty Domain array.
                return new Domain[0];
            } else {
                HostingBlock nextBlock = null;

                // search for the next block based on sequence number.
                // The blocks are in order by ascending sequence number.
                for (int i = 0; i < hostingBlocks.length; i++) {
                    if (hostingBlocks[i].getSequenceNumber() > currentBlock.getSequenceNumber()) {
                        nextBlock = hostingBlocks[i];
                        break;
                    }
                }

                // get the unique domains for all uncompleted slots for current
                // block and immediate block
                Map resultDomainMap = new HashMap();
                HostingSlot[] slots = currentBlock.getSlots();

                for (int i = 0; i < slots.length; i++) {
                    if (!completedSlotIds.contains(slots[i].getId())) {
                        Domain domain = slots[i].getDomain();

                        resultDomainMap.put(domain.getId(), domain);
                    }
                }
                if (nextBlock != null) {
                    slots = nextBlock.getSlots();
                    for (int i = 0; i < slots.length; i++) {
                        if (!completedSlotIds.contains(slots[i].getId())) {
                            Domain domain = slots[i].getDomain();

                            resultDomainMap.put(domain.getId(), domain);
                        }
                    }
                }

                List resultDomains = new ArrayList(resultDomainMap.values());

                // Perhaps this is overkill; the domains will be ordered by ID
                // hashcode as it is:
                Collections.shuffle(resultDomains);

                return (Domain[]) resultDomains
                        .toArray(new Domain[resultDomains.size()]);
            }
        } catch (NamingException namingException) {
            throw new HandlerExecutionException(
                    "Failed to get upcoming domains.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException(
                    "Failed to get upcoming domains.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException(
                    "Failed to get upcoming domains.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException(
                    "Failed to get upcoming domains.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException(
                    "Failed to get upcoming domains.", gameDataException);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to look up
     * all ongoing games in which a domain matching the specified string is a
     * host in a slot that the specified player has not yet completed, and
     * returns an array of all such games. (An empty array could be returned).
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface
     * implementation, none would be done here.
     * </p>
     * 
     * @param domain
     *            the domain name.
     * @param playerId
     *            the player id.
     * @return an array of Game objects.
     * @throws HandlerExecutionException
     *             if fail to get the matched games. It is used to wrap
     *             underlying exceptions.
     */
    public Game[] findGamesByDomain(String domain, long playerId)
            throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game[] games;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject
                        .narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds all games based on domain and player id.
                games = gameData.findGamesByDomain(domain, playerId);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context
                        .lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds all games based on domain and player id.
                games = gameDataLocal.findGamesByDomain(domain, playerId);
            }
            return games;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException(
                    "Failed to find games by domain.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException(
                    "Failed to find games by domain.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException(
                    "Failed to find games by domain.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException(
                    "Failed to find games by domain.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException(
                    "Failed to find games by domain.", gameDataException);
        } catch (EJBException exception) {
            throw new HandlerExecutionException(
                    "Failed to find games by domain.", exception);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get the
     * current leaders in a specified game, only a maximum number of leaders (as
     * specified by maxLeaders) will be returned. This method is likely to
     * return an empty array if the game has no participants or no one is
     * ranked. The order rule is defined in the LeaderBoardHandler's class doc.
     * </p>
     * <p>
     * An example is given for understanding the logic of this method. Assume
     * the game contains 3 blocks with seqNos 1, 2, 3, and each has the
     * following slots: blockSeqNo : slotSeqNos 1: 10, 11, 12 2: 4, 5, 6 3: 7,
     * 8, 9 (the slots in each block will be sorted by sequence number in
     * ascending order) So we would get a series: 11, 12, 13, 4, 5, 6, 7, 8, 9
     * for all slots at last.
     * 
     * And assume the player has completed slots: 4, 5, 8. As this player's last
     * completed slot is 8, so the contiguous subsequence is: 8, with length =
     * 1.
     * </p>
     * <p>
     * NOTE: Argument check of gameId variable is left to concrete Game
     * Persistence Interface implementation, none would be done here.
     * </p>
     * 
     * @param gameId
     *            the game id.
     * @param maxLeaders
     *            the maximum number of leaders to return.
     * @return an array of ranked player ids.
     * @throws HandlerExecutionException
     *             if fail to player ids on the leader board. It is used to wrap
     *             underlying exceptions. throws
     * @throws IllegalArgumentException
     *             if the maxLeaders is non-positive.
     */
    public long[] getLeaderBoard(long gameId, int maxLeaders)
            throws HandlerExecutionException {
        if (maxLeaders <= 0) {
            throw new IllegalArgumentException(
                    "Failed to get leader board, 'maxLeaders' is non-positive.");
        }

        GameData gameData = null;
        GameDataLocal gameDataLocal = null;

        try {
            Game game;

            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(
                        context.lookup(gameDataJNDIName), GameDataHome.class);

                gameData = gameDataHome.create();

                // gets the game for this game id.
                game = gameData.getGame(gameId);
            } else {
                GameDataLocalHome gameDataLocalHome = 
                        (GameDataLocalHome) context.lookup(gameDataJNDIName);

                gameDataLocal = gameDataLocalHome.create();

                // gets the game for this game id.
                game = gameDataLocal.getGame(gameId);
            }

            // The number of keys required, plus one for the slot that currently hosts the Ball:
            // ISV : Uncomment once the leaderboard is back to show the players with keys useful for unlocking the Ball
//            int slotBound = game.getKeyCount() + 1;

            /*
             * Assemble a list of the ids of all hosting slots that ever started hosting the Ball,
             * in DESCENDING order by block sequence number, slot sequence number
             */

            List slotIdList = new ArrayList();
            HostingBlock[] blocks = game.getBlocks();
            Comparator seqNoComparator = new SequenceNumberComparator(true);

            Arrays.sort(blocks, seqNoComparator);
            for (int blockIndex = 0; blockIndex < blocks.length; blockIndex++) {
                HostingSlot[] slots = blocks[blockIndex].getSlots();

                Arrays.sort(slots, seqNoComparator);
                for (int slotIndex = 0; slotIndex < slots.length; slotIndex++) {
                    if (slots[slotIndex].getHostingStart() != null) {
                        slotIdList.add(slots[slotIndex].getId());
                    }
                }
            }

            // Consider only those slots whose keys matter
            // ISV : Uncomment once the leaderboard is back to show the players with keys useful for unlocking the Ball
//            if (slotIdList.size() > slotBound) {
//                slotIdList = slotIdList.subList(0, slotBound);
//            }

            /*
             * Process slot completions from each slot, collecting information on up to the
             * maximum number of players (and perhaps a few more)
             */

            // Maps from Long player IDs to PlayerStatistics objects
            Map contiguousSlotPlayerMap= new HashMap();
            Map thisSlotPlayerMap = new HashMap();
            Map otherPlayerMap = new HashMap();

            // A List of PlayerStatistics objects into which the leaders' statistics are accumulated
            List leaderList = new ArrayList();

            /*
             * For each slot, in the order it appears in the slotIdList, the index into the leader
             * list of the first player for which that slot might have been completed; the next
             * array element can be viewed as the index into the leader list of the first player
             * to not have completed the slot, thus consecutive pairs of entries form the bounds
             * of sublists of the leader list within which all the players' slot completions closest
             * to the ball are on the same slot:
             */
            int[] leadingSlotIndices = new int[slotIdList.size() + 1];

            // A flag by which to recognize when we no longer need to consider additional potential leaders
            boolean allowAdditionalLeaders = true;

            // iterate over the slots we care about
            for (int slotIdIndex = 0; slotIdIndex < slotIdList.size(); slotIdIndex++) {

                // The ID of the slot to consider
                long slotId = ((Long) slotIdList.get(slotIdIndex)).longValue();

                // find the slot completions for the game id and hosting slot id

                SlotCompletion[] completions;

                if (useRemoteHome) {
                    completions = gameData.findSlotCompletions(gameId, slotId);
                } else {
                    completions = gameDataLocal.findSlotCompletions(gameId, slotId);
                }

                // Process the completions for this slot

                thisSlotPlayerMap.clear();
                leadingSlotIndices[slotIdIndex] = leaderList.size();

                for (int compIndex = 0; compIndex < completions.length; compIndex++) {
                    SlotCompletion completion = completions[compIndex];
                    Long playerId = new Long(completion.getPlayerId());

                    if (contiguousSlotPlayerMap.containsKey(playerId)) {
                        // a player who completed the previous slot as well, and is on his first contiguous run of slots
                        PlayerStatistics stats = (PlayerStatistics) contiguousSlotPlayerMap.remove(playerId);

                        stats.incrementCompletions(completion.getTimestamp());
                        thisSlotPlayerMap.put(playerId, stats);
                    } else if (otherPlayerMap.containsKey(playerId)) {
                        // a player who is not on a contiguous run, but who has keys we care about
                        PlayerStatistics stats = (PlayerStatistics) otherPlayerMap.get(playerId);

                        stats.updateCompletionDate(completion.getTimestamp());
                    } else if (allowAdditionalLeaders) {
                        // the first completion we've processed for this player
                        PlayerStatistics stats = new PlayerStatistics(playerId, completion.getTimestamp());

                        thisSlotPlayerMap.put(playerId, stats);
                        leaderList.add(stats);
                    }
                }

                /*
                 * The remaining contents of the contiguousSlotPlayerMap represent players who didn't complete
                 * the current slot.  Add them to the otherPlayerMap to so indicate.
                 */
                otherPlayerMap.putAll(contiguousSlotPlayerMap);

                /*
                 * The players (still) on a contiguous run are exactly those in thisSlotPlayerMap, so just
                 * swap that Map with contiguousSlotPlayerMap
                 */
                Map tempMap = contiguousSlotPlayerMap;

                contiguousSlotPlayerMap = thisSlotPlayerMap;
                thisSlotPlayerMap = tempMap;  // will be cleared near the beginning of the next iteration

                if (leaderList.size() >= maxLeaders) {

                    /*
                     * We have already identified all the players who can show up on the leader board; raise a flag
                     * to remind us that we don't need to consider any further players that we see for the first time.
		     * We do this once at the end of each iteration, because we don't know until later the relative
		     * rankings of the players whose contiguous completions end at the same slot.
                     */
                    allowAdditionalLeaders = false;
                }
            }

            // initialize the last element of leadingSlotIndices
            leadingSlotIndices[slotIdList.size()] = leaderList.size();

            // sort those sublists of the leader list in which all leaders' closest completion to the ball is the same;
            // those sublists are already block-wise in the correct order.  An insertion sort might actually be better
	    // for this case than the k merge sorts we're about to do, as insertion sort is particularly effective when
	    // the input is already sorted into reasonably small blocks
            for (int sublistIndex = 0; sublistIndex < (leadingSlotIndices.length - 1); sublistIndex++) {
                Collections.sort(leaderList.subList(
                        leadingSlotIndices[sublistIndex], leadingSlotIndices[sublistIndex + 1]));
            }

            // Take at most maxLeaders
            if (leaderList.size() > maxLeaders) {
                leaderList = leaderList.subList(0, maxLeaders);
            }

            // get the player ids of leaders and return

            long[] leaders = new long[leaderList.size()];

            for (int i = 0; i < leaders.length; i++) {
                leaders[i] = ((PlayerStatistics) leaderList.get(i)).getPlayerId().longValue();
            }

            return leaders;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to get leader board.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get leader board.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get leader board.", createException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException("Failed to get leader board.", gameDataException);
        } catch (EJBException exception) {
            throw new HandlerExecutionException("Failed to get leader board.", exception);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * If the context is null, it will get the context.
     * </p>
     * 
     * @return the context
     * @throws GameDataConfigurationException
     *             if the name is not found/configured.
     */
    private Context getContext() {
        try {
            Context ctx = JNDIUtils.getContext(jndiContextName);
            return ctx;
        } catch (NamingException e) {
            throw new GameDataConfigurationException(
                    "Failed to get the context for the context name '"
                            + jndiContextName + "'.", e);
        } catch (ConfigManagerException e) {
            throw new GameDataConfigurationException(
                    "Failed to get the context for the context name '"
                            + jndiContextName + "'.", e);
        }
    }

    /**
     * <p>
     * Removes the object from the session.
     * </p>
     * 
     * @param gameData
     *            GameData object.
     * @param gameDataLocal
     *            GameDataLocal object.
     */
    private void removeGameData(GameData gameData, GameDataLocal gameDataLocal) {
        try {
            if (gameData != null) {
                gameData.remove();
            }
            if (gameDataLocal != null) {
                gameDataLocal.remove();
            }
        } catch (RemoteException e) {
            // ignore
        } catch (RemoveException e) {
            // ignore
        }
    }

    /**
     * A nested utility class used in computing statistics for the leader board
     */
    private static class PlayerStatistics implements Comparable {
        
        /**
         * The ID of the player to whome this <code>PlayerStatistics</code> pertains
         */
        private final Long playerId;

        /**
         * The number of contiguous slots this player has completed, ending at the one closest to the Ball
         */
        private int contiguousSlotCount;

        /**
         * The date of the player's most recent slot completion, among those slots considered
         */
        private Date lastCompletionDate;

        /**
         * Initializes a new <code>PlayerStatistics</code> for the specified ID and slot completion date.
         * The contiguous slot count is initialized to 1.
         *
         * @param  playerId the <code>Long</code> ID of the player to which these statistics pertain
         * @param  completionDate the <code>Date</code> of a slot completion attributed to the specified
         *         player
         */
        public PlayerStatistics(Long playerId, Date completionDate) {
            this.playerId = playerId;
            contiguousSlotCount = 1;
            lastCompletionDate = completionDate;
        }

        /**
         * Retrieves the player ID to which these statistics pertain
         *
         * @return the <code>Long</code> player ID
         */
        public Long getPlayerId() {
            return playerId;
        }

        /**
         * Increments the count of contiguous completions comprised by these statistics to account for
	 * a completion time stamped with the specified <code>Date</code>.
	 *
	 * @param timestamp the <code>Date</code> of the completion to account for
         */
        public void incrementCompletions(Date timestamp) {
            contiguousSlotCount++;
	    updateCompletionDate(timestamp);
        }

        /**
         * (Possibly) updates the recorded latest completion timestamp in these statistics;  if the
         * specified candidate date is later than the currently recorded one then it replaces the current one,
         * otherwise the current one is retained
         *
         * @param  timestamp the candidate <code>Date</code>
         */
        public void updateCompletionDate(Date timestamp) {
            if (timestamp.after(lastCompletionDate)) {
                lastCompletionDate = timestamp;
            }
        }

        /**
         * An implementation method of the <code>Comparable</code> interface; compares this object to the
         * specified one, and returns an integer less than, equal to, or greater than zero as this object
         * comes before, at the same place, or after the other object in this class's natural order.  Note
         * that this represents a <em>partial</em> order, not a total order.  Both for that reason and because
         * this class does not override <code>Object.equals(Object)</code>, this order is inconsistent with
         * equals.
         *
         * @param o the <code>Object</code> to compare to this one
         *
         * @throws ClassCastException if o is not an instance of <code>PlayerStatistics</code>
         * @throws NullPointerException is o is <code>null</code>
         */
        public int compareTo(Object o) {
            PlayerStatistics other = (PlayerStatistics) o;

            if (this.contiguousSlotCount > other.contiguousSlotCount) {
                return -1;
            } else if (this.contiguousSlotCount < other.contiguousSlotCount) {
                return 1;
            } else {
                return this.lastCompletionDate.compareTo(other.lastCompletionDate);
            }
        }
    }

    /**
     * A <code>Comparator</code> to compare the sequence numbers of
     * <code>HostingBlock</code> or <code>HostingSlot</code>.
     * 
     * @author mittu
     * @version 1.0
     */
    private static class SequenceNumberComparator implements Comparator {

	/**
	 * An int multiplier for the return value of {@link #compare(Object, Object)}, used to reverse
	 * the default order imposed by this <code>Comparator</code> if so configured
	 */
	private final int order;

	/**
	 * Initializes this <code>SequenceNumberComparator</code> to impose its default
	 * (ascending) order
	 */
        public SequenceNumberComparator() {
            this(false);
	}

	/**
	 * Initializes this <code>SequenceNumberComparator</code> to impose either its
	 * default order (ascending) or the reverse, as indicated by the argument
         *
	 * @param  reverseOrder <code>true</code> if the order should be the reverse
	 *         of the default order
	 */
	public SequenceNumberComparator(boolean reverseOrder) {
            order = (reverseOrder ? -1 : 1);
	}

        /**
         * Compare the objects based on sequence number.
         * 
         * @param o1
         *            object 1 to compare.
         * @param o2
         *            object 2 to compare.
         * @return result of comparison, will be always the difference of
         *         sequence numbers.
         */
        public int compare(Object o1, Object o2) {
            if (o1 instanceof HostingBlock) {
                HostingBlock block1 = (HostingBlock) o1;
                HostingBlock block2 = (HostingBlock) o2;

                return order * (block1.getSequenceNumber() - block2.getSequenceNumber());
            } else {
                HostingSlot slot1 = (HostingSlot) o1;
                HostingSlot slot2 = (HostingSlot) o2;

                return order * (slot1.getSequenceNumber() - slot2.getSequenceNumber());
            }
        }
    }
}


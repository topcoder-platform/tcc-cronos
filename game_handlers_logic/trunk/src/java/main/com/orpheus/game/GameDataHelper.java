/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

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


/**
 * <p>
 * <code>GameDataHelper</code> class encapsulates all EJB calls to the Game Data Persistence Interface, so that the
 * handler implementations don't have to interact with the EJB interfaces directly, which makes them simpler and easier
 * to maintain. Several configurable values are supported by this class through the <code>ConfigManager</code>
 * component in order to make it more flexible: The use_remote_home configurable property is used to control whether to
 * use remote or local home interface of Game Data Persistence EJB. The game_data_jndi_name configurable property is the
 * jndi name used to lookup the home interface object. And the jndi_context_name is used by the JNDIUtility component,
 * so that we can make the created InitialContext more configurable.
 *
 * Thread-safety: All variables are immutable, all methods call methods from EJB interface, and no state is maintained.
 * As the Game Data Interface component is thread-safety, so this class is thread-safe too.
 * </p>
 *
 * @author Standlove, mittu
 * @version 1.0
 */
class GameDataHelper {
    /**
     * <p>
     * Represents the namespace used to load configuration values from the <code>ConfigManager</code>. It is
     * initialized in place with <code>GameDataHelper</code> class' full qualified name, and never changed afterwards.
     * Used in static initializer.
     * </p>
     */
    private static final String NAMESPACE = GameDataHelper.class.getName();

    /**
     * <p>
     * Represents the singleton instance, initialized in the getInstance only once, and never changed afterwards.
     * </p>
     */
    private static GameDataHelper instance;

    /**
     * <p>
     * Represents a flag indicating either the remote interface or local interface of the persistence component should
     * be used. It is initialized in the constructor, and never changed afterwards. It is used in all methods to
     * determine which interface to use. If it is not configured (through ConfigManager), it is default to true - remote
     * interface is used.
     * </p>
     */
    private final boolean useRemoteHome;

    /**
     * <p>
     * Represents the jndi name to lookup the Game Persistence's local interface or remote interface. It is initialized
     * in the constructor, and never changed afterwards. It must be non-null, non-empty string. It is used in all
     * methods to get the local interface or remote interface depending on the useRemoteHome's value.
     * </p>
     */
    private final String gameDataJNDIName;

    /**
     * <p>
     * Represents the context name used to get the <code>Context</code> object from the JNDIUtils class. It is
     * initialized in the constructor, and never changed afterwards. It must be non-null, non-empty string. It is
     * default to "default" if not configured (through ConfigManager). It is used in all methods to get the Context
     * object used to lookup the game data's local or remote interface.
     * </p>
     */
    private final String jndiContextName;

    /**
     * <p>
     * Represents the cached context which is used to get the ebj object. The JNDI lookup maybe slow, anyway it speeds
     * thing up if we cache the result.
     * </p>
     */
    private final Context context;

    /**
     * <p>
     * Initialize all static variables to ensure methods in this helper class work well.
     * </p>
     *
     * @throws GameDataConfigurationException
     *             if any value is not configured properly, wraps exceptions from ConfigManager.
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
     * Helper method to get the key value of the specified key from the <code>ConfigManager</code>. If any error
     * happens <code>GameDataConfigurationException</code> will be thrown.
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
            String value = ConfigManager.getInstance().getString(NAMESPACE, key);
            if (!optional) {
                ImplementationHelper.checkStringValid(value, key);
            }
            return value;
        } catch (UnknownNamespaceException unknownNamespaceException) {
            throw new GameDataConfigurationException("Failed to get the key '" + key + "' specified from config file",
                    unknownNamespaceException);
        } catch (IllegalArgumentException exception) {
            throw new GameDataConfigurationException("Failed to get the key '" + key + "' specified from config file",
                    exception);
        }
    }

    /**
     * <p>
     * Return the singleton instance, if the instance variable is null, initialize it to call the private constructor,
     * otherwise, return it directly. This method is synchronized.
     * </p>
     *
     * @return the singleton instance
     * @throws GameDataConfigurationException
     *             is thrown is any value is not configured properly, and it is also used to wrap exceptions from
     *             ConfigManager.
     */
    public static synchronized GameDataHelper getInstance() {
        if (instance == null) {
            instance = new GameDataHelper();
        }
        return instance;
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get an array of active games. An empty array
     * could be returned if there are no active games.
     * </p>
     *
     * @return an array of active Game objects.
     * @throws HandlerExecutionException
     *             if fail to get active games. It is used to wrap underlying exceptions.
     */
    public Game[] getActiveGames() throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game[] games;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // get the active games from game data.
                games = gameData.findGames(Boolean.TRUE, null);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context.lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // get the active games from game data local.
                games = gameDataLocal.findGames(Boolean.TRUE, null);
            }
            return games;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to get active games.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get active games.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get active games.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to get active games.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException("Failed to get active games.", gameDataException);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get an array of games registered by the given
     * player id. An empty array could be returned if there are no registered games. NOTE: Argument check is left to
     * concrete Game Persistence Interface implementation, none would be done here.
     * </p>
     *
     * @param playerId
     *            the player id.
     * @return an array of Game objects registered by the given player.
     * @throws HandlerExecutionException
     *             if fail to get registered games. It is used to wrap underlying exceptions.
     */
    public Game[] getRegisteredGames(long playerId) throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game[] games;
            long[] gameids;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds the game ids of the game registrations
                gameids = gameData.findGameRegistrations(playerId);
                games = new Game[gameids.length];
                for (int i = 0; i < gameids.length; i++) {
                    // get the corresponding game from persistence.
                    games[i] = gameData.getGame(gameids[i]);
                }
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context.lookup(gameDataJNDIName);
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
            throw new HandlerExecutionException("Failed to get registered games.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get registered games.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get registered games.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to get registered games.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException("Failed to get registered games.", gameDataException);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get the corresponding Game object to the given
     * game id.
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface implementation, none would be done here.
     * </p>
     *
     * @param gameId
     *            the game id.
     * @return the corresponding Game object.
     * @throws HandlerExecutionException
     *             if fail to get specific game. It is used to wrap underlying exceptions.
     */
    public Game getGame(long gameId) throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game game;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // gets the game from persistence.
                game = gameData.getGame(gameId);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context.lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // gets the game from persistence.
                game = gameDataLocal.getGame(gameId);
            }
            return game;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to get active games.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get active games.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get active games.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to get active games.", castException);
        } catch (GameDataException gameDataException) {
            // ignore. this will return a null from this method.
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
        return null;
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to Finds the first HostingSlot in the hosting
     * sequence for the specified game that is assigned the specified domain and has not yet been completed by the
     * specified player.
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface implementation, none would be done here.
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
     *             if fail to find the slot. It is used to wrap underlying exceptions.
     */
    public HostingSlot findSlotForDomain(long gameId, long playerId, String domain) throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            HostingSlot hostingSlot;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds the hosting slot for the domain based on gameid and player id.
                hostingSlot = gameData.findSlotForDomain(gameId, playerId, domain);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context.lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds the hosting slot for the domain based on gameid and player id.
                hostingSlot = gameDataLocal.findSlotForDomain(gameId, playerId, domain);
            }
            return hostingSlot;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to find slot for domain.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to find slot for domain.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to find slot for domain.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to find slot for domain.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException("Failed to find slot for domain.", gameDataException);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get an array of unlocked Domains, an empty array
     * could be returned if there is no such domains.
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface implementation, none would be done here.
     * </p>
     *
     * @param gameId
     *            the game id used to get unlocked domains.
     * @return an array of unlocked domains.
     * @throws HandlerExecutionException
     *             if fail to unlocked domains. It is used to wrap underlying exceptions.
     */
    public Domain[] getUnlockedDomains(long gameId) throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Set domainIds = new HashSet();
            List domains = new ArrayList();
            HostingSlot[] hostingSlots;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds the completed slots.
                hostingSlots = gameData.findCompletedSlots(gameId);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context.lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds the completed slots.
                hostingSlots = gameDataLocal.findCompletedSlots(gameId);
            }
            // gets the domains without adding duplicates based on domain id.
            for (int i = 0; i < hostingSlots.length; i++) {
                if (!domainIds.contains(hostingSlots[i].getDomain().getId())) {
                    domainIds.add(hostingSlots[i].getDomain().getId());
                    domains.add(hostingSlots[i].getDomain());
                }
            }
            return (Domain[]) domains.toArray(new Domain[domains.size()]);
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to get unlocked domains.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get unlocked domains.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get unlocked domains.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to get unlocked domains.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException("Failed to get unlocked domains.", gameDataException);
        } catch (EJBException exception) {
            throw new HandlerExecutionException("Failed to get unlocked domains.", exception);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get an array of upcoming Domains, an empty array
     * could be returned if there is no such domains.
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface implementation, none would be done here.
     * </p>
     *
     * @param gameId
     *            the game id.
     * @return an array of upcoming Domain objects.
     * @throws HandlerExecutionException
     *             HandlerExecutionException if fail to upcoming domains. It is used to wrap underlying exceptions.
     */
    public Domain[] getUpcomingDomains(long gameId) throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game game;
            HostingSlot[] completedSlots;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds the completed slots for this game id.
                completedSlots = gameData.findCompletedSlots(gameId);
                // finds the game for this game id.
                game = gameData.getGame(gameId);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context.lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds the completed slots for this game id.
                completedSlots = gameDataLocal.findCompletedSlots(gameId);
                // finds the game for this game id.
                game = gameDataLocal.getGame(gameId);
            }
            HostingBlock currentBlock = null;
            HostingBlock[] hostingBlocks = game.getBlocks();
            // finds the first valid block.
            for (int i = 0; i < hostingBlocks.length; i++) {
                HostingSlot currentSlot = null;
                HostingSlot[] hostingSlots = hostingBlocks[i].getSlots();
                for (int j = 0; j < hostingSlots.length; j++) {
                    if (hostingSlots[i].getHostingStart() != null && hostingSlots[i].getHostingEnd() == null) {
                        currentSlot = hostingSlots[i];
                        break;
                    }
                }
                if (currentSlot != null) {
                    currentBlock = hostingBlocks[i];
                    break;
                }
            }
            if (currentBlock == null) {
                // if no block return an empty Domain array.
                return new Domain[0];
            } else {
                Domain[] domains;
                HostingBlock immediateBlock = null;
                // sort the hosting block based on sequence number
                List temp = Arrays.asList(hostingBlocks);
                Collections.sort(temp, new SequenceNumberComparator());
                // blocks should be sorted based on the ascending order of sequence number.
                hostingBlocks = (HostingBlock[]) temp.toArray(new HostingBlock[temp.size()]);

                // search for the next immediate block based on sequence number.
                for (int i = 0; i < hostingBlocks.length; i++) {
                    if (hostingBlocks[i].getSequenceNumber() > currentBlock.getSequenceNumber()) {
                        immediateBlock = hostingBlocks[i];
                        break;
                    }
                }
                // gets all slots for current block and immediate block as result slots.
                List resultSlots = new ArrayList();
                HostingSlot[] slots = currentBlock.getSlots();
                for (int i = 0; i < slots.length; i++) {
                    resultSlots.add(slots[i]);
                }
                if (immediateBlock != null) {
                    slots = immediateBlock.getSlots();
                    for (int i = 0; i < slots.length; i++) {
                        resultSlots.add(slots[i]);
                    }
                }
                // remove the completed slots from the result slots.
                for (int i = 0; i < completedSlots.length; i++) {
                    resultSlots.remove(completedSlots[i]);
                }
                // gets the domain from the result slots and return.
                domains = new Domain[resultSlots.size()];
                int idx = 0;
                for (Iterator iter = resultSlots.iterator(); iter.hasNext();) {
                    HostingSlot slot = (HostingSlot) iter.next();
                    domains[idx++] = slot.getDomain();
                }
                return domains;
            }
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to get upcoming domains.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get upcoming domains.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get upcoming domains.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to get upcoming domains.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException("Failed to get upcoming domains.", gameDataException);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to look up all ongoing games in which a domain
     * matching the specified string is a host in a slot that the specified player has not yet completed, and returns an
     * array of all such games. (An empty array could be returned).
     * </p>
     * <p>
     * NOTE: Argument check is left to concrete Game Persistence Interface implementation, none would be done here.
     * </p>
     *
     * @param domain
     *            the domain name.
     * @param playerId
     *            the player id.
     * @return an array of Game objects.
     * @throws HandlerExecutionException
     *             if fail to get the matched games. It is used to wrap underlying exceptions.
     */
    public Game[] findGamesByDomain(String domain, long playerId) throws HandlerExecutionException {
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            Game[] games;
            // check whether to use remote look up.
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds all games based on domain and player id.
                games = gameData.findGamesByDomain(domain, playerId);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context.lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds all games based on domain and player id.
                games = gameDataLocal.findGamesByDomain(domain, playerId);
            }
            return games;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to find games by domain.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to find games by domain.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to find games by domain.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to find games by domain.", castException);
        } catch (GameDataException gameDataException) {
            throw new HandlerExecutionException("Failed to find games by domain.", gameDataException);
        } catch (EJBException exception) {
            throw new HandlerExecutionException("Failed to find games by domain.", exception);
        } finally {
            removeGameData(gameData, gameDataLocal);
        }
    }

    /**
     * <p>
     * This method uses the APIs from the Game Persistence component to get the current leaders in a specified game,
     * only a maximum number of leaders (as specified by maxLeaders) will be returned. This method is likely to return
     * an empty array if the game has no participants or no one is ranked. The order rule is defined in the
     * LeaderBoardHandler's class doc.
     * </p>
     * <p>
     * An example is given for understanding the logic of this method. Assume the game contains 3 blocks with seqNos 1,
     * 2, 3, and each has the following slots: blockSeqNo : slotSeqNos 1: 10, 11, 12 2: 4, 5, 6 3: 7, 8, 9 (the slots in
     * each block will be sorted by sequence number in ascending order) So we would get a series: 11, 12, 13, 4, 5, 6,
     * 7, 8, 9 for all slots at last.
     *
     * And assume the player has completed slots: 4, 5, 8. As this player's last completed slot is 8, so the contiguous
     * subsequence is: 8, with length = 1.
     * </p>
     * <p>
     * NOTE: Argument check of gameId variable is left to concrete Game Persistence Interface implementation, none would
     * be done here.
     * </p>
     *
     * @param gameId
     *            the game id.
     * @param maxLeaders
     *            the maximum number of leaders to return.
     * @return an array of ranked player ids.
     * @throws HandlerExecutionException
     *             if fail to player ids on the leader board. It is used to wrap underlying exceptions. throws
     * @throws IllegalArgumentException
     *             if the maxLeaders is non-positive.
     */
    public long[] getLeaderBoard(long gameId, int maxLeaders) throws HandlerExecutionException {
        if (maxLeaders <= 0) {
            throw new IllegalArgumentException("Failed to get leader board, 'maxLeaders' is non-positive.");
        }
        GameData gameData = null;
        GameDataLocal gameDataLocal = null;
        try {
            HostingSlot[] hostingSlots;
            Game game;
            // local variable to store the player id and list of hosting slots for that player.
            Map playerSlots = new HashMap();
            // local variable to store the player id and the PlayerInfo.
            Map playerInfos = new HashMap();
            if (useRemoteHome) {
                Object object = context.lookup(gameDataJNDIName);
                GameDataHome gameDataHome = (GameDataHome) PortableRemoteObject.narrow(object, GameDataHome.class);
                gameData = gameDataHome.create();
                // finds the completed slots from the game data.
                hostingSlots = gameData.findCompletedSlots(gameId);
                // gets the game for this game id.
                game = gameData.getGame(gameId);
            } else {
                GameDataLocalHome gameDataLocalHome = (GameDataLocalHome) context.lookup(gameDataJNDIName);
                gameDataLocal = gameDataLocalHome.create();
                // finds the completed slots from the game data.
                hostingSlots = gameDataLocal.findCompletedSlots(gameId);
                // gets the game for this game id.
                game = gameDataLocal.getGame(gameId);
            }
            // iterate through the hosting slots.
            for (int i = 0; i < hostingSlots.length; i++) {
                if (hostingSlots[i].getId() == null) {
                    throw new HandlerExecutionException(
                            "Failed to get leader board. HostingSlots with null ids present.");
                }
                // finds the slot completions for the game id and current hosting slots id.
                SlotCompletion[] completions;
                if (useRemoteHome) {
                    completions = gameData.findSlotCompletions(gameId, hostingSlots[i].getId().longValue());
                } else {
                    completions = gameDataLocal.findSlotCompletions(gameId, hostingSlots[i].getId().longValue());
                }
                // iterates through the slot completions.
                for (int j = 0; j < completions.length; j++) {
                    // key is the player id.
                    Long key = new Long(completions[j].getPlayerId());
                    if (playerSlots.containsKey(key)) {
                        // if the player id already exists, add this hosting slot too.
                        ((List) playerSlots.get(key)).add(hostingSlots[i]);
                        // get the player info.
                        PlayerInfo playerInfo = (PlayerInfo) playerInfos.get(key);
                        // if the current hosting slot is the latest set it.
                        if (playerInfo.getLatestSlot() == null
                                || hostingSlots[i].getHostingStart()
                                        .after(playerInfo.getLatestSlot().getHostingStart())) {
                            playerInfo.setLatestSlot(hostingSlots[i]);
                            playerInfo.setLatestSlotCompletion(completions[i]);
                        }
                    } else {
                        // new entry into the maps.
                        List list = new ArrayList();
                        list.add(hostingSlots[i]);
                        // add the player id and hosting slot list.
                        playerSlots.put(key, list);
                        PlayerInfo playerInfo = new PlayerInfo(key.longValue());
                        playerInfo.setLatestSlot(hostingSlots[i]);
                        playerInfo.setLatestSlotCompletion(completions[i]);
                        // add the player id and player info.
                        playerInfos.put(key, playerInfo);
                    }
                }
            }
            // gets the hosting blocks for the given game.
            HostingBlock[] blocks = game.getBlocks();
            List temp = Arrays.asList(blocks);
            Collections.sort(temp, new SequenceNumberComparator());
            // blocks should be sorted based on the ascending order of sequence number.
            blocks = (HostingBlock[]) temp.toArray(new HostingBlock[temp.size()]);
            // store the sorted sequence numbers as a list.
            List slotSeqNos = new ArrayList();
            for (int i = 0; i < blocks.length; i++) {
                slotSeqNos.add(new Integer(blocks[i].getSequenceNumber()));
            }
            // iterates through the hosting blocks.
            for (int i = 0; i < blocks.length; i++) {
                // gets the hosting slots for this block.
                HostingSlot[] slots = blocks[i].getSlots();
                temp = Arrays.asList(slots);
                Collections.sort(temp, new SequenceNumberComparator());
                // slots should be sorted based on sequence number.
                slots = (HostingSlot[]) temp.toArray(new HostingSlot[temp.size()]);
                // add the sequence numbers to the sequence number list too.
                for (int j = 0; j < slots.length; j++) {
                    slotSeqNos.add(new Integer(slots[j].getSequenceNumber()));
                }
            }
            Set players = playerSlots.keySet();
            // iterates through the player slots map.
            for (Iterator iter = players.iterator(); iter.hasNext();) {
                Long playerId = (Long) iter.next();
                // gets the list of hosting slots for a particular player.
                List hostingSlotList = (List) playerSlots.get(playerId);
                // iterate backwards to get a matching sequence number in slot sequence number list.
                for (int k = slotSeqNos.size() - 1; k >= 0; k--) {
                    Integer seqNumber = (Integer) slotSeqNos.get(k);
                    boolean found = false;
                    // search in the hosting slot list.
                    for (Iterator iterator = hostingSlotList.iterator(); iterator.hasNext();) {
                        HostingSlot currSlot = (HostingSlot) iterator.next();
                        if (currSlot.getSequenceNumber() == seqNumber.intValue()) {
                            // if match get the contiguous sequence count from the match.
                            found = true;
                            int count = 1;
                            int prev = seqNumber.intValue();
                            k--;
                            for (int p = k; p >= 0; p--, k--) {
                                seqNumber = (Integer) slotSeqNos.get(p);
                                if (prev - seqNumber.intValue() == 1) {
                                    count++;
                                } else {
                                    break;
                                }
                            }
                            // gets the player info from the player infos map.
                            PlayerInfo playerInfo = (PlayerInfo) playerInfos.get(playerId);
                            // sets the count.
                            playerInfo.setContiguousSlotsCount(count);
                            break;
                        }
                    }
                    if (found) {
                        // stop the search for this player.
                        break;
                    }
                }
            }
            // create a list of player info alone from the player infos map.
            List playerInfo = new ArrayList();
            for (Iterator iter = players.iterator(); iter.hasNext();) {
                Long playerId = (Long) iter.next();
                playerInfo.add(playerInfos.get(playerId));
            }
            // sort the player info list based on the rules.
            Collections.sort(playerInfo, new PlayerInfoComparator());
            // limit the list to max leaders.
            if (playerInfo.size() > maxLeaders) {
                playerInfo = playerInfo.subList(0, maxLeaders);
            }
            // get the player id of leaders and return.
            long[] leaders = new long[playerInfo.size()];
            for (int i = 0; i < leaders.length; i++) {
                leaders[i] = ((PlayerInfo) playerInfo.get(i)).getPlayerId();
            }
            return leaders;
        } catch (NamingException namingException) {
            throw new HandlerExecutionException("Failed to get leader board.", namingException);
        } catch (RemoteException remoteException) {
            throw new HandlerExecutionException("Failed to get leader board.", remoteException);
        } catch (CreateException createException) {
            throw new HandlerExecutionException("Failed to get leader board.", createException);
        } catch (ClassCastException castException) {
            throw new HandlerExecutionException("Failed to get leader board.", castException);
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
            throw new GameDataConfigurationException("Failed to get the context for the context name '"
                    + jndiContextName + "'.", e);
        } catch (ConfigManagerException e) {
            throw new GameDataConfigurationException("Failed to get the context for the context name '"
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
     * <p>
     * PlayerInfo class is a private inner static class of <code>GameDataHelper</code> class. It encapsulates
     * necessary attributes used to sort players on the leader board. This class will only be used in GameDataHelper's
     * getLeaderBoard method, and it can be sorted by the <code>PlayerInfoComparator</code> class.
     *
     * Thread-safety: this class is mutable, so it is not thread-safe, but it is used in a thread-safe way by
     * GameDataHelper, since <code>PlayerInfo</code> objects are never shared in different threads.
     * </p>
     *
     * @author Standlove, mittu
     * @version 1.0
     */
    private static class PlayerInfo {

        /**
         * <p>
         * Represents the latest completed HostingSlot by current user. It is null initially, and it has both getter and
         * setter to access or change this attribute. It is invalid to assign null value to it in its setter.
         * </p>
         */
        private HostingSlot latestSlot;

        /**
         * <p>
         * Represents the count of last contiguous completed slots for this player. It is initialized to 0, and has both
         * getter and setter to access or change this attribute. It is invalid to assign non-positive value to it in the
         * setter.
         * </p>
         */
        private int contiguousSlotsCount;

        /**
         * <p>
         * Represents the SlotCompletion for the latest completed slot of this player. It is null initially, and has
         * both getter and setter to access or change this attribute. It is invalid to set it to null in its setter.
         * </p>
         */
        private SlotCompletion latestSlotCompletion;

        /**
         * <p>
         * Represents the player id used to uniquely identify a player. It is initialized in constructor, and never
         * changed afterwards. It could be any possible long value. And it has a getter to access this attribute.
         * </p>
         */
        private final long playerId;

        /**
         * <p>
         * Constructor with the player id. Simply assign the argument to this.playerId. The argument could be any long
         * value.
         * </p>
         *
         * @param playerId
         *            the player id.
         */
        public PlayerInfo(long playerId) {
            this.playerId = playerId;
        }

        /**
         * <p>
         * Return the player id.
         * </p>
         *
         * @return the player id.
         */
        public long getPlayerId() {
            return playerId;
        }

        /**
         * <p>
         * Get the latest completed slot. Simply return this.latestSlot. The returned value could be null if it is not
         * set.
         * </p>
         *
         * @return the latest completed slot.
         */
        public HostingSlot getLatestSlot() {
            return latestSlot;
        }

        /**
         * <p>
         * Set the latest completed slot.
         * </p>
         *
         * @param slot
         *            the HostingSlot object to set.
         * @throws IllegalArgumentException
         *             if the given argument is null.
         */
        public void setLatestSlot(HostingSlot slot) {
            ImplementationHelper.checkObjectValid(slot, "slot");
            latestSlot = slot;
        }

        /**
         * <p>
         * Return the count of contiguous completed slots for this player.
         * </p>
         *
         * @return the count of contiguous completed slots for this player.
         */
        public int getContiguousSlotsCount() {
            return contiguousSlotsCount;
        }

        /**
         * <p>
         * Set the count of contiguous completed slots for this player.
         * </p>
         *
         * @param count
         *            the count of contiguous completed slots for this player.
         * @throws IllegalArgumentException
         *             if the given argument is non-positive value.
         */
        public void setContiguousSlotsCount(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("The parameter 'count' should not be non-positive.");
            }
            contiguousSlotsCount = count;
        }

        /**
         * <p>
         * Return the the SlotCompletion for the latest completed slot of this player.
         * </p>
         *
         * @return the SlotCompletion for the latest completed slot of this player.
         */
        public SlotCompletion getLatestSlotCompletion() {
            return latestSlotCompletion;
        }

        /**
         * <p>
         * Set the SlotCompletion for the latest completed slot of this player.
         * </p>
         *
         * @param completion
         *            the SlotCompletion for the latest completed slot of this player.
         * @throws IllegalArgumentException
         *             if the given argument is null.
         */
        public void setLatestSlotCompletion(SlotCompletion completion) {
            ImplementationHelper.checkObjectValid(completion, "completion");
            latestSlotCompletion = completion;
        }
    }

    /**
     * <p>
     * <code>PlayerInfoComparator</code> class implements the <code>Comparator</code> interface, it is a private
     * inner static class of <code>GameDataHelper</code> class. This class is used to sort the <code>PlayerInfo</code>
     * objects in ascending order according the rules defined in req spec. The order will be determined according to the
     * following criteria, listed from highest priority to lowest:
     * <ol>
     * <li>Compare the hostingStart date of PlayerInfo's latest slot, <code>PlayerInfo</code> object with larger
     * hostingStart date value should be before the one with smaller value.</li>
     * <li>Compare the contiguousSlotCount of PlayerInfo object, the object with larger value should be before the one
     * with smaller value.</li>
     * <li>Compare the timestamp of the PlayerInfo's latest slotCompletion, the object with smaller timestamp should be
     * before the one with larger value.</li>
     * </ol>
     * Thread-safety: This class is thread-safe, since it is stateless.
     * </p>
     *
     * @author Standlove, mittu
     * @version 1.0
     */
    private static class PlayerInfoComparator implements Comparator {

        /**
         * <p>
         * Empty constructor.
         * </p>
         */
        public PlayerInfoComparator() {
            // empty
        }

        /**
         * <p>
         * Compare the two given objects, and return a negative integer, zero, or a positive integer as the first
         * argument is less than, equal to, or greater than the second. The two given objects must be type of
         * PlayerInfo.
         * </p>
         *
         * @param o1
         *            the first object to compare.
         * @param o2
         *            the second object to compare.
         * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or
         *         greater than the second.
         * @throws IllegalArgumentException
         *             if any argument is null.
         * @throws ClassCastException
         *             if any argument is not type of PlayerInfo.
         */
        public int compare(Object o1, Object o2) {
            ImplementationHelper.checkObjectValid(o1, "o1");
            ImplementationHelper.checkObjectValid(o2, "o2");
            PlayerInfo player1 = (PlayerInfo) o1;
            PlayerInfo player2 = (PlayerInfo) o2;
            Date date1 = player1.getLatestSlot().getHostingStart();
            Date date2 = player2.getLatestSlot().getHostingStart();
            // if the hosting start is not equal decide the result based on that.
            if (date1.compareTo(date2) != 0) {
                return date1.compareTo(date2);
            }
            int count1 = player1.getContiguousSlotsCount();
            int count2 = player2.getContiguousSlotsCount();
            // if the contiguous slots count is not equal, decide the result based on that.
            if (count1 != count2) {
                return count2 - count1;
            }
            // decide the result based on latest slot completion timestamp.
            date1 = player1.getLatestSlotCompletion().getTimestamp();
            date2 = player2.getLatestSlotCompletion().getTimestamp();
            if (date1.compareTo(date2) != 0) {
                return date1.compareTo(date2);
            }
            // if everything is equal return 0.
            return 0;
        }
    }

    /**
     * A <code>Comparator</code> to compare the sequence numbers of <code>HostingBlock</code> or
     * <code>HostingSlot</code>.
     *
     * @author mittu
     * @version 1.0
     */
    private static class SequenceNumberComparator implements Comparator {

        /**
         * Compare the objects based on sequence number.
         *
         * @param o1
         *            object 1 to compare.
         * @param o2
         *            object 2 to compare.
         * @return result of comparison, will be always the difference of sequence numbers.
         */
        public int compare(Object o1, Object o2) {
            if (o1 instanceof HostingBlock) {
                HostingBlock block1 = (HostingBlock) o1;
                HostingBlock block2 = (HostingBlock) o2;
                return block1.getSequenceNumber() - block2.getSequenceNumber();
            } else {
                HostingSlot slot1 = (HostingSlot) o1;
                HostingSlot slot2 = (HostingSlot) o2;
                return slot1.getSequenceNumber() - slot2.getSequenceNumber();
            }
        }
    }
}

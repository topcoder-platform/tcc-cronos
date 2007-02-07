/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game;

import com.orpheus.administration.AdministrationException;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.entities.DomainTargetImpl;
import com.topcoder.randomstringimg.InvalidConfigException;
import com.topcoder.randomstringimg.ObfuscationException;
import com.topcoder.randomstringimg.RandomStringImage;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;
import com.topcoder.util.algorithm.hash.HashException;
import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;
import com.topcoder.util.web.sitestatistics.TextStatistics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * This is a base abstraction of the manager.
 * It adds implementations of listener methods (the manager becomes listener)
 * as well as direct game data manipulation for starting game functionality.
 * </p>
 *
 * <p>
 * The added methods are thread-safe. Sub classes must be thread-safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseGameDataManager implements GameDataManager,
    GameStartListener, NewGameAvailableListener, TargetUpdateListener {

    /**
     * <p>
     * Represents the collection of currently known not-yet-started games.
     * </p>
     *
     * <p>
     * This is initialized in all ctors, and is then changed through:
     * <li>The gameStatusChangedToStarted method when we remove an entry</li>
     * <li>The newGameAvailable method when we add a new entry</li>
     * It can NOT be null, but can be empty. None of the values may be null.
     * </p>
     */
    private final Map notYetStartedGames;

    /**
     * <p>
     * A simple constructor, init the <code>notYetStartedGames</code> member.
     * </p>
     *
     */
    protected BaseGameDataManager() {
        notYetStartedGames = new HashMap();
    }

    /**
     * <p>Update the hosting slot in database. The impl class need to call game data ejb to update the slot to db.
     * Basically, the start date of slot will be updated.</p>
     *
     * @param slot HostingSlot with new start date
     */
    protected abstract void persistSlot(HostingSlot slot);

    /**
     * <p>
     * This is a listener implementation method that simply takes the game to start, starts the game physically,
     * and removes it from that list.
     * All of these steps must be synchronized to achieve an atomic transaction and thread-safety.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @param game game that should be started
     * @throws IllegalArgumentException if the game is null
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void gameStatusChangedToStarted(Game game) {
        Helper.checkObjectNotNull(game, "game");
        checkStopped();

        synchronized (notYetStartedGames) {
            //get the blocks of the game
            HostingBlock[] blocks = game.getBlocks();

            if (blocks != null) {
                hosting_blocks:
                for (int blockNumber = 0; blockNumber < blocks.length; blockNumber++) {
                    HostingSlot[] slots = blocks[blockNumber].getSlots();

                    //if slot exists for the block
                    if (slots != null) {
                        for (int slotNumber = 0; slotNumber < slots.length; slotNumber++) {
                            HostingSlot slot = slots[slotNumber];

                            // look out for deleted slots
                            if (slot.getSequenceNumber() >= 0) {

                                // set the slot's start date in the DB to the current date to
                                // start the game
                                persistSlot(Helper.copyToSetStartDate(slots[0], new Date()));

                                break hosting_blocks;
                            }
                        }
                    }
                }
            }

            // remove from the not started games
            notYetStartedGames.remove(game.getId());
        }
    }

    /**
     * <p>
     * This is a listener implementation method that simply takes the new game,
     * and places this game into the notYetStartedGames map.
     * This must be synchronized to achieve an atomic transaction and thread-safety.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @param game the new game
     * @throws IllegalArgumentException if the parameter is null
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void newGameAvailable(Game game) {
        Helper.checkObjectNotNull(game, "game");
        checkStopped();

        synchronized (notYetStartedGames) {
            notYetStartedGames.put(game.getId(), game);
        }
    }

    /**
     * <p>
     * This is a listener implementation method that gets called in case some target has become
     * obsolete. This method then tries to search for the new target on the same page, and if the
     * new target has been found, creates <code>DomainTarget</code> object containing that new
     * target.
     * </p>
     *
     * @return newly created <code>DomainTarget</code> object containing new target, or a
     *         reference to the old <code>DomainTarget</code> object in case target update
     *         operation fails.
     * @param foundTextStats text statistics collected from some page on the domain. This statistics
     *            information will be used to create new target.
     * @param obsoleteTarget an objects containing old and now become obsolete target. Used to copy
     *            some properties into new target object.
     * @trows IllegalArgumentException if any of the parameters is <code>null</code>.
     * @throws IllegalStateException if Game Data Manager had already been stoped at the time call
     *             was made to this method.
     */
    public DomainTarget targetUpdated(TextStatistics[] foundTextStats, DomainTarget obsoleteTarget) {
        Helper.checkObjectNotNull(foundTextStats, "foundTextStats");
        Helper.checkObjectNotNull(obsoleteTarget, "obsoleteTarget");
        checkStopped();

        Random rd = new Random();
        HashAlgorithm hasher = getHashAlgorithmManager().getAlgorithm("SHA-1");
        int triesNum = 10;

        do {
            // Choose textStatistics randomly
            TextStatistics textStatistics = selectStatistics(foundTextStats, rd);

            try {
                // Create new text ID and hash for the target
                String idText = textStatistics.getText();
                String idHash = hasher.hashToHexString(
                        idText.replaceAll("[\n\r \t\f\u200b]+", ""), "UTF-8");
                // Generate clue image
                long clueImageId = createClueImage(textStatistics.getText());

                // Create a DomainTargetImpl instance and return it
                return new DomainTargetImpl(null, obsoleteTarget.getSequenceNumber(),
                        obsoleteTarget.getUriPath(), idText, idHash, clueImageId);
            } catch (HashException he) {
                // eat the exception
            } catch (AdministrationException ae) {
                // eat the exception
            }
        } while (triesNum-- != 0);

        // Was unable to actually update target
        return obsoleteTarget;
    }

    /**
     * <p>
     * Returns a copy if the notYetStartedGames list as an array.
     * When copying we ensure synchronization.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @return an array of games that have not yet started, in no particular order
     * @throws IllegalStateException if the manager has been stopped.
     */
    public Game[] getAllCurrentNotStartedGames() {
        checkStopped();

        synchronized (notYetStartedGames) {
            return (Game[]) notYetStartedGames.values().toArray(new Game[0]);
        }
    }

    /**
     * <p>
     * Copies a whole array of games into the this.notYetStartedGames list
     * When copying we ensure synchronization.
     * Simply synchronize on the <code>notYetStartedGames</code> instance.
     * </p>
     *
     * @param games an array of games that have not yet started
     * @throws IllegalArgumentException if the parameter is null or the array contains null element
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void setCurrentNotStartedGames(Game[] games) {
        Helper.checkObjectNotNull(games, "game");

        //the elements in the array can not be null
        for (int i = 0; i < games.length; i++) {
            Helper.checkObjectNotNull(games[i],
                    "games[" + i + "]");
        }

        checkStopped();

        synchronized (notYetStartedGames) {
            // first clear the current games
            notYetStartedGames.clear();

            // then insert the specified ones
            for (int i = 0; i < games.length; i++) {
                notYetStartedGames.put(games[i].getId(), games[i]);
            }
        }
    }

    /**
     * <p>
     * Add a new game to the list of notYetStartedGames.
     * All of these steps must be synchronized to achieve an atomic trasnaction and thread-safety.
     * Simply synchronize on the notYetStartedGames instance.
     * </p>
     *
     * @param game a new game which has not started yet
     * @throws IllegalArgumentException if the parameter is null
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void addNewNotStartedGame(Game game) {
        Helper.checkObjectNotNull(game, "game");
        checkStopped();

        synchronized (notYetStartedGames) {
            notYetStartedGames.put(game.getId(), game);
        }
    }

    /**
     * <p>
     * Remove an existing game from the list of notYetStartedGames.
     * All of these steps must be synchronized to achieve an atomic trasnaction and thread-safety.
     * Simply synchronize on the notYetStartedGames instance.
     * </p>
     *
     * @param gameId id of the game to remove
     * @throws IllegalStateException if the manager has been stopped.
     */
    public void removeStartedGameFromNotStartedList(long gameId) {
        checkStopped();

        synchronized (notYetStartedGames) {
            notYetStartedGames.remove(new Long(gameId));
        }
    }

    /**
     * <p>
     * Check whether the manager is stopped or not.
     * </p>
     *
     * @throws IllegalStateException if it is already stopped.
     */
    private void checkStopped() {
        if (isStopped()) {
            throw new IllegalStateException("The manager is stopped.");
        }
    }

    /**
     * <p>
     * This method randomly choses a <code>TextStatistics</code> object from the array of provided
     * text statistics.
     * </p>
     *
     * @return a randomly chosen text statistics, or <code>null</code> if provided array is empty.
     * @param stats the array of text statistics to chose one from.
     * @param rd the random generator.
     */
    private TextStatistics selectStatistics(TextStatistics[] stats, Random rd) {
        return stats[rd.nextInt(stats.length)];
    }

    /**
     * <p>
     * Creates an image representation of the specified text, records it as a downloadable object,
     * and returns the download object ID.
     * </p>
     *
     * @return the downloadable object ID of the generated image.
     * @param imageText a String containing the text to be rendered as an image.
     * @throws AdministrationException if an error occurs while generating or recording the image.
     */
    private long createClueImage(String imageText) throws AdministrationException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            getRandomStringImage().generate(imageText, stream);
            if (getGameDataPersistenceLocal() != null) {
                return getGameDataPersistenceLocal().recordBinaryObject(
                        "clue_image.png", "image/png", stream.toByteArray());
            } else {
                return getGameDataPersistenceRemote().recordBinaryObject(
                        "clue_image.png", "image/png", stream.toByteArray());
            }
        } catch (IOException ioe) {
            throw new AdministrationException("Could not generate clue image", ioe);
        } catch (ObfuscationException oe) {
            throw new AdministrationException("Could not generate clue image", oe);
        } catch (InvalidConfigException ice) {
            throw new AdministrationException("Could not generate clue image", ice);
        } catch (GameDataException gde) {
            throw new AdministrationException("Could not generate clue image", gde);
        }
    }

    /**
     * <p>
     * This is a clean up routine or the manager.
     * If the manager uses any kind of remote resources such as db connections, or threads this method
     * would be implemented to call on all the resources to clean up.
     * Any exceptions should be wrapped into GameDataException.
     * </p>
     *
     * @throws GameDataException if there were any issues with thos method call.
     */
    public abstract void stopManager() throws GameDataException;

    /**
     * <p>
     * An abstract method used to check whether the manager is stopped or not.
     * </p>
     *
     * @return true if the manager is stopped, otherwise false.
     */
    public abstract boolean isStopped();

    /**
     * <p>
     * An abstract method used to obtain Hash Algorithm Manager created by concrete implementation
     * of this class.
     * </p>
     *
     * @return an instance of the <code>HashAlgorithmManager</code> class.
     */
    protected abstract HashAlgorithmManager getHashAlgorithmManager();

    /**
     * <p>
     * An abstract method used to obtain Random String Image created by concrete implementation of
     * this class.
     * </p>
     *
     * @return an instance of the <code>RandomStringImage</code> class.
     */
    protected abstract RandomStringImage getRandomStringImage();

    /**
     * <p>
     * An abstract method used to obtain <code>GameDataLocal</code> object created by concrete
     * implementation of this class.
     * </p>
     *
     * @return an instance of the <code>GameDataLocal</code> class.
     */
    protected abstract GameDataLocal getGameDataPersistenceLocal();

    /**
     * <p>
     * An abstract method used to obtain <code>GameData</code> object created by concrete
     * implementation of this class.
     * </p>
     *
     * @return an instance of the <code>GameData</code> class.
     */
    protected abstract GameData getGameDataPersistenceRemote();
}


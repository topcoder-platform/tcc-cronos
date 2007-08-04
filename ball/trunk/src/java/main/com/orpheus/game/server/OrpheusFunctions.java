/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.server.comparator.ActiveGamesComparator;
import com.orpheus.game.server.comparator.MyGamesComparator;
import com.orpheus.game.server.comparator.plugin.AllGamesListSorter;
import com.orpheus.game.server.comparator.plugin.MyGamesListSorter;
import com.orpheus.game.server.framework.prize.PrizeCalculatorType;
import com.orpheus.game.server.framework.prize.PrizeCalculatorTypeSource;
import com.orpheus.game.server.framework.prize.PrizeException;
import com.orpheus.game.server.framework.bounce.BouncePointCalculatorType;
import com.orpheus.game.server.framework.bounce.BouncePointCalculatorTypeSource;
import com.orpheus.game.server.framework.bounce.BouncePointException;
import com.orpheus.game.server.admin.OrpheusGameServerAdminException;
import com.orpheus.game.server.util.GameCreationType;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.formvalidator.validator.Message;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.web.registration.RegistrationManager;
import com.topcoder.web.registration.WebRegistrationConfigurationException;
import com.topcoder.web.tag.paging.DataPagingTag;
import com.topcoder.web.user.LoginHandler;
import com.topcoder.lang.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * <p>An utility class providing the static functions to be utilized by JSPs when rendering the details for various
 * objects or for accessing the desired services.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class OrpheusFunctions {

    /**
     * <p>A <code>String</code> providing the configuration namespace for custom classes from <code>Orpheus Game Server
     * </code> application.</p>
     */
    public static final String NAMESPACE = "com.orpheus.game.server";

    /**
     * <p>A <code>String</code> providing a status for a game which hasn't been started yet.</p>
     */
    public static final String GAME_STATUS_NOT_STARTED = "Not Started";

    /**
     * <p>A <code>String</code> providing a status for a game which is currently in progress.</p>
     */
    public static final String GAME_STATUS_IN_PROGRESS = "In Progress";

    /**
     * <p>A <code>String</code> providing a status for a game which has finished already.</p>
     */
    public static final String GAME_STATUS_COMPLETED = "Completed";

    /**
     * <p>An <code>int</code> corresponding to approval status for rejected entities.</p>
     */
    public static final int APPROVAL_STATUS_REJECTED = 0;

    /**
     * <p>An <code>int</code> corresponding to approval status for approved entities.</p>
     */
    public static final int APPROVAL_STATUS_APPROVED = 1;

    /**
     * <p>An <code>int</code> corresponding to approval status for unapproved entities.</p>
     */
    public static final int APPROVAL_STATUS_UNAPPROVED = 2;

    /**
     * <p>An <code>int</code> corresponding to bid status for highest bid.</p>
     */
    public static final int BID_STATUS_HIGHEST = 0;

    /**
     * <p>An <code>int</code> corresponding to bid status for qualifying bid.</p>
     */
    public static final int BID_STATUS_QUALIFYING = 1;

    /**
     * <p>An <code>int</code> corresponding to bid status for outbid bid.</p>
     */
    public static final int BID_STATUS_OUTBID = 2;

    /**
     * <p>An <code>int</code> corresponding to default sound option.</p>
     */
    public static final Integer DEFAULT_SOUND_OPTION = new Integer(1);

    /**
     * <p>Constructs new <code>OrpheusFunctions</code> instance. This implementation does nothing and is used for
     * preventing the instantiation of this utility class.</p>
     */
    private OrpheusFunctions() {
    }

    /**
     * <p>Gets the manager for user registration processes.</p>
     *
     * @return a <code>RegistrationManager</code> to be used for managing the user registration process.
     * @throws JspTagException if a registration manager could not be instantiated.
     */
    public static RegistrationManager getRegistrationManager() throws JspTagException {
        try {
            return RegistrationManager.getInstance();
        } catch (WebRegistrationConfigurationException e) {
            e.printStackTrace();
            throw new JspTagException(e);
        }
    }

    /**
     * <p>Gets the manager for sponsor registration processes.</p>
     *
     * @return a <code>RegistrationManager</code> to be used for managing the sponsor registration process.
     * @throws JspTagException if a registration manager could not be instantiated.
     */
    public static RegistrationManager getSponsorRegistrationManager() throws JspTagException {
        try {
            return RegistrationManager.getInstance("com.topcoder.web.registration.sponsor");
        } catch (WebRegistrationConfigurationException e) {
            e.printStackTrace();
            throw new JspTagException(e);
        }
    }

    /**
     * <p>Gets the footer message like <code>1 - 20 of 156</code> for the current state of the provided <code>
     * DataPagingTag</code>.</p>
     *
     * @param tag a <code>DataPagingTag</code> rendering the paginated data.
     * @return a <code>String</code> providing the footer message evaluated based on the current state of the tag.
     */
    public static String getFooterMessage(DataPagingTag tag) {
        StringBuffer buffer = new StringBuffer();
        if (tag.getDataSize() > 0) {
            buffer.append((tag.getCurrentPage() - 1) * tag.getPageSize() + 1);
        } else {
            buffer.append(0);
        }
        buffer.append(" - ");
        buffer.append(Math.min(tag.getCurrentPage() * tag.getPageSize(), tag.getDataSize()));
        buffer.append(" of ");
        buffer.append(tag.getDataSize());
        return buffer.toString();
    }

    /**
     * <p>Gets the starting URL for the specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for game to get starting URL for.
     * @return a <code>String</code> providing the starting URL for the game or <code>null</code> if the URL can not be
     *         evaluated.
     */
    public static String getStartingUrl(Game game) {
        HostingBlock[] blocks = game.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            HostingSlot[] slots = blocks[i].getSlots();
            for (int j = 0; j < slots.length; j++) {
                if (slots[j].getHostingStart() != null) {
                    return slots[j].getDomain().getDomainName();
                }
            }
        }
        return null;
    }

    /**
     * <p>Gets the minimum payout for the specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for game to get minimum payout for.
     * @param context a <code>ServletContext</code> providing the application's context.
     * @return a <code>double</code> providing the minimum payout for the game.
     */
    public static double getMinimumPayout(Game game, ServletContext context) {
        PrizeCalculatorType prizeCalculatorType = getPrizeType(game, context);
        try {
            return prizeCalculatorType.getCalculator().getMinimumPayout(game);
        } catch (Exception e) {
            throw new OrpheusGameServerException("Could not evaluate the minimum payout", e);
        }
/*
        double payment = 0.00;
        HostingBlock[] blocks = game.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            HostingBlock block = blocks[i];
            HostingSlot[] slots = block.getSlots();
            for (int j = 0; j < slots.length; j++) {
                HostingSlot slot = slots[j];
                if (slot.getHostingStart() != null) {
                    payment += slot.getWinningBid();
                }
            }
        }
        try {
            String feeString = ConfigManager.getInstance().getString(NAMESPACE, "AdminFee");
            double fee = Double.parseDouble(feeString);
            return payment * (1.0 - fee);
        } catch (NumberFormatException e) {
            throw new OrpheusGameServerException("The administration fee provided by configuration is invalid", e);
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerException("The administration fee can not be read from configuration", e);
        }
*/
//        return 75.00;
    }

    /**
     * <p>Gets the current status for the specified game to be displayed by <code>Game Details</code> and <code>My Games
     * </code> pages.</p>
     *
     * @param game a <code>Game</code> providing the details for game to get current status for.
     * @return a <code>String</code> providing the current status for the game.
     */
    public static String getGameStatus(Game game) {
        Date startDate = game.getStartDate();
        Date endDate = game.getEndDate();
        Date currentTime = new Date();
        if ((startDate == null) || (currentTime.compareTo(startDate) < 0)) {
            return GAME_STATUS_NOT_STARTED;
        } else {
            if (endDate != null) {
                return GAME_STATUS_COMPLETED;
            } else {
                return GAME_STATUS_IN_PROGRESS;
            }
        }
    }

    /**
     * <p>Checks if the specified game is currently running.</p>
     *
     * @param game a <code>Game</code> providing the details for game to get current status for.
     * @return <code>true</code> if specified game is currently running; <code>false</code> otherwise.
     */
    public static boolean isGameRunning(Game game) {
        return getGameStatus(game).equals(GAME_STATUS_IN_PROGRESS);
    }

    /**
     * <p>Gets the current status for the specified game to be displayed by browser plugin.</p>
     *
     * @param game a <code>Game</code> providing the details for game to get current status for.
     * @return a <code>String</code> providing the current status for the game.
     */
    public static String getPluginGameStatus(Game game) {
        Date startDate = game.getStartDate();
        Date endDate = game.getEndDate();
        Date currentTime = new Date();
        if (startDate == null) {
            // In fact. this should never happen as the games must always have a start date specified.
            return GAME_STATUS_NOT_STARTED;
        } else if (currentTime.compareTo(startDate) < 0) {
            long diff = startDate.getTime() - currentTime.getTime();
            long days = diff / (24 * 60 * 60 * 1000);
            diff %= (24 * 60 * 60 * 1000);
            long hours = diff / (60 * 60 * 1000);
            diff %= (60 * 60 * 1000);
            long minutes = diff / (60 * 1000);

            StringBuffer buffer = new StringBuffer();
            buffer.append("Begins in");
            if (days > 0) {
                buffer.append(" ");
                buffer.append(days);
                buffer.append("d");
            }
            if (hours > 0) {
                buffer.append(" ");
                buffer.append(hours);
                buffer.append("h");
            }
            buffer.append(" ");
            buffer.append(minutes);
            buffer.append("m");

            return buffer.toString();
        } else {
            if (endDate != null) {
                return GAME_STATUS_COMPLETED;
            } else {
                return GAME_STATUS_IN_PROGRESS;
            }
        }
    }

    /**
     * <p>Gets the status of the specified player in context of specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for game.
     * @param playerGames a <code>Game</code> array providing the details for games which the current player is already
     *        registered to.
     * @return <code>"Player"</code> if specified player is already registered to specified game; <code>"Unregistered"
     *         </code> otherwise.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static String getPlayerStatus(Game game, Game[] playerGames) {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if (playerGames == null) {
            throw new IllegalArgumentException("The parameter [playerGames] is NULL");
        }
        for (int i = 0; i < playerGames.length; i++) {
            if (playerGames[i].getId().equals(game.getId())) {
                return "Player";
            }
        }
        return "Unregistered";
    }

    /**
     * <p>Gets the rank of a specified player in context of a game represented by the specified leader board. The rank
     * is determined as position of the specified player in specified list. If the player is not in the list then the
     * rank is evaluated as number of players in list plus 1.</p>
     *
     * @param gameLeaders a <code>UserProfile</code> array listing the current leaders for the game.
     * @param player a <code>UserProfile</code> representing the current player.
     * @return a <code>String</code> representing the current rank of the specified player among specified game leaders.
     */
    public static String getPlayerRank(UserProfile[] gameLeaders, UserProfile player) {
        if (gameLeaders == null) {
            throw new IllegalArgumentException("The parameter [gameLeaders] is NULL");
        }
        if (player == null) {
            throw new IllegalArgumentException("The parameter [player] is NULL");
        }
        for (int i = 0; i < gameLeaders.length; i++) {
            if (gameLeaders[i].getIdentifier().equals(player.getIdentifier())) {
                return String.valueOf(i + 1);
            }
        }
        return String.valueOf(gameLeaders.length + 1);
    }

    /**
     * <p>Gets the rank of a specified player in context of a game represented by the specified leader board. The rank
     * is determined as position of the specified player in specified list. If the player is not in the list then the
     * rank is evaluated as number of players in list plus 1.</p>
     *
     * @param gameLeaders a <code>UserProfile</code> array listing the current leaders for the game.
     * @param player a <code>UserProfile</code> representing the current player.
     * @return a <code>String</code> representing the current rank of the specified player among specified game leaders.
     */
    public static String getPlayerRank(List gameLeaders, UserProfile player) {
        if (gameLeaders == null) {
            throw new IllegalArgumentException("The parameter [gameLeaders] is NULL");
        }
        if (player == null) {
            throw new IllegalArgumentException("The parameter [player] is NULL");
        }
        for (int i = 0; i < gameLeaders.size(); i++) {
            UserProfile leader = (UserProfile) gameLeaders.get(i);
            if (leader.getIdentifier().equals(player.getIdentifier())) {
                return String.valueOf(i + 1);
            }
        }
        return String.valueOf(gameLeaders.size() + 1);
    }

    /**
     * <p>Converts the specified array into a <code>List</code>. This method may be useful when the specified items are
     * to be passed to <code>Data Paging Tag</code> which accepts lists but not arrays.</p>
     *
     * @param items a <code>Object</code> array providing the array of items to convert to list.
     * @return a <code>List</code> containing the specified items.
     * @throws IllegalArgumentException if specified <code>items</code> is <code>null</code>.
     */
    public static List convertToList(Object[] items) {
        if (items == null) {
            throw new IllegalArgumentException("The parameter [items] is NULL");
        }
        return Arrays.asList(items);
    }

    /**
     * <p>Joins the specified lists of game winners and current leaders having the winners placed first. The order of
     * the profiles in specified lists is preserved in returned list.</p>
     *
     * @param winners a <code>UserProfile</code> array providing the list of game winners.
     * @param leaders a <code>UserProfile</code> array providing the list of current game leaders. 
     * @return a <code>List</code> containing the specified lists joined having the winners placed first and having
     *         duplicate profiles removed.
     * @throws IllegalArgumentException if specified <code>items</code> is <code>null</code>.
     */
    public static List joinProfiles(UserProfile[] winners, UserProfile[] leaders) {
        if (winners == null) {
            throw new IllegalArgumentException("The parameter [list1] is NULL");
        }
        if (leaders == null) {
            throw new IllegalArgumentException("The parameter [list2] is NULL");
        }
        LinkedHashMap map = new LinkedHashMap();
        for (int i = 0; i < winners.length; i++) {
            map.put(winners[i].getIdentifier(), winners[i]);
        }
        for (int i = 0; i < leaders.length; i++) {
            if (!map.containsKey(leaders[i].getIdentifier())) {
                map.put(leaders[i].getIdentifier(), leaders[i]);
            }
        }
        return new ArrayList(map.values());
    }

    /**
     * <p>Checks if specified profile is contained among the profiles in specified list.</p>
     *
     * @param profile a <code>UserProfile</code> to check.
     * @param profiles a <code>UserProfile</code> array providing the list of profiles.
     * @return <code>true</code> if specified profile is found within specified list; <code>false</code> otherwise.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static boolean isInList(UserProfile profile, UserProfile[] profiles) {
        if (profile == null) {
            throw new IllegalArgumentException("The parameter [profile] is NULL");
        }
        if (profiles == null) {
            throw new IllegalArgumentException("The parameter [profiles] is NULL");
        }
        for (int i = 0; i < profiles.length; i++) {
            if (profiles[i].getIdentifier().equals(profile.getIdentifier())) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Gets the comparator to be used for sorting the list of active games displayed to player.</p>
     *
     * @param playerGames a <code>Game</code> array providing the games which the player is registered to.
     * @param context a <code>ServletContext</code> providing the context surrounding the <code>Data Paging Tag</code>.
     * @return a <code>ActiveGamesComparator</code> to be used for sorting the list of active games displayed to player.
     * @throws IllegalArgumentException if specified <code>playerGames</code> array is <code>null</code>.
     */
    public static ActiveGamesComparator getPlayerActiveGamesComparator(Game[] playerGames, ServletContext context) {
        if (playerGames == null) {
            throw new IllegalArgumentException("The parameter [playerGames] is NULL");
        }
        return new ActiveGamesComparator(playerGames, context);
    }

    /**
     * <p>Gets the comparator to be used for sorting the list of games displayed to player in plugin window.</p>
     *
     * @param playerGames a <code>Game</code> array providing the games which the player is registered to.
     * @param context a <code>ServletContext</code> providing the context surrounding the <code>Data Paging Tag</code>.
     * @return a <code>AllGamesListSorter</code> to be used for sorting the list of games displayed to player.
     * @throws IllegalArgumentException if specified <code>playerGames</code> array is <code>null</code>.
     */
    public static AllGamesListSorter getPluginAllGamesComparator(Game[] playerGames, ServletContext context) {
        if (playerGames == null) {
            throw new IllegalArgumentException("The parameter [playerGames] is NULL");
        }
        return new AllGamesListSorter(playerGames, context);
    }

    /**
     * <p>Gets the comparator to be used for sorting the list of registered games displayed to player in plugin window.
     * </p>
     *
     * @return a <code>MyGamesListSorter</code> to be used for sorting the list of games displayed to player.
     */
    public static MyGamesListSorter getPluginMyGamesComparator() {
        return new MyGamesListSorter();
    }

    /**
     * <p>Gets the comparator to be used for sorting the list of registered games displayed to player.</p>
     *
     * @return a <code>MyGamesComparator</code> to be used for sorting the list of registered games displayed to player.
     * @param context a <code>ServletContext</code> providing the context surrounding the <code>Data Paging Tag</code>.
     */
    public static MyGamesComparator getPlayerGamesComparator(ServletContext context) {
        return new MyGamesComparator(context);
    }

    /**
     * <p>Gets the details for plugin for <code>FierFox</code> browser which is available for download.</p>
     *
     * @return a <code>PluginInfo</code> providing the details for <code>FireFox</code> plugin.
     */
    public static PluginInfo getFirefoxPluginInfo() {
        ConfigManager cm = ConfigManager.getInstance();
        try {
            Property pluginConfig = cm.getPropertyObject(NAMESPACE, "FireFoxPlugin");
            return new PluginInfo(pluginConfig.getValue("fileSize"), pluginConfig.getValue("datePosted"),
                                  pluginConfig.getValue("version"), pluginConfig.getValue("language"),
                                  pluginConfig.getValue("browser"));
        } catch (UnknownNamespaceException e) {
            // In fact, this should never happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>Gets the details for plugin for <code>Internet Explorer</code> browser which is available for download.</p>
     *
     * @return a <code>PluginInfo</code> providing the details for <code>Internet Explorer</code> plugin.
     */
    public static PluginInfo getInternetExplorerPluginInfo() {
        ConfigManager cm = ConfigManager.getInstance();
        try {
            Property pluginConfig = cm.getPropertyObject(NAMESPACE, "InternetExplorerPlugin");
            return new PluginInfo(pluginConfig.getValue("fileSize"), pluginConfig.getValue("datePosted"),
                                  pluginConfig.getValue("version"), pluginConfig.getValue("language"),
                                  pluginConfig.getValue("browser"));
        } catch (UnknownNamespaceException e) {
            // In fact, this should never happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>Checks if the specified error which has been encountered while servicing the incoming request indicates that
     * the user profile with requested email address already exists. The decision is made based on the text of the
     * message provided by the specified throwable.</p>
     *
     * <p>This functions is supposed to be used during playe or sponsor registration process in case the submitted email
     * address is already registered to <code>Game Server</code>. Unfortunately the underlying service layer does not
     * distinguish such a situtation and does not provide the appropriate response code therefore this function has been
     * put to use to analyze the exception reported by service layer.</p>
     *
     * @param error a <code>Throwable</code> representing the error which has been encountered.
     * @return <code>true</code> if specified error indicates that the user profile with same email address already
     *         exists; <code>false</code> otherwise.
     */
    public static boolean isDuplicateEmailAddress(Throwable error) {
        String message = error.getMessage();
        if (message != null) {
//            return message.indexOf("Violation of UNIQUE KEY constraint 'unique_e_mail'.") >= 0;
            return message.indexOf("user_email_inx") >= 0;
        } else {
            return false;
        }
    }

    /**
     * <p>Checks if the specified error which has been encountered while servicing the incoming request indicates that
     * the user profile with requested handle already exists. The decision is made based on the text of the message
     * provided by the specified throwable.</p>
     *
     * <p>This functions is supposed to be used during player or sponsor registration process in case the submitted
     * handle is already registered to <code>Game Server</code>. Unfortunately the underlying service layer does not
     * distinguish such a situtation and does not provide the appropriate response code therefore this function has been
     * put to use to analyze the exception reported by service layer.</p>
     *
     * @param error a <code>Throwable</code> representing the error which has been encountered.
     * @return <code>true</code> if specified error indicates that the user profile with same handle already exists;
     *         <code>false</code> otherwise.
     */
    public static boolean isDuplicateHandle(Throwable error) {
        String message = error.getMessage();
        if (message != null) {
            return message.indexOf("user_handle_inx") >= 0;
        } else {
            return false;
        }
    }

    /**
     * <p>Gets the current host for the specified game.</p>
     *
     * @param game a <code>Game</code> representing a game to get the current host for.
     * @return a <code>String</code> providing the current host for the specified game or <code>n/a</code> if such a
     *         host is not determined.
     */
    public static String getCurrentHost(Game game) {
        String gameStatus = getGameStatus(game);
        if (GAME_STATUS_IN_PROGRESS.equals(gameStatus)) {
            HostingSlot currentHostingSlot = getCurrentHostingSlot(game);
            if (currentHostingSlot != null) {
                return currentHostingSlot.getDomain().getDomainName();
            }
        }
        return "n/a";
    }

    /**
     * <p>Gets the current hosting slot for the specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for the game in progress.
     * @return a <code>HostingSlot</code> representing a current hosting slot for the game or <code>null</code> if such
     *         a slot is not determined.
     */
    public static HostingSlot getCurrentHostingSlot(Game game) {
        HostingSlot[] slots;
        Date hostingStart, hostingEnd;

        Date currentTime = new Date();
        HostingBlock[] blocks = game.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            slots = blocks[i].getSlots();
            for (int j = 0; j < slots.length; j++) {
                hostingStart = slots[j].getHostingStart();
                if (hostingStart != null) {
                    if (currentTime.compareTo(hostingStart) >= 0) {
                        hostingEnd = slots[j].getHostingEnd();
                        if ((hostingEnd == null) || (currentTime.compareTo(hostingEnd) < 0)) {
                            return slots[j];
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * <p>Gets the name of the person set in the contact info for specified sponsor account.</p>
     *
     * @param sponsor a <code>UserProfile</code> providing details for sponsor account.
     * @return a <code>String</code> providing the full name of contact.
     */
    public static String getSponsorContactName(UserProfile sponsor) {
        return sponsor.getProperty(BaseProfileType.FIRST_NAME) + " " + sponsor.getProperty(BaseProfileType.LAST_NAME);
    }

    /**
     * <p>Gets the email address for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the email address for specified account.
     */
    public static String getEmailAddress(UserProfile account) {
        return (String) account.getProperty(BaseProfileType.EMAIL_ADDRESS);
    }

    /**
     * <p>Gets the handle for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the handle for specified account.
     */
    public static String getHandle(UserProfile account) {
        return (String) account.getProperty(UserConstants.CREDENTIALS_HANDLE);
    }

    /**
     * <p>Gets the address line 1 for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the address line 1 for specified account.
     */
    public static String getAddress1(UserProfile account) {
        return (String) account.getProperty(UserConstants.ADDRESS_STREET_1);
    }

    /**
     * <p>Gets the address line 2 for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the address line 2 for specified account.
     */
    public static String getAddress2(UserProfile account) {
        return (String) account.getProperty(UserConstants.ADDRESS_STREET_2);
    }

    /**
     * <p>Gets the address city for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the address city for specified account.
     */
    public static String getCity(UserProfile account) {
        return (String) account.getProperty(UserConstants.ADDRESS_CITY);
    }

    /**
     * <p>Gets the address state for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the address state for specified account.
     */
    public static String getState(UserProfile account) {
        return (String) account.getProperty(UserConstants.ADDRESS_STATE);
    }

    /**
     * <p>Gets the address postal code for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the address postal code for specified account.
     */
    public static String getPostalCode(UserProfile account) {
        return (String) account.getProperty(UserConstants.ADDRESS_POSTAL_CODE);
    }

    /**
     * <p>Gets the address phone number for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the address phone number for specified account.
     */
    public static String getPhone(UserProfile account) {
        return (String) account.getProperty(UserConstants.ADDRESS_PHONE_NUMBER);
    }

    /**
     * <p>Gets the address country for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the address country for specified account.
     */
    public static String getCountry(UserProfile account) {
        return (String) account.getProperty(UserConstants.ADDRESS_COUNTRY);
    }

    /**
     * <p>Gets the address fax number for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the fax phone number for specified account.
     */
    public static String getFax(UserProfile account) {
        return (String) account.getProperty(UserConstants.SPONSOR_FAX_NUMBER);
    }

    /**
     * <p>Gets the preferred payment method for player associated with the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>String</code> providing the preferred payment method.
     */
    public static String getPlayerPaymentMethodPref(UserProfile account) {
        return (String) account.getProperty(UserConstants.PLAYER_PAYMENT_PREF);
    }

    /**
     * <p>Gets the index of sound option preferred by the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @return a <code>Integer</code> referencing the selected sound option.
     */
    public static Integer getPreferredSound(UserProfile account) {
        Integer option = (Integer) account.getProperty(UserConstants.PREFS_SOUND);
        if (option == null) {
            return DEFAULT_SOUND_OPTION;
        } else {
            return option;
        }
    }

    /**
     * <p>Gets the approval status for the specified domain.</p>
     *
     * @param domain a <code>Domain</code> providing the details for the domain to get approval status for.
     * @return a <code>String</code> providing the approval status for specified domain (one of: <code>approved</code>,
     *         <code>unapproved</code>, <code>rejected</code>).
     */
    public static String getDomainApprovalStatus(Domain domain) {
        Boolean approved = domain.isApproved();
        if (approved == null) {
            return "unapproved";
        } else if (approved.booleanValue()) {
            return "approved";
        } else {
            return "rejected";
        }
    }

    /**
     * <p>Gets the approval status for specified domain.</p>
     *
     * @param domain a <code>Domain</code> providing the details for domain.
     * @return an <code>int</code> providing identifying the approval status.
     */
    public static int getApprovalStatus(Domain domain) {
        return getApprovalStatus(domain.isApproved());
    }

    /**
     * <p>Gets the approval status for specified image.</p>
     *
     * @param image an <code>ImageInfo</code> providing the details for image.
     * @return an <code>int</code> providing identifying the approval status.
     */
    public static int getApprovalStatus(ImageInfo image) {
        return getApprovalStatus(image.isApproved());
    }

    /**
     * <p>Gets the size of specified array.</p>
     *
     * @param array an <code>Object</code> array to get the size for.
     * @return a <code>int</code> providing the size of the specified array.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static int getSize(Object[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The parameter [array] is NULL");
        }
        return array.length;
    }

    /**
     * <p>Gets the <code>UserProfile</code> providing the details for account for a user associated with specified
     * request.</p>
     *
     * @param request an <code>HttpServletRequest</code> representing the incoming request.
     * @return a <code>UserProfile</code> representing current user or <code>null</code> if such profile is not found.
     */
    public static UserProfile getUserProfile(HttpServletRequest request) {
        return LoginHandler.getAuthenticatedUser(request.getSession(false));
    }

    /**
     * <p>Gets the profile type for account for a user associated with specified request.</p>
     *
     * @param request an <code>HttpServletRequest</code> representing the incoming request.
     * @return a <code>String</code> providing profile type for current user or <code>null</code> if such profile is not
     *         found.
     */
    public static String getUserProfileType(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserProfile authenticatedUser = LoginHandler.getAuthenticatedUser(session);
            if (authenticatedUser != null) {
                if (authenticatedUser.getProfileType("player") != null) {
                    return "player";
                }
                if (authenticatedUser.getProfileType("admin") != null) {
                    return "admin";
                }
                if (authenticatedUser.getProfileType("sponsor") != null) {
                    return "sponsor";
                }
            }
        }
        return null;
    }

    /**
     * <p>Gets the email address to be used by users for contacting to <code>Orpheus</code> management staff.</p>
     *
     * @return a <code>String</code> providing the <code>Contact Us</code> email address.
     */
    public static String getContactEmailAddress() {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            return cm.getString(NAMESPACE, "ContactEmailAddress");
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerException(e);
        }
    }

    /**
     * <p>Gets the period for brainteaser update.</p>
     *
     * @return an <code>int</code> providing the brainteaser update period (in seconds).
     */
    public static int getBrainteaserUpdatePeriod() {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            return Integer.parseInt(cm.getString(NAMESPACE, "BrainteaserUpdatePeriod"));
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerException(e);
        }
    }

    /**
     * <p>Gets the period for solving the puzzle.</p>
     *
     * @return an <code>int</code> providing the period for solving the puzzle (in seconds).
     */
    public static int getSolvePuzzlePeriod() {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            return Integer.parseInt(cm.getString(NAMESPACE, "SolvePuzzlePeriod"));
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerException(e);
        }
    }

    /**
     * <p>Gets the period for solving the game win puzzles by players.</p>
     *
     * @return an <code>int</code> providing the period for solving the game win puzzles (in seconds).
     */
    public static int getSolveWinGamePuzzlePeriod() {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            return Integer.parseInt(cm.getString(NAMESPACE, "SolvePuzzlePeriod"));
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerException(e);
        }
    }

    /**
     * <p>Gets the period for recognition of the duplicate requests. The repeated requests reaching the server with
     * interval less or equal than returned one will be considered duplicates.</p>
     *
     * @return an <code>int</code> providing the period for duplicate requests recognition (in milliseconds).
     */
    public static int getDuplicateRequestRecognitionInterval() {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            return Integer.parseInt(cm.getString(NAMESPACE, "DuplicateRequestRecognitionInterval"));
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerException(e);
        }
    }

    /**
     * <p>Gets the validation error which might have been reported for the specified form input field while validating
     * the data submitted by user through web-form. The error (if any) is formatted to </p>
     *
     * @param inputFieldName a <code>String</code> providing the name of the form input field.
     * @param errors a <code>Map</code> mapping the <code>String</code> error key to <code>Message</code> providing
     *        details for error message.
     * @return a <code>String</code> providin the validation error for specified input field or <code>null</code> if
     *         there are no validation errors reported.
     */
    public static String error(String inputFieldName, Map errors) {
        if ((errors != null) && (errors.containsKey(inputFieldName))) {
            Message message = (Message) errors.get(inputFieldName);
            if (message != null) {
                return "<tr><td colspan=\"2\"><span class=\"fBold cRed\">" + message.getMessage() + "</span></td></tr>";
            }
        }
        return null;
    }

    /**
     * <p>Updates the generated puzzle HTML content to append IDs for the game and slot associated with puzzle to a
     * query string.</p>
     *
     * @param puzzleHTMLContent a <code>String</code> providing the generated puzzle HTML content.
     * @param gameId a <code>long</code> providing the ID of a game.
     * @param slotId a <code>long</code> providing the ID of a slot.
     * @return a <code>String</code> providing the fixed puzzle HTML content.
     */
    public static String fixTestPuzzleUrl(String puzzleHTMLContent, long gameId, long slotId) {
        if (puzzleHTMLContent == null) {
            throw new IllegalArgumentException("The parameter [puzzleHTMLContent] is NULL");
        }
        return StringUtil.replace(puzzleHTMLContent,
                                  "testPuzzle.do",
                                  "testPuzzle.do?gameId=" + gameId + "&slotId=" + slotId);
    }

    /**
     * <p>Updates the generated puzzle HTML content to append IDs for practice puzzle to a query string.</p>
     *
     * @param puzzleHTMLContent a <code>String</code> providing the generated puzzle HTML content.
     * @param puzzleId a <code>long</code> providing the ID of a practice puzzle.
     * @return a <code>String</code> providing the fixed puzzle HTML content.
     */
    public static String fixTestPracticePuzzleUrl(String puzzleHTMLContent, long puzzleId) {
        if (puzzleHTMLContent == null) {
            throw new IllegalArgumentException("The parameter [puzzleHTMLContent] is NULL");
        }
        return StringUtil.replace(puzzleHTMLContent, "testPuzzle.do", "testPuzzleForTest.do?puzzleId=" + puzzleId);
    }

    /**
     * <p>Gets the approval status.</p>
     *
     * @param approved a <code>Boolean</code> providing the approval state.
     * @return an <code>int</code> providing identifying the approval status.
     */
    private static int getApprovalStatus(Boolean approved) {
        if (approved == null) {
            return APPROVAL_STATUS_UNAPPROVED;
        } else if (approved.booleanValue()) {
            return APPROVAL_STATUS_APPROVED;
        } else {
            return APPROVAL_STATUS_REJECTED;
        }
    }


    /**
     * <p>Gets the email address for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the email address for specified account.
     */
    public static String getEmailAddress(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, BaseProfileType.EMAIL_ADDRESS);
    }

    /**
     * <p>Gets the email address for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the email address for specified account.
     */
    public static String getFirstName(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, BaseProfileType.FIRST_NAME);
    }

    /**
     * <p>Gets the email address for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the email address for specified account.
     */
    public static String getLastName(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, BaseProfileType.LAST_NAME);
    }

    /**
     * <p>Gets the handle for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the handle for specified account.
     */
    public static String getHandle(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.CREDENTIALS_HANDLE);
    }

    /**
     * <p>Gets the address line 1 for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the address line 1 for specified account.
     */
    public static String getAddress1(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.ADDRESS_STREET_1);
    }

    /**
     * <p>Gets the address line 2 for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the address line 2 for specified account.
     */
    public static String getAddress2(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.ADDRESS_STREET_2);
    }

    /**
     * <p>Gets the address city for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the address city for specified account.
     */
    public static String getCity(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.ADDRESS_CITY);
    }

    /**
     * <p>Gets the address state for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the address state for specified account.
     */
    public static String getState(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.ADDRESS_STATE);
    }

    /**
     * <p>Gets the address postal code for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the address postal code for specified account.
     */
    public static String getPostalCode(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.ADDRESS_POSTAL_CODE);
    }

    /**
     * <p>Gets the address phone number for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the address phone number for specified account.
     */
    public static String getPhone(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.ADDRESS_PHONE_NUMBER);
    }

    /**
     * <p>Gets the address country for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the address country for specified account.
     */
    public static String getCountry(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.ADDRESS_COUNTRY);
    }

    /**
     * <p>Gets the address fax number for the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the fax phone number for specified account.
     */
    public static String getFax(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.SPONSOR_FAX_NUMBER);
    }

    /**
     * <p>Gets the preferred payment method for player associated with the specified user account.</p>
     *
     * @param account a <code>UserProfile</code> providing details for user account.
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @return a <code>String</code> providing the preferred payment method.
     */
    public static String getPlayerPaymentMethodPref(UserProfile account, HttpServletRequest request, String paramName) {
        return resolveValue(account, request, paramName, UserConstants.PLAYER_PAYMENT_PREF);
    }

    /**
     * <p>Builds an URL which could be used for accessing the specified domain through HTTP protocol.</p>
     *
     * @param domain a <code>String</code> providing the domain name to build the URL for.
     * @return a <code>String</code> providing the URL for specified domain or <code>#</code> if specified <code>domain
     *         </code> is <code>null</code> or empty.
     */
    public static String buildDomainUrl(String domain) {
        if ((domain == null) || (domain.trim().length() == 0)) {
            return "#";
        }
        return "http://" + domain + "/";
    }

    public static String concat(String string, int i) {
        return string + i;
    }

    public static String concat(String string, long i) {
        return string + i;
    }

    public static String concat(String string, String i) {
        return string + i;
    }

    public static int getMaxDomainNum(HttpServletRequest request) {
        int r = 1;
        for (int i = 2; i <=5; i++) {
            if (request.getParameter("domain_" + i) != null) {
                r = i;
            }
        }
        return r;
    }

    public static boolean isImageCountLimitReached(HttpServletRequest request, int domainNum) {
        return request.getParameter("imageName_" + domainNum + "_5") != null;
    }

    public static String getMessengerPluginNamespace() {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            return cm.getString(NAMESPACE, "RSSDataStore.NameSpace");
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerException(e);
        }
    }

    public static PrizeCalculatorType getPrizeType(Game game, ServletContext context) {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            PrizeCalculatorTypeSource typeSource
                = (PrizeCalculatorTypeSource) context.getAttribute("PrizeCalcTypeSource");
            return typeSource.getPrizeCalculatorType(game.getPrizeCalculationType());
        } catch (PrizeException e) {
            throw new OrpheusGameServerAdminException("Could not obtain the prize calculation type", e);
        }
    }

    public static BouncePointCalculatorType getBouncePointType(Game game, ServletContext context) {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            BouncePointCalculatorTypeSource typeSource
                = (BouncePointCalculatorTypeSource) context.getAttribute("BouncePointCalcTypeSource");
            return typeSource.getBouncePointCalculatorType(game.getBouncePointCalculationType());
        } catch (BouncePointException e) {
            throw new OrpheusGameServerAdminException("Could not obtain the bounce point calculation type", e);
        }
    }

    /**
     * <p>Re-orders the specified domains list in reverse order. The last element of the array becomes first, etc. Note
     * that the method operates on the specified array but not on it's copy.</p>
     *
     * @param domains a <code>Domain</code> array providing the domains list to re-order.
     * @return a same <code>Domain</code> array with elements re-ordered.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static Domain[] reverseDomains(Domain[] domains) {
        return (Domain[]) reverseArray(domains);
    }

    /**
     * <p>Re-orders the specified hosting slots list in reverse order. The last element of the array becomes first, etc.
     * Note that the method operates on the specified array but not on it's copy.</p>
     *
     * @param slots a <code>HostingSlot</code> array providing the slots list to re-order.
     * @return a same <code>HostingSlot</code> array with elements re-ordered.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static HostingSlot[] reverseSlots(HostingSlot[] slots) {
        return (HostingSlot[]) reverseArray(slots);
    }

    /**
     * <p>Re-orders the specified list in reverse order. The last element of the array becomes first, etc. Note that the
     * method operates on the specified array but not on it's copy.</p>
     *
     * @param array an <code>Object</code> array providing the list to re-order.
     * @return a same <code>Object</code> array with elements re-ordered.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    private static Object[] reverseArray(Object[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The parameter [array] is NULL");
        }
        Object tmp;
        int i = 0;
        int j = array.length - 1;
        while (i < j) {
            tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
            i++;
            j--;
        }
        return array;
    }

    /**
     * <p>Gets the value of specified user profile property. If the specified request provides the value corresponding
     * to specified profile property then it takes precedence.</p>
     *
     * @param account a <code>UserProfile</code> representing the user account. 
     * @param request a <code>HttpServletRequest</code> representing the incoming request.
     * @param paramName a <code>String</code> providing the name of the request parameter to read value from.
     * @param propertyName a <code>String</code> providing the name of profile property to get value from.
     * @return a <code>String</code> providing the value of requested profile property.
     */
    private static String resolveValue(UserProfile account, HttpServletRequest request, String paramName,
                                       String propertyName) {
        String parameter = request.getParameter(paramName);
        if (parameter != null) {
            return parameter;
        } else {
            return (String) account.getProperty(propertyName);
        }
    }
}

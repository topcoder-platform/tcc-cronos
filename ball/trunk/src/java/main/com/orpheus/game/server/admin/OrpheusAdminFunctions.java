/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.admin;

import com.orpheus.administration.persistence.PendingWinner;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.server.comparator.admin.AdminAllGamesListComparator;
import com.orpheus.game.server.framework.bounce.BouncePointCalculatorType;
import com.orpheus.game.server.framework.bounce.BouncePointCalculatorTypeSource;
import com.orpheus.game.server.framework.game.completion.GameCompletionType;
import com.orpheus.game.server.framework.game.completion.GameCompletionTypeSource;
import com.orpheus.game.server.framework.prize.PrizeCalculatorType;
import com.orpheus.game.server.framework.prize.PrizeCalculatorTypeSource;
import com.orpheus.game.server.util.GameCreationType;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.formvalidator.validator.Message;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.web.tag.paging.DataPagingTag;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>An utility class providing the static functions to be utilized by JSPs when rendering the details for various
 * objects or for accessing the desired services. The functions are specific to <code>Orpheus Game Server Administration
 * </code> module.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class OrpheusAdminFunctions {

    /**
     * <p>A <code>String</code> providing the configuration namespace for custom classes from <code>Orpheus Game Server
     * </code> application.</p>
     */
    public static final String NAMESPACE = "com.orpheus.game.server";

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
     * <p>Constructs new <code>OrpheusAdminFunctions</code> instance. This implementation does nothing and is used for
     * preventing the instantiation of this utility class.</p>
     */
    private OrpheusAdminFunctions() {
    }

    public static String[] getGameNames(PendingWinner[] pendingWinners) {
        if (pendingWinners == null) {
            throw new IllegalArgumentException("The parameter [pendingWinners] is NULL");
        }
        String[] gameNames = new String[pendingWinners.length];
        for (int i = 0; i < pendingWinners.length; i++) {

            pendingWinners[i].getGameId();

        }
        return gameNames;
    }

    public static String[] getPlayerNames(PendingWinner[] pendingWinners) {
        if (pendingWinners == null) {
            throw new IllegalArgumentException("The parameter [pendingWinners] is NULL");
        }
        String[] gameNames = new String[pendingWinners.length];
        for (int i = 0; i < pendingWinners.length; i++) {

            pendingWinners[i].getGameId();

        }
        return gameNames;
    }

    public static String print(Map map) {
        return new HashMap(map).toString();
    }

    /**
     * <p>Gets the string presentation of a time left until specified auction closes.</p>
     *
     * @param auction an <code>Auction</code> providing the details for auction to get time left for.
     * @return a <code>String</code> providing the current time left until the specified auction is closed.
     */
    public static String getAuctionTimeLeft(Auction auction) {
        Date currentTime = new Date();

        long diff = auction.getEndDate().getTime() - currentTime.getTime();
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
    }

    /**
     * <p>Gets the auction corresponding to specified hosting block. It is assumed that auctions and blocks share same
     * IDs.</p>
     *
     * @param block a <code>HostingBlock</code> representing a block to get matching auction for.
     * @param auctions a <code>Auction</code> array providing the details for available auctions.
     * @return an <code>Auction</code> matching the specified block or <code>null</code> if such auction is not found.
     */
    public static Auction getAuction(HostingBlock block, Auction[] auctions) {
        for (int i = 0; i < auctions.length; i++) {
            if (auctions[i].getId().equals(block.getId())) {
                return auctions[i];
            }
        }
        return null;
    }

    /**
     * <p>Checks if the specified sponsor has placed at least 1 bid for specified auction.</p>
     *
     * @param sponsor a <code>UserProfile</code> providing details for sponsor account.
     * @param auction an <code>Auction</code> providing details for auction.
     * @return <code>true</code> if the specified sponsor has placed at least 1 bid for specified auction; <code>false
     *         </code> otherwise.
     */
    public static boolean isSponsorBidding(UserProfile sponsor, Auction auction) {
        long sponsorId = Long.parseLong(String.valueOf(sponsor.getIdentifier()));
        Bid[] bids = auction.getBids();
        for (int i = 0; i < bids.length; i++) {
            if (bids[i].getBidderId() == sponsorId) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Gets the list of bids which have been placed by the specified sponsor in context of the specified auction.</p>
     *
     * @param sponsor a <code>UserProfile</code> providing details for sponsor account.
     * @param auction an <code>Auction</code> providing details for auction.
     * @return a <code>List</code> of <code>Bid</code> instances providing the details for sponsor's bids.
     */
    public static List getSponsorBids(UserProfile sponsor, Auction auction) {
        long sponsorId = Long.parseLong(String.valueOf(sponsor.getIdentifier()));
        List result = new ArrayList();
        Bid[] bids = auction.getBids();
        for (int i = 0; i < bids.length; i++) {
            if (bids[i].getBidderId() == sponsorId) {
                result.add(bids[i]);
            }
        }
        return result;
    }

    public static int getBidStatus(Bid bid, Bid[] leadingBids) {
        // TODO : Finish this
        return 0;
    }

    /**
     * <p>Gets the maximum bid amount among the bids placed by the sponsor in context of a single auction.</p>
     *
     * @param sponsorBids a <code>List</code> of <code>Bid</code> instances.
     * @return an <code>int</code> providing the maximum bid amount among specified bids.
     */
    public static int getMaximumBid(List sponsorBids) {
        int max = 0;
        Bid bid;
        for (Iterator iterator = sponsorBids.iterator(); iterator.hasNext();) {
            bid = (Bid) iterator.next();
            max = Math.max(max, bid.getMaxAmount());
        }
        return max;
    }

    /**
     * <p>Gets the maximum bid amount among the bids placed by the sponsor in context of a single auction.</p>
     *
     * @param sponsorBids a <code>List</code> of <code>Bid</code> instances.
     * @return <code>true</code> if at least on of specified bids have reached the specified maximum bid amount;
     *         <code>false</code> otherwise.
     */
    public static boolean isMaximumBidReached(List sponsorBids, int maximumBidAmount) {
        Bid bid;
        for (Iterator iterator = sponsorBids.iterator(); iterator.hasNext();) {
            bid = (Bid) iterator.next();
            if (bid.getEffectiveAmount().intValue() >= maximumBidAmount) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Gets the leading bids for the specified auction</p>
     *
     * @param auction an <code>Auction</code> providing details for an auction.
     * @param leadingBids a <code>Map</code> mapping the auction ID (<code>Long</code>) to leading bids for respective
     *        auction.
     * @return a <code>Bid</code> array providing the leading bids for specified auction or <code>null</code> if such
     *         bids are not found.
     */
    public static Bid[] getLeadingBids(Auction auction, Map leadingBids) {
        Long id = auction.getId();
        return (Bid[]) leadingBids.get(id);
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
        return Arrays.asList(items);
    }

    /**
     * <p>Gets the comparator to be used for sorting the list of registered games displayed to administrator.</p>
     *
     * @return a <code>AdminAllGamesListComparator</code> to be used for sorting the list of games displayed to admin.
     */
    public static AdminAllGamesListComparator getAdminAllGamesComparator() {
        return new AdminAllGamesListComparator();
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
        if (account == null) {
            return null;
        } else {
            return (String) account.getProperty(UserConstants.CREDENTIALS_HANDLE);
        }
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
     * <p>Gets the minimum payout for the specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for game to get minimum payout for.
     * @return a <code>double</code> providing the minimum payout for the game.
     */
    public static double getMinimumPayout(Game game) {
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
            throw new OrpheusGameServerAdminException("The administration fee provided by configuration is invalid", e);
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerAdminException("The administration fee can not be read from configuration", e);
        }
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
                return slots[j].getDomain().getDomainName();
            }
        }
        return null;
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
     * <p>Gets the validation error which might have been reported for the specified form input field while validating
     * the data submitted by user through web-form. The error (if any) is formatted to </p>
     *
     * @param inputFieldName a <code>String</code> providing the name of the form input field.
     * @param errors a <code>Map</code> mapping the <code>String</code> error key to <code>Message</code> providing
     *        details for error message.
     * @return a <code>String</code> providin the validation error for specified input field or <code>null</code> if
     *         there are no validation errors reported.
     */
    public static String error(String inputFieldName, Map errors, int colSpan) {
        if ((errors != null) && (errors.containsKey(inputFieldName))) {
            Message message = (Message) errors.get(inputFieldName);
            if (message != null) {
                return "<tr><td colspan=\"" + colSpan + "\"><span class=\"fBold cRed\">" + message.getMessage() + "</span></td></tr>";
            }
        }
        return null;
    }

    /**
     * <p>Evaluates the current status of specified slot.</p>
     *
     * @param slot a <code>HostingSlot</code> to get status for.
     * @return an <code>int</code> providing the slot status.
     */
    public static int getSlotStatus(HostingSlot slot) {
        if (slot == null) {
            throw new IllegalArgumentException("The parameter [slot] is NULL");
        }
        if (slot.getHostingEnd() != null) {
            // finished
            return 0;
        } else if (slot.getHostingStart() != null && slot.getHostingEnd() == null) {
            // active
            return 1;
        }
        // pending
        return 2;
    }

    /**
     * <p>Checks if the specified index corresponds to last element in the specified array.</p>
     *
     * @param array an <code>Object</code> array representing the target array to compare index against.
     * @param index an <code>int</code> representing the current index.
     * @return <code>true</code> if specified <code>index</code> corresponds to last element of an array; <code>false
     *         </code> otherwise.
     */
    public static boolean isLast(Object[] array, int index) {
        if (array == null) {
            throw new IllegalArgumentException("The parameter [array] is NULL");
        }
        return (index + 1) == array.length;
    }

    /**
     * <p>Produces an HTML <code>select</code> attribute if specified date has specified day.</p>
     *
     * @param date a <code>Date</code> providing the original time.
     * @param day an <code>int</code> providing the day to test.
     * @param request an <code>HttpServletRequest</code> representing the incoming request from the client.
     * @param paramName a <code>String</code> providing the name of the request parameter which may provide actual date
     *        value.
     * @param datePattern a <code>String</code> providing the pattern to be used to parse the value of specified request
     *        parameter into a <code>Date</code> instance.
     * @return a <code>String</code> which is either <code>selected="selected"</code> if specified value must be
     *         selected; or <code>empty</code> string otherwise.
     * @throws ParseException if a date provided by the specified parameter of request can not be parsed in accordance
     *         with specified pattern.
     */
    public static String selectIfDayMatches(Date date, int day, HttpServletRequest request, String paramName,
                                            String datePattern) throws ParseException {
        return select(date, Calendar.DAY_OF_MONTH, day, request, paramName, datePattern);
    }

    public static String selectIfMonthMatches(Date date, int month, HttpServletRequest request, String paramName,
                                             String datePattern) throws ParseException {
        return select(date, Calendar.MONTH, month, request, paramName, datePattern);
    }

    public static String selectIfYearMatches(Date date, int year, HttpServletRequest request, String paramName,
                                             String datePattern) throws ParseException {
        return select(date, Calendar.YEAR, year, request, paramName, datePattern);
    }

    public static String selectIfHourMatches(Date date, int hour, HttpServletRequest request, String paramName,
                                             String datePattern, boolean ampm) throws ParseException {
        if (ampm) {
            return select(date, Calendar.HOUR, hour, request, paramName, datePattern);
        } else {
            return select(date, Calendar.HOUR_OF_DAY, hour, request, paramName, datePattern);
        }
    }

    public static String selectIfMinuteMatches(Date date, int minute, HttpServletRequest request, String paramName,
                                               String datePattern) throws ParseException {
        return select(date, Calendar.MINUTE, minute, request, paramName, datePattern);
    }

    public static String selectIfAMPMMatches(Date date, int ampm, HttpServletRequest request, String paramName,
                                             String datePattern) throws ParseException {
        return select(date, Calendar.AM_PM, ampm, request, paramName, datePattern);
    }

    /**
     * <p>Re-orders the specified list in reverse order. The last element of the array becomes first, etc. Note that the
     * method operates on the specified array but not on it's copy.</p>
     *
     * @param array an <code>Object</code> array providing the list to re-order.
     * @return a same <code>Object</code> array with elements re-ordered.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static Object[] reverseArray(Object[] array) {
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

    public static String getTime(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The parameter [date] is NULL");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String day = String.valueOf(calendar.get(Calendar.HOUR));
        if (day.length() == 1) {
            day = '0' + day;
        }
        String min = String.valueOf(calendar.get(Calendar.MINUTE));
        if (min.length() == 1) {
            min = '0' + min;
        }
        return day + ":" + min;
    }

    public static Long getLong(long value) {
        return new Long(value);
    }

    /**
     * <p>Converts the specified time expressed in milliseconds to a textual presentation.</p>
     *
     * @param time a <code>long</code> providing the time to convert.
     * @return a <code>String</code> providing the textual presentation of specified time.
     */
    public static String convertTime(long time) {
        long days = time / (24 * 60 * 60 * 1000);
        time %= (24 * 60 * 60 * 1000);
        long hours = time / (60 * 60 * 1000);
        time %= (60 * 60 * 1000);
        long minutes = time / (60 * 1000);

        StringBuffer buffer = new StringBuffer();
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
    }

    public static PrizeCalculatorType getPrizeType(Game game, ServletContext context) {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            PrizeCalculatorTypeSource typeSource = (PrizeCalculatorTypeSource) context.getAttribute("PrizeCalcTypeSource");
            return typeSource.getPrizeCalculatorType(game.getPrizeCalculationType());
        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new OrpheusGameServerAdminException("Could not obtain the bounce point calculation type", e);
        }
    }

    public static GameCompletionType getCompletionType(Game game, ServletContext context) {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            GameCompletionTypeSource typeSource
                = (GameCompletionTypeSource) context.getAttribute("GameCompletionTypeSource");
            return typeSource.getGameCompletionType(game.getCompletionType());
        } catch (Exception e) {
            throw new OrpheusGameServerAdminException("Could not obtain the game completion type", e);
        }
    }

    /**
     * <p>Gets the list of supported game creation types.</p>
     *
     * @return a <code>List</code> containing the <code>GameCreationType</code> instances representing the game creation
     *         types currently supported.
     */
    public static List getGameCreationTypes() {
        return Enum.getEnumList(GameCreationType.class);
    }

    /**
     * <p>Checks if the specified block is exhausted, i.e. there are no slots in the block which hasn't started hosting
     * yet.</p>
     *
     * @param block a <code>HostingBlock</code> representing a block to check. 
     * @return <code>true</code> if specified block is exhausted; <code>false</code> otherwise.
     */
    public static boolean isBlockExhausted(HostingBlock block) {
        if (block == null) {
            throw new IllegalArgumentException("The parameter [block] is NULL");
        }
        HostingSlot[] slots = block.getSlots();
        for (int i = 0; i < slots.length; i++) {
            HostingSlot slot = slots[i];
            if (slot.getHostingStart() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if it is allowed to add a new slot to specified block from specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for the agem.
     * @param blockId a <code>long</code> providing the ID of a block to check.
     * @return <code>true</code> if new slot can be added to specified block; <code>false</code> otherwise.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static boolean canAddSlot(Game game, long blockId) {
        if (game == null) {
            throw new IllegalArgumentException("The parameter [game] is NULL");
        }
        boolean isHostingAfter = false;
        boolean targetBlockFound = false;
        HostingBlock[] blocks = game.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            HostingBlock block = blocks[i];
            if (block.getId().longValue() == blockId) {
                targetBlockFound = true;
                HostingSlot[] slots = block.getSlots();
                for (int j = 0; j < slots.length; j++) {
                    HostingSlot slot = slots[j];
                    if (slot.getHostingStart() != null && slot.getHostingEnd() == null) {
                        if (j < (slots.length - 1)) {
                            return true;
                        }
                    }
                }
            } else {
                HostingSlot[] slots = block.getSlots();
                for (int j = 0; j < slots.length; j++) {
                    HostingSlot slot = slots[j];
                    if (slot.getHostingStart() != null && slot.getHostingEnd() == null) {
                        if (targetBlockFound) {
                            isHostingAfter = true;
                        }
                    }
                }
            }
        }
        return !isHostingAfter;
    }

    /**
     * <p>Checks if specified block is hosting The Ball currently.</p>
     *
     * @param block a <code>HostingBlock</code> to check.
     * @return <code>true</code> if specified block is hosting the ball; <code>false</code> otherwise.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static boolean isHostingBall(HostingBlock block) {
        if (block == null) {
            throw new IllegalArgumentException("The parameter [block] is NULL");
        }
        HostingSlot[] slots = block.getSlots();
        for (int j = 0; j < slots.length; j++) {
            HostingSlot slot = slots[j];
            if ((slot.getHostingStart() != null) && (slot.getHostingEnd() == null)) {
                return true;
            }
        }
        return false;
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
     *
     * @param date a <code>Date</code> providing the original time. 
     * @param dateField an <code>int</code> providing a calendar field to get the date part to be tested.
     * @param value an <code>int</code> providing the value to test the date part against.
     * @param request an <code>HttpServletRequest</code> representing the incoming request from the client.
     * @param paramName a <code>String</code> providing the name of the request parameter which may provide actual date
     *        value.
     * @param datePattern a <code>String</code> providing the pattern to be used to parse the value of specified request
     *        parameter into a <code>Date</code> instance. 
     * @return a <code>String</code> which is either <code>selected="selected"</code> if specified value must be
     *         selected; or <code>empty</code> string otherwise.  
     * @throws ParseException if a date provided by the specified parameter of request can not be parsed in accordance
     *         with specified pattern.
     */
    private static String select(Date date, int dateField, int value, HttpServletRequest request, String paramName,
                                 String datePattern) throws ParseException {
        
        if (request == null) {
            throw new IllegalArgumentException("The parameter [request] is NULL");
        }
        if (date == null) {
            throw new IllegalArgumentException("The parameter [date] is NULL");
        }
        if ((datePattern == null) || (datePattern.trim().length() == 0)) {
            throw new IllegalArgumentException("The parameter [datePattern] is not valid. [" + datePattern + "]");
        }
        // The actual a date to test
        Date dateToTest = date;

        // The date provided by request parameter takes precedence
        if (paramName != null) {
            String paramValue = request.getParameter(paramName);
            if ((paramValue != null) && (paramValue.trim().length() > 0)) {
                DateFormat df = new SimpleDateFormat(datePattern);
                dateToTest = df.parse(paramValue);
            }
        }

        // Check if date's field month matches the specified value
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToTest);
        if (calendar.get(dateField) == value) {
            return "selected=\"selected\"";
        } else {
            return "";
        }
    }
}

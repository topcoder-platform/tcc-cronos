/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.auction;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.formvalidator.validator.Message;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.web.registration.RegistrationManager;
import com.topcoder.web.registration.WebRegistrationConfigurationException;
import com.topcoder.web.tag.paging.DataPagingTag;
import com.topcoder.web.user.LoginHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>An utility class providing the static functions to be utilized by JSPs when rendering the details for various
 * objects or for accessing the desired services.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class OrpheusAuctionFunctions {

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
     * <p>Constructs new <code>OrpheusAuctionFunctions</code> instance. This implementation does nothing and is used for
     * preventing the instantiation of this utility class.</p>
     */
    private OrpheusAuctionFunctions() {
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
                return slots[j].getDomain().getDomainName();
            }
        }
        return null;
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
            throw new OrpheusGameServerAuctionException("The administration fee provided by configuration is invalid",
                                                        e);
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerAuctionException("The administration fee can not be read from configuration", e);
        }
    }

    /**
     * <p>Gets the current status for the specified game.</p>
     *
     * @param game a <code>Game</code> providing the details for game to get current status for.
     * @return a <code>String</code> providing the current status for the game.
     */
    public static String getGameStatus(Game game) {
        Date startDate = game.getStartDate();
        Date endDate = game.getEndDate();
        Date currentTime = new Date();
        if (currentTime.compareTo(startDate) < 0) {
            return GAME_STATUS_NOT_STARTED;
        } else {
            if (endDate == null) {
                return GAME_STATUS_IN_PROGRESS;
            } else {
                return GAME_STATUS_COMPLETED;
            }
        }
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
     * <p>Gets the size of specified array.</p>
     *
     * @param array an <code>Object</code> array to get the size for.
     * @return an <code>int</code> providing the size of specified array.
     */
    public static int getSize(Object[] array) {
        return array.length;
    }

    /**
     * <p>Gets the size of specified list.</p>
     *
     * @param list a <code>List</code> to get the size for.
     * @return an <code>int</code> providing the size of specified list.
     */
    public static int getSize(List list) {
        return list.size();
    }

    /**
     * <p>Gets the string presentation of a time left until specified auction closes.</p>
     *
     * @param prep a <code>String</code> providing the string to prepend the returned value with.
     * @param auction an <code>Auction</code> providing the details for auction to get time left for.
     * @return a <code>String</code> providing the current time left until the specified auction is closed.
     */
    public static String getAuctionTimeLeft(String prep, Auction auction) {
        Date currentTime = new Date();

        long diff = auction.getEndDate().getTime() - currentTime.getTime();
        if (diff <= 0) {
            return "Ended";
        }
        return prep + convertTime(diff);
    }

    /**
     * <p>Gets the string presentation of a max duration time for specified hosting block.</p>
     *
     * @param block an <code>HostingBlock</code> providing the details for hosting block to get time left for.
     * @return a <code>String</code> providing the max duration time for specified hosting block.
     */
    public static String getMaxDuration(HostingBlock block) {
        return convertTime(block.getMaxHostingTimePerSlot() * 60 * 1000);
    }

    /**
     * <p>Converts the specified time expressed in milliseconds to a textual presentation.</p>
     *
     * @param time a <code>long</code> providing the time to convert.
     * @return a <code>String</code> providing the textual presentation of specified time.
     */
    private static String convertTime(long time) {
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
     * <p>Gets the status of specified bid comparing to specified list of leading bids for auction.</p>
     *
     * @param bid a <code>Bid</code> representing a bid to get status for.
     * @param leadingBids a <code>Bid</code> array providing the list of leading bids for auction.
     * @return <code>0</code> if current bid is a highest bid; <code>1</code> if current bid is a qualifying bid; <code>
     *         2</code> if current bid is outbid.
     * @throws IllegalArgumentException if any of specified arguments is <code>null</code>.
     */
    public static int getBidStatus(Bid bid, Bid[] leadingBids) {
        if (bid == null) {
            throw new IllegalArgumentException("The parameter [bid] is NULL");
        }
        if (leadingBids == null) {
            throw new IllegalArgumentException("The parameter [leadingBids] is NULL");
        }
        if (bid.getEffectiveAmount() == null) {
            return 2;
        }
        boolean found = false;
        boolean higherExists = false;
        for (int i = 0; i < leadingBids.length; i++) {
            if (leadingBids[i].getBidderId() != bid.getBidderId()) {
                if (leadingBids[i].getEffectiveAmount().compareTo(bid.getEffectiveAmount()) > 0) {
                    higherExists = true;
                }
            } else if (leadingBids[i].getEffectiveAmount().equals(bid.getEffectiveAmount())) {
                found = true;
            }
        }
        if (!found) {
            // Outbid
            return 2;
        } else if (higherExists) {
            // Qualifying
            return 1;
        } else {
            // Highest Bidder
            return 0;
        }
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
     * @param maximumBidAmount an <code>int</code> providing the maximum bid amount.
     * @return <code>true</code> if at least on of specified bids have reached the specified maximum bid amount;
     *         <code>false</code> otherwise.
     */
    public static boolean isMaximumBidReached(List sponsorBids, int maximumBidAmount) {
        Bid bid;
        for (Iterator iterator = sponsorBids.iterator(); iterator.hasNext();) {
            bid = (Bid) iterator.next();
            if (bid.getEffectiveAmount() == null) {
                continue;
            }
            if (bid.getEffectiveAmount().intValue() >= maximumBidAmount) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Gets the leading bids for the specified auction.</p>
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
     * <p>Gets the minimum leading bid among the specified leading bids for the auction. The method assumes that the
     * bids are sorted in a descending order based on effective bid amounts.</p>
     *
     * @param leadingBids a <code>Bid</code> array providing the leading bids for auction.
     * @return a <code>Bid</code> providing the minimum bid among the specified ones or <code>null</code> if specified
     *         list is empty.
     */
    public static Bid getMinimumBid(Bid[] leadingBids) {
        if (leadingBids == null) {
            throw new IllegalArgumentException("The parameter [leadingBids] is NULL");
        }
        if (leadingBids.length > 0) {
            return leadingBids[leadingBids.length - 1];
        } else {
            return null;
        }
    }

    /**
     * <p>Converts the specified string value to numeric value of <code>Long</code> type.</p>
     *
     * @param value a <code>String</code> value to convert.
     * @return a <code>Long</code> providing the converted value of specified string.
     * @throws NumberFormatException if specified value does not represent a numeric value.
     */
    public static Long toLong(String value) {
        return new Long(value);
    }

    /**
     * <p>Gets the maximum leading bid among the specified leading bids for the auction. The method assumes that the
     * bids are sorted in a descending order based on effective bid amounts.</p>
     *
     * @param leadingBids a <code>Bid</code> array providing the leading bids for auction.
     * @return a <code>Bid</code> providing the maximum bid among the specified ones or <code>null</code> if specified
     *         list is empty.
     */
    public static Bid getMaximumBid(Bid[] leadingBids) {
        if (leadingBids == null) {
            throw new IllegalArgumentException("The parameter [leadingBids] is NULL");
        }
        if (leadingBids.length > 0) {
            return leadingBids[0];
        } else {
            return null;
        }
    }

    /**
     * <p>Gets the minimum bid value necessary to qualify for specified auction.</p>
     *
     * @param auction an <code>Auction</code> providing details for current auction.
     * @param leadingBids a <code>Bid</code> array providing the list of leading bids for auction.
     * @return an <code>int</code> providing the minimum bid value necessary to qualify for the specified auction.
     */
    public static int getMinimuAllowedBid(Auction auction, Bid[] leadingBids) {
        if (auction == null) {
            throw new IllegalArgumentException("The parameter [auction] is NULL");
        }
        int leadingBidsMax = -1;
        if (leadingBids != null) {
            if ((leadingBids.length > 0) && (leadingBids.length >= auction.getItemCount())) {
                leadingBidsMax = leadingBids[leadingBids.length - 1].getEffectiveAmount().intValue();
            }
        }
        return Math.max(auction.getMinimumBid(), leadingBidsMax + 1);
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
     * <p>Gets the maximum number of bids which are allowed to be placed by a single sponsor in context of a single
     * auction which has the specified number of slots available.</p>
     *
     * @param slotCount an <code>int</code> providing the number of slots in the auction.
     * @return an <code>int</code> providing the maximum number of bids which are allowed to be placed by a single
     *         sponsor in context of a single auction with specified number of slots available.
     */
    public static int getMaximumBidsCount(int slotCount) {
        try {
            String fractString = ConfigManager.getInstance().getString(NAMESPACE, "BidNumberLimit");
            double fraction = Double.parseDouble(fractString);
            return (int) Math.ceil(fraction * slotCount);
        } catch (NumberFormatException e) {
            throw new OrpheusGameServerAuctionException("The adminstration fee provided by configuration is invalid",
                                                        e);
        } catch (UnknownNamespaceException e) {
            throw new OrpheusGameServerAuctionException("The adminstration fee can not be read from configuration", e);
        }
    }
}

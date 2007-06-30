/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.auction;

import com.orpheus.auction.AuctionListenerImpl;
import com.orpheus.auction.AuctionListenerImplException;
import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.user.persistence.UserConstants;
import com.topcoder.message.messenger.MessageAPI;
import com.topcoder.message.messenger.Messenger;
import com.topcoder.message.messenger.MessengerPlugin;
import com.topcoder.user.profile.BaseProfileType;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.fieldconfig.Field;
import com.topcoder.util.file.fieldconfig.TemplateFields;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

import javax.servlet.ServletContext;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>A custom implementation of <code>AuctionListener</code> which is to be used in context of <code>Game Server
 * Auction</code> module. This class delegates to super-class for handling the auction events. The main purpose of this
 * class is to send notification emails to administrator and auction winners once the auction is completed.</p>
 *
 * @author isv
 * @version 1.0
 */
public class OrpheusAuctionListener extends AuctionListenerImpl {

    /**
     * <p>A <code>String</code> providing the ID of a template source to be passed to <code>Document Generator</code>
     * for locating the template.</p>
     */
    public static final String TEMPLATE_SOURCE_ID = "classpath";

    /**
     * <p>A <code>String</code> providing the <code>Administrator</code> role.</p>
     */
    public static final String ADMIN_ROLE = "admin";

    /**
     * <p>A <code>UserProfile</code> used to locate the profiles for sponsors winning the auction slots.</p>
     */
    private final UserProfileManager profileManager;

    /**
     * <p>The servlet context in which this listener operates</p>
     */
    private final ServletContext context;

    /**
     * <p>Constructs new <code>OrpheusAuctionListener</code> instance with specified servlet context instance.</p>
     *
     * @param context a <code>ServletContext</code> providing the configuration for this listener.
     */
    public OrpheusAuctionListener(ServletContext context) {
        super(context);

	this.context = context;

        try {
            ConfigManagerSpecificationFactory specFactory
                    = new ConfigManagerSpecificationFactory(context.getInitParameter("ObjectFactoryNamespace"));
            ObjectFactory objectFactory = new ObjectFactory(specFactory);
            String userProfileKey = context.getInitParameter("UserProfileManagerID");

            this.profileManager = (UserProfileManager) objectFactory.createObject(userProfileKey);
        } catch (Exception e) {
            throw new OrpheusGameServerAuctionException("Could not initialize auction listener", e);
        }
    }

    /**
     * <p>Alerts this listener that an auction has been completed. </p> <p> Notifies the game data manager service of
     * the winning bids and then requests the administration manager service to initialize the slots for the
     * corresponding block. For this purposes, auction id of the completed auction is used as the block id required by
     * the game data manager. </p>
     *
     * @param auction the auction that has completed.
     * @throws IllegalArgumentException if argument is null.
     * @throws AuctionListenerImplException if error occurs during processing.
     */
    public void auctionComplete(Auction auction) {
        super.auctionComplete(auction);

        // Get all winners and notify them on win
        List winners = new ArrayList();
        long winnerId;
        UserProfile winnerProfile;
        Bid[] bids = auction.getBids();
        List winnerBids = new ArrayList();
        for (int i = 0; i < bids.length; i++) {
            // Filter out non-winning bids
            if (bids[i].getEffectiveAmount() != null) {
                try {
                    winnerId = bids[i].getBidderId();
                    winnerProfile = this.profileManager.getUserProfile(winnerId);
                    sendEmailToSlotWinner(auction, bids[i], winnerProfile);
                    winners.add(winnerProfile);
                    winnerBids.add(bids[i]);
                } catch (Exception e) {
                    // An exception is caught and logged so other winners also have a chance to be notified
                    e.printStackTrace();
                }
            }
        }
        // Get the list of adminstrators and notify them on each winner of the slot
        sendEmailToAdministrator(auction, winners, winnerBids);
    }

    /**
     * <p>Sends a notification email to a sponsor who have won an auction for a single slot.</p>
     *
     * @param auction an <code>Auction</code> providing the details for completed auction.
     * @param bid a <code>Bid</code> providing the details for a bid won by the sponsor.
     * @param winnerProfile a <code>UserProfile</code> providing the details
     */
    private void sendEmailToSlotWinner(Auction auction, Bid bid, UserProfile winnerProfile) {
        // Generate message body from template and send email to administrator
        try {
            DocumentGenerator docGenerator = DocumentGenerator.getInstance();
            Template template = docGenerator.getTemplate(TEMPLATE_SOURCE_ID,
                    "com/orpheus/game/server/template/SponsorAuctionWinnerTemplate.txt");
            com.topcoder.util.file.fieldconfig.Node[] nodes = new com.topcoder.util.file.fieldconfig.Node[3];
            nodes[0] = new Field("credentials-handle", OrpheusAuctionFunctions.getHandle(winnerProfile),
                                 "credentials-handle", true);
            nodes[1] = new Field("AUCTION_ID", String.valueOf(auction.getId()), "AUCTION_ID", true);
            nodes[2] = new Field("BID_AMOUNT", String.valueOf(bid.getEffectiveAmount()), "BID_AMOUNT", true);

            // Generate the message body from template
            TemplateFields fields = new TemplateFields(nodes, template);
            sendEmail((String) winnerProfile.getProperty(BaseProfileType.EMAIL_ADDRESS),
                      docGenerator.applyTemplate(fields), "Auction #" + auction.getId() + " Results");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Sends a notification email to <code>Game Server</code> administrator notifying on the sponsors winning the
     * slots from specified auction.</p>
     *
     * @param auction an <code>Auction</code> providing the details for completed auction.
     * @param winners a <code>List</code> of <code>UserProfile</code> objects providing details for sponsors who have
     *        won an auction for a single slot from the specified auction.
     * @param bids a <code>Bid</code> array providing the details for winning bids. The elements are in direct
     *        correspondence with elements in <code>winners</code> list.
     */
    private void sendEmailToAdministrator(Auction auction, List winners, List bids) {
        String adminEmailAddress = this.context.getInitParameter("AdminEmailAddress");
        CustomBid bid;
        UserProfile userProfile;
        int index = 0;

        // Build XML data
        StringBuffer xml = new StringBuffer();
        xml.append("<DATA>");
        xml.append("<AUCTION_ID>");
        xml.append(auction.getId());
        xml.append("</AUCTION_ID>");
        for (Iterator iterator = winners.iterator(); iterator.hasNext();) {
            bid = (CustomBid) bids.get(index++);
            userProfile = (UserProfile) iterator.next();
            xml.append("<WINNER>");
            // Email
            xml.append("<EMAIL>");
            xml.append(userProfile.getProperty(BaseProfileType.EMAIL_ADDRESS));
            xml.append("</EMAIL>");
            // First Name
            xml.append("<FIRST>");
            xml.append(userProfile.getProperty(BaseProfileType.FIRST_NAME));
            xml.append("</FIRST>");
            // Last Name
            xml.append("<LAST>");
            xml.append(userProfile.getProperty(BaseProfileType.LAST_NAME));
            xml.append("</LAST>");
            // Bid #
            xml.append("<BIDID>");
            xml.append(bid.getId());
            xml.append("</BIDID>");
            // Bid value
            xml.append("<BIDAMNT>");
            xml.append(bid.getEffectiveAmount());
            xml.append("</BIDAMNT>");
            // URL
            xml.append("<URL>");
            xml.append("TODO : ");
            xml.append("</URL>");
            // Phone
            xml.append("<PHONE>");
            xml.append(userProfile.getProperty(UserConstants.ADDRESS_PHONE_NUMBER));
            xml.append("</PHONE>");
            // Payment Pref
            xml.append("<PREF>");
            xml.append(userProfile.getProperty(UserConstants.SPONSOR_PAYMENT_PREF));
            xml.append("</PREF>");
            xml.append("</WINNER>");
        }
        xml.append("</DATA>");

        // Generate message body from template and send email to administrator
        try {
            DocumentGenerator docGenerator = DocumentGenerator.getInstance();
            Template template = docGenerator.getTemplate(TEMPLATE_SOURCE_ID,
                    "com/orpheus/game/server/template/AdminAuctionWinnersTemplate.txt");
            StringWriter stringWriter = new StringWriter();
            docGenerator.applyTemplate(template, new StringReader(xml.toString()), stringWriter);
            sendEmail(adminEmailAddress, stringWriter.getBuffer().toString(),
                      "Winners for Auction #" + auction.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Sends an email to specified address with specified message body and subject. Any exception which might occur
     * while sending an email is caught and logged.</p>
     *
     * @param to a <code>String</code> providing the email address of recipient.
     * @param body a <code>String</code> providing the email message body.
     * @param subject a <code>String</code> providing the email message subject.
     */
    private void sendEmail(String to, String body, String subject) {
        try {
            String pluginName = this.context.getInitParameter("OrpheusMessagePlugin");
            MessengerPlugin plugin = Messenger.createInstance().getPlugin(pluginName);
            MessageAPI msg = plugin.createMessage();
            msg.setParameterValue("from", this.context.getInitParameter("FromEmailAddress"));
            msg.setParameterValue("to", new String[] {to});
            msg.setParameterValue("body", body);
            msg.setParameterValue("subject", subject);
            plugin.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

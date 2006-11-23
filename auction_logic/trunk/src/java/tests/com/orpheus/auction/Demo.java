/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.cactus.ServletTestCase;
import org.apache.cactus.WebRequest;
import org.w3c.dom.Element;

import com.orpheus.auction.persistence.CustomBid;
import com.topcoder.user.profile.UserProfile;
import com.topcoder.user.profile.manager.UserProfileManager;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.Bid;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.user.LoginHandler;

/**
 * <p>
 * Component demonstration for the Auction Logic component.
 * </p>
 * <p>
 * This class demonstrates how to:
 * <ul>
 * <li>Retrieve the currently open auctions.</li>
 * <li>Determine the current leading bids for the specified auction.</li>
 * <li>Place new bid.</li>
 * <li>Update existing bid.</li>
 * </ul>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends ServletTestCase {
    /**
     * <p>
     * ActionContext instance. Used for testing.
     * </p>
     */
    private ActionContext actionContext;

    /**
     * <p>
     * MockAuctionManager instance. Used for testing.
     * </p>
     */
    private MockAuctionManager auctionManager;

    /**
     * <p>
     * MockAuctionPersistence instance. Used for testing.
     * </p>
     */
    private MockAuctionPersistence auctionPersistence;

    /**
     * <p>
     * Element instance used for testing.
     * </p>
     */
    private Element openAuctionsElement;

    /**
     * <p>
     * Element instance used for testing.
     * </p>
     */
    private Element leadingBidsElement;

    /**
     * <p>
     * Element instance used for testing.
     * </p>
     */
    private Element bidPlacementElement;

    /**
     * <p>
     * Element instance used for testing.
     * </p>
     */
    private Element bidUpdateElement;

    /**
     * <p>
     * Holds the date two days before the current one.
     * </p>
     */
    private Calendar twoDaysBefore;

    /**
     * <p>
     * Holds the date two days after the current one.
     * </p>
     */
    private Calendar twoDaysAfter;

    /**
     * <p>
     * Holds the date one week before the current date.
     * </p>
     */
    private Calendar weekBefore;

    /**
     * <p>
     * Holds the date one week after the current one.
     * </p>
     */
    private Calendar weekAfter;

    /**
     * <p>
     * LoginHandler instance. Used for testing.
     * </p>
     */
    private LoginHandler loginHandler;

    /**
     * <p>
     * UserProfileManager instance. Used for testing.
     * </p>
     */
    private UserProfileManager profileManager;

    /**
     * <p>
     * Creates new required instances.
     * </p>
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        // used by KeyConstants
        UnitTestsHelper.loadConfig(UnitTestsHelper.CONFIG_FILE);

        actionContext = new ActionContext(request, response);
        auctionManager = new MockAuctionManager();
        auctionPersistence = new MockAuctionPersistence();

        // remove attribute from the servlet context if it exists
        request.getSession().getServletContext().removeAttribute(
            KeyConstants.ADMINISTRATION_MANAGER_KEY);
        request.getSession().getServletContext().removeAttribute(KeyConstants.AUCTION_MANAGER_KEY);
        request.getSession().getServletContext()
            .removeAttribute(KeyConstants.GAME_DATA_MANAGER_KEY);

        // get current date
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        twoDaysBefore = (Calendar) curDate.clone();
        twoDaysBefore.add(Calendar.DATE, -2);
        twoDaysAfter = (Calendar) curDate.clone();
        twoDaysAfter.add(Calendar.DATE, 2);

        weekBefore = (Calendar) curDate.clone();
        weekBefore.add(Calendar.DATE, -7);
        weekAfter = (Calendar) curDate.clone();
        weekAfter.add(Calendar.DATE, 7);

        openAuctionsElement = UnitTestsHelper.createHandlerElement("OpenAuctions",
            new String[] {"open_auction_key"}, new String[] {"key"});
        leadingBidsElement = UnitTestsHelper.createHandlerElement("LeadingBidsHandler",
            new String[] {"auction_id_param_key", "leading_bids_key"}, new String[] {
                "auction",
                "bids"});
        bidPlacementElement = UnitTestsHelper.createHandlerElement("BidPlacementHandler",
            new String[] {"auction_id_param_key", "max_amount_param_key", "image_id_param_key"},
            new String[] {"auction_id_key", "max_amount_param_key", "image_id_key"});
        bidUpdateElement = UnitTestsHelper.createHandlerElement("BidUpdateHandler", new String[] {
            "auction_id_param_key",
            "max_amount_param_key",
            "bid_id_param_key"}, new String[] {"auction_id_key", "max_amount_key", "bid_id_key"});

        // initialize profile manager
        profileManager = new MockUserProfileManager();

        // initialize login handler
        Map attrs = new HashMap();
        attrs.put("profile_manager", profileManager);
        attrs.put("request_param_keys", new String[] {"firstName", "lastName", "e-mail"});
        attrs.put("search_profile_keys", new String[] {"first_name", "last_name"});
        attrs.put("credential_profile_keys", new String[] {"email_address"});
        attrs.put("no_such_profile_result_name", "no_such_profile_result_name");
        attrs.put("incorrect_credential_result_name", "incorrect_credential_result_name");
        loginHandler = new LoginHandler(attrs);

    }

    /**
     * <p>
     * Clears configuration and removes attributes from the ServletContext.
     * </p>
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        UnitTestsHelper.clearConfig();

        HttpSession session = request.getSession(false);
        if (session != null) {
            // remove attributes from the servlet context if any
            session.getServletContext().removeAttribute(KeyConstants.ADMINISTRATION_MANAGER_KEY);
            session.getServletContext().removeAttribute(KeyConstants.AUCTION_MANAGER_KEY);
            session.getServletContext().removeAttribute(KeyConstants.GAME_DATA_MANAGER_KEY);
            // invalidate session if it exists
            session.invalidate();
        }
    }

    /**
     * <p>
     * Sets up the environment before calling OpenAuctionsHandler#execute.
     * </p>
     */
    private void setUpOpenAuctionsHandler() {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        // add three open auctions
        for (int i = 3; i < 6; i++) {
            addAuctionToPersistence(i, twoDaysBefore.getTime(), twoDaysAfter.getTime());
        }
    }

    /**
     * <p>
     * Demonstrates usage of OpenAuctionsHandler.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testOpenAuctionsHandler() throws Exception {
        // create new OpenAuctionsHandler
        // assume that openAuctionsElement variable represents the following xml:
        // <handler type="x">
        // <open_auction_key>auctions_key</open_auction_key>
        // </handler>
        // this handler will store the retrieved auctions to the request attribute
        // name of this request attribute is the value of the request parameter "key"
        OpenAuctionsHandler handler = new OpenAuctionsHandler(openAuctionsElement);

        // you can also create handler with constructor that takes string argument directly
        handler = new OpenAuctionsHandler("auctions_key");

        // this is the responsibility of the caller to set up the environment
        // this call of this private method is not needed in real application
        setUpOpenAuctionsHandler();

        // assume that 'actionContext' is a valid ActionContext that holds all required data
        // assume that value of request parameter "auctions_key" is "open_auctions"
        // no we can retrieve open auctions
        handler.execute(actionContext);

        // we can retrieve open auctions from the request attribute
        Auction[] auctions = (Auction[]) actionContext.getRequest().getAttribute("open_auctions");
    }

    /**
     * <p>
     * Sets up the environment before calling LeadingBidsHandler#execute.
     * </p>
     */
    private void setUpLeadingBidsHandler() {
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        Bid[] bids = new Bid[5];
        for (int i = 0; i < bids.length; i++) {
            bids[i] = new CustomBid(i, i, 35, new Date());
        }
        addAuctionToPersistence(239, bids);

        MockAuctionStrategy auctionStrategy = new MockAuctionStrategy();
        auctionStrategy.setLeadingBids(bids);
        auctionManager.setAuctionStrategy(auctionStrategy);
    }

    /**
     * <p>
     * Initializes HTTP related parameters that are used in the testLeadingBidsHandler method.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void beginLeadingBidsHandler(WebRequest theRequest) {
        theRequest.addParameter("auction_id_key", "239");
    }

    /**
     * <p>
     * Demonstrates usage of LeadingBidsHandler.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testLeadingBidsHandler() throws Exception {
        // create new LeadingBidsHandler
        // assume that leadingBidsElement variable represents the following xml:
        // <handler type="x">
        // <auction_id_param_key>auction_id_key</auction_id_param_key>
        // <leading_bids_key>bids_key</leading_bids_key>
        // </handler>
        // this handler will store the leading bids to the request attribute
        // name of this request attribute is the value of the request parameter "bids_key"
        // the auction id to get bids from is the value of the request parameter "auction_id_key"
        LeadingBidsHandler handler = new LeadingBidsHandler(leadingBidsElement);

        // you can also create handler with constructor that takes string arguments directly
        handler = new LeadingBidsHandler("auction_id_key", "bids_key");

        // this is the responsibility of the caller to set up the environment
        // this call of this private method is not needed in real application
        setUpLeadingBidsHandler();

        // assume that 'actionContext' is a valid ActionContext that holds all required data
        // assume that value of request parameter "bids_key" is "leading_bids"
        // no we can retrieve leading bids
        handler.execute(actionContext);

        // we can retrieve leading bids from the request attribute
        Bid[] bids = (Bid[]) actionContext.getRequest().getAttribute("leading_bids");
    }

    /**
     * <p>
     * Sets up the environment before calling BidPlacementHandler#execute.
     * </p>
     * @throws Exception to JUnit.
     */
    private void setUpBidPlacementHandler() throws Exception {
        // create UserProfile within the session
        UserProfile bond = new UserProfile(new Long(7));
        bond.setProperty("first_name", "James");
        bond.setProperty("last_name", "Bond");
        bond.setProperty("email_address", "007@sis.uk");
        profileManager.createUserProfile(bond);
        loginHandler.execute(actionContext);

        // add auction manager
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);
        addAuctionToPersistence(239, new Bid[] {});
    }

    /**
     * <p>
     * Initializes HTTP related parameters that are used in the testBidPlacementHandler method.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void beginBidPlacementHandler(WebRequest theRequest) {
        theRequest.addParameter("auction_id_key", "239");
        theRequest.addParameter("max_amount_key", "392");
        theRequest.addParameter("image_id_key", "923");

        // add the user parameters to login
        theRequest.addParameter("firstName", "James");
        theRequest.addParameter("lastName", "Bond");
        theRequest.addParameter("e-mail", "007@sis.uk");
    }

    /**
     * <p>
     * Demonstrates usage of BidPlacementHandler.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testBidPlacementHandler() throws Exception {
        // create new BidPlacementHandler
        // assume that bidPlacementElement variable represents the following xml:
        // <handler type="x">
        // <auction_id_param_key>auction_id_key</auction_id_param_key>
        // <max_amount_param_key>max_amount_key</max_amount_param_key>
        // <image_id_param_key>image_id_key</image_id_param_key>
        // </handler>
        // this handler will store the place new bid to the specified auction
        // the auction id to add bid in is the value of the request parameter "auction_id_key"
        // the bid's max amount is the value of the request parameter "max_amount_key"
        // the bid's image id is the value of the request parameter "image_id_key"
        BidPlacementHandler handler = new BidPlacementHandler(bidPlacementElement);

        // you can also create handler with constructor that takes string arguments directly
        handler = new BidPlacementHandler("auction_id_key", "max_amount_key", "image_id_key");

        // this is the responsibility of the caller to set up the environment
        // this call of this private method is not needed in real application
        setUpBidPlacementHandler();

        // assume that 'actionContext' is a valid ActionContext that holds all required data
        // now we can insert bid
        handler.execute(actionContext);
    }

    /**
     * <p>
     * Sets up the environment before calling BidUpdateHandler#execute.
     * </p>
     * @throws Exception to JUnit.
     */
    private void setUpBidUpdateHandler() throws Exception {
        // create UserProfile within the session
        UserProfile bond = new UserProfile(new Long(7));
        bond.setProperty("first_name", "James");
        bond.setProperty("last_name", "Bond");
        bond.setProperty("email_address", "007@sis.uk");
        profileManager.createUserProfile(bond);
        loginHandler.execute(actionContext);

        // add auction manager
        request.getSession().getServletContext().setAttribute(KeyConstants.AUCTION_MANAGER_KEY,
            auctionManager);
        auctionManager.setAuctionPersistence(auctionPersistence);

        // adds auction with required bid
        Date timestamp = new Date();
        CustomBid originalBid = new CustomBid(111, 123, 150, timestamp);
        originalBid.setId(923);
        originalBid.setEffectiveAmount(199);
        addAuctionToPersistence(239, new Bid[] {originalBid});
    }

    /**
     * <p>
     * Initializes HTTP related parameters that are used in the testBidUpdateHandler method.
     * </p>
     * @param theRequest WebRequest instance to setup initial parameters.
     */
    public void beginBidUpdateHandler(WebRequest theRequest) {
        theRequest.addParameter("auction_id_key", "239");
        theRequest.addParameter("max_amount_key", "392");
        theRequest.addParameter("bid_id_key", "923");

        // add the user parameters to login
        theRequest.addParameter("firstName", "James");
        theRequest.addParameter("lastName", "Bond");
        theRequest.addParameter("e-mail", "007@sis.uk");
    }

    /**
     * <p>
     * Demonstrates usage of BidUpdateHandler.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testBidUpdateHandler() throws Exception {
        // create new BidUpdateHandler
        // assume that bidUpdateElement variable represents the following xml:
        // <handler type="x">
        // <auction_id_param_key>auction_id_key</auction_id_param_key>
        // <max_amount_param_key>max_amount_key</max_amount_param_key>
        // <bid_id_param_key>bid_id_key</bid_id_param_key>
        // </handler>
        // this handler will store the place new bid to the specified auction
        // the auction id to update bid in is the value of the request parameter "auction_id_key"
        // the bid's max amount is the value of the request parameter "max_amount_key"
        // the bid id to update is the value of the request parameter "bid_id_key"
        BidUpdateHandler handler = new BidUpdateHandler(bidUpdateElement);

        // you can also create handler with constructor that takes string arguments directly
        handler = new BidUpdateHandler("auction_id_key", "bid_id_key", "max_amount_key");

        // this is the responsibility of the caller to set up the environment
        // this call of this private method is not needed in real application
        setUpBidUpdateHandler();

        // assume that 'actionContext' is a valid ActionContext that holds all required data
        // now we can update bid
        handler.execute(actionContext);
    }

    /**
     * <p>
     * Creates MockAuction instance and adds it to the persistence.
     * </p>
     * @param id auction's id.
     * @param startDate auction's start date.
     * @param endDate auction's end date.
     */
    private void addAuctionToPersistence(int id, Date startDate, Date endDate) {
        MockAuction auction = new MockAuction();
        auction.setId(id);
        auction.setStartDate(startDate);
        auction.setEndDate(endDate);
        auctionPersistence.addAuction(auction);
    }

    /**
     * <p>
     * Creates MockAuction instance and adds it to the persistence.
     * </p>
     * @param id auction's id.
     * @param bids auction's bids.
     */
    private void addAuctionToPersistence(int id, Bid[] bids) {
        MockAuction auction = new MockAuction();
        auction.setId(id);
        auction.setBids(bids);
        auctionPersistence.addAuction(auction);
    }
}

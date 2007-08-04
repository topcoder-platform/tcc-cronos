/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.handlers;

import com.orpheus.administration.Helper;
import com.orpheus.administration.entities.GameImpl;
import com.orpheus.administration.entities.HostingBlockImpl;
import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.HostingBlock;
import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecoder;
import com.topcoder.json.object.io.JSONDecodingException;
import com.topcoder.json.object.io.StandardJSONDecoder;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionException;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides a Handler implementation that accepts general information about one
 * or more 'blocks' of sites in a game. Information will be parsed from HTTP
 * request parameters, whose names will be configurable. It then creates a new
 * game object, records it via the GameData EJB, and initiates auctions for the
 * configured blocks via an Auction Manager instance. In case of failure, i.e.
 * in case of missing parameters for example, the execute method will return a
 * configurable result name and set a HandlerResult instance as a request
 * attribute so that an appropriate response or user message can be generated.
 * In case of success, the execute() method will return null.<br/> For
 * configuration details on this handler, please see Section 3.2.10 of Comp
 * Spec.<br/> Thread-Safety: This class is thread-safe as is required of
 * Handler implementations. This class is immutable and automatically
 * thread-safe.
 * 
 * @author TCSDESIGNER, KKD
 * @version 1.0
 */
public class CreateGameHandler implements Handler {

    /**
     * This holds the JNDI name to use to look up the GameDataHome service.<br/>
     * This variable is initialized in the constructor and does not change after
     * that.<br/> It will never be null or empty.<br/>
     */
    private final String gameDataJndiName;

    /**
     * This holds the name of the request parameter which will contain block
     * information such as max time per slot, slot count, auction start and end
     * times. The value of the parameter is expected to be in JSON format, such
     * as {"maxSlotTime":5,"slotCount":12,"auctionStartTime":"2006-12-21
     * 09:00","auctionEndTime":"2006-12-30 09:00"}. The JSON component will be
     * used to parse the information. The names of the properties are accepted
     * as configuration parameters. Also, the date format will be accepted as
     * request parameter.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     */
    private final String blockInfoParamName;

    /**
     * This holds the name of the JSON property which will contain the max time
     * per slot. The value should be able to be parsed into an integer value.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     */
    private final String maxSlotTimePropName;

    /**
     * This holds the name of the JSON property which will contain the slot
     * Count. The value should be able to be parsed into an integer value.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     */
    private final String slotCountPropName;

    /**
     * This holds the name of the JSON property which will contain the auction
     * Start Time. The value should be able to be parsed into a java.util.Date
     * object. It should have day,month,year and time of day data. The format of
     * the date string will also be taken as request parameter.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String auctionStartTimePropName;

    /**
     * This holds the name of the JSON property which will contain the auction
     * End Time. The value should be able to be parsed into a java.util.Date
     * object. It should have day,month,year and time of day data. The format of
     * the date string will also be taken as request parameter.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String auctionEndTimePropName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed ball color id. It will be stored as a Long value.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String ballColorIdAttrName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed key count. It will be stored as an Integer value.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String keyCountAttrName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed block count. It will be stored as an Integer value.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String blockCountAttrName;

    /**
     * This holds the name of the request parameter which will contain the date
     * format to use to parse a string date value into a java.util.Date object.<br/>
     * It is initialized in the constructor and does not change after that.<br/>
     * It will never be null or empty.<br/>
     */
    private final String dtFormatParamName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed game start Date. It will be stored as java.util.Date.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String startDateAttrName;

    /**
     * This holds the name of the application attribute which will contain the
     * AuctionManager instance.<br/> It is initialized in the constructor and
     * does not change after that.<br/> It will never be null or empty.<br/>
     */
    private final String auctionMgrAttrName;

    /**
     * This is the minimum bid for any auction when created. Right now it is a
     * fixed value read from configuration. In the future this will probably be
     * added to the UI and handled via a request parameter<br/> It is
     * initialized in the constructor and does not change after that.<br/> May
     * be zero or positive.<br/>
     */
    private final int minimumBid;

    /**
     * This holds the name of the result (as configured in Front Controller
     * component) which should execute in case of execution failure.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String failedResult;

    /**
     * This holds the name of the request attribute to which HandlerResult
     * instance should be assigned to, in case of execution failure.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String failRequestAttrName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed game end Date. It will be stored as java.util.Date.<br/> It is
     * initialized in the constructor and does not change after that.<br/> It
     * will never be null or empty.<br/>
     */
    private final String endDateAttrName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed game bounce point calculation type. It will be stored as
     * java.lang.Integer.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     */
    private final String bounceCalcTypeAttrName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed game prize amount calculation type. It will be stored as
     * java.lang.Integer.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     */
    private final String prizeCalcTypeAttrName;

    /**
     * This holds the name of the session attribute which will contain the
     * parsed game completion type. It will be stored as
     * java.lang.Integer.<br/> It is initialized in the constructor and does
     * not change after that.<br/> It will never be null or empty.<br/>
     */
    private final String gameCompletionTypeAttrName;

    /**
     * Creates a GameParameterHandler instance configured from the given xml
     * element. It will initialize the instance variables. It will throw an
     * IllegalArgumentException if configuration details are missing in the
     * handlerElement argument.
     * 
     * Example xml structure:
     * 
     * <pre>
     *      &lt;handler type=&quot;createGame&quot;&gt;
     *      &lt;!--  JNDI name to use to lookup the GameDataHome interface --&gt;
     *      &lt;game-data-jndi-name&gt;GameDataHome&lt;/ game-data-jndi-name&gt;
     *      &lt;!--  name of request parameter which will contain the block info --&gt;
     *      &lt;block-request-param&gt;ballColorId&lt;/block-request-param&gt;  
     *      &lt;!--  name of request parameter which contain the property name for max slot time --&gt;
     *      &lt;max-slot-time-prop&gt;maxSlotTime&lt;/max-slot-time-prop&gt;  
     *      &lt;!--  name of request parameter which will contain the property name for slot count --&gt;
     *      &lt;slot-count-prop&gt;slotCount&lt;/slot-count-prop&gt;  
     *      &lt;!--  name of request parameter which will contain the property name for auction start time --&gt;
     *      &lt;auction-start-time-prop&gt;auctionStartTime&lt;/auction-start-time-prop&gt;  
     *      &lt;!--  name of request parameter which will contain the property name for auction end time --&gt;
     *      &lt;auction-end-time-prop&gt;auctionEndTime&lt;/auction-end-time-prop&gt;  
     *      &lt;!-- name of session attribute which will contain the parsed ball color id --&gt;
     *      &lt;ballcolor-id-session-attr&gt;ballColorId&lt;/ballcolor-id-session-attr&gt;  
     *      &lt;!-- name of session attribute which will contain the parsed key count --&gt;
     *      &lt;key-count-session-attr&gt;keyCount&lt;/key-count-session-attr&gt;  
     *      &lt;!-- name of session attribute which will contain the parsed block count --&gt;
     *      &lt;block-count-session-attr&gt;blockCount&lt;/block-count-session-attr&gt;
     *      &lt;!--  name of request parameter which will contain the date format --&gt;
     *      &lt;date-format-request-param&gt;dateFormat&lt;/date-format-request-param&gt;  
     *      &lt;!-- name of session attribute which will contain the parsed start date --&gt;
     *      &lt;start-date-session-attr&gt;startDate&lt;/start-date-session-attr&gt;
     *      &lt;!-- name of application attribute which will contain the AuctionManager --&gt;
     *      &lt;auction-mgr-app-attr&gt;auctionMgr&lt;/auction-mgr-app-attr&gt;
     *      &lt;!¡ªminimum bid for any new auction --&gt;
     *      &lt;minimum-auction-bid&gt;auctionMgr&lt;/minimum-auction-bid&gt;
     *      &lt;!--  name of result to return in case of execution failure --&gt;
     *      &lt;fail-result&gt;Failed&lt;/fail-result&gt;
     *      &lt;!--  name of request attribute to store HandlerResult in case of failure--&gt;
     *      &lt;fail-request-attribute&gt;Failed&lt;/fail-request-attribute&gt;
     *      &lt;/handler&gt;
     * 
     * </pre>
     * 
     * 
     * @param handlerElement
     *            the XML element containing configuration for this handler.
     * @throws IllegalArgumentException
     *             if handlerElement is null, or contains invalid data.
     */
    public CreateGameHandler(Element handlerElement) {
        Helper.checkHandlerElement(handlerElement);
        gameDataJndiName = Helper.getValue(handlerElement,
                "/handler/game-data-jndi-name");
        blockInfoParamName = Helper.getValue(handlerElement,
                "/handler/block-request-param");
        maxSlotTimePropName = Helper.getValue(handlerElement,
                "/handler/max-slot-time-prop");
        slotCountPropName = Helper.getValue(handlerElement,
                "/handler/slot-count-prop");
        auctionStartTimePropName = Helper.getValue(handlerElement,
                "/handler/auction-start-time-prop");
        auctionEndTimePropName = Helper.getValue(handlerElement,
                "/handler/auction-end-time-prop");
        ballColorIdAttrName = Helper.getValue(handlerElement,
                "/handler/ballcolor-id-session-attr");
        keyCountAttrName = Helper.getValue(handlerElement,
                "/handler/key-count-session-attr");
        blockCountAttrName = Helper.getValue(handlerElement,
                "/handler/block-count-session-attr");
        dtFormatParamName = Helper.getValue(handlerElement,
                "/handler/date-format-request-param");
        startDateAttrName = Helper.getValue(handlerElement,
                "/handler/start-date-session-attr");
        auctionMgrAttrName = Helper.getValue(handlerElement,
                "/handler/auction-mgr-app-attr");
        endDateAttrName = Helper.getValue(handlerElement,
                "/handler/end-date-session-attr");
        bounceCalcTypeAttrName = Helper.getValue(handlerElement,
                "/handler/bounce-calc-type-session-attr");
        prizeCalcTypeAttrName = Helper.getValue(handlerElement,
                "/handler/prize-calc-type-session-attr");
        gameCompletionTypeAttrName = Helper.getValue(handlerElement,
                "/handler/game-completion-type-session-attr");
        String minimunBidString = Helper.getValue(handlerElement,
                "/handler/minimum-auction-bid");
        int tmp;
        try {
            tmp = Integer.parseInt(minimunBidString);
            if (tmp < 0) {
                throw new IllegalArgumentException(
                        "The 'minimumBid' should not be negative.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The 'minimumBid' can not be parsed as a integer.");
        }
        minimumBid = tmp;
        failedResult = Helper.getValue(handlerElement, "/handler/fail-result");
        failRequestAttrName = Helper.getValue(handlerElement,
                "/handler/fail-request-attribute");
    }

    /**
     * This method will parse information about a game to be created from
     * request parameters into appropriate data types and it then creates a new
     * game object, records it via the GameData EJB, and initiates auctions for
     * the configured blocks via an Auction Manager instance. If there is no
     * session associated with current request, this method will throw a
     * HandlerExecutionException. This is different from other error situations.
     * No session situation is not expected to happen. For other error situation
     * such as missing parameters this method will return a configurable result
     * name and set a HandlerResult as request attribute. In case of success,
     * this method will return null.
     * 
     * @param actionContext
     *            the ActionContext object.
     * 
     * @return null if operation succeeds, otherwise a configurable result name.
     * @throws IllegalArgumentException
     *             if the context is null.
     * @throws HandlerExecutionException
     *             in case no session is associated with the request.
     */
    public String execute(ActionContext actionContext)
        throws HandlerExecutionException {
        Helper.checkNull(actionContext, "actionContext");
        HttpServletRequest request = actionContext.getRequest();
        HttpSession session = Helper.getSession(request);

        // Get the ball color id
        Long ballColorId = (Long) session.getAttribute(ballColorIdAttrName);
        // Get the key count
        Integer keyCount = (Integer) session.getAttribute(keyCountAttrName);
        // Get the block count
        Integer blockCount = (Integer) session.getAttribute(blockCountAttrName);
        // Get the game start and end dates
        Date gameStartDate = (Date) session.getAttribute(startDateAttrName);
        Date gameEndDate = (Date) session.getAttribute(endDateAttrName);
        // Get the bounce points and prize amounts calculation types
        Integer bounceCalcType = (Integer) session.getAttribute(bounceCalcTypeAttrName);
        Integer prizeCalcType = (Integer) session.getAttribute(prizeCalcTypeAttrName);
        Integer completionType = (Integer) session.getAttribute(gameCompletionTypeAttrName);
        // Get the information about the blocks
        String[] blocks = request.getParameterValues(blockInfoParamName);
        if (!Helper.checkArrayNullEmptyContainsNullEmpty(blocks)) {
            Helper.processFailureParameterNotGiven(request,
                    failRequestAttrName, "blocks");
            return failedResult;
        }
        if (blocks.length != blockCount.intValue()) {
            Helper.processFailureBlockInfoLengthNotMatch(request,
                    failRequestAttrName, blockCount.intValue(), blocks.length);
            return failedResult;
        }
        JSONObject[] blockObjs = new JSONObject[blocks.length];
        JSONDecoder decoder = new StandardJSONDecoder();
        for (int i = 0; i < blocks.length; i++) {
            try {
                blockObjs[i] = (JSONObject) decoder.decode(blocks[i]);
            } catch (JSONDecodingException e) {
                Helper.processFailureExceptionOccur(request,
                        "Create game handler failed while "
                                + "parsing block info parameter.",
                        failRequestAttrName, e);
                return failedResult;
            }
        }
        GameData gameData = Helper.getGameData(gameDataJndiName, request,
                failRequestAttrName);
        if (gameData == null) {
            return failedResult;
        }

        // Get the BallColor object for the ball color id
        BallColor[] ballcolors;
        try {
            ballcolors = gameData.findAllBallColors();
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to find all ball colors via GameData.",
                    failRequestAttrName, e);
            return failedResult;
        }
        BallColor ballColor = null;
        for (int i = 0; i < ballcolors.length; i++) {
            if (ballcolors[i].getId().equals(ballColorId)) {
                ballColor = ballcolors[i];
                break;
            }
        }
        if (ballColor == null) {
            Helper.processFailureNoMatchingBallColor(request,
                    failRequestAttrName, "There is no matching ball color");
            return failedResult;
        }
        // Create a game instance
        GameImpl game = new GameImpl();
        game.setBallColor(ballColor);
        game.setKeyCount(keyCount.intValue());
        game.setStartDate(gameStartDate);
        game.setEndDate(gameEndDate);
        game.setBouncePointCalculationType(bounceCalcType.intValue());
        game.setPrizeCalculationType(prizeCalcType.intValue());
        game.setCompletionType(completionType.intValue());
        // Set hosting blocks for the game
        HostingBlock[] hBlocks = new HostingBlock[blockObjs.length];
        // for every [obj] in blockObjs
        for (int i = 0; i < blockObjs.length; i++) {
            HostingBlockImpl newBlock = new HostingBlockImpl();
            newBlock.setMaxHostingTimePerSlot(blockObjs[i]
                    .getInt(maxSlotTimePropName));
            hBlocks[i] = newBlock;
        }
        game.setBlocks(hBlocks);
        // Persist the game
        if (!persistGame(gameData, game, request, session, blockObjs)) {
            return failedResult;
        }
        // return null for successful execution.
        return null;
    }

    /**
     * Persist game.
     * 
     * @param gameData
     *            the GameData
     * @param game
     *            Game
     * @param request
     *            the HttpServletRequest instance
     * @param session
     *            the HttpSession
     * @param blockObjs
     *            the block objects
     * @return true if persist successfully, or false
     */
    private boolean persistGame(GameData gameData, Game game,
            HttpServletRequest request, HttpSession session,
            JSONObject[] blockObjs) {
        Game newGame;
        try {
            newGame = gameData.createGame(game);
        } catch (Exception e) {
            Helper.processFailureExceptionOccur(request,
                    "Failed to create new game.", failRequestAttrName, e);
            return false;
        }
        // Get date format from request parameter
        String dtFormat = request.getParameter(dtFormatParamName);
        // Get the auction manager instance from application context
        AuctionManager auctionMgr
                = (AuctionManager) session.getServletContext().getAttribute(auctionMgrAttrName);
        // Use auction framework to create auctions for each block in newGame
        HostingBlock[] newHBlock = newGame.getBlocks();
        for (int i = 0; i < newHBlock.length; i++) {
            JSONObject obj = blockObjs[i];
            String summary = "Hosting slots for game " + newGame.getName()
                    + ", block " + newHBlock[i].getSequenceNumber();
            String description = summary;
            int itemCount = obj.getInt(slotCountPropName);
            String startDateString = obj.getString(auctionStartTimePropName);
            String endDateString = obj.getString(auctionEndTimePropName);
            // Parse startDate and endDate into java.util.Date using dtFormat
            DateFormat dateFormat = new SimpleDateFormat(dtFormat);
            Date startDate = Helper.parseDate(request, dateFormat,
                    startDateString, "startDate", failRequestAttrName);
            Date endDate = Helper.parseDate(request, dateFormat, endDateString,
                    "endDate", failRequestAttrName);
            if (startDate == null || endDate == null) {
                return false;
            }

            // ISV : FINAL FIX : Auction ID must be same as hosting block ID
//            Auction auction = new AuctionImpl(null, summary, description,
//                    itemCount, this.minimumBid, startDate, endDate, new Bid[0]);
            Auction auction = new AuctionImpl(newHBlock[i].getId(), summary, description,
                    itemCount, this.minimumBid, startDate, endDate, new Bid[0]);
            try {
                auctionMgr.createAuction(auction);
            } catch (AuctionException e) {
                Helper.processFailureExceptionOccur(request,
                        "Failed to create auction.", failRequestAttrName, e);
                return false;
            }
        }

        return true;
    }
}

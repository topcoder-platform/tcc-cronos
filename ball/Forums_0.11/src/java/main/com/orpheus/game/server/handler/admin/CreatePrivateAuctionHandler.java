/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.auction.KeyConstants;
import com.orpheus.auction.persistence.CustomBid;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionManager;
import com.topcoder.util.auction.impl.AuctionImpl;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>A custom {@link Handler} implementation to be used for creating a new game via the pirivate auction where the
 * administrator bids on behalf of selected sponsors. The auction is created and closed immediately.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CreatePrivateAuctionHandler extends AbstractCreateGameHandler implements Handler {

    /**
     * <p>Constructs new <code>CreatePrivateAuctionHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;games_key&gt;games&lt;/games_key&gt;
     *      &lt;auctions_request_attr_key&gt;openAuctions&lt;/auctions_request_attr_key&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or any of required configuration parameters
     *         is missing.
     */
    public CreatePrivateAuctionHandler(Element element) {
        super(element);
    }

    /**
     * <p> Process the user request. Null should be returned, if it wants Action object to continue to execute the next
     * handler (if there is no handler left, the 'success' Result will be executed). It should return a non-empty
     * resultCode if it want to execute a corresponding Result immediately, and ignore all following handlers. </p>
     *
     * @param context the ActionContext object.
     * @return null or a non-empty resultCode string.
     * @throws IllegalArgumentException if the context is null.
     * @throws HandlerExecutionException if fail to execute this handler.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }

        try {
            HttpServletRequest request = context.getRequest();
            HttpSession session = request.getSession(false);
            Game game = createGameInstance(context, false);
            persistGame(getGameDataEJBAdapter(this.jndiContext), game, session, getBlocksData(request, false));
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not create new game with private auction due to unexpected err",
                                                e);
        }
    }

    /**
     * <p>Persists game.</p>
     *
     * @param gameData the GameData
     * @param game Game
     * @param session the HttpSession
     * @param blockObjs the block objects
     * @throws HandlerExecutionException if an unexpected error occurs.
     */
    private void persistGame(GameDataEJBAdapter gameData, Game game, HttpSession session, JSONObject[] blockObjs)
        throws HandlerExecutionException {
        Date now = new Date();
        try {
            Game newGame = gameData.createGame(game);

            // Get the auction manager instance from application context
            AuctionManager auctionMgr
                = (AuctionManager) session.getServletContext().getAttribute(KeyConstants.AUCTION_MANAGER_KEY);
            
            // Use auction framework to create auctions for each block in newGame
            HostingBlock[] newHBlock = newGame.getBlocks();
            for (int i = 0; i < newHBlock.length; i++) {
                JSONObject obj = blockObjs[i];
                String summary = "Hosting slots for game " + newGame.getName() + ", block "
                                 + newHBlock[i].getSequenceNumber();
                int itemCount = obj.getInt(getString(SLOT_COUNT_JSON_PROP_CONFIG));
                JSONArray slots = obj.getArray("slots");

                Date endDate = new Date(now.getTime() + 5 * 1000);
                CustomBid[] bids = new CustomBid[itemCount];
                for (int j = 0; j < bids.length; j++) {
                    JSONObject slot = slots.getJSONObject(j);
                    bids[j] = new CustomBid(slot.getLong("sponsor"), slot.getLong("image"), slot.getInt("maxbid"), now);
                    bids[j].setEffectiveAmount(slot.getInt("maxbid"));
                }

                Auction auction = new AuctionImpl(newHBlock[i].getId(), summary, summary, itemCount,
                                                  getInteger(MINIMUM_BID_VALUE_CONFIG).intValue(), now, endDate, bids);
                auctionMgr.createAuction(auction);
            }
        } catch (Exception e) {
            throw new HandlerExecutionException("", e);
        }
    }
}

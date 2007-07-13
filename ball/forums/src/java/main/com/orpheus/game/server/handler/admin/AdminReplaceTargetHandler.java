/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.administration.entities.DomainTargetImpl;
import com.orpheus.administration.entities.HostingSlotImpl;
import com.orpheus.game.GameDataManager;
import com.orpheus.game.GameDataUtility;
import com.orpheus.game.GameDataManagerConfigurationException;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.server.handler.AbstractGameServerHandler;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.util.algorithm.hash.algorithm.HashAlgorithm;
import com.topcoder.util.algorithm.hash.HashAlgorithmManager;
import com.topcoder.util.algorithm.hash.HashException;
import com.topcoder.util.net.httputility.HttpUtility;
import com.topcoder.util.net.httputility.HttpException;
import com.topcoder.util.web.sitestatistics.SiteStatistics;
import com.topcoder.util.web.sitestatistics.StatisticsException;
import com.topcoder.util.web.sitestatistics.TextStatistics;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.io.IOException;

/**
 * <p>A custom {@link Handler} implementation to be used for replacing the text and/or URL for the selected target from
 * selected slot.</p>
 *
 * @author isv
 * @version 1.0
 */
public class AdminReplaceTargetHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>String</code> providing the template for the message notifying players on re-generated target.</p>
     */
    private static final String TARGET_REPLACED_MSG_CONTENT_PATTERN
        = "Hi - its your friendly Game Administrators here. To make the game more fun we "
          + "have changed one of the targets in Game {0} for site {1} from {2} to {3}. If you already have a key "
          + "for that site, you don't need to do anything. If you are on site and looking for the old target, "
          + "don't forget to switch to the new one. Happy Hunting!";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the domain target text from.</p>
     */
    protected static final String TEXT_PARAM_NAME_CONFIG = "text_param_key";

    /**
     * <p>A <code>String</code> providing the name which could be used as name for configuration parameter providing the
     * name of request parameter to get the domain target URL from.</p>
     */
    protected static final String URL_PARAM_NAME_CONFIG = "url_param_key";

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>AdminReplaceTargetHandler</code> instance initialized based on the configuration
     * parameters provided by the specified <code>XML</code> element.</p>
     *
     * <p>
     * Here is what the xml element likes:
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_data_jndi_name&gt;orpheus/GameData&lt;/game_data_jndi_name&gt;
     *      &lt;jndi_context_name&gt;default&lt;/jndi_context_name&gt;
     *      &lt;use_remote_interface&gt;true&lt;/use_remote_interface&gt;
     *      &lt;slot_id_param_key&gt;slotId&lt;/slot_id_param_key&gt;
     *      &lt;game_id_param_key&gt;gameId&lt;/game_id_param_key&gt;
     *      &lt;target_param_key&gt;target&lt;/target_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     * </p>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or the values configured in xml element are
     *         missing or invalid.
     */
    public AdminReplaceTargetHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, SLOT_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, DOMAIN_TARGET_PARAM_NAME_CONFIG, true);
        readAsString(element, TEXT_PARAM_NAME_CONFIG, true);
        readAsString(element, URL_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }

    /**
     * <p>Processes the incoming request. If a current session is associated with authenticated player then the hosting
     * slot is located based on the current game and domain (identified by request paramaters) and the brainteaser ID
     * and a media type are put to request to be used later by <code>PuzzleRenderingHandler</code>.</p>
     *
     * @param context an <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if request has been serviced successfully. Otherwise an exception will be raised.
     * @throws HandlerExecutionException if un unrecoverable error prevents the successful request processing.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        try {
            HttpServletRequest request = context.getRequest();
            // Get slot ID, game ID and target from request parameters
            long slotId = getLong(SLOT_ID_PARAM_NAME_CONFIG, request);
            int seqNumber = getInteger(DOMAIN_TARGET_PARAM_NAME_CONFIG, request);
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            // Get new text and URL for the target from request parameters
            String newText = request.getParameter(getString(TEXT_PARAM_NAME_CONFIG));
            newText = newText.replaceAll("[ \t\f\r\n\u200b]+", " ");
            newText = newText.replaceFirst("^ +", "");
            newText = newText.replaceFirst(" +$", "");
            newText = newText.toLowerCase();
            String newURL = request.getParameter(getString(URL_PARAM_NAME_CONFIG));
            // Get the details for slot and game from persistent data store
            GameDataManager gameManager = getGameDataManager(context);
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            HostingSlot slot = gameDataEJBAdapter.getSlot(slotId);
            Game game = gameDataEJBAdapter.getGame(gameId);
            // Locate the desired target for replacement and build the list of remaining targets
            boolean firstTargetUpdated = false;
            DomainTarget updatedTarget = null;
            DomainTargetImpl newTarget = null;
            DomainTarget[] targets = slot.getDomainTargets();
            for (int i = 0; i < targets.length; i++) {
                if (targets[i].getSequenceNumber() == seqNumber) {
                    if (targets[i].getIdentifierText().equals(newText) && targets[i].getUriPath().equals(newURL)) {
                        // If text and URL were not updated then do noting
                        break;
                    }
                    // Verify if new target is accessible on specified page. If not - reject to update the target.
                    Boolean targetCheckResult = isTargetValid(newText, newURL);
                    if (targetCheckResult == null) {
                        // Could not retrieve the desired page or could not gather statistics from that page
                        return "targetUnverified";
                    } else if (!targetCheckResult.booleanValue()) {
                        // Target does not exist on the specified page
                        return "invalidTarget";
                    }
                    // Target is valid
                    if (i == 0) {
                        firstTargetUpdated = true;
                    }
                    updatedTarget = targets[i];
                    newTarget = new DomainTargetImpl();
                    newTarget.setClueImageId(updatedTarget.getClueImageId());
                    newTarget.setId(updatedTarget.getId());
                    newTarget.setIdentifierHash(getHash(newText));
                    newTarget.setIdentifierText(newText);
                    newTarget.setSequenceNumber(seqNumber);
                    newTarget.setUriPath(newURL);
                    targets[i] = newTarget;
                }
            }
            // If a requested target was found then update slot, broadcast message and optionally re-generate the
            // brainteaser
            if (updatedTarget != null) {
                // Re-read slot details from DB again to minimize the potential concurrency issue
                slot = gameDataEJBAdapter.getSlot(slotId);
                // Update slot in persistent data store
                final HostingSlotImpl updatedSlot = new HostingSlotImpl();
                updatedSlot.setId(slot.getId());
                updatedSlot.setDomain(slot.getDomain());
                updatedSlot.setImageId(slot.getImageId());
                updatedSlot.setBrainTeaserIds(slot.getBrainTeaserIds());
                updatedSlot.setPuzzleId(slot.getPuzzleId());
                updatedSlot.setSequenceNumber(slot.getSequenceNumber());
                updatedSlot.setWinningBid(slot.getWinningBid());
                updatedSlot.setHostingStart(slot.getHostingStart());
                updatedSlot.setHostingEnd(slot.getHostingEnd());
                updatedSlot.setDomainTargets(targets);
                gameDataEJBAdapter.updateSlots(new HostingSlot[] {updatedSlot});
                if (slot.getHostingStart() != null) {
                    // Broadcast a message notifying players on regenerated target
                    String message = MessageFormat.format(TARGET_REPLACED_MSG_CONTENT_PATTERN,
                                                          new Object[] {game.getName(), slot.getDomain().getDomainName(),
                                                                        updatedTarget.getIdentifierText(),
                                                                        newTarget.getIdentifierText()});
                    broadcastGameMessage(game, message);
                }
                // If first target deleted then re-generate a brainteaser for the slot
                if (firstTargetUpdated) {
                    gameManager.regenerateBrainTeaser(slotId);
                }
            }
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not replace the domain target", e);
        }
    }

    /**
     * <p>Gets the <code>SHA-1</code> hash value for specified text.</p>
     *
     * @param text a <code>String</code> providing the text to get hash value for.
     * @return a <code>String</code> providing the hash code for the specified text.
     * @throws com.topcoder.util.algorithm.hash.ConfigurationException if a configuration error occurs.
     * @throws HashException if a hash value can not be evaluated.
     */
    private String getHash(String text) throws com.topcoder.util.algorithm.hash.ConfigurationException, HashException {
        HashAlgorithmManager hashAlgManager = HashAlgorithmManager.getInstance();
        HashAlgorithm hasher = hashAlgManager.getAlgorithm("SHA-1");
        return hasher.hashToHexString(text.replaceAll("[\n\r \t\f\u00a0\u200b]+", ""), "UTF-8");
    }

    /**
     * <p>Checks if specified target text exists on specified page and the specified page is accessible.</p>
     *
     * @param targetText a <code>String</code> providing the target text to check.
     * @param targetUrl a <code>String</code> providing the URL for the page to check.
     * @return a <code>Boolean</code> indicating whether target exists on specified page or <code>null</code> if the
     *         content of the specified page could not be retrieved and the status of the target is not known. 
     */
    private static Boolean isTargetValid(String targetText, String targetUrl) {
        HttpUtility http = new HttpUtility(HttpUtility.GET);
        http.setFollowRedirects(true);
        http.setDepthLimit(10);
        SiteStatistics siteStatistics;
        try {
            String contents = http.execute(targetUrl);
            siteStatistics = GameDataUtility.getConfiguredSiteStatisticsInstance("SiteStatistics");
            siteStatistics.accumulateFrom(contents, "document1");
        } catch (IOException ioe) {
            System.err.println("IOException while trying to check address: " + targetUrl);
            System.err.println("Message says: " + ioe.getMessage());
            return null;
        } catch (HttpException httpe) {
            System.err.println("HttpException while trying to check address: " + targetUrl);
            System.err.println("Message says: " + httpe.getMessage());
            return null;
        } catch (GameDataManagerConfigurationException gdmce) {
            System.err.println("Unable to obtain SiteStatistics object. Exception information follows.");
            gdmce.printStackTrace(System.err);
            return null;
        } catch (StatisticsException se) {
            System.err.println("Unable to accumulate statistics information from document located at: " + targetUrl);
            se.printStackTrace(System.err);
            return null;
        }
        // Check if target exists on a page
        TextStatistics[] stats = siteStatistics.getElementContentStatistics();
        if (!checkForTextExistence(stats, targetText)) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Checks whether target text exists in the provided statistics information.
     *
     * @return <code>true</code> if target text has been found in the provided statistics
     *         information, <code>false</code> if it has not.
     * @param textStatistics text statistics collected from some page of a hosting site. Used to
     *            check for the presence of the target text.
     * @param targetText target text to check its presence in the provided statistics information.
     */
    private static boolean checkForTextExistence(TextStatistics[] textStatistics, String targetText) {
        for (int i = 0; i < textStatistics.length; ++i)
            if (textStatistics[i].getText().equals(targetText))
                return true;

        return false;
    }
}

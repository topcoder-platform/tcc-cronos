/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler.admin;

import com.orpheus.administration.entities.DomainTargetImpl;
import com.orpheus.administration.entities.HostingSlotImpl;
import com.orpheus.game.GameDataManager;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;
import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.FileUploadResult;
import com.topcoder.servlet.request.LocalFileUpload;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A custom {@link Handler} implementation to be used for creating a new game from scratch requiring the
 * administrator to provide the domains, images and targets for the slots to be included into new game. No auction is
 * created and run.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CreateGameManualHandler extends AbstractCreateGameHandler implements Handler {

    /**
     * <p>Constructs new <code>CreateGameManualHandler</code> instance initialized based on the configuration parameters
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
    public CreateGameManualHandler(Element element) {
        super(element);
        readAsString(element, RANDOM_STRING_IMAGE_NS_VALUE_CONFIG, true);
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
            // Get the request and prepare the data for potential uploaded images
            HttpServletRequest request = context.getRequest();
            FileUploadResult fileUploadResult = null;
            // Parse block data and create the slots
            List hostingSlots = new ArrayList();
            JSONObject[] blockObjs;
            // Test if the request comes from Upload Images screen. If not test if there are any missing images and if
            // there are missing images then forward to Upload Images screen
            boolean blocksFromSession;
            if (request.getParameter("imagesUploaded") == null) {
                blockObjs = getBlocksData(request, false);
                blocksFromSession = false;
                // Test if there are missing any images and if those images are already uploaded
                Map missingImages = getMissingImages(blockObjs);
                if (!missingImages.isEmpty()) {
                    request.setAttribute("missingImages", missingImages);
                    request.getSession(false).setAttribute(getString(BLOCK_INFO_PARAM_NAME_CONFIG),
                                                           request.getParameterValues(getString(BLOCK_INFO_PARAM_NAME_CONFIG)));
                    return "uploadImages";
                }
            } else {
                FileUpload fileUpload = new LocalFileUpload("com.orpheus.game.server.FileUpload");
                fileUploadResult = fileUpload.uploadFiles(request);
                blockObjs = getBlocksData(request, true);
                blocksFromSession = true;
            }
            // Create the game instance
            GameDataManager gameDataManager = getGameDataManager(context);
            GameDataEJBAdapter gameDataEJBAdapter = getGameDataEJBAdapter(this.jndiContext);
            Game game = createGameInstance(context, blocksFromSession);
            Game newGame = gameDataEJBAdapter.createGame(game);
            HostingBlock[] newHBlock = newGame.getBlocks();
            for (int i = 0; i < newHBlock.length; i++) {
                JSONObject block = blockObjs[i];
                JSONArray slots = block.getArray("slots");
                int slotsCount = slots.getSize();
                for (int j = 0; j < slotsCount; j++) {
                    JSONObject slot = slots.getJSONObject(j);
                    // Get the image the slot corresponds to. If the image is not registered yet then register it
                    long imageId = slot.getLong("image");
                    long downloadId;
                    if (imageId == -1) {
                        UploadedFile uploadedFile = fileUploadResult.getUploadedFile("imageUpload_" + i + "_" + j);
                        downloadId = gameDataEJBAdapter.recordBinaryObject("Puzzle Image from Admin",
                                                                           uploadedFile.getContentType(),
                                                                           getFileContent(uploadedFile));
                    } else {
                        ImageInfo existingImage = gameDataEJBAdapter.getImage(imageId);
                        downloadId = existingImage.getDownloadId();
                    }
                    // Get the domain the slot corresponds to. If the domain is not registered yet then register it
                    // without associating with any sponsor
                    long domainId = slot.getLong("domainId");
                    Domain domain;
                    if (domainId == -1) {
                        ImageInfo ii = new ImageInfoImpl(null, downloadId, "Puzzle Image from Admin", Boolean.TRUE);
                        domain = new DomainImpl(null, null, slot.getString("domain"), Boolean.TRUE,
                                                new ImageInfo[] {ii});
                        domain = gameDataEJBAdapter.createDomain(domain);
                        domainId = domain.getId().longValue();
                        ImageInfo[] images = domain.getImages();
                        for (int k = 0; k < images.length; k++) {
                            ImageInfo image = images[k];
                            if (image.getDownloadId() == downloadId) {
                                imageId = image.getId().longValue();
                            }
                        }
                    } else {
                        domain = gameDataEJBAdapter.getDomain(domainId);
                        if (imageId == -1) {
                            List domainImages = new ArrayList(Arrays.asList(domain.getImages()));
                            domainImages.add(new ImageInfoImpl(null, downloadId, "Puzzle Image from Admin",
                                                               Boolean.TRUE));
                            Domain toUpdate = new DomainImpl(domain.getId(), domain.getSponsorId(),
                                                             domain.getDomainName(), domain.isApproved(),
                                                             (ImageInfo[]) domainImages.toArray(new ImageInfo[domainImages.size()]));
                            gameDataEJBAdapter.updateDomain(toUpdate);
                            domain = gameDataEJBAdapter.getDomain(domainId);
                            ImageInfo[] images = domain.getImages();
                            for (int k = 0; k < images.length; k++) {
                                ImageInfo image = images[k];
                                if (image.getDownloadId() == downloadId) {
                                    imageId = image.getId().longValue();
                                }
                            }
                        }
                    }

                    // Set the targets
                    JSONArray jsonTargets = slot.getArray("targets");
                    DomainTargetImpl[] domainTargets = new DomainTargetImpl[jsonTargets.getSize()];
                    for (int k = 0; k < domainTargets.length; k++) {
                        JSONObject jsonTarget = jsonTargets.getJSONObject(k);
                        String targetText = URLDecoder.decode(jsonTarget.getString("text"), "UTF-8");
                        domainTargets[k] = new DomainTargetImpl();
                        domainTargets[k].setIdentifierHash(getHash(targetText));
                        domainTargets[k].setIdentifierText(targetText);
                        domainTargets[k].setSequenceNumber(k);
                        domainTargets[k].setUriPath(URLDecoder.decode(jsonTarget.getString("url"), "UTF-8"));
                        domainTargets[k].setClueImageId(generateClueImage(domainTargets[k], gameDataEJBAdapter));
                    }

                    HostingSlotImpl hostingSlot = new HostingSlotImpl();
                    hostingSlot.setDomain(domain);
                    hostingSlot.setDomainTargets(domainTargets);
                    hostingSlot.setHostingBlockId(newHBlock[i].getId().longValue());
                    hostingSlot.setImageId(imageId);
                    hostingSlot.setSequenceNumber(j);
                    hostingSlot.setWinningBid(slot.getInt("maxbid"));
                    hostingSlots.add(hostingSlot);
                }
            }
            // Persist slot and generate the brainteaser and puzzle for it
            HostingSlot[] slotsArray = (HostingSlot[]) hostingSlots.toArray(new HostingSlot[hostingSlots.size()]);
            slotsArray = gameDataEJBAdapter.createSlots(slotsArray);
            for (int i = 0; i < slotsArray.length; i++) {
                gameDataManager.regenerateBrainTeaser(slotsArray[i].getId().longValue());
                gameDataManager.regeneratePuzzle(slotsArray[i].getId().longValue());
            }

            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Could not create new game from scratch due to unexpected err", e);
        }
    }


    /**
     * <p>Gets the content of an image file uploaded by the sponsor during registration.</p>
     *
     * @param uploadedFile an <code>UploadedFile</code> representing an image file uploaded by sponsor during
     *        registration.
     * @return a <code>byte</code> array providing the content of the uploaded file.
     * @throws Exception if an unexpected error occurs.
     */
    private static byte[] getFileContent(UploadedFile uploadedFile) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) uploadedFile.getSize());
        InputStream imageContentStream = uploadedFile.getInputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = imageContentStream.read(data)) > 0) {
            baos.write(data, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    private Map getMissingImages(JSONObject[] blockObjs) {
        Map images = new LinkedHashMap();
        for (int i = 0; i < blockObjs.length; i++) {
            JSONObject block = blockObjs[i];
            JSONArray slots = block.getArray("slots");
            int slotsCount = slots.getSize();
            for (int j = 0; j < slotsCount; j++) {
                JSONObject slot = slots.getJSONObject(j);
                String domain = slot.getString("domain");
                long imageId = slot.getLong("image");
                if (imageId == -1) {
                    List blockImages;
                    Integer blockNum = new Integer(i);
                    if (images.containsKey(blockNum)) {
                        blockImages = (List) images.get(blockNum);
                    } else {
                        blockImages = new ArrayList();
                        images.put(blockNum, blockImages);
                    }
                    blockImages.add(new Object[] {new Integer(j), domain});
                }
            }
        }
        return images;
    }
}

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.server.handler;

import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.SlotCompletion;
import com.orpheus.game.server.util.GameDataEJBAdapter;
import com.topcoder.util.image.manipulation.encoder.JPEGImageEncoder;
import com.topcoder.util.image.manipulation.image.MutableMemoryImage;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import org.w3c.dom.Element;

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author isv
 * @version 1.0
 */
public class PersonalizedBallHandler extends AbstractGameServerHandler implements Handler {

    /**
     * <p>A <code>Context</code> providing a <code>JNDI</code> context to be used for looking up the home interface for
     * <code>Game Data EJB</code>.</p>
     */
    private final Context jndiContext;

    /**
     * <p>Constructs new <code>PersonalizedBallHandler</code> instance initialized based on the configuration parameters
     * provided by the specified <code>XML</code> element.</p>
     *
     * <p>Below is a sample of an XML element.</p>
     *
     * <pre>
     *     &lt;handler type=&quot;x&quot;&gt;
     *      &lt;game_id_param_key&gt;games&lt;/game_id_param_key&gt;
     *     &lt;/handler&gt;
     * </pre>
     *
     * @param element an <code>Element</code> representing the XML element to extract the configurable values from.
     * @throws IllegalArgumentException if the argument is <code>null</code> or any of required configuration parameters
     *         is missing.
     */
    public PersonalizedBallHandler(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("The parameter [element] is NULL");
        }
        readAsString(element, GAME_ID_PARAM_NAME_CONFIG, true);
        readAsString(element, GAME_DETIALS_ATTR_NAME_CONFIG, true);
        readAsString(element, SLOT_COMPLETIONS_ATTR_NAME_CONFIG, true);
        readAsBoolean(element, USER_REMOTE_INTERFACE_CONFIG, true);
        readAsString(element, GAME_EJB_JNDI_NAME_CONFIG, true);
        this.jndiContext = getJNDIContext(element);
    }

    /**
     * <p>Executes this handler when servicing the specified request. Iterates over the list of the games which the
     * current player is registered to and for each game locates the last domain unlocked by the player in the course of
     * the game. The map mapping the game IDs to respective hosting slots is then bound to request for further use by
     * subsequent handlers or front-end pages.</p>
     *
     * @param context a <code>ActionContext</code> providing the context surrounding the incoming request.
     * @return <code>null</code> if a handler execution succeeds; otherwise an exception will be thrown.
     * @throws HandlerExecutionException if an unrecoverable error prevenst the handler from successful execution.
     */
    public String execute(ActionContext context) throws HandlerExecutionException {
        if (context == null) {
            throw new IllegalArgumentException("The parameter [context] is NULL");
        }
        HttpServletRequest request = context.getRequest();
        try {
            // Get ID of current player and requested game
            long playerId = getUserId(context);
            long gameId = getLong(GAME_ID_PARAM_NAME_CONFIG, request);
            // Load game details and slot completions for the game for the specified player in context of the game
            Map completedSlots = new HashMap();
            GameDataEJBAdapter gameDateEjbAdapter = getGameDataEJBAdapter(this.jndiContext);
            Game game = gameDateEjbAdapter.getGame(gameId);
            HostingBlock[] blocks = game.getBlocks();
            for (int i = 0; i < blocks.length; i++) {
                HostingBlock block = blocks[i];
                HostingSlot[] slots = block.getSlots();
                for (int j = 0; j < slots.length; j++) {
                    HostingSlot slot = slots[j];
                    SlotCompletion[] slotCompletions = gameDateEjbAdapter.findSlotCompletions(gameId,
                                                                                              slot.getId().longValue());
                    for (int k = 0; k < slotCompletions.length; k++) {
                        SlotCompletion slotCompletion = slotCompletions[k];
                        if (slotCompletion.getPlayerId() == playerId) {
                            completedSlots.put(slot.getId(), slotCompletion);
                        }
                    }
                }
            }
            // Put all data to request
            request.setAttribute(getString(GAME_DETIALS_ATTR_NAME_CONFIG), game);
            request.setAttribute(getString(SLOT_COMPLETIONS_ATTR_NAME_CONFIG), completedSlots);
            // Prepare data to be used for rendering the image
            generateImage(request, game);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get data for personalized ball", e);
        }
    }

    private void generateImage(HttpServletRequest request, Game game) {
        HttpSession session = request.getSession(false);
        Map completedSlots = (Map) request.getAttribute(getString(SLOT_COMPLETIONS_ATTR_NAME_CONFIG));
        // A radius for circles representing the game slots
        final int radius = 16;
        final int a = 40;
        final int b = 180;
        final double d = 2 * radius + 10;
        try {
            //
            List slotCenters = new ArrayList();
            List gameSlots = new ArrayList();
            // The collectors for minimum, maximum coordinates of the centers of the circles to be drawn
            int xmin = Integer.MAX_VALUE;
            int ymin = Integer.MAX_VALUE;
            int xmax = Integer.MIN_VALUE;
            int ymax = Integer.MIN_VALUE;
            double angle = 0;
            double r = 1;
            Point center = new Point(0, 0);
            HostingBlock[] blocks = game.getBlocks();
            for (int i = 0; i < blocks.length; i++) {
                HostingSlot[] slots = blocks[i].getSlots();
                for (int j = 0; j < slots.length; j++) {
                    gameSlots.add(slots[j]);
                    slotCenters.add(center);
                    angle += Math.atan(d / r);
                    angle %= 360;
                    r = a + b * angle * Math.PI / 180;
                    xmin = Math.min(xmin, (int) center.getX() - radius - 5);
                    ymin = Math.min(ymin, (int) center.getY() - radius - 5);
                    xmax = Math.max(xmax, (int) center.getX());
                    ymax = Math.max(ymax, (int) center.getY());
                    center = new Point((int) (center.getX() + r * Math.cos(angle)),
                                       (int) (center.getY() + r * Math.sin(angle)));
                }
            }
            int imageWidth = xmax - xmin + radius * 2 + 10;
            int imageHeight = ymax - ymin + radius * 2 + 10;
            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, imageWidth, imageHeight);
            graphics.setColor(Color.GRAY);
            graphics.drawOval(0, 0, imageWidth, imageHeight);
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillOval(0, 0, imageWidth, imageHeight);
            for (int i = 0; i < slotCenters.size(); i++) {
                center = (Point) slotCenters.get(i);
                int x = (int) center.getX();
                int y = (int) center.getY();
                if (xmin < 0) {
                    x += Math.abs(xmin);
                }
                if (ymin < 0) {
                    y += Math.abs(ymin);
                }
                center.setLocation(x, y);
                System.out.println("Circle2 = " + center.getX() + ", " + center.getY());
                HostingSlot slot = (HostingSlot) gameSlots.get(i);
                if (slot.getHostingStart() == null) {
                    graphics.setColor(Color.DARK_GRAY);
                } else {
                    if (slot.getHostingEnd() != null) {
                        if (completedSlots.get(slot.getId()) != null) {
                            graphics.setColor(Color.RED);
                        } else {
                            graphics.setColor(Color.WHITE);
                        }
                    } else {
                        graphics.setColor(Color.GREEN);
                    }
                }
                graphics.drawOval(x - radius, y - radius, radius * 2, radius * 2);
                graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            }
            // Convert image to JPEG format
            MutableMemoryImage mutableImage = new MutableMemoryImage(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JPEGImageEncoder imageEncoder = new JPEGImageEncoder(1.00F);
            imageEncoder.encode(mutableImage, baos, null);
            // Bind data to request and session
            request.setAttribute("gameSlots", gameSlots);
            request.setAttribute("slotCenters", slotCenters);
            request.setAttribute("circleRadius", new Integer(radius));
            request.setAttribute("imageWidth", new Integer(imageWidth));
            request.setAttribute("imageHeight", new Integer(imageHeight));
            session.setAttribute("personalizedBallImage", baos.toByteArray());
            session.setAttribute("personalizedBallImageType", "image/jpeg");
        } catch (Exception e) {
            // The error is caught and not propagated so the image simply is not rendered on a page
            e.printStackTrace();
        }
    }
}

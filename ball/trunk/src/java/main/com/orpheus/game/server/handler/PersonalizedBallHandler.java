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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
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

    private static final Color LARGE_CIRCLE = new Color(0xEEF0F0);
    private static final Color LARGE_CIRCLE_BORDER = new Color(0xC7C7C7);

    private static final Color UPCOMING_SLOT = new Color(0xE2E3E4);
    private static final Color UPCOMING_SLOT_BORDER = new Color(0xC7C7C7);

    private static final Color UNLOCKED_SLOT = new Color(0xEC7523);
    private static final Color UNLOCKED_SLOT_BORDER = new Color(0xDB6727);

    private static final Color COMPLETED_SLOT = new Color(0xA74600);
    private static final Color COMPLETED_SLOT_BORDER = new Color(0xA7A600);

    private static final Color CURRENT_SLOT = new Color(0x94A4DE);
    private static final Color CURRENT_SLOT_BORDER = new Color(0x94A4FF);

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
            generateImage1(request, game, gameDateEjbAdapter);
            return null;
        } catch (Exception e) {
            throw new HandlerExecutionException("Failed to get data for personalized ball", e);
        }
    }

    private void generateImage2(HttpServletRequest request, Game game, GameDataEJBAdapter gameDateEjbAdapter) {
        HttpSession session = request.getSession(false);
        Map completedSlots = (Map) request.getAttribute(getString(SLOT_COMPLETIONS_ATTR_NAME_CONFIG));
        if (isEmpty(game)) {
            return;
        }
        // A radius for circles representing the game slots
        final int radius = 23;
        // A distance between the circles
        final double d = 2 * radius + 10;
        try {
            //
            List slotCenters = new ArrayList();
            List gameSlots = new ArrayList();
            // The collectors for minimum, maximum coordinates of the centers of the circles to be drawn
            int xmin = 0;
            int ymin = 0;
            int xmax = 0;
            int ymax = 0;
            double angle = 0;
            double r = d;
            boolean centerFound = false;
            Point mainCenter = new Point(0, 0);
            Point center = new Point(0, 0);
            HostingBlock[] blocks = game.getBlocks();
            for (int i = 0; i < blocks.length; i++) {
                HostingSlot[] slots = blocks[i].getSlots();
                for (int j = 0; j < slots.length; j++) {
                    gameSlots.add(slots[j]);
                    if (centerFound) {
                        angle += 2 * Math.asin(d / 2 / r);
                        if (angle > Math.PI * 2) {
                            r += d;
                            angle %= (Math.PI * 2);
                        }
                        System.out.println("R = " + r + ", angle = " + angle);
                        xmin = Math.min(xmin, (int) center.getX() - radius - 5);
                        ymin = Math.min(ymin, (int) center.getY() - radius - 5);
                        xmax = Math.max(xmax, (int) center.getX());
                        ymax = Math.max(ymax, (int) center.getY());
                        center = new Point((int) (mainCenter.getX() + r * Math.cos(angle)),
                                           (int) (mainCenter.getY() + r * Math.sin(angle)));

                    } else {
                        centerFound = true;
                    }
                    slotCenters.add(center);
                }
            }
            int imageWidth = xmax - xmin + radius * 2 + 10;
            int imageHeight = ymax - ymin + radius * 2 + 10;
            if (imageWidth < imageHeight) {
                imageWidth = imageHeight;
            } else {
                imageHeight = imageWidth;
            }
            int dx = imageWidth / 2;
            int dy = imageHeight / 2;
            if ((xmin + dx) < 0) {
                dx += Math.abs(xmin + dx);
            }
            if ((ymin + dy) < 0) {
                dy += Math.abs(ymin + dy); 
            }
            imageWidth = dx * 2;
            imageHeight = dy * 2;
            if (imageWidth < imageHeight) {
                imageWidth = imageHeight;
            } else {
                imageHeight = imageWidth;
            }
            dx = imageWidth / 2;
            dy = imageHeight / 2;

            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, imageWidth, imageHeight);
            graphics.setColor(LARGE_CIRCLE);
            graphics.fillOval(0, 0, imageWidth, imageHeight);
            graphics.setColor(LARGE_CIRCLE_BORDER);
            graphics.drawOval(0, 0, imageWidth, imageHeight);
            for (int i = 0; i < slotCenters.size(); i++) {
                center = (Point) slotCenters.get(i);
                int x = (int) center.getX();
                int y = (int) center.getY();
                x += dx;
                y += dy;
                center.setLocation(x, y);
                Color ovalColor;
                Color borderColor;
                HostingSlot slot = (HostingSlot) gameSlots.get(i);
                if (slot.getHostingStart() == null) {
                    ovalColor = UPCOMING_SLOT;
                    borderColor = UPCOMING_SLOT_BORDER;
                } else {
                    if (slot.getHostingEnd() != null) {
                        if (completedSlots.get(slot.getId()) != null) {
                            ovalColor = COMPLETED_SLOT;
                            borderColor = COMPLETED_SLOT_BORDER;
                        } else {
                            ovalColor = UNLOCKED_SLOT;
                            borderColor = UNLOCKED_SLOT_BORDER;
                        }
                    } else {
                        ovalColor = CURRENT_SLOT;
                        borderColor = CURRENT_SLOT_BORDER;
                    }
                }
                graphics.setColor(ovalColor);
                graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                graphics.setColor(borderColor);
                graphics.drawOval(x - radius, y - radius, radius * 2, radius * 2);
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

    private void generateImage1(HttpServletRequest request, Game game, GameDataEJBAdapter gameDateEjbAdapter) {
        HttpSession session = request.getSession(false);
        Map completedSlots = (Map) request.getAttribute(getString(SLOT_COMPLETIONS_ATTR_NAME_CONFIG));
        // A radius for circles representing the game slots
        final int radius = 23;
        final double d = 2 * radius + 10;
        final int b = 180;
        final int a = radius * 2 + 10;
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
            if (imageWidth < imageHeight) {
                imageWidth = imageHeight;
            } else {
                imageHeight = imageWidth;
            }
            int dx = imageWidth / 2;
            int dy = imageHeight / 2;
            if ((xmin + dx) < 0) {
                dx += Math.abs(xmin + dx);
            }
            if ((ymin + dy) < 0) {
                dy += Math.abs(ymin + dy);
            }
            imageWidth = dx * 2;
            imageHeight = dy * 2;
            if (imageWidth < imageHeight) {
                imageWidth = imageHeight;
            } else {
                imageHeight = imageWidth;
            }
            imageWidth += 100;
            imageHeight += 100;
            dx = imageWidth / 2;
            dy = imageHeight / 2;

            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, imageWidth, imageHeight);
            graphics.setColor(LARGE_CIRCLE);
            graphics.fillOval(0, 0, imageWidth, imageHeight);
            graphics.setColor(LARGE_CIRCLE_BORDER);
            graphics.drawOval(0, 0, imageWidth, imageHeight);
            for (int i = 0; i < slotCenters.size(); i++) {
                center = (Point) slotCenters.get(i);
                int x = (int) center.getX();
                int y = (int) center.getY();
                x += dx;
                y += dy;
/*
                if (xmin < 0) {
                    x += Math.abs(xmin);
                }
                if (ymin < 0) {
                    y += Math.abs(ymin);
                }
*/
                center.setLocation(x, y);
                Color ovalColor;
                Color borderColor;
                HostingSlot slot = (HostingSlot) gameSlots.get(i);
                if (slot.getHostingStart() == null) {
                    ovalColor = UPCOMING_SLOT;
                    borderColor = UPCOMING_SLOT_BORDER;
                } else {
                    if (slot.getHostingEnd() != null) {
                        if (completedSlots.get(slot.getId()) != null) {
                            ovalColor = COMPLETED_SLOT;
                            borderColor = COMPLETED_SLOT_BORDER;
                        } else {
                            ovalColor = UNLOCKED_SLOT;
                            borderColor = UNLOCKED_SLOT_BORDER;
                        }
                    } else {
                        ovalColor = CURRENT_SLOT;
                        borderColor = CURRENT_SLOT_BORDER;
                    }
                }
                graphics.setColor(ovalColor);
                graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                graphics.setColor(borderColor);
                graphics.drawOval(x - radius, y - radius, radius * 2, radius * 2);
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

    private boolean isEmpty(Game game) {
        HostingBlock[] blocks = game.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            HostingBlock block = blocks[i];
            HostingSlot[] slots = block.getSlots();
            if (slots.length > 0) {
                return false;
            }
        }
        return true;
    }
}

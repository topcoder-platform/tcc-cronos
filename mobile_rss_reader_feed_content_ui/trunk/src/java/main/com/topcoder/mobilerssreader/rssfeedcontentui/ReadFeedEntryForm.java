/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;

import org.apache.log4j.Logger;

import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;

/**
 * <p>
 * ReadFeedEntryForm is a Screen that extends Form. It merely displays the content of a feed entry. The feed entry to be
 * viewed is set at creation time, but can always be changed by setter. When the user takes an action the command is
 * passed to the registered listener of type CommandListener. Every time there is a call to the listener, an Alert
 * should be shown to user by the listener to let it know action is on-going or in case an exception is caught.
 * </p>
 * <p>
 * It provides a method to get the ID of the displayed entry in case the listeners or the controller need it for
 * processing.
 * </p>
 * <p>
 * When "Refresh" is fired the controller should update the entry and set it in this Screen.
 * When "Mark Read/Unread" is fired the controller should update the model.
 * When "Next" or "Previous" is fired the controller should display the next/previous entry.
 * When "Reply" is fired the controller should display the appropriate ReplyFeedEntryForm.
 * When "Back" is fired the controller should display the previous Screen.
 * When "Help" is fired, the controller should display a custom Screen explaining the mean of the commands.
 * </p>
 * <p>
 * Logging is performed when a method is entered or an exception is caught, assuming all the logger configuration and
 * initialization is done by Mobile RSS Reader Controller.
 * </p>
 * <p>
 * Thread Safe:
 * </p>
 * <p>
 * This class is mutable, so it is not thread-safe.
 * </p>
 *
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
public class ReadFeedEntryForm extends Form {

    /**
     * <p>
     * Represents the log name to use for Logger instance retrieval. It's immutable.
     * </p>
     */
    public static final String DEFAULT_LOG_NAME = "ReadFeedEntryForm";
    /**
     * <p>
     * Represents the command <code>Refresh</code> of type BACK.
     * </p>
     */
    private static final Command REFRESH = new Command("Refresh", Command.BACK, 0);

    /**
     * <p>
     * Represents the command <code>Next</code> of type SCREEN.
     * </p>
     */
    private static final Command NEXT = new Command("Next", Command.SCREEN, 1);
    /**
     * <p>
     * Represents the command <code>Previous</code> of type SCREEN.
     * </p>
     */
    private static final Command PREVIOUS = new Command("Previous", Command.SCREEN, 2);
    /**
     * <p>
     * Represents the command <code>Reply</code> of type SCREEN.
     * </p>
     */
    private static final Command REPLY = new Command("Reply", Command.SCREEN, 3);
    /**
     * <p>
     * Represents the command <code>Mark as Read</code> of type SCREEN.
     * </p>
     */
    private static final Command READ = new Command("Mark as Read", Command.SCREEN, 4);
    /**
     * <p>
     * Represents the command <code>Mark as Unread</code> of type SCREEN.
     * </p>
     */
    private static final Command UNREAD = new Command("Mark as Unread", Command.SCREEN, 4);
    /**
     * <p>
     * Represents the command <code>Back</code> of type SCREEN.
     * </p>
     */
    private static final Command BACK = new Command("Back", Command.SCREEN, 5);
    /**
     * <p>
     * Represents the command <code>Help</code> of type HELP.
     * </p>
     */
    private static final Command HELP = new Command("Help", Command.HELP, 6);
    /**
     * <p>
     * Represents the hyphen String <code>-</code>.
     * </p>
     */
    private static final String HYPEN = "-";
    /**
     * <p>
     * Represents the new line string <code>\n</code>.
     * </p>
     */
    private static final String NEW_LINE = "\n";
    /**
     * <p>
     * Represents the logger to which relevant events are logged. It's set during class preloading and then can never
     * change, can be null if there is no logger associated to the DEFAULT_LOG_NAME.
     * </p>
     */
    private static Logger logger = Logger.getLogger(DEFAULT_LOG_NAME);

    /**
     * <p>
     * Represents the ID of the current displayed feed entry, is set in constructor and setFeedEntry() and its value is
     * mutable. It's accessed by getFeedEntryId(). Can never be null.
     * </p>
     */
    private String feedEntryId;

    /**
     * <p>
     * Represents the image to be set as header. It's set in constructor and can never change. Can be null if no image
     * is needed as header.
     * </p>
     */
    private final Image headerImage;

    /**
     * <p>
     * Create a new ReadFeedEntryForm instance with given title and feed entry to be shown.
     * </p>
     *
     * @param title
     *            the String containing the title of the Screen, may contain Tag and Feed names, can be empty/null
     * @param feedEntry
     *            the feed entry to be viewed
     * @param headerImage
     *            the image to be set as header, can be null if no image is needed as header
     * @throws IllegalArgumentException
     *             if feedEntry is null.
     */
    public ReadFeedEntryForm(String title, RSSFeedContentEntry feedEntry, Image headerImage) {
        super(title);
        logger.info("construct method begins.");
        logger.info("title :" + title);
        if (feedEntry == null) {
            logger.error("the [feedEntry] should not be null.");
            throw new IllegalArgumentException("the [feedEntry] should not be null.");
        }
        this.headerImage = headerImage;
        // add all the commands
        addCommand(REFRESH);
        logger.info("read :" + feedEntry.isRead());
        // if the read flag of feedEntry is true, add command "mark as unread", else add command "mark as read".
        addCommand(feedEntry.isRead() ? UNREAD : READ);
        addCommand(NEXT);
        addCommand(PREVIOUS);
        addCommand(REPLY);
        addCommand(BACK);
        addCommand(HELP);
        // set the feed entry
        setFeedEntry(title, feedEntry);
        logger.info("construct method ends.");
    }

    /**
     * <p>
     * Set and show given title and feed entry.
     * </p>
     *
     * @param title
     *            the String containing the title of the Screen, may contain Tag and Feed names, can be empty/null
     * @param feedEntry
     *            the feed entry to be viewed
     * @throws IllegalArgumentException
     *             if feedEntry is null.
     */
    public void setFeedEntry(String title, RSSFeedContentEntry feedEntry) {
        logger.info("method setFeedEntry begins.");
        if (feedEntry == null) {
            logger.error("the [feedEntry] should not be null.");
            throw new IllegalArgumentException("the [feedEntry] should not be null.");
        }
        this.feedEntryId = feedEntry.getObjectId();
        logger.info("feedEntryId :" + feedEntryId);
        // clear the form
        deleteAll();
        logger.info("title :" + title);
        // set form title
        setTitle(title);
        // set header image if not null
        if (headerImage != null) {
            append(headerImage);
        }

        // append the feed name and updateDate
        String feedTitle = feedEntry.getName() + HYPEN + feedEntry.getUpdatedDate() + NEW_LINE;
        logger.info("feedTitle :" + feedTitle);
        append(feedTitle);
        // append the feed description
        append(feedEntry.getDescription());
        logger.info("method setFeedEntry ends.");
    }

    /**
     * <p>
     * Get the ID of the displayed feed entry.
     * </p>
     *
     * @return the String ID of the displayed feed entry
     */
    public String getFeedEntryId() {
        logger.info("method getFeedEntryId begins.");
        logger.info("feedEntryId :" + feedEntryId);
        logger.info("method getFeedEntryId ends.");
        return feedEntryId;
    }

    /**
     * <p>
     * Static getter for the logger static field.
     * </p>
     *
     * @return the logger used by instances of this class
     */
    public static Logger getLogger() {
        logger.info("method getLogger begins.");
        logger.info("method getLogger ends.");
        return logger;
    }

}

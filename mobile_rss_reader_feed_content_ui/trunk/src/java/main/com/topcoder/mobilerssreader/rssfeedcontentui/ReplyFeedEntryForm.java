/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.TextField;

import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;

import org.apache.log4j.Logger;

/**
 * <p>
 * ReplyFeedEntryForm is a Screen that extends Form. It displays input text field for the reply followed by the content
 * of the feed entry. The feed entry to be replied to is set at creation time, but can always be changed by setter.
 * When the user takes an action the command is passed to the registered listener of type CommandListener. Every time
 * there is a call to the listener, an Alert should be shown to user by the listener to let it know action is on-going
 * or in case an exception is caught.
 * </p>
 * <p>
 * It provides a method to get the ID of the displayed entry and one method to get the message to post, in case the
 * listeners or the controller need them for processing.
 * </p>
 * <p>
 * When "Post" is fired,  the controller should post the message to the feed.
 * When "Clear" is fired, the controller should call clear() in order to clear the displayed text field.
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
public class ReplyFeedEntryForm extends Form {
    /**
     * <p>
     * Represents the log name to use for Logger instance retrieval. It's immutable.
     * </p>
     */
    public static final String DEFAULT_LOG_NAME = "ReplyFeedEntryForm";

    /**
     * <p>
     * Represents the command <code>Clear</code> of type BACK.
     * </p>
     */
    private static final Command CLEAR = new Command("Clear", Command.BACK, 0);
    /**
     * <p>
     * Represents the command <code>Post</code> of type OK.
     * </p>
     */
    private static final Command POST = new Command("Post", Command.OK, 1);

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
     * Represents the comment string of textField <code>Comments:</code>.
     * </p>
     */
    private static final String COMMENTS = "Comments:";

    /**
     * <p>
     * Represents the logger to which relevant events are logged. It's set during class preloading and then can never
     * change, can be null if there is no logger associated to the DEFAULT_LOG_NAME.
     * </p>
     */
    private static Logger logger = Logger.getLogger(DEFAULT_LOG_NAME);
    /**
     * <p>
     * Represents the text field that contains the reply message. It's set in setFeedEntry() and can change. Cannot be
     * null. It's accessed by getPostMessage() to retrieve its text.
     * </p>
     */
    private TextField textField;

    /**
     * <p>
     * Represents the ID of the feed entry to reply, is set in constructor and setFeedEntry() and its value is mutable.
     * It's accessed by getFeedEntryId(). Can never be null.
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
     * Represents the max size of the reply message. It's set in constructor and can never change. Must be positive, but
     * meaningful values should be of at least 100+.
     * </p>
     */
    private final int textFieldMaxSize;

    /**
     * <p>
     * Create a new ReadFeedEntryForm instance with given title and feed entry to be replied to.
     * </p>
     *
     * @param title
     *            the String containing the title of the Screen, may contain Tag and Feed names, can be empty/null
     * @param feedEntry
     *            the feed entry to be replied to
     * @param headerImage
     *            the image to be set as header, can be null if no image is needed as header
     * @param maxSize
     *            the max allowed size of the text field for the reply
     * @throws IllegalArgumentException
     *             if feedEntry is null or maxSize is zero or less.
     */
    public ReplyFeedEntryForm(String title, RSSFeedContentEntry feedEntry, Image headerImage, int maxSize) {
        super(title);
        logger.info("constructor method begins.");
        if (feedEntry == null) {
            logger.error("the [feedEntry] should not be null.");
            throw new IllegalArgumentException("the [feedEntry] should not be null.");
        }
        logger.info("title :" + title);
        logger.info("maxSize :" + maxSize);
        if (maxSize <= 0) {
            logger.error("the [maxSize] should not be zero or less.");
            throw new IllegalArgumentException("the [maxSize] should not be zero or less.");
        }
        this.headerImage = headerImage;
        this.textFieldMaxSize = maxSize;
        // add the command
        addCommand(POST);
        addCommand(CLEAR);
        // set feed entry
        setFeedEntry(title, feedEntry);
        logger.info("constructor method ends.");
    }

    /**
     * <p>
     * Set and show given title and feed entry to be replied to.
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
        logger.info("method setFeedEntry begins. with title :" + title);
        if (feedEntry == null) {
            logger.error("the [feedEntry] should not be null.");
            throw new IllegalArgumentException("the [feedEntry] should not be null.");
        }
        this.feedEntryId = feedEntry.getObjectId();
        logger.info("feedEntryId :" + feedEntryId);
        // clear the form
        deleteAll();
        // set title
        setTitle(title);
        if (headerImage != null) {
            // append header image if not null
            append(headerImage);
        }
        // append the feed title
        String feedTitle = feedEntry.getName() + HYPEN + feedEntry.getUpdatedDate() + NEW_LINE;
        logger.info("append the feedTitle :" + feedTitle);
        append(feedTitle);
        // set the textField, set the text to null if the TextField is to be empty
        textField = new TextField(COMMENTS, null, textFieldMaxSize, Item.LAYOUT_DEFAULT);
        // append the textField
        append(textField);

        // append the feed content
        append(feedEntry.getDescription());
        logger.info("method setFeedEntry ends.");
    }

    /**
     * <p>
     * Get the ID of the feed entry to reply.
     * </p>
     *
     * @return the String ID of the feed entry to reply
     */
    public String getFeedEntryId() {
        logger.info("method getFeedEntryId begins.");
        logger.info("feedEntryId :" + feedEntryId);
        logger.info("method getFeedEntryId ends.");
        return feedEntryId;
    }

    /**
     * <p>
     * Get the reply message.
     * </p>
     *
     * @return the current reply message in the text field
     */
    public String getPostMessage() {
        logger.info("method getPostMessage begins.");
        String postMessage = textField.getString();
        logger.info("getPostMessage :" + postMessage);
        logger.info("method getPostMessage ends.");
        return postMessage;
    }

    /**
     * <p>
     * Clear the displayed text field.
     * </p>
     */
    public void clear() {
        logger.info("method clear begins.");
        // set to null if the TextField is to be made empty
        textField.setString(null);
        logger.info("method clear ends.");
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

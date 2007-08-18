/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Font;

import com.topcoder.mobile.filterlistscreen.FilterList;
import com.topcoder.mobile.filterlistscreen.FilterListEntry;
import com.topcoder.mobile.filterlistscreen.FilterListModel;
import com.topcoder.mobile.filterlistscreen.models.DefaultFilterListEntry;
import com.topcoder.mobile.filterlistscreen.models.DefaultFilterListModel;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;


import org.apache.log4j.Logger;

/**
 * <p>
 * ViewFeedList is a List Screen that extends FilterList. It merely displays feed and feed entries, allowing sorting by
 * date or by name. The feed to be viewed is set at creation time, but can always be changed by setter. When the user
 * takes an action the command is passed to the registered listener of type CommandListener. Every time there is a call
 * to the listener, an Alert should be shown to user by the listener to let it know action is on-going or in case an
 * exception is caught.
 * </p>
 * <p>
 * It provides methods to get the selected feed entry Id in case the listeners or the controller need it for processing
 * (for instance after a selection event) or the feed id. If an entry is unread it is shown in bold Font, else with the
 * default Font. It is responsibility of the controller to set an entry as read or as unread if the user select the
 * related command on it: for this setSelectedFeedEntryStatus is provided. At opposite, if setFeed() is called with the
 * changed model as argument, bold Font is automatically set on unread entries.
 * </p>
 * <P>
 * If "Toggle All" is fired, the controller should update the model switching the isRead field and then call toggleAll()
 * to correctly display the entries.
 * When the "Sort By Date" or "Sort By Name" command event is fired the related method should be called on this screen
 * in order to sort the displayed List.
 * When "View Feed" is fired the ReadFeedEntryForm should be set as the current Displayable.
 * When "Refresh" is fired the controller should update the feed and set it in this Screen.
 * When "Back" is fired the controller should display the previous Screen.
 * When "Help" is fired, the controller should display a custom Screen explaining the mean of the commands.
 * </P>
 * <p>
 * The Mobile Filter List component provides an abstract method to be implemented (updateUI()). Here the list to be
 * displayed is set, based on the ordered model. It is called after ordering by superclass.
 * </p>
 * <p>
 * Logging is performed when a method is entered or an exception is caught, assuming all the logger configuration and
 * initialization is done by Mobile RSS Reader Controller.
 * </p>
 * <p>
 * This class is mutable, so it is not thread-safe.
 * </p>
 *
 * @author caru, TCSDEVELOPER
 * @version 1.0
 */
public class ViewFeedList extends FilterList {

    /**
     * <p>
     * Represents the log name to use for Logger instance retrieval. It's immutable.
     * </p>
     */
    public static final String DEFAULT_LOG_NAME = "ViewFeedList";
    /**
     * <p>
     * Represents the logger to which relevant events are logged.can be null if there is no logger associated to the
     * DEFAULT_LOG_NAME.
     * </p>
     */
    private static Logger logger = Logger.getLogger(DEFAULT_LOG_NAME);
    /**
     * <p>
     * Represents the command <code>View Feed</code> of type BACK.
     * </p>
     */
    private static final Command VIEW_FEED = new Command("View Feed", Command.BACK, 0);
    /**
     * <p>
     * Represents the command <code>Mark Unread</code> of type ITEM.
     * </p>
     */
    private static final Command MARK_UNREAD = new Command("Mark Unread", Command.ITEM, 1);
    /**
     * <p>
     * Represents the command <code>Mark Read</code> of type ITEM.
     * </p>
     */
    private static final Command MARK_READ = new Command("Mark Read", Command.ITEM, 1);
    /**
     * <p>
     * Represents the command <code>Toggle All</code> of type SCREEN.
     * </p>
     */
    private static final Command TOGGLE_ALL = new Command("Toggle All", Command.SCREEN, 2);
    /**
     * <p>
     * Represents the command <code>Refresh</code> of type SCREEN.
     * </p>
     */
    private static final Command REFRESH = new Command("Refresh", Command.SCREEN, 3);
    /**
     * <p>
     * Represents the command <code>Sort By Name</code> of type SCREEN.
     * </p>
     */
    private static final Command SORT_BY_NAME = new Command("Sort By Name", Command.SCREEN, 4);
    /**
     * <p>
     * Represents the command <code>Sort By Date</code> of type SCREEN.
     * </p>
     */
    private static final Command SORT_BY_DATE = new Command("Sort By Date", Command.SCREEN, 5);
    /**
     * <p>
     * Represents the command <code>Back</code> of type OK.
     * </p>
     */
    private static final Command BACK = new Command("Back", Command.OK, 6);
    /**
     * <p>
     * Represents the command <code>Help</code> of type HELP.
     * </p>
     */
    private static final Command HELP = new Command("Help", Command.HELP, 7);

    /**
     * <p>
     * Represents the column index of name <code>0</code>.
     * </p>
     */
    private static final int NAME = 0;
    /**
     * <p>
     * Represents the column index of date <code>1</code>.
     * </p>
     */
    private static final int DATE = 1;
    /**
     * <p>
     * Represents the column index of feedId <code>2</code>.
     * </p>
     */
    private static final int FEED_ID = 2;
    /**
     * <p>
     * Represents the column index of flag read <code>3</code>.
     * </p>
     */
    private static final int READ = 3;
    /**
     * <p>
     * Represents the <code>bold</code> font.
     * </p>
     */
    private static final Font STYLE_BOLD = Font.getFont(Font.FACE_SYSTEM | Font.STYLE_BOLD);
    /**
     * <p>
     * Represents the hyphen String <code>-</code>.
     * </p>
     */
    private static final String HYPEN = "-";
    /**
     * <p>
     * Represents the String <code>true</code>.
     * </p>
     */
    private static final String TRUE = "true";
    /**
     * <p>
     * Represents the String <code>false</code>.
     * </p>
     */
    private static final String FALSE = "false";
    /**
     * <p>
     * Represents the ID of the current displayed feed, is set in constructor and setFeed() and its value is mutable.
     * It's accessed by getFeedId(). Can never be null.
     * </p>
     */
    private String feedId;

    /**
     * <p>
     * Create a new ViewFeedList instance with given title and feed to be shown.
     * </p>
     *
     * @param title
     *            the String containing the title of the Screen, may contain Tag and Feed names, can be empty/null
     * @param feed
     *            the feed to be viewed
     * @throws IllegalArgumentException
     *             if feed is null.
     */
    public ViewFeedList(String title, RSSFeedContent feed) {
        super(title, Choice.IMPLICIT);
        logger.info("constructor method begins.");
        logger.info("title :" + title);
        if (feed == null) {
            logger.error("the [feed] should not be null.");
            throw new IllegalArgumentException("the [feed] should not be null.");
        }
        // add the commands
        addCommand(VIEW_FEED);
        addCommand(TOGGLE_ALL);
        addCommand(REFRESH);
        addCommand(SORT_BY_NAME);
        addCommand(SORT_BY_DATE);
        addCommand(BACK);
        addCommand(HELP);
        // set feed
        setFeed(title, feed);
        logger.info("constructor method ends.");
    }

    /**
     * <p>
     * Set and show given title and feed.
     * </p>
     *
     * @param title
     *            the String containing the title of the Screen, may contain Tag and Feed names, can be empty/null
     * @param feed
     *            the feed to be viewed
     * @throws IllegalArgumentException
     *             if feed is null.
     */
    public void setFeed(String title, RSSFeedContent feed) {
        logger.info("method setFeed begins.");
        // check the feed
        if (feed == null) {
            logger.error("the [feed] should not be null.");
            throw new IllegalArgumentException("the [feed] should not be null.");
        }
        this.feedId = feed.getObjectId();
        logger.info("feedId :" + feedId);
        logger.info("title :" + title);
        // set title
        setTitle(title);
        DefaultFilterListModel model = new DefaultFilterListModel();

        RSSFeedContentEntry[] entries = feed.getAllRSSEntries();
        logger.info("entries length :" + entries.length);
        for (int i = 0; i < entries.length; i++) {
            // Create a new DefaultFilterListEntry and add it to the model
            FilterListEntry entry = new DefaultFilterListEntry(new String[] {entries[i].getName(),
                    entries[i].getUpdatedDate().toString(), entries[i].getObjectId(),
                    entries[i].isRead() ? TRUE : FALSE });
            model.add(entry);
        }
        logger.info("model size :" + model.getSize());
        // set the model, it would call updateUI
        setFilterListModel(model);
        logger.info("method setFeed ends.");
    }

    /**
     * <p>
     * Delete all the elements from the List. Update the UI with model.
     * </p>
     */
    public void updateUI() {
        logger.info("method updateUI begins.");
        // delete all the elements
        deleteAll();
        FilterListModel model = getFilterListModel();
        logger.info("model size :" + model.getSize());
        // store the flag read
        boolean[] reads = new boolean[model.getSize()];

        for (int i = 0; i < model.getSize(); i++) {
            FilterListEntry entry = model.get(i);
            reads[i] = TRUE.equals(entry.getColumn(READ));

            // append a new string
            String displayName = entry.getColumn(NAME) + HYPEN + entry.getColumn(DATE);

            // if it the flag read is false, set it selected, making its font to be bold.
            // It's a bug please see http://forum.java.sun.com/thread.jspa?threadID=680936
            // Instead of changing the font, can you show the read/unread status in some other way? Such as adding an icon or
            // a "*" in front of the name?
            if (!reads[i]) {
                displayName = "*" + displayName;
            }
            append(displayName, null);
            setSelectedIndex(i, !reads[i]);
        }

        // if there is selected one, if the flag read is false add command of "mark read", else "mark unread"
        int index = getSelectedIndex();
        logger.info("selected index :" + index);
        if (index >= 0) {
            // update the command read and unread according to reads status
            addCommand(reads[index] ? MARK_UNREAD : MARK_READ);
        } else {
            addCommand(MARK_UNREAD);
        }
        logger.info("method updateUI ends.");
    }

    /**
     * <p>
     * Get the ID of the selected feed entry.
     * </p>
     *
     * @return the String ID of the selected entry
     * @throws IllegalStateException
     *             if no element in the list selected.
     */
    public String getSelectedFeedEntryId() {
        logger.info("method getSelectedFeedEntryId begins.");
        int elementNum = getSelectedIndex();
        logger.info("selected elementNum :" + elementNum);
        if (elementNum == -1) {
            logger.error("there is no element in the list selected.");
            throw new IllegalStateException("there is no element in the list selected.");
        }
        String entryId = getFilterListModel().get(elementNum).getColumn(FEED_ID);
        logger.info("entryId :" + entryId);
        logger.info("method getSelectedFeedEntryId ends.");
        return entryId;
    }

    /**
     * <p>
     * Set the Font of the entry based on the flag value.
     * </p>
     *
     * @param read
     *            whether the selected entry is marked read or unread
     * @throws IllegalStateException
     *             if no element in the list selected.
     */
    public void setSelectedFeedEntryStatus(boolean read) {
        logger.info("method setSelectedFeedEntryStatus begins.");
        int elementNum = getSelectedIndex();
        logger.info("selected elementNum :" + elementNum);
        if (elementNum == -1) {
            logger.error("there is no element in the list selected.");
            throw new IllegalStateException("there is no element in the list selected.");
        }

        FilterListModel oldModel = getFilterListModel();
        logger.info("old model size :" + oldModel.getSize());

        FilterListEntry entry = oldModel.remove(elementNum);
        oldModel.insert(elementNum, new DefaultFilterListEntry(new String[] {entry.getColumn(NAME), entry.getColumn(DATE),
                entry.getColumn(FEED_ID), read ? TRUE : FALSE }));
        //setFilterListModel(oldModel);

        // if the read is false, set the font to bold, else set to default font
        setFont(elementNum, read ? Font.getDefaultFont() : STYLE_BOLD);
        logger.info("method setSelectedFeedEntryStatus ends.");
    }

    /**
     * <p>
     * Get the ID of the displayed feed.
     * </p>
     *
     * @return the String ID of the displayed feed
     */
    public String getFeedId() {
        logger.info("method getFeedId begins.");
        logger.info("feedId :" + feedId);
        logger.info("method getFeedId ends.");
        return feedId;
    }

    /**
     * <p>
     * Sort the entries by name on display.
     * </p>
     *
     * @param isAscending
     *            whether the entries should be ordered in ascending order
     */
    public void sortByName(boolean isAscending) {
        logger.info("method sortByName begins.");
        logger.info("isAscending :" + isAscending);
        sortByColumn(NAME, isAscending);
        logger.info("method sortByName ends.");
    }

    /**
     * <p>
     * Sort the entries by name on display.
     * </p>
     *
     * @param isAscending
     *            whether the entries should be ordered in ascending order
     */
    public void sortByDate(boolean isAscending) {
        logger.info("method sortByDate begins.");
        logger.info("isAscending :" + isAscending);
        sortByColumn(DATE, isAscending);
        logger.info("method sortByDate ends.");
    }

    /**
     * <p>
     * Toggle the status (read/unread) of all entries.
     * </p>
     */
    public void toggleAll() {
        logger.info("method toggleAll begins.");
        FilterListModel oldModel = getFilterListModel();
        logger.info("old model size :" + oldModel.getSize());
        FilterListModel newModel = new DefaultFilterListModel();
        // iterator all the entry in the old model
        for (int i = 0; i < oldModel.getSize(); i++) {
            FilterListEntry entry = oldModel.get(i);
            // exchange the value false and true
            newModel.add(new DefaultFilterListEntry(new String[] {entry.getColumn(NAME), entry.getColumn(DATE),
                    entry.getColumn(FEED_ID), TRUE.equals(entry.getColumn(READ)) ? FALSE : TRUE }));
        }
        logger.info("new model size :" + newModel.getSize());
        // updateUI would be done in the method setFilterListModel.
        setFilterListModel(newModel);
        logger.info("method toggleAll ends.");
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

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.mobilerssreader.rssfeedcontentui;

import java.util.Date;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.apache.log4j.LogActivator;
import org.apache.log4j.Logger;

import ch.ethz.jadabs.osgi.j2me.OSGiContainer;

import com.topcoder.mobile.httphandler.URL;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContent;
import com.topcoder.mobilerssreader.databroker.entity.RSSFeedContentEntry;

/**
 * <p>
 * Demo class of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest extends MIDlet {
    /**
     * <p>
     * The list page.
     * </p>
     */
    private ViewFeedList viewFeed;
    /**
     * <p>
     * The read page.
     * </p>
     */
    private ReadFeedEntryForm readEntry;
    /**
     * <p>
     * The reply page.
     * </p>
     */
    private ReplyFeedEntryForm replyEntry;

    /**
     * <p>
     * The header image used in demo.
     * </p>
     */
    private Image headerImage;
    /**
     * <p>
     * Array of RSSFeedContentEntry used in the list page.
     * </p>
     */
    private RSSFeedContentEntry[] entries;
    /**
     * <p>
     * The current selected index.
     * </p>
     */
    private int currentSelectedIndex = 0;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public DemoTest() {
        OSGiContainer osgicontainer = OSGiContainer.Instance();
        osgicontainer.setProperty("log4j.priority", "INFO");
        osgicontainer.startBundle(new LogActivator());
        headerImage = TestHepler.getImage("/topcoder.gif");
    }

    /**
     * <p>
     * This method should be called when, from an RSSSubcription Screen, the
     * user wants to view the list of entries of a specific feed.
     * </p>
     *
     * @param title
     *            title to construct the list page.
     * @param feed
     *            feed to construct the list page.
     */
    public void showFeedList(String title, RSSFeedContent feed) {
        viewFeed = new ViewFeedList(title, feed);
        viewFeed.addCommand(new Command("Log", Command.OK, 0));
        viewFeed.setCommandListener(new ViewFeedListener(this));
        setCurrent(viewFeed);
    }

    /**
     * <p>
     * This method should be called when, from the list Screen, the user wants
     * to view the selected entry.
     * </p>
     *
     * @param title
     *            title to construct the read page.
     * @param entry
     *            entry to construct the read page.
     */
    public void showFeedEntry(String title, RSSFeedContentEntry entry) {
        readEntry = new ReadFeedEntryForm(title, entry, headerImage);
        readEntry.addCommand(new Command("Log", Command.OK, 0));
        readEntry.setCommandListener(new ReadEntryListener(this));
        setCurrent(readEntry);
    }

    /**
     * <p>
     * This method should be called when, from the read Screen, the user wants
     * to reply the read entry.
     * </p>
     *
     * @param title
     *            title to construct the reply page.
     * @param entry
     *            entry to construct the reply page.
     */
    public void replyFeedEntry(String title, RSSFeedContentEntry entry) {
        replyEntry = new ReplyFeedEntryForm(title, entry, headerImage, 100);
        replyEntry.addCommand(new Command("Log", Command.OK, 0));
        replyEntry.setCommandListener(new ReplyEntryListener(this));
        setCurrent(replyEntry);
    }

    /**
     * <p>
     * set the display to current display.
     * </p>
     *
     * @param display
     *            the display to set to current.
     */
    public void setCurrent(Displayable display) {
        Display.getDisplay(this).setCurrent(display);
    }

    /**
     * <p>
     * Show the log canvas.
     * </p>
     */
    public void viewLog() {
        setCurrent(Logger.getLogCanvas());
    }

    /**
     * <p>
     * Destroy the application.
     * </p>
     *
     * @param arg0
     *            if detroyApp
     *
     * @throws MIDletStateChangeException
     *             if state change exception occurs.
     */
    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // do nothing
    }

    /**
     * <p>
     * Pause the application.
     * </p>
     */
    protected void pauseApp() {
        // do nothing
    }

    /**
     * <p>
     * Start up of the application.
     * </p>
     *
     * @throws MIDletStateChangeException
     *             if any error occurs.
     */
    protected void startApp() throws MIDletStateChangeException {
        String title = "all the feed content entries";
        entries = new RSSFeedContentEntry[10];
        boolean read = true;
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new RSSFeedContentEntry("name:" + i, "objectId:" + i,
                    "description:" + i, new URL("http://www.topcoder.com/"),
                    read, new Date());
            read = !read;
        }
        RSSFeedContent feed = new RSSFeedContent("name", "objectId",
                "description", new URL("http://www.topcoder.com/"), new Date(),
                entries);
        showFeedList(title, feed);
    }

    /**
     * <p>
     * CommandListener for the list page.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class ViewFeedListener implements CommandListener {
        /**
         * <p>
         * the MIDlet instance.
         * </p>
         */
        private DemoTest controller;

        /**
         * Constructor of the listener.
         *
         * @param controller
         *            the MIDlet instance.
         */
        public ViewFeedListener(DemoTest controller) {
            // initialize something if you need
            this.controller = controller;
        }

        /**
         * <p>
         * The command action method of the listener.
         * </p>
         * @param command command
         * @param displayable displayable
         */
        public void commandAction(Command command, Displayable displayable) {
            // For instance, if the command is "View Feed"
            if (command.getLabel().equals("View Feed")) {
                currentSelectedIndex = viewFeed.getSelectedIndex();
                RSSFeedContentEntry entry = entries[currentSelectedIndex];
                // Let the controller set and display the new Screen
                controller.showFeedEntry(entry.getName(), entry);
            } else if (command.getLabel().equals("Log")) {
                controller.viewLog();
            }
        }
    }

    /**
     * <p>
     * CommandListener for the read page.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class ReadEntryListener implements CommandListener {
        /**
         * <p>
         * the MIDlet instance.
         * </p>
         */
        private DemoTest controller;

        /**
         * <p>
         * The constructor.
         * </p>
         * @param controller controller
         */
        public ReadEntryListener(DemoTest controller) {
            this.controller = controller;
        }

        /**
         * <p>
         * The command action method of the listener.
         * </p>
         * @param command command
         * @param displayable displayable
         */
        public void commandAction(Command command, Displayable displayable) {
            // For instance, if the command is "Reply"
            if (command.getLabel().equals("Reply")) {
                RSSFeedContentEntry entry = entries[currentSelectedIndex];
                // Let the controller set and display the new Screen
                controller.replyFeedEntry(entry.getName(), entry);
            } else if (command.getLabel().equals("Back")) {
                controller.setCurrent(viewFeed);
            } else if (command.getLabel().equals("Log")) {
                controller.viewLog();
            }
        }
    }

    /**
     * <p>
     * CommandListener for the reply page.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class ReplyEntryListener implements CommandListener {
        /**
         * <p>
         * the MIDlet instance.
         * </p>
         */
        private DemoTest controller;

        /**
         * <p>
         * Default constructor.
         * </p>
         * @param controller controller
         */
        public ReplyEntryListener(DemoTest controller) {
            this.controller = controller;
        }

        /**
         * <p>
         * The command action method of the listener.
         * </p>
         * @param command command
         * @param displayable displayable
         */
        public void commandAction(Command command, Displayable displayable) {
            // For instance, if the command is "Back", return to
            if (command.getLabel().equals("Clear")) {
                replyEntry.clear();
            } else if (command.getLabel().equals("Post")) {
                replyEntry.append("\nMessage:" + replyEntry.getPostMessage()
                        + " Posted !");
            } else if (command.getLabel().equals("Log")) {
                controller.viewLog();
            }
        }
    }

}

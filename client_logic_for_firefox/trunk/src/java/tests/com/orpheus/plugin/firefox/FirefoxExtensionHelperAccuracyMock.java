/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import com.topcoder.util.rssgenerator.RSSFeed;

/**
 * Defines a mocked version of <code>FirefoxExtensionHelper</code> class. The mocked version does not process the
 * RSSFeed. Instead, it only records the RSSFeed. The calls to the pop up window methods are monitored.
 * 
 * @author visualage
 * @version 1.0
 */
public class FirefoxExtensionHelperAccuracyMock extends FirefoxExtensionHelper {
    /** Represents the last RSS feed processed. */
    private RSSFeed feed;

    /** Represents the last method called. */
    private String method;

    /**
     * Gets the last processed RSS feed.
     * 
     * @return the last processed RSS feed.
     */
    public RSSFeed getFeed() {
        return feed;
    }

    /**
     * Processes the RSS feed polled by the server. It only records the RSS feed.
     * 
     * @param feed the RSS feed to be processed.
     */
    void serverMessageReceived(RSSFeed feed) {
        this.feed = feed;
    }

    /**
     * Defines a mocked version of <code>popupWindow</code>.
     */
    public void popupWindow(String url, boolean status, boolean toolbar, boolean location, boolean menubar,
        boolean directories, boolean resizable, boolean scrollbars, int height, int width) {
        method = "popupWindow(\"" + url + "\"," + status + "," + toolbar + "," + location + "," + menubar + ","
            + directories + "," + resizable + "," + scrollbars + "," + height + "," + width + ")";
    }

    /**
     * Defines a mocked version of <code>popupWindowWithContent</code>.
     */
    public void popupWindowWithContent(String content, boolean status, boolean toolbar, boolean location,
        boolean menubar, boolean directories, boolean resizable, boolean scrollbars, int height, int width) {
        method = "popupWindowWithContent(\"" + content + "\"," + status + "," + toolbar + "," + location + ","
            + menubar + "," + directories + "," + resizable + "," + scrollbars + "," + height + "," + width + ")";
    }

    /**
     * Gets the last called method in this instance.
     * 
     * @return the last called method in this instance.
     */
    public String getCalledMethod() {
        return method;
    }
}

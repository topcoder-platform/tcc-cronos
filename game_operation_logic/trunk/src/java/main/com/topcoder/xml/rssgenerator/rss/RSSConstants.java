/*
 * RSSConstants.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

/**
 * <p>
 * A class to encapsulate all the constants shared by the rss implementations of different versions.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public class RSSConstants {
    /**
     * <p>
     * Represents the tag name of item of the feed, its value is 'item'.
     * </p>
     */
    public static final String ITEM = "item";

    /**
     * <p>
     * Represents the tag name of channel of the feed, its value is 'channel'.
     * </p>
     */
    public static final String CHANNEL = "channel";

    /**
     * <p>
     * Represents the tag name of rss of the feed, its value is 'rss'.
     * </p>
     */
    public static final String RSS = "rss";

    /**
     * <p>
     * Represents the version attribute of the rss node.
     * </p>
     */
    public static final String VERSION = "version";

    /**
     * <p>
     * Represents the tag name of title node.
     * </p>
     */
    public static final String TITLE = "title";

    /**
     * <p>
     * Represents the tag name of link node.
     * </p>
     */
    public static final String LINK = "link";

    /**
     * <p>
     * Represents the tag name of description node.
     * </p>
     */
    public static final String DESCRIPTION = "description";

    /**
     * <p>
     * Represents the tag name of languange node.
     * </p>
     */
    public static final String LANGUAGE = "language";

    /**
     * <p>
     * Represents the tag name of pubDate node.
     * </p>
     */
    public static final String PUB_DATE = "pubDate";

    /**
     * <p>
     * Represents the text name of the TextInput branch.
     * </p>
     */
    public static final String TEXT_NAME = "name";

    /**
     * <p>
     * Represents the ttl tag in the rss feed.
     * </p>
     */
    public static final String TTL = "ttl";

    /**
     * <p>
     * Represents cloud tag in the rss feed.
     * </p>
     */
    public static final String CLOUD = "cloud";

    /**
     * <p>
     * Represents the textInput in the rss feed.
     * </p>
     */
    public static final String TEXT_INPUT = "textInput";

    /**
     * <p>
     * Represents the copyright in rss feed.
     * </p>
     */
    public static final String COPYRIGHT = "copyright";

    /**
     * <p>
     * Represents the managingEditor in rss feed.
     * </p>
     */
    public static final String MANAGING_EDITOR = "managingEditor";

    /**
     * <p>
     * Represents the webMaster in rss feed.
     * </p>
     */
    public static final String WEB_MASTER = "webMaster";

    /**
     * <p>
     * Represents the lastBuildDate in rss feed.
     * </p>
     */
    public static final String LAST_BUILD_DATE = "lastBuildDate";

    /**
     * <p>
     * Represents the category in rss feed or rss item (v.2.0)
     * </p>
     */
    public static final String CATEGORY = "category";

    /**
     * <p>
     * Represents the generator in rss feed.
     * </p>
     */
    public static final String GENERATOR = "generator";

    /**
     * <p>
     * Represents the docs in rss feed.
     * </p>
     */
    public static final String DOCS = "docs";

    /**
     * <p>
     * Represents image in rss feed.
     * </p>
     */
    public static final String IMAGE = "image";

    /**
     * <p>
     * Represents author in rss item.
     * </p>
     */
    public static final String AUTHOR = "author";

    /**
     * <p>
     * Represents comments in rss item.
     * </p>
     */
    public static final String COMMENTS = "comments";

    /**
     * <p>
     * Represents enclosure in rss item.
     * </p>
     */
    public static final String ENCLOSURE = "enclosure";

    /**
     * <p>
     * Represents guid in rss item.
     * </p>
     */
    public static final String GUID = "guid";

    /**
     * <p>
     * Represents the source in rss item.
     * </p>
     */
    public static final String SOURCE = "source";

    /**
     * <p>
     * Represents the rating in rss feed.
     * </p>
     */
    public static final String RATING = "rating";

    /**
     * <p>
     * Represents url attribute or node name.
     * </p>
     */
    public static final String URL = "url";

    /**
     * <p>
     * Represents the isPermaLink attribute of the guid node.
     * </p>
     */
    public static final String IS_PERMA_LINK = "isPermaLink";

    /**
     * <p>
     * Represents the domain attribute name.
     * </p>
     */
    public static final String DOMAIN = "domain";

    /**
     * <p>
     * Represents the length attribute name.
     * </p>
     */
    public static final String LENGTH = "length";

    /**
     * <p>
     * Represents the type attribute name.
     * </p>
     */
    public static final String TYPE = "type";

    /**
     * <p>
     * Represents the width attribute name.
     * </p>
     */
    public static final String WIDTH = "width";

    /**
     * <p>
     * Represents the height attribute name.
     * </p>
     */
    public static final String HEIGHT = "height";

    /**
     * <p>
     * Represents the path attribute name.
     * </p>
     */
    public static final String PATH = "path";

    /**
     * <p>
     * Represents the port attribute name.
     * </p>
     */
    public static final String PORT = "port";

    /**
     * <p>
     * Represents the protocol attribute name.
     * </p>
     */
    public static final String PROTOCOL = "protocol";

    /**
     * <p>
     * Represents the registerProcedure attribute name.
     * </p>
     */
    public static final String REGISTER_PROCEDURE = "registerProcedure";

    /**
     * <p>
     * Represents the skipDays node in rss feed.
     * </p>
     */
    public static final String SKIP_DAYS = "skipDays";

    /**
     * <p>
     * Represents the skipHours node in rss feed.
     * </p>
     */
    public static final String SKIP_HOURS = "skipHours";

    /**
     * <p>
     * Represents the day node of the skipDays branch.
     * </p>
     */
    public static final String DAY = "day";

    /**
     * <p>
     * Represents the hour node of the skipHours branch.
     * </p>
     */
    public static final String HOUR = "hour";

    /**
     * <p>
     * The default name of the Feed node. This name is just a placeholder and is not used for rendering the root node.
     * </p>
     */
    public static final String FEED_NAME = "feed";

    /**
     * <p>
     * Private constructor to ensure no instance can be created.
     * </p>
     */
    private RSSConstants() {}
}

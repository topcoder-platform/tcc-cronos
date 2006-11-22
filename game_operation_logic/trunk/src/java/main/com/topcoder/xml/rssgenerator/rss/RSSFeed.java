/*
 * RSSFeed.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.xml.rssgenerator.rss;

import com.topcoder.xml.rssgenerator.Feed;
import com.topcoder.xml.rssgenerator.FeedBranch;
import com.topcoder.xml.rssgenerator.FeedNode;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * RSSFeed class models the feed xml which can be shared by the rss spec 0.91, 0.92 and 2.0.
 * </p>
 * 
 * <p>
 * Note that passing a null value to the setter methods will remove the child node from the branch. The getter methods
 * return null if the child node does not exist.
 * </p>
 *
 * @author air2cold
 * @author colau
 * @version 1.0
 */
public abstract class RSSFeed extends Feed {
    /**
     * <p>
     * Create a RSSFeed instance with the feed type.
     * </p>
     *
     * @param type the type of the feed.
     *
     * @throws NullPointerException if the type is null.
     * @throws IllegalArgumentException if the type is empty.
     */
    protected RSSFeed(String type) {
        super(type);
    }

    /**
     * <p>
     * Return the value of its link subelement.
     * </p>
     *
     * @return the value of its link subelement.
     */
    public String getLink() {
        return getChildValue(RSSConstants.LINK);
    }

    /**
     * <p>
     * Set the value of its link subelement.
     * </p>
     *
     * @param link the value of its link subelement.
     *
     * @throws IllegalArgumentException if link is empty.
     */
    public void setLink(String link) {
        setChild(RSSConstants.LINK, link);
    }

    /**
     * <p>
     * Return the value of its title subelement.
     * </p>
     *
     * @return the value of its title subelement.
     */
    public String getTitle() {
        return getChildValue(RSSConstants.TITLE);
    }

    /**
     * <p>
     * Set the value of its title subelement.
     * </p>
     *
     * @param title the value of its title subelement.
     *
     * @throws IllegalArgumentException if title is empty.
     */
    public void setTitle(String title) {
        setChild(RSSConstants.TITLE, title);
    }

    /**
     * <p>
     * Return the value of its description subelement.
     * </p>
     *
     * @return the value of its description subelement.
     */
    public String getDescription() {
        return getChildValue(RSSConstants.DESCRIPTION);
    }

    /**
     * <p>
     * Set the value of its description subelement.
     * </p>
     *
     * @param desc the value of its description subelement.
     *
     * @throws IllegalArgumentException if desc is empty.
     */
    public void setDescription(String desc) {
        setChild(RSSConstants.DESCRIPTION, desc);
    }

    /**
     * <p>
     * Return the value of its language subelement.
     * </p>
     *
     * @return the value of its language subelement.
     */
    public String getLanguage() {
        return getChildValue(RSSConstants.LANGUAGE);
    }

    /**
     * <p>
     * Set the value of its language subelement.
     * </p>
     *
     * @param language the value of its language subelement.
     *
     * @throws IllegalArgumentException if language is empty.
     */
    public void setLanguage(String language) {
        setChild(RSSConstants.LANGUAGE, language);
    }

    /**
     * <p>
     * Return&nbsp;the value of its copyright subelement.
     * </p>
     *
     * @return the value of its copyright subelement.
     */
    public String getCopyright() {
        return getChildValue(RSSConstants.COPYRIGHT);
    }

    /**
     * <p>
     * Set the value of its copyright subelement.
     * </p>
     *
     * @param copyright the value of its copyright subelement.
     *
     * @throws IllegalArgumentException if copyright is empty.
     */
    public void setCopyright(String copyright) {
        setChild(RSSConstants.COPYRIGHT, copyright);
    }

    /**
     * <p>
     * Return the value of its managingEditor subelement.
     * </p>
     *
     * @return the value of its managingEditor subelement.
     */
    public String getManagingEditor() {
        return getChildValue(RSSConstants.MANAGING_EDITOR);
    }

    /**
     * <p>
     * Set the value of its managingEditor subelement.
     * </p>
     *
     * @param managingEditor the value of its managingEditor subelement.
     *
     * @throws IllegalArgumentException if managingEditor is empty.
     */
    public void setManagingEditor(String managingEditor) {
        setChild(RSSConstants.MANAGING_EDITOR, managingEditor);
    }

    /**
     * <p>
     * Return the value of its webMaster subelement.
     * </p>
     *
     * @return the value of its webMaster subelement.
     */
    public String getWebMaster() {
        return getChildValue(RSSConstants.WEB_MASTER);
    }

    /**
     * <p>
     * Set the value of its webMaster subelement.
     * </p>
     *
     * @param webMaster the value of its webMaster subelement.
     *
     * @throws IllegalArgumentException if webMaster is empty.
     */
    public void setWebMaster(String webMaster) {
        setChild(RSSConstants.WEB_MASTER, webMaster);
    }

    /**
     * <p>
     * Return the value of its pubDate subelement.
     * </p>
     *
     * @return the value of its pubDate subelement.
     */
    public String getPubDate() {
        return getChildValue(RSSConstants.PUB_DATE);
    }

    /**
     * <p>
     * Set the value of its pubDate subelement.
     * </p>
     *
     * @param pubDate the value of its pubDate subelement.
     *
     * @throws IllegalArgumentException if pubDate is empty.
     */
    public void setPubDate(String pubDate) {
        setChild(RSSConstants.PUB_DATE, pubDate);
    }

    /**
     * <p>
     * Return the value of its lastBuildDate subelement.
     * </p>
     *
     * @return the value of its lastBuildDate subelement.
     */
    public String getLastBuildDate() {
        return getChildValue(RSSConstants.LAST_BUILD_DATE);
    }

    /**
     * <p>
     * Set the value of its lastBuildDate subelement.
     * </p>
     *
     * @param lastBuildDate the value of its lastBuildDate subelement.
     *
     * @throws IllegalArgumentException if lastBuildDate is empty.
     */
    public void setLastBuildDate(String lastBuildDate) {
        setChild(RSSConstants.LAST_BUILD_DATE, lastBuildDate);
    }

    /**
     * <p>
     * Return image child node.
     * </p>
     *
     * @return image child node.
     */
    public Image getImage() {
        return (Image) getChild(RSSConstants.IMAGE);
    }

    /**
     * <p>
     * Set image child node.
     * </p>
     *
     * @param image image child node.
     */
    public void setImage(Image image) {
        setChild(RSSConstants.IMAGE, image);
    }

    /**
     * <p>
     * Return the value of doc subelement.
     * </p>
     *
     * @return the value of doc subelement.
     */
    public String getDocs() {
        return getChildValue(RSSConstants.DOCS);
    }

    /**
     * <p>
     * Set the value of doc subelement.
     * </p>
     *
     * @param docs the value of doc subelement.
     *
     * @throws IllegalArgumentException if docs is empty.
     */
    public void setDocs(String docs) {
        setChild(RSSConstants.DOCS, docs);
    }

    /**
     * <p>
     * Return the value of rating subelement.
     * </p>
     *
     * @return the value of rating subelement.
     */
    public String getRating() {
        return getChildValue(RSSConstants.RATING);
    }

    /**
     * <p>
     * Set the value of rating subelement.
     * </p>
     *
     * @param rating the value of rating subelement.
     *
     * @throws IllegalArgumentException if rating is empty.
     */
    public void setRating(String rating) {
        setChild(RSSConstants.RATING, rating);
    }

    /**
     * <p>
     * Return the textInput subelement.
     * </p>
     *
     * @return the textInput subelement.
     */
    public TextInput getTextInput() {
        return (TextInput) getChild(RSSConstants.TEXT_INPUT);
    }

    /**
     * <p>
     * Set the textInput subelement.
     * </p>
     *
     * @param textInput the textInput subelement.
     */
    public void setTextInput(TextInput textInput) {
        setChild(RSSConstants.TEXT_INPUT, textInput);
    }

    /**
     * <p>
     * Return the value of skipHours node. An empty array will be returned if there is no skipHours node.
     * </p>
     *
     * @return the value of skipHours node.
     */
    public int[] getSkipHours() {
        // get skipHours node
        FeedBranch branch = (FeedBranch) getChild(RSSConstants.SKIP_HOURS);

        // no skipHours node found
        if (branch == null) {
            return new int[0];
        }

        // get hour nodes under skipHours node
        List nodes = branch.getChildren(RSSConstants.HOUR);
        int[] hours = new int[nodes.size()];
        int count = 0;

        for (Iterator i = nodes.iterator(); i.hasNext();) {
            hours[count++] = Integer.parseInt(((FeedNode) i.next()).getValue());
        }
        return hours;
    }

    /**
     * <p>
     * Set the value of skipHours node. Each element should be a number between 0 and 23.
     * </p>
     *
     * @param skipHours the value of skipHours node.
     *
     * @throws IllegalArgumentException if skipHours is empty, or some element of skipHours is out of range.
     */
    public void setSkipHours(int[] skipHours) {
        // the minimum value of hour
        final int MIN_HOUR = 0;

        // the maximum value of hour
        final int MAX_HOUR = 23;

        if (skipHours == null) {
            removeChildren(RSSConstants.SKIP_HOURS);
        } else if (skipHours.length == 0) {
            throw new IllegalArgumentException("skipHours is empty");
        } else {
            // check the skipHours array
            for (int i = 0; i < skipHours.length; i++) {
                if ((skipHours[i] < MIN_HOUR) || (skipHours[i] > MAX_HOUR)) {
                    throw new IllegalArgumentException("some element of skipHours is out of range");
                }
            }

            FeedBranch branch = new FeedBranch(RSSConstants.SKIP_HOURS);

            // add skipHours node
            setChild(RSSConstants.SKIP_HOURS, branch);

            // add each hour to skipHours node
            for (int i = 0; i < skipHours.length; i++) {
                FeedNode node = new FeedNode(RSSConstants.HOUR);

                node.setValue(String.valueOf(skipHours[i]));
                branch.addChild(node);
            }
        }
    }

    /**
     * <p>
     * Return the value of skipDays node. An empty array will be returned if there is no skipDays node.
     * </p>
     *
     * @return the value of skipDays node.
     */
    public String[] getSkipDays() {
        // get skipDays node
        FeedBranch branch = (FeedBranch) getChild(RSSConstants.SKIP_DAYS);

        // no skipDays node found
        if (branch == null) {
            return new String[0];
        }

        // get day nodes under skipDays node
        List nodes = branch.getChildren(RSSConstants.DAY);
        String[] days = new String[nodes.size()];
        int count = 0;

        for (Iterator i = nodes.iterator(); i.hasNext();) {
            days[count++] = ((FeedNode) i.next()).getValue();
        }
        return days;
    }

    /**
     * <p>
     * Set the value of skipDays node. Each element should be one of: Monday, Tuesday, Wednesday, Thursday, Friday,
     * Saturday or Sunday.
     * </p>
     *
     * @param skipDays the value of skipDays node.
     *
     * @throws IllegalArgumentException if skipDays is empty or some element of skipDays is null or has invalid value.
     */
    public void setSkipDays(String[] skipDays) {
        // valid values of day
        final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        if (skipDays == null) {
            removeChildren(RSSConstants.SKIP_DAYS);
        } else if (skipDays.length == 0) {
            throw new IllegalArgumentException("skipDays is empty");
        } else {
            // check the skipDays array
            List days = Arrays.asList(DAYS);

            for (int i = 0; i < skipDays.length; i++) {
                if (skipDays[i] == null) {
                    throw new IllegalArgumentException("some element of skipDays is null");
                }
                if (!days.contains(skipDays[i])) {
                    throw new IllegalArgumentException("some element of skipDays has invalid value");
                }
            }

            FeedBranch branch = new FeedBranch(RSSConstants.SKIP_DAYS);

            // add skipDays node
            setChild(RSSConstants.SKIP_DAYS, branch);

            // add each day to skipDays node
            for (int i = 0; i < skipDays.length; i++) {
                FeedNode node = new FeedNode(RSSConstants.DAY);

                node.setValue(skipDays[i]);
                branch.addChild(node);
            }
        }
    }

    /**
     * <p>
     * Common operations shared by the setter methods. The name argument must not be null.
     * </p>
     * 
     * <p>
     * If value is null, remove the child node.
     * </p>
     * 
     * <p>
     * If value is empty string, throw IllegalArgumentException.
     * </p>
     * 
     * <p>
     * Otherwise, assign the value to the child node.
     * </p>
     *
     * @param name the name of the child node.
     * @param value the value of the child node.
     *
     * @throws IllegalArgumentException if value is empty.
     */
    private void setChild(String name, String value) {
        if (value == null) {
            removeChildren(name);
        } else if (value.trim().length() == 0) {
            throw new IllegalArgumentException("value is empty");
        } else {
            setChildValue(name, value);
        }
    }

    /**
     * <p>
     * Common operations shared by the setter methods. The name argument must not be null.
     * </p>
     * 
     * <p>
     * First removes the child nodes under the given name. Adds the new child node if it is not null.
     * </p>
     *
     * @param name the name of the child node.
     * @param child the child node to add.
     */
    private void setChild(String name, FeedNode child) {
        removeChildren(name);

        if (child != null) {
            addChild(child);
        }
    }
}

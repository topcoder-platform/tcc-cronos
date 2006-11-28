
package com.topcoder.util.rssgenerator.impl.rss20;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: Extends the RSSFeedImpl class to add properties specific to RSS 2.0 <p>Implementation: We decorate over some more properties to provide objects specific to this format. We extend RSSFeedImpl</p> <p>Thread Safety: This class is not thread safe as its base class is not thread safe either.</p>
 *
 */
public class RSS20Feed extends com.topcoder.util.rssgenerator.impl.RSSFeedImpl {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSFeed interface or its sub-interfaces will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 *
 */
    public  RSS20Feed() {
        // your code here
    }

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 *
 *
 * @param object
 */
    public  RSS20Feed(com.topcoder.util.rssgenerator.RSSObject object) {
        // your code here
    }

/**
 * <p>Purpose: Gets the managing editor of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:managingEditor&quot;</p> <p>Returns: The managing editor of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSEntity getManagingEditor() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Gets the web master of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:webMaster&quot;</p> <p>Returns: The web master of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSEntity getWebMaster() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Gets the docs of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:docs&quot;</p> <p>Returns: The docs of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public java.net.URI getDocs() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Gets the cloud of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:cloud&quot;</p> <p>Returns: The cloud of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.impl.rss20.RSS20Cloud getCloud() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Gets the time-to-live of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:ttl&quot;</p> <p>Returns: The time-to-live of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public Integer getTtl() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Gets the rating of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:rating&quot;</p> <p>Returns: The rating of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String getRating() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Gets the text-input of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:textInput&quot;</p> <p>Returns: The text-input of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public com.topcoder.util.rssgenerator.impl.rss20.RSS20TextInput getTextInput() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Gets the skip hours of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipHours&quot;</p> <p>Returns: The skip hours of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public Integer[] getSkipHours() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Gets the skip days of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipDays&quot;</p> <p>Returns: The skip days of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String[] getSkipDays() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Sets the managing editor of the feed.</p> <p>Args: managingEditor - The managing editor to set. Possibly null..</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:managingEditor&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param managingEditor
 */
    public void setManagingEditor(com.topcoder.util.rssgenerator.RSSEntity managingEditor) {
        // your code here
    }

/**
 * <p>Purpose: Sets the web master of the feed.</p> <p>Args: webMaster - The web master to set. Possibly null..</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:webMaster&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param webMaster
 */
    public void setWebMaster(com.topcoder.util.rssgenerator.RSSEntity webMaster) {
        // your code here
    }

/**
 * <p>Purpose: Sets the docs of the feed.</p> <p>Args: docs - The docs to set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:docs&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param docs
 */
    public void setDocs(java.net.URI docs) {
        // your code here
    }

/**
 * <p>Purpose: Sets the cloud of the feed.</p> <p>Args: cloud - The cloud to set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:cloud&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p></p>
 *
 *
 * @param cloud
 */
    public void setCloud(com.topcoder.util.rssgenerator.impl.rss20.RSS20Cloud cloud) {
        // your code here
    }

/**
 * <p>Purpose: Sets the time-to-live of the feed.</p> <p>Args: ttl - The time-to-live to set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:ttl&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param ttl
 */
    public void setTtl(Integer ttl) {
        // your code here
    }

/**
 * <p>Purpose: Sets the rating of the feed.</p> <p>Args: rating - The rating to set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:rating&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p></p>
 *
 *
 * @param rating
 */
    public void setRating(String rating) {
        // your code here
    }

/**
 * <p>Purpose: Sets the skip hours of the feed.</p> <p>Args: skipHours - The skip hours to set. Possibly null. No element must be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipHours&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If any element is null.</p>
 *
 *
 * @param skipHours
 */
    public void setSkipHours(Integer[] skipHours) {
        // your code here
    }

/**
 * <p>Purpose: Adds to the skip hours of the feed.</p> <p>Args: skipHour - The skip hour to add. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipHours&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If skipHour is null.</p> <p></p>
 *
 *
 * @param skipHour
 */
    public void addSkipHour(Integer skipHour) {
        // your code here
    }

/**
 * <p>Purpose: Removes from the skip hours of the feed.</p> <p>Args: skipHour - The skip hour to remove. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipHours&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If skipHour is null.</p> <p></p>
 *
 *
 * @param skipHour
 */
    public void removeSkipHour(Integer skipHour) {
        // your code here
    }

/**
 * <p>Purpose: Clears the skip hours of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipHours&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 */
    public void clearSkipHours() {
        // your code here
    }

/**
 * <p>Purpose: Sets the skip days of the feed.</p> <p>Args: skipDays - The skip days to set. Possibly null. No element must be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipDays&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If any element is null.</p>
 *
 *
 * @param skipDays
 */
    public void setSkipDays(String[] skipDays) {
        // your code here
    }

/**
 * <p>Purpose: Adds to the skip days of the feed.</p> <p>Args: skipDay - The skip day to add. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipDays&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If skipDay is null.</p>
 *
 *
 * @param skipDay
 */
    public void addSkipDay(String skipDay) {
        // your code here
    }

/**
 * <p>Purpose: Removes from the skip days of the feed.</p> <p>Args: skipDay - The skip day to remove. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipDays&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If skipDay is null.</p>
 *
 *
 * @param skipDay
 */
    public void removeSkipDay(String skipDay) {
        // your code here
    }

/**
 * <p>Purpose: Clears the skip days of the feed.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:skipDays&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 */
    public void clearSkipDays() {
        // your code here
    }
 }

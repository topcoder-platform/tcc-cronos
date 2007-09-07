/*
 * Created on May 10, 2005
 */
package com.topcoder.web.forums.controller.request;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.JiveGlobals;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;
import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumCategory;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ReadTracker;
import com.jivesoftware.forum.WatchManager;
import com.topcoder.web.common.BaseProcessor;
import com.topcoder.web.forums.ForumConstants;

import java.util.Iterator;

/**
 * @author mtong
 *         <p/>
 *         Base class for forum-related processors, providing message and user data.
 */
public abstract class ForumsProcessor extends BaseProcessor {
    protected AuthToken authToken;
    protected ForumFactory forumFactory;
    protected User user;


    /**
     * Subclasses should do their work by implementing this method.
     */
    protected void businessProcessing() throws Exception {
        getRequest().setAttribute("authToken", authToken);
        getRequest().setAttribute("user", user);
        getRequest().setAttribute("forumFactory", forumFactory);

        String timezone = user == null ? ForumConstants.DEFAULT_TIMEZONE :  
            JiveGlobals.getJiveProperty(user.getProperty("timezone"), ForumConstants.DEFAULT_TIMEZONE);
        getRequest().setAttribute("timezone", timezone);
        
        setUnreadCategories();
    }

    //  Determine categories with unread forums
    protected void setUnreadCategories() throws UnauthorizedException {
        ReadTracker readTracker = forumFactory.getReadTracker();
        WatchManager watchManager = forumFactory.getWatchManager();
        StringBuffer unreadCategories = new StringBuffer();
        ForumCategory rootCategory = forumFactory.getRootForumCategory();
        Iterator itCategories = rootCategory.getCategories();
        while (itCategories.hasNext()) {
            ForumCategory category = (ForumCategory) itCategories.next();
            Iterator itForums = category.getForums();
            boolean isCategoryRead = true;
            while (isCategoryRead && itForums.hasNext()) {
                Forum forum = (Forum) itForums.next();
                if (user != null && forum.getLatestMessage() != null
                        && readTracker.getReadStatus(user, forum.getLatestMessage()) != ReadTracker.READ
                        && !("true".equals(user.getProperty("markWatchesRead")) && watchManager.isWatched(user, forum.getLatestMessage().getForumThread()))) {
                    isCategoryRead = false;
                }
            }
            if (category.getProperty(ForumConstants.PROPERTY_LEFT_NAV_NAME) == null) {
                if (log.isDebugEnabled()) {
                    log.debug("category nav name is null " + category.getDescription());
                }
            }
            if (!isCategoryRead && category.getProperty(ForumConstants.PROPERTY_LEFT_NAV_NAME) != null) {
                unreadCategories.append(category.getProperty(ForumConstants.PROPERTY_LEFT_NAV_NAME)).append(',');
            }
        }
        if (unreadCategories.length() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("unread:" + unreadCategories);
            }
            getRequest().setAttribute("unreadCategories",
                    unreadCategories.substring(0, unreadCategories.length() - 1));
        } else {
            if (log.isDebugEnabled()) {
                log.debug("no unread categories");
            }
            getRequest().setAttribute("unreadCategories", "");
        }
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setForumFactory(ForumFactory forumFactory) {
        this.forumFactory = forumFactory;
    }

    public boolean isGuest() {
        return authToken.isAnonymous();
    }
}

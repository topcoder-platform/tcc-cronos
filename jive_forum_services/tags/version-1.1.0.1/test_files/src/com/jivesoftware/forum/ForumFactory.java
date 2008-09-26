package com.jivesoftware.forum;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.GroupManager;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.UserManager;

import com.jivesoftware.forum.mock.MockForumFactory;


public abstract class ForumFactory {
    public static ForumFactory getInstance(AuthToken authToken) {
        return MockForumFactory.getInstance();
    }

    public abstract GroupManager getGroupManager();

    public abstract UserManager getUserManager();

    public abstract WatchManager getWatchManager();

    public abstract ForumCategory getForumCategory(long categoryID)
        throws ForumCategoryNotFoundException;

    public abstract Forum getForum(long forumID)
        throws ForumNotFoundException, UnauthorizedException;

    public abstract ForumThread getForumThread(long threadID)
        throws ForumThreadNotFoundException, UnauthorizedException;

    public abstract Forum createForum(String name, String description,
        ForumCategory destCategory) throws UnauthorizedException;
}

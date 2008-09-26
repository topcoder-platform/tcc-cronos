package com.jivesoftware.forum.mock;

import com.jivesoftware.base.GroupManager;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.UserManager;

import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumCategory;
import com.jivesoftware.forum.ForumCategoryNotFoundException;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ForumNotFoundException;
import com.jivesoftware.forum.ForumThread;
import com.jivesoftware.forum.ForumThreadNotFoundException;
import com.jivesoftware.forum.WatchManager;


public class MockForumFactory extends ForumFactory {
    private static boolean exception = false;
    private static MockForumFactory instance;
    private GroupManager groupManager = new MockGroupManager();
    private UserManager userManager = new MockUserManager();
    private WatchManager watchManager = new MockWatchManager();
    private MockForumCategory aForumCategory = new MockForumCategory();
    private MockForumCategory rootForumCategory = new MockForumCategory();
    private MockForum aForum = new MockForum();
    private MockForumThread aForumThread = new MockForumThread();

    public MockForumFactory() {
        aForumCategory.setID(1);
        rootForumCategory.setID(10);
        aForum.setID(1);
        aForumThread.setID(1);
    }

    public static MockForumFactory getInstance() {
        if (exception) {
            return null;
        }

        if (instance == null) {
            instance = new MockForumFactory();
        }

        return instance;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public WatchManager getWatchManager() {
        return watchManager;
    }

    public ForumCategory getForumCategory(long categoryID)
        throws ForumCategoryNotFoundException {
        if (categoryID == 10) {
            return rootForumCategory;
        }

        if (categoryID != 1) {
            throw new ForumCategoryNotFoundException(
                "Simulate Excpeiton: There is only one category, which id is 1.");
        }

        return aForumCategory;
    }

    public Forum getForum(long forumID)
        throws ForumNotFoundException, UnauthorizedException {
        if (exception) {
            throw new UnauthorizedException("Simulate Exception.");
        }

        if (forumID != 1) {
            throw new ForumNotFoundException(
                "Simulate Exception: There is only one forum, which id is 1.");
        }

        return aForum;
    }

    public ForumThread getForumThread(long threadID)
        throws ForumThreadNotFoundException, UnauthorizedException {
        if (exception) {
            throw new UnauthorizedException("Simulate Exception.");
        }

        if (threadID != 1) {
            throw new ForumThreadNotFoundException(
                "Simulate Exception: There is only on forum thread, which id is 1.");
        }

        return null;
    }

    public static void setException(boolean val) {
        exception = val;
    }

    public Forum createForum(String name, String description,
        ForumCategory destCategory) throws UnauthorizedException {
        MockForum forum = new MockForum();

        forum.setName(name);
        forum.setDescription(description);

        return forum;
    }
}

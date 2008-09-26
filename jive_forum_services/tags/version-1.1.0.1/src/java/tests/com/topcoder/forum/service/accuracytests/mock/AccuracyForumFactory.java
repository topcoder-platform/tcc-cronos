package com.topcoder.forum.service.accuracytests.mock;

import java.util.Iterator;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.FilterManager;
import com.jivesoftware.base.GroupManager;
import com.jivesoftware.base.Permissions;
import com.jivesoftware.base.PermissionsManager;
import com.jivesoftware.base.PollManager;
import com.jivesoftware.base.PresenceManager;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;
import com.jivesoftware.base.UserManager;

import com.jivesoftware.forum.AnnouncementManager;
import com.jivesoftware.forum.ArchiveManager;
import com.jivesoftware.forum.AttachmentManager;
import com.jivesoftware.forum.AvatarManager;
import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumCategory;
import com.jivesoftware.forum.ForumCategoryNotFoundException;
import com.jivesoftware.forum.ForumFactory;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.ForumMessageNotFoundException;
import com.jivesoftware.forum.ForumNotFoundException;
import com.jivesoftware.forum.ForumThread;
import com.jivesoftware.forum.ForumThreadNotFoundException;
import com.jivesoftware.forum.InterceptorManager;
import com.jivesoftware.forum.PrivateMessageManager;
import com.jivesoftware.forum.Query;
import com.jivesoftware.forum.QueryManager;
import com.jivesoftware.forum.QuestionManager;
import com.jivesoftware.forum.ReadTracker;
import com.jivesoftware.forum.ResultFilter;
import com.jivesoftware.forum.RewardManager;
import com.jivesoftware.forum.SearchManager;
import com.jivesoftware.forum.StatusLevelManager;
import com.jivesoftware.forum.WatchManager;

/**
 * Mock class.
 *
 * @author kaqi072821
 * @version 1.0
 */
public class AccuracyForumFactory extends ForumFactory {
    private static boolean exception = false;

    private static AccuracyForumFactory instance;

    private GroupManager groupManager = new AccuracyGroupManager();

    private UserManager userManager = new AccuracyUserManager();

    private WatchManager watchManager = new AccuracyWatchManager();

    private AccuracyForumCategory aForumCategory = new AccuracyForumCategory();

    private AccuracyForumCategory rootForumCategory = new AccuracyForumCategory();

    private AccuracyForum aForum = new AccuracyForum();

    private AccuracyForumThread aForumThread = new AccuracyForumThread();

    public AccuracyForumFactory() {
        aForumCategory.setID(1);
        rootForumCategory.setID(10);
        aForum.setID(1);
        aForumThread.setID(1);
    }

    public static AccuracyForumFactory getInstance() {
        if (exception) {
            return null;
        }

        if (instance == null) {
            instance = new AccuracyForumFactory();
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

    public ForumCategory getForumCategory(long categoryID) throws ForumCategoryNotFoundException {
        if (categoryID == 10) {
            return rootForumCategory;
        }

        if (categoryID != 1) {
            throw new ForumCategoryNotFoundException("Simulate Excpeiton: There is only one category, which id is 1.");
        }

        return aForumCategory;
    }

    public Forum getForum(long forumID) throws ForumNotFoundException, UnauthorizedException {
        if (exception) {
            throw new UnauthorizedException("Simulate Exception.");
        }

        if (forumID != 1) {
            throw new ForumNotFoundException("Simulate Exception: There is only one forum, which id is 1.");
        }

        return aForum;
    }

    public ForumThread getForumThread(long threadID) throws ForumThreadNotFoundException, UnauthorizedException {
        if (exception) {
            throw new UnauthorizedException("Simulate Exception.");
        }

        if (threadID != 1) {
            throw new ForumThreadNotFoundException("Simulate Exception: There is only on forum thread, which id is 1.");
        }

        return null;
    }

    public static void setException(boolean val) {
        exception = val;
    }

    public Forum createForum(String name, String description, ForumCategory destCategory) throws UnauthorizedException {
        AccuracyForum forum = new AccuracyForum();

        forum.setName(name);
        forum.setDescription(description);

        return forum;
    }

    public Forum createForum(String arg0, String arg1) throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public Query createQuery() {
        // TODO Auto-generated method stub
        return null;
    }

    public Query createQuery(Forum[] arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteForum(Forum arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub

    }

    public AnnouncementManager getAnnouncementManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public ArchiveManager getArchiveManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public AttachmentManager getAttachmentManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public AvatarManager getAvatarManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public FilterManager getFilterManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public Forum getForum(String arg0) throws ForumNotFoundException, UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public int getForumCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getForumCount(ResultFilter arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Iterator getForums() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getForums(ResultFilter arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public InterceptorManager getInterceptorManager() throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumMessage getMessage(long arg0) throws ForumMessageNotFoundException, UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public long getMessageID(long arg0, int arg1) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Permissions getPermissions(AuthToken arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public PermissionsManager getPermissionsManager() throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public PollManager getPollManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getPopularForums() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getPopularThreads() {
        // TODO Auto-generated method stub
        return null;
    }

    public PresenceManager getPresenceManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public PrivateMessageManager getPrivateMessageManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public QueryManager getQueryManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public QuestionManager getQuestionManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public ReadTracker getReadTracker() {
        // TODO Auto-generated method stub
        return null;
    }

    public RewardManager getRewardManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumCategory getRootForumCategory() {
        // TODO Auto-generated method stub
        return null;
    }

    public SearchManager getSearchManager() throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public StatusLevelManager getStatusLevelManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getUserMessageCount(User arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getUserMessageCount(User arg0, ResultFilter arg1) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Iterator getUserMessages(User arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getUserMessages(User arg0, ResultFilter arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isAuthorized(long arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public void mergeForums(Forum arg0, Forum arg1) throws UnauthorizedException {
        // TODO Auto-generated method stub

    }
}
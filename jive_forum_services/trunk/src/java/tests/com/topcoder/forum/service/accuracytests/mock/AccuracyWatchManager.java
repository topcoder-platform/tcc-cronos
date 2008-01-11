package com.topcoder.forum.service.accuracytests.mock;

import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;

import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumCategory;
import com.jivesoftware.forum.ForumThread;
import com.jivesoftware.forum.Watch;
import com.jivesoftware.forum.WatchManager;

import com.jivesoftware.util.CronTimer;

import java.util.Iterator;

/**
 * Mock class.
 *
 * @author kaqi072821
 * @version 1.0
 */
public class AccuracyWatchManager implements WatchManager {
    private static boolean watched = false;

    private static boolean threadWatched = false;

    private static boolean forumWatched = false;

    private static boolean categoryWatched = false;

    public int getDeleteDays() {
        return 0;
    }

    public void setDeleteDays(int arg0) throws UnauthorizedException {
    }

    public CronTimer getBatchTimer(User arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setBatchTimer(User arg0, CronTimer arg1) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public Watch createWatch(User user, User arg1) throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public Watch createWatch(User user, ForumThread forumThread) throws UnauthorizedException {
        threadWatched = true;

        return null;
    }

    public Watch createWatch(User user, Forum forum) throws UnauthorizedException {
        forumWatched = true;

        return null;
    }

    public Watch createWatch(User user, ForumCategory forumCategory) throws UnauthorizedException {
        categoryWatched = true;

        return null;
    }

    public int getWatchCount(User arg0, Forum arg1) throws UnauthorizedException {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getWatchCount(User arg0, ForumCategory arg1) throws UnauthorizedException {
        return 0;
    }

    public int getTotalWatchCount(User arg0, int arg1) throws UnauthorizedException {
        return 0;
    }

    public Iterator getAllWatches(User arg0, int arg1) throws UnauthorizedException {
        return null;
    }

    public Iterator getWatches(User arg0, Forum arg1) throws UnauthorizedException {
        return null;
    }

    public Iterator getWatches(User arg0, ForumCategory arg1) throws UnauthorizedException {
        return null;
    }

    public Watch getWatch(User arg0, User arg1) throws UnauthorizedException {
        return null;
    }

    public Watch getWatch(User arg0, ForumThread arg1) throws UnauthorizedException {
        return null;
    }

    public Watch getWatch(User arg0, Forum arg1) throws UnauthorizedException {
        return null;
    }

    public Watch getWatch(User arg0, ForumCategory arg1) throws UnauthorizedException {
        return null;
    }

    public boolean isWatched(User user, ForumCategory forumCategory) throws UnauthorizedException {
        if (watched) {
            return true;
        }

        return categoryWatched;
    }

    public boolean isWatched(User user, Forum forum) throws UnauthorizedException {
        if (watched) {
            return true;
        }

        return forumWatched;
    }

    public boolean isWatched(User user, ForumThread forumThread) throws UnauthorizedException {
        if (watched) {
            return true;
        }

        return threadWatched;
    }

    public static boolean isForumWatched() {
        return forumWatched;
    }

    public static void clearForumWatched() {
        forumWatched = false;
    }

    public static boolean isForumThreadWatched() {
        return threadWatched;
    }

    public static void clearForumThreadWatched() {
        threadWatched = false;
    }

    public static boolean isForumCategoryWatched() {
        return categoryWatched;
    }

    public static void clearForumCategoryWatched() {
        categoryWatched = false;
    }

    public boolean isWatched(User arg0, User arg1) throws UnauthorizedException {
        // TODO Auto-generated method stub
        return false;
    }

    public void deleteWatch(Watch arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void deleteWatches(User arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public static void setWatched(boolean val) {
        watched = val;
    }
}
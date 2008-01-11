package com.jivesoftware.forum.mock;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.FilterManager;
import com.jivesoftware.base.Permissions;
import com.jivesoftware.base.PermissionsManager;
import com.jivesoftware.base.UnauthorizedException;

import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumCategory;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.InterceptorManager;
import com.jivesoftware.forum.ResultFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class MockForumCategory implements ForumCategory {
    private static boolean exception = false;
    private long id;

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        return null;
    }

    public void setName(String arg0) throws UnauthorizedException {
    }

    public String getDescription() {
        return null;
    }

    public void setDescription(String arg0) throws UnauthorizedException {
    }

    public Date getCreationDate() {
        return null;
    }

    public void setCreationDate(Date arg0) throws UnauthorizedException {
    }

    public Date getModificationDate() {
        return null;
    }

    public void setModificationDate(Date arg0) throws UnauthorizedException {
    }

    public String getProperty(String arg0) {
        return null;
    }

    public Collection getProperties(String arg0) {
        return null;
    }

    public void setProperty(String arg0, String arg1)
        throws UnauthorizedException {
    }

    public void deleteProperty(String arg0) throws UnauthorizedException {
    }

    public Iterator getPropertyNames() {
        List<String> properties = new ArrayList<String>();
        properties.add("sss");

        return properties.iterator();
    }

    public int getForumCount() {
        return 0;
    }

    public int getForumCount(ResultFilter arg0) {
        return 0;
    }

    public int getRecursiveForumCount() {
        return 0;
    }

    public int getRecursiveForumCount(ResultFilter arg0) {
        return 0;
    }

    public Iterator getForums() {
        if (id == 1) {
            List<Forum> list = new ArrayList<Forum>();

            MockForum forum = new MockForum();
            forum.setID(11);
            list.add(forum);

            return list.iterator();
        } else {
            List<ForumCategory> list = new ArrayList<ForumCategory>();

            return list.iterator();
        }
    }

    public Iterator getForums(ResultFilter arg0) {
        return null;
    }

    public Iterator getRecursiveForums() {
        return null;
    }

    public Iterator getRecursiveForums(ResultFilter arg0) {
        return null;
    }

    public void setForumIndex(Forum arg0, int arg1)
        throws UnauthorizedException {
    }

    public void moveForum(Forum arg0, ForumCategory arg1)
        throws UnauthorizedException {
    }

    public Iterator getPopularThreads() {
        return null;
    }

    public Iterator getThreads() {
        return null;
    }

    public Iterator getThreads(ResultFilter arg0) {
        return null;
    }

    public int getThreadCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getThreadCount(ResultFilter arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getMessageCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getMessageCount(ResultFilter arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Iterator getMessages() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getMessages(ResultFilter arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumMessage getLatestMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumCategory getParentCategory() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getCategoryCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public Iterator getCategories() {
        if (id == 1) {
            List<ForumCategory> list = new ArrayList<ForumCategory>();

            MockForumCategory category = new MockForumCategory();
            category.setID(11);
            list.add(category);

            return list.iterator();
        } else {
            List<ForumCategory> list = new ArrayList<ForumCategory>();

            return list.iterator();
        }
    }

    public Iterator getCategories(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getRecursiveCategoryCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public Iterator getRecursiveCategories() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getCategoryDepth() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setCategoryIndex(ForumCategory arg0, int arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void moveCategory(ForumCategory arg0, ForumCategory arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public ForumCategory createCategory(String name, String desc)
        throws UnauthorizedException {
        if (exception) {
            throw new UnauthorizedException("Simulated Exception.");
        }

        MockForumCategory newCategory = new MockForumCategory();
        newCategory.setID(1);
        newCategory.setName(name);
        newCategory.setDescription(desc);

        return newCategory;
    }

    public void deleteCategory(ForumCategory arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void deleteForum(Forum arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public PermissionsManager getPermissionsManager()
        throws UnauthorizedException {
        return new MockPermissionsManager();
    }

    public FilterManager getFilterManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public InterceptorManager getInterceptorManager()
        throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public Permissions getPermissions(AuthToken arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isAuthorized(long arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public static void setException(boolean val) {
        exception = val;
    }
}

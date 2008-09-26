package com.topcoder.forum.service.accuracytests.mock;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.FilterManager;
import com.jivesoftware.base.Permissions;
import com.jivesoftware.base.PermissionsManager;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;

import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumCategory;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.ForumMessageIterator;
import com.jivesoftware.forum.ForumThread;
import com.jivesoftware.forum.ForumThreadIterator;
import com.jivesoftware.forum.ForumThreadNotFoundException;
import com.jivesoftware.forum.InterceptorManager;
import com.jivesoftware.forum.MessageRejectedException;
import com.jivesoftware.forum.NameAlreadyExistsException;
import com.jivesoftware.forum.Query;
import com.jivesoftware.forum.ResultFilter;
import com.jivesoftware.forum.gateway.GatewayManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Mock class.
 *
 * @author kaqi072821
 * @version 1.0
 */
public class AccuracyForum implements Forum {
    private long id;

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setName(String arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public String getNNTPName() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setNNTPName(String arg0) throws UnauthorizedException, NameAlreadyExistsException {
        // TODO Auto-generated method stub
    }

    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setDescription(String arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public Date getCreationDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setCreationDate(Date arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public Date getModificationDate() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setModificationDate(Date arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public int getModerationDefaultThreadValue() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setModerationDefaultThreadValue(int arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public int getModerationDefaultMessageValue() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setModerationDefaultMessageValue(int arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public int getMinForumIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getMaxForumIndex() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getProperty(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection getProperties(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setProperty(String arg0, String arg1) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void deleteProperty(String arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public Iterator getPropertyNames() {
        List<String> properties = new ArrayList<String>();

        properties.add("sss");

        return properties.iterator();
    }

    public ForumCategory getForumCategory() {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumThread getThread(long arg0) throws ForumThreadNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumThread createThread(ForumMessage message) throws UnauthorizedException {
        AccuracyForumThread thread = new AccuracyForumThread();
        thread.setRootForumMessage((AccuracyForumMessage) message);
        thread.setForum(this);

        return thread;
    }

    public ForumMessage createMessage() {
        return new AccuracyForumMessage();
    }

    public ForumMessage createMessage(User arg0) throws UnauthorizedException {
        return new AccuracyForumMessage();
    }

    public void addThread(ForumThread arg0) throws MessageRejectedException, UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void deleteThread(ForumThread arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void moveThread(ForumThread arg0, Forum arg1) throws UnauthorizedException, IllegalArgumentException {
    }

    public ForumThreadIterator getThreads() {
        if (id == 11) {
            AccuracyForumThreadIterator it = new AccuracyForumThreadIterator();
            it.setSize(1);

            return it;
        } else {
            AccuracyForumThreadIterator it = new AccuracyForumThreadIterator();

            return it;
        }
    }

    public ForumThreadIterator getThreads(ResultFilter arg0) {
        return null;
    }

    public Iterator getPopularThreads() {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumMessageIterator getMessages() {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumMessageIterator getMessages(ResultFilter arg0) {
        // TODO Auto-generated method stub
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

    public ForumMessage getLatestMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    public Query createQuery() {
        // TODO Auto-generated method stub
        return null;
    }

    public FilterManager getFilterManager() {
        // TODO Auto-generated method stub
        return null;
    }

    public InterceptorManager getInterceptorManager() throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public GatewayManager getGatewayManager() throws UnauthorizedException {
        // TODO Auto-generated method stub
        return null;
    }

    public PermissionsManager getPermissionsManager() throws UnauthorizedException {
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
}
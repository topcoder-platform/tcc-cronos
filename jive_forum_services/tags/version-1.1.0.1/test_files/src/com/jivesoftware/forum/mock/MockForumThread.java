package com.jivesoftware.forum.mock;

import com.jivesoftware.base.AuthToken;
import com.jivesoftware.base.UnauthorizedException;

import com.jivesoftware.forum.Forum;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.ForumMessageNotFoundException;
import com.jivesoftware.forum.ForumThread;
import com.jivesoftware.forum.MessageRejectedException;
import com.jivesoftware.forum.ResultFilter;
import com.jivesoftware.forum.TreeWalker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class MockForumThread implements ForumThread {
    private long id;
    private MockForumMessage rootMessage;
    private MockForum forum;

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public void setRootForumMessage(MockForumMessage message) {
        this.rootMessage = message;
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
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

    public int getModerationValue() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setModerationValue(int arg0, AuthToken arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public String getProperty(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection getProperties(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setProperty(String arg0, String arg1)
        throws UnauthorizedException {
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

    public Forum getForum() {
        return forum;
    }

    public void setForum(MockForum forum) {
        this.forum = forum;
    }

    public ForumMessage getMessage(long arg0)
        throws ForumMessageNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public ForumMessage getRootMessage() {
        return this.rootMessage;
    }

    public ForumMessage getLatestMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getMessageCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getMessageCount(ResultFilter arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void addMessage(ForumMessage arg0, ForumMessage arg1)
        throws MessageRejectedException, UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void deleteMessage(ForumMessage arg0) throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public void deleteMessage(ForumMessage arg0, boolean arg1)
        throws UnauthorizedException {
        // TODO Auto-generated method stub
    }

    public TreeWalker getTreeWalker() {
        // TODO Auto-generated method stub
        return null;
    }

    public Iterator getMessages() {
        List<ForumMessage> list = new ArrayList<ForumMessage>();

        list.add(rootMessage);
        list.add(new MockForumMessage());

        return list.iterator();
    }

    public Iterator getMessages(ResultFilter arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isAuthorized(long arg0) {
        // TODO Auto-generated method stub
        return false;
    }
}

package com.jivesoftware.forum.mock;

import com.jivesoftware.forum.ForumThread;
import com.jivesoftware.forum.ForumThreadIterator;


public class MockForumThreadIterator extends ForumThreadIterator {
    private int size = 0;
    private int cursor = 0;

    public void setSize(int size) {
        this.size = size;
    }

    public boolean hasNext() {
        return (cursor < size);
    }

    public Object next() {
        cursor++;

        MockForumThread thread = new MockForumThread();
        thread.setID(10 + cursor);
        thread.setRootForumMessage(new MockForumMessage());

        return thread;
    }

    public boolean hasPrevious() {
        return false;
    }

    public Object previous() {
        return null;
    }

    public void setIndex(ForumThread arg0) {
    }
}

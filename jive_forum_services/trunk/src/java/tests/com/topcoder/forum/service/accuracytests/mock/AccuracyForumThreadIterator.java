package com.topcoder.forum.service.accuracytests.mock;

import com.jivesoftware.forum.ForumThread;
import com.jivesoftware.forum.ForumThreadIterator;

/**
 * Mock class.
 *
 * @author kaqi072821
 * @version 1.0
 */
public class AccuracyForumThreadIterator extends ForumThreadIterator {
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

        AccuracyForumThread thread = new AccuracyForumThread();
        thread.setID(10 + cursor);
        thread.setRootForumMessage(new AccuracyForumMessage());

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
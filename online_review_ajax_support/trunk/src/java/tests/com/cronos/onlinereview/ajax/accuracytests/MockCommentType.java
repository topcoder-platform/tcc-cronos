/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.review.data.CommentType;


/**
 * Mock class.
 * 
 * @author assistant
 * @version 1.0
 */
public class MockCommentType extends CommentType {

    private int id = -1;
    public MockCommentType() {
        
    }
    public MockCommentType(int id) {
        this.id = id;
    }
    public long getId() {
        if (id == 1) {
            return 666660;
        } else if (id == 2) {
            return 666660;
        }
        return 999990;
    }
    public void setName(String name) {
    }
    public String getName() {
        if (id == 1) {
            return "Appeal Response";
        }
        return "Appeal";
    }
}

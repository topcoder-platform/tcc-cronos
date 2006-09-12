/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;


/**
 * Mock class.
 * 
 * @author assistant
 * @version 1.0
 */
public class MockComment extends Comment {

    public CommentType getCommentType() {
        return new MockCommentType(2);
    }
}

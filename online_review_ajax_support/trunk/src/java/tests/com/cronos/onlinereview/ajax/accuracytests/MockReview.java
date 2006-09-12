/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 */
package com.cronos.onlinereview.ajax.accuracytests;

import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;


/**
 * Mock class.
 * 
 * @author assistant
 * @version 1.0
 */
public class MockReview extends Review {
    public long getSubmission() {
        return 12345;
    }
    
    public long getAuthor() {
        return 777771;
    }
    public Item[] getAllItems() {
        return new Item[]{new MockItem()};
    }
}

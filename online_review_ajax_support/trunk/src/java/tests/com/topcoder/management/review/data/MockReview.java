/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

/**
 * A mock subclass for <code>Review</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockReview extends Review {

    /**
     * The submission.
     */
    private long submission;

    /**
     * The id.
     */
    private long id;

    /**
     * The author.
     */
    private long author;

    /**
     * Get the author.
     * @return the author.
     */
    public long getAuthor() {
        return author;
    }

    /**
     * Set the author.
     * @param author the author
     */
    public void setAuthor(long author) {
        this.author = author;
    }

    /**
     * Get id.
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Set id.
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get submission.
     * @return the submission
     */
    public long getSubmission() {
        return submission;
    }

    /**
     * Set submission.
     * @param submission the submission
     */
    public void setSubmission(long submission) {
        this.submission = submission;
    }

    /**
     * Get all items.
     * @return all items
     */
    public Item[] getAllItems() {
        Item item = new MockItem();
        item.setId(1);

        Item item2 = new MockItem();
        item2.setId(2);

        return new Item[] {item, item2};
    }
}

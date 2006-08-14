/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * The Review class is the class at the root of the review modeling hierarchy.
 * It represents an entire review, which is composed primarily of a list of
 * items. Comments can also be associated with a review, and each review also
 * possesses a few simple data fields. The review class is simply a container
 * for these basic data fields (along with the comment and item lists). All data
 * fields are mutable, and each data field has a 3 method get/set/reset
 * combination for that data field. Each of the comment and item lists is
 * manipulated through 8 methods (2 adds, 2 removes, a clear and 3 getters).
 * </p>
 * <p>
 * Thread Safety: This class is highly mutable and is not thread safe. All
 * fields can be changed.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class Review implements Serializable {
    /**
     * id: The unique identifier of the Review. This field is initialized in the
     * constructor, is mutable, and can be > 0 or equal to -1. By default, this
     * field is initialized to -1. This value indicates that the id of the
     * Review has not yet been set through the constructor or a call to setId()
     * (or has been set back to this value by a call to resetId()). Values > 0
     * indicate that the client using the component has assigned an id (in the
     * constructor or through the setId() method). It is expected that all
     * Review instances will have unique ids. This is up to the client using the
     * component to enforce - this component will not enforce this condition.
     * The id field can be set using the setId/resetId methods and retrieved
     * using the getId method.
     */
    private long id;

    /**
     * author: The identifier of the author of the Review. This field is
     * initialized in the constructor, is mutable, and can be > 0 or equal to
     * -1. By default, this field is initialized to -1. This value indicates
     * that the author of the Review has not yet been set through a call to
     * setAuthor() (or has been set back to this value by a call to
     * resetAuthor()). Values > 0 indicate that the client using the component
     * has assigned an author (in the constructor or through the setAuthor()
     * method). The author field can be set using the setAuthor/resetAuthor
     * methods and retrieved using the getAuthor method.
     */
    private long author;

    /**
     * submission: The identifier of the submission that the Review applies to.
     * This field is initialized in the constructor, is mutable, and can be > 0
     * or equal to -1. By default, this field is initialized to -1. This value
     * indicates that the author of the Review has not yet been set through a
     * call to setSubmission() (or has been set back to this value by a call to
     * resetSubmission()). Values > 0 indicate that the client using the
     * component has assigned a submission (in the constructor or through the
     * setSubmission() method). The submission field can be set using the
     * setSubmission/resetSubmission methods and retrieved using the
     * getSubmission method.
     */
    private long submission;

    /**
     * scorecard: The identifier of the scorecard the Review references. This
     * field is initialized in the constructor, is mutable, and can be > 0 or
     * equal to -1. By default, this field is initialized to -1. This value
     * indicates that the scorecard the Review references has not yet been set
     * through a call to setScorecard() (or has been set back to this value by a
     * call to resetScorecard()). Values > 0 indicate that the client using the
     * component has assigned a scorecard (in the constructor or through the
     * setScorecard() method). The scorecard field can be set using the
     * setScorecard/resetScorecard methods and retrieved using the getScorecard
     * method.
     */
    private long scorecard;

    /**
     * committed: Tells whether the Review has been committed or not. It is up
     * to the application to determine what it means for a Review to be
     * "committed". This field is initialized to false and is mutable. False
     * indicates that the review is "uncommitted", true that the review has been
     * "committed". The committed field can be set using the
     * setCommitted/resetCommitted methods and retrieved using the isCommitted
     * method.
     */
    private boolean committed = false;

    /**
     * score: The score associated with the Review. This field is initialized to
     * null, is mutable, and can be null or wrap a value >= 0 (and not Nan or
     * POSITIVE_INFINITY). This value indicates that the score of the Review has
     * not yet been set through a call to setScore(). A non-null value indicates
     * that the client using the component has assigned a score (through the
     * setScore() method). The score field can be set using the
     * setScore/resetScore methods and retrieved using the getScore method.
     */
    private Float score = null;

    /**
     * items: The list of Item instances that are associated with this Review.
     * This field is set in the constructor and is immutable, although its
     * contents may be changed with the add/removeItem and add/remove/clearItems
     * methods. All items in the list are required to be non-null Item
     * instances, and no duplicates (by reference equality) are allowed. This
     * would normally seem like the situation to use a Set, but the requirements
     * specify that the ordering of items is important, so ordering must be
     * preserved. Hence the use of a List storage structure. The contents of the
     * list are manipulated through the add/removeItem and add/remove/clearItems
     * methods. Items are retrieved through the getItem and getAllItems methods.
     */
    private final List items = new ArrayList();

    /**
     * comments: The list of Comment instances that are associated with this
     * Review. This field is set in the constructor and is immutable, although
     * its contents may be changed with the add/removeComment and
     * add/remove/clearComments methods. All items in the list are required to
     * be non-null Comment instances, and no duplicates (by reference equality)
     * are allowed. This would normally seem like the situation to use a Set,
     * but the requirements specify that the ordering of comments is important,
     * so ordering must be preserved. Hence the use of a List storage structure.
     * The contents of the list are manipulated through the add/removeComment
     * and add/remove/clearComments methods. Comments are retrieved through the
     * getComment and getAllComments methods.
     * @poseidon-object-id [Im1fe54331m10b9eddaca3mm7091]
     */
    private final List comments = new ArrayList();

    /**
     * creationUser: The name of the user that was responsible for creating the
     * Review. This field is initialized in the constructor, is mutable, and can
     * be null or non-null. A null value indicates that this field has not been
     * set in the constructor or through the setCreationUser method. When
     * non-null, no value is considered invalid for this field - the user may be
     * the empty string, all whitespace, etc. Although most applications will
     * probably not change the creation user once it is set, this class does
     * allow this field to be changed. This field is set through the
     * setCreationUser method and retrieved through the getCreationUser method.
     */
    private String creationUser;

    /**
     * creationTimestamp: The date/time that the Review was created. This field
     * is initialized in the constructor, is mutable, and can be null or
     * non-null. By default this field is initialized to null but some
     * constructors set it to the current time. A null value indicates that this
     * field has not been set in the constructor or through the
     * setCreationTimestamp method. No value is considered invalid for this
     * field. Although most applications will probably not change the creation
     * timestamp once it is set, this class does allow this field to be changed.
     * This field is set through the setCreationTimestamp method and retrieved
     * through the getCreationTimestamp method.
     */
    private Date creationTimestamp;

    /**
     * modificationUser: The name of the user that was responsible for the last
     * modification to the Review. This field is initially null, is mutable, and
     * can be null or non-null. A null value indicates that this field has not
     * been set through the setModificationUser method. When non-null, no value
     * is considered invalid for this field - the user may be the empty string,
     * all whitespace, etc. This field is set through the setModificationUser
     * method and retrieved through the getModificationUser method. This field
     * is not automatically updated when changes are made to this class. The
     * ReviewEditor can be used to achieve this.
     */
    private String modificationUser = null;

    /**
     * modificationTimestamp: The date/time that the Review was last modified.
     * This field is initialized to null, is mutable, and can be null or
     * non-null. A null value indicates that this field has not been set in the
     * constructor or through the setModificationTimestamp method. No value is
     * considered invalid for this field. This field is set through the
     * setModificationTimestamp method and retrieved through the
     * getModificationTimestamp method. This field is not automatically updated
     * when changes are made to this class. The ReviewEditor can be used to
     * achieve this.
     */
    private Date modificationTimestamp = null;

    /**
     * <p>
     * Creates a new Review in which all fields are set to the "unassigned"
     * values.
     * </p>
     */
    public Review() {
        id = -1;
        author = -1;
        submission = -1;
        scorecard = -1;
        creationUser = null;
        creationTimestamp = null;
    }

    /**
     * <p>
     * Creates a new Review, setting id to the given value and the other fields
     * to the "unassigned" values.
     * </p>
     * @param id
     *            The id of the Review, must be > 0
     * @throws IllegalArgumentException
     *             If id <= 0
     */
    public Review(long id) {
        Helper.assertLongPositive(id, "id");

        this.id = id;
        author = -1;
        submission = -1;
        scorecard = -1;
        creationUser = null;
        creationTimestamp = null;
    }

    /**
     * <p>
     * Creates a new Review, setting id and creationUser to the given values,
     * creationTimestamp to the current date/time and the other fields to the
     * "unassigned" values.
     * </p>
     * @param id
     *            The id of the Review, must be > 0
     * @param creationUser
     *            The user that is creating the Review
     * @throws IllegalArgumentException
     *             If id <= 0
     * @throws IllegalArgumentException
     *             If creationUser is null
     */
    public Review(long id, String creationUser) {
        Helper.assertLongPositive(id, "id");
        Helper.assertObjectNotNull(creationUser, "creationUser");

        this.id = id;
        author = -1;
        submission = -1;
        scorecard = -1;
        this.creationUser = creationUser;
        creationTimestamp = new Date();
    }

    /**
     * <p>
     * Creates a new Review, setting id, author, submission and scorecard fields
     * set to the given values and the other fields are to the "unassigned"
     * values.
     * </p>
     * @param id
     *            The id of the Review, must be > 0
     * @param author
     *            The identifier for the author of the Review
     * @param submission
     *            The identifier for the submission the Review refers to
     * @param scorecard
     *            The identifier for the scorecard the Review uses
     * @throws IllegalArgumentException
     *             If id, author, submission, or scorecard is <= 0
     */
    public Review(long id, long author, long submission, long scorecard) {
        Helper.assertLongPositive(id, "id");
        Helper.assertLongPositive(author, "author");
        Helper.assertLongPositive(submission, "submission");
        Helper.assertLongPositive(scorecard, "scorecard");

        this.id = id;
        this.author = author;
        this.submission = submission;
        this.scorecard = scorecard;
    }

    /**
     * <p>
     * Creates a new Review, setting id, creationUser, author, submission and
     * scorecard the given values, creationTimeStamp to the current date/time
     * and the other fields to the "unassigned" values.
     * </p>
     * @param id
     *            The id of the Review, must be > 0
     * @param creationUser
     *            The user that is creating the Review
     * @param author
     *            The identifier for the author of the Review
     * @param submission
     *            The identifier for the submission the Review refers to
     * @param scorecard
     *            The identifier for the scorecard the Review uses
     * @throws IllegalArgumentException
     *             If id, author, submission, or scorecard is <= 0
     * @throws IllegalArgumentException
     *             If creationUser is null
     */
    public Review(long id, String creationUser, long author, long submission, long scorecard) {
        Helper.assertLongPositive(id, "id");
        Helper.assertObjectNotNull(creationUser, "creationUser");
        Helper.assertLongPositive(author, "author");
        Helper.assertLongPositive(submission, "submission");
        Helper.assertLongPositive(scorecard, "scorecard");

        this.id = id;
        this.creationUser = creationUser;
        this.creationTimestamp = new Date();
        this.author = author;
        this.submission = submission;
        this.scorecard = scorecard;
    }

    /**
     * <p>
     * Sets the unique identifier of the Review. The id value must be > 0.
     * </p>
     * @param id
     *            The identifier to assign to the review
     * @throws IllegalArgumentException
     *             if id <= 0
     */
    public void setId(long id) {
        Helper.assertLongPositive(id, "id");

        this.id = id;
    }

    /**
     * <p>
     * Gets the id of the Review.
     * </p>
     * @return The id of the Review, always > 0 or -1
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the id of the Review back to its "undefined" value, which is -1.
     * </p>
     */
    public void resetId() {
        id = -1;
    }

    /**
     * <p>
     * Sets the author of this Review. The author valus must be > 0.
     * </p>
     * @param author
     *            The identifier of the author
     * @throws IllegalArgumentException
     *             If author is <= 0
     */
    public void setAuthor(long author) {
        Helper.assertLongPositive(author, "author");

        this.author = author;
    }

    /**
     * <p>
     * Retrieves the author for this Review.
     * </p>
     * @return The identifier of the author of the Review
     */
    public long getAuthor() {
        return author;
    }

    /**
     * <p>
     * Resets the author identifier back to its "undefined" value, which is -1.
     * </p>
     */
    public void resetAuthor() {
        author = -1;
    }

    /**
     * <p>
     * Sets the submission that this Review applies to. The submission value
     * must be > 0.
     * </p>
     * @param submission
     *            The identifier of the submission
     * @throws IllegalArgumentException
     *             If submission is <= 0
     */
    public void setSubmission(long submission) {
        Helper.assertLongPositive(submission, "submission");

        this.submission = submission;
    }

    /**
     * <p>
     * Retrieves the submission that this Review applies to.
     * </p>
     * @return The identifier of the submission the Review applies to.
     */
    public long getSubmission() {
        return submission;
    }

    /**
     * <p>
     * Resets the submission identifier that the Review applies to back to its
     * "undefined" value, which is -1.
     * </p>
     */
    public void resetSubmission() {
        submission = -1;
    }

    /**
     * <p>
     * Sets the scorecard that this Review applies to. The scorecard value must
     * be > 0.
     * </p>
     * @param scorecard
     *            The identifier of the scorecard
     * @throws IllegalArgumentException
     *             If scorecard is <= 0
     */
    public void setScorecard(long scorecard) {
        Helper.assertLongPositive(scorecard, "scorecard");

        this.scorecard = scorecard;
    }

    /**
     * <p>
     * Retrieves the scorecard identifier that the Review relates to.
     * </p>
     * @return The identifier of the scorecard that the Review relates to.
     */
    public long getScorecard() {
        return scorecard;
    }

    /**
     * <p>
     * Resets the scorecard that the Review uses back to its "undefined" value,
     * which is -1.
     * </p>
     */
    public void resetScorecard() {
        scorecard = -1;
    }

    /**
     * <p>
     * Sets whether the Review has or has not been committed. It is up to the
     * application to define what "committed" means. This method can be used
     * both to "commit" and "uncommit" the Review.
     * </p>
     * @param committed
     *            Whether the Review is to be considered committed or not
     */
    public void setCommitted(boolean committed) {
        this.committed = committed;
    }

    /**
     * <p>
     * Gets whether the Review has or has not been committed. It is up to the
     * application to define what "committed" means.
     * </p>
     * @return True if the Review is committed (i.e. isCommitted(true) has
     *         previously been called)
     */
    public boolean isCommitted() {
        return committed;
    }

    /**
     * <p>
     * Resets the review back to its non-committed state.
     * </p>
     */
    public void resetCommitted() {
        setCommitted(false);
    }

    /**
     * <p>
     * Sets the score associated with this Review. The score is required to be a
     * wrapper around a float value that is >= 0 (and not NaN or
     * POSITIVE_INFINITY) or null. A null value indicates that no score is
     * associated with the review.
     * @param score
     *            The score to associate with the Review
     * @throws IllegalArgumentException
     *             If score wraps a value < 0, or which is NaN,
     *             POSITIVE_INFINITY, or NEGATIVE_INFINITY
     */
    public void setScore(Float score) {
        if (score != null) {
            if (score.floatValue() < 0.0 || score.isInfinite() || score.isNaN()) {
                throw new IllegalArgumentException("score [" + score
                    + "] is < 0 or Nan or Infintity.");
            }
        }

        this.score = score;
    }

    /**
     * <p>
     * Gets the score associated with the Review. This method may return null,
     * which indicates that there is no score associated with the Review. When
     * non-null, the value will be >= 0 and not NaN or NEGATIVE_INFINITY.
     * </p>
     * @return The score associated with the review, may be null.
     */
    public Float getScore() {
        return score;
    }

    /**
     * <p>
     * Resets the score associated with the review.
     * </p>
     */
    public void resetScore() {
        setScore(null);
    }

    /**
     * <p>
     * Adds an item to the review.
     * </p>
     * @param item
     *            The item to add
     * @throws IllegalArgumentException
     *             If item is null
     */
    public void addItem(Item item) {
        Helper.assertObjectNotNull(item, "item");

        if (!items.contains(item)) {
            items.add(item);
        }
    }

    /**
     * <p>
     * Adds all items in the array to the Review.
     * </p>
     * @param items
     *            The array of Items to add to the Review. (May be a 0-length
     *            array.)
     * @throws IllegalArgumentException
     *             If items is null or has null entries
     */
    public void addItems(Item[] items) {
        Helper.assertArrayNotNullNorHasNull(items, "items");

        for (int idx = 0; idx < items.length; ++idx) {
            addItem(items[idx]);
        }
    }

    /**
     * <p>
     * Removes the given item associated with the Review.
     * </p>
     * @param item
     *            The Item to remove from this Review
     * @throws IllegalArgumentException
     *             If item is null
     */
    public void removeItem(Item item) {
        Helper.assertObjectNotNull(item, "item");

        items.remove(item);
    }

    /**
     * <p>
     * Removes all items in the array from the Review.
     * </p>
     * @param items
     *            The array of Items to remove from the Review. (May be a
     *            0-length array.)
     * @throws IllegalArgumentException
     *             If items is null or has null entries
     */
    public void removeItems(Item[] items) {
        Helper.assertArrayNotNullNorHasNull(items, "items");

        for (int idx = 0; idx < items.length; ++idx) {
            removeItem(items[idx]);
        }
    }

    /**
     * <p>
     * Clears all Items associated with the Review.
     * </p>
     */
    public void clearItems() {
        items.clear();
    }

    /**
     * <p>
     * Gets the itemIndex-th item in the items list.
     * </p>
     * @param itemIndex
     *            The index of the Item to retrieve
     * @return The Item in the itemIndex position
     * @throws IllegalArgumentException
     *             If itemIndex is < 0 or >= # of Items in the items list
     */
    public Item getItem(int itemIndex) {
        if (itemIndex < 0 || itemIndex >= items.size()) {
            throw new IllegalArgumentException("itemIndex [" + itemIndex + "] is out of range.");
        }

        return (Item) items.get(itemIndex);
    }

    /**
     * <p>
     * Gets all the items associated with the Review.
     * </p>
     * @return All the Items in the review. (May be a 0-length array.)
     */
    public Item[] getAllItems() {
        return (Item[]) items.toArray(new Item[] {});
    }

    /**
     * <p>
     * Adds a comment to the review.
     * </p>
     * @param comment
     *            The comment to add
     * @throws IllegalArgumentException
     *             If comment is null
     */
    public void addComment(Comment comment) {
        Helper.assertObjectNotNull(comment, "comment");

        if (!comments.contains(comment)) {
            comments.add(comment);
        }
    }

    /**
     * <p>
     * Adds all Comments in the array to the Review.
     * </p>
     * @param comments
     *            The array of Comments to add to the Review. (May be a 0-length
     *            array.)
     * @throws IllegalArgumentException
     *             If comments is null or has null entries
     */
    public void addComments(Comment[] comments) {
        Helper.assertArrayNotNullNorHasNull(comments, "comments");

        for (int idx = 0; idx < comments.length; ++idx) {
            addComment(comments[idx]);
        }
    }

    /**
     * <p>
     * Removes the given comment associated with the Review.
     * </p>
     * @param comment
     *            The Comment to remove from this Review
     * @throws IllegalArgumentException
     *             If comment is null
     */
    public void removeComment(Comment comment) {
        Helper.assertObjectNotNull(comment, "comment");

        comments.remove(comment);
    }

    /**
     * <p>
     * Removes all comments in the array from the Review.
     * </p>
     * @param comments
     *            The array of Comments to remove from the Review. (May be a
     *            0-length array.)
     * @throws IllegalArgumentException
     *             If comments is null or has null entries
     */
    public void removeComments(Comment[] comments) {
        Helper.assertArrayNotNullNorHasNull(comments, "comments");

        for (int idx = 0; idx < comments.length; ++idx) {
            removeComment(comments[idx]);
        }
    }

    /**
     * <p>
     * Clears all Comments associated with the Review.
     * </p>
     */
    public void clearComments() {
        comments.clear();
    }

    /**
     * <p>
     * Gets the commentIndex-th item in the comments list.
     * </p>
     * @param commentIndex
     *            The index of the Comment to retrieve
     * @return The Comment in the commentIndex position
     * @throws IllegalArgumentException
     *             If commentIndex is < 0 or >= # of Comments in the comments
     *             list
     */
    public Comment getComment(int commentIndex) {
        if (commentIndex < 0 || commentIndex >= comments.size()) {
            throw new IllegalArgumentException("commentIndex [" + commentIndex
                + "] is out of range.");
        }

        return (Comment) comments.get(commentIndex);
    }

    /**
     * <p>
     * Gets all the comments associated with the Review.
     * </p>
     * @return All the Comments associated with the Review. (May be a 0-length
     *         array.)
     */
    public Comment[] getAllComments() {
        return (Comment[]) comments.toArray(new Comment[] {});
    }

    /**
     * <p>
     * Sets the user that is responsible for the creation of the Review. The
     * value can be null or non-null, and no value is considered invalid.
     * </p>
     * @param creationUser
     *            The user responsible for Review creation, can be null
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Gets the user responsible for creating the Review. The return may be null
     * which indicates that the creation user has not been set.
     * </p>
     * @return The user that created the Review
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * <p>
     * Sets the date/time at which the Review was created. This value may be
     * null or non-null and no value is considered invalid.
     * </p>
     * @param creationTimestamp
     *            The date/time the review was created.
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * <p>
     * Gets the date/time the Review was created. The return may be null,
     * indicating that the creation date/time has not been set.
     * </p>
     * @return The date/time the Review was created.
     */
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * <p>
     * Sets the user that is responsible for the last modification to the the
     * Review. The value can be null or non-null, and no value is considered
     * invalid.
     * </p>
     * @param modificationUser
     *            The user responsible for the last Review modification, can be
     *            null
     */
    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Gets the user responsible for last modifying the Review. The return may
     * be null which indicates that the modification user has not been set.
     * </p>
     * @return The user that last modified the Review
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * <p>
     * Sets the date/time at which the Review was last modified. This value may
     * be null or non-null and no value is considered invalid.
     * </p>
     * @param modificationTimestamp
     *            The date/time the review was last modified
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * <p>
     * Gets the date/time the Review was last modified. The return may be null,
     * indicating that the modification date/time has not been set.
     * </p>
     * @return The date/time the Review was last modified
     */
    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }

    /**
     * <p>
     * Gets the number of comments for this review.
     * </p>
     * @return The number of comments
     */
    public int getNumberOfComments() {
        return comments.size();
    }

    /**
     * <p>
     * Gets the number of items for this review.
     * </p>
     * @return The number of items
     */
    public int getNumberOfItems() {
        return items.size();
    }

    /**
     * <p>
     * Removes the first item with the given id from the items list. If no item
     * has the given id, then no item will be removed.
     * </p>
     * @param id
     *            The id of the item to remove
     * @throws IllegalArgumentException
     *             If id is <= 0 and not -1
     */
    public void removeItem(long id) {
        if (id <= 0 && id != -1) {
            throw new IllegalArgumentException("id should be -1 or positive.");
        }

        for (int idx = 0; idx < items.size(); ++idx) {
            if (((Item) items.get(idx)).getId() == id) {
                items.remove(idx);
                return;
            }
        }
    }

    /**
     * <p>
     * Removes the first comment with the given id from the items list. If no
     * comment has the given id, then no comment will be removed.
     * </p>
     * @param id
     *            The id of the comment to remove
     * @throws IllegalArgumentException
     *             If id is <= 0 and not -1
     */
    public void removeComment(long id) {
        if (id <= 0 && id != -1) {
            throw new IllegalArgumentException("id should be -1 or positive.");
        }

        for (int idx = 0; idx < comments.size(); ++idx) {
            if (((Comment) comments.get(idx)).getId() == id) {
                comments.remove(idx);
                return;
            }
        }
    }
}

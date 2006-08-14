/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;

/**
 * <p>
 * The Comment class can be considered the third level in the review model
 * hierarchy. Comments can be associated with both reviews/items. There is no
 * limit on the number of comments that can be associated with a review/item.
 * Since a review/item is not required to have any associated comments, the
 * Comment class can be seen as (slightly) auxiliary to the main Review/Item
 * part of the model. As in the Review and Item classes, each data field has a 3
 * method get/set/reset combination for manipulating that data field.
 * </p>
 * <p>
 * Thread Safety: This class is highly mutable and is not thread safe . All
 * fields can be changed.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class Comment implements Serializable {
    /**
     * id: The identifier of the Comment. This field is initialized in the
     * constructor, is mutable, and can be > 0 or equal to -1. By default, this
     * field is initialized to -1. This value indicates that the id of the
     * Comment has not yet been set through the constructor or a call to setId()
     * (or has been set back to this value by a call to resetId()). Values > 0
     * indicate that the client using the component has assigned an id (in the
     * constructor or through the setId() method). It is expected that all
     * Comment instances will have unique ids. This is up to the client using
     * the component to enforce - this component will not enforce this
     * condition. The id field can be set using the setId/resetId methods and
     * retrieved using the getId method.
     */
    private long id;

    /**
     * author: The identifier of the author of the Comment. This field is
     * initialized in the constructor, is mutable, and can be > 0 or equal to
     * -1. By default, this field is initialized to -1. This value indicates
     * that the author of the Comment has not yet been set through a call to
     * setAuthor() (or has been set back to this value by a call to
     * resetAuthor()). Values > 0 indicate that the client using the component
     * has assigned an author (in the constructor or through the setAuthor()
     * method). The author field can be set using the setAuthor/resetAuthor
     * methods and retrieved using the getAuthor method.
     */
    private long author;

    /**
     * commentType: The general type that this Comment belong to. This field is
     * set in the constructor, is mutable, and can be null. By default, this
     * field is set to null. When this field is null it indicates that no
     * comment type is associated with this Comment. This field can be set
     * through the set/resetCommentType methods and the value retrieved through
     * the getCommentType method.
     */
    private CommentType commentType;

    /**
     * extraInfo: Any extra information that is desired to be associated with
     * the Item. This field is intended to allow as generic a type as possible.
     * Originally Object was used, but Serializiable enables this class to
     * guarantee that serialization will succeed. This field is initialized to
     * null, is mutable, and can be null. The default when not specified in the
     * constructor is null. Null indicates that no extra information is
     * associated with the comment. A non-null value indicates that the extra
     * info has been set through the setExtraInfo method or the constructor. It
     * is up to the application to decide how to use this field. The
     * setExtraInfo and resetExtraInfo methods are used to change this field and
     * the getExtraInfo to retrieve its value.
     */
    private Serializable extraInfo = null;

    /**
     * comment: The actual text of the comment. This field is set in the
     * constructor, is mutable, and can be null. The default for this field is
     * null, which indicates that no text is associated with the comment. This
     * field can be set through the setComment/resetComment methods and
     * retrieved through the getComment method. No restrictions are applied to
     * this field - it may be the empty string, all whitespace, etc.
     */
    private String comment;

    /**
     * <p>
     * Creates a new Comment, setting all the fields to their "unassigned"
     * values.
     * </p>
     */
    public Comment() {
        id = -1;
        author = -1;
        commentType = null;
        comment = null;
    }

    /**
     * <p>
     * Creates a new Comment, setting id to the given value and the other fields
     * to their "unassigned" values.
     * </p>
     * @param id
     *            The id for the Comment
     * @throws IllegalArgumentException
     *             If id is <= 0
     */
    public Comment(long id) {
        Helper.assertLongPositive(id, "id");

        this.id = id;
        author = -1;
        commentType = null;
        comment = null;
    }

    /**
     * <p>
     * Creates a new Comment, setting id and author to the given values and the
     * other fields to their "unassigned" values.
     * </p>
     * @param id
     *            The id for the Comment
     * @param author
     *            The identifier of the author of the comment
     * @throws IllegalArgumentException
     *             If id or author is <= 0
     */
    public Comment(long id, long author) {
        Helper.assertLongPositive(id, "id");
        Helper.assertLongPositive(author, "author");

        this.id = id;
        this.author = author;
        commentType = null;
        comment = null;
    }

    /**
     * <p>
     * Creates a new Comment, setting id, author and comment to the given values
     * and the other fields to their "unassigned" values.
     * </p>
     * @param id
     *            The id for the Comment
     * @param author
     *            The identifier of the author of the comment
     * @param comment
     *            The text content of the comment
     * @throws IllegalArgumentException
     *             If id or author is <= 0
     * @throws IllegalArgumentException
     *             If comment is null
     */
    public Comment(long id, long author, String comment) {
        Helper.assertLongPositive(id, "id");
        Helper.assertLongPositive(author, "author");
        Helper.assertObjectNotNull(comment, "comment");

        this.id = id;
        this.author = author;
        this.comment = comment;
        commentType = null;
    }

    /**
     * <p>
     * Creates a new Comment, setting id, author and commentType to the given
     * values and the other constructor set fields to their "unassigned" values.
     * </p>
     * @param id
     *            The id for the Comment
     * @param author
     *            The identifier of the author of the comment
     * @param commentType
     *            The type of the comment
     * @throws IllegalArgumentException
     *             If id or author is <= 0
     * @throws IllegalArgumentException
     *             If commentType is null
     */
    public Comment(long id, long author, CommentType commentType) {
        Helper.assertLongPositive(id, "id");
        Helper.assertLongPositive(author, "author");
        Helper.assertObjectNotNull(commentType, "commentType");

        this.id = id;
        this.author = author;
        this.commentType = commentType;
        comment = null;
    }

    /**
     * <p>
     * Creates a new Comment, setting all the fields to the values given.
     * </p>
     * @param id
     *            The id for the Comment
     * @param author
     *            The identifier of the author of the comment
     * @param commentType
     *            The type of the comment
     * @param comment
     *            The text of the comment
     * @throws IllegalArgumentException
     *             If id or author is <= 0
     * @throws IllegalArgumentException
     *             If comment or commentType is null
     */
    public Comment(long id, long author, CommentType commentType, String comment) {
        Helper.assertLongPositive(id, "id");
        Helper.assertLongPositive(author, "author");
        Helper.assertObjectNotNull(commentType, "commentType");
        Helper.assertObjectNotNull(comment, "comment");

        this.id = id;
        this.author = author;
        this.commentType = commentType;
        this.comment = comment;
    }

    /**
     * <p>
     * Sets the unique identifier of the Comment. The id value must be > 0.
     * </p>
     * @param id
     *            The identifier to assign to the comment
     * @throws IllegalArgumentException
     *             if id <= 0
     */
    public void setId(long id) {
        Helper.assertLongPositive(id, "id");

        this.id = id;
    }

    /**
     * <p>
     * Retrieves the id for this Comment.
     * </p>
     * @return The id of the Comment
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
     * Sets the author of this Comment. The author value must be > 0.
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
     * Retrieves the author for this Comment.
     * </p>
     * @return The identifier of the author of the Comment
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
     * Sets the type of this comment. The argument may be null.
     * </p>
     * @param commentType
     *            The type of the comment
     */
    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    /**
     * <p>
     * Gets the type of the comment. This method may return null, indicating
     * that the comment is not associated with a type.
     * </p>
     * @return The type of the comment
     */
    public CommentType getCommentType() {
        return commentType;
    }

    /**
     * <p>
     * Resets the type of the comment to its unassigned value, which is null.
     * </p>
     */
    public void resetCommentType() {
        setCommentType(null);
    }

    /**
     * <p>
     * Sets the actual text content of the comment. The argument can be null or
     * non-null. Null indicates that there should be no text associated with the
     * comment. Any non-null value is allowed (can be the empty string, all
     * whitespace, etc).
     * </p>
     * @param comment
     *            The comment text of this comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * <p>
     * Gets the actual text of the comment. This method may return null,
     * indicating that no text is associated with the comment.
     * </p>
     * @return The actual text of the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * <p>
     * Resets the text of the comment to its "unassigned" value.
     * </p>
     */
    public void resetComment() {
        setComment(null);
    }

    /**
     * <p>
     * Allows a user to set any extra information needed for the comment. The
     * argument may be null to indicate that no extra information is associated
     * with the comment.
     * </p>
     * @param extraInfo
     *            The extra information to associate with the comment
     */
    public void setExtraInfo(Serializable extraInfo) {
        this.extraInfo = extraInfo;
    }

    /**
     * <p>
     * Retrieves the extra information associated with this comment. If no extra
     * info has been associated with this Comment, null will be returned.
     * </p>
     * @return The user specified extra info associated with this comment
     */
    public Serializable getExtraInfo() {
        return extraInfo;
    }

    /**
     * <p>
     * Resets the extra information associated with this comment to its
     * "unassigned" value.
     * </p>
     */
    public void resetExtraInfo() {
        setExtraInfo(null);
    }
}

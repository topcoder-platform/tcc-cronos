/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * The Item class is the second level in the review model hierarchy. A review
 * consists of responses to individual items, each of which is represented by an
 * instance of this class. There are two types of data fields for this class:
 * the simple data fields and the comments list. All data fields are mutable,
 * and each data field has a 3 method get/set/reset combination for that data
 * field. The comment list is manipulated through 8 methods (2 adds, 2 removes,
 * a clear, and 3 getters).
 * </p>
 * <p>
 * Thread Safety: This class is highly mutable and is not thread safe. All
 * fields can be changed.
 * </p>
 * @author aubergineanode, TCSDEVELOPER
 * @version 1.0
 */
public class Item implements Serializable {
    /**
     * id: The identifier of the Item. This field is initialized in the
     * constructor, is mutable, and can be > 0 or equal to -1. By default, this
     * field is initialized to -1. This value indicates that the id of the Item
     * has not yet been set through the constructor or a call to setId() (or has
     * been set back to this value by a call to resetId()). Values > 0 indicate
     * that the client using the component has assigned an id (in the
     * constructor or through the setId() method). It is expected that all Item
     * instances will have unique ids. This is up to the client using the
     * component to enforce - this component will not enforce this condition.
     * The id field can be set using the setId/resetId methods and retrieved
     * using the getId method.
     */
    private long id;

    /**
     * answer: The answer associated with the Item. This field is intended to
     * allow as generic an answer type as possible. Originally Object was used,
     * but Serializiable enables this class to guarantee that serialization will
     * succeed. This field is initialized in the constructor, is mutable, and
     * can be null. The default when not specified in the constructor is null.
     * Null indicates that no answer is associated with the item. A non-null
     * value indicates that the answer has been set through the setAnswer method
     * or the constructor. It is up to the application to decide how to use this
     * field. The set/resetAnswer methods are used to set this field, and the
     * getAnswer method to retrieve its value.
     */
    private Serializable answer;

    /**
     * question: The identifier of the question this Item is about. This field
     * is initialized in the constructor, is mutable, and can be > 0 or equal to
     * -1. By default, this field is initialized to -1. This value indicates
     * that the question has not yet been set through a call to setQuestion()
     * (or has been set back to this value by a call to resetQuestion()). Values
     * >0 indicate that the client using the component has assigned a question
     * (in the constructor or through the setQuestion() method). The question
     * field can be set using the setQuestion/resetQuestion methods and
     * retrieved using the getQuestion method.
     */
    private long question;

    /**
     * document: The identifier of an optional attachment associated with the
     * Item. This field is initialized to null, is mutable, and can be null or
     * wrap a value > 0. Null indicates that no attachment is associated with
     * the Item. A non-null value indicates that there is a document associated
     * with the item. This field simply stores the identifier for the document,
     * and it is up to the application to define how to retrieve an actual
     * document for this id. This field can be set using the setDocument and
     * resetDocument methods and retrieved through the getDocument method.
     */
    private Long document = null;

    /**
     * comments: The list of Comment instances that are associated with this
     * Item. This field is set in the constructor and is immutable, although its
     * contents may be changed with the add/removeComment and
     * add/remove/clearComments methods. All items in the list are required to
     * be non-null Comment instances, and no duplicates (by reference equality)
     * are allowed. This would normally seem like the situation to use a Set,
     * but the requirements specify that the ordering of comments is important,
     * so ordering must be preserved. Hence the use of a List storage structure.
     * The contents of the list are manipulated through the add/removeComment
     * and add/remove/clearComments methods. Comments are retrieved through the
     * getComment and getAllComments methods.
     */
    private final List comments = new ArrayList();

    /**
     * <p>
     * Creates a new Item in which all the fields are set to the "unassigned"
     * values.
     * </p>
     */
    public Item() {
        id = -1;
        answer = null;
        question = -1;
    }

    /**
     * <p>
     * Creates a new Item with the specified id, while other values are
     * "undefined".
     * </p>
     * <p>
     * Other constructor assigned fields are set to the "unassigned" values.
     * </p>
     * @param id
     *            The id of the Item, must be > 0
     * @throws IllegalArgumentException
     *             If id <= 0
     */
    public Item(long id) {
        Helper.assertLongPositive(id, "id");

        this.id = id;
        answer = null;
        question = -1;
    }

    /**
     * <p>
     * Creates a new Item with the specified id and question, while other values
     * are "undefined".
     * </p>
     * @param id
     *            The id of the Item, must be > 0
     * @param question
     *            The id of the question the item is about
     * @throws IllegalArgumentException
     *             If id <= 0 or question <= 0
     */
    public Item(long id, long question) {
        Helper.assertLongPositive(id, "id");
        Helper.assertLongPositive(question, "question");

        this.id = id;
        answer = null;
        this.question = question;
    }

    /**
     * <p>
     * Creates a new Item for which all the fields are set to the given values.
     * </p>
     * @param id
     *            The id of the Item, must be > 0
     * @param question
     *            The id of the question the item is about
     * @param answer
     *            The answer to the question.
     * @throws IllegalArgumentException
     *             If id <= 0 or question <= 0
     * @throws IllegalArgumentException
     *             If answer is null
     */
    public Item(long id, long question, Serializable answer) {
        Helper.assertLongPositive(id, "id");
        Helper.assertLongPositive(question, "question");
        Helper.assertObjectNotNull(answer, "answer");

        this.id = id;
        this.answer = answer;
        this.question = question;
    }

    /**
     * <p>
     * Sets the id of the Item. The id valus must be > 0.
     * </p>
     * @param id
     *            The identifier to assign to the item
     * @throws IllegalArgumentException
     *             if id <= 0
     */
    public void setId(long id) {
        Helper.assertLongPositive(id, "id");

        this.id = id;
    }

    /**
     * <p>
     * Retrieves the id for this Item.
     * </p>
     * @return The id for this Item;
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
     * Sets the answer to the item. Passing null indicates that no answer is to
     * be associated with the item.
     * </p>
     * @param answer
     *            The answer to associate with the item. Can be null.
     */
    public void setAnswer(Serializable answer) {
        this.answer = answer;
    }

    /**
     * <p>
     * Gets the answer that is associated with the Item. The return value can be
     * null, indicating that no answer is associated with the Item.
     * </p>
     * @return The answer associated with this Item.
     */
    public Serializable getAnswer() {
        return answer;
    }

    /**
     * <p>
     * Sets the answer associated with this Item back to its "unassigned" value,
     * which is null.
     * </p>
     */
    public void resetAnswer() {
        setAnswer(null);
    }

    /**
     * <p>
     * Sets the question this Item references. The question must be > 0.
     * </p>
     * @param question
     *            The identifier of the question to associate with this Item
     * @throws IllegalArgumentException
     *             If question is <= 0
     */
    public void setQuestion(long question) {
        Helper.assertLongPositive(question, "question");

        this.question = question;
    }

    /**
     * <p>
     * Retrieves the identifier of the question this Item is associated with.
     * </p>
     * @return The identifier of the question this Item is associated with
     */
    public long getQuestion() {
        return question;
    }

    /**
     * <p>
     * Resets the question identifier back to its "undefined" value, which is
     * -1.
     * </p>
     */
    public void resetQuestion() {
        question = -1;
    }

    /**
     * <p>
     * Sets the identifer of a document that is attached to the Item. The value
     * may be null to indicate that no document is associated with the Item. If
     * non-null, the value must be > 0.
     * </p>
     * @param document
     *            The identifier for the attached document
     * @throws IllegalArgumentException
     *             If document is a wrapper of a value that is <= 0
     */
    public void setDocument(Long document) {
        if (document != null && document.longValue() <= 0) {
            throw new IllegalArgumentException("document is a wrapper of a value that is <= 0.");
        }

        this.document = document;
    }

    /**
     * <p>
     * Gets the attached document identifier. The return value may be null to
     * indicate that no document is attached. If non-null, the value will be >
     * 0.
     * </p>
     * @return The identifier of the attached document.
     */
    public Long getDocument() {
        return document;
    }

    /**
     * <p>
     * Resets the document field to null to indicate that no document is
     * attached to this Item.
     * </p>
     */
    public void resetDocument() {
        setDocument(null);
    }

    /**
     * <p>
     * Adds a comment to the item.
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
     * Adds all Comments in the array to the Item.
     * </p>
     * @param comments
     *            The array of Comments to add to the Item. (May be a 0-length
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
     * Removes the given comment associated with the Item.
     * </p>
     * @param comment
     *            The Comment to remove from this Item
     * @throws IllegalArgumentException
     *             If comment is null
     */
    public void removeComment(Comment comment) {
        Helper.assertObjectNotNull(comment, "comment");

        comments.remove(comment);
    }

    /**
     * <p>
     * Removes all comments in the array from the Item.
     * </p>
     * @param comments
     *            The array of Comments to remove from the Item. (May be a
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
     * Clears all Comments associated with the Item.
     * </p>
     */
    public void clearComments() {
        comments.clear();
    }

    /**
     * <p>
     * Gets the columnIndex-th item in the comments list.
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
     * Gets all the comments associated with the Item.
     * </p>
     * @return All the Comments associated with the Item. (May be a 0-length
     *         array.)
     */
    public Comment[] getAllComments() {
        return (Comment[]) comments.toArray(new Comment[] {});
    }

    /**
     * <p>
     * Gets the number of comments for this Item.
     * </p>
     * @return The number of comments
     */
    public int getNumberOfComments() {
        return comments.size();
    }

    /**
     * <p>
     * Removes the first comment with the given id from the comments list. If no
     * comment has the given id, then no comment will be removed.
     * </p>
     * @param id
     *            The id of the comment to remove
     * @throws IllegalArgumentException
     *             If id is <= 0 and not -1
     */
    public void removeComment(long id) {
        if (id <= 0 && id != -1) {
            throw new IllegalArgumentException("id should be -1 or larger than 0.");
        }

        for (int idx = 0; idx < comments.size(); ++idx) {
            if (((Comment) comments.get(idx)).getId() == id) {
                comments.remove(idx);
                return;
            }
        }
    }
}

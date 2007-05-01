package com.topcoder.chat.message.pool;

import java.util.*;

/**
 * This is an abstract class that represents a message. This class contains
 * information about it's sender, timestamp, recipients and attributes. This
 * class has four protected constructors and methods for
 * setting/getting/removing message's content (i.e. sender, timestamp etc.).
 * <p>
 * All accesses to fields are synchronized. So, the class is thread-safe.
 * </p>
 *
 */
public abstract class Message implements java.io.Serializable {

	/**
	 * <p>
	 * Represents the sender of the message. Can be null. There are setter and
	 * getter for this field. Access should be synchronized.
	 * </p>
	 *
	 */
	private Object sender = null;

	/**
	 * <p>
	 * Represents recipients of the message. Initialized once in constructor and
	 * then never changed. Can not be null. Can be empty. There are methods to
	 * add/remove/get recipients. Access should be synchronized.
	 * </p>
	 *
	 */
	private final Set recipients = new HashSet();

	/**
	 * <p>
	 * Represents timestamp of the message. Initialized in constructor by
	 * current timestamp. Can not be null. There are setter and getter for this
	 * field. Access should be synchronized.
	 * </p>
	 *
	 */
	private Date timestamp = new Date();

	/**
	 * <p>
	 * Represents attributes of the message. Initialized once in constructor and
	 * never changed. Can not be null. Can be empty. There are methods to
	 * add/remove/get attributes. Access should be synchronized.
	 * </p>
	 *
	 */
	private final Map attributes = new HashMap();

	/**
	 * <p>
	 * Creates Message instance with current timestamp, empty set of recipients
	 * and attributes, and null value for sender field.
	 * </p>
	 *
	 */
	protected Message() {
	}

	/**
	 * <p>
	 * Creates Message instance with current timestamp, empty set of recipients
	 * and attributes. Initializes sender field with the sender parameter.
	 * </p>
	 * <p>
	 * Since sender can be null, no exceptions are thrown here.
	 * </p>
	 *
	 *
	 * @param sender
	 *            Sender of the message. Can be null.
	 */
	protected Message(Object sender) {
		this.sender = sender;
	}

	/**
	 * <p>
	 * Creates Message instance with an empty set of recipients and attributes,
	 * and null value for sender field. Initializes timestamp field with the
	 * timestamp parameter.
	 * </p>
	 * <p>
	 * IllegalArgumentException is thrown if timeStamp is null.
	 * </p>
	 *
	 *
	 * @param timestamp
	 *            Timestamp of the message. Can not be null.
	 */
	protected Message(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * <p>
	 * Creates Message instance with an empty set of recipients and attributes.
	 * Initializes timestamp field with the timestamp parameter, and sender
	 * field with the sender parameter.
	 * </p>
	 * <p>
	 * IllegalArgumentException is thrown if timeStamp is null.
	 * </p>
	 *
	 *
	 * @param sender
	 *            Sender of the message. Can be null.
	 * @param timestamp
	 *            Timestamp of the message. Can not be null.
	 */
	protected Message(Object sender, Date timestamp) {
		this.sender = sender;
		this.timestamp = timestamp;
	}

	/**
	 * <p>
	 * Returns a sender of the message. Access to sender field should be
	 * synchronized.
	 * </p>
	 *
	 *
	 * @return Sender of the message. Can be null.
	 */
	public Object getSender() {
		return this.sender;
	}

	/**
	 * <p>
	 * Sets sender of the message. Access to sender field should be
	 * synchronized.
	 * </p>
	 *
	 *
	 * @param sender
	 *            Sender of the message. Can be null.
	 */
	public void setSender(Object sender) {
		this.sender = sender;
	}

	/**
	 * <p>
	 * Adds non-null recipient of the message to set of recipients (recipients
	 * field). If the recipient is already added, this method has no effect.
	 * Access to recipients field should be synchronized.
	 * </p>
	 * <p>
	 * IllegalArgumentException is thrown if recipient is null.
	 * </p>
	 *
	 *
	 * @param recipient
	 *            Recipient (to be added) of the message. Can not be null.
	 */
	public void addRecipient(Object recipient) throws IllegalArgumentException {
	}

	/**
	 * <p>
	 * Removes non-null recipient of the message from set of recipients
	 * (recipients field). Access to recipients field should be synchronized.
	 * </p>
	 * <p>
	 * IllegalArgumentException is thrown if recipient is null. MessageException
	 * is thrown if specified recipient was not previously added.
	 * </p>
	 *
	 *
	 * @param recipient
	 *            Recipient (to be removed) of the message. Can not be null.
	 */
	public void removeRecipient(Object recipient) {
	}

	/**
	 * <p>
	 * Removes all recipients of the message. Access to recipients field should
	 * be synchronized.
	 * </p>
	 *
	 */
	public void removeAllRecipients() {
	}

	/**
	 * <p>
	 * Returns all recipients of the message. Access to recipents field should
	 * be synchronized.
	 * </p>
	 *
	 *
	 * @return Recipients of the message.
	 */
	public Object[] getAllRecipients() {
		return null;
	}

	/**
	 * <p>
	 * Returns message's timestamp. Access to timestamp field should be
	 * synchronized.
	 * </p>
	 *
	 *
	 * @return Timestamp of the message. Can not be null.
	 */
	public java.util.Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * <p>
	 * Sets message's timestamp. Access to timestamp field should be
	 * synchronized.
	 * </p>
	 * <p>
	 * IllegalArgumentException is thrown if timestamp parameter is null.
	 * </p>
	 *
	 *
	 * @param timestamp
	 *            Timestamp of the message to be set. Can not be null.
	 */
	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * <p>
	 * Returns all attributes of the message. Access to attributes field should
	 * be synchronized.
	 * </p>
	 *
	 *
	 * @return Attributes of the message. Can not be null.
	 */
	public Map getAllAttributes() {
		return null;
	}

	/**
	 * <p>
	 * Returns value for specified non-null key. Access to attributes field
	 * should be synchronized.
	 * </p>
	 * <p>
	 * IllegalArgumentException is thrown if key is null. MessageException is
	 * thrown if specified attribute was not previously set.
	 * </p>
	 *
	 *
	 * @param key
	 *            non-null key for attribute to be returned.
	 * @return Attribute of the message with specified key. Can be null.
	 */
	public Object getAttribute(String key) {
		return null;
	}

	/**
	 * <p>
	 * Sets specified attribute with specified non-null key. Access to
	 * attributes field should be synchronized.
	 * </p>
	 * <p>
	 * IllegalArgumentException is thrown if key is null.
	 * </p>
	 *
	 *
	 * @param key
	 *            non-null key for attribute to be set.
	 * @param value
	 *            Value for attribute to be set. Can be null.
	 */
	public void setAttribute(String key, Object value) {
	}

	/**
	 * <p>
	 * Removes specified attribute. Access to attributes field should be
	 * synchronized.
	 * </p>
	 * <p>
	 * IllegalArgumentException is thrown if key is null. MessageException is
	 * thrown if specified attribute was not previously set.
	 * </p>
	 *
	 *
	 * @param key
	 *            non-null key of the attribute to be removed.
	 */
	public void removeAttribute(String key) {
	}

	/**
	 * <p>
	 * Removes all attributes of the message. Access to attributes field should
	 * be synchronized.
	 * </p>
	 *
	 */
	public void removeAllAttributes() {
	}
}

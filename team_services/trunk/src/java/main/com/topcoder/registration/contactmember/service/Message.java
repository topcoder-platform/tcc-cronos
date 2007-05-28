package com.topcoder.registration.contactmember.service;

import java.util.Date;

public class Message {

	/**
	 * <p>Represents the recipient handles to who the messages will be sent.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will contain one or more non-null/empty Strings. It will never be null.</p>
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79ea]
	 */
	    private String[] toHandles;

	/**
	 * <p>Represents the message text that will be sent to all recipients.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will never be null/empty</p>
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79e5]
	 */
	    private String text = null;

	/**
	 * <p>Represents the sender's handle.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will never be null/empty</p>
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79df]
	 */
	    private String fromHandle = null;

	/**
	 * <p>Represents the ID of the project that this message concerns.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be negative</p>
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79d8]
	 */
	    private long projectId = -1;

	/**
	 * <p>Represents the name of the project that this message concerns.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will never be null/empty</p>
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79d2]
	 */
	    private String projectName = null;

	/**
	 * <p>Represents the date and time of this message.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will never be null.</p>
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79c9]
	 */
	    private Date timeStamp = null;

	/**
	 * <p>Represents the subject for the message that will be sent to all recipients.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>It can be null, indicating the desire to use a default subject. Empty values are set to null.</p>
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79c3]
	 */
	    private String subject = null;

	/**
	 * <p>Represents the ID of this message.</p>
	 * <p>This value can be set in the constructor or in the setter, and accessed with the getter.</p>
	 * <p>Once set, it will not be negative</p>
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79bd]
	 */
	    private long messageId = -1;

	/**
	 * Default constructor. Sets toHandles to empty array.
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79b6]
	 */
	    public  Message() {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * Constructor. This convenience constructor allows for an external user to set all fields in one go without the messageId or timestamp, both of this will be added by the service. The subject can be null, and empty subjects are set to null.
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm79aa]
	 * @param toHandles the recipient handles to who the messages will be sent
	 * @param text the message text that will be sent to all recipients
	 * @param fromHandle the sender's handle
	 * @param projectId the ID of the project that this message concerns
	 * @param projectName the name of the project that this message concerns
	 * @param subject the subject header for the message that will be sent to all recipients
	 * @throws IllegalArgumentException If toHandles is null, empty, or contains empty/null elements
	 * @throws IllegalArgumentException If text is null/empty
	 * @throws IllegalArgumentException If fromHandle is null/empty
	 * @throws IllegalArgumentException If projectId is negative
	 * @throws IllegalArgumentException If projectName is null/empty
	 */
	    public  Message(String[] toHandles, String text, String fromHandle, long projectId, String projectName, String subject) {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * Full constructor. This convenience constructor allows for setting all values in one go. The subject can be null, and empty subjects are set to null.
	 * 
	 * @poseidon-object-id [Im2c454c77m112201430d2mm7991]
	 * @param toHandles the recipient handles to who the messages will be sent
	 * @param text the message text that will be sent to all recipients
	 * @param fromHandle the sender's handle
	 * @param projectId the ID of the project that this message concerns
	 * @param projectName the name of the project that this message concerns
	 * @param timeStamp the date and time of this message
	 * @param subject the subject header for the message that will be sent to all recipients
	 * @param messageId the ID of this message
	 * @throws IllegalArgumentException If toHandles is null, empty, or contains empty/null elements
	 * @throws IllegalArgumentException If text is null/empty
	 * @throws IllegalArgumentException If fromHandle is null/empty
	 * @throws IllegalArgumentException If projectId is negative
	 * @throws IllegalArgumentException If projectName is null/empty
	 * @throws IllegalArgumentException If timeStamp is null
	 * @throws IllegalArgumentException If messageId is negative
	 */
	    public  Message(String[] toHandles, String text, String fromHandle, long projectId, String projectName, Date timeStamp, String subject, long messageId) {        /** lock-end */
	        // your code here
	    } /** lock-begin */

	/**
	 * @return Returns the fromHandle.
	 */
	public String getFromHandle() {
		return fromHandle;
	}

	/**
	 * @param fromHandle The fromHandle to set.
	 */
	public void setFromHandle(String fromHandle) {
		this.fromHandle = fromHandle;
	}

	/**
	 * @return Returns the messageId.
	 */
	public long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId The messageId to set.
	 */
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return Returns the projectId.
	 */
	public long getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId The projectId to set.
	 */
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	/**
	 * @return Returns the projectName.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName The projectName to set.
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text The text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return Returns the timeStamp.
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp The timeStamp to set.
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return Returns the toHandles.
	 */
	public String[] getToHandles() {
		return toHandles;
	}

	/**
	 * @param toHandles The toHandles to set.
	 */
	public void setToHandles(String[] toHandles) {
		this.toHandles = toHandles;
	}

}

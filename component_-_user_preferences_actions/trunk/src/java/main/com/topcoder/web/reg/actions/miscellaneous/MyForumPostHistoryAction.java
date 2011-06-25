/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Iterator;

import com.jivesoftware.base.JiveConstants;
import com.jivesoftware.base.User;
import com.jivesoftware.forum.ForumMessage;
import com.jivesoftware.forum.ResultFilter;
import com.jivesoftware.forum.action.util.Paginator;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.ejb.messagehistory.MessageHistoryLocal;
import com.topcoder.web.forums.ForumConstants;
import com.topcoder.web.forums.model.Paging;

/**
 * <p>
 * This action extends the BaseForumAction, and it allows user to see his/her forum posts.
 * </p>
 * <p>
 * All its instance variables have setters/getters and will be injected.
 * </p>
 * <p>
 * Thread safety: This class is mutable and not thread safe, however will be used thread-safely in Struts framework.
 * </p>
 * @author maksimilian, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MyForumPostHistoryAction extends BaseForumAction {

    /**
     * <p>
     * Represents execute() method signature.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "MyForumPostHistoryAction.execute()";

    /**
     * <p>
     * Represents validate() method signature.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "MyForumPostHistoryAction.validate()";

    /**
     * <p>
     * Represents the start index for my posts.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is used in corresponding getter/setter
     * and execute method. Can't be negative when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int startIndex;

    /**
     * <p>
     * Represents the range for my posts.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is used in corresponding getter/setter
     * and execute method. Can't be negative when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int range;

    /**
     * <p>
     * Represents the sort field for my posts.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String sortField;

    /**
     * <p>
     * Represents the sort order for my posts.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String sortOrder;

    /**
     * <p>
     * Represents the history user.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private User historyUser;

    /**
     * <p>
     * Represents the user messages.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private Iterator < ForumMessage > messages;

    /**
     * <p>
     * Represents the paginator.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private Paginator paginator;

    /**
     * <p>
     * Represents the message history local bean.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is supposed to be injected by container.
     * It is used in corresponding getter/setter and execute method. Can't be null when execute method is called. Used
     * in getter/setter, prepare and execute method.
     * </p>
     */
    private MessageHistoryLocal messageHistoryBean;

    /**
     * <p>
     * Creates an instance of MyForumPostHistoryAction.
     * </p>
     */
    public MyForumPostHistoryAction() {
        super();
    }

    /**
     * <p>
     * Execute method is responsible for viewing the user forum posts.
     * </p>
     * @return a string representing the logical result of the execution. If an action succeeds, it returns
     *         Action.SUCCESS. If some error occurs, the exception will be thrown.
     * @throws UserPreferencesActionExecutionException if any unexpected error occurs. (it's used to wrap all
     *             underlying exceptions)
     */
    public String execute() throws UserPreferencesActionExecutionException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null);
        // get all the execution results:
        historyUser = Helper.getLoggedInForumUser(this, EXECUTE_METHOD_SIGNATURE);
        // create sort filter for field and order
        ResultFilter resultFilter = ResultFilter.createDefaultMessageFilter();
        resultFilter.setSortField(Integer.parseInt(sortField));
        resultFilter.setSortOrder(Integer.parseInt(sortOrder));
        resultFilter.setStartIndex(startIndex);
        resultFilter.setNumResults(range);
        // retrieve all items count
        int totalItemCount = getForumFactory().getUserMessageCount(historyUser, resultFilter);
        // create paginator
        Paging paging = new Paging(resultFilter, totalItemCount);
        paginator = new Paginator(paging);
        // retrieve messages
        messages = (Iterator < ForumMessage >) getForumFactory().getUserMessages(historyUser, resultFilter);
        LoggingWrapperUtility.logExit(getLogger(), EXECUTE_METHOD_SIGNATURE, new String[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Validates the inputed parameters.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null);
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (range <= 0) {
            range = ForumConstants.DEFAULT_HISTORY_RANGE;
        }
        if (sortField == null || sortField.trim().length() == 0) {
            sortField = String.valueOf(JiveConstants.MODIFICATION_DATE);
        }
        if (sortOrder == null || sortOrder.trim().length() == 0) {
            sortOrder = String.valueOf(ResultFilter.DESCENDING);
        }
        MyForumPostHistoryAction.checkValidIntegerField(getLogger(), VALIDATE_METHOD_SIGNATURE, this, sortField,
                "sortField");
        MyForumPostHistoryAction.checkValidIntegerField(getLogger(), VALIDATE_METHOD_SIGNATURE, this, sortOrder,
                "sortOrder");
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null);
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the range
     */
    public int getRange() {
        return range;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param range the range to set
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the sortField
     */
    public String getSortField() {
        return sortField;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param sortField the sortField to set
     */
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the sortOrder
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the historyUser
     */
    public User getHistoryUser() {
        return historyUser;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param historyUser the historyUser to set
     */
    public void setHistoryUser(User historyUser) {
        this.historyUser = historyUser;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the messages
     */
    public Iterator < ForumMessage > getMessages() {
        return messages;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param messages the messages to set
     */
    public void setMessages(Iterator < ForumMessage > messages) {
        this.messages = messages;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the paginator
     */
    public Paginator getPaginator() {
        return paginator;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param paginator the paginator to set
     */
    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the messageHistoryBean
     */
    public MessageHistoryLocal getMessageHistoryBean() {
        return messageHistoryBean;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param messageHistoryBean the messageHistoryBean to set
     */
    public void setMessageHistoryBean(MessageHistoryLocal messageHistoryBean) {
        this.messageHistoryBean = messageHistoryBean;
    }

    /**
     * <p>
     * Checks whether given fieldValue is valid integer value.
     * </p>
     * @param logger the logger to provide logging
     * @param methodSignature the method signature
     * @param action the action to be populated with field errors
     * @param fieldValue the field value to check
     * @param fieldName the field name
     */
    private static void checkValidIntegerField(Log logger, String methodSignature, ActionSupport action,
            String fieldValue, String fieldName) {
        try {
            Integer.parseInt(fieldValue);
        } catch (NumberFormatException e) {
            String errorMessage = fieldName + " has invalid numeric format.";
            action.addFieldError(fieldName, errorMessage);
            logger.log(Level.WARN, "Validation error in method " + methodSignature + ". " + errorMessage);
        }
    }
}

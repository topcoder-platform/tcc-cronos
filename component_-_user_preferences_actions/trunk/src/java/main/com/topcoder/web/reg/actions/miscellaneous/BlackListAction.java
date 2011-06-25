/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.web.common.dao.MemberContactBlackListDAO;
import com.topcoder.web.common.model.MemberContactBlackList;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This action extends the BasePreferencesAction, and it manages the user's black list of users who can't send the mail
 * to the current user.
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
public class BlackListAction extends BasePreferencesAction {

    /**
     * <p>
     * Represents audit operation type.
     * </p>
     */
    private static final String AUDIT_OPERATION_TYPE = "User Black List Update";

    /**
     * <p>
     * Represents blocked value for auditing.
     * </p>
     */
    private static final String BLOCKED_VALUE = "Blocked";

    /**
     * <p>
     * Represents execute() method signature.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "BlackListAction.execute()";

    /**
     * <p>
     * Represents not blocked value for auditing.
     * </p>
     */
    private static final String NOT_BLOCKED_VALUE = "Not blocked";

    /**
     * <p>
     * Represents validate() method signature.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "BlackListAction.validate()";

    /**
     * <p>
     * Represents the list of the blocked users.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private List < User > blockedUsers;

    /**
     * <p>
     * Represents the member contact black list DAO which will be used to manage black list.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null. Used in getter/setter.
     * </p>
     */
    private MemberContactBlackListDAO memberContactBlackListDao;

    /**
     * <p>
     * Represents the list of the previously blocked users.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be used by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private List < User > previouslyBlockedUsers;

    /**
     * <p>
     * Represents the blocked users parameters key which will be used to manage black list.
     * </p>
     * <p>
     * It's injected by the container. After injection, it must be non-null, non-empty. Used in getter/setter.
     * </p>
     */
    private String blockedUsersParametersKey;

    /**
     * <p>
     * Creates an instance of BlackListAction.
     * </p>
     */
    public BlackListAction() {
        super();
    }

    /**
     * <p>
     * Execute method is responsible for viewing/updating the user's black list of users who can't send the mail to the
     * current user.
     * </p>
     * @return a string representing the logical result of the execution. If an action succeeds, it returns
     *         Action.SUCCESS. If some error occurs, the exception will be thrown.
     * @throws UserPreferencesActionExecutionException - if any unexpected error occurs. (it's used to wrap all
     *             underlying exceptions)
     * @throws PreferencesActionDiscardException - if error occurs when attempting to discard
     */
    public String execute() throws UserPreferencesActionExecutionException, PreferencesActionDiscardException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null);
        // get the user:
        User user = getLoggedInUser();
        // handle submit action
        if (getAction().equals(Helper.SUBMIT_ACTION)) {
            // backup the user:
            ActionContext.getContext().getSession().put(getBackupSessionKey(), user);
            // get the current black list of the user from persistence:
            List < User > currentBlockedUsers =
                    (List < User >) memberContactBlackListDao.getBlockedUsers(user.getId());
            // for each User that is in blockedUsers but not in the currentBlockedUsers add the blocked user to
            // persistence.
            for (User newBlockedUser : blockedUsers) {
                if (!containsUser(currentBlockedUsers, newBlockedUser)) {
                    User blockedUser = getUserDao().find(newBlockedUser.getHandle(), true);
                    MemberContactBlackList blackList = new MemberContactBlackList();
                    blackList.setId(new MemberContactBlackList.Identifier(user, blockedUser));
                    blackList.setBlocked(true);
                    audit(NOT_BLOCKED_VALUE, BLOCKED_VALUE, AUDIT_OPERATION_TYPE);
                    // provide auditing
                    memberContactBlackListDao.saveOrUpdate(blackList);
                }
            }
            // for each User that is in currentBlockedUsers but not in the blockedUsers remove the blocked user
            // from persistence.
            for (User oldBlockedUser : currentBlockedUsers) {
                if (!containsUser(blockedUsers, oldBlockedUser)) {
                    User blockedUser = getUserDao().find(oldBlockedUser.getHandle(), true);
                    MemberContactBlackList blackList = memberContactBlackListDao.find(user, blockedUser);
                    blackList.setBlocked(false);
                    audit(BLOCKED_VALUE, NOT_BLOCKED_VALUE, AUDIT_OPERATION_TYPE);
                    // provide auditing
                    memberContactBlackListDao.saveOrUpdate(blackList);
                }
            }
        }
        // handle discard action
        if (getAction().equals(Helper.DISCARD_ACTION)) {
            Helper.handleDiscardAction(this, EXECUTE_METHOD_SIGNATURE);
        }
        // send email if needed
        if (isEmailSendFlag()) {
            sendEmail();
        }
        LoggingWrapperUtility.logExit(getLogger(), EXECUTE_METHOD_SIGNATURE, new String[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the blockedUsers
     */
    public List < User > getBlockedUsers() {
        return blockedUsers;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the memberContactBlackListDao
     */
    public MemberContactBlackListDAO getMemberContactBlackListDao() {
        return memberContactBlackListDao;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the previouslyBlockedUsers
     */
    public List < User > getPreviouslyBlockedUsers() {
        return previouslyBlockedUsers;
    }

    /**
     * <p>
     * Prepares the action and checks field on valid values after injection.
     * </p>
     * @param Return
     * @throws Exception if any field has invalid value after injection
     */
    public void prepare() throws Exception {
        // check fields
        super.prepare();
        ValidationUtility.checkNotNull(memberContactBlackListDao, "memberContactBlackListDao",
                UserPreferencesActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(blockedUsersParametersKey, "blockedUsersParametersKey",
                UserPreferencesActionConfigurationException.class);
        // prepare action
        User user = getLoggedInUser();
        blockedUsers = (List < User >) memberContactBlackListDao.getBlockedUsers(user.getId());
        previouslyBlockedUsers = (List < User >) memberContactBlackListDao.getPreviouslyBlockedUsers(user.getId());
        previouslyBlockedUsers.removeAll(blockedUsers);
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param blockedUsers the blockedUsers to set
     */
    public void setBlockedUsers(List < User > blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param memberContactBlackListDao the memberContactBlackListDao to set
     */
    public void setMemberContactBlackListDao(MemberContactBlackListDAO memberContactBlackListDao) {
        this.memberContactBlackListDao = memberContactBlackListDao;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param previouslyBlockedUsers the previouslyBlockedUsers to set
     */
    public void setPreviouslyBlockedUsers(List < User > previouslyBlockedUsers) {
        this.previouslyBlockedUsers = previouslyBlockedUsers;
    }

    /**
     * <p>
     * Validates the input parameters.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null);
        // retrieve blocked users
        String[] blockedUsersHandles = ServletActionContext.getRequest().getParameterValues(blockedUsersParametersKey);
        createBlockedUsers(blockedUsersHandles);
        int i = 0;
        for (User user : blockedUsers) {
            Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, user.getHandle(),
                    "blockedUsers[index=" + (i++) + "]");
        }
    }

    /**
     * <p>
     * Populates blockedUsers parameter with given newly created Users with handles from given blocked users array.
     * </p>
     * @param blockedUsersHandles the blocked users handles
     */
    private void createBlockedUsers(String[] blockedUsersHandles) {
        blockedUsers = new ArrayList < User >();
        if (blockedUsersHandles != null) {
            for (String handle : blockedUsersHandles) {
                User user = new User();
                user.setHandle(handle);
                blockedUsers.add(user);
            }
        }
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the blockedUsersParametersKey
     */
    public String getBlockedUsersParametersKey() {
        return blockedUsersParametersKey;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param blockedUsersParametersKey the blockedUsersParametersKey to set
     */
    public void setBlockedUsersParametersKey(String blockedUsersParametersKey) {
        this.blockedUsersParametersKey = blockedUsersParametersKey;
    }

    /**
     * <p>
     * Checks whether given list contains given user comparing by handle.
     * </p>
     * @param list the list to check
     * @param user the user to check
     * @return true, if given list contains given user comparing by handle, false otherwise
     */
    private static boolean containsUser(List < User > list, User user) {
        for (User u : list) {
            if (user.getHandle().equals(u.getHandle())) {
                return true;
            }
        }
        return false;
    }
}

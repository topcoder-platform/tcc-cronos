/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * This is a base class for Struts actions, which perform search for group
 * invitations. This class inherits from GroupInvitationAwareBaseAction and
 * additionally holds group invitation search related input and output
 * parameters, exposing them to subclasses.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is technically mutable and not thread-safe,
 * however it's expected to be used as Spring bean and thus it will be immutable
 * after initialization (as well as base class), so it's thread-safe.
 * </p>
 * <p>
 * <b>Sample Usage:</b>
 *
 * Spring configuration:
 *
 * <pre>
 * &lt;bean id="GroupInvitationSearchBaseAction" abstract="true"
 *   class="com.topcoder.security.groups.actions.GroupInvitationSearchBaseAction"&gt;
 *   &lt;property name="logger" ref="logger"/&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * Struts configuration:
 *
 * <pre>
 * &lt;action name="groupInvitationSearchBaseAction" class="GroupInvitationSearchBaseAction"
 *    method="execute"&gt;
 *    &lt;result name="success"&gt;success.jsp&lt;/result&gt;
 *    &lt;result name="input"&gt;group_invitation_search.jsp&lt;/result&gt;
 * &lt;/action&gt;
 * </pre>
 *
 * </p>
 * @author gevak, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class GroupInvitationSearchBaseAction extends
        GroupInvitationAwareBaseAction {

    /** The default page size. */
    private static final int DEFAULT_PAGESIZE = 10;
    /**
     * Invitation search criteria, used for searching the invitations. Will be
     * used by subclasses. It is injected via the setter with no validation,
     * thus can be any value. When execute() is called, XML validations are
     * applied on this variable. Has default value (in order to
     * allow page to load first time (before user clicks search/filter button)).
     * Mutable via public setter, exposed to subclasses via protected getter.
     */
    private InvitationSearchCriteria criteria = new InvitationSearchCriteria();

    /**
     * Amount of found records to show on a single page (used for search result
     * pagination). Will be used by subclasses. It is injected via the setter
     * with no validation, thus can be any value. When execute() is called,
     * <code>validate</code> method is applied on this variable. Has default
     * value (in order to allow page to load first time (before user clicks
     * search/filter button)). Mutable via public setter, exposed to subclasses
     * via protected getter.
     */
    private int pageSize = DEFAULT_PAGESIZE;

    /**
     * 1-based index of page to show (used for search result pagination). 0 for
     * retrieving all records. Will be used by subclasses. It is injected via
     * the setter with no validation, thus can be any value. When execute() is
     * called, <code>validate</code> method is applied on this variable. Has
     * default value (in order to allow page to load first time (before user
     * clicks search/filter button)). Mutable via public setter, exposed to
     * subclasses via protected getter.
     */
    private int page = 1;

    /**
     * The invitations search result. Initially null, but expected to be
     * populated by subclasses with non-null value. Has public getter and
     * protected setter.
     */
    private PagedResult<GroupInvitation> invitations;

    /**
     * Empty default constructor.
     */
    protected GroupInvitationSearchBaseAction() {
    }
    /**
     * Validate the fields.
     */
    public void validate() {
        if (page != 0 && pageSize <= 0) {
            addFieldError("pageSize", "PageSize must be positive when page != 0");
        }
        if (page < 0) {
            addFieldError("page", "Page must be non-negative");
        }
    }

    /**
     * Gets invitation search criteria, used for searching the invitations.
     *
     * @return Invitation search criteria, used for searching the invitations.
     *
     */
    protected InvitationSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * Sets invitation search criteria, used for searching the invitations.
     *
     * @param criteria Invitation search criteria, used for searching the
     *            invitations.
     *
     */
    public void setCriteria(InvitationSearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * Gets amount of found records to show on a single page (used for search
     * result pagination).
     *
     * @return Amount of found records to show on a single page (used for search
     *         result pagination).
     *
     */
    protected int getPageSize() {
        return pageSize;
    }

    /**
     * Sets amount of found records to show on a single page (used for search
     * result pagination).
     *
     * @param pageSize Amount of found records to show on a single page (used
     *            for search result pagination).
     *
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets 1-based index of page to show (used for search result pagination). 0
     * for retrieving all records.
     *
     * @return 1-based index of page to show (used for search result
     *         pagination). 0 for retrieving all records.
     *
     */
    protected int getPage() {
        return page;
    }

    /**
     * Sets 1-based index of page to show (used for search result pagination). 0
     * for retrieving all records.
     *
     * @param page 1-based index of page to show (used for search result
     *            pagination). 0 for retrieving all records.
     *
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets the invitations search result.
     *
     * @return The invitations search result.
     *
     */
    public PagedResult<GroupInvitation> getInvitations() {
        return invitations;
    }

    /**
     * Sets the invitations search result.
     *
     * @param invitations The invitations search result.
     *
     */
    protected void setInvitations(PagedResult<GroupInvitation> invitations) {
        this.invitations = invitations;
    }
}

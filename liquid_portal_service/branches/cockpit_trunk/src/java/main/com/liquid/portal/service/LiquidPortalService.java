/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.util.Date;

import javax.jws.WebService;

import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;

/**
 * <p>
 * This represents the API to manage portal users, create and delete contests,
 * and provision projects.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations should be effectively thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@WebService
public interface LiquidPortalService {
	/**
	 * <p>
	 * Creates the user in TopCoder site with the passed-in information. Returns
	 * the non-null result of the action.
	 * </p>
	 * 
	 * @param user
	 *            the User instance with the user info
	 * @param termsAgreedDate
	 *            the date of the terms agreement
	 * @return the result of the registration attempt
	 * @throws LiquidPortalIllegalArgumentException
	 *             It argument is invalid, the error details are provided by the
	 *             errorCode in the exception:
	 *             <ul>
	 *             <li>user is null -- 2000</li>
	 *             <li>user.firstName is null/empty -- 2001</li>
	 *             <li>user.lastName is null/empty -- 2002</li>
	 *             <li>user.emailAddress is null/empty -- 2003</li>
	 *             <li>user.password is null/empty -- 2004</li>
	 *             <li>user.handle is null/empty -- 2005</li>
	 *             <li>user.phone is null/empty -- 2006</li>
	 *             <li>termsAgreedDate is null -- 2007</li>
	 *             <li>termsAgreedDate in the future -- 2008</li>
	 *             </ul>
	 * @throws HandleCreationException
	 *             If unable to create the user handle
	 * @throws LiquidPortalServiceException
	 *             If an error occurs while performing the operation
	 */
	public RegisterUserResult registerUser(User user, Date termsAgreedDate)
			throws LiquidPortalServiceException;

	/**
	 * <p>
	 * Ensurer the passed-in handle is valid, and it's in the correct groups.
	 * Provides non-null result with warnings if it is not.
	 * </p>
	 * 
	 * @param user
	 *            he user info to validate force
	 * @param force
	 *            If true, it will ignore warnings and add user to terms &
	 *            eligibility groups
	 * @return the validation result
	 * @throws LiquidPortalIllegalArgumentException
	 *             It argument is invalid, the error details are provided by the
	 *             errorCode in the exception:
	 *             <ul>
	 *             <li>user is null -- 2000</li>
	 *             <li>user.firstName is null/empty -- 2001</li>
	 *             <li>user.lastName is null/empty -- 2002</li>
	 *             <li>user.emailAddress is null/empty -- 2003</li>
	 *             <li>user.handle is null/empty -- 2005</li>
	 *             </ul>
	 * @throws HandleNotFoundException
	 *             If unable to find the given user handle
	 * @throws LiquidPortalServiceException
	 *             If an error occurs while performing the operation
	 */
	public Result validateUser(UserInfo user, boolean force)
			throws LiquidPortalServiceException;

	/**
	 * <p>
	 * Sets up the user with the correct permissions to be able to create
	 * competitions as needed. Returns the non-null result of the action.
	 * </p>
	 * 
	 * @param hasAccountAccess
	 *            flag whether the user has account level access
	 * @param cockpitProjectNames
	 *            the names of all cockpit projects that the user has access to
	 * @param requestorHandle
	 *            the TopCoder handle of the user that requested the update
	 * @param billingProjectIds
	 *            the IDs of the billing projects that user can charge against
	 *            when creating competitions
	 * @param userHandle
	 *            the handle of the user getting setup
	 * @return the provisioning result
	 * @throws LiquidPortalIllegalArgumentException
	 *             It argument is invalid, the error details are provided by the
	 *             errorCode in the exception:
	 *             <ul>
	 *             <li>requestorHandle is null/empty -- 3000</li>
	 *             <li>userHandle is null/empty -- 3001</li>
	 *             <li>cockpitProjectNames is null/empty -- 3002</li>
	 *             <li>cockpitProjectNames contains null/empty entries -- 3003</li>
	 *             <li>billingProjectIds is null/empty -- 3004</li>
	 *             </ul>
	 * @throws HandleNotFoundException
	 *             If unable to find the given requester or user handle
	 * @throws InvalidHandleException
	 *             If the handle is invalid
	 * @throws LiquidPortalServiceException
	 *             If an error occurs while performing the operation
	 */
	public ProvisionUserResult provisionUser(String requestorHandle,
			String userHandle, boolean hasAccountAccess,
			String[] cockpitProjectNames, long[] billingProjectIds)
			throws LiquidPortalServiceException;

	/**
	 * <p>
	 * Creates a new competition. It checks the user has permissions to perform
	 * this task. Returns the non-null result of the action.
	 * </p>
	 * 
	 * @param requestorHandle
	 *            the TopCoder handle of the user that requested the new
	 *            competition
	 * @param competitionData
	 *            the data about the competition to create
	 * @param supportHandles
	 *            the handles that will perform contest support, such as forum
	 *            monitoring
	 * @return the competition creation result
	 * @throws LiquidPortalIllegalArgumentException
	 *             It argument is invalid, the error details are provided by the
	 *             errorCode in the exception:
	 *             <ul>
	 *             <li>requestorHandle is null/empty -- 3000</li>
	 *             <li>competitionData is null -- 4000</li>
	 *             <li>supportHandles is null -- 3005</li>
	 *             <li>supportHandles contains null/empty elements -- 3006</li>
	 *             <li>contestTypeName is null/empty -- 4001</li>
	 *             <li>subContestTypeName is null/empty and contestTypeName =
	 *             "studio" -- 4002</li>
	 *             <li>contestName is null/empty -- 4003</li>
	 *             <li>cockpitProjectName is null/empty -- 4004</li>
	 *             <li>requestedStartDate is null -- 4005</li>
	 *             </ul>
	 * @throws HandleNotFoundException
	 *             If unable to find the given requester handle or any of the
	 *             supportHandles handles
	 * @throws InvalidHandleException
	 *             If the handle is invalid
	 * @throws ActionNotPermittedException
	 *             If the user with the handle does not have the permissions to
	 *             perform this action
	 * @throws LiquidPortalServiceException
	 *             If an error occurs while performing the operation
	 */
	public CreateCompetitonResult createCompetition(String requestorHandle,
			CompetitionData competitionData, String[] supportHandles)
			throws LiquidPortalServiceException;

	/**
	 * <p>
	 * Sets up the project with the list of handles that can access the project.
	 * Returns the non-null result of the action.
	 * </p>
	 * 
	 * @param requestorHandle
	 *            the TopCoder handle of the user that requested the project
	 *            provisioning
	 * @param cockpitProjectName
	 *            the name of the project to provision
	 * @param handles
	 *            the handles of the users to be given full control of the
	 *            cockpit project
	 * @return the provisioning result
	 * @throws LiquidPortalIllegalArgumentException
	 *             It argument is invalid, the error details are provided by the
	 *             errorCode in the exception:
	 *             <ul>
	 *             <li>requestorHandle is null/empty -- 3000</li>
	 *             <li>cockpitProjectName is null/empty -- 4004</li>
	 *             <li>handles is null/empty -- 3007</li>
	 *             <li>handles contains null/empty elements -- 3008</li>
	 *             </ul>
	 * @throws HandleNotFoundException
	 *             If unable to find the given requester handle or any of the
	 *             handles
	 * @throws InvalidHandleException
	 *             If the handle is invalid
	 * @throws ActionNotPermittedException
	 *             If the user with the handle does not have the permissions to
	 *             perform this action
	 * @throws LiquidPortalServiceException
	 *             If an error occurs while performing the operation
	 */
	public Result provisionProject(String requestorHandle,
			String cockpitProjectName, String[] handles)
			throws LiquidPortalServiceException;

	/**
	 * <p>
	 * Deletes the given contest.
	 * </p>
	 * 
	 * @param contestId
	 *            the ID of the contest to delet
	 * @param requestorHandle
	 *            the TopCoder handle of the user that requested the contest
	 *            deletion
	 * @param reason
	 *            the reason for the deletion
	 * @param isStudio
	 *            flag whether the contest is a studio competition
	 * @throws LiquidPortalIllegalArgumentException
	 *             It argument is invalid, the error details are provided by the
	 *             errorCode in the exception:
	 *             <ul>
	 *             <li>requestorHandle is null/empty -- 3000</li>
	 *             <li>reason is empty -- 3009</li>
	 *             </ul>
	 * @throws HandleNotFoundException
	 *             If unable to find the given requester handle
	 * @throws ContestNotFoundException
	 *             If unable to find contest with the given ID
	 * @throws ActionNotPermittedException
	 *             If the user with the handle does not have the permissions to
	 *             perform this action
	 * @throws LiquidPortalServiceException
	 *             If an error occurs while performing the operation
	 */
	public void deleteCompetition(String requestorHandle, long contestId,
			boolean isStudio, String reason)
			throws LiquidPortalServiceException;

	/**
	 * <p>
	 * Removes the user with the given handle.
	 * </p>
	 * 
	 * @param requestorHandle
	 *            the TopCoder handle of the user that requested the delete
	 * @param userHandle
	 *            the handle of the user to be decommisioned
	 * @throws LiquidPortalIllegalArgumentException
	 *             It argument is invalid, the error details are provided by the
	 *             errorCode in the exception:
	 *             <ul>
	 *             <li>requestorHandle is null/empty -- 3000</li>
	 *             <li>userHandle is null/empty -- 3010</li>
	 *             </ul>
	 * @throws HandleNotFoundException
	 *             If unable to find the given requester or user handle
	 * @throws ActionNotPermittedException
	 *             If the user with the handle does not have the permissions to
	 *             perform this action
	 * @throws LiquidPortalServiceException
	 *             If an error occurs while performing the operation
	 */
	public void decommissionUser(String requestorHandle, String userHandle)
			throws LiquidPortalServiceException;
}

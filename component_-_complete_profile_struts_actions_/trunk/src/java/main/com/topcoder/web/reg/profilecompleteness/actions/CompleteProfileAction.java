/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.profilecompleteness.actions;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.reg.profilecompleteness.filter.impl.UserIdRetriever;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.dao.AuditDAO;
import com.topcoder.web.common.dao.CoderTypeDAO;
import com.topcoder.web.common.dao.CountryDAO;
import com.topcoder.web.common.dao.DemographicQuestionDAO;
import com.topcoder.web.common.dao.StateDAO;
import com.topcoder.web.common.dao.TimeZoneDAO;
import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.Address;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.CoderType;
import com.topcoder.web.common.model.Company;
import com.topcoder.web.common.model.Contact;
import com.topcoder.web.common.model.Country;
import com.topcoder.web.common.model.DemographicAnswer;
import com.topcoder.web.common.model.DemographicQuestion;
import com.topcoder.web.common.model.DemographicResponse;
import com.topcoder.web.common.model.Phone;
import com.topcoder.web.common.model.State;
import com.topcoder.web.common.model.TimeZone;
import com.topcoder.web.common.model.User;
import com.topcoder.web.common.model.UserProfile;
import com.topcoder.web.common.model.UserSecurityKey;

/**
 * <p>
 * This is the action that is responsible for getting and saving user profile for certain purpose (such as for forum
 * participation). It extends ActionSupport and implement ServletRequestAware and ServletResponseAware.
 * </p>
 * <p>
 * It performs the full logic of getting user profile and updating user profile. There is no need to define any
 * subclasses for specific purpose such as for forum participation.
 * </p>
 * <p>
 * The struts validation framework can be configured so that different rules apply to different action URL, which are
 * all backed by this class.
 * </p>
 * <p>
 * Sample Spring configuration:
 * <pre>
 *   &lt;bean id=&quot;completeProfileAction&quot;
 *     class=&quot;com.topcoder.web.reg.profilecompleteness.actions.CompleteProfileAction&quot;
 *     init-method=&quot;checkConfiguration&quot;&gt;
 *     &lt;property name=&quot;phoneTypeId&quot; value=&quot;1&quot; /&gt;
 *     &lt;property name=&quot;primary&quot; value=&quot;true&quot; /&gt;
 *     &lt;property name=&quot;securityKeyTypeId&quot; value=&quot;1&quot; /&gt;
 *     &lt;property name=&quot;getUserProfileOperationType&quot; value=&quot;Get User Profile Operation&quot; /&gt;
 *     &lt;property name=&quot;saveUserProfileOperationType&quot; value=&quot;Save User Profile Operation&quot; /&gt;
 *     &lt;property name=&quot;incomingRequestURIKey&quot; value=&quot;incomingRequestURIKey&quot; /&gt;
 *     &lt;property name=&quot;genderQuestionId&quot; value=&quot;1&quot; /&gt;
 *     &lt;property name=&quot;ageQuestionId&quot; value=&quot;2&quot; /&gt;
 *     &lt;!-- DAOs --&gt;
 *     &lt;property name=&quot;userDAO&quot; ref=&quot;userDAO&quot; /&gt;
 *     &lt;property name=&quot;auditDAO&quot; ref=&quot;auditDAO&quot;/&gt;
 *     &lt;property name=&quot;userIdRetriever&quot; ref=&quot;userIdRetriever&quot; /&gt;
 *     &lt;property name=&quot;coderTypeDAO&quot; ref=&quot;coderTypeDAO&quot; /&gt;
 *     &lt;property name=&quot;timeZoneDAO&quot; ref=&quot;timeZoneDAO&quot; /&gt;
 *     &lt;property name=&quot;stateDAO&quot; ref=&quot;stateDAO&quot; /&gt;
 *     &lt;property name=&quot;demographicQuestionDAO&quot; ref=&quot;demographicQuestionDAO&quot; /&gt;
 *     &lt;property name=&quot;countryDAO&quot; ref=&quot;countryDAO&quot; /&gt;
 *   &lt;/bean&gt;
 * </pre>
 * Sample usage:
 * <pre>
 * // Create action via Spring
 * CompleteProfileAction action = ...;
 * // Get User profile
 * action.getUserProfile();
 * // action.getUser() will store retrieved user
 * // Save or update user with parameters set from JSP view
 * action.saveUserProfile();
 * // user will be store to back-end
 * </pre>
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe because it's mutable. However, dedicated instance of struts action will
 * be created by the struts framework to process the user request (and because the various DAO and UserIdRetriever are
 * all thread-safe), so the struts actions don't need to be thread-safe.
 * </p>
 * @author mekanizumu, sokol
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CompleteProfileAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    /**
     * <p>
     * Represents field validation error constant message.
     * </p>
     */
    private static final String FIELD_VALIDATION_ERROR_MESSAGE = "Error occurred while making field validation.";

    /**
     * <p>
     * Represents state field constant name.
     * </p>
     */
    private static final String STATE_FIELD_NAME = "state";

    /**
     * <p>
     * Represents state validate error property name.
     * </p>
     */
    private static final String STATE_VALIDATE_ERROR_PROPERTY_NAME = "state.invalid";

    /**
     * <p>
     * Represents fist phone number part pattern.
     * </p>
     */
    private static final Pattern FIRST_PHONE_NUMBER_PART_PATTERN = Pattern.compile("\\(\\+[0-9]+\\)");

    /**
     * <p>
     * Represents second phone number part pattern.
     * </p>
     */
    private static final Pattern SECOND_PHONE_NUMBER_PART_PATTERN = Pattern.compile("[0-9]+");

    /**
     * <p>
     * Represents third phone number part pattern.
     * </p>
     */
    private static final Pattern THIRD_PHONE_NUMBER_PART_PATTERN = Pattern.compile("[0-9]+\\-[0-9]+");

    /**
     * <p>
     * Represents phone number validate error property name.
     * </p>
     */
    private static final String PHONE_NUMBER_VALIDATE_ERROR_PROPERTY_NAME = "phoneNumber.invalid";

    /**
     * <p>
     * Represents phone number field constant name.
     * </p>
     */
    private static final String PHONE_NUMBER_FIELD_NAME = "phoneNumber";

    /**
     * <p>
     * The Log object used for logging.
     * </p>
     * <p>
     * LegalValue: It's a constant. So it can only be that constant value
     * </p>
     * <p>
     * Initialization and Mutability: It is final and won't change once it is initialized as part of variable
     * declaration to: LogManager.getLog(CompleteProfileAction.class.toString()).
     * </p>
     * <p>
     * Usage: It is used throughout the class wherever logging is needed.
     * </p>
     */
    private static final Log LOG = LogManager.getLog(CompleteProfileAction.class.toString());

    /**
     * <p>
     * Represents getUserProfile() method signature constant name.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "CompleteProfileAction.validate()";

    /**
     * <p>
     * Represents validation field error message prefix constant used for logging.
     * </p>
     */
    private static final String VALIDATION_FIELD_ERROR_MESSAGE_PREFIX =
            "Validation error occurs in method " + VALIDATE_METHOD_SIGNATURE + ": ";

    /**
     * <p>
     * Represents getUserProfile() method signature constant name.
     * </p>
     */
    private static final String GET_USER_PROFILE_METHOD_SIGNATURE = "CompleteProfileAction.getUserProfile()";

    /**
     * <p>
     * Represents saveUserProfile() method signature constant name.
     * </p>
     */
    private static final String SAVE_USER_PROFILE_METHOD_SIGNATURE = "CompleteProfileAction.saveUserProfile()";;

    /**
     * <p>
     * The UserDAO instance used to perform persistence operation on User. It is set through setter and doesn't have a
     * getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setUserDAO(), getUserProfile(), saveUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private UserDAO userDAO;

    /**
     * <p>
     * The AuditDAO instance used to perform auditing. It is set through setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setAuditDAO(), audit(). Its value legality is checked in checkConfiguration() method.
     * </p>
     */
    private AuditDAO auditDAO;

    /**
     * <p>
     * The UserIdRetriever used for retrieving user ID. It is set through setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getUserProfile(), setUserIdRetriever(), saveUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private UserIdRetriever userIdRetriever;

    /**
     * <p>
     * The StateDAO instance used to perform persistence operation on State. It is set through setter and doesn't have
     * a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setStateDAO(), validate(), saveUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private StateDAO stateDAO;

    /**
     * <p>
     * The CountryDAO instance used to perform persistence operation on Country. It is set through setter and doesn't
     * have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setCountryDAO(), saveUserProfile(). Its value legality is checked in checkConfiguration()
     * method.
     * </p>
     */
    private CountryDAO countryDAO;

    /**
     * <p>
     * The TimeZoneDAO instance used to perform persistence operation on TimeZone. It is set through setter and doesn't
     * have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setTimeZoneDAO(), saveUserProfile(). Its value legality is checked in checkConfiguration()
     * method.
     * </p>
     */
    private TimeZoneDAO timeZoneDAO;

    /**
     * <p>
     * The CoderTypeDAO instance used to perform persistence operation on CoderType. It is set through setter and
     * doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setCoderTypeDAO(), saveUserProfile(). Its value legality is checked in checkConfiguration()
     * method.
     * </p>
     */
    private CoderTypeDAO coderTypeDAO;

    /**
     * <p>
     * The DemographicQuestionDAO instance used to perform persistence operation on DemographicQuestion.
     * </p>
     * <p>
     * LegalValue: It cannot be null. (Note that the above statement applies only after the setter has been called as
     * part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setCoderTypeDAO(), saveUserProfile(). Its value legality is checked in checkConfiguration()
     * method.
     * </p>
     */
    private DemographicQuestionDAO demographicQuestionDAO;

    /**
     * <p>
     * The phone type ID that is used to set the property of phoneTypeId of the Phone entity of the user profile. It is
     * set through setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It must be non-negative. (Note that the above statement applies only after the setter has been
     * called as part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setPhoneTypeId(), saveUserProfile(). Its value legality is checked in checkConfiguration()
     * method.
     * </p>
     */
    private int phoneTypeId;

    /**
     * <p>
     * This flag denotes whether the user phone is the primary phone. It is set through setter and doesn't have a
     * getter.
     * </p>
     * <p>
     * LegalValue: Not Applicable
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setPrimary(), saveUserProfile().
     * </p>
     */
    private boolean primary;

    /**
     * <p>
     * The security key type ID that is used to set the property of securityKeyTypeId of the UserSecurityKey entity of
     * the user. It is set through setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It must be non-negative. (Note that the above statement applies only after the setter has been
     * called as part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setSecurityKeyTypeId(), saveUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private int securityKeyTypeId;

    /**
     * <p>
     * The operation type for the action of getting the user profile. This is for auditing purpose. It is set through
     * setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the setter has been
     * called as part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setGetUserProfileOperationType(), getUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private String getUserProfileOperationType;

    /**
     * <p>
     * The operation type for the action of saving the user profile. This is for auditing purpose. It is set through
     * setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the setter has been
     * called as part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setSaveUserProfileOperationType(), saveUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private String saveUserProfileOperationType;

    /**
     * <p>
     * The session key for retrieving the incoming request URI. It is set through setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null but can be empty. (Note that the above statement applies only after the setter has
     * been called as part of the IoC initialization. This field's value has no restriction before the IoC
     * initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setIncomingRequestURIKey(), saveUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private String incomingRequestURIKey;

    /**
     * <p>
     * The ID of the DemographicQuestion that asks about the gender of the user. It is set through setter and doesn't
     * have a getter.
     * </p>
     * <p>
     * LegalValue: It must be non-negative. (Note that the above statement applies only after the setter has been
     * called as part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setGenderQuestionId(), saveUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private long genderQuestionId;

    /**
     * <p>
     * The ID of the DemographicQuestion that asks about the age of the user. It is set through setter and doesn't have
     * a getter.
     * </p>
     * <p>
     * LegalValue: It must be non-negative. (Note that the above statement applies only after the setter has been
     * called as part of the IoC initialization. This field's value has no restriction before the IoC initialization)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setAgeQuestionId(), saveUserProfile(). Its value legality is checked in
     * checkConfiguration() method.
     * </p>
     */
    private long ageQuestionId;

    /**
     * <p>
     * The http servlet request. This is injected by Struts. It is set through setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null.
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in audit(), setServletRequest(), getUserProfile(), saveUserProfile().
     * </p>
     */
    private HttpServletRequest servletRequest;

    /**
     * <p>
     * The http servlet response. This is injected by Struts. It is set through setter and doesn't have a getter.
     * </p>
     * <p>
     * LegalValue: It cannot be null.
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setServletResponse(), saveUserProfile().
     * </p>
     */
    private HttpServletResponse servletResponse;

    /**
     * <p>
     * The user that is retrieved from the back end for displaying user profile. It is accessed through getter and
     * doesn't have a setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null.
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getUser(), audit(), getUserProfile(), saveUserProfile().
     * </p>
     */
    private User user;

    /**
     * <p>
     * The first name of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setFirstName(), getFirstName(), saveUserProfile().
     * </p>
     */
    private String firstName;

    /**
     * <p>
     * The last name of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setLastName(), getLastName(), saveUserProfile().
     * </p>
     */
    private String lastName;

    /**
     * <p>
     * The job title of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null but cannot be empty. (Note that the above statement applies only after the field has
     * passed Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getJobTitle(), setJobTitle(), saveUserProfile().
     * </p>
     */
    private String jobTitle;

    /**
     * <p>
     * The company name of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null but cannot be empty. (Note that the above statement applies only after the field has
     * passed Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setCompanyName(), getCompanyName(), saveUserProfile().
     * </p>
     */
    private String companyName;

    /**
     * <p>
     * The age of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getAge(), setAge(), saveUserProfile().
     * </p>
     */
    private String age;

    /**
     * <p>
     * The gender of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null but cannot be empty. (Note that the above statement applies only after the field has
     * passed Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setGender(), getGender(), saveUserProfile().
     * </p>
     */
    private String gender;

    /**
     * <p>
     * The line 1 of the user's address. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getCurrentAddress1(), setCurrentAddress1(), saveUserProfile().
     * </p>
     */
    private String currentAddress1;

    /**
     * <p>
     * The line 2 of the user's address. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getCurrentAddress2(), setCurrentAddress2(), saveUserProfile().
     * </p>
     */
    private String currentAddress2;

    /**
     * <p>
     * The line 3 of the user's address. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getCurrentAddress3(), setCurrentAddress3(), saveUserProfile().
     * </p>
     */
    private String currentAddress3;

    /**
     * <p>
     * The city of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setCity(), getCity(), saveUserProfile().
     * </p>
     */
    private String city;

    /**
     * <p>
     * The state of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getState(), setState(), validate(), saveUserProfile().
     * </p>
     */
    private String state;

    /**
     * <p>
     * The postal code of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setPostalCode(), getPostalCode(), saveUserProfile().
     * </p>
     */
    private String postalCode;

    /**
     * <p>
     * The province of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setProvince(), getProvince(), saveUserProfile().
     * </p>
     */
    private String province;

    /**
     * <p>
     * The country of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getCountry(), setCountry(), saveUserProfile().
     * </p>
     */
    private String country;

    /**
     * <p>
     * The country to represent. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null but cannot be empty. (Note that the above statement applies only after the field has
     * passed Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getCountryToRepresent(), setCountryToRepresent(), saveUserProfile().
     * </p>
     */
    private String countryToRepresent;

    /**
     * <p>
     * The phone number of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It cannot be null or empty. (Note that the above statement applies only after the field has passed
     * Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getPhoneNumber(), setPhoneNumber(), validate(), saveUserProfile().
     * </p>
     */
    private String phoneNumber;

    /**
     * <p>
     * The timezone of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null but cannot be empty. (Note that the above statement applies only after the field has
     * passed Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getTimezone(), setTimezone(), saveUserProfile().
     * </p>
     */
    private String timezone;

    /**
     * <p>
     * The ID of the coder type. This is used to suggest whether the user is student or professional. It has both
     * getter and setter.
     * </p>
     * <p>
     * LegalValue: It is nullable. It can be any value.
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in getCoderTypeId(), setCoderTypeId(), saveUserProfile().
     * </p>
     */
    private Integer coderTypeId;

    /**
     * <p>
     * The security key of the user. It has both getter and setter.
     * </p>
     * <p>
     * LegalValue: It can be null but cannot be empty. (Note that the above statement applies only after the field has
     * passed Struts validation. This field's value has no restriction before then.)
     * </p>
     * <p>
     * Initialization and Mutability: It does not need to be initialized when the instance is created.
     * </p>
     * <p>
     * Usage: It is used in setSecurityKey(), getSecurityKey(), saveUserProfile().
     * </p>
     */
    private String securityKey;

    /**
     * <p>
     * Creates an instance of CompleteProfileAction.
     * </p>
     */
    public CompleteProfileAction() {
    }

    /**
     * <p>
     * Executes the action logic of getting user profile.
     * </p>
     * @return SUCCESS if no error occurs, or LOGIN if the user ID is not available from userIdRetriever
     * @throws CompleteProfileActionException if any error occurs
     */
    public String getUserProfile() throws CompleteProfileActionException {
        LoggingWrapperUtility.logEntrance(LOG, GET_USER_PROFILE_METHOD_SIGNATURE, null, null);
        // Get the logged in user from database
        user = retrieveUser(GET_USER_PROFILE_METHOD_SIGNATURE, this.getUserProfileOperationType);
        if (user == null) {
            LoggingWrapperUtility.logExit(LOG, GET_USER_PROFILE_METHOD_SIGNATURE, new Object[] {LOGIN});
            return LOGIN;
        }
        LoggingWrapperUtility.logExit(LOG, GET_USER_PROFILE_METHOD_SIGNATURE, new Object[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Executes the action logic of saving the user profile.
     * </p>
     * @return null if user is successfully redirected to the requested URI, or LOGIN if user ID is not available from
     *         userIdRetriever
     * @throws CompleteProfileActionException if any error occurs
     */
    public String saveUserProfile() throws CompleteProfileActionException {
        LoggingWrapperUtility.logEntrance(LOG, SAVE_USER_PROFILE_METHOD_SIGNATURE, null, null);
        // Get the logged in user from database
        user = retrieveUser(SAVE_USER_PROFILE_METHOD_SIGNATURE, this.saveUserProfileOperationType);
        if (user == null) {
            LoggingWrapperUtility.logExit(LOG, SAVE_USER_PROFILE_METHOD_SIGNATURE, new Object[] {LOGIN});
            return LOGIN;
        }
        // Get the user profile:
        UserProfile userProfile = updateUserProfile();
        // update user contact
        updateUserContact(userProfile);
        // update user address
        updateUserAddress(userProfile);
        // update user phone number
        updateUserPhone(userProfile);
        // update user timezone
        updateUserTimezone(userProfile);
        // update user security key
        updateUserSecurityKey();
        // update user coder field
        updateCoder();
        // update gender
        updateDemographicResponse(userProfile, this.gender, this.genderQuestionId);
        // update age
        updateDemographicResponse(userProfile, this.age, this.ageQuestionId);
        // Save the user and the profile:
        try {
            userDAO.saveOrUpdate(user);
        } catch (RuntimeException e) {
            throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE, "Error occurred while saving user.", e);
        }
        redirectResponse();
        LoggingWrapperUtility.logExit(LOG, SAVE_USER_PROFILE_METHOD_SIGNATURE, new Object[] {null});
        return null;
    }

    /**
     * <p>
     * Updates user security key with new parameters.
     * </p>
     */
    private void updateUserSecurityKey() {
        if (securityKey != null) {
            // Get the existing UserSecurityKey:
            UserSecurityKey userSecurityKey = user.getUserSecurityKey();
            if (userSecurityKey == null) {
                userSecurityKey = new UserSecurityKey();
                // Set the user security key object to the user:
                user.setUserSecurityKey(userSecurityKey);
            }
            // Set the user association:
            userSecurityKey.setUser(user);
            // Set the security key type ID:
            userSecurityKey.setSecurityKeyTypeId(securityKeyTypeId);
            // Set the security key:
            userSecurityKey.setSecurityKey(securityKey);
        }
    }

    /**
     * <p>
     * Updates user profile timezone with new parameters.
     * </p>
     * @param userProfile the user profile for update
     * @throws CompleteProfileActionException if any error occurs
     */
    private void updateUserTimezone(UserProfile userProfile) throws CompleteProfileActionException {
        if (timezone != null) {
            try {
                // Find the TimeZone entity by the timezone string:
                TimeZone timeZoneEntity = timeZoneDAO.find(timezone);
                checkExist(timeZoneEntity, "timeZoneEntity", timezone, SAVE_USER_PROFILE_METHOD_SIGNATURE);
                // Set the time zone:
                userProfile.setTimeZone(timeZoneEntity);
            } catch (RuntimeException e) {
                throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE,
                        "Error occurred while retrieving timezone for ID='" + timezone + "'.", e);
            }
        }
    }

    /**
     * <p>
     * Updates user profile address with parameters.
     * </p>
     * @param userProfile the user profile for update
     * @throws CompleteProfileActionException if any error occurs
     */
    private void updateUserAddress(UserProfile userProfile) throws CompleteProfileActionException {
        Address address;
        if (userProfile.getAddresses() != null && userProfile.getAddresses().size() > 0) {
            // Get the existing Address object:
            address = userProfile.getAddresses().iterator().next();
        } else {
            address = new Address();
            if (userProfile.getAddresses() == null) {
                userProfile.setAddresses(new HashSet < Address >());
            }
            // Add the address to user profile:
            userProfile.addAddress(address);
        }
        // This block populates the address object
        // Set the address line 1:
        address.setAddress1(currentAddress1);
        // Set the address line 2:
        address.setAddress1(currentAddress2);
        // Set the address line 3:
        address.setAddress1(currentAddress3);
        // Set the city:
        address.setCity(city);
        if (checkNotNullNorEmpty(state)) {
            try {
                // Get the state entity by state code:
                State stateEntity = stateDAO.find(state);
                checkExist(stateEntity, "stateEntity", state, SAVE_USER_PROFILE_METHOD_SIGNATURE);
                // Set the state:
                address.setState(stateEntity);
            } catch (RuntimeException e) {
                throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE,
                        "Error occurred while retrieving state for ID='" + state + "'.", e);
            }
        }
        // Set the postal code:
        address.setPostalCode(postalCode);
        // Set the province:
        address.setProvince(province);
        // Set the country:
        address.setCountry(getCountry(country, "country"));
    }

    /**
     * <p>
     * Updates user profile with first name and last name.
     * </p>
     * @return updated user profile with first name and last name
     */
    private UserProfile updateUserProfile() {
        UserProfile userProfile = user.getUserProfile();
        if (userProfile == null) {
            // Create a new profile:
            userProfile = new UserProfile();
            // Set the user profile to the user:
            user.setUserProfile(userProfile);
        }
        // Set the first name:
        userProfile.setFirstName(firstName);
        // Set the last name:
        userProfile.setLastName(lastName);
        return userProfile;
    }

    /**
     * <p>
     * Updates user contact with parameters.
     * </p>
     * @param userProfile the user profile for update
     */
    private void updateUserContact(UserProfile userProfile) {
        if (jobTitle != null) {
            // Get the contact from userProfile:
            Contact contact = userProfile.getContact();
            if (contact == null) {
                contact = new Contact();
                // Set the contact to the user profile:
                userProfile.setContact(contact);
                // Set the user:
                contact.setUser(user);
            }
            // Set the title:
            contact.setTitle(jobTitle);
            // Get the company from contact:
            Company company = contact.getCompany();
            if (company == null) {
                company = new Company();
                // Set the company to the contact:
                contact.setCompany(company);
            }
            // Set the company name:
            company.setName(companyName);
            // Set the primary contact:
            company.setPrimaryContact(contact);
        }
    }

    /**
     * <p>
     * Updates user phone with parameters.
     * </p>
     * @param userProfile the user profile to update
     */
    private void updateUserPhone(UserProfile userProfile) {
        Phone phoneEntity;
        if (userProfile.getPhoneNumbers() != null && userProfile.getPhoneNumbers().size() > 0) {
            // Get the existing Phone object:
            phoneEntity = userProfile.getPhoneNumbers().iterator().next();
        } else {
            phoneEntity = new Phone();
            if (userProfile.getPhoneNumbers() == null) {
                userProfile.setPhoneNumbers(new HashSet < Phone >());
            }
            // Add the phone to the user profile:
            userProfile.addPhoneNumber(phoneEntity);
        }
        // This block populates the phone entity
        phoneEntity.setUser(user);
        phoneEntity.setNumber(phoneNumber);
        phoneEntity.setPhoneTypeId(phoneTypeId);
        phoneEntity.setPrimary(primary);
    }

    /**
     * <p>
     * Updates coder's type info and country to represent.
     * </p>
     * @throws CompleteProfileActionException if any error occurs while updating coder
     */
    private void updateCoder() throws CompleteProfileActionException {
        if (this.coderTypeId != null || this.countryToRepresent != null) {
            if (user.getCoder() == null) {
                throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE,
                        "User's coder field should not be null.", null);
            }
        }
        if (this.coderTypeId != null) {
            try {
                CoderType coderType = coderTypeDAO.find(coderTypeId);
                // check if coderType exists
                checkExist(coderType, "coderType", coderTypeId, SAVE_USER_PROFILE_METHOD_SIGNATURE);
                user.getCoder().setCoderType(coderType);
            } catch (RuntimeException e) {
                throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE,
                        "Error occurred while retrieving coder type for coderTypeId=" + coderTypeId + ".", e);
            }
        }
        if (this.countryToRepresent != null) {
            // Get the country entity by country name:
            user.getCoder().setCompCountry(getCountry(countryToRepresent, "countryToRepresent"));
        }
    }

    /**
     * <p>
     * Retrieves Country instance for given country code.
     * </p>
     * @param countryCode the country code
     * @param fieldName the field name for logging
     * @return retrieved Country instance for given country code
     * @throws CompleteProfileActionException if any error occurs
     */
    private Country getCountry(String countryCode, String fieldName) throws CompleteProfileActionException {
        try {
            Country countryEntity = countryDAO.find(countryCode);
            // check if country exists
            checkExist(countryEntity, fieldName, countryCode, SAVE_USER_PROFILE_METHOD_SIGNATURE);
            return countryEntity;
        } catch (RuntimeException e) {
            throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE,
                    "Error occurred while retrieving country for countryCode='" + countryCode + "'.", e);
        }
    }

    /**
     * <p>
     * Redirects servlet response to session attribute for key incomingRequestURIKey.
     * </p>
     * @throws CompleteProfileActionException if any error occurs or incomingRequestURIKey is missing in session or
     *             object for incomingRequestURIKey in session is not string
     */
    private void redirectResponse() throws CompleteProfileActionException {
        // Get the session from servletRequest:
        HttpSession session = servletRequest.getSession();
        // Get the incoming request URI from session:
        Object obj = session.getAttribute(incomingRequestURIKey);
        // check session attribute values
        if (obj == null) {
            throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE, "Servlet session attribute '"
                    + incomingRequestURIKey + "' for incomingRequestURIKey is missing.", null);
        }
        if (!(obj instanceof String)) {
            throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE, "Servlet session attribute value for key '"
                    + incomingRequestURIKey + "' is not string.", null);
        }
        String incomingRequestURI = (String) obj;
        // Redirect to the requested URI:
        try {
            servletResponse.sendRedirect(incomingRequestURI);
        } catch (IOException e) {
            throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE,
                    "Error occurred while redirecting servlet response.", e);
        }
    }

    /**
     * <p>
     * Updates given user profile demographic response for given question ID and answer.
     * </p>
     * @param userProfile the user profile to be updated
     * @param answerText the answer text
     * @param questionId the question ID
     * @throws CompleteProfileActionException if question is not found
     */
    private void updateDemographicResponse(UserProfile userProfile, String answerText, long questionId)
        throws CompleteProfileActionException {
        if (answerText != null) {
            try {
                // check if demographic responses exist
                if (userProfile.getDemographicResponses() == null) {
                    userProfile.setDemographicResponses(new HashSet < DemographicResponse >());
                }
                // Get the question:
                DemographicQuestion question = this.demographicQuestionDAO.find(questionId);
                // check if question is found
                checkExist(question, "question", questionId, SAVE_USER_PROFILE_METHOD_SIGNATURE);
                // flag whether answer exist for given answerText
                boolean foundAnswer = false;
                for (Object obj : question.getAnswers()) {
                    DemographicAnswer answer = (DemographicAnswer) obj;
                    if (answer.getText().equals(answerText)) {
                        foundAnswer = true;
                        DemographicResponse response = new DemographicResponse();
                        // Set the user:
                        response.setUser(user);
                        // Set the answer:
                        response.setAnswer(answer);
                        // Set the question:
                        response.setQuestion(question);
                        // Set identifier:
                        response.setId(new DemographicResponse.Identifier(user, question, answer));
                        // Add to the user profile:
                        userProfile.addDemographicResponse(response);
                    }
                }
                // check if answer found
                if (!foundAnswer) {
                    throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE,
                            "There is no demographic answer for demographic question ID=" + questionId
                                    + " and answer text='" + answerText + "'.", null);
                }
            } catch (RuntimeException e) {
                throw logAndWrapException(SAVE_USER_PROFILE_METHOD_SIGNATURE,
                        "Error occurred while retrieving demographic question for ID=" + questionId + "'.", e);
            }
        }
    }

    /**
     * <p>
     * Checks whether given object is null.
     * </p>
     * @param obj the object value to check
     * @param objectName the object name
     * @param objectId the object ID
     * @param methodSignature the method signature for logging
     * @throws CompleteProfileActionException if given object is null
     */
    private void checkExist(Object obj, String objectName, Object objectId, String methodSignature)
        throws CompleteProfileActionException {
        if (obj == null) {
            throw logAndWrapException(methodSignature, objectName + " for ID='" + objectId
                    + "' is not found in database.", null);
        }
    }

    /**
     * <p>
     * Retrieves logged in user from database.
     * </p>
     * @param methodSignature the method signature for logging
     * @param operationType the operation type for auditing
     * @return User instance retrieved from database, or null if user is not logged in
     * @throws CompleteProfileActionException if user is not found in database
     */
    private User retrieveUser(String methodSignature, String operationType) throws CompleteProfileActionException {
        Long userId = userIdRetriever.getUserId(servletRequest);
        if (userId == null) {
            return null;
        }
        try {
            // Get the User object by ID:
            user = userDAO.find(userId);
        } catch (RuntimeException e) {
            throw logAndWrapException(methodSignature, "Error occurred while retrieving user for id=" + userId + ".",
                    e);
        }
        // check if user exists
        checkExist(user, "user", userId, methodSignature);
        // Perform auditing:
        audit(user, operationType);
        return user;
    }

    /**
     * <p>
     * Performs auditing for the current user.
     * </p>
     * @param operationType the operation type
     * @param user the current user
     * @throws CompleteProfileActionException if any error occurs
     */
    private void audit(User user, String operationType) {
        // create a new AuditRecord:
        AuditRecord auditRecord = new AuditRecord();
        // populates the auditRecord
        auditRecord.setHandle(user.getHandle());
        auditRecord.setIpAddress(servletRequest.getRemoteAddr());
        auditRecord.setTimestamp(new Date());
        auditRecord.setOperationType(operationType);
        // persist the auditRecord:
        auditDAO.audit(auditRecord);
    }

    /**
     * <p>
     * Validates the input parameters programmatically.
     * </p>
     * <p>
     * If any error is caught, it will be logged, added to action errors and immediately returned.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(LOG, VALIDATE_METHOD_SIGNATURE, null, null);
        if (phoneNumber != null) {
            // check phone number to match pattern: (+xxx) xxx xxx-xxxx
            String[] parts = phoneNumber.trim().split(" ");
            // check if it has exactly 3 parts and any phone number part has invalid value
            if (parts.length != 3
                    || !(FIRST_PHONE_NUMBER_PART_PATTERN.matcher(parts[0]).matches()
                            && SECOND_PHONE_NUMBER_PART_PATTERN.matcher(parts[1]).matches()
                            && THIRD_PHONE_NUMBER_PART_PATTERN.matcher(parts[2]).matches())) {
                logFieldError(PHONE_NUMBER_FIELD_NAME, PHONE_NUMBER_VALIDATE_ERROR_PROPERTY_NAME);
            }
        }
        if (checkNotNullNorEmpty(state)) {
            // check valid state
            try {
                if (stateDAO.find(state) == null) {
                    // add a field error suggesting that the state is invalid
                    logFieldError(STATE_FIELD_NAME, STATE_VALIDATE_ERROR_PROPERTY_NAME);
                }
            } catch (RuntimeException e) {
                logAndWrapException(VALIDATE_METHOD_SIGNATURE, FIELD_VALIDATION_ERROR_MESSAGE, e);
                this.addActionError(FIELD_VALIDATION_ERROR_MESSAGE);
                return;
            }
        }
        LoggingWrapperUtility.logExit(LOG, VALIDATE_METHOD_SIGNATURE, null);
    }

    /**
     * <p>
     * Checks whether given value is null or empty.
     * </p>
     * @return true, if given value is not null or empty, false otherwise
     */
    private boolean checkNotNullNorEmpty(String value) {
        return value != null && value.trim().length() > 0;
    }

    /**
     * <p>
     * Logs and wraps given exception.
     * </p>
     * @param methodSignature the method signature for logging
     * @param errorMessage the error message
     * @param cause the error cause
     * @return wrapped exception by CompleteProfileActionException instance
     */
    private CompleteProfileActionException logAndWrapException(String methodSignature, String errorMessage,
            Throwable cause) {
        Throwable exceptionToLog = cause;
        CompleteProfileActionException result = null;
        if (cause == null) {
            // cause is null, log wrapped exception
            result = new CompleteProfileActionException(errorMessage);
            exceptionToLog = result;
        } else {
            // cause is not null, log original exception
            result = new CompleteProfileActionException(errorMessage, cause);
        }
        LoggingWrapperUtility.logException(LOG, methodSignature, exceptionToLog);
        return result;
    }

    /**
     * <p>
     * Logs field error with given values.
     * </p>
     * @param fieldName the field name for adding error
     * @param fieldErrorMessagePropertyKey the field error message property key in resources properties file
     */
    private void logFieldError(String fieldName, String fieldErrorMessagePropertyKey) {
        String errorMessage = getText(fieldErrorMessagePropertyKey);
        this.addFieldError(fieldName, errorMessage);
        LOG.log(Level.ERROR, VALIDATION_FIELD_ERROR_MESSAGE_PREFIX + errorMessage);
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getAge() {
        return age;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getGender() {
        return gender;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getCurrentAddress1() {
        return currentAddress1;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getCurrentAddress2() {
        return currentAddress2;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getCurrentAddress3() {
        return currentAddress3;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getProvince() {
        return province;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getCountryToRepresent() {
        return countryToRepresent;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public Integer getCoderTypeId() {
        return coderTypeId;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getSecurityKey() {
        return securityKey;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getState() {
        return state;
    }

    /**
     * <p>
     * Retrieves the namesake instance variable.
     * </p>
     * @return the namesake instance variable
     */
    public String getCountry() {
        return country;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param auditDAO The AuditDAO instance used to perform auditing.
     */
    public void setAuditDAO(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param servletRequest The http servlet request. This is injected by Struts.
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param getUserProfileOperationType The operation type for the action of getting the user profile. This is for
     *            auditing purpose.
     */
    public void setGetUserProfileOperationType(String getUserProfileOperationType) {
        this.getUserProfileOperationType = getUserProfileOperationType;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param saveUserProfileOperationType The operation type for the action of saving the user profile. This is for
     *            auditing purpose.
     */
    public void setSaveUserProfileOperationType(String saveUserProfileOperationType) {
        this.saveUserProfileOperationType = saveUserProfileOperationType;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param userDAO The UserDAO instance used to perform persistence operation on User.
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param userIdRetriever The UserIdRetriever used for retrieving user ID.
     */
    public void setUserIdRetriever(UserIdRetriever userIdRetriever) {
        this.userIdRetriever = userIdRetriever;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param stateDAO The StateDAO instance used to perform persistence operation on State.
     */
    public void setStateDAO(StateDAO stateDAO) {
        this.stateDAO = stateDAO;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param countryDAO The CountryDAO instance used to perform persistence operation on Country.
     */
    public void setCountryDAO(CountryDAO countryDAO) {
        this.countryDAO = countryDAO;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param timeZoneDAO The TimeZoneDAO instance used to perform persistence operation on TimeZone.
     */
    public void setTimeZoneDAO(TimeZoneDAO timeZoneDAO) {
        this.timeZoneDAO = timeZoneDAO;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param phoneTypeId The phone type ID that is used to set the property of phoneTypeId of the Phone entity of the
     *            user profile.
     */
    public void setPhoneTypeId(int phoneTypeId) {
        this.phoneTypeId = phoneTypeId;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param securityKeyTypeId The security key type ID that is used to set the property of securityKeyTypeId of the
     *            UserSecurityKey entity of the user.
     */
    public void setSecurityKeyTypeId(int securityKeyTypeId) {
        this.securityKeyTypeId = securityKeyTypeId;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param incomingRequestURIKey The session key for retrieving the incoming request URI.
     */
    public void setIncomingRequestURIKey(String incomingRequestURIKey) {
        this.incomingRequestURIKey = incomingRequestURIKey;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param servletResponse The http servlet response. This is injected by Struts.
     */
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param firstName The first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param lastName The last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param jobTitle The job title of the user.
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param companyName The company name of the user.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param age The age of the user.
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param gender The gender of the user.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param currentAddress1 The line 1 of the user's address.
     */
    public void setCurrentAddress1(String currentAddress1) {
        this.currentAddress1 = currentAddress1;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param currentAddress2 The line 2 of the user's address.
     */
    public void setCurrentAddress2(String currentAddress2) {
        this.currentAddress2 = currentAddress2;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param currentAddress3 The line 3 of the user's address.
     */
    public void setCurrentAddress3(String currentAddress3) {
        this.currentAddress3 = currentAddress3;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param city The city of the user.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param postalCode The postal code of the user.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param province The province of the user.
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param country The country of the user.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param countryToRepresent The country to represent.
     */
    public void setCountryToRepresent(String countryToRepresent) {
        this.countryToRepresent = countryToRepresent;
    }

    /**
     * Sets the the namesake instance variable.
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param phoneNumber The phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param timezone The timezone of the user.
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param coderTypeId The ID of the coder type. This is used to suggest whether the user is student or
     *            professional.
     */
    public void setCoderTypeId(Integer coderTypeId) {
        this.coderTypeId = coderTypeId;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param securityKey The security key of the user.
     */
    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param primary This flag denotes whether the user phone is the primary phone.
     */
    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param coderTypeDAO The CoderTypeDAO instance used to perform persistence operation on CoderType.
     */
    public void setCoderTypeDAO(CoderTypeDAO coderTypeDAO) {
        this.coderTypeDAO = coderTypeDAO;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param genderQuestionId The ID of the DemographicQuestion that asks about the gender of the user.
     */
    public void setGenderQuestionId(long genderQuestionId) {
        this.genderQuestionId = genderQuestionId;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param ageQuestionId The ID of the DemographicQuestion that asks about the age of the user.
     */
    public void setAgeQuestionId(long ageQuestionId) {
        this.ageQuestionId = ageQuestionId;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param state The state of the user.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * <p>
     * Sets the the namesake instance variable.
     * </p>
     * <p>
     * Simply assign the value to the instance variable.
     * </p>
     * @param demographicQuestionDAO The DemographicQuestionDAO instance used to perform persistence operation on
     *            DemographicQuestion.
     */
    public void setDemographicQuestionDAO(DemographicQuestionDAO demographicQuestionDAO) {
        this.demographicQuestionDAO = demographicQuestionDAO;
    }

    /**
     * <p>
     * Checks if the injected values are valid.
     * </p>
     * <p>
     * This method is called right after the dependency of this class is fully injected.
     * </p>
     * @throws CompleteProfileActionConfigurationException if any of the injected values is invalid
     */
    public void checkConfiguration() {
        // check fields
        ValidationUtility.checkNotNull(userDAO, "userDAO", CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(auditDAO, "auditDAO", CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(userIdRetriever, "userIdRetriever",
                CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(stateDAO, "stateDAO", CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(countryDAO, "countryDAO", CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(timeZoneDAO, "timeZoneDAO", CompleteProfileActionConfigurationException.class);
        ValidationUtility
                .checkNotNull(coderTypeDAO, "coderTypeDAO", CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNull(demographicQuestionDAO, "demographicQuestionDAO",
                CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNegative(phoneTypeId, "phoneTypeId",
                CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNegative(securityKeyTypeId, "securityKeyTypeId",
                CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNegative(genderQuestionId, "genderQuestionId",
                CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNegative(ageQuestionId, "ageQuestionId",
                CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(getUserProfileOperationType,
                "getUserProfileOperationType", CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(incomingRequestURIKey, "incomingRequestURIKey",
                CompleteProfileActionConfigurationException.class);
        ValidationUtility.checkNotNullNorEmptyAfterTrimming(saveUserProfileOperationType,
                "saveUserProfileOperationType", CompleteProfileActionConfigurationException.class);
    }
}

/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.registration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import org.hibernate.HibernateException;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.randomstringimg.InvalidConfigException;
import com.topcoder.randomstringimg.ObfuscationException;
import com.topcoder.randomstringimg.RandomStringImage;
import com.topcoder.shared.security.User;
import com.topcoder.util.log.Level;
import com.topcoder.util.spell.ConfigException;
import com.topcoder.web.common.TCWebException;
import com.topcoder.web.common.security.WebAuthentication;
import com.topcoder.web.reg.RegistrationActionConfigurationException;
import com.topcoder.web.tc.Constants;

/**
 * <p>
 * This action will generate a CAPTCHA word image with the RandomStringImage from the Random String Image
 * component, store it in a local file, and provide the word and the image to the result.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * <p>
 * <b>Sample Configuration:</b>
 *
 * <pre>
 *     &lt;bean id="preCreateAccountAction"
 *      class="com.topcoder.web.reg.actions.registration.PreCreateAccountAction"
 *         init-method="checkInitialization"&gt;
 *         &lt;property name="logger" ref="logger" /&gt;
 *         &lt;property name="userDAO" ref="userDAO" /&gt;
 *         &lt;property name="auditDAO" ref="auditDAO" /&gt;
 *         &lt;property name="registrationTypeDAO" ref="registrationTypeDAO" /&gt;
 *         &lt;property name="servletRequest" ref="servletRequest" /&gt;
 *         &lt;property name="authenticationSessionKey" value="webAuthentication" /&gt;
 *         &lt;property name="captchaImageGenerator" ref="randomStringImage" /&gt;
 *
 *         &lt;property name="session"&gt;
 *             &lt;map&gt;
 *                 &lt;entry key="webAuthentication"&gt;
 *                     &lt;ref bean="webAuthentication" /&gt;
 *                 &lt;/entry&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * </p>
 *
 * @author argolite, stevenfrog
 * @version 1.0
 */
public class PreCreateAccountAction extends BaseRegistrationAction {
    /**
     * <p>
     * Represent the class name.
     * </p>
     */
    private static final String CLASS_NAME = PreCreateAccountAction.class.getName();

    /**
     * <p>
     * This is the RandomStringImage instance used to generate the CAPTCHA image. It is set in the setter. It
     * can be retrieved in the getter. It may have any value. This field will be injected by the container
     * (expected to be done only once), and is not expected to change afterward.
     * </p>
     */
    private RandomStringImage captchaImageGenerator;

    /**
     * <p>
     * Represents the name of the file where the CAPTCHA word image is stored after it is generated. It is set
     * in the setter. It can be retrieved in the getter. It may have any value. This field will be set by the
     * execute method.
     * </p>
     */
    private String capfname;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public PreCreateAccountAction() {
        // Empty
    }

    /**
     * <p>
     * This method checks that all required values have been injected by the system right after construction
     * and injection. This is used to obviate the need to check these values each time in the execute method.
     * </p>
     *
     * @throws RegistrationActionConfigurationException
     *             If any required value has not been injected into this class
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();
        ValidationUtility.checkNotNull(captchaImageGenerator, "captchaImageGenerator",
            RegistrationActionConfigurationException.class);
    }

    /**
     * <p>
     * This action will generate a CAPTCHA word image, store it in a local file, and provide the word and the
     * image to the result.
     * </p>
     *
     * @return a string representing the logical result of the execution.
     * @throws TCWebException
     *             If there are any errors in the action
     */
    @Override
    public String execute() throws TCWebException {
        // Log the entry
        final String signature = CLASS_NAME + ".execute()";
        LoggingWrapperUtility.logEntrance(getLogger(), signature, null, null);

        OutputStream fos = null;
        try {
            // Get authentication object
            WebAuthentication authentication = getAuthentication();
            // Get user
            User activeUser = authentication.getActiveUser();
            // generate filename
            capfname = activeUser.getId() + "_" + System.currentTimeMillis() + ".png";
            // Open a file output stream
            fos = new FileOutputStream(Constants.CAPTCHA_PATH + capfname);
            // Generate word:
            String word = captchaImageGenerator.generateRandomFromDictionaries(fos);
            // Add word to session:
            getSession().put(Constants.CAPTCHA_WORD, word);

            // Audit user action:
            auditAction("pre create account", activeUser.getUserName());

            // Log the response
            getLogger().log(Level.DEBUG, "The response is [capfname:" + capfname + ", word:" + word + "]");
            // Log the exit
            LoggingWrapperUtility.logExit(getLogger(), signature, new Object[] {SUCCESS});

            return SUCCESS;
        } catch (TCWebException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, e);
        } catch (IOException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "IOException occurs while generating random word and image", e));
        } catch (ObfuscationException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "ObfuscationException occurs while generating random word and image", e));
        } catch (InvalidConfigException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "InvalidConfigException occurs while generating random word and image", e));
        } catch (ConfigException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "ConfigException occurs while generating random word and image", e));
        } catch (HibernateException e) {
            throw LoggingWrapperUtility.logException(getLogger(), signature, new TCWebException(
                "HibernateException occurs while accessing to database", e));
        } finally {
            // Close stream
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // Just log it
                    LoggingWrapperUtility.logException(getLogger(), signature, e);
                }
            }
        }
    }

    /**
     * <p>
     * Getter method for captchaImageGenerator, simply return the value of the namesake field.
     * </p>
     *
     * @return the captchaImageGenerator
     */
    public RandomStringImage getCaptchaImageGenerator() {
        return captchaImageGenerator;
    }

    /**
     * <p>
     * Setter method for the captchaImageGenerator, simply set the value to the namesake field.
     * </p>
     *
     * @param captchaImageGenerator
     *            the captchaImageGenerator to set
     */
    public void setCaptchaImageGenerator(RandomStringImage captchaImageGenerator) {
        this.captchaImageGenerator = captchaImageGenerator;
    }

    /**
     * <p>
     * Getter method for capfname, simply return the value of the namesake field.
     * </p>
     *
     * @return the capfname
     */
    public String getCapfname() {
        return capfname;
    }

    /**
     * <p>
     * Setter method for the capfname, simply set the value to the namesake field.
     * </p>
     *
     * @param capfname
     *            the capfname to set
     */
    public void setCapfname(String capfname) {
        this.capfname = capfname;
    }

}

/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.failuretests.impl;

import java.io.File;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.registration.service.RegistrationServiceConfigurationException;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.service.impl.RegistrationServicesImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

/**
 * Failure tests for RegistrationServicesImpl.
 * @author liulike
 * @version 1.0
 */
public class RegistrationServicesImplFailureTest extends TestCase {

    /**
     * The base path for config files.
     */
    private static String basePath = "FailureTests" + File.separator;

    /**
     * The RegistrationServicesImpl instance used in test.
     */
    private RegistrationServicesImpl instance;

    /**
     * The RegistrationInfoImpl instance used in test.
     */
    private RegistrationInfoImpl registrationInfo;

    /**
     * Set up the test environment.
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        tearDown();
        registrationInfo = new RegistrationInfoImpl(2, 2, 2);

    }

    /**
     * Tear down the test environment.
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        registrationInfo = null;
        ConfigManager configManager = ConfigManager.getInstance();

        Iterator iter = configManager.getAllNamespaces();

        while (iter.hasNext()) {
            configManager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid1() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid1.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid2() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid2.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid3() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid3.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid4() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid4.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid5() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid5.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid6() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid6.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid7() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid7.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid8() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid8.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid9() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid9.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid10() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid10.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid11() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid11.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid12() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid12.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid13() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid13.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }

    /**
     * Failure tests for RegistrationServicesImpl(String),
     * it tests the case that the configuration is invalid,
     * RegistrationServiceConfigurationException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegistrationServicesImplString_invalid14() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "invalid14.xml");
        try{
            new RegistrationServicesImpl();
            fail("RegistrationServiceConfigurationException is expected");
        }catch(RegistrationServiceConfigurationException e){
            // good
        }
    }


    /**
     * Failure test for registerForProject(RegistrationInfo),
     * it tests the case that the parameter is null,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegisterForProject_Invalid1() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        try{
            instance.registerForProject(null);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for registerForProject(RegistrationInfo),
     * it tests the case that the parameter contains negative id.,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegisterForProject_Invalid2() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();

        registrationInfo = new RegistrationInfoImpl();
        registrationInfo.setProjectId(1);
        registrationInfo.setUserId(1);
        try{
            instance.registerForProject(registrationInfo);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for registerForProject(RegistrationInfo),
     * it tests the case that the parameter contains negative id.,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegisterForProject_Invalid3() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();

        registrationInfo = new RegistrationInfoImpl();
        registrationInfo.setRoleId(1);
        registrationInfo.setUserId(1);
        try{
            instance.registerForProject(registrationInfo);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for registerForProject(RegistrationInfo),
     * it tests the case that the parameter contains negative id.,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRegisterForProject_Invalid4() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();

        registrationInfo = new RegistrationInfoImpl();
        registrationInfo.setProjectId(1);
        registrationInfo.setRoleId(1);
        try{
            instance.registerForProject(registrationInfo);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }


    /**
     * Failure test for validateRegistration(RegistrationInfo),
     * it tests the case that the parameter is null,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testValidateRegistration_invalid1() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        try{
            instance.validateRegistration(null);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for validateRegistration(RegistrationInfo),
     * it tests the case that the parameter contains negative id,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testValidateRegistration_invalid2() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        registrationInfo = new RegistrationInfoImpl();
        registrationInfo.setProjectId(1);
        registrationInfo.setUserId(1);
        try{
            instance.validateRegistration(registrationInfo);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for validateRegistration(RegistrationInfo),
     * it tests the case that the parameter contains negative id,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testValidateRegistration_invalid3() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();

        registrationInfo = new RegistrationInfoImpl();
        registrationInfo.setRoleId(1);
        registrationInfo.setUserId(1);
        try{
            instance.validateRegistration(registrationInfo);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for validateRegistration(RegistrationInfo),
     * it tests the case that the parameter contains negative id,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testValidateRegistration_invalid4() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();

        registrationInfo = new RegistrationInfoImpl();
        registrationInfo.setProjectId(1);
        registrationInfo.setRoleId(1);
        try{
            instance.validateRegistration(registrationInfo);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for getRegistration(long, long),
     * it tests the case that the parameter is negative,
     * and the IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testGetRegistration_invalid1() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        try{
            instance.getRegistration(-1, 1);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for getRegistration(long, long),
     * it tests the case that the parameter is negative,
     * and the IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testGetRegistration_invalid2() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        try{
            instance.getRegistration(1, -1);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure tests for findAvailableRegistrationPositions(ProjectCategory),
     * it tests the case that the parameter is null,
     * and the IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testFindAvailableRegistrationPositions() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        try{
            instance.findAvailableRegistrationPositions(null);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure tests for removeRegistration(RegistrationInfo, int),
     * it tests the case that the parameter is null,
     * and the IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRemoveRegistration_invalid1() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        try{
            instance.removeRegistration(null, 1);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure tests for removeRegistration(RegistrationInfo, int),
     * it tests the case that the RegistrationInfo contains nagetive id,
     * and the IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRemoveRegistration_invalid2() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        registrationInfo = new RegistrationInfoImpl();
        try{
            instance.removeRegistration(registrationInfo, 1);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure tests for removeRegistration(RegistrationInfo, int),
     * it tests the case that the parameter is nagetive,
     * and the IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testRemoveRegistration_invalid3() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        registrationInfo = new RegistrationInfoImpl(1, 1, 1);
        try{
            instance.removeRegistration(registrationInfo, -1);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for getRegisteredResources(long),
     * it tests the case that the parameter is nagetive,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testGetRegisteredResources() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        try{
            instance.getRegisteredResources(-1);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * Failure test for getRegisteredProjects(long),
     * it tests the case that the parameter is nagetive,
     * and IllegalArgumentException is expected.
     * @throws ConfigManagerException to JUnit
     *
     */
    public void testGetRegisteredProjects() throws ConfigManagerException {
        ConfigManager.getInstance().add(basePath + "valid.xml");
        instance = new RegistrationServicesImpl();
        try{
            instance.getRegisteredProjects(-1);
            fail("IllegalArgumentException is expected");
        }catch(IllegalArgumentException iae){
            // good
        }
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all test cases.
     */
    public static Test suite() {
        return new TestSuite(RegistrationServicesImplFailureTest.class);
    }

}

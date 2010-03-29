/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Linq;
using System.ServiceModel;
using NUnit.Framework;
using TopCoder.Configuration;
using TopCoder.Services.WCF;
using Toro.TurfGuard.Common.Core.Domain;
using Toro.TurfGuard.Common.Core.Domain.Impl;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.Common.Core.Services;
using Toro.TurfGuard.Common.Core.Services.Impl;

namespace Toro.TurfGuard.WebService.Impl
{
    /// <summary>
    /// Unit test for <see cref="DeviceService"/> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    [CoverageExclude]
    public class DeviceServiceUnitTest
    {
        /// <summary>
        /// Represents the backup web.config filename. It's copied from the original web.config file.
        /// </summary>
        private const string BACKUP_WEB_CONFIG = "../../test_files/web.config.bkup";

        /// <summary>
        /// Represents the original web.config filename.
        /// </summary>
        private const string ORIG_WEB_CONFIG = "../../test_files/web.config";

        /// <summary>
        /// Represents <see cref="DeviceService"/> instance used for tests.
        /// </summary>
        private DeviceService instance;

        /// <summary>
        /// The data set used for tests.
        /// </summary>
        private DataSet dataSet;

        /// <summary>
        /// Restores the web.config to its initial state.
        /// </summary>
        private static void RestoreWebConfigFile()
        {
            // if the backup web.config file exists, restore it to original web.config
            if (File.Exists(BACKUP_WEB_CONFIG))
            {
                // restore the backup web.config to the original web.config
                File.Copy(BACKUP_WEB_CONFIG, ORIG_WEB_CONFIG, true);

                // delete the backup web.config
                File.Delete(BACKUP_WEB_CONFIG);
            }
        }

        /// <summary>
        /// Replaces <paramref name="oldVal"/> with <paramref name="newVal"/> in the web.config for
        /// unit testing purposes.
        /// </summary>
        ///
        /// <param name="oldVal">The original string value.</param>
        ///
        /// <param name="newVal">The value to replace <paramref name="oldVal"/> with.</param>
        private static void ModifyWebConfigFile(string oldVal, string newVal)
        {
            // get the web.config back to the original state if necessary
            RestoreWebConfigFile();

            // make a backup of the original web.config
            File.Copy(ORIG_WEB_CONFIG, BACKUP_WEB_CONFIG);

            // get the contents of the web.config
            string contents = File.ReadAllText(ORIG_WEB_CONFIG);

            // replace the value
            contents = contents.Replace(oldVal, newVal);

            // delete the original web.config
            File.Delete(ORIG_WEB_CONFIG);

            // write out the changes to the web.config
            File.WriteAllText(ORIG_WEB_CONFIG, contents);
        }

        /// <summary>
        /// Cleans up the environment after all tests in this file have completed.
        /// </summary>
        [TestFixtureTearDown]
        protected void TestFixtureTearDown()
        {
            UnitTestHelper.DeleteLogFile();
        }

        /// <summary>
        /// Setup environment for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            // get the web.config back to its original state
            RestoreWebConfigFile();

            // prepare mock data used for tests
            dataSet = UnitTestHelper.PrepareMockData();

            // create instance used for tests
            instance = new DeviceService();
        }

        /// <summary>
        /// Tear down environment after each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            // get the web.config back to its original state
            RestoreWebConfigFile();

            // reset mock data used for tests
            UnitTestHelper.ResetMockData();
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService"/> constructor.
        /// </para>
        ///
        /// <para>
        /// The instance should be created successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(instance, "DeviceService instance should be initialized.");

            // make sure inheritance is correct
            UnitTestHelper.AssertType<IDeviceService>(instance);
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing default configuration.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingDefaultConfiguration()
        {
            // remove the default configuration from web.config
            string text = File.ReadAllText(ORIG_WEB_CONFIG);
            int start = text.IndexOf("<defaultConfiguration>");
            int end = text.LastIndexOf("</defaultConfiguration>") + "</defaultConfiguration>".Length;
            string toRemove = text.Substring(start, end - start);
            ModifyWebConfigFile(toRemove, "");

            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing class configuration.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingClassConfiguration()
        {
            // remove the class configuration from web.config
            string text = File.ReadAllText(ORIG_WEB_CONFIG);
            int start = text.IndexOf("<children>");
            int end = text.LastIndexOf("</children>") + "</children>".Length;
            string toRemove = text.Substring(start, end - start);
            ModifyWebConfigFile(toRemove, "<children></children>");

            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing logger configuration.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingLoggerConfiguration()
        {
            ModifyWebConfigFile("logging_wrapper_configuration", "fakeConfig");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with invalid logger configuration.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithInvalidLoggerConfiguration()
        {
            ModifyWebConfigFile("log4net.config", "fail.config");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing object factory configuration.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingObjectFactoryConfiguration()
        {
            ModifyWebConfigFile("object_factory_configuration", "fakeConfig");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing <c>user_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingUserRepositoryKey()
        {
            ModifyWebConfigFile("user_repository_key", "FakeKey");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with empty <c>user_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithEmptyUserRepositoryKey()
        {
            ModifyWebConfigFile(
                "name=\"user_repository_key\" value=\"user_repository_key\"",
                "name=\"user_repository_key\" value=\" \"");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing <c>authentication_service_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingAuthenticationServiceKey()
        {
            ModifyWebConfigFile("authentication_service_key", "FakeKey");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with empty <c>authentication_service_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithEmptyAuthenticationServiceKey()
        {
            ModifyWebConfigFile(
                "name=\"authentication_service_key\" value=\"authentication_service_key\"",
                "name=\"authentication_service_key\" value=\" \"");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing <c>device_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingDeviceRepositoryKey()
        {
            ModifyWebConfigFile("device_repository_key", "FakeKey");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with empty <c>device_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithEmptyDeviceRepositoryKey()
        {
            ModifyWebConfigFile(
                "name=\"device_repository_key\" value=\"device_repository_key\"",
                "name=\"device_repository_key\" value=\" \"");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing <c>device_group_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingDeviceGroupRepositoryKey()
        {
            ModifyWebConfigFile("device_group_repository_key", "FakeKey");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with empty <c>device_group_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithEmptyDeviceGroupRepositoryKey()
        {
            ModifyWebConfigFile(
                "name=\"device_group_repository_key\" value=\"device_group_repository_key\"",
                "name=\"device_group_repository_key\" value=\" \"");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing <c>device_install_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingDeviceInstallRepositoryKey()
        {
            ModifyWebConfigFile("device_install_repository_key", "FakeKey");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with empty <c>device_install_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithEmptyDeviceInstallRepositoryKey()
        {
            ModifyWebConfigFile(
                "name=\"device_install_repository_key\" value=\"device_install_repository_key\"",
                "name=\"device_install_repository_key\" value=\" \"");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing <c>reading_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingReadingRepositoryKey()
        {
            ModifyWebConfigFile("reading_repository_key", "FakeKey");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with empty <c>reading_repository_key</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithEmptyReadingRepositoryKey()
        {
            ModifyWebConfigFile(
                "name=\"reading_repository_key\" value=\"reading_repository_key\"",
                "name=\"reading_repository_key\" value=\" \"");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with missing <c>xsd_directory</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithMissingXsdDirectory()
        {
            ModifyWebConfigFile("xsd_directory", "FakeKey");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with empty <c>xsd_directory</c>.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithEmptyXsdDirectory()
        {
            const string DIR = @"..\..\test_files";
            ModifyWebConfigFile(
                "name=\"xsd_directory\" value=\"" + DIR + "\"",
                "name=\"xsd_directory\" value=\" \"");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with failure to create the <see cref="IUserRepository"/> object.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithFailureToCreateUserRepository()
        {
            ModifyWebConfigFile(
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockUserRepository",
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockAuthenticationService");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with failure to create the <see cref="IAuthenticationService"/> object.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithFailureToCreateAuthenticationService()
        {
            ModifyWebConfigFile(
                "Toro.TurfGuard.Common.Core.Services.Impl.MockAuthenticationService",
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockUserRepository");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with failure to create the <see cref="IDeviceRepository"/> object.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithFailureToCreateDeviceRepository()
        {
            ModifyWebConfigFile(
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockDeviceRepository",
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockUserRepository");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with failure to create the <see cref="IDeviceGroupRepository"/> object.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithFailureToCreateDeviceGroupRepository()
        {
            ModifyWebConfigFile(
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockDeviceGroupRepository",
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockUserRepository");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with failure to create the <see cref="IDeviceInstallRepository"/> object.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithFailureToCreateDeviceInstallRepository()
        {
            ModifyWebConfigFile(
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockDeviceInstallRepository",
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockUserRepository");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with failure to create the <see cref="IReadingRepository"/> object.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithFailureToCreateReadingRepository()
        {
            ModifyWebConfigFile(
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockReadingRepository",
                "Toro.TurfGuard.Common.Core.Domain.Impl.MockUserRepository");
            new DeviceService();
        }

        /// <summary>
        /// <para>Test the failure of the <see cref="DeviceService()"/> constructor
        /// with failure to load xsd file.</para>
        ///
        /// <para><see cref="DeviceServiceConfigurationException"/> is expected to be thrown.</para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestCtor1WithFailureToLoadXsdFile()
        {
            ModifyWebConfigFile(
                "name=\"xsd_directory\" value=\"..\\..\\test_files\"",
                "name=\"xsd_directory\" value=\"..\\..\\test_files\\fake\"");
            new DeviceService();
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.AddDevice"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The device should be added to the repository successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestAddDevice()
        {
            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            // make sure device was added to repository
            Assert.IsTrue(MockDeviceRepository.Repository.ContainsKey(1), "The device should have been added.");
            Assert.AreEqual("100", MockDeviceRepository.Repository[1].SerialNumber, "The SerialNumber is wrong.");
            Assert.AreEqual(1, MockDeviceRepository.Repository[1].NodeId, "The NodeID is wrong.");
            Assert.AreEqual(1, MockDeviceRepository.Repository[1].Site.Id, "The Site.ID is wrong.");
            Assert.AreEqual(1, MockDeviceRepository.Repository[1].GetDeviceInstalls().Length,
                "The device install should have been added to device.");
            Assert.AreEqual(14, MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].Latitude,
                "The DeviceInstall.Latitude is wrong.");
            Assert.AreEqual(15, MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].Longitude,
                "The DeviceInstall.Longitude is wrong.");
            Assert.AreEqual(new DateTime(2010, 01, 01),
                MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].InstallationDate,
                "The DeviceInstall.InstallationDate is wrong.");

            Assert.IsNotNull(MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].PrimaryGroup,
                "The DeviceInstall.PrimaryGroup shouldn't be null.");
            Assert.AreEqual(1, MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].PrimaryGroup.Id,
                "The DeviceInstall.PrimaryGroup.Id is wrong.");
            Assert.AreEqual("1", MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].PrimaryGroup.Name,
                "The DeviceInstall.PrimaryGroup.Name is wrong.");

            Assert.IsNotNull(MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].SecondaryGroup,
                "The DeviceInstall.SecondaryGroup shouldn't be null.");
            Assert.AreEqual(-1, MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].SecondaryGroup.Id,
                "The DeviceInstall.SecondaryGroup.Id is wrong.");
            Assert.AreEqual("-1", MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].SecondaryGroup.Name,
                "The DeviceInstall.SecondaryGroup.Name is wrong.");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with null username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithNullUsername()
        {
            instance.AddDevice(null, "x", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with empty username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithEmptyUsername()
        {
            instance.AddDevice("\t \n", "x", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with null password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithNullPassword()
        {
            instance.AddDevice("a", null, 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with empty password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithEmptyPassword()
        {
            instance.AddDevice("a", "\t \n", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with null input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithNullInput()
        {
            instance.AddDevice("x", "x", 1, null);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with empty input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithEmptyInput()
        {
            instance.AddDevice("x", "x", 1, "\t \n");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with non XML input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithNonXmlInput()
        {
            instance.AddDevice("user1", "password1", 1, "fake");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with bad password for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithBadPassword()
        {
            instance.AddDevice("user1", "bad", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with invalid site for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithInvalidSite()
        {
            instance.AddDevice("user1", "password1", 500, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with missing sensor info row.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithMissingSensorInfoRow()
        {
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Clear();
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with multiple sensor info rows.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithMultipleSensorInfoRows()
        {
            DataRow row = dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].NewRow();
            row[UnitTestHelper.COLUMN_HOLE_NUMBER] = 1;
            row[UnitTestHelper.COLUMN_LOCATION_TYPE] = -1;
            row[UnitTestHelper.COLUMN_X] = 14;
            row[UnitTestHelper.COLUMN_Y] = 15;
            row[UnitTestHelper.COLUMN_NODE_ID] = 1;
            row[UnitTestHelper.COLUMN_SERIAL_NUMBER] = 100;
            row[UnitTestHelper.COLUMN_IN_SERVICE] = new DateTime(2010, 01, 01);
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Add(row);

            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with missing primary group.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithMissingPrimaryGroup()
        {
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 100;
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with missing secondary group.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithMissingSecondaryGroup()
        {
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = 100;
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with missing X value.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithMissingXValue()
        {
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_X] = DBNull.Value;
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with missing Y value.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithMissingYValue()
        {
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_Y] = DBNull.Value;
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with missing serial number value.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithMissingSerialNumberValue()
        {
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_SERIAL_NUMBER]
                = DBNull.Value;
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDevice"/>
        /// method with failure to get site.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceWithFailToGetSite()
        {
            MockUserRepository.ThrowException = true;
            MockUserRepository.CallingMethodForThrowException = "GetSite";
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.UpdateDevice"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The device should be updated in the repository successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestUpdateDevice()
        {
            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            // make sure device was added to repository
            Assert.IsTrue(MockDeviceRepository.Repository.ContainsKey(1), "The device should have been added.");

            // prepare for update
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 2;
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = -2;
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_IN_SERVICE] =
                new DateTime(2012, 1, 1);
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_X] = 150;
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_Y] = 160;
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_SERIAL_NUMBER] = "200";

            // update the device
            instance.UpdateDevice("user1", "password1", 2, dataSet.GetXml());

            // make sure device was updated in repository
            Assert.IsTrue(MockDeviceRepository.Repository.ContainsKey(1), "The device should have been updated.");
            Assert.AreEqual("200", MockDeviceRepository.Repository[1].SerialNumber, "The SerialNumber is wrong.");
            Assert.AreEqual(1, MockDeviceRepository.Repository[1].NodeId, "The NodeID is wrong.");
            Assert.AreEqual(2, MockDeviceRepository.Repository[1].Site.Id, "The Site.ID is wrong.");
            Assert.AreEqual(1, MockDeviceRepository.Repository[1].GetDeviceInstalls().Length,
                "The device install should have been updated in device.");
            Assert.AreEqual(150, MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].Latitude,
                "The DeviceInstall.Latitude is wrong.");
            Assert.AreEqual(160, MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].Longitude,
                "The DeviceInstall.Longitude is wrong.");
            Assert.AreEqual(new DateTime(2012, 01, 01),
                MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].InstallationDate,
                "The DeviceInstall.InstallationDate is wrong.");

            Assert.IsNotNull(MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].PrimaryGroup,
                "The DeviceInstall.PrimaryGroup shouldn't be null.");
            Assert.AreEqual(2, MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].PrimaryGroup.Id,
                "The DeviceInstall.PrimaryGroup.Id is wrong.");
            Assert.AreEqual("2", MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].PrimaryGroup.Name,
                "The DeviceInstall.PrimaryGroup.Name is wrong.");

            Assert.IsNotNull(MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].SecondaryGroup,
                "The DeviceInstall.SecondaryGroup shouldn't be null.");
            Assert.AreEqual(-2, MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].SecondaryGroup.Id,
                "The DeviceInstall.SecondaryGroup.Id is wrong.");
            Assert.AreEqual("-2", MockDeviceRepository.Repository[1].GetDeviceInstalls()[0].SecondaryGroup.Name,
                "The DeviceInstall.SecondaryGroup.Name is wrong.");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with null username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithNullUsername()
        {
            instance.UpdateDevice(null, "x", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with empty username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithEmptyUsername()
        {
            instance.UpdateDevice("\t \n", "x", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with null password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithNullPassword()
        {
            instance.UpdateDevice("a", null, 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with empty password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithEmptyPassword()
        {
            instance.UpdateDevice("a", "\t \n", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with null input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithNullInput()
        {
            instance.UpdateDevice("x", "x", 1, null);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with empty input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithEmptyInput()
        {
            instance.UpdateDevice("x", "x", 1, "\t \n");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with non XML input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithNonXmlInput()
        {
            instance.UpdateDevice("user1", "password1", 1, "fake");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with bad password for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithBadPassword()
        {
            instance.UpdateDevice("user1", "bad", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with invalid site for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithInvalidSite()
        {
            instance.UpdateDevice("user1", "password1", 500, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with missing sensor info row.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithMissingSensorInfoRow()
        {
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Clear();
            instance.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with multiple sensor info rows.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithMultipleSensorInfoRows()
        {
            DataRow row = dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].NewRow();
            row[UnitTestHelper.COLUMN_HOLE_NUMBER] = 1;
            row[UnitTestHelper.COLUMN_LOCATION_TYPE] = -1;
            row[UnitTestHelper.COLUMN_X] = 14;
            row[UnitTestHelper.COLUMN_Y] = 15;
            row[UnitTestHelper.COLUMN_NODE_ID] = 1;
            row[UnitTestHelper.COLUMN_SERIAL_NUMBER] = 100;
            row[UnitTestHelper.COLUMN_IN_SERVICE] = new DateTime(2010, 01, 01);
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Add(row);

            instance.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with missing primary group.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithMissingPrimaryGroup()
        {
            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 100;
            instance.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with missing secondary group.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithMissingSecondaryGroup()
        {
            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = 100;
            instance.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with missing current device install.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithMissingCurrentDeviceInstall()
        {
            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            MockDeviceInstallRepository.Repository.Clear();
            instance.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDevice"/>
        /// method with missing device.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceWithMissingDevice()
        {
            instance.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.GetAllDevices"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The devices should be retrieved successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestGetAllDevices()
        {
            // add devices, 2 devices for site 1 and 1 device for site 2
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_NODE_ID] = 2;
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_NODE_ID] = 3;
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 2;
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = -2;
            instance.AddDevice("user1", "password1", 2, dataSet.GetXml());

            // fetch devices for site 1
            string xml = instance.GetAllDevices("user1", "password1", 1);

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            DataSet resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // make sure devices were retrieved successfully
            Assert.AreEqual(2, resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Count,
                "Wrong number of sensor info rows found.");
            Assert.AreEqual(2,
                resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Select(
                    UnitTestHelper.COLUMN_NODE_ID + " = 1 or " +
                    UnitTestHelper.COLUMN_NODE_ID + " = 2").Length,
                "Wrong rows were fetched, node id doesn't match.");
            Assert.AreEqual(2,
                resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Select(
                    UnitTestHelper.COLUMN_X + " = 14 ").Length,
                "Wrong rows were fetched, X doesn't match.");
            Assert.AreEqual(2,
                resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Select(
                    UnitTestHelper.COLUMN_Y + " = 15 ").Length,
                "Wrong rows were fetched, Y doesn't match.");
            Assert.AreEqual(2,
                resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Select(
                    UnitTestHelper.COLUMN_SERIAL_NUMBER + " = '100' ").Length,
                "Wrong rows were fetched, serial number doesn't match.");
            Assert.IsTrue(
                "2010-01-01" ==
                Convert.ToDateTime(
                    resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_IN_SERVICE]).
                    ToString("yyyy-MM-dd") &&
                "2010-01-01" ==
                Convert.ToDateTime(
                    resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[1][UnitTestHelper.COLUMN_IN_SERVICE]).
                    ToString("yyyy-MM-dd"),
                "Wrong rows were fetched, in service doesn't match.");

            // this test should return no devices
            MockDeviceRepository.Repository.Clear();
            xml = instance.GetAllDevices("user1", "password1", 1);

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // make sure no records were returned
            Assert.AreEqual(0, resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Count,
                "Wrong number of sensor info rows found.");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDevices"/>
        /// method with null username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDevicesWithNullUsername()
        {
            instance.GetAllDevices(null, "x", 1);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDevices"/>
        /// method with empty username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDevicesWithEmptyUsername()
        {
            instance.GetAllDevices("\t \n", "x", 1);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDevices"/>
        /// method with null password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDevicesWithNullPassword()
        {
            instance.GetAllDevices("a", null, 1);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDevices"/>
        /// method with empty password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDevicesWithEmptyPassword()
        {
            instance.GetAllDevices("a", "\t \n", 1);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDevices"/>
        /// method with bad password for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDevicesWithBadPassword()
        {
            instance.GetAllDevices("user1", "bad", 1);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDevices"/>
        /// method with missing current device install.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDevicesWithInvalidSite()
        {
            instance.GetAllDevices("user1", "password1", 500);
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The device group should be added to the repository successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestAddDeviceGroup()
        {
            // add device group
            MockDeviceGroupRepository.Repository.Clear();
            instance.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

            // make sure device group was added to repository
            Assert.IsTrue(MockDeviceGroupRepository.Repository.ContainsKey("loc1"),
                "The device group should have been added.");
            Assert.AreEqual(DeviceGroup.PRIMARY, MockDeviceGroupRepository.Repository["loc1"].DeviceGroupType,
                "The device group type is wrong.");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with null username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithNullUsername()
        {
            instance.AddDeviceGroup(null, "x", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with empty username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithEmptyUsername()
        {
            instance.AddDeviceGroup("\t \n", "x", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with null password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithNullPassword()
        {
            instance.AddDeviceGroup("a", null, 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with empty password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithEmptyPassword()
        {
            instance.AddDeviceGroup("a", "\t \n", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with null input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithNullInput()
        {
            instance.AddDeviceGroup("x", "x", 1, null);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with empty input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithEmptyInput()
        {
            instance.AddDeviceGroup("x", "x", 1, "\t \n");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with non XML input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithNonXmlInput()
        {
            instance.AddDeviceGroup("user1", "password1", 1, "fake");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with bad password for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithBadPassword()
        {
            instance.AddDeviceGroup("user1", "bad", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with invalid site for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithInvalidSite()
        {
            instance.AddDeviceGroup("user1", "password1", 500, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with missing location names row.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithMissingLocationNamesRow()
        {
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();
            instance.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with multiple location names rows.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithMultipleLocationNamesRow()
        {
            DataRow row = dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].NewRow();
            row[UnitTestHelper.COLUMN_NAME] = "loc1";
            row[UnitTestHelper.COLUMN_ID] = 1;
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Add(row);

            instance.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with missing name value.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithMissingNameValue()
        {
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME] = DBNull.Value;
            instance.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AddDeviceGroup"/>
        /// method with empty name value.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestAddDeviceGroupWithEmptyNameValue()
        {
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME] = "\t \n";
            instance.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The device group should be updated in the repository successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestUpdateDeviceGroup()
        {
            // add device group
            MockDeviceGroupRepository.Repository.Clear();
            MockDeviceGroupRepository.DeviceGroupsForSite.Clear();
            instance.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

            // make sure device group was added to repository
            Assert.IsTrue(MockDeviceGroupRepository.Repository.ContainsKey("loc1"),
                "The device group should have been added.");

            // prepare for update
            MockDeviceGroupRepository.DeviceGroupsForSite[1] = new List<DeviceGroup>();
            MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(MockDeviceGroupRepository.Repository["loc1"]);
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "loc1";
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME] = "loc2";

            // update device group
            instance.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());

            // make sure device group was updated in repository
            Assert.IsTrue(MockDeviceGroupRepository.Repository.ContainsKey("loc2"),
                "The device group should have been updated.");
            Assert.AreEqual("loc2", MockDeviceGroupRepository.Repository["loc2"].Name,
                "The name is wrong.");
            Assert.AreEqual(DeviceGroup.PRIMARY, MockDeviceGroupRepository.Repository["loc2"].DeviceGroupType,
                "The device group type is wrong.");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with null username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithNullUsername()
        {
            instance.UpdateDeviceGroup(null, "x", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with empty username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithEmptyUsername()
        {
            instance.UpdateDeviceGroup("\t \n", "x", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with null password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithNullPassword()
        {
            instance.UpdateDeviceGroup("a", null, 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with empty password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithEmptyPassword()
        {
            instance.UpdateDeviceGroup("a", "\t \n", 1, "a");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with null input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithNullInput()
        {
            instance.UpdateDeviceGroup("x", "x", 1, null);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with empty input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithEmptyInput()
        {
            instance.UpdateDeviceGroup("x", "x", 1, "\t \n");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with non XML input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithNonXmlInput()
        {
            instance.UpdateDeviceGroup("user1", "password1", 1, "fake");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with bad password for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithBadPassword()
        {
            instance.UpdateDeviceGroup("user1", "bad", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with invalid site for user.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithInvalidSite()
        {
            instance.UpdateDeviceGroup("user1", "password1", 500, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with missing location names row.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithMissingLocationNamesRow()
        {
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();
            instance.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with multiple location names rows.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithMultipleLocationNamesRow()
        {
            DataRow row = dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].NewRow();
            row[UnitTestHelper.COLUMN_NAME] = "loc1";
            row[UnitTestHelper.COLUMN_ID] = 1;
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Add(row);

            instance.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with missing device group.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithMissingDeviceGroup()
        {
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "fake";
            instance.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with missing name.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithMissingName()
        {
            // add device group
            MockDeviceGroupRepository.Repository.Clear();
            MockDeviceGroupRepository.DeviceGroupsForSite.Clear();
            instance.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

            // make sure device group was added to repository
            Assert.IsTrue(MockDeviceGroupRepository.Repository.ContainsKey("loc1"),
                "The device group should have been added.");

            // prepare for update
            MockDeviceGroupRepository.DeviceGroupsForSite[1] = new List<DeviceGroup>();
            MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(MockDeviceGroupRepository.Repository["loc1"]);
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "loc1";
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME] = DBNull.Value;
            instance.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.UpdateDeviceGroup"/>
        /// method with empty name.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestUpdateDeviceGroupWithEmptyName()
        {
            // add device group
            MockDeviceGroupRepository.Repository.Clear();
            MockDeviceGroupRepository.DeviceGroupsForSite.Clear();
            instance.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

            // make sure device group was added to repository
            Assert.IsTrue(MockDeviceGroupRepository.Repository.ContainsKey("loc1"),
                "The device group should have been added.");

            // prepare for update
            MockDeviceGroupRepository.DeviceGroupsForSite[1] = new List<DeviceGroup>();
            MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(MockDeviceGroupRepository.Repository["loc1"]);
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "loc1";
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME] = "\t \n";
            instance.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.GetAllDeviceGroups"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The device groups should be retrieved successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestGetAllDeviceGroups()
        {
            // get the primary device groups for site 1
            string xml = instance.GetAllDeviceGroups("user1", "password1", 1, DeviceGroup.PRIMARY);

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            DataSet resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // validate the result
            Assert.AreEqual(1, resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Count,
                "Wrong number of location names rows fetched.");
            Assert.AreEqual("1",
                resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME],
                "The name is wrong.");
            Assert.AreEqual(1,
                resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_ID],
                "The ID is wrong.");

            // get the secondary device groups for site 2
            xml = instance.GetAllDeviceGroups("user1", "password1", 2, DeviceGroup.SECONDARY);

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // validate the result
            Assert.AreEqual(1, resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Count,
                "Wrong number of location names rows fetched.");
            Assert.AreEqual("-2",
                resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME],
                "The name is wrong.");
            Assert.AreEqual(-2,
                resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_ID],
                "The ID is wrong.");

            // this test should return no site groups
            xml = instance.GetAllDeviceGroups("user1", "password1", 3, DeviceGroup.SECONDARY);

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // validate the result
            Assert.AreEqual(0, resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Count,
                "Wrong number of location names rows fetched.");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDeviceGroups"/>
        /// method with null username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDeviceGroupsWithNullUsername()
        {
            instance.GetAllDeviceGroups(null, "x", 1, DeviceGroup.PRIMARY);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDeviceGroups"/>
        /// method with empty username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDeviceGroupsWithEmptyUsername()
        {
            instance.GetAllDeviceGroups("\t \n", "x", 1, DeviceGroup.PRIMARY);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDeviceGroups"/>
        /// method with null password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDeviceGroupsWithNullPassword()
        {
            instance.GetAllDeviceGroups("a", null, 1, DeviceGroup.PRIMARY);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetAllDeviceGroups"/>
        /// method with empty password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetAllDeviceGroupsWithEmptyPassword()
        {
            instance.GetAllDeviceGroups("a", "\t \n", 1, DeviceGroup.PRIMARY);
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.GetDeviceReading"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The device readings should be retrieved successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestGetDeviceReading()
        {
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.RemoveAt(0);

            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            // ------------------------
            // get real time readings
            // ------------------------
            string xml = instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "RealTime");

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            DataSet resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // validate the result
            Assert.AreEqual(2, resultDataSet.Tables[UnitTestHelper.TABLE_MINUTELY_READINGS].Rows.Count,
                "Wrong number of rows returned.");
            var rows =
                resultDataSet.Tables[UnitTestHelper.TABLE_MINUTELY_READINGS].AsEnumerable().OrderBy(
                    r => r[UnitTestHelper.COLUMN_TIME]).ToArray();
            Assert.AreEqual("2010-01-01 12:01:00",
                Convert.ToDateTime(rows[0][UnitTestHelper.COLUMN_TIME]).ToString("yyyy-MM-dd hh:mm:ss"),
                "The reading date is wrong.");
            Assert.AreEqual(1, rows[0][UnitTestHelper.COLUMN_TEMP], "Temp reading is wrong.");
            Assert.AreEqual(2, rows[0][UnitTestHelper.COLUMN_SALT], "Salt reading is wrong.");
            Assert.AreEqual(3, rows[0][UnitTestHelper.COLUMN_PERCENTAGE], "Percentage reading is wrong.");

            Assert.AreEqual("2010-01-01 12:02:00",
                Convert.ToDateTime(rows[1][UnitTestHelper.COLUMN_TIME]).ToString("yyyy-MM-dd hh:mm:ss"),
                "The reading date is wrong.");
            Assert.AreEqual(4, rows[1][UnitTestHelper.COLUMN_TEMP], "Temp reading is wrong.");
            Assert.AreEqual(5, rows[1][UnitTestHelper.COLUMN_SALT], "Salt reading is wrong.");
            Assert.AreEqual(6, rows[1][UnitTestHelper.COLUMN_PERCENTAGE], "Percentage reading is wrong.");

            // ---------------------
            // get hourly readings
            // ---------------------
            xml = instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 1, 0, 0), new DateTime(2010, 1, 1, 2, 0, 0), "Hourly");

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // validate the result
            Assert.AreEqual(2, resultDataSet.Tables[UnitTestHelper.TABLE_HOURLY_READINGS].Rows.Count,
                "Wrong number of rows returned.");
            rows =
                resultDataSet.Tables[UnitTestHelper.TABLE_HOURLY_READINGS].AsEnumerable().OrderBy(
                    r => r[UnitTestHelper.COLUMN_TIME]).ToArray();
            Assert.AreEqual("2010-01-01 01:00:00",
                Convert.ToDateTime(rows[0][UnitTestHelper.COLUMN_TIME]).ToString("yyyy-MM-dd hh:mm:ss"),
                "The reading date is wrong.");
            Assert.AreEqual(7, rows[0][UnitTestHelper.COLUMN_TEMP], "Temp reading is wrong.");
            Assert.AreEqual(8, rows[0][UnitTestHelper.COLUMN_SALT], "Salt reading is wrong.");
            Assert.AreEqual(9, rows[0][UnitTestHelper.COLUMN_PERCENTAGE], "Percentage reading is wrong.");

            Assert.AreEqual("2010-01-01 02:00:00",
                Convert.ToDateTime(rows[1][UnitTestHelper.COLUMN_TIME]).ToString("yyyy-MM-dd hh:mm:ss"),
                "The reading date is wrong.");
            Assert.AreEqual(10, rows[1][UnitTestHelper.COLUMN_TEMP], "Temp reading is wrong.");
            Assert.AreEqual(11, rows[1][UnitTestHelper.COLUMN_SALT], "Salt reading is wrong.");
            Assert.AreEqual(12, rows[1][UnitTestHelper.COLUMN_PERCENTAGE], "Percentage reading is wrong.");

            // -------------------
            // get daily readings
            // -------------------
            xml = instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 2, 1, 0, 0, 0), new DateTime(2010, 3, 1, 0, 0, 0), "Daily");

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // validate the result
            Assert.AreEqual(2, resultDataSet.Tables[UnitTestHelper.TABLE_MULTI_HOURLY_READINGS].Rows.Count,
                "Wrong number of rows returned.");
            rows =
                resultDataSet.Tables[UnitTestHelper.TABLE_MULTI_HOURLY_READINGS].AsEnumerable().OrderBy(
                    r => r[UnitTestHelper.COLUMN_TIME]).ToArray();
            Assert.AreEqual("2010-02-01 12:00:00",
                Convert.ToDateTime(rows[0][UnitTestHelper.COLUMN_TIME]).ToString("yyyy-MM-dd hh:mm:ss"),
                "The reading date is wrong.");
            Assert.AreEqual(13, rows[0][UnitTestHelper.COLUMN_TEMP], "Temp reading is wrong.");
            Assert.AreEqual(14, rows[0][UnitTestHelper.COLUMN_SALT], "Salt reading is wrong.");
            Assert.AreEqual(15, rows[0][UnitTestHelper.COLUMN_PERCENTAGE], "Percentage reading is wrong.");

            Assert.AreEqual("2010-03-01 12:00:00",
                Convert.ToDateTime(rows[1][UnitTestHelper.COLUMN_TIME]).ToString("yyyy-MM-dd hh:mm:ss"),
                "The reading date is wrong.");
            Assert.AreEqual(16, rows[1][UnitTestHelper.COLUMN_TEMP], "Temp reading is wrong.");
            Assert.AreEqual(17, rows[1][UnitTestHelper.COLUMN_SALT], "Salt reading is wrong.");
            Assert.AreEqual(18, rows[1][UnitTestHelper.COLUMN_PERCENTAGE], "Percentage reading is wrong.");

            // ---------------------------------------------
            // get real time readings, but use device group
            // ---------------------------------------------
            dataSet = UnitTestHelper.PrepareMockData();

            // clear sensor info row so that location names row (corresponds to device group) will be used
            // when we call the GetDeviceReading method
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Clear();
            xml = instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "RealTime");

            Assert.IsNotNull(xml, "Returned value should not be null.");

            // parse the result
            resultDataSet = UnitTestHelper.ParseResultSet(xml);

            // validate the result
            Assert.AreEqual(2, resultDataSet.Tables[UnitTestHelper.TABLE_MINUTELY_READINGS].Rows.Count,
                "Wrong number of rows returned.");
            rows =
                resultDataSet.Tables[UnitTestHelper.TABLE_MINUTELY_READINGS].AsEnumerable().OrderBy(
                    r => r[UnitTestHelper.COLUMN_TIME]).ToArray();
            Assert.AreEqual("2010-01-01 12:01:00",
                Convert.ToDateTime(rows[0][UnitTestHelper.COLUMN_TIME]).ToString("yyyy-MM-dd hh:mm:ss"),
                "The reading date is wrong.");
            Assert.AreEqual(1, rows[0][UnitTestHelper.COLUMN_TEMP], "Temp reading is wrong.");
            Assert.AreEqual(2, rows[0][UnitTestHelper.COLUMN_SALT], "Salt reading is wrong.");
            Assert.AreEqual(3, rows[0][UnitTestHelper.COLUMN_PERCENTAGE], "Percentage reading is wrong.");

            Assert.AreEqual("2010-01-01 12:02:00",
                Convert.ToDateTime(rows[1][UnitTestHelper.COLUMN_TIME]).ToString("yyyy-MM-dd hh:mm:ss"),
                "The reading date is wrong.");
            Assert.AreEqual(4, rows[1][UnitTestHelper.COLUMN_TEMP], "Temp reading is wrong.");
            Assert.AreEqual(5, rows[1][UnitTestHelper.COLUMN_SALT], "Salt reading is wrong.");
            Assert.AreEqual(6, rows[1][UnitTestHelper.COLUMN_PERCENTAGE], "Percentage reading is wrong.");

        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with null username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithNullUsername()
        {
            instance.GetDeviceReading(null, "x", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "Daily");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with empty username.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithEmptyUsername()
        {
            instance.GetDeviceReading("\t \n", "x", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "Daily");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with null password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithNullPassword()
        {
            instance.GetDeviceReading("a", null, 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "Daily");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with empty password.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithEmptyPassword()
        {
            instance.GetDeviceReading("a", "\t \n", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "Daily");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with null input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithNullInput()
        {
            instance.GetDeviceReading("a", "b", 1, null, 1, DateTime.Now, DateTime.Now, "Daily");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with empty input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithEmptyInput()
        {
            instance.GetDeviceReading("a", "b", 1, "\t \n", 1, DateTime.Now, DateTime.Now, "Daily");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with invalid XML input.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithInvalidXmlInput()
        {
            instance.GetDeviceReading("user1", "password1", 1, "fake", 1, DateTime.Now, DateTime.Now, "Daily");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with start date greater than end date.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithStartDateGreaterThanEndDate()
        {
            instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1, DateTime.Now,
                DateTime.Now.AddDays(-5), "Daily");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with null scale.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithNullScale()
        {
            instance.GetDeviceReading("a", "b", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, null);
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with empty scale.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithEmptyScale()
        {
            instance.GetDeviceReading("a", "b", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "\t \n");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with invalid scale.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithInvalidScale()
        {
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Clear();
            instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "fake");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with wrong tables in input XML. There must be one sensor info row or one
        /// location names row, but in this test there are both.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithWrongTablesInInputXML1()
        {
            instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "RealTime");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with wrong tables in input XML. There must be one sensor info row or one
        /// location names row, but in this test there are neither.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithWrongTablesInInputXML2()
        {
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows.Clear();
            instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "RealTime");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with missing device install.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithMissingDeviceInstall()
        {
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.RemoveAt(0);

            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            MockDeviceInstallRepository.Repository.Clear();
            instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "RealTime");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with missing readings.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithMissingReadings()
        {
            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            MockReadingRepository.Repository["Percentage"].Rows.Clear();
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();

            instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "RealTime");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetDeviceReading"/>
        /// method with invalid readings.
        /// </para>
        ///
        /// <para>
        /// <see cref="FaultException{TCFaultException}"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(FaultException<TCFaultException>))]
        public void TestGetDeviceReadingWithInvalidReadings()
        {
            // add device
            instance.AddDevice("user1", "password1", 1, dataSet.GetXml());

            MockReadingRepository.ReturnCorruptTable = true;
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();

            instance.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "RealTime");
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.AuthenticateUser"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The user should be authenticated successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestAuthenticateUser()
        {
            UnitTestHelper.RunInstanceMethod(instance, "AuthenticateUser",
                new string[] { "username", "password", "siteID" },
                new Type[] { typeof(string), typeof(string), typeof(int) },
                new object[] { "user1", "password1", 1 });

            // no exception means success
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AuthenticateUser"/>
        /// method with null username.
        /// </para>
        ///
        /// <para>
        /// <see cref="ArgumentNullException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestAuthenticateUserWithNullUsername()
        {
            UnitTestHelper.RunInstanceMethod(instance, "AuthenticateUser",
                new string[] { "username", "password", "siteID" },
                new Type[] { typeof(string), typeof(string), typeof(int) },
                new object[] { null, "password1", 1 });
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AuthenticateUser"/>
        /// method with empty username.
        /// </para>
        ///
        /// <para>
        /// <see cref="ArgumentException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestAuthenticateUserWithEmptyUsername()
        {
            UnitTestHelper.RunInstanceMethod(instance, "AuthenticateUser",
                new string[] { "username", "password", "siteID" },
                new Type[] { typeof(string), typeof(string), typeof(int) },
                new object[] { "\t \n", "password1", 1 });
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AuthenticateUser"/>
        /// method with null password.
        /// </para>
        ///
        /// <para>
        /// <see cref="ArgumentNullException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestAuthenticateUserWithNullPassword()
        {
            UnitTestHelper.RunInstanceMethod(instance, "AuthenticateUser",
                new string[] { "username", "password", "siteID" },
                new Type[] { typeof(string), typeof(string), typeof(int) },
                new object[] { "user1", null, 1 });
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AuthenticateUser"/>
        /// method with empty password.
        /// </para>
        ///
        /// <para>
        /// <see cref="ArgumentException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestAuthenticateUserWithEmptyPassword()
        {
            UnitTestHelper.RunInstanceMethod(instance, "AuthenticateUser",
                new string[] { "username", "password", "siteID" },
                new Type[] { typeof(string), typeof(string), typeof(int) },
                new object[] { "user1", "\t \n", 1 });
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AuthenticateUser"/>
        /// method with invalid site.
        /// </para>
        ///
        /// <para>
        /// <see cref="InvalidDataInputException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(InvalidDataInputException))]
        public void TestAuthenticateUserWithInvalidSite()
        {
            UnitTestHelper.RunInstanceMethod(instance, "AuthenticateUser",
                new string[] { "username", "password", "siteID" },
                new Type[] { typeof(string), typeof(string), typeof(int) },
                new object[] { "user1", "password1", 50 });
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.AuthenticateUser"/>
        /// method with service failure.
        /// </para>
        ///
        /// <para>
        /// <see cref="AuthenticationException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(AuthenticationException))]
        public void TestAuthenticateUserWithServiceFailure()
        {
            MockAuthenticationService.ThrowException = true;
            UnitTestHelper.RunInstanceMethod(instance, "AuthenticateUser",
                new string[] { "username", "password", "siteID" },
                new Type[] { typeof(string), typeof(string), typeof(int) },
                new object[] { "user1", "password1", 1 });
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.ReadDataSet"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The data set should be read successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestReadDataSet()
        {
            DataSet ds = (DataSet)UnitTestHelper.RunInstanceMethod(instance, "ReadDataSet",
                new string[] { "input" },
                new Type[] { typeof(string) },
                new object[] { dataSet.GetXml() });
            Assert.AreEqual(dataSet.GetXml(), ds.GetXml(), "Returned data set is wrong.");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.ReadDataSet"/>
        /// method with null input.
        /// </para>
        ///
        /// <para>
        /// <see cref="ArgumentNullException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestReadDataSetWithNullInput()
        {
            UnitTestHelper.RunInstanceMethod(instance, "ReadDataSet",
                new string[] { "input" },
                new Type[] { typeof(string) },
                new object[] { null });
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.ReadDataSet"/>
        /// method with empty input.
        /// </para>
        ///
        /// <para>
        /// <see cref="ArgumentException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestReadDataSetWithEmptyInput()
        {
            UnitTestHelper.RunInstanceMethod(instance, "ReadDataSet",
                new string[] { "input" },
                new Type[] { typeof(string) },
                new object[] { "\t \n" });
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.ReadDataSet"/>
        /// method with invalid input (it's not valid XML).
        /// </para>
        ///
        /// <para>
        /// <see cref="InvalidDataInputException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(InvalidDataInputException))]
        public void TestReadDataSetWithInvalidDataSet()
        {
            UnitTestHelper.RunInstanceMethod(instance, "ReadDataSet",
                new string[] { "input" },
                new Type[] { typeof(string) },
                new object[] { "fake" });
        }

        /// <summary>
        /// <para>
        /// Tests the accuracy of the <see cref="DeviceService.WriteDataSet"/>
        /// method.
        /// </para>
        ///
        /// <para>
        /// The data set should be written successfully.
        /// </para>
        /// </summary>
        [Test]
        public void TestWriteDataSet()
        {
            string xml = (string)UnitTestHelper.RunInstanceMethod(instance, "WriteDataSet",
                new string[] { "dataSet" },
                new Type[] { typeof(DataSet) },
                new object[] { dataSet });
            Assert.AreEqual(dataSet.GetXml(), xml, "Returned XML is wrong.");
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.WriteDataSet"/>
        /// method with null data set.
        /// </para>
        ///
        /// <para>
        /// <see cref="ArgumentNullException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestWriteDataSetWithNullDataSet()
        {
            UnitTestHelper.RunInstanceMethod(instance, "WriteDataSet",
                new string[] { "dataSet" },
                new Type[] { typeof(DataSet) },
                new object[] { null });
        }

        /// <summary>
        /// <para>
        /// Tests the failure of the <see cref="DeviceService.GetStringAttribute"/>
        /// method with null configuration.
        /// </para>
        ///
        /// <para>
        /// <see cref="DeviceServiceConfigurationException"/> is expected to be thrown.
        /// </para>
        /// </summary>
        [Test, ExpectedException(typeof(DeviceServiceConfigurationException))]
        public void TestGetStringAttributeWithNullConfiguration()
        {
            UnitTestHelper.RunInstanceMethod(instance, "GetStringAttribute",
                new string[] { "configuration", "name" },
                new Type[] { typeof(IConfiguration), typeof(string) },
                new object[] { null, "x" });
        }

    }
}


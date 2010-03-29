/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.ServiceModel;
using NUnit.Framework;
using TopCoder.Services.WCF;
using Toro.TurfGuard.Common.Core.Domain.Impl;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.WebService.Clients;

namespace Toro.TurfGuard.WebService.Impl
{
    /// <summary>
    /// Unit test for <see cref="DeviceService"/> class using WCF environment.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    [CoverageExclude]
    public class DeviceServiceWcfTest
    {
        /// <summary>
        /// The client to test against.
        /// </summary>
        private DeviceServiceClient client;

        /// <summary>
        /// The TCWcfServiceHost client used to host the service.
        /// </summary>
        private TCWcfServiceHost serviceHost;

        /// <summary>
        /// The data set used for tests.
        /// </summary>
        private DataSet dataSet;

        /// <summary>
        /// Sets up test fixture environment.
        /// </summary>
        [TestFixtureSetUp]
        public void TestFixtureSetUp()
        {
            serviceHost = new TCWcfServiceHost(typeof(DeviceService), UnitTestHelper.CreateLogger(),
                        UnitTestHelper.CreateExceptionPublishManager());
            serviceHost.Open();
        }

        /// <summary>
        /// Tears down test fixture environment.
        /// </summary>
        [TestFixtureTearDown]
        public void TestFixtureTearDown()
        {
            if (serviceHost != null)
            {
                try
                {
                    serviceHost.Close();
                }
                catch
                {
                    serviceHost.Abort();
                }
            }
            UnitTestHelper.DeleteLogFile();
        }

        /// <summary>
        /// Sets up test environment.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            // prepare mock data used for tests
            dataSet = UnitTestHelper.PrepareMockData();

            client = new DeviceServiceClient();
        }

        /// <summary>
        /// Tears down test environment.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            // reset mock data used for tests
            UnitTestHelper.ResetMockData();

            if (client != null)
            {
                try
                {
                    // close client
                    client.Close();
                }
                catch
                {
                    client.Abort();
                }
            }
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

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
            client.AddDevice(null, "x", 1, "a");
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
            client.AddDevice("\t \n", "x", 1, "a");
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
            client.AddDevice("a", null, 1, "a");
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
            client.AddDevice("a", "\t \n", 1, "a");
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
            client.AddDevice("x", "x", 1, null);
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
            client.AddDevice("x", "x", 1, "\t \n");
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
            client.AddDevice("user1", "password1", 1, "fake");
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
            client.AddDevice("user1", "bad", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 500, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
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

            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

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
            client.UpdateDevice("user1", "password1", 2, dataSet.GetXml());

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
            client.UpdateDevice(null, "x", 1, "a");
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
            client.UpdateDevice("\t \n", "x", 1, "a");
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
            client.UpdateDevice("a", null, 1, "a");
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
            client.UpdateDevice("a", "\t \n", 1, "a");
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
            client.UpdateDevice("x", "x", 1, null);
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
            client.UpdateDevice("x", "x", 1, "\t \n");
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
            client.UpdateDevice("user1", "password1", 1, "fake");
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
            client.UpdateDevice("user1", "bad", 1, dataSet.GetXml());
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
            client.UpdateDevice("user1", "password1", 500, dataSet.GetXml());
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
            client.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
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

            client.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 100;
            client.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = 100;
            client.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

            MockDeviceInstallRepository.Repository.Clear();
            client.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.UpdateDevice("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_NODE_ID] = 2;
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_NODE_ID] = 3;
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 2;
            dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = -2;
            client.AddDevice("user1", "password1", 2, dataSet.GetXml());

            // fetch devices for site 1
            string xml = client.GetAllDevices("user1", "password1", 1);

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
            xml = client.GetAllDevices("user1", "password1", 1);

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
            client.GetAllDevices(null, "x", 1);
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
            client.GetAllDevices("\t \n", "x", 1);
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
            client.GetAllDevices("a", null, 1);
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
            client.GetAllDevices("a", "\t \n", 1);
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
            client.GetAllDevices("user1", "bad", 1);
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
            client.GetAllDevices("user1", "password1", 500);
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
            client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

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
            client.AddDeviceGroup(null, "x", 1, "a");
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
            client.AddDeviceGroup("\t \n", "x", 1, "a");
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
            client.AddDeviceGroup("a", null, 1, "a");
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
            client.AddDeviceGroup("a", "\t \n", 1, "a");
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
            client.AddDeviceGroup("x", "x", 1, null);
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
            client.AddDeviceGroup("x", "x", 1, "\t \n");
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
            client.AddDeviceGroup("user1", "password1", 1, "fake");
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
            client.AddDeviceGroup("user1", "bad", 1, dataSet.GetXml());
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
            client.AddDeviceGroup("user1", "password1", 500, dataSet.GetXml());
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
            client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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

            client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

            // make sure device group was added to repository
            Assert.IsTrue(MockDeviceGroupRepository.Repository.ContainsKey("loc1"),
                "The device group should have been added.");

            // prepare for update
            MockDeviceGroupRepository.DeviceGroupsForSite[1] = new List<DeviceGroup>();
            MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(MockDeviceGroupRepository.Repository["loc1"]);
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "loc1";
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME] = "loc2";

            // update device group
            client.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());

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
            client.UpdateDeviceGroup(null, "x", 1, "a");
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
            client.UpdateDeviceGroup("\t \n", "x", 1, "a");
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
            client.UpdateDeviceGroup("a", null, 1, "a");
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
            client.UpdateDeviceGroup("a", "\t \n", 1, "a");
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
            client.UpdateDeviceGroup("x", "x", 1, null);
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
            client.UpdateDeviceGroup("x", "x", 1, "\t \n");
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
            client.UpdateDeviceGroup("user1", "password1", 1, "fake");
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
            client.UpdateDeviceGroup("user1", "bad", 1, dataSet.GetXml());
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
            client.UpdateDeviceGroup("user1", "password1", 500, dataSet.GetXml());
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
            client.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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

            client.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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
            client.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

            // make sure device group was added to repository
            Assert.IsTrue(MockDeviceGroupRepository.Repository.ContainsKey("loc1"),
                "The device group should have been added.");

            // prepare for update
            MockDeviceGroupRepository.DeviceGroupsForSite[1] = new List<DeviceGroup>();
            MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(MockDeviceGroupRepository.Repository["loc1"]);
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "loc1";
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME] = DBNull.Value;
            client.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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
            client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

            // make sure device group was added to repository
            Assert.IsTrue(MockDeviceGroupRepository.Repository.ContainsKey("loc1"),
                "The device group should have been added.");

            // prepare for update
            MockDeviceGroupRepository.DeviceGroupsForSite[1] = new List<DeviceGroup>();
            MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(MockDeviceGroupRepository.Repository["loc1"]);
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "loc1";
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows[0][UnitTestHelper.COLUMN_NAME] = "\t \n";
            client.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
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
            string xml = client.GetAllDeviceGroups("user1", "password1", 1, DeviceGroup.PRIMARY);

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
            xml = client.GetAllDeviceGroups("user1", "password1", 2, DeviceGroup.SECONDARY);

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
            xml = client.GetAllDeviceGroups("user1", "password1", 3, DeviceGroup.SECONDARY);

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
            client.GetAllDeviceGroups(null, "x", 1, DeviceGroup.PRIMARY);
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
            client.GetAllDeviceGroups("\t \n", "x", 1, DeviceGroup.PRIMARY);
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
            client.GetAllDeviceGroups("a", null, 1, DeviceGroup.PRIMARY);
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
            client.GetAllDeviceGroups("a", "\t \n", 1, DeviceGroup.PRIMARY);
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

            // ------------------------
            // get real time readings
            // ------------------------
            string xml = client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            xml = client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            xml = client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            xml = client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            client.GetDeviceReading(null, "x", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "Daily");
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
            client.GetDeviceReading("\t \n", "x", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "Daily");
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
            client.GetDeviceReading("a", null, 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "Daily");
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
            client.GetDeviceReading("a", "\t \n", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "Daily");
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
            client.GetDeviceReading("a", "b", 1, null, 1, DateTime.Now, DateTime.Now, "Daily");
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
            client.GetDeviceReading("a", "b", 1, "\t \n", 1, DateTime.Now, DateTime.Now, "Daily");
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
            client.GetDeviceReading("user1", "password1", 1, "fake", 1, DateTime.Now, DateTime.Now, "Daily");
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
            client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1, DateTime.Now,
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
            client.GetDeviceReading("a", "b", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, null);
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
            client.GetDeviceReading("a", "b", 1, dataSet.GetXml(), 1, DateTime.Now, DateTime.Now, "\t \n");
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
            client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

            MockDeviceInstallRepository.Repository.Clear();
            client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

            MockReadingRepository.Repository["Percentage"].Rows.Clear();
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();

            client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
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
            client.AddDevice("user1", "password1", 1, dataSet.GetXml());

            MockReadingRepository.ReturnCorruptTable = true;
            dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();

            client.GetDeviceReading("user1", "password1", 1, dataSet.GetXml(), 1,
                new DateTime(2010, 1, 1, 0, 1, 0), new DateTime(2010, 1, 1, 0, 2, 0), "RealTime");
        }

    }
}


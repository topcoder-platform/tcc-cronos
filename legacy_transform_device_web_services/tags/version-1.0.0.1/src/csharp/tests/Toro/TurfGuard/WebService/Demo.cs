/*
 * Copyright (c) 2010 TopCoder Inc., All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Data;
using System.ServiceModel;
using NUnit.Framework;
using TopCoder.Services.WCF;
using Toro.TurfGuard.Common.Core.Domain.Impl;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.WebService.Clients;
using Toro.TurfGuard.WebService.Impl;

namespace Toro.TurfGuard.WebService
{
    /// <summary>
    /// This class demonstrates the usage of this component.
    /// </summary>
    ///
    /// <author>argolite</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture, CoverageExclude]
    public class Demo
    {
        /// <summary>
        /// The data set used for demo.
        /// </summary>
        private DataSet dataSet;

        /// <summary>
        /// Sets up test environment.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            // prepare mock data used for tests
            dataSet = UnitTestHelper.PrepareMockData();
        }

        /// <summary>
        /// Tears down test environment.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            // reset mock data used for tests
            UnitTestHelper.ResetMockData();

            UnitTestHelper.DeleteLogFile();
        }

        /// <summary>
        /// This demo shows how to host the <see cref="DeviceService"/> service and access the service
        /// using the WCF client. It demonstrates the business methods used in the service.
        /// </summary>
        [Test]
        public void DemoUsage()
        {
            // the client to use for the demo
            DeviceServiceClient client = null;

            // create and open the service host using WCF technology
            using (ServiceHost serviceHost =
                new TCWcfServiceHost(typeof(DeviceService),
                    UnitTestHelper.CreateLogger(),
                    UnitTestHelper.CreateExceptionPublishManager()))
            {
                try
                {
                    // open the service host
                    serviceHost.Open();
                    Console.WriteLine("Service host opened successfully...");

                    // create the client
                    client = new DeviceServiceClient();

                    // add devices, 2 devices for site 1 and 1 device for site 2
                    client.AddDevice("user1", "password1", 1, dataSet.GetXml());
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_NODE_ID] = 2;
                    client.AddDevice("user1", "password1", 1, dataSet.GetXml());
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_NODE_ID] = 3;
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 2;
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = -2;
                    client.AddDevice("user1", "password1", 2, dataSet.GetXml());

                    // prepare for update
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 2;
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = -2;
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_IN_SERVICE] =
                        new DateTime(2012, 1, 1);
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_X] = 150;
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_Y] = 160;
                    dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows[0][UnitTestHelper.COLUMN_SERIAL_NUMBER] = "200";

                    // update the device
                    client.UpdateDevice("user1", "password1", 2, dataSet.GetXml());

                    // fetch devices for site 1
                    string xml = client.GetAllDevices("user1", "password1", 1);
                    DataSet resultDataSet = UnitTestHelper.ParseResultSet(xml);
                    Console.WriteLine("There were " +
                        resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
                        Rows.Count + " devices fetched.");

                    // add device group
                    client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());

                    // prepare for update
                    MockDeviceGroupRepository.DeviceGroupsForSite[1] =
                        new List<DeviceGroup>();
                    MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(
                        MockDeviceGroupRepository.Repository["loc1"]);
                    dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].
                        Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "loc1";
                    dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].
                        Rows[0][UnitTestHelper.COLUMN_NAME] = "loc2";

                    // update device group
                    client.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());

                    // get the primary device groups for site 1
                    xml = client.GetAllDeviceGroups("user1", "password1", 1,
                        DeviceGroup.PRIMARY);
                    resultDataSet = UnitTestHelper.ParseResultSet(xml);
                    Console.WriteLine("There were " +
                        resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].
                        Rows.Count + " device groups fetched.");

                    dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();

                    // get real time readings on a device
                    xml = client.GetDeviceReading("user1", "password1", 1,
                        dataSet.GetXml(), 1,
                        new DateTime(2010, 1, 1, 0, 1, 0),
                        new DateTime(2010, 1, 1, 0, 2, 0),
                        "RealTime");
                    resultDataSet = UnitTestHelper.ParseResultSet(xml);
                    Console.WriteLine("There were " +
                        resultDataSet.Tables[UnitTestHelper.TABLE_MINUTELY_READINGS].
                        Rows.Count + " real time readings fetched.");
                }
                finally
                {
                    if (client != null)
                    {
                        // close the client
                        try
                        {
                            client.Close();
                        }
                        catch
                        {
                            client.Abort();
                        }
                        Console.WriteLine("Client closed.");
                    }

                    // close the host
                    try
                    {
                        serviceHost.Close();
                    }
                    catch
                    {
                        serviceHost.Abort();
                    }
                    Console.WriteLine("Service host closed.");

                    Console.WriteLine("Demo complete.");
                }
            }
        }
    }
}

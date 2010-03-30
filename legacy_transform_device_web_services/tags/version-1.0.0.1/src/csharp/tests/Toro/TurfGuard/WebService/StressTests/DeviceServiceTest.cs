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
using System.Xml;
using Toro.TurfGuard.WebService.Impl;

namespace Toro.TurfGuard.WebService.StressTests
{
    /// <summary>
    /// Stress test for <see cref="DeviceService"/> class.
    /// </summary>
    ///
    /// <author>assistant</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    [CoverageExclude]
    public class DeviceServiceTest
    {
        /// <summary>
        /// Represents <see cref="DeviceService"/> instance used for tests.
        /// </summary>
        private DeviceService instance;

        /// <summary>
        /// Prepares mock data used during unit tests.
        /// </summary>
        ///
        /// <returns>A data set containing mock data used for tests.</returns>
        internal DataSet PrepareMockData()
        {
            DataSet dataSet = new DataSet();
            // read schema from file and load it to data set
            using (FileStream fileStream = new FileStream(@"..\..\test_files\stress\ClientSchema.xsd", FileMode.Open))
            {
                using (XmlTextReader reader = new XmlTextReader(fileStream))
                {
                    dataSet.ReadXmlSchema(reader);
                }
            }

            DataRow row = dataSet.Tables["sensorinfo"].NewRow();
            row["HoleNumber"] = 1;
            row["LocationType"] = -1;
            row["X"] = 14;
            row["Y"] = 15;
            row["NodeID"] = 1;
            row["SerialNumber"] = 100;
            row["InService"] = new DateTime(2010, 01, 01);
            dataSet.Tables["sensorinfo"].Rows.Add(row);

            row = dataSet.Tables["LocationNames"].NewRow();
            row["Name"] = "loc1";
            row["ID"] = 1;
            dataSet.Tables["LocationNames"].Rows.Add(row);
            return dataSet;
        }

        /// <summary>
        /// Creates the readings table used for unit tests.
        /// </summary>
        ///
        /// <returns>Readings table used for unit tests.</returns>
        private DataTable CreateReadingsTable()
        {
            DataTable readingsTable = new DataTable();
            readingsTable.Columns.Add(new DataColumn("reading_dt", typeof(DateTime)));
            readingsTable.Columns.Add(new DataColumn("reading_val", typeof(Single)));
            return readingsTable;
        }

        /// <summary>
        /// Setup environment.
        /// </summary>
        [TestFixtureSetUp]
        public void SetUp()
        {
            if (File.Exists(@"../../test_files/web.config"))
            {
                File.Copy(@"../../test_files/web.config", @"../../test_files/stress/web.config.backup", true);
                File.Copy(@"../../test_files/stress/web.config", @"../../test_files/web.config", true);
            }

            // create instance used for tests
            instance = new DeviceService();

            DeviceGroup deviceGroup = new DeviceGroup { Id = 1, Name = "1" };
            MockDeviceGroupRepository.Repository[deviceGroup.Name] = deviceGroup;
            deviceGroup = new DeviceGroup { Id = 2, Name = "2" };
            MockDeviceGroupRepository.Repository[deviceGroup.Name] = deviceGroup;

            deviceGroup = new DeviceGroup { Id = -1, Name = "-1" };
            MockDeviceGroupRepository.Repository[deviceGroup.Name] = deviceGroup;
            deviceGroup = new DeviceGroup { Id = -2, Name = "-2" };
            MockDeviceGroupRepository.Repository[deviceGroup.Name] = deviceGroup;

            MockDeviceGroupRepository.DeviceGroupsForSite[1] = new List<DeviceGroup>();
            MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(MockDeviceGroupRepository.Repository["1"]);
            MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(MockDeviceGroupRepository.Repository["-1"]);

            MockDeviceGroupRepository.DeviceGroupsForSite[2] = new List<DeviceGroup>();
            MockDeviceGroupRepository.DeviceGroupsForSite[2].Add(MockDeviceGroupRepository.Repository["2"]);
            MockDeviceGroupRepository.DeviceGroupsForSite[2].Add(MockDeviceGroupRepository.Repository["-2"]);

            MockUserRepository.Repository["user1"] = (new User { Username = "user1", Password = "password1" });

            DateTime[] readingDates =
                new DateTime[] {
                    new DateTime(2010, 1, 1, 0, 1, 0),
                    new DateTime(2010, 1, 1, 0, 2, 0),
                    new DateTime(2010, 1, 1, 1, 0, 0),
                    new DateTime(2010, 1, 1, 2, 0, 0),
                    new DateTime(2010, 2, 1, 0, 0, 0),
                    new DateTime(2010, 3, 1, 0, 0, 0)
                };

            MockReadingRepository.Repository["Temp"] = CreateReadingsTable();
            MockReadingRepository.Repository["Salinity"] = CreateReadingsTable();
            MockReadingRepository.Repository["Percentage"] = CreateReadingsTable();

            int readingVal = 1;
            foreach (DateTime readingDate in readingDates)
            {
                DataRow row = MockReadingRepository.Repository["Temp"].NewRow();
                row["reading_dt"] = readingDate;
                row["reading_val"] = readingVal++;
                MockReadingRepository.Repository["Temp"].Rows.Add(row);

                row = MockReadingRepository.Repository["Salinity"].NewRow();
                row["reading_dt"] = readingDate;
                row["reading_val"] = readingVal++;
                MockReadingRepository.Repository["Salinity"].Rows.Add(row);

                row = MockReadingRepository.Repository["Percentage"].NewRow();
                row["reading_dt"] = readingDate;
                row["reading_val"] = readingVal++;
                MockReadingRepository.Repository["Percentage"].Rows.Add(row);
            }

        }

        /// <summary>
        /// Cleans up the environment.
        /// </summary>
        [TestFixtureTearDown]
        public void TearDown()
        {
            MockReadingRepository.Repository.Clear();
            MockDeviceGroupRepository.Repository.Clear();
            MockDeviceInstallRepository.Repository.Clear();
            MockDeviceRepository.Repository.Clear();
            MockDeviceInstallRepository.SequenceNumber = 0;
            MockDeviceGroupRepository.SequenceNumber = 1;

            File.Copy(@"../../test_files/config_backup/web.config", @"../../test_files/web.config", true);
        }

        /// <summary>
        /// <para>
        /// Tests of the <see cref="DeviceService.AddDevice"/> method.
        /// </para>
        /// </summary>
        [Test]
        public void TestAddDevice()
        {
            // add 1000 device
            DateTime start = DateTime.Now;
            for (int i = 0; i < 1000; i++)
            {
                instance.AddDevice("user1", "password1", 1, PrepareMockData().GetXml());
            }
            DateTime end = DateTime.Now;
            Console.WriteLine("Add device used : " + (end - start));

            Assert.IsTrue(MockDeviceRepository.Repository.ContainsKey(1), "The device should have been added.");
        }

    }
}


/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using NUnit.Framework;
using Toro.TurfGuard.Common.Core.Domain.Impl;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.Common.Core.Services.Impl;
using System.Xml;

namespace Toro.TurfGuard.WebService.Impl.AccuracyTests
{
    /// <summary>
    /// AccuracyTests for <see cref="DeviceService"/> class.
    /// </summary>
    ///
    /// <author>tangliangl</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class DeviceServiceAccuracyTests
    {
        /// <summary>
        /// Represents <see cref="DeviceService"/> instance used for tests.
        /// </summary>
        private DeviceService instance;

        /// <summary>
        /// Cleans up the environment after all tests in this file have completed.
        /// </summary>
        [TestFixtureTearDown]
        protected void TestFixtureTearDown()
        {
            try
            {
                log4net.LogManager.Shutdown();
                if (File.Exists(@"..\..\test_files\log.txt"))
                {
                    File.Delete(@"..\..\test_files\log.txt");
                }
            }
            catch
            {
                // ignore
            }
        }

        /// <summary>
        /// Setup environment for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            // reset dataset.
            ResetAccuracyRepository();

            // create instance used for tests
            instance = new DeviceService();

            // prepare dataset
            inputAccuracyDataSet = PrepareAccuracyDataSet();
        }

        /// <summary>
        /// Tear down environment after each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            // reset dataset.
            ResetAccuracyRepository();
        }

        /// <summary>
        /// PrepareAccuracyDataSet
        /// </summary>
        /// <returns>DataSet</returns>
        private DataSet PrepareAccuracyDataSet()
        {
            // reset data set.
            ResetAccuracyRepository();

            // read schema from file and load it to data set
            DataSet dataSet = new DataSet();

            using (XmlTextReader reader = new XmlTextReader(@"..\..\test_files\ClientSchema.xsd"))
            {
                dataSet.ReadXmlSchema(reader);
            }

            // prepare data set
            PrepareDataSetTableData(dataSet);

            // prepare mock repositories
            PrepareMockRepositories();

            return dataSet;
        }

        /// <summary>
        /// PrepareMockRepositories
        /// </summary>
        private void PrepareMockRepositories()
        {
            // prepare repository for UserRepository
            MockUserRepository.Repository[username] = (new User { Username = username, Password = password });

            // prepare repository for eviceGroupRepository
            MockDeviceGroupRepository.Repository["1"] = new DeviceGroup { Id = 101, Name = "1" };
            MockDeviceGroupRepository.Repository["2"] = new DeviceGroup { Id = -102, Name = "2" };
            MockDeviceGroupRepository.Repository["3"] = new DeviceGroup { Id = 103, Name = "3" };
            MockDeviceGroupRepository.Repository["4"] = new DeviceGroup { Id = -104, Name = "4" };
            MockDeviceGroupRepository.DeviceGroupsForSite[1] = new List<DeviceGroup> {  MockDeviceGroupRepository.Repository["1"],
                MockDeviceGroupRepository.Repository["2"]
            };
            MockDeviceGroupRepository.DeviceGroupsForSite[2] = new List<DeviceGroup> { MockDeviceGroupRepository.Repository["3"],
                MockDeviceGroupRepository.Repository["4"]};

            // prepare repository for ReadingRepository
            DateTime[] readingDates =
                new DateTime[] {
                    new DateTime(2010, 3, 23, 8, 1, 0),
                    new DateTime(2010, 3, 23, 8, 2, 0),
                    new DateTime(2010, 3, 23, 8, 3, 0),
                    new DateTime(2010, 3, 23, 8, 4, 0)
                };
            DataTable readingsTable = new DataTable();
            readingsTable.Columns.Add(new DataColumn("reading_dt", typeof(DateTime)));
            readingsTable.Columns.Add(new DataColumn("reading_val", typeof(Double)));
            MockReadingRepository.Repository["Temp"] = readingsTable.Copy();
            MockReadingRepository.Repository["Salinity"] = readingsTable.Copy();
            MockReadingRepository.Repository["Percentage"] = readingsTable.Copy();

            int readingVal = 1;
            DataRow row = null;
            foreach (DateTime readingDate in readingDates)
            {
                row = MockReadingRepository.Repository["Temp"].NewRow();
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
        /// PrepareDataSetTableData
        /// </summary>
        /// <param name="dataSet">dataSet</param>
        private void PrepareDataSetTableData(DataSet dataSet)
        {
            AppendSensorInfoRow(dataSet);

            AppendLocationNamesRow(dataSet);
        }

        /// <summary>
        /// AppendSensorInfoRow
        /// </summary>
        /// <param name="dataSet">dataSet</param>
        private void AppendSensorInfoRow(DataSet dataSet)
        {
            DataRow row = dataSet.Tables["sensorinfo"].NewRow();
            row["HoleNumber"] = 3;
            row["LocationType"] = 4;
            row["X"] = 21;
            row["Y"] = 22;
            row["NodeID"] = 101;
            row["SerialNumber"] = 102;
            row["InService"] = new DateTime(2010, 03, 24);
            dataSet.Tables["sensorinfo"].Rows.Add(row);

            dataSet.AcceptChanges();

        }

        /// <summary>
        /// AppendLocationNamesRow
        /// </summary>
        /// <param name="dataSet">dataSet</param>
        private void AppendLocationNamesRow(DataSet dataSet)
        {
            DataRow row = dataSet.Tables["LocationNames"].NewRow();
            row["Name"] = "LocationName1";
            row["ID"] = 100;
            dataSet.Tables["LocationNames"].Rows.Add(row);

            dataSet.AcceptChanges();

        }

        /// <summary>
        /// ResetAccuracyDataSet
        /// </summary>
        private void ResetAccuracyRepository()
        {
            MockDeviceGroupRepository.Repository.Clear();
            MockDeviceInstallRepository.Repository.Clear();
            MockDeviceRepository.Repository.Clear();
            MockReadingRepository.Repository.Clear();
            MockUserRepository.Repository.Clear();

            MockDeviceInstallRepository.SequenceNumber = 0;
            MockDeviceGroupRepository.SequenceNumber = 1;
            MockAuthenticationService.ThrowException = false;
            MockUserRepository.ThrowException = false;
            MockUserRepository.CallingMethodForThrowException = null;
            MockReadingRepository.ReturnCorruptTable = false;
        }

        /// <summary>
        /// inputAccuracyDataSet
        /// </summary>
        private DataSet inputAccuracyDataSet;

        /// <summary>
        /// username
        /// </summary>
        private const string username = "AccuracyUser1";

        /// <summary>
        /// password
        /// </summary>
        private const string password = "AccuracyPassword1";

        /// <summary>
        /// Test AddDevice() method's accuracy.
        /// </summary>
        [Test]
        public void AddDevice_AccuracyTests1()
        {
            // invoke it
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // check result
            Device device = MockDeviceRepository.Repository[101];
            Assert.AreEqual("102", device.SerialNumber, "not expected");
            Assert.AreEqual(101, device.NodeId, "not expected");
            Assert.AreEqual(2, device.Site.Id, "not expected");
            Assert.AreEqual(1, device.GetDeviceInstalls().Length, "not expected");

            DeviceInstall[] deviceInstalls = device.GetDeviceInstalls();
            Assert.AreEqual(21, deviceInstalls[0].Latitude, "not expected");
            Assert.AreEqual(22, deviceInstalls[0].Longitude, "not expected");
            Assert.AreEqual(new DateTime(2010, 03, 24),
                deviceInstalls[0].InstallationDate, "not expected");

            Assert.IsNotNull(deviceInstalls[0].PrimaryGroup, "not expected");
            Assert.AreEqual(103, deviceInstalls[0].PrimaryGroup.Id, "not expected");
            Assert.AreEqual("3", deviceInstalls[0].PrimaryGroup.Name, "not expected");

            Assert.IsNotNull(deviceInstalls[0].SecondaryGroup, "not expected");
            Assert.AreEqual(-104, deviceInstalls[0].SecondaryGroup.Id, "not expected");
            Assert.AreEqual("4", deviceInstalls[0].SecondaryGroup.Name, "not expected");
        }

        /// <summary>
        /// Test AddDevice() method's accuracy.
        /// </summary>
        [Test]
        public void AddDevice_AccuracyTests2()
        {
            // invoke it 2 times.
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // check result
            Device device = MockDeviceRepository.Repository[101];
            Assert.AreEqual("102", device.SerialNumber, "not expected");
            Assert.AreEqual(101, device.NodeId, "not expected");
            Assert.AreEqual(2, device.Site.Id, "not expected");
            Assert.AreEqual(1, device.GetDeviceInstalls().Length, "not expected");

            DeviceInstall[] deviceInstalls = device.GetDeviceInstalls();
            Assert.AreEqual(21, deviceInstalls[0].Latitude, "not expected");
            Assert.AreEqual(22, deviceInstalls[0].Longitude, "not expected");
            Assert.AreEqual(new DateTime(2010, 03, 24),
                deviceInstalls[0].InstallationDate, "not expected");

            Assert.IsNotNull(deviceInstalls[0].PrimaryGroup, "not expected");
            Assert.AreEqual(103, deviceInstalls[0].PrimaryGroup.Id, "not expected");
            Assert.AreEqual("3", deviceInstalls[0].PrimaryGroup.Name, "not expected");

            Assert.IsNotNull(deviceInstalls[0].SecondaryGroup, "not expected");
            Assert.AreEqual(-104, deviceInstalls[0].SecondaryGroup.Id, "not expected");
            Assert.AreEqual("4", deviceInstalls[0].SecondaryGroup.Name, "not expected");
        }

        /// <summary>
        /// Test UpdateDevice() method's accuracy.
        /// </summary>
        [Test]
        public void UpdateDevice_AccuracyTests1()
        {
            // add device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // prepare for update
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["InService"] = new DateTime(2010, 3, 28);
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["X"] = 2012;
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["Y"] = 2013;
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["SerialNumber"] = 202;

            // update the device
            instance.UpdateDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // check result
            Device device = MockDeviceRepository.Repository[101];
            Assert.AreEqual("202", device.SerialNumber, "not expected");
            Assert.AreEqual(101, device.NodeId, "not expected");
            Assert.AreEqual(2, device.Site.Id, "not expected");
            Assert.AreEqual(1, device.GetDeviceInstalls().Length, "not expected");

            DeviceInstall[] deviceInstalls = device.GetDeviceInstalls();
            Assert.AreEqual(2012, deviceInstalls[0].Latitude, "not expected");
            Assert.AreEqual(2013, deviceInstalls[0].Longitude, "not expected");
            Assert.AreEqual(new DateTime(2010, 03, 28),
                deviceInstalls[0].InstallationDate, "not expected");

            Assert.IsNotNull(deviceInstalls[0].PrimaryGroup, "not expected");
            Assert.AreEqual(103, deviceInstalls[0].PrimaryGroup.Id, "not expected");
            Assert.AreEqual("3", deviceInstalls[0].PrimaryGroup.Name, "not expected");

            Assert.IsNotNull(deviceInstalls[0].SecondaryGroup, "not expected");
            Assert.AreEqual(-104, deviceInstalls[0].SecondaryGroup.Id, "not expected");
            Assert.AreEqual("4", deviceInstalls[0].SecondaryGroup.Name, "not expected");
        }

        /// <summary>
        /// Test GetAllDevices() method's accuracy.
        /// </summary>
        [Test]
        public void GetAllDevices_AccuracyTests1()
        {
            // check result
            string xml = instance.GetAllDevices(username, password, 2);

            Assert.IsNotNull(xml, "not expected");

            // for siteID = 2;
            DataSet resultDataSet = ParseDataSetFromXml(xml);

            Assert.AreEqual(0, resultDataSet.Tables["sensorinfo"].Rows.Count, "not expected");
        }

        /// <summary>
        /// Test GetAllDevices() method's accuracy.
        /// </summary>
        [Test]
        public void GetAllDevices_AccuracyTests2()
        {
            // add device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // prepare new value.
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["SerialNumber"] = 2001;
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["NodeID"] = 102;

            // add the device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // check result
            string xml = instance.GetAllDevices(username, password, 2);

            Assert.IsNotNull(xml, "not expected");

            // for siteID = 2;
            DataSet resultDataSet = ParseDataSetFromXml(xml);

            Assert.AreEqual(2, resultDataSet.Tables["sensorinfo"].Rows.Count, "not expected");
            DataRow row = resultDataSet.Tables["sensorinfo"].Rows[0];
            Assert.AreEqual(3, row["HoleNumber"], "not expected");
            Assert.AreEqual(4, row["LocationType"], "not expected");
            Assert.AreEqual(21, row["X"], "not expected");
            Assert.AreEqual(22, row["Y"], "not expected");
            Assert.AreEqual(101, row["NodeID"], "not expected");
            Assert.AreEqual(102, row["SerialNumber"], "not expected");
            Assert.AreEqual(new DateTime(2010, 03, 24), row["InService"], "not expected");

            row = resultDataSet.Tables["sensorinfo"].Rows[1];
            Assert.AreEqual(2001, row["SerialNumber"], "not expected");
            Assert.AreEqual(102, row["NodeID"], "not expected");
        }

        /// <summary>
        /// Test GetAllDevices() method's accuracy.
        /// </summary>
        [Test]
        public void GetAllDevices_AccuracyTests3()
        {
            // add device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // prepare new value.
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["SerialNumber"] = 2001;
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["NodeID"] = 102;

            // add the device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // prepare new value.
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["NodeID"] = 103;
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["HoleNumber"] = 1;
            inputAccuracyDataSet.Tables["sensorinfo"].Rows[0]["LocationType"] = 2;

            // add the device
            instance.AddDevice(username, password, 1, inputAccuracyDataSet.GetXml());

            // check result
            string xml = instance.GetAllDevices(username, password, 1);

            Assert.IsNotNull(xml, "not expected");

            // for siteID = 1;
            DataSet resultDataSet = ParseDataSetFromXml(xml);

            Assert.AreEqual(1, resultDataSet.Tables["sensorinfo"].Rows.Count, "not expected");
            Assert.AreEqual(103, resultDataSet.Tables["sensorinfo"].Rows[0]["NodeID"], "not expected");
            Assert.AreEqual(1, resultDataSet.Tables["sensorinfo"].Rows[0]["HoleNumber"], "not expected");
            Assert.AreEqual(2, resultDataSet.Tables["sensorinfo"].Rows[0]["LocationType"], "not expected");
        }

        /// <summary>
        /// ParseDataSetFromXml
        /// </summary>
        /// <param name="xml">xml</param>
        /// <returns>DataSet</returns>
        private DataSet ParseDataSetFromXml(string xml)
        {
            // read schema from file and load it to data set
            DataSet dataSet = new DataSet();

            using (XmlTextReader reader = new XmlTextReader(@"..\..\test_files\ClientSchema.xsd"))
            {
                dataSet.ReadXmlSchema(reader);
            }

            // load returned xml to data set
            using (StringReader reader = new StringReader(xml))
            {
                // read the xml string into the data set
                dataSet.ReadXml(reader);
            }

            return dataSet;
        }

        /// <summary>
        /// Test AddDeviceGroup() method's accuracy.
        /// </summary>
        [Test]
        public void AddDeviceGroup_AccuracyTests1()
        {
            MockDeviceGroupRepository.Repository.Clear();

            // add device group
            instance.AddDeviceGroup(username, password, 2, inputAccuracyDataSet.GetXml());

            // check result
            Assert.AreEqual(1, MockDeviceGroupRepository.Repository.Count, "not expected");
            
            DeviceGroup group = MockDeviceGroupRepository.Repository["LocationName1"];
            Assert.IsNotNull(group, "not expected");
            Assert.AreEqual(1, group.Id, "not expected");
            Assert.AreEqual("LocationName1", group.Name, "not expected");
            Assert.AreEqual(DeviceGroup.PRIMARY, group.DeviceGroupType, "not expected");
        }

        /// <summary>
        /// Test AddDeviceGroup() method's accuracy.
        /// </summary>
        [Test]
        public void AddDeviceGroup_AccuracyTests2()
        {
            MockDeviceGroupRepository.Repository.Clear();

            // add device group
            instance.AddDeviceGroup(username, password, 2, inputAccuracyDataSet.GetXml());

            inputAccuracyDataSet.Tables["LocationNames"].Clear();
            DataRow row = inputAccuracyDataSet.Tables["LocationNames"].NewRow();
            row["Name"] = "LocationName2";
            row["ID"] = 101;
            inputAccuracyDataSet.Tables["LocationNames"].Rows.Add(row);

            inputAccuracyDataSet.AcceptChanges();
            instance.AddDeviceGroup(username, password, 3, inputAccuracyDataSet.GetXml());

            // check result
            Assert.AreEqual(2, MockDeviceGroupRepository.Repository.Count, "not expected");

            DeviceGroup group = MockDeviceGroupRepository.Repository["LocationName1"];
            Assert.IsNotNull(group, "not expected");
            Assert.AreEqual(1, group.Id, "not expected");
            Assert.AreEqual("LocationName1", group.Name, "not expected");
            Assert.AreEqual(DeviceGroup.PRIMARY, group.DeviceGroupType, "not expected");

            group = MockDeviceGroupRepository.Repository["LocationName2"];
            Assert.IsNotNull(group, "not expected");
            Assert.AreEqual(2, group.Id, "not expected");
            Assert.AreEqual("LocationName2", group.Name, "not expected");
            Assert.AreEqual(DeviceGroup.PRIMARY, group.DeviceGroupType, "not expected");
        }

        /// <summary>
        /// Test UpdateDeviceGroup() method's accuracy.
        /// </summary>
        [Test]
        public void UpdateDeviceGroup_AccuracyTests1()
        {
            MockDeviceGroupRepository.Repository.Clear();

            // add device group
            instance.AddDeviceGroup(username, password, 2, inputAccuracyDataSet.GetXml());

            // check result
            Assert.AreEqual(1, MockDeviceGroupRepository.Repository.Count, "not expected");

            // updae some data.
            inputAccuracyDataSet.Tables["LocationNames"].Rows[0]["Name"] = "NewLocationName";
            inputAccuracyDataSet.Tables["LocationNames"].Rows[0]["ID"] = 9999;
            inputAccuracyDataSet.Tables["LocationNames"].Rows[0]["OldName"] = "3";

            instance.UpdateDeviceGroup(username, password, 2, inputAccuracyDataSet.GetXml());

            DeviceGroup group = MockDeviceGroupRepository.Repository["NewLocationName"];
            Assert.IsNotNull(group, "not expected");
            Assert.AreEqual(103, group.Id, "not expected");
            Assert.AreEqual("NewLocationName", group.Name, "not expected");
        }

        /// <summary>
        /// Test GetAllDeviceGroups() method's accuracy.
        /// </summary>
        [Test]
        public void GetAllDeviceGroups_AccuracyTests1()
        {
            MockDeviceGroupRepository.Repository.Clear();
            MockDeviceGroupRepository.DeviceGroupsForSite.Clear();

            // invoke it.
            string xml = instance.GetAllDeviceGroups(username, password, 2, DeviceGroup.PRIMARY);

            // check.
            Assert.IsNotNull(xml, "not expected");

            DataSet ds = ParseDataSetFromXml(xml);
            Assert.AreEqual(0, ds.Tables["LocationNames"].Rows.Count, "not expected");
        }

        /// <summary>
        /// Test GetAllDeviceGroups() method's accuracy.
        /// </summary>
        [Test]
        public void GetAllDeviceGroups_AccuracyTests2()
        {
            MockDeviceGroupRepository.Repository.Clear();

            // add device group
            instance.AddDeviceGroup(username, password, 2, inputAccuracyDataSet.GetXml());

            // invoke it.
            string xml = instance.GetAllDeviceGroups(username, password, 2, DeviceGroup.PRIMARY);

            // check.
            Assert.IsNotNull(xml, "not expected");

            DataSet ds = ParseDataSetFromXml(xml);
            Assert.AreEqual(1, ds.Tables["LocationNames"].Rows.Count, "not expected");
            Assert.AreEqual("3",
                ds.Tables["LocationNames"].Rows[0]["Name"], "not expected");
            Assert.AreEqual(103,
                ds.Tables["LocationNames"].Rows[0]["ID"], "not expected");
        }

        /// <summary>
        /// Test GetDeviceReading() method's accuracy.
        /// </summary>
        [Test]
        public void GetDeviceReading_AccuracyTests1()
        {
            inputAccuracyDataSet.Tables["LocationNames"].Clear();

            // add device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // invoke it with "RealTime".
            string xml = instance.GetDeviceReading(username, password, 2, inputAccuracyDataSet.GetXml(), 2,
                new DateTime(2000, 1, 1), new DateTime(2020, 1, 1), "RealTime");
            Assert.IsNotNull(xml, "not expected");

            DataSet ds = ParseDataSetFromXml(xml);
            Assert.AreEqual(4, ds.Tables["MinutelyReadings"].Rows.Count, "not expected");
            Assert.AreEqual(0, ds.Tables["HourlyReadings"].Rows.Count, "not expected");
            Assert.AreEqual(0, ds.Tables["MultiHourlyReadings"].Rows.Count, "not expected");

        }

        /// <summary>
        /// Test GetDeviceReading() method's accuracy.
        /// </summary>
        [Test]
        public void GetDeviceReading_AccuracyTests2()
        {
            inputAccuracyDataSet.Tables["LocationNames"].Clear();

            // add device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // invoke it with "RealTime".
            string xml = instance.GetDeviceReading(username, password, 2, inputAccuracyDataSet.GetXml(), 2,
                new DateTime(2010, 3, 23, 8, 1, 0), new DateTime(2010, 3, 23, 8, 2, 0), "RealTime");
            Assert.IsNotNull(xml, "not expected");

            DataSet ds = ParseDataSetFromXml(xml);
            Assert.AreEqual(2, ds.Tables["MinutelyReadings"].Rows.Count, "not expected");
            Assert.AreEqual(0, ds.Tables["HourlyReadings"].Rows.Count, "not expected");
            Assert.AreEqual(0, ds.Tables["MultiHourlyReadings"].Rows.Count, "not expected");

        }

        /// <summary>
        /// Test GetDeviceReading() method's accuracy.
        /// </summary>
        [Test]
        public void GetDeviceReading_AccuracyTests3()
        {
            inputAccuracyDataSet.Tables["LocationNames"].Clear();

            // add device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // invoke it with "RealTime".
            string xml = instance.GetDeviceReading(username, password, 2, inputAccuracyDataSet.GetXml(), 2,
                new DateTime(2010, 3, 23, 8, 1, 0), new DateTime(2010, 3, 23, 8, 2, 0), "Hourly");
            Assert.IsNotNull(xml, "not expected");

            DataSet ds = ParseDataSetFromXml(xml);
            Assert.AreEqual(0, ds.Tables["MinutelyReadings"].Rows.Count, "not expected");
            Assert.AreEqual(2, ds.Tables["HourlyReadings"].Rows.Count, "not expected");
            Assert.AreEqual(0, ds.Tables["MultiHourlyReadings"].Rows.Count, "not expected");

        }

        /// <summary>
        /// Test GetDeviceReading() method's accuracy.
        /// </summary>
        [Test]
        public void GetDeviceReading_AccuracyTests4()
        {
            inputAccuracyDataSet.Tables["LocationNames"].Clear();

            // add device
            instance.AddDevice(username, password, 2, inputAccuracyDataSet.GetXml());

            // invoke it with "RealTime".
            string xml = instance.GetDeviceReading(username, password, 2, inputAccuracyDataSet.GetXml(), 2,
                new DateTime(2010, 3, 23, 8, 1, 0), new DateTime(2010, 3, 23, 8, 2, 0), "Daily");
            Assert.IsNotNull(xml, "not expected");

            DataSet ds = ParseDataSetFromXml(xml);
            Assert.AreEqual(0, ds.Tables["MinutelyReadings"].Rows.Count, "not expected");
            Assert.AreEqual(0, ds.Tables["HourlyReadings"].Rows.Count, "not expected");
            Assert.AreEqual(2, ds.Tables["MultiHourlyReadings"].Rows.Count, "not expected");

        }
    }
}


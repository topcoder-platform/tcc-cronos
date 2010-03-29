/*
 * Copyright (C) 2010 TopCoder Inc., All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Data;
using System.IO;
using System.Reflection;
using System.Xml;
using NUnit.Framework;
using TopCoder.Configuration;
using TopCoder.LoggingWrapper;
using TopCoder.Util.ExceptionManager;
using TopCoder.Util.ExceptionManager.Formatters;
using TopCoder.Util.ExceptionManager.Publishers;
using Toro.TurfGuard.Common.Core.Domain.Impl;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.Common.Core.Services.Impl;

namespace Toro.TurfGuard.WebService
{
    /// <summary>
    /// <para>
    /// This class provides common methods used in the unit test.
    /// </para>
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [CoverageExclude]
    internal static class UnitTestHelper
    {
        /// <summary>
        /// Represents table name for <c>sensorinfo</c>.
        /// </summary>
        internal const string TABLE_SENSOR_INFO = "sensorinfo";

        /// <summary>
        /// Represents table name for <c>LocationNames</c>.
        /// </summary>
        internal const string TABLE_LOCATION_NAMES = "LocationNames";

        /// <summary>
        /// Represents table name for <c>HourlyReadings</c>.
        /// </summary>
        internal const string TABLE_HOURLY_READINGS = "HourlyReadings";

        /// <summary>
        /// Represents table name for <c>MultiHourlyReadings</c>.
        /// </summary>
        internal const string TABLE_MULTI_HOURLY_READINGS = "MultiHourlyReadings";

        /// <summary>
        /// Represents table name for <c>MinutelyReadings</c>.
        /// </summary>
        internal const string TABLE_MINUTELY_READINGS = "MinutelyReadings";

        /// <summary>
        /// Represents column name for <c>reading_dt</c>.
        /// </summary>
        internal const string COLUMN_READING_DT = "reading_dt";

        /// <summary>
        /// Represents column name for <c>reading_val</c>.
        /// </summary>
        internal const string COLUMN_READING_VAL = "reading_val";

        /// <summary>
        /// Represents column name for <c>Name</c>.
        /// </summary>
        internal const string COLUMN_NAME = "Name";

        /// <summary>
        /// Represents column name for <c>SerialNumber</c>.
        /// </summary>
        internal const string COLUMN_SERIAL_NUMBER = "SerialNumber";

        /// <summary>
        /// Represents column name for <c>NodeID</c>.
        /// </summary>
        internal const string COLUMN_NODE_ID = "NodeID";

        /// <summary>
        /// Represents column name for <c>X</c>.
        /// </summary>
        internal const string COLUMN_X = "X";

        /// <summary>
        /// Represents column name for <c>Y</c>.
        /// </summary>
        internal const string COLUMN_Y = "Y";

        /// <summary>
        /// Represents column name for <c>LocationType</c>.
        /// </summary>
        internal const string COLUMN_LOCATION_TYPE = "LocationType";

        /// <summary>
        /// Represents column name for <c>InService</c>.
        /// </summary>
        internal const string COLUMN_IN_SERVICE = "InService";

        /// <summary>
        /// Represents column name for <c>HoleNumber</c>.
        /// </summary>
        internal const string COLUMN_HOLE_NUMBER = "HoleNumber";

        /// <summary>
        /// Represents column name for <c>Time</c>.
        /// </summary>
        internal const string COLUMN_TIME = "Time";

        /// <summary>
        /// Represents column name for <c>Percentage</c>.
        /// </summary>
        internal const string COLUMN_PERCENTAGE = "Percentage";

        /// <summary>
        /// Represents column name for <c>Temp</c>.
        /// </summary>
        internal const string COLUMN_TEMP = "Temp";

        /// <summary>
        /// Represents column name for <c>Salt</c>.
        /// </summary>
        internal const string COLUMN_SALT = "Salt";

        /// <summary>
        /// Represents column name for <c>ID</c>.
        /// </summary>
        internal const string COLUMN_ID = "ID";

        /// <summary>
        /// Represents column name for <c>OldName</c>.
        /// </summary>
        internal const string COLUMN_OLD_NAME = "OldName";

        /// <summary>
        /// Deletes the log file(s) used during unit tests.
        /// </summary>
        internal static void DeleteLogFile()
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
        /// Asserts that a given variable is of correct type.
        /// </summary>
        ///
        /// <typeparam name="T">The type that the variable should be.</typeparam>
        ///
        /// <param name="o">The variable to check.</param>
        ///
        /// <param name="msg">The error message to display (optional). If not passed, then
        /// a default error message is used. If multiple messages are passed, only the first is used.</param>
        internal static void AssertType<T>(object o, params string[] msg)
        {
            Assert.IsTrue(o is T, msg.Length == 0 ? "Variable should be of type " + typeof(T) + "." :
                msg[0]);
        }

        /// <summary>
        /// Creates configuration object for logging wrapper.
        /// </summary>
        ///
        /// <returns>
        /// The created configuration.
        /// </returns>
        private static IConfiguration GetLoggerConfig()
        {
            IConfiguration loggerConfiguration = new DefaultConfiguration("default");
            loggerConfiguration.SetSimpleAttribute("logger_class", "TopCoder.LoggingWrapper.Log4NETImpl");
            loggerConfiguration.SetSimpleAttribute("config_file", @"..\..\test_files\log4net.config");
            loggerConfiguration.SetSimpleAttribute("logger_name", "logger");
            loggerConfiguration.SetSimpleAttribute("default_level", "INFO");
            loggerConfiguration.SetSimpleAttribute("source", "UnitTests");
            return loggerConfiguration;
        }

        /// <summary>
        /// Creates an instance of <see cref="Logger"/>.
        /// </summary>
        /// <returns>The <see cref="Logger"/> instance created.</returns>
        internal static Logger CreateLogger()
        {
            return LogManager.CreateLogger(GetLoggerConfig());
        }

        /// <summary>
        /// Creates an instance of <see cref="ExceptionPublishManager"/>.
        /// </summary>
        ///
        /// <returns>The <see cref="ExceptionPublishManager"/> instance.</returns>
        internal static ExceptionPublishManager CreateExceptionPublishManager()
        {
            IExceptionFormatter formatter = new TextFormatter();
            IExceptionPublisher publisher = new DefaultExceptionPublisher(formatter,
                new string[] { }, new object[] { }, CreateLogger());

            return new ExceptionPublishManager(new string[] { }, new IExceptionPublisher[] { }, publisher);
        }

        /// <summary>
        /// Resets all mock data used during unit tests.
        /// </summary>
        internal static void ResetMockData()
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
        /// Prepares mock data used during unit tests.
        /// </summary>
        ///
        /// <returns>A data set containing mock data used for tests.</returns>
        internal static DataSet PrepareMockData()
        {
            ResetMockData();

            // read schema from file and load it to data set
            DataSet dataSet = new DataSet();
            LoadSchemaToDataSet(dataSet);

            // prepare data set
            DataRow row = dataSet.Tables[TABLE_SENSOR_INFO].NewRow();
            row[COLUMN_HOLE_NUMBER] = 1;
            row[COLUMN_LOCATION_TYPE] = -1;
            row[COLUMN_X] = 14;
            row[COLUMN_Y] = 15;
            row[COLUMN_NODE_ID] = 1;
            row[COLUMN_SERIAL_NUMBER] = 100;
            row[COLUMN_IN_SERVICE] = new DateTime(2010, 01, 01);
            dataSet.Tables[TABLE_SENSOR_INFO].Rows.Add(row);

            row = dataSet.Tables[TABLE_LOCATION_NAMES].NewRow();
            row[COLUMN_NAME] = "loc1";
            row[COLUMN_ID] = 1;
            dataSet.Tables[TABLE_LOCATION_NAMES].Rows.Add(row);

            // prepare mock repositories
            DeviceGroup deviceGroup = new DeviceGroup {Id = 1, Name = "1"};
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
                row = MockReadingRepository.Repository["Temp"].NewRow();
                row[COLUMN_READING_DT] = readingDate;
                row[COLUMN_READING_VAL] = readingVal++;
                MockReadingRepository.Repository["Temp"].Rows.Add(row);

                row = MockReadingRepository.Repository["Salinity"].NewRow();
                row[COLUMN_READING_DT] = readingDate;
                row[COLUMN_READING_VAL] = readingVal++;
                MockReadingRepository.Repository["Salinity"].Rows.Add(row);

                row = MockReadingRepository.Repository["Percentage"].NewRow();
                row[COLUMN_READING_DT] = readingDate;
                row[COLUMN_READING_VAL] = readingVal++;
                MockReadingRepository.Repository["Percentage"].Rows.Add(row);
            }

            return dataSet;
        }

        /// <summary>
        /// Loads the XML schema used by this component to the data set.
        /// </summary>
        ///
        /// <param name="dataSet">The data set.</param>
        private static void LoadSchemaToDataSet(DataSet dataSet)
        {
            // read schema from file and load it to data set
            using (FileStream fileStream = new FileStream(@"..\..\test_files\ClientSchema.xsd", FileMode.Open))
            {
                using (XmlTextReader reader = new XmlTextReader(fileStream))
                {
                    dataSet.ReadXmlSchema(reader);
                }
            }
        }

        /// <summary>
        /// Parses XML returned by service methods into data set.
        /// </summary>
        ///
        /// <param name="xml">The XML to parse.</param>
        ///
        /// <returns>The data set for the given XML.</returns>
        internal static DataSet ParseResultSet(string xml)
        {
            // prepare the data set
            DataSet resultDataSet = new DataSet();
            LoadSchemaToDataSet(resultDataSet);

            // load returned xml to data set
            using (StringReader reader = new StringReader(xml))
            {
                // read the xml string into the data set
                resultDataSet.ReadXml(reader);
            }

            return resultDataSet;
        }

        /// <summary>
        /// Creates the readings table used for unit tests.
        /// </summary>
        ///
        /// <returns>Readings table used for unit tests.</returns>
        private static DataTable CreateReadingsTable()
        {
            DataTable readingsTable = new DataTable();
            readingsTable.Columns.Add(new DataColumn(COLUMN_READING_DT, typeof(DateTime)));
            readingsTable.Columns.Add(new DataColumn(COLUMN_READING_VAL, typeof(Single)));
            return readingsTable;
        }

        /// <summary>
        /// Uses reflection to run a method of an object.
        /// </summary>
        ///
        /// <param name="instance">The instance object.</param>
        /// <param name="methodName">The name of the method to run.</param>
        /// <param name="paramNames">The method parameter names.</param>
        /// <param name="paramTypes">The method parameter types.</param>
        /// <param name="invocationParams">The parameter values to pass when running the method.</param>
        ///
        /// <returns>The object returned from the invoked method.</returns>
        ///
        /// <exception cref="Exception">Any inner exception which is thrown by the method being invoked.</exception>
        internal static object RunInstanceMethod(object instance, string methodName, string[] paramNames,
            Type[] paramTypes, object[] invocationParams)
        {
            BindingFlags bindFlags = BindingFlags.NonPublic | BindingFlags.Instance | BindingFlags.Static |
                                     BindingFlags.Public;

            // get a list of the methods for the instance
            MethodInfo[] methods = instance.GetType().GetMethods(bindFlags);

            // search for the method, making sure the name and signature match
            for (int i = 0; i < methods.Length; ++i)
            {
                if (methods[i].Name == methodName)
                {
                    ParameterInfo[] paramInfo = methods[i].GetParameters();
                    if (paramInfo.Length != paramNames.Length)
                    {
                        continue;
                    }

                    // check method signature for a match
                    bool ok = true;
                    for (int j = 0; j < paramInfo.Length; ++j)
                    {
                        if (paramInfo[j].Name != paramNames[j] || paramInfo[j].ParameterType != paramTypes[j])
                        {
                            ok = false;
                            break;
                        }
                    }

                    if (ok)
                    {
                        try
                        {
                            // invoke the method with the invocation parameters
                            return methods[i].Invoke(instance, invocationParams);
                        }
                        catch (Exception e)
                        {
                            // only throw the inner exception
                            throw e.InnerException;
                        }
                    }
                }
            }
            return null;
        }
    }
}

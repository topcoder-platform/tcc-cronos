/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.Configuration;
using System.Data;
using System.IO;
using System.Linq;
using System.Reflection;
using System.ServiceModel;
using System.Text;
using System.Xml;
using TopCoder.Configuration;
using TopCoder.Configuration.ApplicationConfiguration;
using TopCoder.LoggingWrapper;
using TopCoder.Services.WCF;
using TopCoder.Util.ObjectFactory;
using Toro.TurfGuard.Common.Core.Domain;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.Common.Core.Services;

namespace Toro.TurfGuard.WebService.Impl
{
    /// <summary>
    /// This class is the realization of the <see cref="IDeviceService"/> that implements all methods.
    /// It mostly defers to existing client classes to perform the work.
    /// </summary>
    ///
    /// <remarks>
    /// <para>
    /// It uses a <see cref="WebApplicationConfigurationPersistenceProvider"/> to get to its custom configuration
    /// in the IIS web.config file. If there are errors during any operation, they are logged using the
    /// Logging Wrapper component.
    /// </para>
    ///
    /// <threadsafety>
    /// <para>
    /// This class is immutable and thread-safe.
    /// </para>
    /// </threadsafety>
    ///
    /// <example>
    /// Sample Configuration:
    /// <code>
    /// &lt;child name="device_service_configuration"&gt;
    ///  &lt;properties&gt;
    ///      &lt;property name="user_repository_key" value="user_repository_key"
    ///         valueType="System.String" /&gt;
    ///      &lt;property name="authentication_service_key" value="authentication_service_key"
    ///         valueType="System.String" /&gt;
    ///      &lt;property name="device_repository_key" value="device_repository_key"
    ///         valueType="System.String" /&gt;
    ///      &lt;property name="device_group_repository_key" value="device_group_repository_key"
    ///         valueType="System.String" /&gt;
    ///      &lt;property name="device_install_repository_key" value="device_install_repository_key"
    ///         valueType="System.String" /&gt;
    ///      &lt;property name="reading_repository_key" value="reading_repository_key"
    ///         valueType="System.String" /&gt;
    ///      &lt;property name="xsd_directory" value="..\..\test_files"
    ///         valueType="System.String" /&gt;
    ///  &lt;/properties&gt;
    ///
    ///  &lt;children&gt;
    ///      &lt;child name="object_factory_configuration"&gt;
    ///          &lt;children&gt;
    ///              &lt;child name="object_1"&gt;
    ///                  &lt;properties&gt;
    ///                      &lt;property name="name" value="user_repository_key" valueType="System.String" /&gt;
    ///                  &lt;/properties&gt;
    ///                  &lt;children&gt;
    ///                      &lt;child name="type_name"&gt;
    ///                          &lt;properties&gt;
    ///                              &lt;property name="value"
    ///                                   value="Toro.TurfGuard.Common.Core.Domain.Impl.MockUserRepository,
    ///                                      Toro.TurfGuard.WebService.Test"
    ///                                   valueType="System.String" /&gt;
    ///                          &lt;/properties&gt;
    ///                      &lt;/child&gt;
    ///                  &lt;/children&gt;
    ///              &lt;/child&gt;
    ///              &lt;child name="object_2"&gt;
    ///                  &lt;properties&gt;
    ///                      &lt;property name="name" value="authentication_service_key"
    ///                      valueType="System.String" /&gt;
    ///                  &lt;/properties&gt;
    ///                  &lt;children&gt;
    ///                      &lt;child name="type_name"&gt;
    ///                          &lt;properties&gt;
    ///                              &lt;property name="value"
    ///                                   value="Toro.TurfGuard.Common.Core.Services.Impl.MockAuthenticationService,
    ///                                      Toro.TurfGuard.WebService.Test"
    ///                                   valueType="System.String" /&gt;
    ///                          &lt;/properties&gt;
    ///                      &lt;/child&gt;
    ///                  &lt;/children&gt;
    ///              &lt;/child&gt;
    ///              &lt;child name="object_3"&gt;
    ///                  &lt;properties&gt;
    ///                      &lt;property name="name" value="device_repository_key" valueType="System.String" /&gt;
    ///                  &lt;/properties&gt;
    ///                  &lt;children&gt;
    ///                      &lt;child name="type_name"&gt;
    ///                          &lt;properties&gt;
    ///                              &lt;property name="value"
    ///                                   value="Toro.TurfGuard.Common.Core.Domain.Impl.MockDeviceRepository,
    ///                                      Toro.TurfGuard.WebService.Test"
    ///                                   valueType="System.String" /&gt;
    ///                          &lt;/properties&gt;
    ///                      &lt;/child&gt;
    ///                  &lt;/children&gt;
    ///              &lt;/child&gt;
    ///              &lt;child name="object_4"&gt;
    ///                  &lt;properties&gt;
    ///                      &lt;property name="name" value="device_group_repository_key"
    ///                      valueType="System.String" /&gt;
    ///                  &lt;/properties&gt;
    ///                  &lt;children&gt;
    ///                      &lt;child name="type_name"&gt;
    ///                          &lt;properties&gt;
    ///                              &lt;property name="value"
    ///                                   value="Toro.TurfGuard.Common.Core.Domain.Impl.MockDeviceGroupRepository,
    ///                                      Toro.TurfGuard.WebService.Test"
    ///                                   valueType="System.String" /&gt;
    ///                          &lt;/properties&gt;
    ///                      &lt;/child&gt;
    ///                  &lt;/children&gt;
    ///              &lt;/child&gt;
    ///              &lt;child name="object_5"&gt;
    ///                  &lt;properties&gt;
    ///                      &lt;property name="name" value="device_install_repository_key"
    ///                      valueType="System.String" /&gt;
    ///                  &lt;/properties&gt;
    ///                  &lt;children&gt;
    ///                      &lt;child name="type_name"&gt;
    ///                          &lt;properties&gt;
    ///                              &lt;property name="value"
    ///                                   value="Toro.TurfGuard.Common.Core.Domain.Impl.MockDeviceInstallRepository,
    ///                                      Toro.TurfGuard.WebService.Test"
    ///                                   valueType="System.String" /&gt;
    ///                          &lt;/properties&gt;
    ///                      &lt;/child&gt;
    ///                  &lt;/children&gt;
    ///              &lt;/child&gt;
    ///              &lt;child name="object_6"&gt;
    ///                  &lt;properties&gt;
    ///                      &lt;property name="name" value="reading_repository_key" valueType="System.String" /&gt;
    ///                  &lt;/properties&gt;
    ///                  &lt;children&gt;
    ///                      &lt;child name="type_name"&gt;
    ///                          &lt;properties&gt;
    ///                              &lt;property name="value"
    ///                                   value="Toro.TurfGuard.Common.Core.Domain.Impl.MockReadingRepository,
    ///                                      Toro.TurfGuard.WebService.Test"
    ///                                   valueType="System.String" /&gt;
    ///                          &lt;/properties&gt;
    ///                      &lt;/child&gt;
    ///                  &lt;/children&gt;
    ///              &lt;/child&gt;
    ///          &lt;/children&gt;
    ///      &lt;/child&gt;
    ///
    ///      &lt;child name="logging_wrapper_configuration"&gt;
    ///          &lt;properties&gt;
    ///              &lt;property name="logger_class"
    ///                 value="TopCoder.LoggingWrapper.Log4NETImpl" valueType="System.String" /&gt;
    ///              &lt;property name="config_file" value="..\..\test_files\log4net.config"
    ///                 valueType="System.String" /&gt;
    ///              &lt;property name="logger_name" value="logger" valueType="System.String" /&gt;
    ///              &lt;property name="default_level" value="INFO" valueType="System.String" /&gt;
    ///              &lt;property name="source" value="UnitTests" valueType="System.String" /&gt;
    ///          &lt;/properties&gt;
    ///      &lt;/child&gt;
    ///  &lt;/children&gt;
    /// &lt;/child&gt;
    /// </code>
    /// </example>
    ///
    /// Demo
    /// <code>
    /// public void DemoUsage()
    /// {
    ///     // the client to use for the demo
    ///     DeviceServiceClient client = null;
    ///
    ///     // create and open the service host using WCF technology
    ///     using (ServiceHost serviceHost =
    ///         new TCWcfServiceHost(typeof(DeviceService),
    ///             UnitTestHelper.CreateLogger(),
    ///             UnitTestHelper.CreateExceptionPublishManager()))
    ///     {
    ///         try
    ///         {
    ///             // open the service host
    ///             serviceHost.Open();
    ///             Console.WriteLine("Service host opened successfully...");
    ///
    ///             // create the client
    ///             client = new DeviceServiceClient();
    ///
    ///             // add devices, 2 devices for site 1 and 1 device for site 2
    ///             client.AddDevice("user1", "password1", 1, dataSet.GetXml());
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_NODE_ID] = 2;
    ///             client.AddDevice("user1", "password1", 1, dataSet.GetXml());
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_NODE_ID] = 3;
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 2;
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = -2;
    ///             client.AddDevice("user1", "password1", 2, dataSet.GetXml());
    ///
    ///             // prepare for update
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_HOLE_NUMBER] = 2;
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_LOCATION_TYPE] = -2;
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_IN_SERVICE] =
    ///                 new DateTime(2012, 1, 1);
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_X] = 150;
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_Y] = 160;
    ///             dataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows[0][UnitTestHelper.COLUMN_SERIAL_NUMBER] = "200";
    ///
    ///             // update the device
    ///             client.UpdateDevice("user1", "password1", 2, dataSet.GetXml());
    ///
    ///             // fetch devices for site 1
    ///             string xml = client.GetAllDevices("user1", "password1", 1);
    ///             DataSet resultDataSet = UnitTestHelper.ParseResultSet(xml);
    ///             Console.WriteLine("There were " +
    ///                 resultDataSet.Tables[UnitTestHelper.TABLE_SENSOR_INFO].
    ///                 Rows.Count + " devices fetched.");
    ///
    ///             // add device group
    ///             client.AddDeviceGroup("user1", "password1", 1, dataSet.GetXml());
    ///
    ///             // prepare for update
    ///             MockDeviceGroupRepository.DeviceGroupsForSite[1] =
    ///                 new List&lt;DeviceGroup&gt;();
    ///             MockDeviceGroupRepository.DeviceGroupsForSite[1].Add(
    ///                 MockDeviceGroupRepository.Repository["loc1"]);
    ///             dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].
    ///                 Rows[0][UnitTestHelper.COLUMN_OLD_NAME] = "loc1";
    ///             dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].
    ///                 Rows[0][UnitTestHelper.COLUMN_NAME] = "loc2";
    ///
    ///             // update device group
    ///             client.UpdateDeviceGroup("user1", "password1", 1, dataSet.GetXml());
    ///
    ///             // get the primary device groups for site 1
    ///             xml = client.GetAllDeviceGroups("user1", "password1", 1,
    ///                 DeviceGroup.PRIMARY);
    ///             resultDataSet = UnitTestHelper.ParseResultSet(xml);
    ///             Console.WriteLine("There were " +
    ///                 resultDataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].
    ///                 Rows.Count + " device groups fetched.");
    ///
    ///             dataSet.Tables[UnitTestHelper.TABLE_LOCATION_NAMES].Rows.Clear();
    ///
    ///             // get real time readings on a device
    ///             xml = client.GetDeviceReading("user1", "password1", 1,
    ///                 dataSet.GetXml(), 1,
    ///                 new DateTime(2010, 1, 1, 0, 1, 0),
    ///                 new DateTime(2010, 1, 1, 0, 2, 0),
    ///                 "RealTime");
    ///             resultDataSet = UnitTestHelper.ParseResultSet(xml);
    ///             Console.WriteLine("There were " +
    ///                 resultDataSet.Tables[UnitTestHelper.TABLE_MINUTELY_READINGS].
    ///                 Rows.Count + " real time readings fetched.");
    ///         }
    ///         finally
    ///         {
    ///             if (client != null)
    ///             {
    ///                 // close the client
    ///                 try
    ///                 {
    ///                     client.Close();
    ///                 }
    ///                 catch
    ///                 {
    ///                     client.Abort();
    ///                 }
    ///                 Console.WriteLine("Client closed.");
    ///             }
    ///
    ///             // close the host
    ///             try
    ///             {
    ///                 serviceHost.Close();
    ///             }
    ///             catch
    ///             {
    ///                 serviceHost.Abort();
    ///             }
    ///             Console.WriteLine("Service host closed.");
    ///
    ///             Console.WriteLine("Demo complete.");
    ///         }
    ///     }
    /// }
    /// </code>
    ///
    /// </remarks>
    ///
    /// <author>argolite</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [ServiceBehavior(
        ConcurrencyMode = ConcurrencyMode.Multiple,
        InstanceContextMode = InstanceContextMode.PerSession)]
    public class DeviceService : IDeviceService
    {
        /// <summary>
        /// Represents the namespace used to retrieve the configuration object for this class in web.config.
        /// The configuration is expected to be stored in default configuration section.
        /// </summary>
        private const string NAMESPACE = "Toro.TurfGuard.WebService.Impl.DeviceService";

        /// <summary>
        /// Represents the name of the configuration node under default configuration that contains
        /// the configuration used for this class.
        /// </summary>
        private const string CONFIG_NAME = "device_service_configuration";

        /// <summary>
        /// Represents the configuration key for <c>object_factory_configuration</c>.
        /// </summary>
        private const string CONFIG_KEY_OBJECT_FACTORY_CONFIGURATION = "object_factory_configuration";

        /// <summary>
        /// Represents the configuration key for <c>logging_wrapper_configuration</c>.
        /// </summary>
        private const string CONFIG_KEY_LOGGING_WRAPPER_CONFIGURATION = "logging_wrapper_configuration";

        /// <summary>
        /// Represents the configuration key for <c>user_repository_key</c>.
        /// </summary>
        private const string CONFIG_KEY_USER_REPOSITORY_KEY = "user_repository_key";

        /// <summary>
        /// Represents the configuration key for <c>authentication_service_key</c>.
        /// </summary>
        private const string CONFIG_KEY_AUTHENTICATION_SERVICE_KEY = "authentication_service_key";

        /// <summary>
        /// Represents the configuration key for <c>device_repository_key</c>.
        /// </summary>
        private const string CONFIG_KEY_DEVICE_REPOSITORY_KEY = "device_repository_key";

        /// <summary>
        /// Represents the configuration key for <c>device_group_repository_key</c>.
        /// </summary>
        private const string CONFIG_KEY_DEVICE_GROUP_REPOSITORY_KEY = "device_group_repository_key";

        /// <summary>
        /// Represents the configuration key for <c>device_install_repository_key</c>.
        /// </summary>
        private const string CONFIG_KEY_DEVICE_INSTALL_REPOSITORY_KEY = "device_install_repository_key";

        /// <summary>
        /// Represents the configuration key for <c>reading_repository_key</c>.
        /// </summary>
        private const string CONFIG_KEY_READING_REPOSITORY_KEY = "reading_repository_key";

        /// <summary>
        /// Represents the configuration key for <c>xsd_directory</c>.
        /// </summary>
        private const string CONFIG_KEY_XSD_DIRECTORY = "xsd_directory";

        /// <summary>
        /// Represents the filename containing the schema used to configure the <see cref="dataSetTemplate"/> data
        /// set in the constructor.
        /// </summary>
        private const string SCHEMA_NAME = "ClientSchema.xsd";

        /// <summary>
        /// Represents the <c>Hourly</c> scale value.
        /// </summary>
        private const string SCALE_HOURLY = "Hourly";

        /// <summary>
        /// Represents the <c>RealTime</c> scale value.
        /// </summary>
        private const string SCALE_REAL_TIME = "RealTime";

        /// <summary>
        /// Represents the <c>Daily</c> scale value.
        /// </summary>
        private const string SCALE_DAILY = "Daily";

        /// <summary>
        /// Represents the <c>Temp</c> reading type.
        /// </summary>
        private const string READING_TYPE_TEMP = "Temp";

        /// <summary>
        /// Represents the <c>Salinity</c> reading type.
        /// </summary>
        private const string READING_TYPE_SALINITY = "Salinity";

        /// <summary>
        /// Represents the <c>Percentage</c> reading type.
        /// </summary>
        private const string READING_TYPE_PERCENTAGE = "Percentage";

        /// <summary>
        /// Represents table name for <c>sensorinfo</c>.
        /// </summary>
        private const string TABLE_SENSOR_INFO = "sensorinfo";

        /// <summary>
        /// Represents table name for <c>LocationNames</c>.
        /// </summary>
        private const string TABLE_LOCATION_NAMES = "LocationNames";

        /// <summary>
        /// Represents table name for <c>MinutelyReadings</c>.
        /// </summary>
        private const string TABLE_MINUTELY_READINGS = "MinutelyReadings";

        /// <summary>
        /// Represents table name for <c>HourlyReadings</c>.
        /// </summary>
        private const string TABLE_HOURLY_READINGS = "HourlyReadings";

        /// <summary>
        /// Represents table name for <c>MultiHourlyReadings</c>.
        /// </summary>
        private const string TABLE_MULTI_HOURLY_READINGS = "MultiHourlyReadings";

        /// <summary>
        /// Represents column name for <c>reading_dt</c>.
        /// </summary>
        private const string COLUMN_READING_DT = "reading_dt";

        /// <summary>
        /// Represents column name for <c>reading_val</c>.
        /// </summary>
        private const string COLUMN_READING_VAL = "reading_val";

        /// <summary>
        /// Represents column name for <c>Name</c>.
        /// </summary>
        private const string COLUMN_NAME = "Name";

        /// <summary>
        /// Represents column name for <c>SerialNumber</c>.
        /// </summary>
        private const string COLUMN_SERIAL_NUMBER = "SerialNumber";

        /// <summary>
        /// Represents column name for <c>NodeID</c>.
        /// </summary>
        private const string COLUMN_NODE_ID = "NodeID";

        /// <summary>
        /// Represents column name for <c>X</c>.
        /// </summary>
        private const string COLUMN_X = "X";

        /// <summary>
        /// Represents column name for <c>Y</c>.
        /// </summary>
        private const string COLUMN_Y = "Y";

        /// <summary>
        /// Represents column name for <c>InService</c>.
        /// </summary>
        private const string COLUMN_IN_SERVICE = "InService";

        /// <summary>
        /// Represents column name for <c>LocationType</c>.
        /// </summary>
        private const string COLUMN_LOCATION_TYPE = "LocationType";

        /// <summary>
        /// Represents column name for <c>HoleNumber</c>.
        /// </summary>
        private const string COLUMN_HOLE_NUMBER = "HoleNumber";

        /// <summary>
        /// Represents column name for <c>Time</c>.
        /// </summary>
        private const string COLUMN_TIME = "Time";

        /// <summary>
        /// Represents column name for <c>Percentage</c>.
        /// </summary>
        private const string COLUMN_PERCENTAGE = "Percentage";

        /// <summary>
        /// Represents column name for <c>Temp</c>.
        /// </summary>
        private const string COLUMN_TEMP = "Temp";

        /// <summary>
        /// Represents column name for <c>Salt</c>.
        /// </summary>
        private const string COLUMN_SALT = "Salt";

        /// <summary>
        /// Represents column name for <c>ID</c>.
        /// </summary>
        private const string COLUMN_ID = "ID";

        /// <summary>
        /// Represents column name for <c>OldName</c>.
        /// </summary>
        private const string COLUMN_OLD_NAME = "OldName";

        /// <summary>
        /// Represents the instance of the logger used for logging in this service.
        /// </summary>
        ///
        /// <remarks>
        /// It is set in the constructor and used in all public methods. It will not be null. Once
        /// set it will not be changed.
        /// </remarks>
        private readonly Logger logger;

        /// <summary>
        /// Represents the user repository instance.
        /// </summary>
        ///
        /// <remarks>
        /// It is set in the constructor and used in the <c>AuthenticateUser</c> and <c>GetSite</c> methods.
        /// It will not be null. Once set it will not be changed.
        /// </remarks>
        private readonly IUserRepository userRepository;

        /// <summary>
        /// Represents the authentication service instance.
        /// </summary>
        ///
        /// <remarks>
        /// It is set in the constructor and used in the <c>AuthenticateUser</c> method. It will not be null.
        /// Once set it will not be changed.
        /// </remarks>
        private readonly IAuthenticationService authenticationService;

        /// <summary>
        /// Represents the device repository instance.
        /// </summary>
        ///
        /// <remarks>
        /// It is set in the constructor and used in all device management methods. It will not be null.
        /// Once set it will not be changed.
        /// </remarks>
        private readonly IDeviceRepository deviceRepository;

        /// <summary>
        /// Represents the device group repository instance.
        /// </summary>
        ///
        /// <remarks>
        /// It is set in the constructor and used in all device group management methods. It will not be null.
        /// Once set it will not be changed.
        /// </remarks>
        private readonly IDeviceGroupRepository deviceGroupRepository;

        /// <summary>
        /// Represents the device install repository instance.
        /// </summary>
        ///
        /// <remarks>
        /// It is set in the constructor and used in methods that deal with device installs. It will not be null.
        /// Once set it will not be changed.
        /// </remarks>
        private readonly IDeviceInstallRepository deviceInstallRepository;

        /// <summary>
        /// Represents the device reading repository instance.
        /// </summary>
        ///
        /// <remarks>
        /// It is set in the constructor and used in the <c>GetDeviceReading</c> method. It will not be null.
        /// Once set it will not be changed.
        /// </remarks>
        private readonly IReadingRepository readingRepository;

        /// <summary>
        /// Represents the DataSet that will be the template for all data sets. It will be configured with the
        /// <c>ClientSchema</c> xsd file.
        /// </summary>
        ///
        /// <remarks>
        /// This template will allow the creation of the correctly configured data set to be done much faster.
        /// It is set in the constructor and used in all management methods. It will not be null.
        /// Once set it will not be changed.
        /// </remarks>
        private readonly DataSet dataSetTemplate;

        /// <summary>
        /// Default constructor which constructs a new instance of this class.
        /// </summary>
        ///
        /// <exception cref="DeviceServiceConfigurationException">
        /// If any configuration error occurs, such as missing required configuration parameter, encountering
        /// invalid configuration parameter, or some other problem.
        /// </exception>
        public DeviceService()
        {
            try
            {
                // create the web application configuration provider
                WebApplicationConfigurationPersistenceProvider provider =
                    new WebApplicationConfigurationPersistenceProvider(
                        ConfigurationManager.AppSettings[NAMESPACE]);

                // fetch the configuration for this class
                IConfiguration config = provider.GetConfiguration(CONFIG_NAME);
                if (config == null)
                {
                    throw new DeviceServiceConfigurationException(
                        string.Format("The configuration for {0} was not found.", NAMESPACE));
                }

                // get the object factory configuration
                IConfiguration objectFactoryConfig =
                    GetRequiredChildConfiguration(config, CONFIG_KEY_OBJECT_FACTORY_CONFIGURATION);

                // create the object factory instance
                ConfigurationAPIObjectFactory objectFactory =
                    new ConfigurationAPIObjectFactory(objectFactoryConfig);

                //
                // create the required objects using the object factory
                //

                userRepository = CreateObject<IUserRepository>(objectFactory, config,
                    CONFIG_KEY_USER_REPOSITORY_KEY);

                authenticationService = CreateObject<IAuthenticationService>(objectFactory, config,
                    CONFIG_KEY_AUTHENTICATION_SERVICE_KEY);

                deviceRepository = CreateObject<IDeviceRepository>(objectFactory, config,
                    CONFIG_KEY_DEVICE_REPOSITORY_KEY);

                deviceGroupRepository = CreateObject<IDeviceGroupRepository>(objectFactory, config,
                    CONFIG_KEY_DEVICE_GROUP_REPOSITORY_KEY);

                deviceInstallRepository = CreateObject<IDeviceInstallRepository>(objectFactory, config,
                    CONFIG_KEY_DEVICE_INSTALL_REPOSITORY_KEY);

                readingRepository = CreateObject<IReadingRepository>(objectFactory, config,
                    CONFIG_KEY_READING_REPOSITORY_KEY);

                //
                // prepare the data set template
                //

                dataSetTemplate = new DataSet();

                // get the path to xsd file
                string xsdPath =
                    string.Format("{0}{1}{2}",
                        GetStringAttribute(config, CONFIG_KEY_XSD_DIRECTORY),
                        Path.DirectorySeparatorChar,
                        SCHEMA_NAME);

                // read schema from file and load it to data set template
                try
                {
                    using (XmlReader reader = XmlReader.Create(xsdPath))
                    {
                        dataSetTemplate.ReadXmlSchema(reader);
                    }
                }
                catch (Exception e)
                {
                    throw new DeviceServiceConfigurationException(
                        "Could not load client schema to data set template.", e);
                }

                // get the logging wrapper configuration
                IConfiguration loggingWrapperConfig =
                    GetRequiredChildConfiguration(config, CONFIG_KEY_LOGGING_WRAPPER_CONFIGURATION);

                // create the logger
                logger = LogManager.CreateLogger(loggingWrapperConfig);
            }
            catch (DeviceServiceConfigurationException)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new DeviceServiceConfigurationException(
                    "An error occurred creating the DeviceService class instance.", e);
            }
        }

        /// <summary>
        /// Adds the device in the <paramref name="input"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device.
        /// </param>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationBehavior(TransactionScopeRequired = false)]
        public void AddDevice(string username, string password, int siteID, string input)
        {
            try
            {
                DateTime methodEntryTime = DateTime.Now;
                LogMethodEntry(logger, MethodBase.GetCurrentMethod(), methodEntryTime, username, password, siteID,
                    input);

                ValidateNotNullOrEmpty(username, "username");
                ValidateNotNullOrEmpty(password, "password");
                ValidateNotNullOrEmpty(input, "input");

                // authenticate the user
                AuthenticateUser(username, password, siteID);

                // get the device and device install from sensor info row in input XML
                Site site = GetSite(username, siteID);
                DataRow sensorInfoRow = GetRowFromDataSet(ReadDataSet(input), TABLE_SENSOR_INFO);
                DeviceInstall deviceInstall = BuildDeviceInstallFromSensorInfoRow(null, sensorInfoRow, site);
                Device device = BuildDeviceFromSensorInfoRow(sensorInfoRow, site);

                // add the device install to the device
                device.AddDeviceInstall(deviceInstall);

                // add the device to the repository
                deviceRepository.Add(device);

                LogMethodExit(logger, MethodBase.GetCurrentMethod(), methodEntryTime);
            }
            catch (Exception e)
            {
                throw WrapAndLogException(MethodBase.GetCurrentMethod(), e, "An error occurred adding device.");
            }
        }

        /// <summary>
        /// Updates the device in the <paramref name="input"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device.
        /// </param>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationBehavior(TransactionScopeRequired = false)]
        public void UpdateDevice(string username, string password, int siteID, string input)
        {
            try
            {
                DateTime methodEntryTime = DateTime.Now;
                LogMethodEntry(logger, MethodBase.GetCurrentMethod(), methodEntryTime, username, password, siteID,
                    input);

                ValidateNotNullOrEmpty(username, "username");
                ValidateNotNullOrEmpty(password, "password");
                ValidateNotNullOrEmpty(input, "input");

                // authenticate the user
                AuthenticateUser(username, password, siteID);

                // get the device from the input XML
                Site site = GetSite(username, siteID);
                DataRow sensorInfoRow = GetRowFromDataSet(ReadDataSet(input), TABLE_SENSOR_INFO);
                Device passedDevice = BuildDeviceFromSensorInfoRow(sensorInfoRow, site);

                // get the device from repository
                Device device = deviceRepository.GetById(passedDevice.NodeId);
                if (device == null)
                {
                    throw new InvalidDataInputException("Could not find device in repository.");
                }

                // update the object values
                device.Site = passedDevice.Site;
                device.SerialNumber = passedDevice.SerialNumber;

                // update the current device install
                DeviceInstall currentDeviceInstall = GetCurrentDeviceInstall(device);
                BuildDeviceInstallFromSensorInfoRow(currentDeviceInstall, sensorInfoRow, site);

                // save the device to repository
                deviceRepository.Save(device);

                LogMethodExit(logger, MethodBase.GetCurrentMethod(), methodEntryTime);
            }
            catch (Exception e)
            {
                throw WrapAndLogException(MethodBase.GetCurrentMethod(), e, "An error occurred updating device.");
            }
        }

        /// <summary>
        /// Gets all devices for the given <paramref name="siteID"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <returns>
        /// An XML string with the devices.
        /// </returns>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationBehavior(TransactionScopeRequired = false)]
        public string GetAllDevices(string username, string password, int siteID)
        {
            try
            {
                DateTime methodEntryTime = DateTime.Now;
                LogMethodEntry(logger, MethodBase.GetCurrentMethod(), methodEntryTime, username, password, siteID);

                ValidateNotNullOrEmpty(username, "username");
                ValidateNotNullOrEmpty(password, "password");

                // authenticate the user
                AuthenticateUser(username, password, siteID);

                // get all devices for site
                Site site = GetSite(username, siteID);
                Device[] devices = deviceRepository.GetAllForSite(site);

                // get new copy of data set
                DataSet dataSet = dataSetTemplate.Copy();

                // package devices into data set
                foreach (Device device in devices)
                {
                    // find the device install that is current
                    DeviceInstall currentInstall = GetCurrentDeviceInstall(device);

                    // populate the row and add to data set
                    DataRow row = dataSet.Tables[TABLE_SENSOR_INFO].NewRow();
                    row[COLUMN_HOLE_NUMBER] = currentInstall.PrimaryGroup.Name;
                    row[COLUMN_LOCATION_TYPE] = currentInstall.SecondaryGroup.Name;
                    row[COLUMN_X] = Convert.ToInt32(currentInstall.Latitude);
                    row[COLUMN_Y] = Convert.ToInt32(currentInstall.Longitude);
                    row[COLUMN_IN_SERVICE] = currentInstall.InstallationDate;
                    row[COLUMN_NODE_ID] = device.NodeId;
                    row[COLUMN_SERIAL_NUMBER] = device.SerialNumber;
                    dataSet.Tables[TABLE_SENSOR_INFO].Rows.Add(row);
                }

                // write data set to output XML which will be returned
                string output = WriteDataSet(dataSet);

                LogMethodExit(logger, MethodBase.GetCurrentMethod(), output, methodEntryTime);

                return output;
            }
            catch (Exception e)
            {
                throw WrapAndLogException(MethodBase.GetCurrentMethod(), e, "An error occurred getting all devices.");
            }
        }

        /// <summary>
        /// Adds the device group in the <paramref name="input"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device group.
        /// </param>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationBehavior(TransactionScopeRequired = false)]
        public void AddDeviceGroup(string username, string password, int siteID, string input)
        {
            try
            {
                DateTime methodEntryTime = DateTime.Now;
                LogMethodEntry(logger, MethodBase.GetCurrentMethod(), methodEntryTime, username, password, siteID,
                    input);

                ValidateNotNullOrEmpty(username, "username");
                ValidateNotNullOrEmpty(password, "password");
                ValidateNotNullOrEmpty(input, "input");

                // authenticate the user
                AuthenticateUser(username, password, siteID);

                // get the device group from the input XML
                DataRow locationNamesRow = GetRowFromDataSet(ReadDataSet(input), TABLE_LOCATION_NAMES);
                DeviceGroup deviceGroup = BuildDeviceGroupFromLocationNamesRow(locationNamesRow);

                // save device group to repository
                deviceGroupRepository.Save(deviceGroup);

                LogMethodExit(logger, MethodBase.GetCurrentMethod(), methodEntryTime);
            }
            catch (Exception e)
            {
                throw WrapAndLogException(MethodBase.GetCurrentMethod(), e, "An error occurred adding device group.");
            }
        }

        /// <summary>
        /// Updates the device group in the <paramref name="input"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device group.
        /// </param>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationBehavior(TransactionScopeRequired = false)]
        public void UpdateDeviceGroup(string username, string password, int siteID, string input)
        {
            try
            {
                DateTime methodEntryTime = DateTime.Now;
                LogMethodEntry(logger, MethodBase.GetCurrentMethod(), methodEntryTime, username, password, siteID,
                    input);

                ValidateNotNullOrEmpty(username, "username");
                ValidateNotNullOrEmpty(password, "password");
                ValidateNotNullOrEmpty(input, "input");

                // authenticate the user
                AuthenticateUser(username, password, siteID);

                // get location names row from input XML
                DataRow locationNamesRow = GetRowFromDataSet(ReadDataSet(input), TABLE_LOCATION_NAMES);

                // select group whose name is LocationNames.OldName
                Site site = GetSite(username, siteID);
                DeviceGroup[] groups = deviceGroupRepository.GetForSite(site, DeviceGroup.PRIMARY);
                string oldName = Convert.ToString(locationNamesRow[COLUMN_OLD_NAME]);
                DeviceGroup deviceGroup = groups.Where(x => x.Name == oldName).SingleOrDefault();
                if (deviceGroup == null)
                {
                    throw new InvalidDataInputException("Unable to find device group for old name = " + oldName);
                }

                // update fields in the group
                ValidateColumnValue(locationNamesRow, COLUMN_NAME);
                deviceGroup.Name = Convert.ToString(locationNamesRow[COLUMN_NAME]);

                // save device group to repository
                deviceGroupRepository.Save(deviceGroup);

                LogMethodExit(logger, MethodBase.GetCurrentMethod(), methodEntryTime);
            }
            catch (Exception e)
            {
                throw WrapAndLogException(MethodBase.GetCurrentMethod(), e,
                    "An error occurred updating device group.");
            }
        }

        /// <summary>
        /// Gets all device groups for the given <paramref name="siteID"/> and <paramref name="deviceGroupType"/>.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="deviceGroupType">
        /// The device group type.
        /// </param>
        ///
        /// <returns>
        /// The XML with the device groups.
        /// </returns>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationBehavior(TransactionScopeRequired = false)]
        public string GetAllDeviceGroups(string username, string password, int siteID, int deviceGroupType)
        {
            try
            {
                DateTime methodEntryTime = DateTime.Now;
                LogMethodEntry(logger, MethodBase.GetCurrentMethod(), methodEntryTime, username, password, siteID,
                    deviceGroupType);

                ValidateNotNullOrEmpty(username, "username");
                ValidateNotNullOrEmpty(password, "password");

                // authenticate the user
                AuthenticateUser(username, password, siteID);

                // get site and device groups
                Site site = GetSite(username, siteID);
                DeviceGroup[] groups = deviceGroupRepository.GetForSite(site, deviceGroupType);

                // get new copy of data set
                DataSet dataSet = dataSetTemplate.Copy();

                // package device groups into data set
                foreach (DeviceGroup group in groups)
                {
                    DataRow row = dataSet.Tables[TABLE_LOCATION_NAMES].NewRow();
                    row[COLUMN_NAME] = group.Name;
                    row[COLUMN_ID] = group.Id;

                    dataSet.Tables[TABLE_LOCATION_NAMES].Rows.Add(row);
                }

                // write data set to output XML which will be returned
                string output = WriteDataSet(dataSet);

                LogMethodExit(logger, MethodBase.GetCurrentMethod(), output, methodEntryTime);

                return output;
            }
            catch (Exception e)
            {
                throw WrapAndLogException(MethodBase.GetCurrentMethod(), e,
                    "An error occurred getting all device groups.");
            }
        }

        /// <summary>
        /// Gets all device readings for the given parameters.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <param name="input">
        /// The XML with the device or device group.
        /// </param>
        ///
        /// <param name="readingDepth">
        /// The reading depth id.
        /// </param>
        ///
        /// <param name="startDate">
        /// The start date of the search range.
        /// </param>
        ///
        /// <param name="endDate">
        /// The end date of the search range.
        /// </param>
        ///
        /// <param name="scale">
        /// The scale of the search.
        /// </param>
        ///
        /// <returns>
        /// The XML with the device readings.
        /// </returns>
        ///
        /// <exception cref="FaultException{TCFaultException}">
        /// <para>If any error occurs, <see cref="TCFaultException"/> will be created from:</para>
        /// <para><see cref="ArgumentNullException"/> if any string parameter is null.</para>
        /// <para><see cref="ArgumentException"/> if any string parameter is empty, or if scale is not exactly
        /// <c>Hourly</c>, <c>RealTime</c>, or <c>Daily</c>, or if start date is greater than end date.</para>
        /// <para><see cref="InvalidDataInputException"/> if there is some problem with the input XML.</para>
        /// <para><see cref="AuthenticationException"/> if user cannot be authenticated.</para>
        /// <para><see cref="DeviceServiceException"/> if some unknown error occurs, such as error
        /// in service call to a repository, etc.</para>
        /// </exception>
        [OperationBehavior(TransactionScopeRequired = false)]
        public string GetDeviceReading(string username, string password, int siteID, string input, int readingDepth,
            DateTime startDate, DateTime endDate, string scale)
        {
            try
            {
                DateTime methodEntryTime = DateTime.Now;
                LogMethodEntry(logger, MethodBase.GetCurrentMethod(), methodEntryTime, username, password, siteID,
                    input, readingDepth, startDate, endDate, scale);

                ValidateNotNullOrEmpty(username, "username");
                ValidateNotNullOrEmpty(password, "password");
                ValidateNotNullOrEmpty(input, "input");
                ValidateNotNullOrEmpty(scale, "scale");
                if (startDate > endDate)
                {
                    throw new ArgumentException("startDate must be <= endDate.", "startDate");
                }

                // authenticate the user
                AuthenticateUser(username, password, siteID);

                // read the input into data set
                DataSet dataSet = ReadDataSet(input);

                // input XML must have exactly one sensor info row or one location names row
                if (dataSet.Tables[TABLE_SENSOR_INFO].Rows.Count +
                    dataSet.Tables[TABLE_LOCATION_NAMES].Rows.Count != 1)
                {
                    throw new InvalidDataInputException(
                        "The input XML must have exactly one sensor info row or one location names row.");
                }

                Site site = GetSite(username, siteID);

                // populate the reading query to use for getting readings
                ReadingQuery query = new ReadingQuery();
                query.Depth = new ReadingDepth { Id = readingDepth };
                query.StartDate = startDate;
                query.EndDate = endDate;
                query.Scale = scale;

                // get device install or device group and load it to query
                if (dataSet.Tables[TABLE_SENSOR_INFO].Rows.Count == 1)
                {
                    Device device = BuildDeviceFromSensorInfoRow(dataSet.Tables[TABLE_SENSOR_INFO].Rows[0], site);
                    query.DeviceInstall = GetCurrentDeviceInstall(device);
                }
                else
                {
                    query.Group = BuildDeviceGroupFromLocationNamesRow(dataSet.Tables[TABLE_LOCATION_NAMES].Rows[0]);
                }

                // get the readings for each type
                query.Type = new ReadingType { Name = READING_TYPE_TEMP };
                DataTable tempReadings = readingRepository.GetReadings(query);

                query.Type = new ReadingType { Name = READING_TYPE_PERCENTAGE };
                DataTable percentageReadings = readingRepository.GetReadings(query);

                query.Type = new ReadingType { Name = READING_TYPE_SALINITY };
                DataTable salinityReadings = readingRepository.GetReadings(query);

                // determine what table to use for result
                string tableToUse;
                switch (scale)
                {
                    case SCALE_REAL_TIME:
                        tableToUse = TABLE_MINUTELY_READINGS;
                        break;
                    case SCALE_HOURLY:
                        tableToUse = TABLE_HOURLY_READINGS;
                        break;
                    case SCALE_DAILY:
                        tableToUse = TABLE_MULTI_HOURLY_READINGS;
                        break;
                    default:
                        throw new ArgumentException(string.Format("scale must be {0}, {1} or {2}.",
                            SCALE_REAL_TIME, SCALE_HOURLY, SCALE_DAILY), scale);
                }

                // make a new copy of data set to hold the result rows
                DataSet outputDataSet = dataSetTemplate.Copy();

                // process the temp readings and build the result rows to return
                foreach (DataRow row in tempReadings.Rows)
                {
                    // build the result row from the readings and add it to output data set
                    DateTime readingDate = Convert.ToDateTime(row[COLUMN_READING_DT]);
                    DataRow resultRow = outputDataSet.Tables[tableToUse].NewRow();
                    resultRow[COLUMN_TIME] = row[COLUMN_READING_DT];
                    resultRow[COLUMN_PERCENTAGE] = GetReadingVal(readingDate, percentageReadings);
                    resultRow[COLUMN_TEMP] = GetReadingVal(readingDate, tempReadings);
                    resultRow[COLUMN_SALT] = GetReadingVal(readingDate, salinityReadings);

                    outputDataSet.Tables[tableToUse].Rows.Add(resultRow);
                }

                // write data set to string to return
                string output = WriteDataSet(outputDataSet);

                LogMethodExit(logger, MethodBase.GetCurrentMethod(), output, methodEntryTime);

                return output;
            }
            catch (Exception e)
            {
                throw WrapAndLogException(MethodBase.GetCurrentMethod(), e,
                    "An error occurred getting device reading.");
            }
        }

        /// <summary>
        /// Authenticate the user and password info.
        /// </summary>
        ///
        /// <param name="username">
        /// The username for the user.
        /// </param>
        ///
        /// <param name="password">
        /// The password for the user.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <exception cref="ArgumentNullException">
        /// If any string argument is null.
        /// </exception>
        ///
        /// <exception cref="ArgumentException">
        /// If any string argument is empty.
        /// </exception>
        ///
        /// <exception cref="AuthenticationException">
        /// If authentication fails for user, or some unknown error occurs.
        /// </exception>
        ///
        /// <exception cref="InvalidDataInputException">
        /// If site can't be found for user.
        /// </exception>
        protected virtual void AuthenticateUser(string username, string password, int siteID)
        {
            try
            {
                ValidateNotNullOrEmpty(username, "username");
                ValidateNotNullOrEmpty(password, "password");

                // get the user
                User user = userRepository.GetUserByName(username);

                // make sure password matches
                if (!authenticationService.PasswordMatches(user, password))
                {
                    throw new AuthenticationException("Failed to authenticate user, password didn't match.");
                }

                // make sure site matches
                if (user.GetSites().Count(x => x.Id == siteID) == 0)
                {
                    throw new InvalidDataInputException("Failed to authenticate user, couldn't find site for user.");
                }
            }
            catch (Exception e)
            {
                if (e is ArgumentException || e is DeviceServiceException)
                {
                    throw;
                }
                throw new AuthenticationException("An error occurred authenticating user.", e);
            }
        }

        /// <summary>
        /// Reads the XML <paramref name="input"/> into a DataSet instance.
        /// </summary>
        ///
        /// <param name="input">
        /// The XML string to load to the DataSet instance.
        /// </param>
        ///
        /// <returns>
        /// The DataSet from the XML string.
        /// </returns>
        ///
        /// <exception cref="InvalidDataInputException">
        /// If there is an error while reading the string into a DataSet.
        /// </exception>
        ///
        /// <exception cref="ArgumentNullException">
        /// If the argument is null.
        /// </exception>
        ///
        /// <exception cref="ArgumentException">
        /// If the argument is empty.
        /// </exception>
        protected virtual DataSet ReadDataSet(string input)
        {
            try
            {
                ValidateNotNullOrEmpty(input, "input");

                // prepare the data set by copying the template data set
                DataSet dataSet = dataSetTemplate.Copy();

                using (StringReader reader = new StringReader(input))
                {
                    // read the xml string into the data set
                    dataSet.ReadXml(reader);
                }

                return dataSet;
            }
            catch (ArgumentException)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new InvalidDataInputException(
                    "An error occurred reading XML string into data set.", e);
            }
        }

        /// <summary>
        /// Writes the <paramref name="dataSet"/> into an XML string.
        /// </summary>
        ///
        /// <param name="dataSet">
        /// The DataSet to write to the XML string.
        /// </param>
        ///
        /// <returns>
        /// The XML string from the DataSet.
        /// </returns>
        ///
        /// <exception cref="ArgumentNullException">
        /// If the argument is null.
        /// </exception>
        ///
        /// <exception cref="DeviceServiceException">
        /// If there is an error while writing the data set into an XML string.
        /// </exception>
        protected virtual string WriteDataSet(DataSet dataSet)
        {
            ValidateNotNull(dataSet, "dataSet");

            try
            {
                return dataSet.GetXml();
            }
            catch (Exception e)
            {
                throw new DeviceServiceException("An error occurred writing data set to XML string.", e);
            }
        }

        /// <summary>
        /// Helper method to get the Site instance for the user and the site ID.
        /// </summary>
        ///
        /// <param name="username">
        /// The username.
        /// </param>
        ///
        /// <param name="siteID">
        /// The ID of the site.
        /// </param>
        ///
        /// <returns>
        /// The site instance.
        /// </returns>
        ///
        /// <exception cref="DeviceServiceException">
        /// If there are any errors while executing this method, which in this case would result from the
        /// userRepository throwing an exception.
        /// </exception>
        private Site GetSite(string username, int siteID)
        {
            try
            {
                User user = userRepository.GetUserByName(username);

                // search for the site for the username
                var matchedSite = (from Site site in user.GetSites()
                                   where site.Id == siteID
                                   select site).SingleOrDefault();
                return matchedSite;
            }
            catch (Exception e)
            {
                throw new DeviceServiceException("An error occurred getting the site.", e);
            }
        }

        /// <summary>
        /// Gets the string attribute from the given configuration object and returns it as a string.
        /// </summary>
        ///
        /// <param name="configuration">The configuration object from which to fetch the attribute.</param>
        ///
        /// <param name="name">The attribute name.</param>
        ///
        /// <returns>The attribute value as a string.</returns>
        ///
        /// <exception cref="DeviceServiceConfigurationException">
        /// If the attribute is <c>null</c> or is not a string object, or if it is not present in the configuration,
        /// or if any other errors occur when fetching the attribute.
        /// </exception>
        private static string GetStringAttribute(IConfiguration configuration, String name)
        {
            try
            {
                // fetch attribute
                object value = configuration.GetSimpleAttribute(name);

                if (value != null)
                {
                    // make sure it is a valid non-empty string
                    if ((value as string == null) || (((string)value).Trim().Length == 0))
                    {
                        throw new DeviceServiceConfigurationException(
                            string.Format("The {0} attribute in configuration object {1} must be a non-empty string.",
                                name, configuration.Name));
                    }
                }
                else
                {
                    throw new DeviceServiceConfigurationException(
                        string.Format("The {0} attribute in configuration object {1} is required.",
                            name, configuration.Name));
                }

                return (string)value;
            }
            catch (DeviceServiceConfigurationException)
            {
                throw;
            }
            catch (Exception e)
            {
                // wrap exception
                throw new DeviceServiceConfigurationException(
                    string.Format("An error occurred while retrieving the {0} attribute.", name), e);
            }
        }

        /// <summary>
        /// Creates an object of type <typeparamref name="T"/> using the given <paramref name="key"/> from
        /// the passed in <paramref name="config"/>.
        /// </summary>
        ///
        /// <typeparam name="T">The type of object to create.</typeparam>
        ///
        /// <param name="objFactory">The <see cref="ObjectFactory"/> instance used to create
        /// the object.</param>
        ///
        /// <param name="config">The <see cref="IConfiguration"/> which contains the information required to
        /// create the object.</param>
        ///
        /// <param name="key">The key from the <paramref name="config"/> to use when creating the object.</param>
        ///
        /// <returns>The created object of type <typeparamref name="T"/>.</returns>
        ///
        /// <exception cref="DeviceServiceConfigurationException">If the object was not created
        /// successfully or if the <paramref name="key"/> was not present in the configuration.</exception>
        private static T CreateObject<T>(ObjectFactory objFactory, IConfiguration config,
            string key) where T : class
        {
            try
            {
                // get the object key from the configuration
                string objectKey = GetStringAttribute(config, key);

                // create the object
                T obj = objFactory.CreateDefinedObject(objectKey) as T;

                if (obj == null)
                {
                    throw new DeviceServiceConfigurationException(
                        string.Format("Unable to create object for object key = {0} and type = {1}.",
                            key, typeof(T).FullName));
                }
                return obj;
            }
            catch (DeviceServiceConfigurationException)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new DeviceServiceConfigurationException(
                    string.Format("Unable to create object for object key = {0} and type = {1}.",
                        key, typeof(T).FullName), e);
            }
        }

        /// <summary>
        /// Retrieves the required child configuration from the given <paramref name="config"/>.
        /// </summary>
        ///
        /// <param name="config">The configuration from which to retrieve the child node.</param>
        /// <param name="childName">The required child name from the configuration.</param>
        ///
        /// <returns>
        /// The required child configuration.
        /// </returns>
        ///
        /// <exception cref="DeviceServiceConfigurationException">If the required child node does not
        /// exist in the configuration.</exception>
        private static IConfiguration GetRequiredChildConfiguration(IConfiguration config, string childName)
        {
            // see if child node exists
            IConfiguration child = config.GetChild(childName);
            if (child == null)
            {
                throw new DeviceServiceConfigurationException(
                    string.Format("The {0} child configuration for parent configuration {1} is required.", childName,
                        config.Name));
            }

            return child;
        }

        /// <summary>
        /// A generic method which handles writing to the logger.
        /// </summary>
        ///
        /// <remarks>
        /// Any exceptions that occur in this method will be ignored.
        /// </remarks>
        ///
        /// <param name="logger">The logger object.</param>
        ///
        /// <param name="level">The log level.</param>
        ///
        /// <param name="logMessage">The message to be logged.</param>
        ///
        /// <param name="parameters">The log parameters.</param>
        private static void WriteToLog(Logger logger, Level level, string logMessage,
            params object[] parameters)
        {
            try
            {
                logger.Log(level, logMessage, parameters);
            }
            catch
            {
                // ignore
            }
        }

        /// <summary>
        /// Logs method entry and parameters at DEBUG level.
        /// </summary>
        ///
        /// <param name="logger">The logger object.</param>
        ///
        /// <param name="methodBase">The method base for the method we are logging.</param>
        ///
        /// <param name="methodEntryTime">The <see cref="DateTime"/> representing when the method was entered.</param>
        ///
        /// <param name="parameters">The method parameters.</param>
        private static void LogMethodEntry(Logger logger, MethodBase methodBase, DateTime methodEntryTime,
            params object[] parameters)
        {
            WriteToLog(logger, Level.DEBUG,
                string.Format("Entering the method: {0}.{1}, timestamp: {2}",
                methodBase.DeclaringType, methodBase.Name, methodEntryTime.ToString("yyyy-MM-dd HH:mm:ss:ffff")));

            ParameterInfo[] parmInfo = methodBase.GetParameters();
            if (parmInfo.Length > 0)
            {
                // create the format string to display parameters
                StringBuilder logFormat = new StringBuilder();

                logFormat.AppendLine("Input parameters:");
                for (int i = 0; i < parmInfo.Length; i++)
                {
                    logFormat.Append(parmInfo[i].Name).Append(": ");
                    logFormat.Append(GetObjectDescription(parameters[i]));
                    logFormat.AppendLine();
                }
                WriteToLog(logger, Level.DEBUG, logFormat.ToString());
            }
        }

        /// <summary>
        /// Logs method exit and return value at DEBUG level. Also logs the elapsed time in milliseconds for the method
        /// execution.
        /// </summary>
        ///
        /// <param name="logger">The logger object.</param>
        ///
        /// <param name="methodBase">The method base for the method we are logging.</param>
        ///
        /// <param name="returnValue">The method return value.</param>
        ///
        /// <param name="methodEntryTime">The <see cref="DateTime"/> that the method was originally
        /// entered.</param>
        ///
        /// <typeparam name="T">The type of the return value.</typeparam>
        private static void LogMethodExit<T>(Logger logger, MethodBase methodBase, T returnValue,
            DateTime methodEntryTime)
        {
            LogMethodExit(logger, methodBase, methodEntryTime);
            WriteToLog(logger, Level.DEBUG,
                string.Format("Return value of the method {0}.{1} is of type {2}, return value: {3}",
                    methodBase.DeclaringType, methodBase.Name, typeof(T), GetObjectDescription(returnValue)));
        }

        /// <summary>
        /// Logs method exit at DEBUG level. Also logs the elapsed time in milliseconds for the method
        /// execution.
        /// </summary>
        ///
        /// <param name="logger">The logger object.</param>
        ///
        /// <param name="methodBase">The method base for the method we are logging.</param>
        ///
        /// <param name="methodEntryTime">The <see cref="DateTime"/> that the method was originally
        /// entered.</param>
        private static void LogMethodExit(Logger logger, MethodBase methodBase, DateTime methodEntryTime)
        {
            WriteToLog(logger, Level.DEBUG,
                string.Format("Exiting the method {0}.{1}", methodBase.DeclaringType, methodBase.Name));
            TimeSpan elapsedTime = DateTime.Now.Subtract(methodEntryTime);
            WriteToLog(logger, Level.DEBUG,
                string.Format("The method took {0} milliseconds to execute.", elapsedTime.TotalMilliseconds));
        }

        /// <summary>
        /// Logs the given exception at ERROR level.
        /// </summary>
        ///
        /// <param name="logger">The logger object.</param>
        ///
        /// <param name="methodBase">The method base for the method we are logging.</param>
        ///
        /// <param name="ex">The exception to be logged and re-thrown.</param>
        private static void LogException(Logger logger, MethodBase methodBase, Exception ex)
        {
            // write exception to log at ERROR level
            WriteToLog(logger, Level.ERROR,
                string.Format(
                    "Error occurred in {0}.{1}.\n\nDetails:\n{2}", methodBase.DeclaringType, methodBase.Name,
                    ex));
        }

        /// <summary>
        /// Gets the description for the object.
        /// </summary>
        ///
        /// <param name="obj">The object for which to get the description.</param>
        ///
        /// <returns>The object description.</returns>
        private static string GetObjectDescription(object obj)
        {
            return obj == null ? "[NULL]" : obj.ToString();
        }

        /// <summary>
        /// Wraps the exception into <see cref="DeviceServiceException"/> if necessary, then wraps it into
        /// <see cref="FaultException{TCFaultException}"/>. Also logs the exception.
        /// </summary>
        ///
        /// <param name="methodBase">The method base for the method that threw exception.</param>
        ///
        /// <param name="e">The exception to wrap and log.</param>
        ///
        /// <param name="msg">The message to use when wrapping the exception into
        /// <see cref="DeviceServiceException"/>.</param>
        ///
        /// <returns>The wrapped exception.</returns>
        private FaultException<TCFaultException> WrapAndLogException(MethodBase methodBase, Exception e, string msg)
        {
            // wrap to DeviceServiceException if necessary
            e = e is DeviceServiceException || e is ArgumentException ? e : new DeviceServiceException(msg, e);

            LogException(logger, methodBase, e);

            // wrap with FaultException<TCFaultException>
            return new FaultException<TCFaultException>(TCFaultException.CreateFromException(e),
                new FaultReason(e.Message));
        }

        /// <summary>
        /// Validates that <paramref name="value"/> is not null.
        /// </summary>
        ///
        /// <param name="value">The object to validate.</param>
        ///
        /// <param name="name">The variable name of the object.</param>
        ///
        /// <exception cref="ArgumentNullException">If <paramref name="value"/> is null.</exception>
        private static void ValidateNotNull(object value, string name)
        {
            if (value == null)
            {
                throw new ArgumentNullException(name, string.Format("{0} cannot be null.", name));
            }
        }

        /// <summary>
        /// Validates that the column value for given <paramref name="row"/> and
        /// <paramref name="columnName"/> is not null and if it's a string value, not empty.
        /// </summary>
        ///
        /// <param name="row">The data row.</param>
        ///
        /// <param name="columnName">The column name in the data row.</param>
        ///
        /// <exception cref="InvalidDataInputException">If the column value is null or empty string (for string
        /// columns).</exception>
        private static void ValidateColumnValue(DataRow row, string columnName)
        {
            object val = row[columnName];
            if (val == DBNull.Value)
            {
                throw new InvalidDataInputException(string.Format("{0} column in {1} table cannot be null.",
                    columnName, row.Table.TableName));
            }
            if (val is string && val.ToString().Trim().Length == 0)
            {
                throw new InvalidDataInputException(string.Format("{0} column in {1} table cannot be empty string.",
                    columnName, row.Table.TableName));
            }
        }

        /// <summary>
        /// Validates that a string is not null or empty.
        /// </summary>
        ///
        /// <param name="value">The string to validate.</param>
        ///
        /// <param name="name">The name of the string.</param>
        ///
        /// <exception cref="ArgumentNullException">If <paramref name="value"/> is null.</exception>
        ///
        /// <exception cref="ArgumentException">If <paramref name="value"/> is empty after trimming.</exception>
        private static void ValidateNotNullOrEmpty(string value, string name)
        {
            ValidateNotNull(value, name);
            if (value.Trim().Length == 0)
            {
                throw new ArgumentException(string.Format("{0} cannot be empty string.", name), name);
            }
        }

        /// <summary>
        /// Returns the single row from data set for given <paramref name="tableName"/>.
        /// </summary>
        ///
        /// <param name="dataSet">The data set.</param>
        ///
        /// <param name="tableName">The table name from which to fetch the single row.</param>
        ///
        /// <returns>
        /// The data row from the data set.
        /// </returns>
        ///
        /// <exception cref="InvalidDataInputException">If the data set does not contain single row for
        /// table.</exception>
        private static DataRow GetRowFromDataSet(DataSet dataSet, string tableName)
        {
            if (dataSet.Tables[tableName].Rows.Count != 1)
            {
                throw new InvalidDataInputException("There must be exactly one " + tableName + " row in data set.");
            }
            return dataSet.Tables[tableName].Rows[0];
        }

        /// <summary>
        /// Populates a <see cref="DeviceInstall"/> object from the <paramref name="sensorInfoRow"/>.
        /// </summary>
        ///
        /// <remarks>
        /// If any error occurs other than invalid input data, it will be handled by caller.
        /// </remarks>
        ///
        /// <param name="deviceInstall">The <see cref="DeviceInstall"/> instance to populate. It will be null in
        /// case of an add operation, and this method will create the instance.</param>
        ///
        /// <param name="sensorInfoRow">The sensor input row.</param>
        ///
        /// <param name="site">The site.</param>
        ///
        /// <returns>The populated <see cref="DeviceInstall"/> object.</returns>
        ///
        /// <exception cref="InvalidDataInputException">
        /// If there is some problem while processing the sensor info row, such as having a null value
        /// for a required field, etc.</exception>
        private DeviceInstall BuildDeviceInstallFromSensorInfoRow(DeviceInstall deviceInstall,
            DataRow sensorInfoRow, Site site)
        {
            // get primary and secondary groups
            DeviceGroup[] primaryGroups = deviceGroupRepository.GetForSite(site, DeviceGroup.PRIMARY);
            DeviceGroup[] secondaryGroups = deviceGroupRepository.GetForSite(site, DeviceGroup.SECONDARY);

            // find the primary group for the hole number
            string holeNumber = Convert.ToString(sensorInfoRow[COLUMN_HOLE_NUMBER]);
            DeviceGroup primaryGroup;
            try
            {
                primaryGroup = primaryGroups.Where(x => x.Name == holeNumber).SingleOrDefault();
            }
            catch (Exception e)
            {
                throw new InvalidDataInputException(
                    "Error finding primary group for hole number = " + holeNumber, e);
            }

            if (primaryGroup == null)
            {
                throw new InvalidDataInputException(
                    "Unable to find primary group for hole number = " + holeNumber);
            }

            // find the secondary group for the location type
            string locationType = Convert.ToString(sensorInfoRow[COLUMN_LOCATION_TYPE]);
            DeviceGroup secondaryGroup;
            try
            {
                secondaryGroup = secondaryGroups.Where(x => x.Name == locationType).SingleOrDefault();
            }
            catch (Exception e)
            {
                throw new InvalidDataInputException(
                    "Error finding secondary group for location type = " + locationType, e);
            }

            if (secondaryGroup == null)
            {
                throw new InvalidDataInputException(
                    "Unable to find secondary group for location type = " + locationType);
            }

            if (deviceInstall == null)
            {
                // this is an add operation, so create new DeviceInstall instance
                deviceInstall = new DeviceInstall();
            }

            // populate the DeviceInstall
            ValidateColumnValue(sensorInfoRow, COLUMN_X);
            deviceInstall.Latitude = Convert.ToDouble(sensorInfoRow[COLUMN_X]);

            ValidateColumnValue(sensorInfoRow, COLUMN_Y);
            deviceInstall.Longitude = Convert.ToDouble(sensorInfoRow[COLUMN_Y]);

            deviceInstall.InstallationDate = Convert.ToDateTime(sensorInfoRow[COLUMN_IN_SERVICE]);

            deviceInstall.PrimaryGroup = primaryGroup;

            deviceInstall.SecondaryGroup = secondaryGroup;

            return deviceInstall;
        }

        /// <summary>
        /// Populates a <see cref="Device"/> object from the <paramref name="sensorInfoRow"/>.
        /// </summary>
        ///
        /// <param name="sensorInfoRow">The sensor input row.</param>
        ///
        /// <param name="site">The site.</param>
        ///
        /// <returns>The populated <see cref="Device"/> object.</returns>
        ///
        /// <exception cref="InvalidDataInputException">
        /// If there is some problem while processing the sensor info row, such as having a null value
        /// for a required field, etc.
        /// </exception>
        private static Device BuildDeviceFromSensorInfoRow(DataRow sensorInfoRow, Site site)
        {
            // create new Device object and populate it
            Device device = new Device();
            device.NodeId = Convert.ToInt32(sensorInfoRow[COLUMN_NODE_ID]);
            device.Site = site;
            ValidateColumnValue(sensorInfoRow, COLUMN_SERIAL_NUMBER);
            device.SerialNumber = Convert.ToString(sensorInfoRow[COLUMN_SERIAL_NUMBER]);

            return device;
        }

        /// <summary>
        /// Populates a <see cref="DeviceGroup"/> object from the <paramref name="locationNamesRow"/>.
        /// </summary>
        ///
        /// <param name="locationNamesRow">The sensor input row.</param>
        ///
        /// <returns>The populated <see cref="DeviceGroup"/> object.</returns>
        ///
        /// <exception cref="InvalidDataInputException">
        /// If there is some problem while processing the location names row.
        /// </exception>
        private static DeviceGroup BuildDeviceGroupFromLocationNamesRow(DataRow locationNamesRow)
        {
            // populate device group object
            DeviceGroup deviceGroup = new DeviceGroup();
            ValidateColumnValue(locationNamesRow, COLUMN_NAME);
            deviceGroup.Name = Convert.ToString(locationNamesRow[COLUMN_NAME]);
            deviceGroup.DeviceGroupType = DeviceGroup.PRIMARY;

            return deviceGroup;
        }

        /// <summary>
        /// Gets the current device install for the given <paramref name="device"/>.
        /// </summary>
        ///
        /// <remarks>
        /// If any error occurs other than not being able to find current device install,
        /// it will be handled by caller.
        /// </remarks>
        ///
        /// <param name="device">The device.</param>
        ///
        /// <returns>The current device install for the given <paramref name="device"/></returns>
        ///
        /// <exception cref="DeviceServiceException">If current device install could not be found.</exception>
        private DeviceInstall GetCurrentDeviceInstall(Device device)
        {
            // find the device install that is current (it will have null removal date)
            DeviceInstall[] deviceInstalls = deviceInstallRepository.GetAllForDevice(device);

            DeviceInstall currentInstall;

            try
            {
                currentInstall = deviceInstalls.Where(x => x.RemovalDate == null).SingleOrDefault();
            }
            catch (Exception e)
            {
                throw new DeviceServiceException("Error fetching device with null removal date for " +
                    "device with NodeId = " + device.NodeId, e);
            }

            if (currentInstall == null)
            {
                throw new DeviceServiceException("Could not find device install with null removal date for " +
                    "device with NodeId = " + device.NodeId);
            }
            return currentInstall;
        }

        /// <summary>
        /// Gets the reading value from the given <paramref name="table"/>.
        /// </summary>
        ///
        /// <param name="readingDate">The date to use when searching the table.</param>
        ///
        /// <param name="table">The table containing the reading value.</param>
        ///
        /// <returns>The matching reading value.</returns>
        ///
        /// <exception cref="DeviceServiceException">If no matching row could be found
        /// in <paramref name="table"/> or if some other error occurs getting reading value.</exception>
        private static Single GetReadingVal(DateTime readingDate, DataTable table)
        {
            try
            {
                // find row that has matching reading date
                DataRow row = (from DataRow r in table.Rows
                               where Convert.ToDateTime(r[COLUMN_READING_DT]) == readingDate
                               select r).SingleOrDefault();
                if (row == null)
                {
                    throw new DeviceServiceException(
                        "Could not find matching reading row in " + table.TableName + " table.");
                }

                // return the reading val from matched row
                return Convert.ToSingle(row[COLUMN_READING_VAL]);
            }
            catch (DeviceServiceException)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new DeviceServiceException(
                    "Error retrieving reading row in " + table.TableName + " table.", e);
            }
        }
    }
}


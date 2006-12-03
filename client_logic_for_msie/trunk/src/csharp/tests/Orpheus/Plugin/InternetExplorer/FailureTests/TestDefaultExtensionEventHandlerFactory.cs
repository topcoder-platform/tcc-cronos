using System;
using NUnit.Framework;
using TopCoder.Util.ConfigurationManager;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Factories;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for DefaultExtensionEventHandlerFactory class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestDefaultExtensionEventHandlerFactory
    {
        /// <summary>
        /// The DefaultExtensionEventHandlerFactory instance used to test.
        /// </summary>
        private DefaultExtensionEventHandlerFactory factory = null;

        /// <summary>
        /// The ConfigManager instance used to test.
        /// </summary>
        private ConfigManager cm = ConfigManager.GetInstance();

        /// <summary>
        /// SetUp the test environment for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            cm.Clear(false);
            cm.LoadFile(@"../../test_files/failure/FactoryInvalid.xml");
            factory = new DefaultExtensionEventHandlerFactory();
        }
        /// <summary>
        /// TearDown the test environment for each test case.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            cm.Clear(false);
        }
        /// <summary>
        /// Tests <c>DefaultExtensionEventHandlerFactory(string configurationNamespace,string
        /// objectFactoryNamespace)</c> method with null string configurationNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestDefaultExtensionEventHandlerFactory_NullConfigurationNamespace()
        {
            new DefaultExtensionEventHandlerFactory(null, "ns");
        }
        /// <summary>
        /// Tests <c>DefaultExtensionEventHandlerFactory(string configurationNamespace,string
        /// objectFactoryNamespace)</c> method with empty string configurationNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestDefaultExtensionEventHandlerFactory_EmptyConfigurationNamespace()
        {
            new DefaultExtensionEventHandlerFactory(" ", "ns");
        }
        /// <summary>
        /// Tests <c>DefaultExtensionEventHandlerFactory(string configurationNamespace,string
        /// objectFactoryNamespace)</c> method with null string objectFactoryNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestDefaultExtensionEventHandlerFactory_NullObjectFactoryNamespace()
        {
            new DefaultExtensionEventHandlerFactory("ns", null);
        }
        /// <summary>
        /// Tests <c>DefaultExtensionEventHandlerFactory(string configurationNamespace,string
        /// objectFactoryNamespace)</c> method with empty string objectFactoryNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestDefaultExtensionEventHandlerFactory_EmptyObjectFactoryNamespace()
        {
            new DefaultExtensionEventHandlerFactory("ns", " ");
        }
        /// <summary>
        /// Tests <c>CreateHandlers(string eventName)</c> method with null string eventName
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestCreateHandlers_NullEventName()
        {
            factory.CreateHandlers(null);
        }
        /// <summary>
        /// Tests <c>CreateHandlers(string eventName)</c> method with empty string eventName
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestCreateHandlers_EmptyEventName()
        {
            factory.CreateHandlers(" ");
        }

        /// <summary>
        /// Tests <c>CreateHandlers(string eventName)</c> method with empty string eventName
        /// EventHandlerCreationException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(EventHandlerCreationException))]
        public void TestCreateHandlers_InvalidConf()
        {
            factory.CreateHandlers("invalid");
        }
    }
}

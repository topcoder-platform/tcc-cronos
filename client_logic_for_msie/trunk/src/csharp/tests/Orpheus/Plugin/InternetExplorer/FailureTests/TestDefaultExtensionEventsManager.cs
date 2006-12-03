using System;
using NUnit.Framework;
using TopCoder.Util.ConfigurationManager;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using SHDocVw;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for DefaultExtensionEventsManager class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestDefaultExtensionEventsManager
    {
        /// <summary>
        /// The DefaultExtensionEventsManager instance used to test.
        /// </summary>
        private DefaultExtensionEventsManager manager = null;

        /// <summary>
        /// The ConfigManager instance used to test.
        /// </summary>
        private ConfigManager cm = ConfigManager.GetInstance();

        /// <summary>
        /// The mock handler
        /// </summary>
        private MockExtensionEventHandler handler;

        /// <summary>
        /// The delegate instance used to test.
        /// </summary>
        private ExtensionEventHandlerDelegate @delegate;

        /// <summary>
        /// The args used to test.
        /// </summary>
        private ExtensionEventArgs args;

        /// <summary>
        /// SetUp the test environment for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            cm.Clear(false);
            cm.LoadFile(@"../../test_files/failure/client_logic_for_msie.xml");
            cm.LoadFile(@"../../test_files/failure/object_factory.xml");

            handler = new MockExtensionEventHandler();
            @delegate = new ExtensionEventHandlerDelegate(handler.HandleEvent);
            args = new ExtensionEventArgs("Mock", new MsieClientLogic(new WebBrowserClass()));
            manager = new DefaultExtensionEventsManager();
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
        /// Tests <c>DefaultExtensionEventsManager(string configurationNamespace,string
        /// objectFactoryNamepsace)</c> method with null string configurationNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestDefaultExtensionEventsManager_NullConfigurationNamespace()
        {
            new DefaultExtensionEventsManager(null, "ns");
        }
        /// <summary>
        /// Tests <c>DefaultExtensionEventsManager(string configurationNamespace,string
        /// objectFactoryNamepsace)</c> method with empty string configurationNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestDefaultExtensionEventsManager_EmptyConfigurationNamespace()
        {
            new DefaultExtensionEventsManager(" ", "ns");
        }
        /// <summary>
        /// Tests <c>DefaultExtensionEventsManager(string configurationNamespace,string
        /// objectFactoryNamepsace)</c> method with null string objectFactoryNamepsace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestDefaultExtensionEventsManager_NullObjectFactoryNamepsace()
        {
            new DefaultExtensionEventsManager("ns", null);
        }
        /// <summary>
        /// Tests <c>DefaultExtensionEventsManager(string configurationNamespace,string
        /// objectFactoryNamepsace)</c> method with empty string objectFactoryNamepsace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestDefaultExtensionEventsManager_EmptyObjectFactoryNamepsace()
        {
            new DefaultExtensionEventsManager("ns", " ");
        }
        /// <summary>
        /// Tests <c>DefaultExtensionEventsManager(IExtensionEventHandlerFactory
        /// extensionEventHandlerFactory)</c> method with null IExtensionEventHandlerFactory extensionEventHandlerFactory
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestDefaultExtensionEventsManager_NullExtensionEventHandlerFactory()
        {
            new DefaultExtensionEventsManager(null);
        }
        /// <summary>
        /// Tests <c>AddEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler)</c>
        /// method with null string eventName
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestAddEventHandler_NullEventName()
        {
            manager.AddEventHandler(null, @delegate);
        }
        /// <summary>
        /// Tests <c>AddEventHandler(string eventName, ExtensionEventHandlerDelegate
        /// eventHandler)</c> method with empty string eventName
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestAddEventHandler_EmptyEventName()
        {
            manager.AddEventHandler(" ", @delegate);
        }
        /// <summary>
        /// Tests <c>AddEventHandler(string eventName, ExtensionEventHandlerDelegate
        /// eventHandler)</c> method with null ExtensionEventHandlerDelegate eventHandler
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestAddEventHandler_NullEventHandler()
        {
            manager.AddEventHandler("events", null);
        }
        /// <summary>
        /// Tests <c>RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler)</c>
        /// method with null string eventName
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestRemoveEventHandler_NullEventName()
        {
            manager.RemoveEventHandler(null, @delegate);
        }
        /// <summary>
        /// Tests <c>RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler)</c>
        /// method with empty string eventName
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestRemoveEventHandler_EmptyEventName()
        {
            manager.RemoveEventHandler(" ", @delegate);
        }
        /// <summary>
        /// Tests <c>RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler)</c>
        /// method with null ExtensionEventHandlerDelegate eventHandler
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestRemoveEventHandler_NullEventHandler()
        {
            manager.RemoveEventHandler("events", null);
        }
        /// <summary>
        /// Tests <c>GetEventHandlers(string eventName)</c> method with null string eventName
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestGetEventHandlers_NullEventName()
        {
            manager.GetEventHandlers(null);
        }
        /// <summary>
        /// Tests <c>GetEventHandlers(string eventName)</c> method with empty string eventName
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestGetEventHandlers_EmptyEventName()
        {
            manager.GetEventHandlers(" ");
        }
        /// <summary>
        /// Tests <c>FireEvent(string eventName, object sender, ExtensionEventArgs args)</c>
        /// method with null string eventName
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestFireEvent_NullEventName()
        {
            manager.FireEvent(null, new object(), args);
        }
        /// <summary>
        /// Tests <c>FireEvent(string eventName, object sender, ExtensionEventArgs args)</c>
        /// method with empty string eventName
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestFireEvent_EmptyEventName()
        {
            manager.FireEvent(" ", new object(), args);
        }
        /// <summary>
        /// Tests <c>FireEvent(string eventName, object sender, ExtensionEventArgs args)</c>
        /// method with null object sender
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestFireEvent_NullSender()
        {
            manager.FireEvent("events", null, args);
        }
        /// <summary>
        /// Tests <c>FireEvent(string eventName, object sender, ExtensionEventArgs args)</c>
        /// method with null ExtensionEventArgs args
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestFireEvent_NullArgs()
        {
            manager.FireEvent("events", new object(), null);
        }
    }
}

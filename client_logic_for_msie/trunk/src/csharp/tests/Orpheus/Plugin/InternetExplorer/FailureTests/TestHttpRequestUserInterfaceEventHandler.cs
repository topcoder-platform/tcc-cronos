using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;
using SHDocVw;
using TopCoder.Util.ConfigurationManager;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for HttpRequestUserInterfaceEventHandler class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestHttpRequestUserInterfaceEventHandler
    {
        /// <summary>
        /// The args used to test..
        /// </summary>
        private ExtensionEventArgs args = null;

        /// <summary>
        /// The HttpRequestUserInterfaceEventHandler instance used to test.
        /// </summary>
        private HttpRequestUserInterfaceEventHandler handler = null;

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
            cm.LoadFile(@"../../test_files/failure/client_logic_for_msie.xml");
            cm.LoadFile(@"../../test_files/failure/object_factory.xml");
            MsieClientLogic logic = new MsieClientLogic(new WebBrowserClass());
            args = new ExtensionEventArgs("Mock", logic);
            handler = new HttpRequestUserInterfaceEventHandler();
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
        /// Tests <c>HttpRequestUserInterfaceEventHandler(string configurationNamespace)</c>
        /// method with null string configurationNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestHttpRequestUserInterfaceEventHandler_NullConfigurationNamespace()
        {
            new HttpRequestUserInterfaceEventHandler(null);
        }
        /// <summary>
        /// Tests <c>HttpRequestUserInterfaceEventHandler(string configurationNamespace)</c>
        /// method with empty string configurationNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestHttpRequestUserInterfaceEventHandler_EmptyConfigurationNamespace()
        {
            new HttpRequestUserInterfaceEventHandler(" ");
        }
        /// <summary>
        /// Tests <c>HandleEvent(object sender, ExtensionEventArgs args)</c> method with null object sender
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestHandleEvent_NullSender()
        {
            handler.HandleEvent(null, args);
        }
        /// <summary>
        /// Tests <c>HandleEvent(object sender, ExtensionEventArgs args)</c>
        /// method with null ExtensionEventArgs args
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestHandleEvent_NullArgs()
        {
            handler.HandleEvent(new object(), null);
        }
    }
}

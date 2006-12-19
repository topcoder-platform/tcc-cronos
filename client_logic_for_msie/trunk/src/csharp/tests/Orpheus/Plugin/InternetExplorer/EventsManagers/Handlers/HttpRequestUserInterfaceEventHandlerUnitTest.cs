/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * HttpRequestUserInterfaceEventHandlerUnitTest.cs
 */

using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.Persistence;
using MsHtmHstInterop;
using SHDocVw;
using TopCoder.Util.BloomFilter;
using Orpheus.Plugin.InternetExplorer.Mock;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// Unit test for <c>HttpRequestUserInterfaceEventHandler</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class HttpRequestUserInterfaceEventHandlerUnitTest
    {
        /// <summary>
        /// The event name of args to handle.
        /// </summary>
        private const string EVENT_NAME = "http";

        /// <summary>
        /// The instance to perform test on.
        /// </summary>
        private HttpRequestUserInterfaceEventHandler tester;

        /// <summary>
        /// The argument used in handle event.
        /// </summary>
        private ExtensionEventArgs args;

        /// <summary>
        /// The context used in args.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// The form hold the control.
        /// </summary>
        private BrowserForm form;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();

            context = new MsieClientLogic(new WebBrowserClass());
            args = new ExtensionEventArgs(EVENT_NAME, context);

            tester = new HttpRequestUserInterfaceEventHandler();
        }

        /// <summary>
        /// Clear all namespaces.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            TestHelper.ClearNamespace();
        }

        /// <summary>
        /// Test inheritance,
        /// HttpRequestUserInterfaceEventHandler should implement IExtensionEventHandler.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsTrue(tester is IExtensionEventHandler,
                "HttpRequestUserInterfaceEventHandler should implement IExtensionEventHandler");
        }

        /// <summary>
        /// Test const DefaultConfigurationNamespace,
        /// it should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers'.
        /// </summary>
        public void TestDefaultConfigurationNamespace()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers",
                HttpRequestUserInterfaceEventHandler.DefaultConfigurationNamespace,
                "should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers'.");
        }

        /// <summary>
        /// Test ctor HttpRequestUserInterfaceEventHandler(),
        /// the default namespace will be used.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.AreEqual(HttpRequestUserInterfaceEventHandler.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(tester, "configurationNamespace"),
                "the default namespace should be used.");
        }

        /// <summary>
        /// Test ctor HttpRequestUserInterfaceEventHandler(string configurationNamespace),
        /// when configurationNamespace is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ConfigurationNamespaceIsNull()
        {
            new HttpRequestUserInterfaceEventHandler(null);
        }

        /// <summary>
        /// Test ctor HttpRequestUserInterfaceEventHandler(string configurationNamespace),
        /// when configurationNamespace is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ConfigurationNamespaceIsEmpty()
        {
            new HttpRequestUserInterfaceEventHandler(" \r \t \n ");
        }

        /// <summary>
        /// Test ctor HttpRequestUserInterfaceEventHandler(string configurationNamespace),
        /// when configurationNamespace is empty, ArgumentException is expected.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            const string ns = "Test";

            HttpRequestUserInterfaceEventHandler handler = new HttpRequestUserInterfaceEventHandler(ns);
            Assert.AreEqual(ns, TestHelper.GetFieldValue(handler, "configurationNamespace"),
                "the namespace should be set to the given namespace");
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// when sender is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestHandleEvent_SenderIsNull()
        {
            tester.HandleEvent(null, args);
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// when sender is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestHandleEvent_ArgsIsNull()
        {
            tester.HandleEvent(this, null);
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// Since no context attached, HandleEventException will thrown.
        /// </summary>
        [Test, ExpectedException(typeof(HandleEventException))]
        public void TestHandleEvent_NoContext()
        {
            tester.HandleEvent(this, args);
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// a window should popup.
        /// </summary>
        [Test]
        public void TestHandleEvent()
        {
            form = new BrowserForm();
            MsieClientLogic context = new MsieClientLogic(form.GetWebBrowserClass());

            HttpRequestUserInterfaceEventHandler handler = new HttpRequestUserInterfaceEventHandler();
            ExtensionEventArgs args = new ExtensionEventArgs("http", context);
            handler.HandleEvent(this, args);
        }
    }
}

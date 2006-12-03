/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * PoolingEventHandlerUnitTest.cs
 */

using System;
using NUnit.Framework;
using TopCoder.Util.RSS.IO;
using TopCoder.Util.RSS.Atom.IO;
using Orpheus.Plugin.InternetExplorer.Persistence;
using MsHtmHstInterop;
using SHDocVw;
using TopCoder.Util.BloomFilter;
using Orpheus.Plugin.InternetExplorer.Mock;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// Unit test for <c>PoolingEventHandler</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class PoolingEventHandlerUnitTest
    {
        /// <summary>
        /// The event name of args to handle.
        /// </summary>
        private const string EVENT_NAME = "poll";

        /// <summary>
        /// The instance to perform test on.
        /// </summary>
        private PollingEventHandler tester;

        /// <summary>
        /// The argument used in handle event.
        /// </summary>
        private ExtensionEventArgs args;

        /// <summary>
        /// The context used in args.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();
            context = new MsieClientLogic(new WebBrowserClass());

            args = new ExtensionEventArgs(EVENT_NAME, context);

            tester = new PollingEventHandler();
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
        /// Test const DefaultObjectFactoryNamespace,
        /// it should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers'.
        /// </summary>
        [Test]
        public void TestDefaultObjectFactoryNamespace()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers",
                PollingEventHandler.DefaultConfigurationNamespace,
                "should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers'.");
        }

        /// <summary>
        /// Test const DefaultObjectFactoryNamespace,
        /// it should be 'TopCoder.Util.ObjectFactory'.
        /// </summary>
        [Test]
        public void TestDefaultConfigurationNamespace()
        {
            Assert.AreEqual("TopCoder.Util.ObjectFactory",
                PollingEventHandler.DefaultObjectFactoryNamespace,
                "should be 'TopCoder.Util.ObjectFactory'.");
        }

        /// <summary>
        /// Test ctor PollingEventHandler(),
        /// an instance should be created with the default namespace,
        /// and rssParser should be created too.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(tester, "Failed to create instance");
            Assert.AreEqual(PollingEventHandler.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(tester, "configurationNamespace"),
                "configurationNamespace should set to default");
            Assert.IsNotNull(TestHelper.GetFieldValue(tester, "rssParser"),
                "rss parser should be created.");
        }

        /// <summary>
        /// Test ctor PollingEventHandler(string configurationNamespace,
        /// string objectFactoryNamespace),
        /// when configurationNamespace is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ConfigurationNamespaceIsNull()
        {
            new PollingEventHandler(null, PollingEventHandler.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor PollingEventHandler(string configurationNamespace,
        /// string objectFactoryNamespace),
        /// when configurationNamespace is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ConfigurationNamespaceIsEmpty()
        {
            new PollingEventHandler(" \r \t \n ", PollingEventHandler.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor PollingEventHandler(string configurationNamespace,
        /// string objectFactoryNamespace),
        /// when objectFactoryNamespace is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_OjectFactoryNamespaceIsNull()
        {
            new PollingEventHandler(PollingEventHandler.DefaultObjectFactoryNamespace, null);
        }

        /// <summary>
        /// Test ctor PollingEventHandler(string configurationNamespace,
        /// string objectFactoryNamespace),
        /// when objectFactoryNamespace is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_OjectFactoryNamespaceIsEmpty()
        {
            new PollingEventHandler(PollingEventHandler.DefaultObjectFactoryNamespace, " \r \t \n ");
        }

        /// <summary>
        /// Test ctor PollingEventHandler(string configurationNamespace,
        /// string objectFactoryNamespace),
        /// when both arguments are valid, ArgumentException is expected.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            PollingEventHandler handler = new PollingEventHandler(
                PollingEventHandler.DefaultConfigurationNamespace,
                PollingEventHandler.DefaultObjectFactoryNamespace);

            // check
            Assert.IsNotNull(handler, "Failed to create handler");
            Assert.AreEqual(PollingEventHandler.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(handler, "configurationNamespace"),
                "configurationNamespace should set to default");
            Assert.IsNotNull(TestHelper.GetFieldValue(handler, "rssParser"),
                "rss parser should be created.");
        }

        /// <summary>
        /// Test ctor PollingEventHandler(IRSSParser rssParser),
        /// when rssParser is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_RssParserIsNull()
        {
            new PollingEventHandler(null);
        }

        /// <summary>
        /// Test ctor PollingEventHandler(IRSSParser rssParser),
        /// when rssParser is null, ArgumentNullException is expected.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            IRSSParser parser = new Atom10Parser();
            PollingEventHandler handler = new PollingEventHandler(parser);

            Assert.IsNotNull(handler, "Failed to create handler");
            Assert.AreEqual(PollingEventHandler.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(handler, "configurationNamespace"),
                "configurationNamespace should set to default");
            Assert.AreEqual(parser, TestHelper.GetFieldValue(handler, "rssParser"),
                "rss parser should be set.");
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
        /// when args is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestHandleEvent_ArgsIsNull()
        {
            tester.HandleEvent(this, null);
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// since the webbrowser has no context, HandleEventException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(HandleEventException))]
        public void TestHandleEvent_Failed()
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
            BrowserForm form = new BrowserForm();
            MsieClientLogic context = new MsieClientLogic(form.GetWebBrowserClass());

            PollingEventHandler handler = new PollingEventHandler();
            ExtensionEventArgs args = new ExtensionEventArgs("Polling", context);
            handler.HandleEvent(this, args);
        }
    }
}

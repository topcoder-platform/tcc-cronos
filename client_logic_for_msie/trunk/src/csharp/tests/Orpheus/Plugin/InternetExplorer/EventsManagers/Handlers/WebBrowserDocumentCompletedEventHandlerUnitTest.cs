/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * WebBrowserDocumentCompletedEventHandlerUnitTest.cs
 */

using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.Mock;
using Orpheus.Plugin.InternetExplorer.Persistence;
using MsHtmHstInterop;
using SHDocVw;
using TopCoder.Util.BloomFilter;
using Orpheus.Plugin.InternetExplorer.Mock;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// Unit test for <c>WebBrowserDocumentCompletedEventHandler</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class WebBrowserDocumentCompletedEventHandlerUnitTest
    {
        /// <summary>
        /// The event name used to construct args.
        /// </summary>
        private const string EVENT_NAME = "DocumentCompleted";

        /// <summary>
        /// An instance of WebBrowserDocumentCompletedEventHandler to perform test on.
        /// </summary>
        private WebBrowserDocumentCompletedEventHandler tester;

        /// <summary>
        /// The extension event arg used in <c>HandleEvent</c>.
        /// </summary>
        private ExtensionEventArgs args;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();
            tester = new WebBrowserDocumentCompletedEventHandler();
            args = new ExtensionEventArgs(EVENT_NAME, new MsieClientLogic(new WebBrowserClass()));
        }

        /// <summary>
        /// Tear down for each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            TestHelper.ClearNamespace();
        }

        /// <summary>
        /// Test inheritance,
        /// WebBrowserDocumentCompletedEventHandler should implement IExtensionEventHandler.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsTrue(tester is IExtensionEventHandler,
                "WebBrowserDocumentCompletedEventHandler should implement IExtensionEventHandler");
        }

        /// <summary>
        /// Test const DefaultConfigurationNamespace,
        /// it should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers'.
        /// </summary>
        [Test]
        public void TestDefaultConfigurationNamespace()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers",
                WebBrowserDocumentCompletedEventHandler.DefaultConfigurationNamespace);
        }

        /// <summary>
        /// Test ctor WebBrowserDocumentCompletedEventHandler(),
        /// an instance should be created with the default namespace.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(tester, "an instance should be created with the default namespace.");
            Assert.AreEqual(WebBrowserDocumentCompletedEventHandler.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(tester, "configurationNamespace"),
                "configurationNamespace should be set to default");
        }

        /// <summary>
        /// Test ctor WebBrowserDocumentCompletedEventHandler(string configurationNamespace),
        /// when configurationNamespace is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ConfigurationNamespaceIsNull()
        {
            new WebBrowserDocumentCompletedEventHandler(null);
        }

        /// <summary>
        /// Test ctor WebBrowserDocumentCompletedEventHandler(string configurationNamespace),
        /// when configurationNamespace is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ConfigurationNamespaceIsEmpty()
        {
            new WebBrowserDocumentCompletedEventHandler(" \r \n \t ");
        }

        /// <summary>
        /// Test ctor WebBrowserDocumentCompletedEventHandler(string configurationNamespace),
        /// when configurationNamespace is not empty string, instance with the given namespace
        /// should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            const string Ns = "Test only";
            WebBrowserDocumentCompletedEventHandler handler =
                new WebBrowserDocumentCompletedEventHandler(Ns);
            Assert.IsNotNull(handler, "An instance with the given namespace should be created.");
            Assert.AreEqual(Ns, TestHelper.GetFieldValue(handler, "configurationNamespace"),
                "The configurationNamespace is not set correctly.");
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
        /// since the webBrowser don't contain context, HandleEventException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(HandleEventException))]
        public void TestHandleEvent_Failed()
        {
            tester.HandleEvent(this, args);
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// test the document content.
        /// </summary>
        [Test]
        public void TestHandleEvent()
        {
            BrowserForm form = new BrowserForm();
            MsieClientLogic context = new MsieClientLogic(form.GetWebBrowserClass());

            WebBrowserDocumentCompletedEventHandler handler = new WebBrowserDocumentCompletedEventHandler();
            form.Navigate("www.topcoder.com");
            context.BloomFilter.Add("www.topcoder.com");

            ExtensionEventArgs args = new ExtensionEventArgs("DocumentCompleted", context);
            handler.HandleEvent(this, args);
        }
    }
}

/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests.EventsManager.Factories
{
    /// <summary>
    /// Accuracy tests for <c>WebBrowserDocumentCompletedEventHandler</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class WebBrowserDocumentCompletedEventHandlerAccTests
    {
        /// <summary>
        /// The WebBrowserDocumentCompletedEventHandler instance to test.
        /// </summary>
        private WebBrowserDocumentCompletedEventHandler handler;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();

            handler = new WebBrowserDocumentCompletedEventHandler();
        }

        /// <summary>
        /// Clean up for each test case.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            TestHelper.ClearNamespace();
        }

        /// <summary>
        /// Accuracy test of namespace constants.
        /// </summary>
        [Test]
        public void TestNamespaces()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers",
                            WebBrowserDocumentCompletedEventHandler.DefaultConfigurationNamespace,
                            "Not the expected default namespace.");
        }

        /// <summary>
        /// Accuracy test of the default constructor.
        /// </summary>
        [Test]
        public void TestConstructor1()
        {
            string configNS = (string) AccuracyHelper.GetPrivateFieldValue(handler, "configurationNamespace");

            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers", configNS,
                            "Not the expected configuration namespace.");
        }

        /// <summary>
        /// Accuracy test of the constructor that receives namespaces.
        /// </summary>
        [Test]
        public void TestConstructor2()
        {
            handler = new WebBrowserDocumentCompletedEventHandler(
                "Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers");

            string configNS = (string) AccuracyHelper.GetPrivateFieldValue(handler, "configurationNamespace");

            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers", configNS,
                            "Not the expected configuration namespace.");
        }
    }
}

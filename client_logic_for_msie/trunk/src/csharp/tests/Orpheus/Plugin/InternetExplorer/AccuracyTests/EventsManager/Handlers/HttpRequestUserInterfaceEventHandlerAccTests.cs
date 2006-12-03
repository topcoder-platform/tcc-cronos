/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests.EventsManager.Factories
{
    /// <summary>
    /// Accuracy tests for <c>HttpRequestUserInterfaceEventHandler</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class HttpRequestUserInterfaceEventHandlerAccTests
    {
        /// <summary>
        /// The HttpRequestUserInterfaceEventHandler instance to test.
        /// </summary>
        private HttpRequestUserInterfaceEventHandler handler;

        /// <summary>
        /// The MsieClientLogic instance used in test cases.
        /// </summary>
        private MsieClientLogic mcl;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();
            mcl = new MsieClientLogic(new WebBrowserClass());

            handler = new HttpRequestUserInterfaceEventHandler();
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
                            HttpRequestUserInterfaceEventHandler.DefaultConfigurationNamespace,
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
            handler = new HttpRequestUserInterfaceEventHandler("configNS");

            string configNS = (string) AccuracyHelper.GetPrivateFieldValue(handler, "configurationNamespace");

            Assert.AreEqual("configNS", configNS,
                            "Not the expected configuration namespace.");
        }
    }
}

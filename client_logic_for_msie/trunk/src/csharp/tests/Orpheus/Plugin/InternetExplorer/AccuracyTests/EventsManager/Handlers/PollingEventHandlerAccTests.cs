/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;
using TopCoder.Util.RSS.Atom.IO;
using TopCoder.Util.RSS.IO;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests.EventsManager.Hanlders
{
    /// <summary>
    /// Accuracy tests for <c>PollingEventHandler</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class PollingEventHandlerAccTests
    {
        /// <summary>
        /// The PollingEventHandler instance to test.
        /// </summary>
        private PollingEventHandler handler;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            AccuracyHelper.LoadConfiguration();

            handler = new PollingEventHandler();
        }

        /// <summary>
        /// Clean up for each test case.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            AccuracyHelper.ClearConfiguration();
        }

        /// <summary>
        /// Accuracy test of namespace constants.
        /// </summary>
        [Test]
        public void TestNamespaces()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers",
                            PollingEventHandler.DefaultConfigurationNamespace,
                            "Not the expected default namespace.");

            Assert.AreEqual("TopCoder.Util.ObjectFactory",
                            PollingEventHandler.DefaultObjectFactoryNamespace,
                            "Not the expected object factory namespace.");
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
            handler = new PollingEventHandler("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers",
                                              "TopCoder.Util.ObjectFactory");

            string configNS = (string) AccuracyHelper.GetPrivateFieldValue(handler, "configurationNamespace");

            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers", configNS,
                            "Not the expected configuration namespace.");
        }

        /// <summary>
        /// Accuracy test of the constructor that receives namespaces.
        /// </summary>
        [Test]
        public void TestConstructor3()
        {
            IRSSParser parser = new Atom10Parser();
            handler = new PollingEventHandler(parser);

            string configNS = (string) AccuracyHelper.GetPrivateFieldValue(handler, "configurationNamespace");

            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers", configNS,
                            "Not the expected configuration namespace.");
            Assert.AreEqual(parser, AccuracyHelper.GetPrivateFieldValue(handler, "rssParser"),
                            "Not the expected object factory namespace.");

        }
    }
}
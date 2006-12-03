/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Factories;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests.EventsManager.Factories
{
    /// <summary>
    /// Accuracy tests for <c>DefaultExtensionEventHandlerFactory</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class DefaultExtensionEventHandlerFactoryAccTests
    {
        /// <summary>
        /// The factory instance to test.
        /// </summary>
        private DefaultExtensionEventHandlerFactory factory;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            AccuracyHelper.LoadConfiguration();
            factory = new DefaultExtensionEventHandlerFactory();
        }

        /// <summary>
        /// Tear down for each test case.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            AccuracyHelper.ClearConfiguration();
        }

        /// <summary>
        /// Accuracy test of the default constructor.
        /// </summary>
        [Test]
        public void TestConstructor1()
        {
            string configNS = (string) AccuracyHelper.GetPrivateFieldValue(factory, "configurationNamespace");
            string objectFactoryNS = (string) AccuracyHelper.GetPrivateFieldValue(factory, "objectFactoryNamespace");

            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Factories", configNS,
                            "Not the expected configuration namespace.");
            Assert.AreEqual("TopCoder.Util.ObjectFactory", objectFactoryNS,
                            "Not the expected object factory namespace.");
        }

        /// <summary>
        /// Accuracy test of the constructor that receives namespaces.
        /// </summary>
        [Test]
        public void TestConstructor2()
        {
            factory = new DefaultExtensionEventHandlerFactory("configNS", "objectFactoryNS");

            string configNS = (string) AccuracyHelper.GetPrivateFieldValue(factory, "configurationNamespace");
            string objectFactoryNS = (string) AccuracyHelper.GetPrivateFieldValue(factory, "objectFactoryNamespace");

            Assert.AreEqual("configNS", configNS,
                            "Not the expected configuration namespace.");
            Assert.AreEqual("objectFactoryNS", objectFactoryNS,
                            "Not the expected object factory namespace.");
        }

        /// <summary>
        /// Accuracy test of the <code>CreateHandlers()</code> method.
        /// </summary>
        [Test]
        public void TestCreateHandlers()
        {
            IExtensionEventHandler[] handlers = factory.CreateHandlers("test");
            Assert.IsTrue(handlers[0] is PollingEventHandler, "Handlers are not created correctly.");
            Assert.IsTrue(handlers[1] is HttpRequestUserInterfaceEventHandler,
                          "Handlers are not created correctly.");
        }
    }
}

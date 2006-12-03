/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using MsHtmHstInterop;
using SHDocVw;
using Orpheus.Plugin.InternetExplorer.Persistence;
using Orpheus.Plugin.InternetExplorer.WindowNavigators;
using TopCoder.Util.BloomFilter;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// Accuracy tests for <c>MsieClientLogic</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class MsieClientLogicAccTests
    {
        /// <summary>
        /// An instance of <c>MsieClientLogic</c> to test.
        /// </summary>
        private MsieClientLogic mcl;

        /// <summary>
        /// The window naviagator used in test cases.
        /// </summary>
        private IWebBrowserWindowNavigator webBrowserWindowNavigator;

        /// <summary>
        /// The events manager used in test cases.
        /// </summary>
        private IExtensionEventsManager eventsManager;

        /// <summary>
        /// The scripting object used in test cases.
        /// </summary>
        private object scriptingObject;

        /// <summary>
        /// The doc host ui handler used in test cases.
        /// </summary>
        private IDocHostUIHandler browserCustomization;

        /// <summary>
        /// The persistence used in test cases.
        /// </summary>
        private IPersistence persistence;

        /// <summary>
        /// The bloom filter used in test cases.
        /// </summary>
        private BloomFilter bloomFilter;

        /// <summary>
        /// The web broswer used in test cases.
        /// </summary>
        private WebBrowserClass webBrowser;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            AccuracyHelper.LoadConfiguration();
            webBrowser = new WebBrowserClass();

            mcl = new MsieClientLogic(webBrowser);

            bloomFilter = new BloomFilter(10, 0.95f);
            persistence = new RegistryPersistence();
            webBrowserWindowNavigator = new DefaultWebBrowserWindowNavigator();
            eventsManager = new DefaultExtensionEventsManager();
            browserCustomization = new DefaultDocHostUIHandler(mcl);
            scriptingObject = new ScriptingObject(mcl);

            mcl = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                                      webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
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
        /// Accuracy test of the constructor that load members from the default configuration namespaces.
        /// </summary>
        [Test]
        public void TestConstructor1()
        {
            mcl = new MsieClientLogic(webBrowser);

            // test if members are loaded from the configuration namespaces
            Assert.AreEqual(webBrowser, mcl.WebBrowser, "Failed to set WebBrowser");
            Assert.AreEqual(bloomFilter.GetType().FullName,
                            mcl.BloomFilter.GetType().FullName, "Failed to set BloomFilter");
            Assert.AreEqual(persistence.GetType().FullName,
                            mcl.Persistence.GetType().FullName, "Failed to set Persistence");
            Assert.AreEqual(webBrowserWindowNavigator.GetType().FullName,
                            mcl.WebBrowserWindowNavigator.GetType().FullName, "Failed to set WebBrowserWindowNavigator");
            Assert.AreEqual(eventsManager.GetType().FullName,
                            mcl.EventsManager.GetType().FullName, "Failed to set EventsManager");
            Assert.AreEqual(browserCustomization.GetType().FullName,
                            mcl.BrowserCustomization.GetType().FullName, "Failed to set BrowserCustomization");
            Assert.AreEqual(scriptingObject.GetType().FullName,
                            mcl.ScriptingObject.GetType().FullName, "Failed to set ScriptingObject");
        }

        /// <summary>
        /// Accuracy test of the constructor that load members from the given configuration namespaces.
        /// </summary>
        [Test]
        public void TestConstructor2()
        {
            mcl = new MsieClientLogic(webBrowser, "Orpheus.Plugin.InternetExplorer", "TopCoder.Util.ObjectFactory");

            // test if members are loaded from the configuration namespaces
            Assert.AreEqual(webBrowser, mcl.WebBrowser, "Failed to set WebBrowser");
            Assert.AreEqual(bloomFilter.GetType().FullName,
                            mcl.BloomFilter.GetType().FullName, "Failed to set BloomFilter");
            Assert.AreEqual(persistence.GetType().FullName,
                            mcl.Persistence.GetType().FullName, "Failed to set Persistence");
            Assert.AreEqual(webBrowserWindowNavigator.GetType().FullName,
                            mcl.WebBrowserWindowNavigator.GetType().FullName, "Failed to set WebBrowserWindowNavigator");
            Assert.AreEqual(eventsManager.GetType().FullName,
                            mcl.EventsManager.GetType().FullName, "Failed to set EventsManager");
            Assert.AreEqual(browserCustomization.GetType().FullName,
                            mcl.BrowserCustomization.GetType().FullName, "Failed to set BrowserCustomization");
            Assert.AreEqual(scriptingObject.GetType().FullName,
                            mcl.ScriptingObject.GetType().FullName, "Failed to set ScriptingObject");
        }

        /// <summary>
        /// Accuracy test of the constructor that use the given members.
        /// </summary>
        [Test]
        public void TestConstructor3()
        {
            // test if members are loaded from the configuration namespaces
            Assert.AreEqual(webBrowser, mcl.WebBrowser, "Failed to set WebBrowser");
            Assert.AreEqual(bloomFilter.GetType().FullName,
                            mcl.BloomFilter.GetType().FullName, "Failed to set BloomFilter");
            Assert.AreEqual(persistence.GetType().FullName,
                            mcl.Persistence.GetType().FullName, "Failed to set Persistence");
            Assert.AreEqual(webBrowserWindowNavigator.GetType().FullName,
                            mcl.WebBrowserWindowNavigator.GetType().FullName, "Failed to set WebBrowserWindowNavigator");
            Assert.AreEqual(eventsManager.GetType().FullName,
                            mcl.EventsManager.GetType().FullName, "Failed to set EventsManager");
            Assert.AreEqual(browserCustomization.GetType().FullName,
                            mcl.BrowserCustomization.GetType().FullName, "Failed to set BrowserCustomization");
            Assert.AreEqual(scriptingObject.GetType().FullName,
                            mcl.ScriptingObject.GetType().FullName, "Failed to set ScriptingObject");
        }

        /// <summary>
        /// Accuracy test of the propery <code>WebBrowser</code>
        /// </summary>
        [Test]
        public void TestGetWebBrowser()
        {
            Assert.AreEqual(webBrowser, mcl.WebBrowser, "Failed to get WebBrowser");
        }

        /// <summary>
        /// Accuracy test of the propery <code>BloomFilter</code>
        /// </summary>
        [Test]
        public void TestGetBloomFilter()
        {
            BloomFilter bf = new BloomFilter(10, 0.99f);
            mcl.BloomFilter = bf;

            Assert.AreEqual(bf, mcl.BloomFilter, "Failed to get BloomFilter");

        }

        /// <summary>
        /// Accuracy test of the propery <code>Persistence</code>
        /// </summary>
        [Test]
        public void TestGetPersistence()
        {
            Assert.AreEqual(persistence, mcl.Persistence, "Failed to get Persistence");
        }

        /// <summary>
        /// Accuracy test of the propery <code>WebBrowserWindowNavigator</code>
        /// </summary>
        [Test]
        public void TestGetWebBrowserWindowNavigator()
        {
            Assert.AreEqual(webBrowserWindowNavigator, mcl.WebBrowserWindowNavigator,
                            "Failed to get WebBrowserWindowNavigator");
        }

        /// <summary>
        /// Accuracy test of the propery <code>EventsManager</code>
        /// </summary>
        [Test]
        public void TestGetEventsManager()
        {
            Assert.AreEqual(eventsManager, mcl.EventsManager,
                            "Failed to get EventsManager");
        }

        /// <summary>
        /// Accuracy test of the propery <code>BrowserCustomization</code>
        /// </summary>
        [Test]
        public void TestGetBrowserCustomization()
        {
            Assert.AreEqual(browserCustomization, mcl.BrowserCustomization,
                            "Failed to get BrowserCustomization");
        }

        /// <summary>
        /// Accuracy test of the propery <code>ScriptingObject</code>
        /// </summary>
        [Test]
        public void TestGetScriptingObject()
        {
            Assert.AreEqual(scriptingObject, mcl.ScriptingObject,
                            "Failed to get ScriptingObject");
        }

    }
}

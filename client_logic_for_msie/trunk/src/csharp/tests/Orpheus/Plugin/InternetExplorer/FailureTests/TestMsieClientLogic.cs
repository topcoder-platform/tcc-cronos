using System;
using NUnit.Framework;
using TopCoder.Util.ConfigurationManager;
using MsHtmHstInterop;
using SHDocVw;
using Orpheus.Plugin.InternetExplorer.Persistence;
using Orpheus.Plugin.InternetExplorer.WindowNavigators;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using TopCoder.Util.BloomFilter;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for MsieClientLogic class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestMsieClientLogic
    {
        /// <summary>
        /// The WebBrowserClass instance used to test.
        /// </summary>
        private WebBrowserClass wbc = new WebBrowserClass();
        /// <summary>
        /// The MsieClientLogic instance used to test.
        /// </summary>
        private MsieClientLogic logic = null;

                /// <summary>
        /// The bloom filter used to construct MsieClientLogic.
        /// </summary>
        private BloomFilter bloomFilter;

        /// <summary>
        /// The persistence used to construct MsieClientLogic.
        /// </summary>
        private IPersistence persistence;

        /// <summary>
        /// The window naviagator used to construct MsieClientLogic.
        /// </summary>
        private IWebBrowserWindowNavigator nav;

        /// <summary>
        /// The events manager used to construct MsieClientLogic.
        /// </summary>
        private IExtensionEventsManager manager;

        /// <summary>
        /// The doc host ui handler used to construct MsieClientLogic.
        /// </summary>
        private IDocHostUIHandler handler;

        private ScriptingObject so = null;

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
            logic = new MsieClientLogic(wbc);

            bloomFilter = new BloomFilter(10, 0.9f);
            persistence = new RegistryPersistence();
            nav = new DefaultWebBrowserWindowNavigator();
            manager = new DefaultExtensionEventsManager();
            handler = new DefaultDocHostUIHandler(logic);
            so = new ScriptingObject(logic);
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
        /// Tests <c>BloomFilter</c> property set with null value.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestBloomFilterSetNullValue()
        {
            logic.BloomFilter = null;
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser)</c> method with null WebBrowserClass webBrowser
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullWebBrowser()
        {
            new MsieClientLogic(null);
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser,string configurationNamsepace,
        /// string objectFactoryNamespace)</c>
        /// method with null WebBrowserClass webBrowser
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic2_NullWebBrowser()
        {
            new MsieClientLogic(null, "ns", "ns2");
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser,string configurationNamsepace,
        /// string objectFactoryNamespace)</c>
        /// ethod with null string configurationNamsepace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullConfigurationNamsepace()
        {
            new MsieClientLogic(wbc, null, "ns2");
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser,string configurationNamsepace,
        /// string objectFactoryNamespace)</c> method with empty string configurationNamsepace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestMsieClientLogic_EmptyConfigurationNamsepace()
        {
            new MsieClientLogic(wbc, " ", "ns2");
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser,string configurationNamsepace,
        /// string objectFactoryNamespace)</c> method with null string objectFactoryNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullObjectFactoryNamespace()
        {
            new MsieClientLogic(wbc, "ns", null);
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser,string configurationNamsepace,
        /// string objectFactoryNamespace)</c> method with empty string objectFactoryNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestMsieClientLogic_EmptyObjectFactoryNamespace()
        {
            new MsieClientLogic(wbc, "ns", " ");
        }


        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter, IPersistence persistence,
        /// IWebBrowserWindowNavigator webBrowserWindowNavigator, IExtensionEventsManager eventsManager,
        /// IDocHostUIHandler browserCustomization, object scriptingObject)</c> method with null
        /// WebBrowserClass webBrowser
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic3_NullWebBrowser()
        {
            new MsieClientLogic(null, bloomFilter, persistence, nav, manager, handler, so);
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization, object scriptingObject)</c>
        /// method with null BloomFilter bloomFilter
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullBloomFilter()
        {
            new MsieClientLogic(wbc, null, persistence, nav, manager, handler, so);
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter, IPersistence persistence,
        /// IWebBrowserWindowNavigator webBrowserWindowNavigator, IExtensionEventsManager eventsManager,
        /// IDocHostUIHandler browserCustomization, object scriptingObject)</c> method with null IPersistence persistence
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullPersistence()
        {
            new MsieClientLogic(wbc, bloomFilter, null, nav, manager, handler, so);
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter, IPersistence persistence,
        /// IWebBrowserWindowNavigator webBrowserWindowNavigator, IExtensionEventsManager eventsManager,
        /// IDocHostUIHandler browserCustomization, object scriptingObject)</c> method with null
        /// IWebBrowserWindowNavigator webBrowserWindowNavigator
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullWebBrowserWindowNavigator()
        {
            new MsieClientLogic(wbc, bloomFilter, persistence, null, manager, handler, so);
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator, IExtensionEventsManager
        /// eventsManager, IDocHostUIHandler browserCustomization, object scriptingObject)</c> method
        /// with null IExtensionEventsManager eventsManager
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullEventsManager()
        {
            new MsieClientLogic(wbc, bloomFilter, persistence, nav, null, handler, so);
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter, IPersistence persistence,
        /// IWebBrowserWindowNavigator webBrowserWindowNavigator, IExtensionEventsManager eventsManager,
        /// IDocHostUIHandler browserCustomization, object scriptingObject)</c> method with null
        /// IDocHostUIHandler browserCustomization
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullBrowserCustomization()
        {
            new MsieClientLogic(wbc, bloomFilter, persistence, nav, manager, null, so);
        }
        /// <summary>
        /// Tests <c>MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization, object scriptingObject)</c>
        /// method with null object scriptingObject
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestMsieClientLogic_NullScriptingObject()
        {
            new MsieClientLogic(wbc, bloomFilter, persistence, nav, manager, handler, null);
        }


        /// <summary>
        /// Tests <c>CustomizeWebBrowser(WebBrowserClass webBrowser)</c> method with null WebBrowserClass webBrowser
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestCustomizeWebBrowser_NullWebBrowser()
        {
            logic.CustomizeWebBrowser(null);
        }
    }
}

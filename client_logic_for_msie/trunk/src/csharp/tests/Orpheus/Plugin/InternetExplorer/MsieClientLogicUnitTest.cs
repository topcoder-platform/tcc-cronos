/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * MsieClientLogicUnitTest.cs
 */

using System;
using NUnit.Framework;
using MsHtmHstInterop;
using Orpheus.Plugin.InternetExplorer.Persistence;
using Orpheus.Plugin.InternetExplorer.WindowNavigators;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using TopCoder.Util.BloomFilter;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <c>MsieClientLogic</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class MsieClientLogicUnitTest
    {
        /// <summary>
        /// An instance of <c>MsieClientLogic</c> to perform test on.
        /// </summary>
        private MsieClientLogic tester;

        /// <summary>
        /// The web broswer used to construct MsieClientLogic.
        /// </summary>
        private WebBrowserClass webBrowser;

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
        private IWebBrowserWindowNavigator webBrowserWindowNavigator;

        /// <summary>
        /// The events manager used to construct MsieClientLogic.
        /// </summary>
        private IExtensionEventsManager eventsManager;

        /// <summary>
        /// The doc host ui handler used to construct MsieClientLogic.
        /// </summary>
        private IDocHostUIHandler browserCustomization;

        /// <summary>
        /// The scripting object used to construct MsieClientLogic.
        /// </summary>
        private object scriptingObject;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();
            webBrowser = new WebBrowserClass();
            tester = new MsieClientLogic(webBrowser);

            bloomFilter = new BloomFilter(10, 0.9f);
            persistence = new RegistryPersistence();
            webBrowserWindowNavigator = new DefaultWebBrowserWindowNavigator();
            eventsManager = new DefaultExtensionEventsManager();
            browserCustomization = new DefaultDocHostUIHandler(tester);
            scriptingObject = new ScriptingObject(tester);
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
        /// Test const DefaultConfigurationNamepsace,
        /// it should be 'Orpheus.Plugin.InternetExplorer'.
        /// </summary>
        [Test]
        public void TestDefaultConfigurationNamepsace()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer",
                MsieClientLogic.DefaultConfigurationNamepsace,
                "DefaultConfigurationNamepsace should be 'Orpheus.Plugin.InternetExplorer'.");
        }

        /// <summary>
        /// Test const DefaultObjectFactoryNamespace,
        /// it should be 'TopCoder.Util.ObjectFactory'.
        /// </summary>
        [Test]
        public void TestDefaultObjectFactoryNamespace()
        {
            Assert.AreEqual("TopCoder.Util.ObjectFactory",
                MsieClientLogic.DefaultObjectFactoryNamespace,
                "DefaultObjectFactoryNamespace should be 'TopCoder.Util.ObjectFactory'.");
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser),
        /// when webBroswer is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor1_WebBrowserIsNull()
        {
            new MsieClientLogic(null);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser),
        /// when webBroswer is not null, instance with default namespace should be created.
        /// </summary>
        public void TestCtor1()
        {
            AssertMsieClientLogic(tester);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, string configurationNamsepace,
        /// string objectFactoryNamespace), when webBrowser is null,
        /// ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_WebBrowserIsNull()
        {
            new MsieClientLogic(null, MsieClientLogic.DefaultConfigurationNamepsace,
                MsieClientLogic.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, string configurationNamsepace,
        /// string objectFactoryNamespace), when configurationNamsepace is null,
        /// ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ConfigurationNamsepaceIsNull()
        {
            new MsieClientLogic(webBrowser, null,
                MsieClientLogic.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, string configurationNamsepace,
        /// string objectFactoryNamespace), when configurationNamsepace is empty,
        /// ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ConfigurationNamsepaceIsEmpty()
        {
            new MsieClientLogic(webBrowser, "  \t \r \n ",
                MsieClientLogic.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, string configurationNamsepace,
        /// string objectFactoryNamespace), when objectFactoryNamespace is null,
        /// ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ObjectFactoryNamespaceIsNull()
        {
            new MsieClientLogic(webBrowser, MsieClientLogic.DefaultConfigurationNamepsace,
                null);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, string configurationNamsepace,
        /// string objectFactoryNamespace), when objectFactoryNamespace is empty,
        /// ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ObjectFactoryNamespaceIsEmpty()
        {
            new MsieClientLogic(webBrowser, MsieClientLogic.DefaultConfigurationNamepsace,
                "  \t \r \n ");
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, string configurationNamsepace,
        /// string objectFactoryNamespace), when webBrowser is not null, both namespace
        /// are valid instance should be created.
        /// </summary>
        public void TestCtor2()
        {
            AssertMsieClientLogic(
                new MsieClientLogic(webBrowser, MsieClientLogic.DefaultConfigurationNamepsace,
                MsieClientLogic.DefaultObjectFactoryNamespace));
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization,
        /// object scriptingObject), when webBrowser is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_WebBrowserIsNull()
        {
            new MsieClientLogic(null, bloomFilter, persistence, webBrowserWindowNavigator,
                eventsManager, browserCustomization, scriptingObject);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization,
        /// object scriptingObject), when bloomFilter is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_BloomFilterIsNull()
        {
            new MsieClientLogic(webBrowser, null, persistence, webBrowserWindowNavigator,
                eventsManager, browserCustomization, scriptingObject);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization,
        /// object scriptingObject), when persistence is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_PersistenceIsNull()
        {
            new MsieClientLogic(webBrowser, bloomFilter, null, webBrowserWindowNavigator,
                eventsManager, browserCustomization, scriptingObject);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization,
        /// object scriptingObject), when webBrowserWindowNavigator is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_WebBrowserWindowNavigatorIsNull()
        {
            new MsieClientLogic(webBrowser, bloomFilter, persistence, null,
                eventsManager, browserCustomization, scriptingObject);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization,
        /// object scriptingObject), when webBrowserWindowNavigator is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_EventsManagerIsNull()
        {
            new MsieClientLogic(webBrowser, bloomFilter, persistence, webBrowserWindowNavigator,
                null, browserCustomization, scriptingObject);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization,
        /// object scriptingObject), when browserCustomization is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_BrowserCustomizationIsNull()
        {
            new MsieClientLogic(webBrowser, bloomFilter, persistence, webBrowserWindowNavigator,
                eventsManager, null, scriptingObject);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization,
        /// object scriptingObject), when scriptingObject is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_ScriptingObjectIsNull()
        {
            new MsieClientLogic(webBrowser, bloomFilter, persistence, webBrowserWindowNavigator,
                eventsManager, browserCustomization, null);
        }

        /// <summary>
        /// Test ctor MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
        /// IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
        /// IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization,
        /// object scriptingObject), all arguments are not null, instance should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);

            // check the internal members
            Assert.IsNotNull(context, "Failed to create MsieClientLogic");
            Assert.AreEqual(webBrowser, context.WebBrowser, "Failed to set WebBrowser");
            Assert.AreEqual(bloomFilter, context.BloomFilter, "Failed to set BloomFilter");
            Assert.AreEqual(persistence, context.Persistence, "Failed to set Persistence");
            Assert.AreEqual(webBrowserWindowNavigator, context.WebBrowserWindowNavigator,
                "Failed to set WebBrowserWindowNavigator");
            Assert.AreEqual(eventsManager, context.EventsManager, "Failed to set EventsManager");
            Assert.AreEqual(browserCustomization, context.BrowserCustomization,
                "Failed to set BrowserCustomization");
            Assert.AreEqual(scriptingObject, context.ScriptingObject,
                "Failed to set ScriptingObject");
        }

        /// <summary>
        /// Check the msie client logic is expected.
        /// </summary>
        ///
        /// <param name="context">the msie client logic</param>
        private void AssertMsieClientLogic(MsieClientLogic context)
        {
            Assert.IsNotNull(context, "Failed to initialize the MsieClientLogic");
        }

        /// <summary>
        /// Test property WebBrowser Getter,
        /// it should return the WebBrowser set in constructor.
        /// </summary>
        public void TestWebBrowser_Getter()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
            Assert.AreEqual(webBrowser, context.WebBrowser, "Failed to Get WebBrowser");
        }

        /// <summary>
        /// Test property BloomFilter Getter,
        /// it should return the BloomFilter set in constructor.
        /// </summary>
        public void TestBloomFilter_Getter()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
            Assert.AreEqual(bloomFilter, context.BloomFilter, "Failed to Get BloomFilter");
        }

        /// <summary>
        /// Test property BloomFilter Setter,
        /// when set to null, throw ArgumentNullException.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestBloomFilter_Setter_Null()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
            context.BloomFilter = null;
        }

        /// <summary>
        /// Test property BloomFilter Setter,
        /// the inner bloomFilter should be updated.
        /// </summary>
        [Test]
        public void TestBloomFilter_Setter()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);

            BloomFilter filter = new BloomFilter(100, 0.5f);
            context.BloomFilter = filter;
            Assert.AreEqual(filter, context.BloomFilter,
                "Failed to update the inner bloomfilter.");
        }

        /// <summary>
        /// Test property UpdatesPollingTimer Setter,
        /// the created updates timer should be returned.
        /// </summary>
        [Test]
        public void TestUpdatesPollingTimer_Getter()
        {
            Assert.IsNotNull(tester.UpdatesPollingTimer, "Failed to create update timer");
            Assert.IsTrue(tester.UpdatesPollingTimer.Enabled,
                "The timer should be start");
            Assert.AreEqual(60 * 1000, tester.UpdatesPollingTimer.Interval,
                "The timer's Interval is not correct");
        }

        /// <summary>
        /// Test property Persistence Getter,
        /// it should return the Persistence set in constructor.
        /// </summary>
        [Test]
        public void TestPersistence_Getter()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
            Assert.AreEqual(persistence, context.Persistence, "Failed to Get Persistence");
        }

        /// <summary>
        /// Test property WebBrowserWindowNavigator Getter,
        /// it should return the WebBrowserWindowNavigator set in constructor.
        /// </summary>
        [Test]
        public void TestWebBrowserWindowNavigator_Getter()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
            Assert.AreEqual(webBrowserWindowNavigator, context.WebBrowserWindowNavigator,
                "Failed to Get WebBrowserWindowNavigator");
        }

        /// <summary>
        /// Test property EventsManager Getter,
        /// it should return the EventsManager set in constructor.
        /// </summary>
        [Test]
        public void TestEventsManager_Getter()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
            Assert.AreEqual(eventsManager, context.EventsManager,
                "Failed to Get EventsManager");
        }

        /// <summary>
        /// Test property BrowserCustomization Getter,
        /// it should return the BrowserCustomization set in constructor.
        /// </summary>
        [Test]
        public void TestBrowserCustomization_Getter()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
            Assert.AreEqual(browserCustomization, context.BrowserCustomization,
                "Failed to Get browserCustomization");
        }

        /// <summary>
        /// Test property ScriptingObject Getter,
        /// it should return the ScriptingObject set in constructor.
        /// </summary>
        [Test]
        public void TestScriptingObject_Getter()
        {
            MsieClientLogic context = new MsieClientLogic(webBrowser, bloomFilter, persistence,
                webBrowserWindowNavigator, eventsManager, browserCustomization, scriptingObject);
            Assert.AreEqual(scriptingObject, context.ScriptingObject,
                "Failed to Get ScriptingObject");
        }

        /// <summary>
        /// Test GetInstance(WebBrowserClass webBrowser), it should return the singleton.
        /// </summary>
        [Test]
        public void TestGetInstance()
        {
            Assert.AreEqual(MsieClientLogic.GetInstance(),
                MsieClientLogic.GetInstance(),
                "It should be singleton");
        }

        /// <summary>
        /// Test CustomizeWebBrowser(WebBrowserClass webBrowser),
        /// when webBrowser is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCustomizeWebBrowser_WebBroswerIsNull()
        {
            tester.CustomizeWebBrowser(null);
        }

        /// <summary>
        /// Test CustomizeWebBrowser(WebBrowserClass webBrowser),
        /// since webBrowser don't have context, WebBrowserCustomizationException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(WebBrowserCustomizationException))]
        public void TestCustomizeWebBrowser_Invalid()
        {
            tester.CustomizeWebBrowser(new WebBrowserClass());
        }

        /// <summary>
        /// Test OnDocumentCompleted(object pDisp, ref object url),
        /// the handle will be fired.
        /// </summary>
        [Test]
        public void TestOnDocumentCompleted()
        {
            object obj = new object();
            tester.OnDocumentCompleted(this, ref obj);
        }

        /// <summary>
        /// Test OnUpdatesPolling(object pDisp, ref object url),
        /// the handle will be fired.
        /// </summary>
        [Test]
        public void TestOnUpdatesPolling()
        {
            tester.OnUpdatesPolling(this, new EventArgs());
        }
    }
}

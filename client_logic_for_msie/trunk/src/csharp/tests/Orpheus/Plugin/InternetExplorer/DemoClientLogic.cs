/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DemoClientLogic.cs
 */

using System;
using System.IO;
using System.Drawing;
using NUnit.Framework;
using MsHtmHstInterop;
using Orpheus.Plugin.InternetExplorer.Mock;
using Orpheus.Plugin.InternetExplorer.Persistence;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using Orpheus.Plugin.InternetExplorer.WindowNavigators;
using TopCoder.Util.BloomFilter;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This class shows how to Working Programmatically with the Client Side Logic.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class DemoClientLogic
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
        /// Get the domain from url,
        /// the url may like http://www.topcoder.com/..., http:8080//www.topcoder.com/, or
        /// www.topcoder.com/.
        /// </summary>
        ///
        /// <param name="url">the url</param>
        /// <returns>the domain of the url</returns>
        private string GetDomain(string url)
        {
            // the http prefix if existed.
            int pos1 = url.Replace("\\", "/").IndexOf("//");
            int pos2 = url.IndexOf("/", pos1 < 0 ? 0 : pos1 + 2);

            if (pos1 < 0)
            {
                return (pos2 > 0) ? url.Substring(0, pos2) : url;
            }
            else
            {
                return url.Substring(pos1 + 2, pos2 - pos1 - 2);
            }
        }

        /// <summary>
        /// Shows how to Working Programmatically with the Client Side Logic.
        /// Create MsieClientLogic, Add/Remove/Get/Fire event handler.
        /// Navigate, persist value with the given key.
        /// </summary>
        [Test]
        public void Demo()
        {
            // create a new client side logic object
            MsieClientLogic logic = new MsieClientLogic(new WebBrowserClass());

            //or as a singleton
            logic = MsieClientLogic.GetInstance();

            // or using custom configuration namespaces
            logic = new MsieClientLogic(new WebBrowserClass(),
                MsieClientLogic.DefaultConfigurationNamepsace, MsieClientLogic.DefaultObjectFactoryNamespace);

            //get the browser
            WebBrowserClass browser = logic.WebBrowser;

            //get the events manager
            IExtensionEventsManager events = logic.EventsManager;

            //add a handler
            ExtensionEventHandlerDelegate d1 =  new ExtensionEventHandlerDelegate(
                new MockExtensionEventHandler().HandleEvent);
            ExtensionEventHandlerDelegate d2 =  new ExtensionEventHandlerDelegate(
                new MockExtensionEventHandler().HandleEvent);

            events.AddEventHandler("Demo", d1);
            events.AddEventHandler("Demo", d2);

            //get all handlers for an event
            ExtensionEventHandlerDelegate[] delegates=events.GetEventHandlers("Demo");

            //remove an event handler
            events.AddEventHandler("Demo", d1);

            //fire an event
            ExtensionEventArgs args = new ExtensionEventArgs("Demo", logic);
            events.FireEvent("Demo", this, args);

            //get the web browser window navigator
            IWebBrowserWindowNavigator navigator = logic.WebBrowserWindowNavigator;

            //navigate to a URL in a new window; false to navigate in the same browser window
            // navigator.Navigate(logic.Host, "http://www.topcoder.com", true);

            //set the content to the web browser window from a stream
            //get a stream to display in the browser
            using(Stream doc = new FileStream("../../test_files/msie/demo.html", FileMode.Open))
            {
                // navigator.Navigate(logic.Host, doc, true);
            }

            //get the persistence
            IPersistence storage = logic.Persistence;

            //persist a value
            storage["key"] = "value";

            //get the value back
            string value = storage["key"];

            //get or set the bloom filter
            BloomFilter filter = logic.BloomFilter;

            //get the browser customization object

            IDocHostUIHandler  handler = logic.BrowserCustomization;

            //get the scripting object
            object scriptingObject = logic.ScriptingObject;
        }
    }
}

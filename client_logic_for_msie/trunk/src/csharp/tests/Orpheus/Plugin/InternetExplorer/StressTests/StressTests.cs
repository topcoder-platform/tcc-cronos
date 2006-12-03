/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using System;
using Microsoft.Win32;
using System.Threading;
using Mshtml;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;
using TopCoder.Util.ConfigurationManager;
using Orpheus.Plugin.InternetExplorer.Mock;

namespace Orpheus.Plugin.InternetExplorer.StressTests
{
    /// <summary>
    /// Stress tests for the class <see cref="HttpRequestUserInterfaceEventHandler"/>.
    /// </summary>
    /// <author>urtks</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class StressTests
    {
        /// <summary>
        /// The config file for object factory.
        /// </summary>
        private const string CONFIG_OBJECT_FACTORY = "../../test_files/stresstests/object_factory.xml";

        /// <summary>
        /// The config file for this component
        /// </summary>
        private const string CONFIG_CLIENT_LOGIC = "../../test_files/stresstests/client_logic_for_msie.xml";

        /// <summary>
        /// The context used in args.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// The windows form containg WebBrowserClass.
        /// </summary>
        private StressTestsForm form;

        /// <summary>
        /// Setup the testing environment.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            ConfigManager cm = ConfigManager.GetInstance();
            cm.Clear(false);
            cm.LoadFile(CONFIG_CLIENT_LOGIC);
            cm.LoadFile(CONFIG_OBJECT_FACTORY);

            form = new StressTestsForm();

            context = new MsieClientLogic(form.GetWebBrowserClass());
        }

        /// <summary>
        /// Clear the testing environment.
        /// </summary>
        public void TearDown()
        {
            ConfigManager cm = ConfigManager.GetInstance();
            cm.Clear(false);

            try
            {
                Registry.CurrentUser.DeleteSubKeyTree("Software\\Orpheus");
            }
            catch (Exception)
            {
                // ignore
            }
        }

        /// <summary>
        /// Stress test for the handler <see cref="HttpRequestUserInterfaceEventHandler"/>.
        /// </summary>
        [Test]
        public void TestHttpRequestUserInterfaceEventHandler()
        {
            HttpRequestUserInterfaceEventHandler handler = new HttpRequestUserInterfaceEventHandler();
            ExtensionEventArgs args = new ExtensionEventArgs("HttpRequestUserInterface", context);

            DateTime start = DateTime.Now;

            for (int i = 0; i < 10; ++i)
            {
                handler.HandleEvent(this, args);
            }

            TimeSpan time = DateTime.Now - start;
            Console.WriteLine("Running HttpRequestUserInterfaceEventHandler.HandlerEvent for 10 times takes " +
                              time.TotalMilliseconds + "ms.");
        }

        /// <summary>
        /// Stress test for the handler <see cref="PollingEventHandler"/>.
        /// </summary>
        [Test]
        public void TestPollingEventHandler()
        {
            PollingEventHandler handler = new PollingEventHandler();
            ExtensionEventArgs args = new ExtensionEventArgs("Polling", context);

            DateTime start = DateTime.Now;

            for (int i = 0; i < 10; ++i)
            {
                handler.HandleEvent(this, args);
            }

            TimeSpan time = DateTime.Now - start;
            Console.WriteLine("Running PollingEventHandler.HandlerEvent for 10 times takes " + time.TotalMilliseconds +
                              "ms.");
        }

        /// <summary>
        /// Stress test for the handler <see cref="WebBrowserDocumentCompletedEventHandler"/>.
        /// </summary>
        [Test]
        public void TestWebBrowserDocumentCompletedEventHandler()
        {
            WebBrowserDocumentCompletedEventHandler handler = new WebBrowserDocumentCompletedEventHandler();
            form.Navigate("www.topcoder.com");
            context.BloomFilter.Add("www.topcoder.com");

            ExtensionEventArgs args = new ExtensionEventArgs("DocumentCompleted", context);

            DateTime start = DateTime.Now;

            for (int i = 0; i < 10; ++i)
            {
                handler.HandleEvent(this, args);
            }

            TimeSpan time = DateTime.Now - start;
            Console.WriteLine("Running WebBrowserDocumentCompletedEventHandler.HandlerEvent for 10 times takes " +
                              time.TotalMilliseconds + "ms.");
        }

        /// <summary>
        /// Stress test for the handler <see cref="TestObjectEventHandler"/>.
        /// </summary>
        [Test]
        public void TestTestObjectEventHandler()
        {
            TestObjectEventHandler handler = new TestObjectEventHandler();

            IHTMLElement element = (form.GetWebBrowserClass().Document as IHTMLDocument2).createElement("B");
            element.innerText = "12 34 Ab cD";
            ExtensionEventArgs args = new ExtensionEventArgs("TestObject", context, new object[] {element});
            args.Context.Persistence["hash"] = "ef73781effc5774100f87fe2f437a435";

            DateTime start = DateTime.Now;

            for (int i = 0; i < 10; ++i)
            {
                handler.HandleEvent(this, args);
            }

            TimeSpan time = DateTime.Now - start;
            Console.WriteLine("Running ObjectEventHandler.HandlerEvent for 10 times takes " + time.TotalMilliseconds +
                              "ms.");
        }
    }
}

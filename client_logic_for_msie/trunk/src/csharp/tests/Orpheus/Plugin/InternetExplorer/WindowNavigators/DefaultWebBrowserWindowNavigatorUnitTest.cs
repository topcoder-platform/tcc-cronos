/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultWebBrowserWindowNavigatorUnitTest.cs
 */
using System;
using System.IO;
using System.Text;

using NUnit.Framework;

using MsHtmHstInterop;
using SHDocVw;
using Orpheus.Plugin.InternetExplorer.Mock;

namespace Orpheus.Plugin.InternetExplorer.WindowNavigators
{
    /// <summary>
    /// Unit test for <c>DefaultWebBrowserWindowNavigator</c> class.
    /// DefaultWebBrowserWindowNavigator is used with IE,
    /// here only test constructor and failure case.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class DefaultWebBrowserWindowNavigatorUnitTest
    {
        /// <summary>
        /// An instance of <c>DefaultWebBrowserWindowNavigator</c> to perform test on.
        /// </summary>
        private DefaultWebBrowserWindowNavigator tester;

        /// <summary>
        /// The web browser used in <c>Navigate</c>.
        /// </summary>
        private WebBrowserClass webBrowser;

        /// <summary>
        /// The content stream used in <c>Navigate</c>.
        /// </summary>
        private Stream content;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            tester = new DefaultWebBrowserWindowNavigator();
            webBrowser = new WebBrowserClass();
            content = new MemoryStream();
        }

        /// <summary>
        /// Tear Down for each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            content.Close();
        }

        /// <summary>
        /// Test inheritance,
        /// DefaultWebBrowserWindowNavigator should implement IWebBrowserWindowNavigator
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsTrue(tester is IWebBrowserWindowNavigator,
                "DefaultWebBrowserWindowNavigator should implement IWebBrowserWindowNavigator");
        }

        /// <summary>
        /// Test constructor DefaultWebBrowserWindowNavigator(),
        /// Empty constructor, should be created always.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(tester,
                "Empty constructor, should be created always.");
            Assert.IsNull(TestHelper.GetFieldValue(tester, "popupWindow"),
                "popupWindow should be initialized to null");
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, string url, bool newWindow),
        /// when webBrowser is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestNavigate1_WebBrowserIsNull()
        {
            tester.Navigate(null, TestHelper.URL, true);
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, string url, bool newWindow),
        /// when url is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestNavigate1_UrlIsNull()
        {
            tester.Navigate(webBrowser, (string) null, true);
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, string url, bool newWindow),
        /// when url is empty string, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestNavigate1_UrlIsEmpty()
        {
            tester.Navigate(webBrowser, " \r \t \n ", true);
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, string url, bool newWindow),
        /// since the webBrowser haven't context, WebBrowserNavigationException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(WebBrowserNavigationException))]
        public void TestNavigate1_Failed()
        {
            tester.Navigate(webBrowser, TestHelper.URL, true);
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, string url, bool newWindow),
        /// a browser window with the given url should be show.
        /// </summary>
        public void TestNavigate1()
        {
            BrowserForm form = new BrowserForm();

            tester.Navigate(form.GetWebBrowserClass(), TestHelper.URL, true);
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow),
        /// when webBrowser is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestNavigate2_WebBrowserIsNull()
        {
            tester.Navigate(null, content, true);
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow),
        /// when content is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestNavigate2_ContentIsNull()
        {
            tester.Navigate(webBrowser, (Stream) null, true);
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow),
        /// since the webBrowser haven't context, WebBrowserNavigationException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(WebBrowserNavigationException))]
        public void TestNavigate2_Failed()
        {
            tester.Navigate(webBrowser, content, true);
        }

        /// <summary>
        /// Test Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow),
        /// a browser window with the given content should be showed.
        /// </summary>
        public void TestNavigate2()
        {
            BrowserForm form = new BrowserForm();
            using (Stream stream = new MemoryStream())
            {
                byte[] buffer = ASCIIEncoding.ASCII.GetBytes("<html><body>Demo</body></html>");
                stream.Write(buffer, 0, buffer.Length);
                stream.Seek(0, SeekOrigin.Begin);
                tester.Navigate(form.GetWebBrowserClass(), stream, true);
            }
        }
    }
}

using System;
using System.IO;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.WindowNavigators;
using SHDocVw;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for DefaultWebBrowserWindowNavigator class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestDefaultWebBrowserWindowNavigator
    {
        /// <summary>
        /// The DefaultWebBrowserWindowNavigator instance used to test.
        /// </summary>
        private DefaultWebBrowserWindowNavigator browser = new DefaultWebBrowserWindowNavigator();

        /// <summary>
        /// The WebBrowserClass instance used to test.
        /// </summary>
        private WebBrowserClass wbc = new WebBrowserClass();

        /// <summary>
        /// The content stream used in <c>Navigate</c>.
        /// </summary>
        private Stream stream = new MemoryStream();

        /// <summary>
        /// Tests <c>Navigate(WebBrowserClass webBrowser, string url, bool newWindow)</c> method with
        /// null WebBrowserClass webBrowser
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestNavigate_NullWebBrowser()
        {
            browser.Navigate(null, "url", false);
        }
        /// <summary>
        /// Tests <c>Navigate(WebBrowserClass webBrowser, string url, bool newWindow)</c> method with null string url
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestNavigate_NullUrl()
        {
            browser.Navigate(wbc, (string) null, false);
        }
        /// <summary>
        /// Tests <c>Navigate(WebBrowserClass webBrowser, string url, bool newWindow)</c> method with empty string url
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestNavigate_EmptyUrl()
        {
            browser.Navigate(wbc, " ", false);
        }

        /// <summary>
        /// Tests <c>Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow)</c> method with
        /// null WebBrowserClass webBrowser
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestNavigate1_NullWebBrowser()
        {
            browser.Navigate(null, stream, false);
        }
        /// <summary>
        /// Tests <c>Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow)</c> method
        /// with null Stream content
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestNavigate_NullContent()
        {
            browser.Navigate(wbc, (Stream) null, false);
        }
    }
}

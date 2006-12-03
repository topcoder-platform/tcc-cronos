/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * MockDefaultWebBrowserWindowNavigator.cs
 */
using System;
using System.IO;
using Orpheus.Plugin.InternetExplorer.WindowNavigators;
using MsHtmHstInterop;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.Mock
{
    /// <summary>
    /// A mock class for IWebBrowserWindowNavigator, used in test handlers.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public sealed class MockDefaultWebBrowserWindowNavigator : IWebBrowserWindowNavigator
    {
        /// <summary>
        /// Record the webBrowser.
        /// </summary>
        private WebBrowserClass webBrowser;

        /// <summary>
        /// Record the url.
        /// </summary>
        private string url;

        /// <summary>
        /// Record the new window.
        /// </summary>
        private bool newWindow;

        /// <summary>
        /// Record the content.
        /// </summary>
        private string content;

        /// <summary>
        /// The webBrowser passed in Navigate.
        /// </summary>
        ///
        /// /// <value>the webBrowser</value>
        public WebBrowserClass WebBrowser
        {
            get
            {
                return webBrowser;
            }
        }

        /// <summary>
        /// The url passed in Navigate.
        /// </summary>
        ///
        /// <value>the url</value>
        public string Url
        {
            get
            {
                return url;
            }
        }

        /// <summary>
        /// The stream's content passed in Navigate.
        /// </summary>
        ///
        /// <value>The stream's content.</value>
        public string Content
        {
            get
            {
                return content;
            }
        }

        /// <summary>
        /// The new window passed in Navigate.
        /// </summary>
        ///
        /// <value>The new window.</value>
        public bool NewWindow
        {
            get
            {
                return newWindow;
            }
        }


        /// <summary>
        /// Record the argument for test only.
        /// </summary>
        ///
        /// <param name="webBrowser">The web browser to disaplay the url in,
        /// or to open the window from.</param>
        /// <param name="url">The URL to navigate to .</param>
        /// <param name="newWindow">Indoicates whether a new window should be opened.</param>
        public void Navigate(WebBrowserClass webBrowser, string url, bool newWindow)
        {
            this.webBrowser = webBrowser;
            this.url = url;
            this.newWindow = newWindow;
        }


        /// <summary>
        /// Record the argument for test only.
        /// </summary>
        ///
        /// <param name="webBrowser">The web browser to disaplay the url in,
        /// or to open the window from.</param>
        /// <param name="content"> The content to set to the browser.</param>
        /// <param name="newWindow">Indoicates whether a new window should be opened.</param>
        public void Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow)
        {
            this.webBrowser = webBrowser;
            this.content = new StreamReader(content).ReadToEnd();
            this.newWindow = newWindow;
        }
    }
}

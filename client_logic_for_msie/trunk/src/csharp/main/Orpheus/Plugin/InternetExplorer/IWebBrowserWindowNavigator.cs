/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * IWebBrowserWindowNavigator.cs
 */

using System;
using System.IO;
using MsHtmHstInterop;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This interface defines the contract that is required for any browser window navigator
    /// to implement. A browser window navigator is responsible for displaying a new page to
    /// the user either inside the browser or in a new window. <br />
    /// This component provides an implementation of this interface that reuses an opened
    /// popup window to display the web pages.
    /// <br />
    ///
    /// <strong>Thread safety</strong>: Implementations of this interface are expected to
    /// be thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public interface IWebBrowserWindowNavigator
    {
        /// <summary>
        /// This is the declaration of the window manger method which based on
        /// the URL should display the page inside either the opened browser
        /// window or inside a new one.
        /// </summary>
        ///
        /// <param name="webBrowser">The web browser of the extension.</param>
        /// <param name="url">The URL location to set to the browser.</param>
        /// <param name="newWindow">Indicates whether a new window should be opened.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        /// <exception cref="WebBrowserNavigationException">to signal any problems
        /// while navigating to the new location.</exception>
        void Navigate(WebBrowserClass webBrowser, string url, bool newWindow);

        /// <summary>
        /// This is the declaration of the window manager method which based on the given
        /// stream should set the content either to the opened browser window
        /// or inside a new one.
        /// </summary>
        ///
        /// <param name="webBrowser">The web browser of the extension</param>
        /// <param name="content">The content stream to set to the browser.</param>
        /// <param name="newWindow">Indicates whether a new window should be opened.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="WebBrowserNavigationException">to signal any problems
        /// while navigating to the new location.</exception>
        ///
        void Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow);
    }
}

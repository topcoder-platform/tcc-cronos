/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultWebBrowserWindowNavigator.cs
 */
using System;
using System.Text;
using System.IO;

using MsHtmHstInterop;
using Mshtml;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.WindowNavigators
{
    /// <summary>
    /// This class is an implementation of the <c>IWebBrowserWindowNavigator</c> interface. <br />
    ///
    /// It will be responsible for displaying the requested page or the provided content inside
    /// either the main browser window or inside a opened and reused window. <br />
    ///
    /// The new opened window will have the same customization object set to it as the main
    /// browser window. When the new window is opened, the Internet Explorer will load the
    /// extension object again. The extension object, if used as depicted in the component
    /// specifications, will get the same instance of the <c>MsieClientLogic</c> by using
    /// the singleton of the class, and will call the <c>CustomizeWebBrowser</c> method
    /// which will set the same customization object to the newly opened browser. <br />
    ///
    /// <strong>Thread safety:</strong> This class locks on this inside the property getter
    /// and setter and inside the <c>Navigate</c> methods.
    /// </summary>
    ///
    /// <author>kr00tki</author>
    /// <version>1.0.1</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class DefaultWebBrowserWindowNavigator : IWebBrowserWindowNavigator
    {
        /// <summary>
        /// Represents the newlly created window. This new window is created using the DOM
        /// of the browser inside the <c>Navigate</c> method. <br />
        ///
        /// It can be null if no new window is displayed.
        /// Once a new one is displayed it will point to this one.
        /// </summary>
        //private IHTMLWindow2 popupWindow = null;
        private SHDocVw.InternetExplorer popupWindow = null;

        /// <summary>
        /// Synchronization object used when access is made to popupWindow field.
        /// </summary>
        private object newWindowLock = new object();

        /// <summary>
        /// This flag indicates if the Javascript should be able to close browser window without
        /// propmting user. Default value to false.
        /// </summary>
        private bool allowJSCloseWindow = false;

        /// <summary>
        /// Empty constructor.
        /// </summary>
        public DefaultWebBrowserWindowNavigator()
        {
        }

        /// <summary>
        /// Constructor. It sets the given parameter to instance field.
        /// </summary>
        /// <param name="allowClose">If true, then Javascript call to window.close() will not prompt
        /// user for confirmation</param>
        public DefaultWebBrowserWindowNavigator(bool allowClose)
        {
            this.allowJSCloseWindow = allowClose;
        }

        /// <summary>
        /// This method directs the browser or the new window to the specified URL.
        /// </summary>
        ///
        /// <param name="webBrowser">The web browser to disaplay the url in,
        /// or to open the window from.</param>
        /// <param name="url">The URL to be navigate to.</param>
        /// <param name="newWindow">Indicates whether a new window should be opened.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        /// <exception cref="WebBrowserNavigationException">to signal problems
        /// while navigating to the new location.</exception>
        public void Navigate(WebBrowserClass webBrowser, string url, bool newWindow)
        {
            Validator.ValidateNull(webBrowser, "webBrowser");
            Validator.ValidateNullOrEmptyString(url, "url");

            object address = url;
            try
            {
                if (newWindow)
                {
                    lock (newWindowLock)
                    {
                        if (popupWindow == null)
                        {
                            // create new IE COM object
                            popupWindow = new InternetExplorerClass();
                            // hide the bars
                            popupWindow.AddressBar = false;
                            popupWindow.MenuBar = false;
                            popupWindow.StatusBar = false;
                            // hide the nagivation and others toolbars - unfortunately it will hide also our bar
                            popupWindow.ToolBar = 0;

                            // add the quit delegate - it's used for releasing our popup window on close
                            popupWindow.OnQuit += new DWebBrowserEvents2_OnQuitEventHandler(ReleasePopupWindow);

                            if (allowJSCloseWindow)
                            {
                                popupWindow.WindowClosing += new DWebBrowserEvents2_WindowClosingEventHandler(
                                    WindowClosingHandler);
                            }
                        }

                        // navigate to requested location
                        object nullObj = null;
                        popupWindow.Navigate2(ref address, ref nullObj, ref nullObj, ref nullObj, ref nullObj);
                        // show window
                        popupWindow.Visible = true;
                    }
                }
                else
                {
                    // navigate to requested location
                    object nullObj = null;
                    webBrowser.Navigate2(ref address, ref nullObj, ref nullObj, ref nullObj, ref nullObj);
                }
            }
            catch (Exception e)
            {
                throw new WebBrowserNavigationException(
                    string.Format("Failed to navigate to the url {0} in webbrowser." + e, url), e);
            }
        }

        /// <summary>
        /// The handler for the WindowClosing event. This event occurs when the Javascript's
        /// <code>window.close()</code> method is invoked. By default, Internet Explorer will prompt user
        /// if (s)he aggree to close the window. With this 'hack', there will be no such prompt.
        /// </summary>
        /// <param name="IsChildWindow">flag indicating if the window is chile window</param>
        /// <param name="Cancel">indicates if the window closing event should be cancelled or not.</param>
        private void WindowClosingHandler(bool IsChildWindow, ref bool Cancel)
        {
            lock (newWindowLock)
            {
                Cancel = true;
                popupWindow.Quit();
            }
        }

        /// <summary>
        /// A handler for the window's Quit event. It set the internal popup variable to null since this
        /// window cannot be used again, as the COM server behind it was already released.
        /// </summary>
        private void ReleasePopupWindow()
        {
            lock (newWindowLock)
            {
                popupWindow = null;
            }
        }

        /// <summary>
        /// This method displays the content in the browser browser or the new window.
        /// </summary>
        ///
        /// <param name="webBrowser">The web browser to disaplay the url in,
        /// or to open the window from.</param>
        /// <param name="content"> The content to set to the browser.</param>
        /// <param name="newWindow">Indoicates whether a new window should be opened.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="WebBrowserNavigationException">to signal problems
        /// while navigating to the new location.</exception>
        public void Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow)
        {
            Validator.ValidateNull(webBrowser, "webBrowser");
            Validator.ValidateNull(content, "content");
            // Read the content into stream.
            // we don't close the stream here.
            string output = null;
            try
            {
                using (TextReader reader = new StreamReader(content, Encoding.Default))
                {
                    output = reader.ReadToEnd();
                }
            }
            catch (Exception e)
            {
                throw new WebBrowserNavigationException(
                    "Failed to read content from the input stream.", e);
            }

            try
            {
                if (newWindow)
                {
                    lock (newWindowLock)
                    {
                        if (popupWindow == null)
                        {
                            // create new IE COM object
                            popupWindow = new InternetExplorerClass();
                            // hide the bars
                            popupWindow.AddressBar = false;
                            popupWindow.MenuBar = false;
                            popupWindow.StatusBar = false;
                            // hide the nagivation and others toolbars - unfortunately it will hide also our bar
                            popupWindow.ToolBar = 0;

                            // add the quit delegat - it's used for realing our popup window on close
                            popupWindow.OnQuit += new DWebBrowserEvents2_OnQuitEventHandler(ReleasePopupWindow);
                            ContentLoader loader = new ContentLoader(popupWindow, output);
                            DWebBrowserEvents2_DocumentCompleteEventHandler docHandler =
                                new DWebBrowserEvents2_DocumentCompleteEventHandler(loader.LoadWindowContent);
                            loader.Handler = docHandler;
                            popupWindow.DocumentComplete += docHandler;

                            // navigate to requested location
                            object nullObj = null;
                            object address = "about:blank";
                            popupWindow.Navigate2(ref address, ref nullObj, ref nullObj, ref nullObj, ref nullObj);
                        }
                        else
                        {
                            WriteDocumentContent(popupWindow, output);
                            popupWindow.Visible = true;
                        }
                    }
                   
                }
                else
                {
                    WriteDocumentContent(webBrowser, output);
                }
            }
            catch (Exception e)
            {
                throw new WebBrowserNavigationException("Failed to Display the content in webbrowser.", e);
            }
        }

        /// <summary>
        /// A helper method that writes the content to the given browser's document.
        /// </summary>
        /// <param name="browser">the browser window.</param>
        /// <param name="content">the new document content.</param>
        private static void WriteDocumentContent(IWebBrowser2 browser, string content)
        {
            IHTMLDocument2 doc = browser.Document as IHTMLDocument2;
            // clear the content
            doc.clear();
            // write new
            doc.write(content);
            // close document as flush
            doc.close();
        }

        /// <summary>
        /// This class is responsible for loading new document content to newly opened window.
        /// Because this process is asynchronous, this class is need to be used in OnDocumentComplete event.
        /// </summary>
        /// <author>kr00tki</author>
        /// <version>1.0.1</version>
        /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
        private class ContentLoader
        {
            /// <summary>
            /// The instance of nternet Explorer window to which the new content will be loaded.
            /// </summary>
            private SHDocVw.InternetExplorer ie;

            /// <summary>
            /// The content of the document.
            /// </summary>
            private string content;

            /// <summary>
            /// The handler to be unregistred from the window after successful operation.
            /// </summary>
            private DWebBrowserEvents2_DocumentCompleteEventHandler handler;

            /// <summary>
            /// A setter for the DocumentCompleted event to be unregistred from window.
            /// </summary>
            public DWebBrowserEvents2_DocumentCompleteEventHandler Handler
            {
                set
                {
                    handler = value;
                }
            }

            /// <summary>
            /// Creates new ContentLoader instance. It set the given parameters to internal fields.
            /// </summary>
            /// <param name="ie">the IE window.</param>
            /// <param name="popupContent">The popup window content.</param>
            public ContentLoader(SHDocVw.InternetExplorer ie, string popupContent)
            {
                this.ie = ie;
                this.content = popupContent;
            }

            /// <summary>
            /// This method handles the DocumentCompleted event. It simply loads the
            /// new popup window content.
            /// </summary>
            /// <param name="pDisp">not used</param>
            /// <param name="URL">not used</param>
            public void LoadWindowContent(object pDisp, ref object URL)
            {
                WriteDocumentContent(ie, content);
                ie.Visible = true;
                // unregister event
                ie.DocumentComplete -= handler;
            }
        }
    }
}

/*
 * Copyright (C) 2006, 2007 TopCoder Inc., All Rights Reserved.
 *
 * DefaultWebBrowserWindowNavigator.cs
 */
using System;
using System.Text;
using System.IO;

using MsHtmHstInterop;
using Mshtml;
using SHDocVw;
using TopCoder.Util.ConfigurationManager;

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
    /// <version>1.0.2</version>
    /// <copyright>Copyright (C) 2006, 2007 TopCoder Inc., All Rights Reserved.</copyright>
    public class DefaultWebBrowserWindowNavigator : IWebBrowserWindowNavigator
    {
        /// <summary>
        /// Represents the newlly created window. This new window is created using the DOM
        /// of the browser inside the <c>Navigate</c> method. <br />
        ///
        /// It can be null if no new window is displayed.
        /// Once a new one is displayed it will point to this one.
        /// </summary>
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
        /// The popup window height.
        /// </summary>
        private int windowHeight = 100;

        /// <summary>
        /// The popup window width.
        /// </summary>
        private int windowWidth = 100;

        /// <summary>
        /// This flag indicates if address bar should be displayed in popup window.
        /// </summary>
        private bool addressBarEnabled = false;

        /// <summary>
        /// This flag indicates if manu bar should be displayed in popup window.
        /// </summary>
        private bool menuBarEnabled = false;

        /// <summary>
        /// This flag indicates if status bar should be displayed in popup window.
        /// </summary>
        private bool statusBarEnabled = false;

        /// <summary>
        /// This value indicates if toolabe bar should be displayed in popup window. If value is 0, then the
        /// toolbars won't be displayed.
        /// </summary>
        private int toolbarEnabled = 0;

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
        /// This constructor loads configuration paramteres and sets them to internal fields.
        /// <list type="bullet">
        /// <item>window_width - the popup window width</item>
        /// <item>window_height - the popup window height</item>
        /// <item>allow_window_close - indicates if popup window can be closed from JS</item>
        /// <item>addressbar - indicates if address bar should be displayed.</item>
        /// <item>menubar - indicates if menu bar should be displayed.</item>
        /// <item>statusbar - indicates if status bar should be displayed.</item>
        /// <item>toolbar - indicates if toolbar should be displayed.</item>
        /// </list>
        /// </summary>
        /// <param name="confNamespace">the configuration namespace.</param>
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if any parameter is empty string.</exception>
        public DefaultWebBrowserWindowNavigator(String confNamespace)
        {
            Validator.ValidateNullOrEmptyString(confNamespace, "confNamespace");

            ConfigManager cm = ConfigManager.GetInstance();
            windowWidth = cm.GetIntValue(confNamespace, "window_width");
            windowHeight = cm.GetIntValue(confNamespace, "window_height");
            allowJSCloseWindow = bool.Parse(cm.GetValue(confNamespace, "allow_window_close"));
            addressBarEnabled = bool.Parse(cm.GetValue(confNamespace, "addressbar"));
            menuBarEnabled = bool.Parse(cm.GetValue(confNamespace, "menubar"));
            statusBarEnabled = bool.Parse(cm.GetValue(confNamespace, "statusbar"));
            toolbarEnabled = cm.GetIntValue(confNamespace, "toolbar");
        }

        /// <summary>
        /// Creates new browser window using configured properties.
        /// </summary>
        /// <returns>new browser window.</returns>
        private SHDocVw.InternetExplorer CreateWindow()
        {
            // create new IE COM object
            SHDocVw.InternetExplorer popupWindow = new InternetExplorerClass();
            // hide the bars
            popupWindow.AddressBar = addressBarEnabled;
            popupWindow.MenuBar = menuBarEnabled;
            popupWindow.StatusBar = statusBarEnabled;
            // hide the nagivation and others toolbars - unfortunately it will hide also our bar
            popupWindow.ToolBar = toolbarEnabled;

            popupWindow.Width = windowWidth;
            popupWindow.Height = windowHeight;

            // add the quit delegate - it's used for releasing our popup window on close
            popupWindow.OnQuit += new DWebBrowserEvents2_OnQuitEventHandler(ReleasePopupWindow);

            if (allowJSCloseWindow)
            {
                popupWindow.WindowClosing += new DWebBrowserEvents2_WindowClosingEventHandler(
                    WindowClosingHandler);
            }
            popupWindow.PutProperty("nnaame", "Popup");
            return popupWindow;
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
            Validator.ValidateNullOrEmptyString(url, "url");
            object address = url;
            try
            {
                if (newWindow || (webBrowser == null))
                {
                    lock (newWindowLock)
                    {
                        if (popupWindow == null)
                        {
                            popupWindow = CreateWindow();
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
                throw new WebBrowserNavigationException("Failed to read content from the input stream.", e);
            }

            try
            {
                if (newWindow || (webBrowser == null))
                {
                    lock (newWindowLock)
                    {
                        if (popupWindow == null)
                        {
                            popupWindow = CreateWindow();

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

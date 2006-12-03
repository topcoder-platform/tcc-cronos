/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * HttpRequestUserInterfaceEventHandler.cs
 */

using System;
using Microsoft.Win32;
using TopCoder.Util.ConfigurationManager;
using System.Net;
using System.IO;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// This class is an implementation of the <c>IExtensionEventHandler</c> interface. <br />
    ///
    /// This event handler should be configured in the configuration file for user interface
    /// generated events, such as login button pressed. Most of these events require a HTTP
    /// request to be made to a URL and the result displayed in either the main browser window
    /// or a popup window.<br />
    ///
    /// This class constructs the URL of the page to request and then directs the browser
    /// navigator to the specified URL.
    /// <br />
    ///
    /// A sample config file: <br />
    /// &lt;property name="http_url"&gt; <br />
    /// &lt;value&gt;http://localhost/msie/test.html&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="http_new_window"&gt; <br />
    /// &lt;value&gt;true&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="http_method"&gt; <br />
    /// &lt;value&gt;Get&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    ///
    /// <strong>Thread safety</strong>: This class has no mutable state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class HttpRequestUserInterfaceEventHandler : IExtensionEventHandler
    {
        /// <summary>
        /// Default configuration namespace.
        /// </summary>
        public const string DefaultConfigurationNamespace =
            "Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers";

        /// <summary>
        /// The property key used in this handler to get event url,
        /// it should like the following format &lt;event name&gt;_url,
        /// where the event name is the detailed for the event handler.
        /// </summary>
        private const string FORAMT_URL = "{0}_url";

        /// <summary>
        /// The property key used in this handler to indicates new window or not,
        /// it should like the following format &lt;event name&gt;_new_window,
        /// where the event name is the detailed for the event handler.
        /// </summary>
        private const string FORAMT_NEW_WINDOW = "{0}_new_window";

        /// <summary>
        /// The property key used in this handler to indicates the http request method.
        /// it should like the following format &lt;event name&gt;_new_window,
        /// where the event name is the detailed for the event handler.
        /// </summary>
        private const string FORAMT_METHOD = "{0}_method";

        /// <summary>
        /// The default http request method.
        /// </summary>
        private const string DEFAULT_METHOD = "Get";

        /// <summary>
        /// Represents the configuration namespace to use.
        /// Set in the constructor and not changed afterwards. Can not be null or empty.
        /// </summary>
        private readonly string configurationNamespace;

        /// <summary>
        /// Construct with the default namespace.
        /// </summary>
        public HttpRequestUserInterfaceEventHandler() : this(DefaultConfigurationNamespace)
        {
        }

        /// <summary>
        ///  Construct with the given namespace.
        /// </summary>
        ///
        /// <param name="configurationNamespace">Custom configuration namespace.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string</exception>
        public HttpRequestUserInterfaceEventHandler(string configurationNamespace)
        {
            Validator.ValidateNullOrEmptyString(configurationNamespace, "configurationNamespace");

            this.configurationNamespace = configurationNamespace;
        }

        /// <summary>
        /// Retrieves from the configuration file and based on the event name the URL
        /// of the page to request, whether the page should be displayed in a new window,
        /// and the method to use for the request and display the result.
        /// </summary>
        ///
        /// <param name="sender">Sender object.</param>
        /// <param name="args">Extension event arguments.</param>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="HandleEventException">to signal problems in handling the event.</exception>
        public void HandleEvent(object sender, ExtensionEventArgs args)
        {
            Validator.ValidateNull(sender, "sender");
            Validator.ValidateNull(args, "args");
            Stream stream = null;
            try
            {
                // get config manager
                ConfigManager cm = ConfigManager.GetInstance();

                // Reads from the configuration file and based on the event name
                // the URL of the page to request (the <event name>_url property).
                string url = cm.GetValue(configurationNamespace,
                    string.Format(FORAMT_URL, args.EventName));

                // Reads from the configuration file and based on the event name
                // whether the page should be displayed in a new window
                // (the <event name>_new_window property).
                string newWindow = cm.GetValue(configurationNamespace,
                    string.Format(FORAMT_NEW_WINDOW, args.EventName));

                // Reads from the configuration file and based on the event name the method
                // to use for the request (the <event name>_method property).
                string method = cm.GetValue(configurationNamespace,
                    string.Format(FORAMT_METHOD, args.EventName));

                // Creates a new web request (WebRequest.Create) for the configured URL.
                WebRequest request = WebRequest.Create(url);

                // Sets the method to use to the request to the configured value.
                request.Method = method == null ? DEFAULT_METHOD : method;

                // Gets the response stream of the request.
                stream = request.GetResponse().GetResponseStream();

                // then uses the web browser window navigator
                // from the context object to set the content to the browser.
                args.Context.WebBrowserWindowNavigator.Navigate(args.Context.WebBrowser, stream,
                    newWindow == null ? false : Boolean.Parse(newWindow));
            }
            catch (Exception e)
            {
                throw new HandleEventException("Failed to handler the event.", e);
            }
            finally
            {
                // close the stream
                if (null != stream)
                {
                    stream.Close();
                }
            }
        }
    }
}

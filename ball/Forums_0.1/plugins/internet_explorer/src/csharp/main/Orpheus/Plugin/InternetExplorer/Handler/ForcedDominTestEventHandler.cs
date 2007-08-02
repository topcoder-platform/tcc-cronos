using System;
using TopCoder.Util.ConfigurationManager;
using Orpheus.Plugin.InternetExplorer.EventsManagers;

namespace Orpheus.Plugin.InternetExplorer.Handler
{
    /// <summary>
    /// This class is an implementation of the <c>IExtensionEventHandler</c> interface.<br />
    ///
    /// It takes the URL from the event attributes and redirects the browser to requested url.
    ///
    /// A sample config file: <br />
    /// &lt;property name="document_completed_0_responce_url"&gt;<br />
    /// &lt;value&gt;www.topcoder.com/default.aspx?host={0}&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    ///
    /// <strong>Thread safety</strong>: This class has no state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <author>kr00tki</author>
    /// <version>1.0.2</version>
    /// <copyright>Copyright (C) 2006, 2007 TopCoder Inc., All Rights Reserved.</copyright>
    public class ForcedDominTestEventHandler : IExtensionEventHandler
    {
        /// <summary>
        /// Default configuration namespace.
        /// </summary>
        public const string DefaultConfigurationNamespace =
            "Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers";

        /// <summary>
        /// The document completed response url property key name.
        /// </summary>
        private const string PROPERTY_DOCUMENT_COMPLETED_RESPONSE_URL = "document_completed_0_responce_url";

        /// <summary>
        /// Represents the configuration namespace to use.
        /// Set in the constructor and not changed afterwards. Can not be null or empty.
        /// </summary>
        private readonly string testDomainURL;

        /// <summary>
        /// Construct with the default namespace.
        /// </summary>
        public ForcedDominTestEventHandler() : this(DefaultConfigurationNamespace)
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
        public ForcedDominTestEventHandler(string configurationNamespace)
        {
            // get config manager
            ConfigManager cm = ConfigManager.GetInstance();
            testDomainURL = cm.GetValue(configurationNamespace, PROPERTY_DOCUMENT_COMPLETED_RESPONSE_URL);
        }

        /// <summary>
        /// Handler method for the TestDomain event. It takes the domain from event argument,
        /// creates the redirection URL and redirects the browser to it.
        /// </summary>
        ///
        /// <param name="sender">Sender object.</param>
        /// <param name="args">Extension event arguments.</param>
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        public void HandleEvent(object sender, ExtensionEventArgs args)
        {
            try
            {
                if (args.Parameters[0] != null) 
                {
                    // get the url of the current page
                    Uri uri = new Uri(args.Parameters[0] as string);
                    string host = uri.Host;
                    string url = string.Format(testDomainURL, host);
                    args.Context.WebBrowserWindowNavigator.Navigate(args.Context.WebBrowser, url, true);
                }
            }
            catch (Exception e)
            {
                ErrorHandler.Instance.ReportError(new HandleEventException(string.Format(
                    "Failed to handler the event: {0}", args.EventName), e));
            }
        }
    }
}

using System;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;

namespace Orpheus.Plugin.InternetExplorer.Handler
{
    /// <summary>
    /// This class is wrapper for <c>PollingEventHandler</c> class.
    /// It redirects calls to HandleEvent method only if the user is logged in to the server.
    /// </summary>
    /// <author>kr00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class CustomPollingEventHandler : IExtensionEventHandler
	{
        /// <summary>
        /// The PollingEventHandler used to handle the polling event.
        /// </summary>
        private readonly PollingEventHandler handler = null;

        /// <summary>
        /// Default constructor.
        /// </summary>
        /// <exception cref="ConfigurationException">if can not read the property
        /// or can not create the object.</exception>
		public CustomPollingEventHandler()
		{
			handler = new PollingEventHandler();
		}

        /// <summary>
        /// Constructor.
        /// Creates using the Object Factory and a configured key the <c>IRSSParser</c> using
        /// the custom namespaces.
        /// </summary>
        ///
        /// <param name="configurationNamespace">Custom configuration namespace.</param>
        /// <param name="objectFactoryNamespace">Custom Object Factory namespace.</param>
        ///
        /// <exception cref="ConfigurationException">if can not read the property
        /// or can not create the object.</exception>
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if any parameter is empty string.</exception>
        public CustomPollingEventHandler(string configurationNamespace, string objectFactoryNamespace) 
        {
            handler = new PollingEventHandler(configurationNamespace, objectFactoryNamespace);
        }

        /// <summary>
        /// Event handler method. If the user is logged in, this method will get the content from "polling_url", 
        /// if the content is "application/x-tc-bloom-filter", it will update the BloomFilter,
        /// otherwise if it is html, text, xhtml, it will display it. 
        /// </summary>
        ///
        /// <param name="sender"> Sender object.</param>
        /// <param name="args">Extension event arguments.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="HandleEventException">to  to signal problems in
        /// handling the event.</exception>
        public void HandleEvent(object sender, ExtensionEventArgs args) 
        {
            if (OrpheusToolbar.IsLoggedIn) 
            {
                try 
                {
                    handler.HandleEvent(sender, args);
                } 
                catch (Exception ex) 
                {
                    ErrorHandler.Instance.ReportError(ex, "RSS polling error.");
                }
            }
        }
	}
}

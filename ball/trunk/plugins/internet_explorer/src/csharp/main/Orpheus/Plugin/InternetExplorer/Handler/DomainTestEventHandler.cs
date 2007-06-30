using System;

using Orpheus.Plugin.InternetExplorer.EventsManagers;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;

namespace Orpheus.Plugin.InternetExplorer.Handler
{
	/// <summary>
	/// This class is wrapper for <c>WebBrowserDocumentCompletedEventHandler</c> class.
	/// It redirects calls to HandleEvent method only if the user is logged in to the server.
	/// </summary>
    /// <author>kr00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
	public class DomainTestEventHandler : IExtensionEventHandler
	{
        /// <summary>
        /// WebBrowserDocumentCompletedEventHandler instance used to test new domain.
        /// </summary>
        private WebBrowserDocumentCompletedEventHandler handler = null;

        /// <summary>
        /// The last tested domain name. If the next domain to test will have the same name, the
        /// fail the test will be ignored.
        /// </summary>
        private string lastDomain = null;

        /// <summary>
        /// Default constructor.
        /// </summary>
		public DomainTestEventHandler()
		{
			handler = new WebBrowserDocumentCompletedEventHandler();
		}

        /// <summary>
        /// Creates the handler from the given configuration.
        /// </summary>
        /// <param name="configNamespace">the configuration namespace</param>
        /// <exception cref="ArgumentNullException">if can not read the property or can not create
        /// the object.</exception>
        /// <exception cref="ArgumentException">if any parameter is empty string.</exception>
        /// <exception cref="ConfigurationException">to signal any problems with the configuration.
        /// </exception>
        public DomainTestEventHandler(String configNamespace)
        {
            handler = new WebBrowserDocumentCompletedEventHandler(configNamespace);
        }

        /// <summary>
        /// Handler method for the DocumentCompleted event. If the user is logged in, it performs a request to the server, 
        /// if needed, and gets a numeric value based
        /// on which it will direct the browser to a new page.
        /// </summary>
        ///
        /// <param name="sender">Sender object.</param>
        /// <param name="args">Extension event arguments.</param>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="HandleEventException">to signal problems in handling the event.</exception>
        public void HandleEvent(object sender, ExtensionEventArgs args) 
        {
            if (OrpheusToolbar.IsLoggedIn) 
            {
                try 
                {
                    string host = new Uri(args.Parameters[0] as string).Host;
                    if ((host != lastDomain) && (host.Length > 0)) 
                    {
                        handler.HandleEvent(sender, args);
                        lastDomain = host;
                    }
                } 
                catch (Exception ex) 
                {
                    ErrorHandler.Instance.ReportError(ex, "Test domain action error.");
                }
            } 
            else 
            {
                lastDomain = null;
            }
        }

	}
}

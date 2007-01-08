/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * WebBrowserDocumentCompletedEventHandler.cs
 */

using System;
using Microsoft.Win32;
using TopCoder.Util.ConfigurationManager;
using System.Net;
using System.IO;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// This class is an implementation of the <c>IExtensionEventHandler</c> interface.<br />
    ///
    /// It performs a request to the server, if needed, and gets a numeric value based
    /// on which it will direct the browser to a new page.<br />
    ///
    /// A sample config file: <br />
    /// &lt;property name="document_completed_url"&gt;<br />
    /// &lt;value&gt;www.topcoder.com/default.aspx?host={0}&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    /// &lt;property name="document_completed_0_responce_url"&gt;<br />
    /// &lt;value&gt;www.topcoder.com/default.aspx?host={0}&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    ///
    /// <strong>Thread safety</strong>: This class has no state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class WebBrowserDocumentCompletedEventHandler : IExtensionEventHandler
    {
        /// <summary>
        /// Default configuration namespace.
        /// </summary>
        public const string DefaultConfigurationNamespace =
            "Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers";

        /// <summary>
        /// The document completed url property key name.
        /// </summary>
        private const string PROPERTY_DOCUMENT_COMPLETED_URL = "document_completed_url";

        /// <summary>
        /// The document completed response url property key name.
        /// </summary>
        private const string PROPERTY_DOCUMENT_COMPLETED_RESPONSE_URL = "document_completed_0_responce_url";

        /// <summary>
        /// Represents the configuration namespace to use.
        /// Set in the constructor and not changed afterwards. Can not be null or empty.
        /// </summary>
        private readonly string configurationNamespace;

        /// <summary>
        /// Construct with the default namespace.
        /// </summary>
        public WebBrowserDocumentCompletedEventHandler() : this(DefaultConfigurationNamespace)
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
        public WebBrowserDocumentCompletedEventHandler(string configurationNamespace)
        {
            Validator.ValidateNullOrEmptyString(configurationNamespace, "configurationNamespace");

            this.configurationNamespace = configurationNamespace;
        }

        /// <summary>
        /// Handler method for the DocumentCompleted event. It performs a request to the server,
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

            Validator.ValidateNull(sender, "sender");
            Validator.ValidateNull(args, "args");
            try
            {
                // get the url of the current page
                Uri uri = new Uri(args.Parameters[0] as string);
                string host = uri.Host;

                if (args.Context.BloomFilter.Contains(host))
                {
                    // if found by bloom filter

                    // get config manager
                    ConfigManager cm = ConfigManager.GetInstance();


                    // Read from the configuration file the configured URL(document_completed_url
                    // property), set the host parameter using string.Format.
                    string url = string.Format(cm.GetValue(configurationNamespace,
                        PROPERTY_DOCUMENT_COMPLETED_URL), host);

                    byte[] buffer = null;
                    using (Stream stream = Helper.GetDocumentContent(url))
                    {
                        using (Stream tmpStream = new MemoryStream())
                        {
                            BinaryReader reader = new BinaryReader(stream);
                            try
                            {
                                while (true)
                                {
                                    tmpStream.WriteByte(reader.ReadByte());
                                }
                            }
                            catch (EndOfStreamException)
                            {
                                // ok
                            }
                            buffer = new byte[tmpStream.Length];
                            tmpStream.Seek(0, SeekOrigin.Begin);
                            tmpStream.Read(buffer, 0, buffer.Length);
                        }
                    }
                    int number = 0;
                    for (int i = 0; i < buffer.Length; i++)
                    {
                        number = (number << 8) | buffer[i];
                    }

                    if (number != 0)
                    {
                        // the number is not 0
                        // Read from the configuration file the configured
                        // URL(document_completed_0_responce_url property), set the host parameter
                        url = string.Format(cm.GetValue(configurationNamespace,
                            PROPERTY_DOCUMENT_COMPLETED_RESPONSE_URL), host);

                        // direct the web browser window navigator to the new location
                        //to open in a new window.
                        args.Context.WebBrowserWindowNavigator.Navigate(args.Context.WebBrowser, url, true);
                    }
                }
            }
            catch (Exception e)
            {
                throw new HandleEventException(string.Format(
                    "Failed to handler the event: {0}", args.EventName), e);
            }
        }
    }
}

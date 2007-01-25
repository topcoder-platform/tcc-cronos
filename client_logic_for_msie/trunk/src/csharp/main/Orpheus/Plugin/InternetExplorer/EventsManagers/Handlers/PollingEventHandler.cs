/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * PoolingEventHandler.cs
 */

using System;
using Microsoft.Win32;
using System.Net;
using System.Text;
using System.IO;
using TopCoder.Util.RSS;
using TopCoder.Util.RSS.IO;
using TopCoder.Util.RSS.Atom10Content;
using TopCoder.Util.RSS.Atom.IO;
using TopCoder.Util.BloomFilter;
using TopCoder.Util.ConfigurationManager;
using TopCoder.Util.ObjectFactory;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// This class is an implementation of the <c>IExtensionEventHandler</c>
    /// interface. <br />
    ///
    /// This class will get the content from "polling_url", if the content
    /// is "application/x-tc-bloom-filter", it will update the BloomFilter,
    /// otherwise if it is html, text, xhtml, it will display it. <br />
    ///
    /// This event handler will be invoked at regular intervals or through
    /// the scripting method. <br />
    ///
    /// A sampel config file: <br />
    /// &lt;property name="rss_parser"&gt; <br />
    /// &lt;value&gt;rssparser&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="polling_url"&gt; <br />
    /// &lt;value&gt;Ex:www.tc.com/?timestamp={0}&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    ///
    /// <strong>Thread safety</strong>: This class has no mutable state
    /// and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <author>kr00tki</author>
    /// <version>1.0.3</version>
    /// <copyright>Copyright (C) 2006, 2007 TopCoder Inc., All Rights Reserved.</copyright>
    public class PollingEventHandler : IExtensionEventHandler
    {
        /// <summary>
        /// Default configuration namespace.
        /// </summary>
        public const string DefaultConfigurationNamespace =
            "Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers";

        /// <summary>
        /// Default ObjectFactory namespace.
        /// </summary>
        public const string DefaultObjectFactoryNamespace = "TopCoder.Util.ObjectFactory";

        /// <summary>
        /// The rss parser property key name.
        /// </summary>
        private const string PROPERTY_RSS_PARSER = "rss_parser";
        /// <summary>
        /// The polling property key name.
        /// </summary>
        private const string PROPERTY_POLLING_URL = "polling_url";

        /// <summary>
        /// The mime type of bloom filter.
        /// </summary>
        private const string MIME_BLOOM_FILTER = "application/x-tc-bloom-filter";

        /// <summary>
        /// Represents the configuration namespace to use.
        /// Set in the constructor and not changed afterwards. Can not be null or empty.
        /// </summary>
        private readonly string configurationNamespace;

        /// <summary>
        /// Represents the <c>IRSSParser</c> implementation to use to parse RSS responeses.
        /// Set in the constructor form the given parameter or instantiated using a configurable key.
        /// Once set can not change and can not be null.
        /// </summary>
        private readonly IRSSParser rssParser;

        /// <summary>
        /// Constructor.
        /// Creates using the Object Factory and a configured key the IRSSParser.
        /// </summary>
        public PollingEventHandler()
            : this(DefaultConfigurationNamespace, DefaultObjectFactoryNamespace)
        {
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
        public PollingEventHandler(string configurationNamespace, string objectFactoryNamespace)
        {
            Validator.ValidateNullOrEmptyString(configurationNamespace, "configurationNamespace");
            Validator.ValidateNullOrEmptyString(objectFactoryNamespace, "objectFactoryNamespace");

            this.configurationNamespace = configurationNamespace;

            // get config manager
            ConfigManager cm = ConfigManager.GetInstance();

            // create object factory by the given namespace
            ObjectFactory objectfactory = null;
            try
            {
                objectfactory = ObjectFactory.GetDefaultObjectFactory(objectFactoryNamespace);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create object factory by {0}", objectFactoryNamespace), e);
            }

            // Reads the "rss_parser" property and for each event name:
            string value = cm.GetValue(configurationNamespace, PROPERTY_RSS_PARSER);
            try
            {
                rssParser = (IRSSParser)objectfactory.CreateDefinedObject(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create IRSSParser instance by {0}", value), e);
            }
        }

        /// <summary>
        /// Constructor with the given rss parser.
        /// </summary>
        ///
        /// <param name="rssParser">The <c>IRSSParser</c> instance to use.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        public PollingEventHandler(IRSSParser rssParser)
        {
            Validator.ValidateNull(rssParser, "rssParser");
            this.rssParser = rssParser;

            this.configurationNamespace = DefaultConfigurationNamespace;
        }

        /// <summary>
        /// Event handler method. This method will get the content from "polling_url",
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
            Validator.ValidateNull(sender, "sender");
            Validator.ValidateNull(args, "args");
            String value = null;
            try
            {
                // get config manager
                ConfigManager cm = ConfigManager.GetInstance();

                string lastPollingDate = args.Context.Persistence[Helper.KEY_TIMESTAMP];
                if (lastPollingDate.Length == 0) {
                    lastPollingDate = DateTime.UtcNow.ToString("s") + "Z";
                }

                // Reads from the configuration file the configured URL(polling_url property).
                // Reads the "events" property and for each event name:
                value = string.Format(cm.GetValue(configurationNamespace, PROPERTY_POLLING_URL),
                    lastPollingDate);
                // get the content of url and parse it into rss feed.
                RSSFeed rssFeed = null;
                using (Stream stream = Helper.GetDocumentContent(value))
                {
                    rssFeed = rssParser.Parse(stream);
                }

                foreach (RSSItem item in rssFeed.Items)
                {
                    if (item.Description == null)
                    {
                        continue;
                    }

                    if (item.Description.Type == RSSTextType.Html ||
                        item.Description.Type == RSSTextType.Text ||
                        item.Description.Type == RSSTextType.Xhtml)
                    {
                        // If the content is of type text, HTML or XHTML sets the content
                        // to be displayed in the new window using the web browser window navigator.
                        // args.Context.WebBrowserWindowNavigator.Navigate(args.Context.WebBrowser,
                        using(Stream stream = new MemoryStream(ASCIIEncoding.UTF8.GetBytes(item.Description.Text)))
                        {
                            args.Context.WebBrowserWindowNavigator.Navigate(args.Context.WebBrowser, stream, true);
                        }
                    }
                    else
                    {
                        AtomMultimediaContentItem multi = item.Description as AtomMultimediaContentItem;
                        if ((multi != null) && multi.MimeType.Equals(MIME_BLOOM_FILTER))
                        {
                            // If the content is of type "application/x-tc-bloom-filter" restore the bloom filter
                            // from the serialized content of the feed item.
                            // args.Context.BloomFilter
                            string serializedForm = Encoding.UTF8.GetString(multi.RawData);
                            args.Context.BloomFilter = new BloomFilter(serializedForm);
                            MsieClientLogic.GetInstance().Persistence[Helper.KEY_BLOOM_FILTER] = serializedForm;
                        }
                    }
                }

                // Persist the feed timestamp.
                args.Context.Persistence[Helper.KEY_TIMESTAMP] =
                    rssFeed.PublicationDate.ToUniversalTime().ToString("s") + "Z";
            }
            catch (Exception e)
            {
                throw new HandleEventException(
                    string.Format("Failed to handle the event : {0}\n{1}\n{2}", args.EventName, e, value), e);
            }
        }
    }
}

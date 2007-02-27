/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * TestObjectEventHandler.cs
 */

using System;
using System.Text;
using System.Globalization;
using Microsoft.Win32;

using TopCoder.Util.Hash.Algorithm;
using TopCoder.Util.ConfigurationManager;
using TopCoder.Util.ObjectFactory;
using Mshtml;
using System.Text.RegularExpressions;
//using Orpheus.Plugin.InternetExplorer.Interop.Mshtml;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// This class is an implementation of the <c>IExtensionEventHandler</c> interface.<br />
    ///
    /// This handler will test the object input, if don't match, does nothing,
    /// if matched, it will navigate to the configured "test_object_url" url.<br />
    ///
    /// A sample config file: <br />
    /// &lt;property name="hash_algorithm"&gt; <br />
    /// &lt;value&gt;hash_algorithm&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="test_object_url"&gt;
    /// &lt;value&gt;&lt;![CDATA[www.tc.com/?gameId={0}&amp;domain={1}&amp;seq={2}&amp;
    /// hash={3}]]&gt;&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    ///
    /// <strong>Thread safety</strong>: This class has no mutable state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class TestObjectEventHandler : IExtensionEventHandler
    {
        /// <summary>
        /// The property name to object factory
        /// </summary>
        private const string HASH_ALGORITHM = "hash_algorithm";

        /// <summary>
        /// The property name to object factory
        /// </summary>
        private const string TEST_OBJECT_URL = "test_object_url";

        /// <summary>
        /// The culture to use for culture-sensitive text manipulations
        /// </summary>
        private CultureInfo US_CULTURE = new CultureInfo("en-US", false);

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
        /// Represents the configuration namespace to use.
        /// Set in the constructor and not changed afterwards. Can not be null or empty.
        /// </summary>
        private string configurationNamespace;

        /// <summary>
        /// Represents the hash algorithm to use to get the hash.
        /// Set in the constructor form the given parameter or instantiated
        /// using a configurable key. One set can not change and can not be null.
        /// </summary>
        private readonly HashAlgorithm hashAlgorithm;

        /// <summary>
        /// Constructor.
        /// Creates using the Object Factory and a configured key the HashAlgorithm.
        /// </summary>
        ///
        /// <exception cref="ConfigurationException">to signal any problems with the
        /// configuration.</exception>
        public TestObjectEventHandler()
            : this(DefaultConfigurationNamespace, DefaultObjectFactoryNamespace)
        {
        }

        /// <summary>
        /// Constructor.
        /// Creates using the Object Factory and a configured key the HashAlgorithm
        /// using custom namespaces.
        /// </summary>
        ///
        /// <param name="configurationNamespace">Custom configuration namespace.</param>
        /// <param name="objectFactoryNamespace">Custom Object Factory namespace.</param>
        ///
        /// <exception cref="ArgumentNullException">if can not read the property or can not create
        /// the object.</exception>
        /// <exception cref="ArgumentException">if any parameter is empty string.</exception>
        /// <exception cref="ConfigurationException">to signal any problems with the configuration.
        /// </exception>
        public TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace)
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

            // Reads from the configuration file the "hash_algorithm" property.
            string value = cm.GetValue(configurationNamespace, HASH_ALGORITHM);
            try
            {
                hashAlgorithm = (HashAlgorithm) objectfactory.CreateDefinedObject(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException("Failed to create instance of hashAlgorithm.", e);
            }
        }

        /// <summary>
        /// Constructor. Create handler with the gvien hash algorithm.
        /// </summary>
        ///
        /// <param name="hashAlgorithm">The hash algorithm to use.</param>
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        public TestObjectEventHandler(HashAlgorithm hashAlgorithm)
        {
            Validator.ValidateNull(hashAlgorithm, "hashAlgorithm");

            this.hashAlgorithm = hashAlgorithm;
            this.configurationNamespace = DefaultConfigurationNamespace;
        }

        /// <summary>
        /// Event handler method. This handler will test the object input,
        /// if don't match, does nothing, if matched,
        /// it will navigate to the configured "test_object_url" url.
        /// </summary>
        ///
        /// <param name="sender">Sender object.</param>
        /// <param name="args">event arguments.</param>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="HandleEventException">to signal problems in handling the event.</exception>
        public void HandleEvent(object sender, ExtensionEventArgs args)
        {
            Validator.ValidateNull(sender, "sender");
            Validator.ValidateNull(args, "args");

            try
            {
                // Gets the IHTMLElement for the params array.
                IHTMLElement element = (IHTMLElement) args.Parameters[0];
                
                // here not done as the design said get text one by one,
                // but just use innerText, which has the same effect.
                string content = element.innerText;

                if (content == null)
                {
                    return;
                }

                string hash = hashAlgorithm.HashToHexString(NormalizeText(content.ToString()));
                if (hash == args.Context.Persistence[Helper.KEY_HASH])
                {
                    string gameId = args.Context.Persistence[Helper.KEY_GAME_ID];
                    string seq = args.Context.Persistence[Helper.KEY_SEQUENCE];

                    try
                    {
                        string testObjectUrl = ConfigManager.GetInstance().GetValue(
                            configurationNamespace, TEST_OBJECT_URL);
                        // string domain = GetDomain(testObjectUrl);
                        string domain = new Uri(testObjectUrl).Host;

                        string url = string.Format(testObjectUrl, gameId, domain, seq);
                        args.Context.WebBrowserWindowNavigator.Navigate(args.Context.WebBrowser, url, true);
                    }
                    catch (Exception e)
                    {
                        throw new HandleEventException(string.Format(
                            "Failed to handler the event: {0}", args.EventName), e);
                    }
                }
            }
            catch (Exception e)
            {
                throw new HandleEventException("Failed to handle the event." + e, e);
            }
        }

        /// <summary>
        /// Get the domain from url,
        /// the url may like http://www.topcoder.com/..., http:8080//www.topcoder.com/, or
        /// www.topcoder.com/.
        /// </summary>
        ///
        /// <param name="url">the url</param>
        /// <returns>the domain of the url</returns>
        private string GetDomain(string url)
        {
            // the http prefix if existed.
            int pos1 = url.Replace("\\", "/").IndexOf("//");
            int pos2 = url.IndexOf("/", pos1 < 0 ? 0 : pos1 + 2);

            if (pos1 < 0)
            {
                return (pos2 > 0) ? url.Substring(0, pos2) : url;
            }
            else
            {
                return url.Substring(pos1 + 2, pos2 - pos1 - 2);
            }
        }

        /// <summary>
        /// Normalize the text, by trim the string,
        /// and all sequences of whitespace to a single space, and folding all
        /// characters to lower case.
        /// </summary>
        ///
        /// <param name="text">the string text to normalize</param>
        /// <returns>the normalized string</returns>
        private string NormalizeText(string text)
        {
            text = text.ToLower(US_CULTURE);
            StringBuilder builder = new StringBuilder();

            foreach (char ch in text)
            {
                // The applicable definition of "white space" differs from that
                // provided by Char.isWhitespace()
                if (" \t\r\n\f\u200b".IndexOf(ch) < 0) // not HTML whitespace
                {
                    builder.Append(ch);
                }
            }

            return builder.ToString();
        }
    }
}

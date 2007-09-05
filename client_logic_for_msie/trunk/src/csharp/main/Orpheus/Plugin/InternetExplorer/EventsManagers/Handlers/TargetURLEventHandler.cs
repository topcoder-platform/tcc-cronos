/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
using System;
using TopCoder.Util.Hash.Algorithm;
using TopCoder.Util.ConfigurationManager;
using TopCoder.Util.ObjectFactory;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// This class is an implementation of the <c>IExtensionEventHandler</c> interface.<br />
    /// This handler with check if the currently loaded page is the one where the target object can be found.
    /// In such a case new event (CORRECT_PAGE_LOADED) is fired; otherwise the INCORRECT_PAGE_LOADED is fired.
    /// 
    /// <strong>Thread safety</strong>: This class has no mutable state and is thread safe.
    /// </summary>
    /// <author>kr00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2007 TopCoder Inc., All Rights Reserved.</copyright>
    public class TargetURLEventHandler : IExtensionEventHandler
    {
        /// <summary>
        /// The property name to object factory
        /// </summary>
        private const string HASH_ALGORITHM = "hash_algorithm";

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
        /// Default constructor. It creates TargetURLEventHandler instance using the default configuration
        /// namespacs.
        /// </summary>
        /// <exception cref="ConfigurationException">to signal any problems with the
        /// configuration.</exception>
        public TargetURLEventHandler()
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
        public TargetURLEventHandler(string configurationNamespace, string objectFactoryNamespace)
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
                hashAlgorithm = (HashAlgorithm)objectfactory.CreateDefinedObject(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException("Failed to create instance of hashAlgorithm.", e);
            }

        }

        /// <summary>
        /// Event handler method. It checks if the URL given as an event parameter matches the one from registry.
        /// The hash codes of both URL are compared. If they are equal then CORRECT_PAGE_LOADED event is
        /// triggered; otherwise EVENT_INCORRECT_PAGE_LOADED triggered.
        /// </summary>
        ///
        /// <param name="sender">Sender object.</param>
        /// <param name="args">event arguments. The first parameter is expected to be page URL.</param>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="HandleEventException">to signal problems in handling the event.</exception>
        public void HandleEvent(object sender, ExtensionEventArgs args)
        {
            Validator.ValidateNull(sender, "sender");
            Validator.ValidateNull(args, "args");

            try
            {
                string url = args.Parameters[0] as string;
                if (url != null)
                {
                    int idx = url.IndexOf('#');
                    if (idx > 0)
                    {
                        url = url.Substring(0, idx);
                    }
                    // get the hash code of the event URL
                    string hashCode = hashAlgorithm.HashToHexString(url).ToUpper();
                    ExtensionEventArgs eventArgs = null;
                    // check if correct page is loaded
                    if (hashCode.Equals(args.Context.Persistence[Helper.KEY_TARGET_URL]))
                    {
                        eventArgs = new ExtensionEventArgs(Helper.EVENT_CORRECT_PAGE_LOADED, args.Context,
                            args.Parameters);
                    }
                    else
                    {
                        eventArgs = new ExtensionEventArgs(Helper.EVENT_INCORRECT_PAGE_LOADED, args.Context,
                            args.Parameters);
                    }
                    // fire new event
                    args.Context.EventsManager.FireEvent(eventArgs.EventName, this, eventArgs);
                }
            }
            catch (Exception e)
            {
                throw new HandleEventException(string.Format("Failed to handler the event: {0}",
                    args.EventName), e);
            }
        }
    }
}

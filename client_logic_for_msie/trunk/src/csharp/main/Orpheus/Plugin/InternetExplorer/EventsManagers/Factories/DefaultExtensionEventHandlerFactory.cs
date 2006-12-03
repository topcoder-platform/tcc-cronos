/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultExtensionEventHandlerFactory.cs
 */

using System;
using TopCoder.Util.ConfigurationManager;
using TopCoder.Util.ObjectFactory;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Factories
{
    /// <summary>
    /// This class is an implementation of the <c>IExtensionEventHandlerFactory</c>
    /// interface. This implementation will use the specified event name to get
    /// from the configuration file a value representing a key. This key will be
    /// used by the Object Factory component to create the required
    /// <c>IExtensionEventHandler</c> implementation. By using Object Factory
    /// component, constructor parameter values can be specified for the created
    /// implementation. The details of how to configure the Object Factory component
    /// can be found in the component¡¯s specification document.
    ///
    /// A sample config file: <br />
    /// &lt;namespace name="Orpheus.Plugin.InternetExplorer.EventsManagers.Factories"&gt;<br />
    /// &lt;property name="test_handlers"&gt;<br />
    /// &lt;value&gt;test1&lt;/value&gt;<br />
    /// &lt;value&gt;test2&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    /// &lt;property name="test1_handler"&gt;<br />
    /// &lt;value&gt;poll&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    /// &lt;property name="test2_handler"&gt;<br />
    /// &lt;value&gt;http&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    /// &lt;property name="PageChanged_handlers"&gt;<br />
    /// &lt;value&gt;mock&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    /// &lt;property name="PollUpdates_handlers"&gt;<br />
    /// &lt;value&gt;mock&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    /// &lt;property name="mock_handler"&gt;<br />
    /// &lt;value&gt;mock&lt;/value&gt;<br />
    /// &lt;/property&gt;<br />
    /// &lt;property name="empty_handlers"&gt;<br />
    /// &lt;/property&gt;<br />
    /// &lt;/namespace&gt;<br />
    ///
    /// <strong>Thread safety</strong>: This class has no state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class DefaultExtensionEventHandlerFactory : IExtensionEventHandlerFactory
    {
        /// <summary>
        /// Represents the default configuration namespace.
        /// </summary>
        public const string DefaultConfigurationNamespace =
            "Orpheus.Plugin.InternetExplorer.EventsManagers.Factories";

        /// <summary>
        /// Represents the default Object Factory component namespace.
        /// </summary>
        public const string DefaultObjectFactoryNamespace = "TopCoder.Util.ObjectFactory";

        /// <summary>
        /// The property key used in factory to get the event handler name,
        /// it should like the following format &lt;event name&gt;_handlers,
        /// where the event name is the detailed to get the handles.
        /// </summary>
        private const string FORMAT_EVENT_NAMES = "{0}_handlers";

        /// <summary>
        /// The property key used in factory to get the event handler,
        /// it should like the following format &lt;handler name&gt;_handler,
        /// where the handler name is the detailed to get the handles.
        /// </summary>
        private const string FORMAT_EVENT_HANDDLER = "{0}_handler";

        /// <summary>
        /// Represents the configuration namespace to use.
        /// Set in the constructor and not changed afterwards. Can not ne null or empty string.
        /// </summary>
        private readonly string configurationNamespace;

        /// <summary>
        /// Represents the Object Factory namespace to use.
        /// Set in the constructor and not changed afterwards. Can not ne null or empty string.
        /// </summary>
        private readonly string objectFactoryNamespace;

        /// <summary>
        /// Constructor with the default namespace.
        /// </summary>
        public DefaultExtensionEventHandlerFactory()
            : this(DefaultConfigurationNamespace, DefaultObjectFactoryNamespace)
        {
        }

        /// <summary>
        /// Constructor with the given namespace.
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        ///
        /// <param name="configurationNamespace">Custom configuration namespace.</param>
        /// <param name="objectFactoryNamespace">Custom Object Factory namespace.</param>
        /// </summary>
        public DefaultExtensionEventHandlerFactory(string configurationNamespace,
            string objectFactoryNamespace)
        {
            Validator.ValidateNullOrEmptyString(configurationNamespace, "configurationNamespace");
            Validator.ValidateNullOrEmptyString(objectFactoryNamespace, "objectFactoryNamespace");

            this.configurationNamespace = configurationNamespace;
            this.objectFactoryNamespace = objectFactoryNamespace;
        }

        /// <summary>
        /// Reads the key from the configuration file and uses the Object Factory to
        /// create the event handler instances.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        /// <exception cref="EventHandlerCreationException">to signal problems if can not
        /// create the event handlers.</exception>
        ///
        /// <param name='eventName'>Event name.</param>
        /// <returns>Event handlers.</returns>
        public IExtensionEventHandler[] CreateHandlers(string eventName)
        {
            Validator.ValidateNullOrEmptyString(eventName, "eventName");

            try
            {
                // get config manager
                ConfigManager cm = ConfigManager.GetInstance();

                // get the handler names and check it
                string[] handlerNames = cm.GetValues(configurationNamespace,
                    string.Format(FORMAT_EVENT_NAMES, eventName));
                if (handlerNames == null)
                {
                    throw new EventHandlerCreationException(string.Format(
                        "There is no {0} defined in the config file.", eventName));
                }
                if (handlerNames.Length == 0)
                {
                    // if no handler existed, return empty array
                    return new IExtensionEventHandler[0];
                }

                for (int i = 0; i < handlerNames.Length; i++)
                {
                    if (handlerNames[i].Trim().Length == 0)
                    {
                        throw new EventHandlerCreationException(string.Format(
                            "Event {0} value contains empty string.", eventName));
                    }
                }

                string[] handlerTypes = new string[handlerNames.Length];
                // get handler type for each handler name
                for (int i = 0; i < handlerNames.Length; i++)
                {
                    string handlerType = cm.GetValue(configurationNamespace,
                        string.Format(FORMAT_EVENT_HANDDLER, handlerNames[i]));

                    if ((handlerType == null) || (handlerType.Trim().Length == 0))
                    {
                        throw new EventHandlerCreationException(string.Format(
                            "Handler type for {0} is not correct.", handlerNames[i]));
                    }
                    handlerTypes[i] = handlerType;
                }

                // create object factory by the given namespace
                ObjectFactory factory = ObjectFactory.GetDefaultObjectFactory(objectFactoryNamespace);

                // create the handlers with the types given from config file
                // if the type is not IExtensionEventHandler type, throw EventHandlerCreationException
                IExtensionEventHandler[] handlers = new IExtensionEventHandler[handlerTypes.Length];
                for (int i = 0; i < handlerTypes.Length; i++)
                {
                    handlers[i] = (IExtensionEventHandler) factory.CreateDefinedObject(handlerTypes[i]);
                }
                return handlers;
            }
            catch (EventHandlerCreationException)
            {
                throw;
            }
            catch (Exception e)
            {
                throw new EventHandlerCreationException("Failed to create handlers", e);
            }
        }
    }
}

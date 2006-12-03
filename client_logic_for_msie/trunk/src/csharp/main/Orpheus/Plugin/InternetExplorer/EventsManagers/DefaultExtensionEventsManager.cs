/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultExtensionEventsManager.cs
 */

using System;
using System.Collections;
using TopCoder.Util.ConfigurationManager;
using TopCoder.Util.ObjectFactory;
using Orpheus.Plugin.InternetExplorer;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers
{
    /// <summary>
    /// This class is the default implementation of the <c>IExtensionEventsManager</c>
    /// interface. This class restores using a handler factory, the event handlers,
    /// creates a delegate for each handler and stores them, to be invoked
    /// when an event is fired. <br/>
    ///
    /// A sample config file: <br />
    /// &lt;property name="event_handlers_factory"&gt; <br />
    /// &lt;value&gt;event_handlers_factory&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="events"&gt; <br />
    /// &lt;value&gt;test&lt;/value&gt; <br />
    /// &lt;value&gt;empty&lt;/value&gt; <br />
    /// &lt;/property&gt;
    ///
    /// <strong>Thread safety</strong>: This class is thread safe.
    /// It locks on the dictionary inside the methods.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class DefaultExtensionEventsManager : IExtensionEventsManager
    {
        /// <summary>
        /// Represents the default configuration namespace.
        /// </summary>
        public const string DefaultConfigurationNamespace =
            "Orpheus.Plugin.InternetExplorer.EventsManagers";

        /// <summary>
        /// Represents the default Object Factory component namespace.
        /// </summary>
        public const string DefaultObjectFactoryNamespace = "TopCoder.Util.ObjectFactory";

        /// <summary>
        /// The event handler factory key property name.
        /// </summary>
        private const string PROPERTY_EVENT_HANDLER_FACTORY = "event_handlers_factory";

        /// <summary>
        /// The event handler factory key property name.
        /// </summary>
        private const string PROPERTY_EVENTS = "events";

        /// <summary>
        /// Represents a dictionary needed to store the delegates for every event.
        /// The dictionary is set in the constructor to a new Hashtable.
        /// The reference is not changed afterwards. Can not be null or contain null objects.
        /// The keys are event names and the values are IList objects.
        /// The list contains all the handlers for the respective event.
        /// Handlers are added are removed from the dictionary in the
        /// <c>AddEventHandler</c> and <c>RemoveEventHander</c> methods.
        /// </summary>
        private readonly IDictionary extensionEventsHandlers = new Hashtable();

        /// <summary>
        /// Creates the event handlers factory based on a configured key,
        /// gets the event names from the configuration file and
        /// for each one gets the handlers and stores them.
        /// </summary>
        ///
        /// <exception cref="ConfigurationException">if there are problems with the
        /// configuration file or can not create the factory.</exception>
        /// <exception cref="EventHandlerCreationException">propagated from the factory
        /// </exception>
        public DefaultExtensionEventsManager()
            : this(DefaultConfigurationNamespace, DefaultObjectFactoryNamespace)
        {
        }

        /// <summary>
        /// Creates the event handlers factory based on a configured key,
        /// gets the event names from the configuration file
        /// and for each one gets the handlers and stores them.
        /// Uses the custom namespaces to get the ConfigManager and Object Factory.
        /// </summary>
        ///
        /// <exception cref="ConfigurationException">if there are problems with the
        /// configuration file or can not create the factory.</exception>
        /// <exception cref="EventHandlerCreationException">propagated from the factory
        /// </exception>
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        ///
        /// <param name="configurationNamespace">Custom configuration namespace.</param>
        /// <param name="objectFactoryNamepsace">Custom object factory namespace.</param>
        public DefaultExtensionEventsManager(string configurationNamespace,
            string objectFactoryNamepsace)
        {
            Validator.ValidateNullOrEmptyString(configurationNamespace, "configurationNamespace");
            Validator.ValidateNullOrEmptyString(objectFactoryNamepsace, "objectFactoryNamepsace");

            // create object factory by the given namespace
            ObjectFactory objectfactory = null;
            try
            {
                objectfactory = ObjectFactory.GetDefaultObjectFactory(objectFactoryNamepsace);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create object factory by {0}", objectFactoryNamepsace), e);
            }

            IExtensionEventHandlerFactory factory = null;

            // get config manager
            ConfigManager cm = ConfigManager.GetInstance();
            // Reads the "event_handler_factory" key and creates the instance using the Object Factory.
            string value = cm.GetValue(configurationNamespace, PROPERTY_EVENT_HANDLER_FACTORY);
            try
            {
                factory = (IExtensionEventHandlerFactory)objectfactory.CreateDefinedObject(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create event handler factory by {0}", value), e);
            }

            CreateEventHandlers(factory, configurationNamespace);
        }

        /// <summary>
        /// Gets the event names from the configuration file and for each one gets the handlers and stores them.
        /// </summary>
        ///
        /// <exception cref="EventHandlerCreationException"> propagated from the factory,</exception>
        /// <exception cref="ConfigurationException">if there are problems with the configuration file </exception>
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        ///
        /// <param name='extensionEventHandlerFactory'>The event handler factory to use.</param>
        public DefaultExtensionEventsManager(IExtensionEventHandlerFactory extensionEventHandlerFactory)
        {
            Validator.ValidateNull(extensionEventHandlerFactory, "extensionEventHandlerFactory");
            CreateEventHandlers(extensionEventHandlerFactory, DefaultConfigurationNamespace);
        }

        /// <summary>
        /// Create the event handler, and add the delegate to the dictionary.
        /// </summary>
        ///
        /// <param name="factory">the event handler factory</param>
        /// <param name="configurationNamespace">the configuration namespace</param>
        ///
        /// <exception cref="EventHandlerCreationException"> propagated from the factory,</exception>
        /// <exception cref="ConfigurationException">if there are problems with the configuration file </exception>
        private void CreateEventHandlers(IExtensionEventHandlerFactory factory, string configurationNamespace)
        {
            // get config manager
            ConfigManager cm = ConfigManager.GetInstance();

            // Reads the "events" property and for each event name:
            string[] values = cm.GetValues(configurationNamespace, PROPERTY_EVENTS);
            // check the values first
            foreach (string s in values)
            {
                if (s == null || s.Trim().Length == 0)
                {
                    throw new ConfigurationException("Event name should not contains empty string");
                }
            }

            // create delegate
            foreach (string s in values)
            {
                IExtensionEventHandler[] handlers = factory.CreateHandlers(s);
                IList delegators = null;

                if (extensionEventsHandlers.Contains(s))
                {
                    delegators = (IList) extensionEventsHandlers[s];
                }
                else
                {
                    delegators = new ArrayList();
                    extensionEventsHandlers[s] = delegators;
                }

                foreach(IExtensionEventHandler handler in handlers)
                {
                    delegators.Add(new ExtensionEventHandlerDelegate(handler.HandleEvent));
                }
            }
        }

        /// <summary>
        /// This methods adds the delegate for the specified event.
        /// When the event is fired the delegate will be invoked.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException"> if  parameter is empty string.</exception>
        ///
        /// <param name='eventName'>The event name to add the handler to.</param>
        /// <param name='eventHandler'>The event handler.</param>
        /// <returns> Returns true if added false otherwise.</returns>
        public bool AddEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler)
        {
            Validator.ValidateNullOrEmptyString(eventName, "eventName");
            Validator.ValidateNull(eventHandler, "eventHandler");

            lock (extensionEventsHandlers)
            {
                // as the design not required, duplicate eventHandler should not added,
                // here don't consider such case
                IList handlers = (IList) extensionEventsHandlers[eventName];
                if (handlers == null)
                {
                    handlers = new ArrayList();
                    this.extensionEventsHandlers[eventName] = handlers;
                }
                return handlers.Add(eventHandler) >= 0;
            }
        }

        /// <summary>
        /// /// This methods removes the delegate for the specified event.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException"> if parameter is empty string.</exception>
        ///
        /// <param name='eventName'>The event name to remove the handler from.</param>
        /// <param name='eventHandler'>The event handler.</param>
        /// <returns>True if removed false otherwise.</returns>
        public bool RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler)
        {
            Validator.ValidateNullOrEmptyString(eventName, "eventName");
            Validator.ValidateNull(eventHandler, "eventHandler");

            lock (extensionEventsHandlers )
            {
                // as the design not required, duplicate eventHandler should not added,
                // here don't consider such case
                IList list = (IList) extensionEventsHandlers[eventName];
                if (null != list  && list.Contains(eventHandler))
                {
                    list.Remove(eventHandler);
                    return !list.Contains(eventHandler);
                }
                return false;
            }
        }

        /// <summary>
        /// This method returns the registered delegates for the specified event.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException"> if any parameter is null.</exception>
        /// <exception>ArgumentException if parameter is empty string.</exception>
        ///
        /// <param name='eventName'>The event name.</param>
        /// <returns>The array of delegates registered for the current event.
        /// Empty array is returned if none are registered.</returns>
        public ExtensionEventHandlerDelegate[] GetEventHandlers(string eventName)
        {
            Validator.ValidateNullOrEmptyString(eventName, "eventName");

            lock (extensionEventsHandlers)
            {
                // Gets from the dictionary the IList for the event name as the key.
                if (extensionEventsHandlers.Contains(eventName))
                {
                    return (ExtensionEventHandlerDelegate[]) (((ArrayList)
                        this.extensionEventsHandlers[eventName])).ToArray(typeof(ExtensionEventHandlerDelegate));
                }
                return new ExtensionEventHandlerDelegate[0];
            }
        }

        /// <summary>
        /// This method fires the specifid event. As a result all registerd delegates
        /// for the event get invoked.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException"> if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        /// <exception cref="FireEventException">to wrap any exceptions thrown while
        /// inoking the delegates.</exception>
        ///
        /// <param name='eventName'>The event to fire.</param>
        /// <param name='sender'>The sender object to pass to handlers.</param>
        /// <param name='args'>The args object to pass to handlers.</param>
        public void FireEvent(string eventName, object sender, ExtensionEventArgs args)
        {
            Validator.ValidateNull(sender, "sender");
            Validator.ValidateNull(args, "args");

            // Get the delegates from the event name and
            // invokes each one passing the sender and the args objects.
            ExtensionEventHandlerDelegate[] delegates = GetEventHandlers(eventName);
            foreach (ExtensionEventHandlerDelegate @delegate in delegates)
            {
                @delegate(sender, args);
            }
        }
    }
}

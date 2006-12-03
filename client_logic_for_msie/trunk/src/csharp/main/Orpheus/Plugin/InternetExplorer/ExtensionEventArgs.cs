/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ExtensionEventArgs.cs
 */

using System;
using System.IO;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Event argument class for event handlers.
    /// This class holds a reference to the context object and provides handlers
    /// with the event name as well for which they were invoked. As extra flexibility
    /// it also allows clients to pass to event handlers any object thorugh the
    /// params array. The handlers will need to know of any of these objects
    /// if they are to use it.
    ///
    /// <strong>Thread safety: </strong>This class has no mutable state
    /// and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class ExtensionEventArgs : EventArgs
    {
        /// <summary>
        /// Represents the name of the event that was fired.
        /// Set in the constructor, can not be null or empty string
        /// and not changed afterwards.
        /// </summary>
        private readonly string eventName;

        /// <summary>
        /// Represents the context object passed to the handlers.
        /// Set in the constructor, can not be null, and not changed afterwards.
        /// </summary>
        private readonly MsieClientLogic context;

        /// <summary>
        /// Represents an array of custom objects passed to the handlers.
        /// Set in the constructor, can not be null, and not changed afterwards.
        /// </summary>
        private readonly object[] parameters;

        /// <summary>
        /// Returns the event name.
        /// </summary>
        ///
        /// <value>Represents the name of the event that was fired.</value>
        public string EventName
        {
            get
            {
                return eventName;
            }
        }

        /// <summary>
        /// Returns the context object.
        /// </summary>
        ///
        /// <value>Represents the context object passed to the handlers.</value>
        public MsieClientLogic Context
        {
            get
            {
                return context;
            }
        }

        /// <summary>
        /// Returns the params array.
        /// </summary>
        ///
        /// <value>Represents an array of custom objects passed to the handlers.</value>
        public object[] Parameters
        {
            get
            {
                return parameters;
            }
        }

        /// <summary>
        /// Constructor, create an instance with the given arguments.
        /// </summary>
        ///
        /// <param name="eventName">The name of the event</param>
        /// <param name="context">Event context.</param>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        public ExtensionEventArgs(string eventName, MsieClientLogic context)
            : this(eventName, context, new object[0] { })
        {
        }

        /// <summary>
        /// Sets the fields to the parameter values. Creates a new empty array.
        /// </summary>
        ///
        /// <param name="eventName">The name of the event</param>
        /// <param name="context">Event context.</param>
        /// <param name="parameters">An array of objects to pass to the event handlers.</param>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        public ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters)
        {
            Validator.ValidateNullOrEmptyString(eventName, "eventName");
            Validator.ValidateNull(context, "context");
            Validator.ValidateNull(parameters, "params");

            this.eventName = eventName;
            this.context = context;
            this.parameters = parameters;
        }
    }
}

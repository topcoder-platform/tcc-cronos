/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * EventHandlerCreationException.cs
 */

using System;
using System.Runtime.Serialization;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers
{
    /// <summary>
    /// Exception to signal any problems when creating the event handlers for a event.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [Serializable]
    public class EventHandlerCreationException : ClientLogicExtensionException
    {
        /// <summary>
        /// Creates a new exception.
        /// </summary>
        public EventHandlerCreationException() : base()
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message.
        /// </summary>
        ///
        /// <param name="message">Exception message.</param>
        public EventHandlerCreationException(string message) : base(message)
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message and cause
        /// </summary>
        ///
        /// <param name="message">Exception message.</param>
        /// <param name="cause">Exception cause.</param>
        public EventHandlerCreationException(string message, Exception cause) :
            base(message, cause)
        {
        }

        /// <summary>
        /// Seriaization constructor.
        /// </summary>
        ///
        /// <param name="info">Serialization info.</param>
        /// <param name="context">Streaming context</param>
        protected EventHandlerCreationException(SerializationInfo info,
            StreamingContext context) : base(info, context)
        {
        }
    }
}

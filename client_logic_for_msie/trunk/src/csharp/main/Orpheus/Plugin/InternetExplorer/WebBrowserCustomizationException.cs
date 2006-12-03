/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * WebBrowserCustomizationException.cs
 */

using System;
using System.Runtime.Serialization;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Exception to signal any problems when customizing the browser
    /// by providing a <c>IDocHostUIHandler</c>.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [Serializable]
    public class WebBrowserCustomizationException : ClientLogicExtensionException
    {
        /// <summary>
        /// Creates a new exception.
        /// </summary>
        public WebBrowserCustomizationException()
            : base()
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message.
        /// </summary>
        ///
        /// <param name="message">Exception message.</param>
        public WebBrowserCustomizationException(string message)
            : base(message)
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message and cause
        /// </summary>
        ///
        /// <param name="message">Exception message.</param>
        /// <param name="cause">Exception cause.</param>
        public WebBrowserCustomizationException(string message, Exception cause)
            : base(message, cause)
        {
        }

        /// <summary>
        /// Seriaization constructor.
        /// </summary>
        ///
        /// <param name="info">Serialization info.</param>
        /// <param name="context">Streaming context</param>
        protected WebBrowserCustomizationException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
        }
    }
}

/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ExtensionAttribute.cs
 */

using System;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Extension atribute class. <c>ToolBand</c> objects that need to be installed must be set
    /// with this attribute.
    ///
    /// <strong>Thread safety:</strong> This class has no mutable state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [Serializable]
    public class ExtensionAttribute : Attribute
    {
        /// <summary>
        /// Represents the name of the tool band.
        /// Set in the constructor and not changed afterwards. Can not be null or empty.
        /// </summary>
        private readonly string name;

        /// <summary>
        /// Returns the name of the tool band.
        /// </summary>
        ///
        /// <value>the name of the tool band.</value>
        public string Name
        {
            get
            {
                return name;
            }
        }

        /// <summary>
        /// Constructor. Sets the field to the paramter value.
        /// </summary>
        ///
        /// <param name="name">The tool band name.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ArgumentException">if parameter is empty string.</exception>
        public ExtensionAttribute(string name)
        {
            Validator.ValidateNullOrEmptyString(name, "name");

            this.name = name;
        }
    }
}

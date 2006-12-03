/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * Validator.cs
 */
using System;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This class defines some validate methods used in this component
    /// to check the argument.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    sealed class Validator
    {
        /// <summary>
        /// Private constructor prevents from being initialized.
        /// </summary>
        private Validator()
        {
        }

        /// <summary>
        /// Check if the given obj is null, if it is null, throws <c>ArgumentNullException</c>.
        /// </summary>
        ///
        /// <param name="obj">the object to check.</param>
        /// <param name="name">the name used in <c>ArgumentNullException</c> to
        /// indicate the name of obj.</param>
        ///
        /// <exception cref="ArgumentNullException">if obj is null</exception>
        public static void ValidateNull(object obj, string name)
        {
            if (obj == null)
            {
                throw new ArgumentNullException(name, String.Format("{0} should be non-null.", name));
            }
        }

        /// <summary>
        /// Check if the given str is a null or empty string.
        /// If it is null, throws <c>ArgumentNullException</c>.
        /// If it is empty string (trimmed), throws <c>ArgumentException</c>.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException">if str is null.</exception>
        /// <exception cref="ArgumentException">if str is an emtpy string (trimmed).</exception>
        ///
        /// <param name="str">the string to check</param>
        /// <param name="name">
        /// the name used in exception to indicate the name of str</param>
        public static void ValidateNullOrEmptyString(string str, string name)
        {
            Validator.ValidateNull(str, name);
            if (str.Trim().Length == 0)
            {
                throw new ArgumentException(String.Format("{0} should be non-empty string (trimmed).", name));
            }
        }
    }
}

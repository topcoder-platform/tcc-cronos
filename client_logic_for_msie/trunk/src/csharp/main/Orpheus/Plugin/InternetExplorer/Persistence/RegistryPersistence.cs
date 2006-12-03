/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * RegistrytPersistence.cs
 */

using System;
using Microsoft.Win32;

namespace Orpheus.Plugin.InternetExplorer.Persistence
{
    /// <summary>
    /// This class is an implementation of the <c>IPersistence</c> interface.
    /// It used <c>Registry</c> to save the values.
    /// <br />
    ///
    /// <strong>Thread safety</strong>: This class has no state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class RegistryPersistence : IPersistence
    {
        /// <summary>
        /// The registry key used to save the save.
        /// </summary>
        private const string APPLICATION_KEY = "Software\\Orpheus";

        /// <summary>
        /// Empty constrcutor.
        /// </summary>
        public RegistryPersistence()
        {
        }

        /// <summary>
        /// This indexer saves and returns a value for a specified key from the registry.
        /// </summary>
        ///
        /// <param name="key">Key under which the value is stored.</param>
        /// <returns>The value.</returns>
        ///
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if any parameter is empty string.</exception>
        /// <exception cref="PersistenceException">to wrap any problems with the registry.</exception>
        public string this[string key]
        {
            get
            {
                Validator.ValidateNullOrEmptyString(key, "key");

                // Gets the subkey for this application, here we use "Orpheus"
                // instead of using the OpenSubkey, here use CreateSubKey,
                // if no application existed, create it.
                // and gets the value from this registry key for the indexer key.
                // If no registry key/value is found, empty string is returned.
                RegistryKey appKey = null;
                try
                {
                    appKey = Registry.CurrentUser.CreateSubKey(APPLICATION_KEY);
                    string value = (string) appKey.GetValue(key);
                    return (value == null) ? string.Empty : value;
                }
                catch (Exception e)
                {
                    throw new PersistenceException(string.Format("Failed to get the value for {0}", key), e);
                }
                finally
                {
                    if (null != appKey)
                    {
                        appKey.Close();
                    }
                }
            }
            set
            {
                Validator.ValidateNullOrEmptyString(key, "key");
                Validator.ValidateNullOrEmptyString(value, "value");

                RegistryKey appKey = null;

                // Gets the subkey for this application, here we use "Orpheus"
                // instead of using the OpenSubkey, here use CreateSubKey,
                // if no application existed, create it.
                // and sets the value for the key.
                try
                {
                    appKey = Registry.CurrentUser.CreateSubKey(APPLICATION_KEY);
                    appKey.SetValue(key, value);
                }
                catch (Exception e)
                {
                    throw new PersistenceException(string.Format("Failed to set {0} to {1}", key, value), e);
                }
                finally
                {
                    if (null != appKey)
                    {
                        appKey.Close();
                    }
                }
            }
        }
    }
}

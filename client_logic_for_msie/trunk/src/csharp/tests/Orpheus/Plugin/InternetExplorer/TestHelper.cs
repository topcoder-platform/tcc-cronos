/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * TestHelper.cs
 */
using System;
using System.Reflection;
using TopCoder.Util.ConfigurationManager;
using Microsoft.Win32;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// The helper class for Unit Test.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public sealed class TestHelper
    {
        /// <summary>
        /// A page used in test.
        /// </summary>
        public const string URL = "http://localhost//msie/test.html";

        /// <summary>
        /// The config file for object factory.
        /// </summary>
        private const string CONFIG_OBJECT_FACTORY = "../../test_files/object_factory.xml";

        /// <summary>
        /// The config file for this component
        /// </summary>
        private const string CONFIG_MSIE = "../../test_files/client_logic_for_msie.xml";

        /// <summary>
        /// The singleton instance of config manager.
        /// </summary>
        private static ConfigManager CM = ConfigManager.GetInstance();

        /// <summary>
        /// Private constructor prevents from initialization.
        /// </summary>
        private TestHelper()
        {
        }

        /// <summary>
        /// Load the config files needed for test
        /// </summary>
        public static void LoadConfigFile()
        {
            ClearNamespace();
            CM.LoadFile(CONFIG_MSIE);
            CM.LoadFile(CONFIG_OBJECT_FACTORY);
        }

        /// <summary>
        /// Clear all loaded namespace.
        /// And clear the registered key if existed.
        /// </summary>
        public static void ClearNamespace()
        {
            CM.Clear(false);
            ClearKeys();
        }

        /// <summary>
        /// Clear all loaded namespace.
        /// </summary>
        private static void ClearKeys()
        {
            try
            {
                Registry.CurrentUser.DeleteSubKeyTree("Software\\Orpheus");
            }
            catch (Exception)
            {
                // ignore
            }
        }

        /// <summary>
        /// Gets the internal field value by reflection.
        /// </summary>
        ///
        /// <param name="obj">The instance to get field value from</param>
        /// <param name="fieldName">the field name</param>
        /// <returns>the field value</returns>
        public static object GetFieldValue(object obj, string fieldName)
        {
            FieldInfo field = obj.GetType().GetField(fieldName,
                BindingFlags.NonPublic | BindingFlags.Instance);
            return field.GetValue(obj);
        }
    }
}

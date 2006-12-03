/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ExtensionInstaller.cs
 */

using System;
using Microsoft.Win32;
using System.Runtime.InteropServices;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Installer class for Internet Exporer Extensions.
    /// Extensions must be set with the <c>ExtensionAttribute</c> in order to be recognized by the installer.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class ExtensionInstaller
    {
        /// <summary>
        /// Represents the format string for clsid key.
        /// </summary>
        private const string CLSID_FORMAT = "CLSID\\{0}";

        /// <summary>
        /// Represents the name of the key to hold the registered information.
        /// </summary>
        private const string IMPLEMENTED_CATEGORIES = "Implemented Categories";

        /// <summary>
        /// The key for the name of extension attriubte.
        /// </summary>
        private const string MENU_TEXT = "MenuText";

        /// <summary>
        /// Registry key for toool bar.
        /// </summary>
        private const string CLSID_TOOLBAR = "{00021494-0000-0000-C000-000000000046}";

        /// <summary>
        /// Default empty constructor.
        /// </summary>
        private ExtensionInstaller()
        {
        }

        /// <summary>
        /// Register the tool bar.
        /// </summary>
        /// <param name="type">Type to register</param>
        [ComRegisterFunction]
        public static void Register(Type type)
        {
            if (type == null)
            {
                Console.WriteLine("The type to register is null, simply return");
                return;
            }

            // Gets the extension name
            string name = GetExtensionName(type);
            if (name == null)
            {
                return;
            }

            RegistryKey clsidKey = null;
            RegistryKey catKey = null;
            try
            {
                // create two subkeys
                clsidKey = Registry.ClassesRoot.CreateSubKey(GetGUIDFromType(type));
                clsidKey.SetValue(MENU_TEXT, name);

                catKey = clsidKey.CreateSubKey(IMPLEMENTED_CATEGORIES);
                catKey.CreateSubKey(CLSID_TOOLBAR);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            finally
            {
                if (null != clsidKey)
                {
                    clsidKey.Close();
                }
                if (null != catKey)
                {
                    catKey.Close();
                }
            }
        }

        /// <summary>
        /// Unregister the tool bar.
        /// </summary>
        ///
        /// <param name="type">Type to unregister</param>
        [ComUnregisterFunction]
        public static void Unregister(Type type)
        {
            if (type == null)
            {
                Console.WriteLine("The type to unregister is null, simply return");
                return;
            }

            // Gets the extension name
            string name = GetExtensionName(type);
            if (name == null)
            {
                return;
            }

            try
            {
                // Deletes the registry key:
                Registry.ClassesRoot.DeleteSubKeyTree(GetGUIDFromType(type));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        /// <summary>
        /// Gets the string format of guid of type.
        /// The format of guid is like '{xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx}'.
        /// </summary>
        ///
        ///
        /// <param name="type">the type to register/unregister</param>
        /// <returns>the string format of guid.</returns>
        private static string GetGUIDFromType(Type type)
        {
            return string.Format(CLSID_FORMAT, type.GUID.ToString("B"));
        }

        /// <summary>
        /// Gets the extension name of the type.
        /// </summary>
        ///
        /// <param name="type">the type to register/unregister</param>
        /// <returns>the extension name, or null if no extension set.</returns>
        private static string GetExtensionName(Type type)
        {
            ExtensionAttribute[] objs = (ExtensionAttribute[])
                type.GetCustomAttributes(typeof(ExtensionAttribute), true);
            if (objs == null || objs.Length == 0)
            {
                Console.WriteLine(string.Format(
                    "The type {0} do not set extension attribute.", type.FullName));
                return null;
            }
            return objs[0].Name;
        }
    }
}

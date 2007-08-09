using System;
using System.Runtime.InteropServices;
using MsHtmHstInterop;
using Orpheus.Plugin.InternetExplorer.Persistence;

namespace Orpheus.Plugin.InternetExplorer.BHO
{
	/// <summary>
	/// The Browser Helper Object (BHO) that is use to enable the Orpheus Toolbar on the first
	/// browser start. It uses the Windows Registry (via RegistryPersistence class) to store the
	/// start up flag.
	/// </summary>
    /// <author>kr00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2007 TopCoder Inc., All Rights Reserved.</copyright>
	[ComVisible(true)]
    [Guid("0A537A61-3814-484c-8194-5C46C369AE21")]
    public class OrpheusBHO : IObjectWithSite
	{
        /// <summary>
        /// The name of the stratup flag value.
        /// </summary>
        private const string StartupKey = "startup";

        /// <summary>
        /// The browser instance.
        /// </summary>
        private SHDocVw.InternetExplorer explorer;

        /// <summary>
        /// Empty constructor.
        /// </summary>
		public OrpheusBHO()
		{
		}

        /// <summary>
        /// Implements the IObjectWithSite.SetSite method. It casts given site object to InternetExplorer
        /// and enables the Orpheus toolbar (only on the first browser start).
        /// </summary>
        /// <param name="site">the site object.</param>
        public void SetSite(object site)
        {
            if (site != null)
            {
                explorer = site as SHDocVw.InternetExplorer;
                if (explorer != null) 
                {
                    ShowBrowserBar();
                }
            }
        }

        /// <summary>
        /// Retrieves the last site set with <c>IObjectWithSite.SetSite</c>.
        /// </summary>
        /// <param name="guid">The IID of the interface pointer that should be returned in ppvSite.</param>
        /// <param name="ppvSite">Address of pointer variable that receives the interface pointer requested in riid.
        /// Upon successful return, ppvSite contains the requested interface pointer to the site</param>
        public void GetSite(ref Guid guid, out IntPtr ppvSite)
        {
            IntPtr punk = Marshal.GetIUnknownForObject(explorer);
            Marshal.QueryInterface(punk, ref guid, out ppvSite);
            Marshal.Release(punk);
        }

        /// <summary>
        /// This method will explicitly show the Orpheus Toolbar on first browser start after the installation.
        /// </summary>
        private void ShowBrowserBar()
        {
            RegistryPersistence persistence = new RegistryPersistence();
            if (bool.Parse(persistence[StartupKey]))
            {
                object toolbarGuid = (object)typeof(OrpheusToolbar).GUID.ToString("B");
                object show = true;
                object nullObj = null;
                object showFalse = false;
                // turn it off first
                explorer.ShowBrowserBar(ref toolbarGuid, ref showFalse, ref nullObj);
                // turn the toolbar on
                explorer.ShowBrowserBar(ref toolbarGuid, ref show, ref nullObj);
                persistence[StartupKey] = false.ToString();
            }

            
        }

 	}
}

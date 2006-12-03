/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * WebBrowserSite.cs
 */

using System;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using MsHtmHstInterop;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This class is the base class of the Internet Explorer extension model and
    /// provides for derived classes a site within the browser. In general,
    /// a site is an intermediate object placed in the middle of the container
    /// and each contained object. <br />
    ///
    /// This class implements <c>IObjectWithSite</c> interface so it's container
    /// can supply it with an interface pointer for its site object. Then,
    /// this class, can communicate directly with its site. <br />
    ///
    /// A container can pass the <c>IUnknown</c> pointer of its site to an object
    /// through <c>IObjectWithSite.SetSite</c>. Callers can also retrieve the latest
    /// site passed to <c>IObjectWithSite.SetSite</c> through <c>IObjectWithSite.GetSite</c>.
    /// This usage provides a hooking mechanism, allowing third parties to intercept calls
    /// from an object to its container site object. <br />
    ///
    /// This class extends the Control class to allow derived classes to
    /// build their user interface. <br />
    ///
    /// Together with the <c>ToolBand</c> class, both classes provide a generic mechanism
    /// for creating Internet Explorer extensions. These classes are decoupled for the
    /// intended purpose of this component to provide the client side logic for a web a
    /// pplication. The usage scenario is to derive the <c>ToolBand</c> class and there hook
    /// the client logic functionality implemented in the <c>MsieClientLogic</c> class. <br />
    ///
    /// <strong>Thread safety</strong>: This class is not thread safe,
    /// but because it is a user control it does not need to be.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class WebBrowserSite : UserControl, IObjectWithSite
    {
        /// <summary>
        /// The guid for IWebBrowserApp.
        /// </summary>
        private const string GUID_IWEBBROWSERAPP = "{0002DF05-0000-0000-C000-000000000046}";

        /// <summary>
        /// The guid for IUnknown.
        /// </summary>
        private const string GUID_IUNKNOWN = "{00000000-0000-0000-C000-000000000046}";

        /// <summary>
        /// Represents the host Internet Explorer.
        /// The host is set in the <c>SetSite</c> method and should not change afterwards.
        /// Can be null until the <c>SetSite</c> method is invoked by the browser.
        /// </summary>
        private WebBrowserClass host = null;

        /// <summary>
        /// Represents the site of the tool bar in the Internet Explorer host.
        /// The site is set in the <c>SetSite</c> method and should not change afterwards.
        /// Can be null until the <c>SetSite</c> method is invoked by the browser.
        /// It is returned in the <c>GetSite</c> method.
        /// </summary>
        private IInputObjectSite site = null;

        /// <summary>
        /// Returns the member host browser.
        /// </summary>
        ///
        /// <value>Represents the member host browser.</value>
        public WebBrowserClass Host
        {
            get
            {
                return host;
            }
        }

        /// <summary>
        /// Returns the member extension site.
        /// </summary>
        ///
        /// <value>Represents the member extension site.</value>
        public new IInputObjectSite Site
        {
            get
            {
                return site;
            }
        }

        /// <summary>
        /// Default empty constructor.
        /// </summary>
        public WebBrowserSite()
        {
        }

        /// <summary>
        /// The <c>SetSite</c> method passes the container site object's <c>IUnknown</c>
        /// pointer to the object being managed. The object should hold onto this pointer,
        /// calling <c>IUnknown.AddRef</c> in doing so. If the object already has a site,
        /// it should call that existing site's <c>IUnknown.Release</c>, save the new site
        /// pointer, and call the new site's <c>IUnknown.AddRef</c>.
        /// </summary>
        ///
        /// <param name="pUnkSite">Pointer to the IUnknown interface pointer of the site
        /// managing this object.</param>
        ///
        /// <exception cref="SiteSettingException">if can not get the browser and to
        /// wrap COM exceptions.</exception>
        public virtual void SetSite(object pUnkSite)
        {
            if (pUnkSite == null)
            {
                return;
            }

            if (null != site)
            {
                // If the member site is not null (already has a site) releases it
                Marshal.ReleaseComObject(site);
            }
            if (null != host)
            {
                // If the host explorer is not null release it
                 Marshal.ReleaseComObject(host);
            }

            try
            {
                // Get the IInputObjectSite by casting the input pUnkSite.
                site = pUnkSite as IInputObjectSite;

                // The passed pointer also implements the IServiceProvider interface
                // which we will use to get to the browser reference
                // Create a wrapper of type WebBrowserClass using the Marshal.CreateWrapperOfType.method.
                MsHtmHstInterop.IServiceProvider serviceProvider = pUnkSite as MsHtmHstInterop.IServiceProvider;
                Guid guid = new Guid(GUID_IWEBBROWSERAPP);
                Guid riid = new Guid(GUID_IUNKNOWN);

                object webBrowser;
                serviceProvider.RemoteQueryService(ref guid, ref riid, out webBrowser);

                // Cast the object to WebBrowserClass
                host = (WebBrowserClass)Marshal.CreateWrapperOfType(webBrowser, typeof(WebBrowserClass));
            }
            catch (Exception e)
            {
                throw new SiteSettingException("Failed to set site.", e);
            }
        }

        /// <summary>
        /// Retrieves the last site set with <c>IObjectWithSite.SetSite</c>.
        /// </summary>
        ///
        /// <param name="riid"> The IID of the interface pointer that should be returned in ppvSite.</param>
        /// <param name="ppvSite">Address of pointer variable that receives the interface pointer requested in riid.
        /// Upon successful return, ppvSite contains the requested interface pointer to the site</param>
        public virtual void GetSite(ref Guid riid, out IntPtr ppvSite)
        {
            // catch the exception is not required by the design

            IntPtr punk = Marshal.GetIUnknownForObject(host);
            Marshal.QueryInterface(punk, ref riid, out ppvSite);
            Marshal.Release(punk);
        }
    }
}

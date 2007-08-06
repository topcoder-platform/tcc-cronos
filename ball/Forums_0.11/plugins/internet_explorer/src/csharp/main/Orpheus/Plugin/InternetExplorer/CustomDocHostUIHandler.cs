using System;
using MsHtmHstInterop;
using Mshtml;
using SHDocVw;
using System.Runtime.InteropServices;
namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This class extends the <see cref="DefaultDocHostUIHandler"/>. It provides additional functionality 
    /// to the base handler.
    /// </summary>
    /// <author>k00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class CustomDocHostUIHandler : DefaultDocHostUIHandler
    {
        /// <summary>
        /// The client logic context.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// Constructor used by the <c>MsieClientLogic</c> to create this object.
        /// The <c>MsieClientLogic</c> passed is needed to get the scripting object.
        /// </summary>
        ///
        /// <param name="context">MsieClientLogic context.</param>
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        public CustomDocHostUIHandler(MsieClientLogic context) : base(context)
        {
            this.context = context;
        }

        /// <summary>
        /// Called by MSHTML when <c>IOleInPlaceActiveObject.TranslateAccelerator</c>
        /// or <c>IOleControlSite.TranslateAccelerator</c> is called. In this case we need
        /// to throw NotImplementedException to use the default accelerators.
        /// </summary>
        ///
        /// <param name="lpmsg">Pointer to a MSG structure that specifies the message
        /// to be translated.</param>
        /// <param name="pguidCmdGroup">Pointer to a GUID for the command group identifier.</param>
        /// <param name="nCmdID">specifies a command identifier.</param>
        public override void TranslateAccelerator(ref tagMSG lpmsg, ref Guid pguidCmdGroup, uint nCmdID)
        {
            throw new NotImplementedException();
        }
  }
}

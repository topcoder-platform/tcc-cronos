/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultDocHostUIHandler.cs
 */

using System;
using MsHtmHstInterop;
using Mshtml;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This class implements the <see cref="IDocHostUIHandler"/> interface and
    /// provides the means to extend the Internet Explorer Document Object Model
    /// (DOM) with objects, methods, and properties. <br />
    ///
    /// This is done by providing MSHTML a pointer to the <c>IDispatch</c> interface
    /// for the COM automation object that implements the custom object
    /// (ScriptingObject class) properties, and methods.
    /// These objects, properties, and methods will then be available to
    /// any page displayed by the web browser through the document's external object.
    /// Instances of this class are created by the <c>OrpheusToolBand</c> class
    /// and are set to the browser. The only requirement on <c>IDocHostUIHandler</c>
    /// implementations is to provide a constructor with a (ExtensionContext) parameter<br />
    ///
    /// There is only one method that this class actually implements,
    /// the GetExternal method which will set the pointer to the scripting object created
    /// by this class. This class is also responsible for creating the configured scripting object
    /// using the Object Factory. The scripting object must provide an (ExtensionContext)
    /// constructor which will be invoked and must have the COM visible attribute set to true.<br />
    ///
    /// <strong>Thread safety</strong>: This class has no mutable state and is thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class DefaultDocHostUIHandler : IDocHostUIHandler
    {
        /// <summary>
        /// The context <c>MsieClientLogic</c> instance.
        /// The <c>MsieClientLogic</c> is used by this class to get the object to be exposed
        /// to JavaScript code.<br />
        /// Set in the constructor and setter. Can not be null.
        /// </summary>
        private MsieClientLogic context = null;

        /// <summary>
        /// Constructor used by the <c>MsieClientLogic</c> to create this object.
        /// The <c>MsieClientLogic</c> passed is needed to get the scripting object.
        /// </summary>
        ///
        /// <param name="context">MsieClientLogic context.</param>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        public DefaultDocHostUIHandler(MsieClientLogic context)
        {
            Validator.ValidateNull(context, "context");

            this.context = context;
        }

        /// <summary>
        /// Called when MSHTML displays a modal UI.
        /// </summary>
        ///
        /// <param name="fEnable">BOOL that indicates if the host's modeless
        /// dialog boxes are enabled or disabled.</param>
        public virtual void EnableModeless(int fEnable)
        {
            // does nothing
        }

        /// <summary>
        /// Called by MSHTML to allow the host to replace the MSHTML data object.
        /// </summary>
        ///
        /// <param name="pDO">Pointer to an <c>IDataObject</c> interface
        /// supplied by MSHTML.</param>
        /// <param name="ppDORet">Address of a pointer variable that receives
        /// an <c>IDataObject</c> interface pointer supplied by the host.</param>
        public virtual void FilterDataObject(IDataObject pDO, out IDataObject ppDORet)
        {
            // does nothing
            ppDORet = null;
        }

        /// <summary>
        /// Called by MSHTML when it is used as a drop target.
        /// This method enables the host to supply an alternative
        /// <c>IDropTarget</c>interface.
        /// </summary>
        ///
        /// <param name="pDropTarget">Pointer to an <c>IDropTarget</c> interface
        /// for the current drop target object supplied by MSHTML.</param>
        /// <param name="ppDropTarget">Address of a pointer variable that receives
        /// an <c>IDropTarget</c> interface pointer for the alternative drop target
        /// object supplied by the host.</param>
        public virtual void GetDropTarget(IDropTarget pDropTarget, out IDropTarget ppDropTarget)
        {
            // does nothing
            ppDropTarget = null;
        }

        /// <summary>
        /// Called by MSHTML to obtain the host's <c>IDispatch</c> interface.
        /// This method will be invoked by the Internet Exporer scripting engine
        /// whenever JavaScript code will access the external object.
        ///
        /// </summary>
        /// <param name="ppDispatch">Address of a pointer to a variable that receives
        /// an <c>IDispatch</c> interface pointer for the host application.</param>
        public virtual void GetExternal(out object ppDispatch)
        {
            ppDispatch = context.ScriptingObject;
        }

        /// <summary>
        /// Called by MSHTML to retrieve the user interface (UI)
        /// capabilities of the application that is hosting MSHTML.
        /// </summary>
        ///
        /// <param name="pInfo">Pointer to a DOCHOSTUIINFO structure that receives
        /// the host's UI capabilities.</param>
        public virtual void GetHostInfo(ref _DOCHOSTUIINFO pInfo)
        {
            // does nothing
        }

        /// <summary>
        /// Called by the WebBrowser Control to retrieve a registry subkey path
        /// that overrides the default Internet Explorer registry settings.
        /// </summary>
        ///
        /// <param name="pchKey">Pointer to a <c>LPOLESTR</c> that receives
        /// the registry subkey string where the host stores its registry settings.</param>
        /// <param name="dw">Reserved. Must be set to NULL.</param>
        public virtual void GetOptionKeyPath(out string pchKey, uint dw)
        {
            pchKey = string.Empty;
        }

        /// <summary>
        /// Called when MSHTML removes its menus and toolbars.
        /// </summary>
        public virtual void HideUI()
        {
            // does nothing
        }

        /// <summary>
        /// Called by the MSHTML implementation of
        /// <c>IOleInPlaceActiveObject.OnDocWindowActivate</c>.
        /// </summary>
        ///
        /// <param name="fActivate">BOOL value that indicates
        /// the state of the document window.</param>
        public virtual void OnDocWindowActivate(int fActivate)
        {
            // does nothing
        }

        /// <summary>
        /// Called by the MSHTML implementation of
        /// <c>IOleInPlaceActiveObject.OnFrameWindowActivate</c>.
        /// </summary>
        ///
        /// <param name="fActivate">BOOL value that indicates the state of
        /// the container's top-level frame window.</param>
        public virtual void OnFrameWindowActivate(int fActivate)
        {
            // does nothing
        }

        /// <summary>
        /// Called by the MSHTML implementation of <c>IOleInPlaceActiveObject.ResizeBorder</c>.
        /// </summary>
        ///
        /// <param name="prcBorder">Constant pointer to a <c>RECT</c> for the new outer
        /// rectangle of the border.</param>
        /// <param name="pUIWindow">Pointer to an <c>IOleInPlaceUIWindow</c> interface
        /// for the frame or document window whose border is to be changed.</param>
        /// <param name="fRameWindow">BOOL that is TRUE if the frame window is calling
        /// <c>IDocHostUIHandler::ResizeBorder</c>, or FALSE otherwise.</param>
        public virtual void ResizeBorder(ref MsHtmHstInterop.tagRECT prcBorder,
            IOleInPlaceUIWindow pUIWindow, int fRameWindow)
        {
            // does nothing
        }

        /// <summary>
        /// Called by MSHTML to display a shortcut menu
        /// </summary>
        ///
        /// <param name="dwID">specifies the identifier of the shortcut menu to be displayed</param>
        /// <param name="ppt">Pointer to a POINT structure containing the screen
        /// coordinates for the menu.</param>
        /// <param name="pcmdtReserved">Pointer to the <c>IUnknown</c> of an <c>IOleCommandTarget</c>
        /// interface used to query command status and execute commands on this object.</param>
        /// <param name="pdispReserved">Pointer to an <c>IDispatch</c> interface of the object
        /// at the screen coordinates specified in ppt</param>
        public virtual void ShowContextMenu(uint dwID, ref MsHtmHstInterop.tagPOINT ppt,
            object pcmdtReserved, object pdispReserved)
        {
            // does nothing
        }

        /// <summary>
        /// Called by MSHTML to enable the host to replace MSHTML menus and toolbars.
        /// </summary>
        ///
        /// <param name="dwID">receives a DOCHOSTUITYPE value indicating the type of
        /// user interface (UI).</param>
        /// <param name="pActiveObject">Pointer to an <c>IOleInPlaceActiveObject</c>
        /// interface for the active object.</param>
        /// <param name="pCommandTarget">Pointer to an <c>IOleCommandTarget</c>
        /// interface for the object.</param>
        /// <param name="pFrame">Pointer to an <c>IOleInPlaceFrame</c> interface
        /// for the object. Menus and toolbars must use this parameter.</param>
        /// <param name="pDoc">Pointer to an <c>IOleInPlaceUIWindow</c> interface
        /// for the object.Toolbars must use this parameter.</param>
        public virtual void ShowUI(uint dwID, IOleInPlaceActiveObject pActiveObject,
            IOleCommandTarget pCommandTarget,  IOleInPlaceFrame pFrame, IOleInPlaceUIWindow pDoc)
        {
            // does nothing
        }

        /// <summary>
        /// Called by MSHTML when <c>IOleInPlaceActiveObject.TranslateAccelerator</c>
        /// or <c>IOleControlSite.TranslateAccelerator</c> is called.
        /// </summary>
        ///
        /// <param name="lpmsg">Pointer to a MSG structure that specifies the message
        /// to be translated.</param>
        /// <param name="pguidCmdGroup">Pointer to a GUID for the command group identifier.</param>
        /// <param name="nCmdID">specifies a command identifier.</param>
        public virtual void TranslateAccelerator(ref tagMSG lpmsg, ref Guid pguidCmdGroup, uint nCmdID)
        {
            // does nothing
        }

        /// <summary>
        /// Called by MSHTML to retrieve the user interface (UI) capabilities of the application
        /// that is hosting MSHTML.
        /// </summary>
        ///
        /// <param name="dwTranslate">Reserved. Must be set to NULL.</param>
        /// <param name="pchURLIn">Pointer to an OLECHAR that specifies the current URL
        /// for navigation.</param>
        /// <param name="ppchURLOut"> Address of a pointer variable that receives an OLECHAR
        /// pointer containing the new URL.</param>
        public virtual void TranslateUrl(uint dwTranslate, ref ushort pchURLIn, IntPtr ppchURLOut)
        {
            // does nothing.
        }

        /// <summary>
        /// Called by MSHTML to notify the host that the command state has changed.
        /// </summary>
        public virtual void UpdateUI()
        {
            // does nothing
        }
    }
}

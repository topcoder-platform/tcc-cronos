/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ComInterop.cs
 */

using System;
using System.Runtime.InteropServices;

namespace MsHtmHstInterop
{
    /// <summary>
    /// DESKBANDINFO structure defined in ShObjIdl.idl file.
    /// </summary>
    [StructLayout(LayoutKind.Sequential, Pack=4)]
    public struct DESKBANDINFO
    {
        /// <summary>
        /// Set of flags that determine which members of this structure are being requested.
        /// </summary>
        public uint dwMask;

        /// <summary>
        /// POINTL structure that receives the minimum size of the band object. The minimum width is placed 
        /// in the x member, and the minimum height is placed in the y member.
        /// </summary>
        public _POINTL ptMinSize;
        
        /// <summary>
        /// POINTL structure that receives the maximum size of the band object. The maximum height is placed
        /// in the y member and the x member is ignored. If there is no limit for the maximum height,
        /// (LONG)-1 should be used.
        /// </summary>
        public _POINTL ptMaxSize;
        
        /// <summary>
        /// POINTL structure that receives the sizing step value of the band object.
        /// The vertical step value is placed in the y member, and the x member is ignored.
        /// The step value determines in what increments the band will be resized.
        /// This member is ignored if dwModeFlags does not contain DBIMF_VARIABLEHEIGHT.
        /// </summary>
        public _POINTL ptIntegral;
        
        /// <summary>
        /// POINTL structure that receives the ideal size of the band object.
        /// The ideal width is placed in the x member, and the ideal height is placed in the y member.
        /// The band container will attempt to use these values, but the band is not guaranteed to be this size.
        /// </summary>
        public _POINTL ptActual;
        
        /// <summary>
        /// WCHAR buffer that receives the title of the band.
        /// </summary>
        [MarshalAs(UnmanagedType.ByValArray, SizeConst=0x100)]
        public ushort[] wszTitle;

        /// <summary>
        /// Value that receives a set of flags that define the mode of operation for the band object.
        /// </summary>
        public uint dwModeFlags;

        /// <summary>
        /// Value that receives the background color of the band. This member is ignored if dwModeFlags
        /// does not contain the DBIMF_BKCOLOR flag.
        /// </summary>
        public uint crBkgnd;
    }



    /// <summary>
    /// IDeskBand interface defined in ShObjIdl.idl file. This interface should be implemented
    /// by IE toolbars.
    /// </summary>
    [ComImport]
    [Guid("EB0FE172-1A3A-11D0-89B3-00A0C90A90AC")] 
    [InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    public interface IDeskBand
    {
        /// <summary>
        /// Returns the window handle to one of the windows participating in in-place
        /// activation (frame, document, parent, or in-place object window).
        /// </summary>
        ///
        /// <param name="ppWnd">Pointer to where to return the window handle.</param>
        void GetWindow(out IntPtr ppWnd);

        /// <summary>
        /// Determines whether context-sensitive help mode should be entered
        /// during an in-place activation session.
        /// </summary>
        ///
        /// <param name="fEnterMode">nonzero if help mode should be entered;
        /// zero if it should be exited</param>
        void ContextSensitiveHelp([In] int fEnterMode);

        /// <summary>
        /// Called by explorer when band object needs to be shown or hidden.
        /// </summary>
        ///
        /// <param name="bShow">Int value indicating whether the docking window object
        /// should show or hide itself. If this parameter is nonzero, the docking window
        /// object should show its window. If it is zero, the docking window object should
        /// hide its window.</param>
        void ShowDW([In] int bShow);

        /// <summary>
        /// Notifies the docking window object that it is about to be removed
        /// from the frame. The docking window object should save any persistent
        /// information at this time.
        /// </summary>
        ///
        /// <param name="bShow">Reserved. This parameter should always be zero. </param>
        void CloseDW([In] uint bShow);

        /// <summary>
        /// Notifies the docking window object that the frame's border space has changed.
        /// In response to this method, the <c>IDockingWindow</c> implementation must call
        /// <c>IDockingWindowSite::SetBorderSpaceDW</c>, even if no border space is required
        /// or a change is not necessary.
        /// </summary>
        ///
        /// <param name="prcBorder">Address of a RECT structure that contains the
        /// frame's available border space. </param>
        /// <param name="punkToolbarSite">Address of the site's IUnknown interface.
        /// The docking window object should call the QueryInterface method for this
        /// interface, requesting IID_IShellToolbarSite, and use that interface to
        /// negotiate its border space. It is the docking window object's responsibility
        /// to release this interface when it is no longer needed. </param>
        /// <param name="fReserved">Reserved. This parameter should always be zero.</param>
        void ResizeBorderDW(ref tagRECT prcBorder,
            [In, MarshalAs(UnmanagedType.IUnknown)] Object punkToolbarSite,
           int fReserved);

        /// <summary>
        /// This method is invoked by the browser to get the details of the tool band.
        /// </summary>
        ///
        /// <param name="dwBandID">Identifier of the band. The container assigns this identifier.
        /// he band object can keep this value if it is required.</param>
        /// <param name="dwViewMode">View mode of the band object</param>
        /// <param name="dbi">Address of a DESKBANDINFO structure that receives the band
        /// information for the object</param>
        void GetBandInfo(uint dwBandID, uint dwViewMode, ref DESKBANDINFO dbi);
    }
}
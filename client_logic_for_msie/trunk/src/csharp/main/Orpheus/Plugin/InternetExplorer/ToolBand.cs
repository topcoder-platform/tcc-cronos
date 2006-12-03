/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ToolBand.cs
 */
using System;
using System.ComponentModel;
using MsHtmHstInterop;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This class extends the <c>WebBrowserSite</c> and implements the <c>IDeskBand</c> interface
    /// in order to provide a base class for all tool band objects. The <c>IDeskBand</c> interface
    /// is used by the browser to retrieve information about a band object. <br />
    ///
    /// <strong>Thread safety</strong>: This class is not thread safe, but because it is
    /// a user control it does not need to be.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class ToolBand : WebBrowserSite, IDeskBand
    {
        /// <summary>
        /// Represents the title of the band.
        /// It will be displayed at the left or on top of the band object.
        /// Get and set in the associated property.
        /// Can not be null.
        /// Referenced also in the <c>GetBandInfo</c> method to construct the required structure.
        /// </summary>
        private string title = string.Empty;

        /// <summary>
        /// Represents the minimum size of the band object.
        /// Default value of (-1,-1) sets no minimum constraint.
        /// Get and set in the associated property.
        /// Referenced also in the <c>GetBandInfo</c> method to construct the required structure.
        /// </summary>
        private System.Drawing.Size minSize = new System.Drawing.Size(-1, -1);

        /// <summary>
        /// Represents the maximum size of the band object.
        /// Default value of (-1,-1) sets no maximum constraint.
        /// Get and set in the associated property.
        /// Referenced also in the <c>GetBandInfo</c> method to construct the required structure.
        /// </summary>
        private System.Drawing.Size maxSize = new System.Drawing.Size(-1, -1);

        /// <summary>
        /// Represents the integral size of the band object.
        /// Default value of (-1,-1) sets no integral constraint.
        /// Get and set in the associated property.
        /// Referenced also in the <c>GetBandInfo</c> method to construct the required structure.
        /// </summary>
        private System.Drawing.Size integralSize = new System.Drawing.Size(-1, -1);

        /// <summary>
        /// Returns or sets the title of the tool band.
        /// </summary>
        ///
        /// <value>Represents the title of the tool band.</value>
        [Browsable(true)]
        [DefaultValue("")]
        public string Title
        {
            get
            {
                return this.title;
            }
            set
            {
                // the design don't required to check

                this.title = value;
            }
        }

        /// <summary>
        /// Returns or sets the minimum size of the tool band.
        /// </summary>
        ///
        /// <value>Represents the minimum size of the tool band.</value>
        [Browsable(true)]
        [DefaultValue(typeof(System.Drawing.Size), "-1, -1")]
        public System.Drawing.Size MinSize
        {
            get
            {
                return minSize;
            }
            set
            {
                // the design don't required to check

                this.minSize = value;
            }
        }

        /// <summary>
        /// Returns or sets the maximum size of the tool band.
        /// </summary>
        ///
        /// <value>Represents the maximum size of the tool band.</value>
        [Browsable(true)]
        [DefaultValue(typeof(System.Drawing.Size), "-1, -1")]
        public System.Drawing.Size MaxSize
        {
            get
            {
                return maxSize;
            }
            set
            {
                // the design don't required to check

                this.maxSize = value;
            }
        }

        /// <summary>
        /// Returns or sets the integral size of the tool band.
        /// </summary>
        ///
        /// <value>Represents the integral size of the tool band.</value>
        [Browsable(true)]
        [DefaultValue(typeof(System.Drawing.Size), "-1, -1")]
        public System.Drawing.Size IntegralSize
        {
            get
            {
                return integralSize;
            }
            set
            {
                // the design don't required to check

                this.integralSize = value;
            }
        }

        /// <summary>
        /// Empty default constructor.
        /// </summary>
        public ToolBand()
        {
        }

        /// <summary>
        /// This method is invoked by the browser to get the details of the tool band.
        /// </summary>
        ///
        /// <param name="dwBandID">Identifier of the band. The container assigns this identifier.
        /// he band object can keep this value if it is required.</param>
        /// <param name="dwViewMode">View mode of the band object</param>
        /// <param name="dbi">Address of a DESKBANDINFO structure that receives the band
        /// information for the object</param>
        public virtual void GetBandInfo(uint dwBandID, uint dwViewMode, ref DESKBANDINFO dbi)
        {
             // DBIM_MINSIZE    = 0x0001,
             // DBIM_MAXSIZE    = 0x0002,
             // DBIM_INTEGRAL   = 0x0004,
             // DBIM_ACTUAL     = 0x0008,
             // DBIM_TITLE      = 0x0010,
             // DBIM_MODEFLAGS  = 0x0020,
             // DBIM_BKCOLOR    = 0x0040
            dbi.dwModeFlags = 0x0010 | 0x0008 | 0x0002 | 0x0001 | 0x0004;

            System.Array.Copy(Title.ToCharArray(), dbi.wszTitle,
                Title.Length > dbi.wszTitle.Length ? dbi.wszTitle.Length : Title.Length);
            dbi.ptMinSize.x = MinSize.Width; dbi.ptMinSize.y = MinSize.Height;
            dbi.ptMaxSize.x = MaxSize.Width; dbi.ptMaxSize.y = MaxSize.Height;
            dbi.ptIntegral.x = IntegralSize.Width; dbi.ptIntegral.y = IntegralSize.Height;
            dbi.ptActual.x = Size.Width; dbi.ptActual.y = Size.Height;
        }

        /// <summary>
        /// Called by explorer when band object needs to be shown or hidden.
        /// </summary>
        ///
        /// <param name="bShow">Boolean value indicating whether the docking window object
        /// should show or hide itself. If this parameter is nonzero, the docking window
        /// object should show its window. If it is zero, the docking window object should
        /// hide its window.</param>
        public virtual void ShowDW(int bShow)
        {
            if (bShow != 0)
            {
                Show();
            }
            else
            {
                Hide();
            }
        }

        /// <summary>
        /// Notifies the docking window object that it is about to be removed
        /// from the frame. The docking window object should save any persistent
        /// information at this time.
        /// </summary>
        ///
        /// <param name="bShow">Reserved. This parameter should always be zero. </param>
        public virtual void CloseDW(uint bShow)
        {
            Dispose(true);
        }

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
        public virtual void ResizeBorderDW(ref tagRECT prcBorder, object punkToolbarSite, int fReserved)
        {
            // not implemented.
        }

        /// <summary>
        /// Returns the window handle to one of the windows participating in in-place
        /// activation (frame, document, parent, or in-place object window).
        /// </summary>
        ///
        /// <param name="ppWnd">Pointer to where to return the window handle.</param>
        public virtual void GetWindow(out IntPtr ppWnd)
        {
            ppWnd = Handle;
        }

        /// <summary>
        /// Determines whether context-sensitive help mode should be entered
        /// during an in-place activation session.
        /// </summary>
        ///
        /// <param name="fEnterMode">nonzero if help mode should be entered;
        /// zero if it should be exited</param>
        public virtual void ContextSensitiveHelp(int fEnterMode)
        {
            // not implemetned
        }
    }
}

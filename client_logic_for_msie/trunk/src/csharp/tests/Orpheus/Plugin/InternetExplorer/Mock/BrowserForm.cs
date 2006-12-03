using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using AxSHDocVw;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.Mock
{
    /// <summary>
    /// </summary>
    public class BrowserForm : System.Windows.Forms.Form
    {
        private AxWebBrowser axWebBrowser1;
        /// <summary>
        /// </summary>
        private System.ComponentModel.Container components = null;

        public BrowserForm()
        {
            InitializeComponent();
            Navigate("about:blank");
            while (axWebBrowser1.Document == null)
            {
                Application.DoEvents();
            }
        }

        /// <summary>
        /// </summary>
        protected override void Dispose( bool disposing )
        {
            if( disposing )
            {
                if(components != null)
                {
                    components.Dispose();
                }
            }
            base.Dispose( disposing );
        }

        #region Windows
        /// <summary>
        /// </summary>
        private void InitializeComponent()
        {
            System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(BrowserForm));
            this.axWebBrowser1 = new AxSHDocVw.AxWebBrowser();
            ((System.ComponentModel.ISupportInitialize)(this.axWebBrowser1)).BeginInit();
            this.SuspendLayout();
            //
            // axWebBrowser1
            //
            this.axWebBrowser1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.axWebBrowser1.Enabled = true;
            this.axWebBrowser1.Location = new System.Drawing.Point(0, 0);
            this.axWebBrowser1.OcxState = ((System.Windows.Forms.AxHost.State)(resources.GetObject("axWebBrowser1.OcxState")));
            this.axWebBrowser1.Size = new System.Drawing.Size(292, 273);
            this.axWebBrowser1.TabIndex = 0;
            //
            // BrowserForm
            //
            this.AutoScaleBaseSize = new System.Drawing.Size(6, 14);
            this.ClientSize = new System.Drawing.Size(292, 273);
            this.Controls.Add(this.axWebBrowser1);
            this.Name = "BrowserForm";
            this.Text = "BrowserForm";
            ((System.ComponentModel.ISupportInitialize)(this.axWebBrowser1)).EndInit();
            this.ResumeLayout(false);

        }
        #endregion




        public WebBrowserClass GetWebBrowserClass()
        {
            return (WebBrowserClass) axWebBrowser1.GetOcx();
        }

        public AxWebBrowser GetAxWebBrowser()
        {
            return axWebBrowser1;
        }

        public void Navigate(string url)
        {
            object url1 = url;
            axWebBrowser1.Navigate2(ref url1);
            while (axWebBrowser1.ReadyState != tagREADYSTATE.READYSTATE_COMPLETE)
            {
                Application.DoEvents();
            }
        }
    }
}

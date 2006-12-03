using System.ComponentModel;
using System.Drawing;
using System.Threading;
using System.Resources;
using System.Windows.Forms;
using AxSHDocVw;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.StressTests
{
    public class StressTestsForm : Form
    {
        private AxWebBrowser axWebBrowser1;

        private Container components = null;

        public StressTestsForm()
        {
            InitializeComponent();

            Navigate("about:blank");
            while (axWebBrowser1.Document == null)
            {
                Application.DoEvents();
                Thread.Sleep(200);
            }
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
            }
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            ResourceManager resources = new ResourceManager(typeof (StressTestsForm));
            axWebBrowser1 = new AxWebBrowser();
            ((ISupportInitialize) (axWebBrowser1)).BeginInit();
            SuspendLayout();
            //
            // axWebBrowser1
            //
            axWebBrowser1.Dock = DockStyle.Fill;
            axWebBrowser1.Enabled = true;
            axWebBrowser1.Location = new Point(0, 0);
            axWebBrowser1.OcxState = ((AxHost.State) (resources.GetObject("axWebBrowser1.OcxState")));
            axWebBrowser1.Size = new Size(288, 269);
            axWebBrowser1.TabIndex = 0;
            //
            // StressTestsForm
            //
            AutoScaleBaseSize = new Size(6, 14);
            ClientSize = new Size(288, 269);
            Controls.Add(axWebBrowser1);
            Name = "StressTestsForm";
            Text = "StressTestsForm";
            ((ISupportInitialize) (axWebBrowser1)).EndInit();
            axWebBrowser1.
            ResumeLayout(false);
        }

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

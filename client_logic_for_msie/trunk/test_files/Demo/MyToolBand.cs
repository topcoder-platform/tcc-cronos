/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * 
 * MyToolBand.cs
 */

using System;
using System.Windows.Forms;
using TopCoder.Util.ConfigurationManager;

using Orpheus.Plugin.InternetExplorer;
using System.Runtime.InteropServices;
using Mshtml;

namespace Orpheus.Plugin.InternetExplorer
{
	/// <summary>
	/// The first thing to do is to actually create the tool bar user interface 
	/// and hook up various user control events to handlers. <br />
	/// 
	/// A tool band should be created by extending the ToolBand class. 
	/// The user interface can be created using the Visual studio designer. 
	/// Several properties are available at design time like the title 
	/// and size of the tool band. <br />
	/// 
	/// The class must be marked with the ExtensionAttribute and the Guid attribute. 
	/// The Extension attribute is needed to be recognized by the installer 
	/// class and the Guid to be accessible as a COM object.
	/// </summary>
	/// 
	/// <author>TCSDESIGNER</author>
	/// <author>TCSDEVELOPER</author>
	/// <version>1.0</version>
	/// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
	[ComVisible(true)]
	[Guid("F2E189BE-405E-45fb-98C2-026CFFEBF95B")]
	[Extension("Demo Plugin")]
	[ClassInterface(ClassInterfaceType.None)]
	public class MyToolBand : ToolBand
	{
		private System.Windows.Forms.Button btnLogin;
		private System.Windows.Forms.Button btnLogout;
		/// <summary>
		/// Hold a reference to the client side logic object
		/// </summary>
		private MsieClientLogic clientLogic = null;

		/// <summary>
		/// Login in handler
		/// </summary>
		private ExtensionEventHandlerDelegate login;
		
		/// <summary>
		/// Login out handler
		/// </summary>
		private ExtensionEventHandlerDelegate logout;

		/// <summary>
		/// Empty constructor
		/// </summary>
		public MyToolBand()
		{
			InitializeComponent();
			login = new ExtensionEventHandlerDelegate(HandleLoggedIn);
			logout = new ExtensionEventHandlerDelegate(HandleLoggedOut);

		}

		/// <summary>
		/// override the method sa we know when the site is set.
		/// </summary>
		/// 
		/// <param name="pUnkSite">Pointer to the IUnknown interface pointer of the site
		/// managing this object.</param>
		public override void SetSite(object pUnkSite)
		{
			if (pUnkSite == null)
			{
				return;
			}
			base.SetSite(pUnkSite);
			try
			{

				// this should get the singleton instance of the object.
				// The singleton is needed so the same MsieClientLogic instance 
				// is shared if a popup browser window is opened.
				// The MsieClientLogic can also be created using the public constructors.
				this.clientLogic = new MsieClientLogic(this.Host);

				// after the site is set and we have a reference to 
				// the web browser we get the client side logic object
				this.clientLogic = MsieClientLogic.GetInstance(this.Host);

				// add event hanlder here
				this.clientLogic.EventsManager.AddEventHandler(Helper.EVENT_PAGE_CHANGED,
					new ExtensionEventHandlerDelegate(OnCompleted));
			}
			catch (Exception e)
			{
				System.Console.WriteLine(e.StackTrace);
			}
		}

		/// <summary>
		/// When completed load, navigate the our url.
		/// </summary>
		/// <param name="sender">the sender</param>
		/// <param name="args">the args</param>
		private void OnCompleted(object sender, ExtensionEventArgs args)
		{
			this.clientLogic.WebBrowserWindowNavigator.Navigate(this.clientLogic.WebBrowser,
				"http://www.topcoder.com", true);

			// customize the opened browser window
			this.clientLogic.CustomizeWebBrowser(this.Host);

			// add event handler
			this.clientLogic.EventsManager.AddEventHandler(Helper.EVENT_LOGGEDIN, login);
			this.clientLogic.EventsManager.AddEventHandler(Helper.EVENT_LOGGEDOUT, logout);
		}

		/// <summary>
		/// Evne handler
		/// </summary>
		/// <param name="sender">the sender</param>
		/// <param name="e">the argument</param>
		public void HandleLoggedIn(object sender, ExtensionEventArgs args)
		{
			MessageBox.Show("You have loggined.");
		}

		/// <summary>
		/// Evne handler
		/// </summary>
		/// <param name="sender">the sender</param>
		/// <param name="e">the argument</param>
		public void HandleLoggedOut(object sender, ExtensionEventArgs args)
		{
			MessageBox.Show("You have log out.");
		}


		/// <summary>
		/// Initialize the components.
		/// </summary>
		private void InitializeComponent()
		{
			this.btnLogin = new System.Windows.Forms.Button();
			this.btnLogout = new System.Windows.Forms.Button();
			this.SuspendLayout();
			// 
			// btnLogin
			// 
			this.btnLogin.Location = new System.Drawing.Point(56, 5);
			this.btnLogin.Name = "btnLogin";
			this.btnLogin.TabIndex = 0;
			this.btnLogin.Text = "Login";
			this.btnLogin.Click += new System.EventHandler(this.btnLogin_Click);
			// 
			// btnLogout
			// 
			this.btnLogout.Location = new System.Drawing.Point(152, 5);
			this.btnLogout.Name = "btnLogout";
			this.btnLogout.TabIndex = 1;
			this.btnLogout.Text = "Logout";
			this.btnLogout.Click += new System.EventHandler(this.btnLogout_Click);
			// 
			// MyToolBand
			// 
			this.Controls.Add(this.btnLogout);
			this.Controls.Add(this.btnLogin);
			this.MaxSize = new System.Drawing.Size(256, 100);
			this.MinSize = new System.Drawing.Size(60, 32);
			this.Name = "MyToolBand";
			this.Size = new System.Drawing.Size(256, 32);
			this.Title = "Msie plugin Demo";
			this.ResumeLayout(false);

		}

		/// <summary>
		/// Handler for login.
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void btnLogin_Click(object sender, System.EventArgs e)
		{
			MessageBox.Show("Login...");
			
			//create the event arguments class
			ExtensionEventArgs args = new ExtensionEventArgs(Helper.EVENT_LOGGEDIN, this.clientLogic);

			//fire the corresponding event
			this.clientLogic.EventsManager.FireEvent(Helper.EVENT_LOGGEDIN, sender, args);
		}

		/// <summary>
		/// Handler for logout.
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		private void btnLogout_Click(object sender, System.EventArgs e)
		{
			MessageBox.Show("Logout....");
			//create the event arguments class
			ExtensionEventArgs args = new ExtensionEventArgs(Helper.EVENT_LOGGEDOUT, this.clientLogic);

			//fire the corresponding event
			this.clientLogic.EventsManager.FireEvent(Helper.EVENT_LOGGEDOUT, sender, args);
		}

		/// <summary>
		/// Register the com.
		/// </summary>
		/// <param name="t">the type</param>
		[ComRegisterFunction]
		public static void RegisterBHO(Type t)
		{
			ExtensionInstaller.Register(t);
		}

		/// <summary>
		/// Unregister the com.
		/// </summary>
		/// <param name="t">the type.</param>
		[ComUnregisterFunction]
		public static void UnregisterBHO(Type t)
		{
			ExtensionInstaller.Unregister(t);
		}
	}
}
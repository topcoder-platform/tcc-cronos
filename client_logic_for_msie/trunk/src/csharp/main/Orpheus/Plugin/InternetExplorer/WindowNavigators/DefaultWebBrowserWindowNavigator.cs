/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultWebBrowserWindowNavigator.cs
 */
using System;
using System.Text;
using System.IO;
using System.Net;

using MsHtmHstInterop;
using Mshtml;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.WindowNavigators
{
	/// <summary>
	/// This class is an implementation of the <c>IWebBrowserWindowNavigator</c> interface. <br />
	///
	/// It will be responsible for displaying the requested page or the provided content inside
	/// either the main browser window or inside a opened and reused window. <br />
	///
	/// The new opened window will have the same customization object set to it as the main
	/// browser window. When the new window is opened, the Internet Explorer will load the
	/// extension object again. The extension object, if used as depicted in the component
	/// specifications, will get the same instance of the <c>MsieClientLogic</c> by using
	/// the singleton of the class, and will call the <c>CustomizeWebBrowser</c> method
	/// which will set the same customization object to the newly opened browser. <br />
	///
	/// <strong>Thread safety:</strong> This class locks on this inside the property getter
	/// and setter and inside the <c>Navigate</c> methods.
	/// </summary>
	///
	/// <author>TCSDESIGNER</author>
	/// <author>TCSDEVELOPER</author>
	/// <version>1.0</version>
	/// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
	public class DefaultWebBrowserWindowNavigator : IWebBrowserWindowNavigator
	{
		/// <summary>
		/// Represents the newlly created window. This new window is created using the DOM
		/// of the browser inside the <c>Navigate</c> method. <br />
		///
		/// It can be null if no new window is displayed.
		/// Once a new one is displayed it will point to this one.
		/// </summary>
		private IHTMLWindow2 popupWindow = null;

		/// <summary>
		/// Empty constructor.
		/// </summary>
		public DefaultWebBrowserWindowNavigator()
		{
		}

		/// <summary>
		/// This method directs the browser or the new window to the specified URL.
		/// </summary>
		///
		/// <param name="webBrowser">The web browser to disaplay the url in,
		/// or to open the window from.</param>
		/// <param name="url">The URL to navigate to .</param>
		/// <param name="newWindow">Indoicates whether a new window should be opened.</param>
		///
		/// <exception cref="ArgumentNullException">if parameter is null.</exception>
		/// <exception cref="ArgumentException">if parameter is empty string.</exception>
		/// <exception cref="WebBrowserNavigationException">to signal problems
		/// while navigating to the new location.</exception>
		public void Navigate(WebBrowserClass webBrowser, string url, bool newWindow)
		{
			Validator.ValidateNull(webBrowser, "webBrowser");
			Validator.ValidateNullOrEmptyString(url, "url");

			try
			{
				if (!newWindow)
				{
					// when new window is false, nagivate use the browser directly
					object flags = 0;
					object targetFrame = "_self";
					object postData = String.Empty;
					object headers = String.Empty;
					webBrowser.Navigate(url, ref flags, ref targetFrame, ref postData, ref headers);
				}
				else
				{
					// when new window is true, nagivate use popupWindow
					if (!(popupWindow != null && (!popupWindow.closed)))
					{
						// when the popup window is null or closed,
						// open it with "about:blank" and minimum controls displayed
						string name = "_blank";
						string feature = "status=no,menubar=no,location=no, scrollbars=yes";
						bool replace = false;
						IHTMLDocument2 doc = webBrowser.Document as IHTMLDocument2;
						popupWindow = (IHTMLWindow2)doc.open(url, name, feature, replace);
					}
					if (popupWindow != null)
					{
						popupWindow.navigate(url);
					}
				}
			}
			catch (Exception e)
			{
				throw new WebBrowserNavigationException(
					string.Format("Failed to navigate to the url {0} in webbrowser.", url), e);
			}
		}

		/// <summary>
		/// This method displays the content in the browser browser or the new window.
		/// </summary>
		///
		/// <param name="webBrowser">The web browser to disaplay the url in,
		/// or to open the window from.</param>
		/// <param name="content"> The content to set to the browser.</param>
		/// <param name="newWindow">Indoicates whether a new window should be opened.</param>
		///
		/// <exception cref="ArgumentNullException">if parameter is null.</exception>
		/// <exception cref="WebBrowserNavigationException">to signal problems
		/// while navigating to the new location.</exception>
		public void Navigate(WebBrowserClass webBrowser, Stream content, bool newWindow)
		{
			Validator.ValidateNull(webBrowser, "webBrowser");
			Validator.ValidateNull(content, "content");

			// Read the content into stream.
			// we don't close the stream here.
			string output = null;
			try
			{
				using (TextReader reader = new StreamReader(content, Encoding.Default))
				{
					output = reader.ReadToEnd();
				}
			}
			catch (Exception e)
			{
				throw new WebBrowserNavigationException(
					"Failed to read content from the input stream.", e);
			}

			try
			{
				if (!newWindow)
				{
					// when new window is false,
					// output the content to the browser
					((IHTMLDocument2)webBrowser.Document).write(output);
				}
				else
				{
					// a new window should be used
					// output the content to popup window
					if (!(popupWindow != null && (!popupWindow.closed)))
					{
						// the popup is null or closed
						// open it with "about:blank" and minimum controls displayed
						string name = "_blank";
						string feature = "status=0;menubar=0;location=0";
						bool replace = false;
						popupWindow = (IHTMLWindow2)((IHTMLDocument2)webBrowser.Document).open(
							"about:blank", name, feature, replace);
					}

					if (popupWindow != null)
					{
						// output the content to the popup window
						popupWindow.document.write(output);
					}
				}
			}
			catch (Exception e)
			{
				throw new WebBrowserNavigationException("Failed to Display the content in webbrowser.", e);
			}
		}
	}
}

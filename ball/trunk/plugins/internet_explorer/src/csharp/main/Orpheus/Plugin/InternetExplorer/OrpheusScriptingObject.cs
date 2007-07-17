using System;
using Mshtml;
using System.Windows.Forms;

namespace Orpheus.Plugin.InternetExplorer
{
	/// <summary>
	/// OrpheusScriptingObject extandes Scripting object and add new method testObject(domobject) whic is intented
	/// to be exposed as a part of srcipting interface.
	/// </summary>
    /// <author>kr00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
	public class OrpheusScriptingObject : ScriptingObject
	{
        /// <summary>
        /// The main client logic instance.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// Constructor. Calls the base one.
        /// </summary>
        /// <param name="context">The client logic object used to handle requests.</param>
		public OrpheusScriptingObject(MsieClientLogic context) : base(context)
		{
            this.context = context;
		}

        /// <summary>
        /// This method is exposed to JavaScript. It's main  task is to take given DOM element and
        /// check if it contain expected key.
        /// </summary>
        /// <param name="el">The DOM element to test on.</param>
        public virtual void TestObject(IHTMLElement el) 
        {
            // create the delegate
            TestObjectResult del = new TestObjectResult(TestObjectResult);
            // the current location of the test object
            string url = ((IHTMLDocument2) el.document).url;
            ExtensionEventArgs args = new ExtensionEventArgs("TestObject", context, new object[] {el, url, del});
            ExtensionEventHandlerDelegate[] handlers = context.EventsManager.GetEventHandlers("TestObject");
            for (int i = 0; i < handlers.Length; i++)
            {
                handlers[i](this, args);
            }
        }

        /// <summary>
        /// The callback handler for the test that fails locally. It will show a messeg box to the user with information
        /// that the object is not correct.
        /// </summary>
        /// <param name="value">The result of the test.</param>
        public void TestObjectResult(bool value)
        {
            if (!value) 
            {
                MessageBox.Show("Make sure you've got the right clue and the right page on our domain, " +
                    "and then try again. Good hunting!", "Nope, not quite.", MessageBoxButtons.OK,
                    MessageBoxIcon.Information);
            }
        }

	}
}

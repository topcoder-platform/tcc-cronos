/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * MsieClientLogic.cs
 */

using System;
using System.Windows.Forms;

using TopCoder.Util.BloomFilter;
using TopCoder.Util.ConfigurationManager;
using TopCoder.Util.ObjectFactory;

using MsHtmHstInterop;
using SHDocVw;
using System.Collections;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This class implements the client-side logic for interacting with the web application.
    /// This client logic will be incorporated into an Internet Explorer extension that enables
    /// Internet Explorer to be used as a client. <br />
    ///
    /// This class is decoupled from the Internet Explorer extension mechanism, to give clients
    /// a great level of flexibility when incorporating this logic into an Internet Explorer
    /// extension. <br />
    ///
    /// It is responsible with the creation of the logic objects through the configuration file
    /// and using the Object Factory component.<br />
    ///
    /// It hooks to web browser events in order to invoke component event handlers when a new
    /// page is displayed in the browser. It also starts a timer and invokes event handlers
    /// on regular time intervals. <br />
    ///
    /// This class can be used as a singleton, as well. The reason for using this class as
    /// a singleton is to have the same instance of this class for multiple opened browser
    /// windows. The way this works is as follows: when Internet Explorer is started it looks
    /// in the registry for an extension object and creates it using its GUID. In this case
    /// the extension will be some derived class of <c>ToolBand</c> class. So every web browser
    /// window, regardless how it was opened, will have a different instance of a derived
    /// <c>ToolBand</c> class. In order for web browser windows, opened by the main web browser
    /// like for example using window.open, to have a reference to the same <c>MsieClientLogic</c>
    /// object they must use it as a singleton. <br />
    ///
    /// One more requirement for the clients is to invoke the <c>CustomizeWebBrowser</c> method
    /// whenever a new window is opened and a new extension is created, passing the newly obtain
    /// reference of the web browser, in order to provide the browser with the same customization
    /// like scripting object or custom context menu. <br />
    ///
    /// A sample config file: <br />
    /// &lt;namespace name="Orpheus.Plugin.InternetExplorer"&gt; <br />
    /// &lt;property name="bloom_filter"&gt; <br />
    /// &lt;value&gt;bloom_filter&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="window_navigator"&gt; <br />
    /// &lt;value&gt;window_navigator&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="persistence"&gt; <br />
    /// &lt;value&gt;persistence&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="extension_events_manager"&gt; <br />
    /// &lt;value&gt;extension_events_manager&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="doc_host_ui_handler"&gt; <br />
    /// &lt;value&gt;doc_host_ui_handler&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="scripting_object"&gt; <br />
    /// &lt;value&gt;scripting_object&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;property name="poll_interval"&gt; <br />
    /// &lt;value&gt;1&lt;/value&gt; <br />
    /// &lt;/property&gt; <br />
    /// &lt;/namespace&gt; <br />
    ///
    /// <strong>Thread safety</strong>: This class is thread safe.
    /// Thread safety is achieved by locking on this in the <c>BloomFilter</c> setter and getter.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>arylio</author>
    /// <author>kr00tki</author>
    /// <version>1.1</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public class MsieClientLogic
    {
        /// <summary>
        /// The bloom filter key property name.
        /// </summary>
        private const string PROPERTY_BLOOM_FILTER = "bloom_filter";

        /// <summary>
        /// The window navigator key property name.
        /// </summary>
        private const string PROPERTY_WINDOW_NAVIGATOR = "window_navigator";

        /// <summary>
        /// The persistence key property name.
        /// </summary>
        private const string PROPERTY_PERSISTENCE = "persistence";

        /// <summary>
        /// The events manager key property name.
        /// </summary>
        private const string PROPERTY_EXTENSION_EVENTS_MANAGER = "extension_events_manager";

        /// <summary>
        /// The host ui handler key property name.
        /// </summary>
        private const string PROPERTY_DOC_HOST_UI_HANDLER = "doc_host_ui_handler";

        /// <summary>
        /// The scripting object key property name.
        /// </summary>
        private const string PROPERTY_SCRIPTING_OBJECT = "scripting_object";

        /// <summary>
        /// The poll interval key property name.
        /// </summary>
        private const string PROPERTY_POLL_INTERVAL = "poll_interval";

        /// <summary>
        /// Represents the millisecond in on minute.
        /// </summary>
        private const int MILLISECOND_PER_MINUTE = 60000;

        /// <summary>
        /// Represents the default configuration namespace.
        /// </summary>
        public const string DefaultConfigurationNamepsace = "Orpheus.Plugin.InternetExplorer";

        /// <summary>
        /// Represents the default Object Factory namespace.
        /// </summary>
        public const string DefaultObjectFactoryNamespace = "TopCoder.Util.ObjectFactory";

        /// <summary>
        /// Represents the web browser. It is passed in the constructor.
        /// The web reference is usually obtained in the <c>WebBrowserSite.SetSite</c >method
        /// and should be passed to this class. Note that popup browser windows opened form
        /// the main window browser will have a reference to this same class instance,
        /// while the <c>webBrowser</c> member will still point to the main browser,
        /// when this class is used as a singleton. <br />
        ///
        /// Once set does not change. Can not be null.
        /// </summary>
        private readonly WebBrowserClass webBrowser;

        /// <summary>
        /// The object used to lock in GetInstance.
        /// </summary>
        private readonly static object lockojbect = new object();

        /// <summary>
        /// Represents the Bloom Filter instance.
        /// Set in the constructor either passed as a parameter or created using
        /// a configured key and the Object Factory. Used by event handlers.
        /// Returned and set in its associated property.Can not be null.
        /// </summary>
        private BloomFilter bloomFilter;

        /// <summary>
        /// Represents the timer preset to a configured interval.
        /// Used to fire the polling update event.
        /// Instantiated in the constructor and not changed afterwards. Can not be null.
        /// Returned in its associated property
        /// </summary>
        private readonly Timer updatesPollingTimer;

        /// <summary>
        /// Represents the <c>IPersistence</c> instance.
        /// Set in the constructor either passed as a parameter or created using a configured key
        /// and the Object Factory. Used by event handlers. Returned in its associated property.
        /// Can not be null.
        /// </summary>
        private readonly IPersistence persistence;

        /// <summary>
        /// Represents the <c>IWebBrowserWindowNavigator</c> instance.
        /// Set in the constructor either passed as a parameter or created using a configured key
        /// and the Object Factory. Used by event handlers. Returned in its associated property.
        /// Can not be null.
        /// </summary>
        private readonly IWebBrowserWindowNavigator webBrowserWindowNavigator;

        /// <summary>
        /// Represents the <c>IExtensionEventsManager</c> instance.
        /// Set in the constructor either passed as a parameter or created using a configured key
        /// and the Object Factory. Returned in its associated property.
        /// Can not be null.
        /// </summary>
        private readonly IExtensionEventsManager eventsManager;

        /// <summary>
        /// Represents the <c>IDocHostUIHandler</c> instance.
        /// Set in the constructor either passed as a parameter or created using a configured key
        /// and the Object Factory. Used in the <c>CustomizeWebBrowser</c> method.
        /// Returned in its associated property.Can not be null.
        /// </summary>
        private readonly DefaultDocHostUIHandler browserCustomization;

        /// <summary>
        /// Represents the the scripting object.
        /// Set in the constructor either passed as a parameter or created using a configured key
        /// and the Object Factory. Used in the <c>CustomizeWebBrowser</c> method.
        /// Returned in its associated property.Can not be null.
        /// </summary>
        private readonly object scriptingObject;

        /// <summary>
        /// Represents the list of all browser objects that are active and open.
        /// </summary>
        private readonly IList browserWindows = new ArrayList();

        /// <summary>
        /// Returns or sets the bloom filter.
        /// </summary>
        ///
        /// <value>Represents the bloom filter.</value>
        /// <exception cref="ArgumentNullException">if set to null.</exception>
        public BloomFilter BloomFilter
        {
            get
            {
                lock (bloomFilter)
                {
                    return bloomFilter;
                }
            }
            set
            {
                Validator.ValidateNull(value, "BloomFilter");
                lock (bloomFilter)
                {
                    bloomFilter = value;
                }
            }
        }

        /// <summary>
        /// Returns the update polling timer.
        /// </summary>
        ///
        /// <value>Represents the polling time.</value>
        public Timer UpdatesPollingTimer
        {
            get
            {
                return updatesPollingTimer;
            }
        }

        /// <summary>
        /// Returns the <c>IPersistence</c> used.
        /// </summary>
        ///
        /// <value>Represents the <c>IPersistence</c> used.</value>
        public IPersistence Persistence
        {
            get
            {
                return persistence;
            }
        }

        /// <summary>
        /// Returns the <c>IWebBrowserNavigator</c> used.
        /// </summary>
        ///
        /// <value>Represents the <c>IWebBrowserNavigator</c> used.</value>
        public IWebBrowserWindowNavigator WebBrowserWindowNavigator
        {
            get
            {
                return webBrowserWindowNavigator;
            }
        }

        /// <summary>
        /// Returns the <c>IExtensionEventsManager</c> used.
        /// </summary>
        ///
        /// <value>Represents the <c>IExtensionEventsManager</c> used.</value>
        public IExtensionEventsManager EventsManager
        {
            get
            {
                return eventsManager;
            }
        }

        /// <summary>
        /// Returns the <c>IDocHostUIHandler</c> implementation used.
        /// </summary>
        ///
        /// <value>Represents the <c>IDocHostUIHandler</c> used.</value>
        public IDocHostUIHandler BrowserCustomization
        {
            get
            {
                return browserCustomization;
            }
        }

        /// <summary>
        /// Returns the scripting object.
        /// </summary>
        ///
        /// <value>Represents the scripting object.</value>
        public object ScriptingObject
        {
            get
            {
                return scriptingObject;
            }
        }

        /// <summary>
        /// Returns the web browser.
        /// </summary>
        ///
        /// <value>Represents the web browser.</value>
        public WebBrowserClass WebBrowser
        {
            get
            {
                return webBrowser;
            }
        }

        /// <summary>
        /// Represents the singleton instance of this class.
        /// Set in the <c>GetInstance</c> method with a new instance of this class
        /// and returned afterwards by the <c>GetInstance</c> method.
        /// Can be null until set in <c>GetInstance</c> method.
        /// Does not change once set.
        /// </summary>
        private static MsieClientLogic instance = null;

        /// <summary>
        /// This constructor sets the given web browser to the class field
        /// and creates using the object factory all the required objects.
        /// It then hooks to the <c>DocumentCompleted</c> event of the browser
        /// and the Timer Tick event.
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <exception cref="ConfigurationException">to signal problems with the configuration
        /// file missing properties or if it can not create the objects using the Object
        /// Factory.</exception>
        ///
        /// <param name="webBrowser">The web browser reference obtained in
        /// the <c>WebBrowserSite.SetSite</c> method.</param>
        public MsieClientLogic(WebBrowserClass webBrowser) :
            this (webBrowser, DefaultConfigurationNamepsace, DefaultObjectFactoryNamespace)
        {
        }

        /// <summary>
        /// This constructor creates using the object factory all the required objects.
        /// It then hooks the Timer Tick event.
        /// </summary>
        ///
        /// <exception cref="ConfigurationException">to signal problems with the configuration
        /// file missing properties or if it can not create the objects using the Object
        /// Factory.</exception>
        public MsieClientLogic()
            : this(DefaultConfigurationNamepsace, DefaultObjectFactoryNamespace)
        {
        }


        /// <summary>
        /// This constructor sets the given web browser to the class field
        /// and creates using the object factory all the required objects.
        /// It then hooks to the <c>DocumentCompleted</c> event of the browser
        /// and the Timer Tick event. <br />
        /// </summary>
        ///
        /// <exception cref="ConfigurationException">to signal problems with the configuration file
        /// like missing properties or if it can not create the objects using the Object Factory.
        /// </exception>
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if any parameter is empty string.</exception>
        ///
        /// <param name="webBrowser">The web browser reference obtained in the
        /// <c>WebBrowserSite.SetSite</c>method.</param>
        /// <param name="configurationNamsepace">Custom configuration namespace.</param>
        /// <param name="objectFactoryNamespace">Custom object factory configuration namespace.</param>
        public MsieClientLogic(WebBrowserClass webBrowser,
            string configurationNamsepace, string objectFactoryNamespace)
            : this(configurationNamsepace, objectFactoryNamespace)
        {
            Validator.ValidateNull(webBrowser, "webBrowser");

            // Sets the class field to the parameter value.
            this.webBrowser = webBrowser;

            // Attaches the OnDocumentCompleted event handler to the webBrowser.
            WebBrowser.DocumentComplete += new DWebBrowserEvents2_DocumentCompleteEventHandler(OnDocumentCompleted);
        }


        /// <summary>
        /// This constructor creates using the object factory all the required objects.
        /// It then hooks the Timer Tick event. <br />
        /// </summary>
        ///
        /// <exception cref="ConfigurationException">to signal problems with the configuration file
        /// like missing properties or if it can not create the objects using the Object Factory.
        /// </exception>
        /// <exception cref="ArgumentNullException">if any parameter is null.</exception>
        /// <exception cref="ArgumentException">if any parameter is empty string.</exception>
        ///
        /// <param name="configurationNamsepace">Custom configuration namespace.</param>
        /// <param name="objectFactoryNamespace">Custom object factory configuration namespace.</param>
        public MsieClientLogic(string configurationNamsepace, string objectFactoryNamespace)
        {
            Validator.ValidateNullOrEmptyString(configurationNamsepace, "configurationNamsepace");
            Validator.ValidateNullOrEmptyString(objectFactoryNamespace, "objectFactoryNamespace");

            // get config manager
            ConfigManager cm = ConfigManager.GetInstance();

            // create object factory by the given namespace
            ObjectFactory factory = null;
            try
            {
                factory = ObjectFactory.GetDefaultObjectFactory(objectFactoryNamespace);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create object factory by {0}", objectFactoryNamespace), e);
            }

            // Reads the "window_navigator" configuration property and passes the value
            // to the Object Factory to create the required instance.
            string value = cm.GetValue(configurationNamsepace, PROPERTY_WINDOW_NAVIGATOR);
            try
            {
                webBrowserWindowNavigator = (IWebBrowserWindowNavigator)factory.CreateDefinedObject(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create window navigator by {0}", value), e);
            }

            // Reads the "bloom_filter" configuration property and passes the value
            // to the Object Factory to create the required instance.
            value = cm.GetValue(configurationNamsepace, PROPERTY_BLOOM_FILTER);
            try
            {
                bloomFilter = (BloomFilter)factory.CreateDefinedObject(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create bloom filter by {0}", value), e);
            }

            // Reads the "persistence" configuration property and passes the value to
            // the Object Factory to create the required instance.
            value = cm.GetValue(configurationNamsepace, PROPERTY_PERSISTENCE);
            try
            {
                persistence = (IPersistence)factory.CreateDefinedObject(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create persistence by {0}", value), e);
            }

            // Reads the "extension_events_manager" configuration property
            // and passes the value to the Object Factory to create the required instance.
            value = cm.GetValue(configurationNamsepace, PROPERTY_EXTENSION_EVENTS_MANAGER);
            try
            {
                eventsManager = (IExtensionEventsManager)factory.CreateDefinedObject(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create extension events manager by {0}", value), e);
            }

            // Reads the "doc_host_ui_handler" configuration property and passes the value
            // and a reference to "this" to the Object Factory to create the required instance.
            value = cm.GetValue(configurationNamsepace, PROPERTY_DOC_HOST_UI_HANDLER);
            try
            {
                browserCustomization = (DefaultDocHostUIHandler)factory.CreateDefinedObject(value,
                    new MsieClientLogic[] { this });
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create doc host ui handler by {0}", value), e);
            }

            // Reads the "scripting_object" configuration property and passes the value and
            // a reference to "this" to the Object Factory to create the required instance.
            value = cm.GetValue(configurationNamsepace, PROPERTY_SCRIPTING_OBJECT);
            try
            {
                //scriptingObject = (ScriptingObject)factory.CreateDefinedObject(value,
                //    new MsieClientLogic[] {this});
                scriptingObject = factory.CreateDefinedObject(value, new MsieClientLogic[] { this });
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to create scripting object by {0}", value), e);
            }

            // Instantiates the Timer, sets the interval to the configured "poll_interval" value
            // and attaches the OnUpdatesPolling handler method to the Tick event.
            updatesPollingTimer = new Timer();
            InitUpdatesPollingTimer(cm.GetValue(configurationNamsepace, PROPERTY_POLL_INTERVAL));
        }

        /// <summary>
        /// This constructor sets the fields to the given parameters.
        /// It then hooks to the <c>DocumentCompleted</c> event of
        /// the browser and the Timer Tick event.
        /// </summary>
        ///
        /// <exception cref="ConfigurationException">to signal problems with
        /// the configuration file like missing properties.</exception>
        /// <exception cref="ArgumentNullException"> if any parameter is null.</exception>
        ///
        /// <param name="webBrowser">The web browser reference obtained in the
        /// <c>WebBrowserSite.SetSite</c>method.</param>
        /// <param name="bloomFilter">The bloom filter instance to use.</param>
        /// <param name="persistence">The <c>IPersistence</c> instance to use.</param>
        /// <param name="webBrowserWindowNavigator">The <c>IWebBrowserNavigator</c> instance to use.</param>
        /// <param name="eventsManager">The <c>IExtensionEventsManager</c> to use.</param>
        /// <param name="browserCustomization">The <c>IDocHostUIHandler</c> instance to use.</param>
        /// <param name="scriptingObject">The scripting object to use.</param>
        public MsieClientLogic(WebBrowserClass webBrowser, BloomFilter bloomFilter,
            IPersistence persistence, IWebBrowserWindowNavigator webBrowserWindowNavigator,
            IExtensionEventsManager eventsManager, IDocHostUIHandler browserCustomization, object scriptingObject)
        {
            Validator.ValidateNull(webBrowser, "webBrowser");
            Validator.ValidateNull(bloomFilter, "bloomFilter");
            Validator.ValidateNull(persistence, "persistence");
            Validator.ValidateNull(webBrowserWindowNavigator, "webBrowserWindowNavigator");
            Validator.ValidateNull(eventsManager, "eventsManager");
            Validator.ValidateNull(browserCustomization, "browserCustomization");
            Validator.ValidateNull(scriptingObject, "scriptingObject");

            this.webBrowser = webBrowser;
            this.bloomFilter = bloomFilter;
            this.persistence = persistence;
            this.webBrowserWindowNavigator = webBrowserWindowNavigator;
            this.eventsManager = eventsManager;
            this.browserCustomization = (DefaultDocHostUIHandler)browserCustomization;
            this.scriptingObject = scriptingObject;

            // Attaches the OnDocumentCompleted event handler to the webBrowser
            //WebBrowser.DocumentComplete +=new DWebBrowserEvents2_DocumentCompleteEventHandler(OnDocumentCompleted);

            // Instantiates the Timer, sets the interval to the configured value and attaches the handler method.
            updatesPollingTimer = new Timer();
            InitUpdatesPollingTimer(ConfigManager.GetInstance().GetValue(
                DefaultConfigurationNamepsace, PROPERTY_POLL_INTERVAL));
        }

        /// <summary>
        /// Parse the value into int, and instantiates the updates polling Timer,
        /// sets the interval to it and attaches the OnUpdatesPolling handler method to the Tick event.
        /// </summary>
        ///
        /// <param name="value">the property value of time_interval</param>
        ///
        /// <exception cref="ConfigurationException">if value is invalid</exception>
        private void InitUpdatesPollingTimer(string value)
        {
            int interval = 0;
            try
            {
                interval = int.Parse(value);
            }
            catch (Exception e)
            {
                throw new ConfigurationException(
                    string.Format("Failed to parse the poll interval -- {0}", value), e);
            }
            if (interval <= 0)
            {
                throw new ConfigurationException("The time interval should be positive");
            }

            updatesPollingTimer.Interval = MILLISECOND_PER_MINUTE * interval;
            updatesPollingTimer.Tick += new EventHandler(OnUpdatesPolling);
            updatesPollingTimer.Start();
        }

        /// <summary>
        /// This event handler method is invoked by the host web browser when a new page is displayed.
        /// As a result this method will fire a corresponding event (PageChanged).
        ///
        /// <exception cref="FireEventException">
        /// propagated from the <c>IExtensionEventsManager</c></exception>
        ///
        /// <param name="pDisp">Pointer to the IDispatch interface of
        /// the window or frame in which the document has loaded</param>
        /// <param name="url">Pointer to a VARIANT structure of type
        /// VT_BSTR that specifies the URL, Universal Naming Convention (UNC) file name,
        /// or pointer to an item identifier list (PIDL) of the loaded document.</param>
        /// </summary>
        public virtual void OnDocumentCompleted(object pDisp, ref object url)
        {
            // Gets from the factory all the handlers for eventName
            ExtensionEventHandlerDelegate[] handlers = eventsManager.GetEventHandlers(Helper.EVENT_PAGE_CHANGED);

            // creates a new ExtensionEventArgs class and invokes the handlers.
            ExtensionEventArgs args = new ExtensionEventArgs(Helper.EVENT_PAGE_CHANGED, this, new object[] { url });
            for (int i = 0; i < handlers.Length; i++)
            {
                handlers[i](this, args);
            }
        }

        /// <summary>
        ///  This event handler is invoked by the timer when the Tick event is fired.
        /// As a result this method will fire a corresponding event (PollUpdates).
        /// </summary>
        ///
        /// <exception cref="FireEventException">propagated from the
        /// <c>IExtensionEventsManager</c></exception>
        ///
        /// <param name="sender">The tick events sender</param>
        /// <param name="args">Tick event args.</param>
        public void OnUpdatesPolling(object sender, EventArgs args)
        {
            FireEvent(Helper.EVENT_POLL_UPDATES);
        }

        /// <summary>
        /// This helper method get handlers for the given event name.
        /// and invoke the handlers to the ents.
        /// </summary>
        ///
        /// <exception cref="FireEventException">propagated from the
        /// <c>IExtensionEventsManager</c></exception>
        ///
        /// <param name="eventName">the event name</param>
        private void FireEvent(String eventName)
        {
            // Gets from the factory all the handlers for eventName
            ExtensionEventHandlerDelegate[] handlers = eventsManager.GetEventHandlers(eventName);

            // creates a new ExtensionEventArgs class and invokes the handlers.
            ExtensionEventArgs args = new ExtensionEventArgs(eventName, this);
            for (int i = 0; i < handlers.Length; i++)
            {
                handlers[i](this, args);
            }
        }

        /// <summary>
        /// This method is intended to be used by client code.
        /// When the client logic (this class) is incorporated inside an Internet Explorer extension,
        /// the web browser will also require customization like providing a scripting object
        /// and changing the context menu. <br />
        ///
        /// On initialization, MSHTML calls <c>QueryInterface</c> on the host's client site,
        /// requesting an <c>IDocHostUIHandler</c> interface. If available, MSHTML will call
        /// <c>IDocHostUIHandler</c>methods at appropriate times during the lifetime of
        /// the MSHTML component. <br />
        ///
        /// This method provides the <c>IDocHostUIHandler</c>> implementation to the browser.
        /// The default implementation provided only provides the scripting object.
        /// </summary>
        ///
        /// <exception cref="WebBrowserCustomizationException">
        /// if can not set the the customization object.</exception>
        /// <exception cref="ArgumentNullException"> if parameter is null.</exception>
        ///
        /// <param name="webBrowser">The web browser to customize with the class
        /// <c>IDocHostUIHandler</c> member.</param>
        public void CustomizeWebBrowser(WebBrowserClass webBrowser)
        {
            Validator.ValidateNull(webBrowser, "webBrowser");
            try
            {
                ICustomDoc customDoc = (ICustomDoc)webBrowser.Document;
                customDoc.SetUIHandler(browserCustomization);
            }
            catch (Exception e)
            {
                throw new WebBrowserCustomizationException("Failed to customize the browser.", e);
            }
        }

        /// <summary>
        /// This method returns a singleton instance of this class.
        /// It creates the singleton using the given parameter.
        /// This method also locks on instance when checking if null and creating the instance
        /// </summary>
        ///
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        /// <returns>The singleton instance.</returns>
        public static MsieClientLogic GetInstance()
        {
            lock (lockojbect)
            {
                if (null == instance)
                {
                    instance = new MsieClientLogic();
                }
                return instance;
            }
        }

        /// <summary>
        /// This methods add the given browser object to internal list and also adds the DocumentCompleteEventHandler
        /// to the browser object.
        /// </summary>
        /// <param name="browser">The browser object</param>
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        public void AddBrowser(WebBrowserClass browser)
        {
            Validator.ValidateNull(browser, "browser");
            if (!browserWindows.Contains(browser))
            {
                browserWindows.Add(browser);
                browser.DocumentComplete += new DWebBrowserEvents2_DocumentCompleteEventHandler(OnDocumentCompleted);
            }
        }

        /// <summary>
        /// Removes the browser from interna list.
        /// </summary>
        /// <param name="browser">The browser object.</param>
        /// <exception cref="ArgumentNullException">if parameter is null.</exception>
        public void RemoveBrowser(WebBrowserClass browser)
        {
            Validator.ValidateNull(browser, "browser");
            if (browserWindows.Contains(browser))
            {
                browserWindows.Remove(browser);
            }
        }
    }
}

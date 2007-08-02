using System;
using System.Collections;
using System.ComponentModel;
using System.Configuration;
using System.Drawing;
using System.Data;
using System.Runtime.InteropServices;
using System.Threading;
using System.Windows.Forms;
using Microsoft.Win32;
using Orpheus.Plugin.InternetExplorer;

using TopCoder.Util.ConfigurationManager;

using System.Net;
using MsHtmHstInterop;
using Mshtml;
using SHDocVw;

using MSXML2;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// The main class of the Orpheus Plug-in.
    /// </summary>
    /// <author>kr00tki</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [ComVisible(true)]
    [ExtensionAttribute("Orpheus Plugin")]
    [Guid("50FF0AAC-8874-46e0-B27F-8008984AC4A8")]
    public class OrpheusToolbar : ToolBand
    {
        /// <summary>
        /// The toolbar configuration namespace.
        /// </summary>
        private const string CONF_NAMESPACE = "Orpheus.Plugin.Toolbar";

        /// <summary>
        /// The login page path configuration key.
        /// </summary>
        private const string LOGIN_URL_KEY = "login_url";
        
        /// <summary>
        /// The server contex configuration key.
        /// </summary>
        private const string CONTEXT_KEY = "context";
        
        /// <summary>
        /// The logout page path configuration key.
        /// </summary>
        private const string LOGOUT_URL_KEY = "logout_url";
        
        /// <summary>
        /// The leaderboard page path configuration key.
        /// </summary>
        private const string LEADERBOARD_URL_KEY = "leaderboard_url";
        
        /// <summary>
        /// The games page path configuration key.
        /// </summary>
        private const string GAMES_URL_KEY = "games_url";
        
        /// <summary>
        /// The unlocked sites page path configuration key.
        /// </summary>
        private const string UNLOCKED_URL_KEY = "unlocked_url";
        
        /// <summary>
        /// The name of "test domain" event.
        /// </summary>
        private const string TEST_DOMAIN_EVENT_NAME = "TestDomain";
        
        /// <summary>
        /// The upcoming sites page path configuration key.
        /// </summary>
        private const string UPCOMING_URL_KEY = "upcoming_url";

        /// <summary>
        /// The static initializer. It should load COnfigManager config before it first use.
        /// </summary>
        static OrpheusToolbar()
        {
            ConfigurationLoad();
        }

        #region Controls
        private System.Windows.Forms.PictureBox logoImg;
        private System.Windows.Forms.Button btnLogout;
        private System.Windows.Forms.Button btnLogin;
        private System.Windows.Forms.Button btnGames;
        private System.Windows.Forms.Button btnUnlocked;
        private System.Windows.Forms.Button btnUpcoming;
        private System.Windows.Forms.Button btnLeaderboard;
        private System.Windows.Forms.Panel gamePanel;
        private System.Windows.Forms.Button btnTestDomain;
        private System.Windows.Forms.PictureBox correctPageImage;
        private System.Windows.Forms.PictureBox incorrectPageImage;
        private System.Windows.Forms.ToolTip toolTip;
        private System.ComponentModel.IContainer components;
        #endregion

        /// <summary>
        /// Internal flag indicating if user is logged in or not.
        /// </summary>
        private static bool isLoggedIn = false;

        /// <summary>
        /// The full URL to the login page.
        /// </summary>
        private readonly string loginURL;
        
        /// <summary>
        /// The full URL to the logout page.
        /// </summary>
        private readonly string logoutURL;
        
        /// <summary>
        /// The full URL to the leaderboard page.
        /// </summary>
        private readonly string leaderboardURL;
        
        /// <summary>
        /// The full URL to the games page.
        /// </summary>
        private readonly string gamesURL;
        
        /// <summary>
        /// The full URL to the unlocked sites page.
        /// </summary>
        private readonly string unlockedURL;
        
        /// <summary>
        /// The full URL to the upcoming sites page.
        /// </summary>
        private readonly string upcomingURL;

        /// <summary>
        /// The login/logout events handler delegate. Initialized in the constructor.
        /// </summary>
        private ExtensionEventHandlerDelegate loginDelegate = null;

        /// <summary>
        /// The game changed event handler delegate. Initialized in the constructor.
        /// </summary>
        private ExtensionEventHandlerDelegate gameChangedDelegate = null;

        /// <summary>
        /// The CORRECT_PAGE_LOADED event handler delegate.
        /// </summary>
        private ExtensionEventHandlerDelegate correctPageLoadedDelegate = null;

        /// <summary>
        /// The INCORRECT_PAGE_LOADED event handler delegate.
        /// </summary>
        private ExtensionEventHandlerDelegate incorrectPageLoadedDelegate = null;

        /// <summary>
        /// The NEW_TARGET_SET event handler delegate.
        /// </summary>
        private ExtensionEventHandlerDelegate newTargetSetDelegate = null;

        /// <summary>
        /// The full URL of the loaded page.
        /// </summary>
        private string currentPageURL = null;
        
        /// <summary>
        /// The MsieClientLogic instance used by the toolbar.
        /// </summary>
        private MsieClientLogic clientLogic = null;
        
        /// <summary>
        /// This static property indicates if the user is logged to server or not.
        /// </summary>
        /// <value>true - if the user is loggedin; false otherwise</value>
        public static bool IsLoggedIn 
        {
            get
            {
                return isLoggedIn;
            }
        }

        /// <summary>
        /// Creates the Orpheus Toolbar instance. It reads the buttons URLs from the configuration.
        /// </summary>
        public OrpheusToolbar()
        {
            try 
            {
                // This call is required by the Windows.Forms Form Designer.
                InitializeComponent();

                // read the context
                ConfigManager cm = ConfigManager.GetInstance();
                string host = cm.GetValue(CONF_NAMESPACE, CONTEXT_KEY);

                // load the urls for each button
                loginURL = host + cm.GetValue(CONF_NAMESPACE, LOGIN_URL_KEY);
                logoutURL = host + cm.GetValue(CONF_NAMESPACE, LOGOUT_URL_KEY);
                leaderboardURL = host + cm.GetValue(CONF_NAMESPACE, LEADERBOARD_URL_KEY);
                gamesURL = host + cm.GetValue(CONF_NAMESPACE, GAMES_URL_KEY);
                unlockedURL = host + cm.GetValue(CONF_NAMESPACE, UNLOCKED_URL_KEY);
                upcomingURL = host + cm.GetValue(CONF_NAMESPACE, UPCOMING_URL_KEY);

                clientLogic = MsieClientLogic.GetInstance();
                LoginStatusChangeImpl(isLoggedIn);

                loginDelegate = new ExtensionEventHandlerDelegate(LoginLogoutEventHandler);
                gameChangedDelegate = new ExtensionEventHandlerDelegate(GameChangedEventHandler);
                clientLogic.EventsManager.AddEventHandler(Helper.EVENT_LOGGEDIN, loginDelegate);
                clientLogic.EventsManager.AddEventHandler(Helper.EVENT_LOGGEDOUT, loginDelegate);
                clientLogic.EventsManager.AddEventHandler(Helper.EVENT_GAME_CHANGED, gameChangedDelegate);

                correctPageLoadedDelegate = new ExtensionEventHandlerDelegate(CorrectPageEventHandler);
                incorrectPageLoadedDelegate = new ExtensionEventHandlerDelegate(IncorrectPageEventHandler);
                newTargetSetDelegate = new ExtensionEventHandlerDelegate(NewTargetSetEventHandler);

                clientLogic.EventsManager.AddEventHandler(Helper.EVENT_CORRECT_PAGE_LOADED, correctPageLoadedDelegate);
                clientLogic.EventsManager.AddEventHandler(Helper.EVENT_INCORRECT_PAGE_LOADED, 
                    incorrectPageLoadedDelegate);
                clientLogic.EventsManager.AddEventHandler(Helper.EVENT_NEW_TARGET_SET,
                    newTargetSetDelegate);
                
            }
            catch (Exception ex) 
            {
                ErrorHandler.Instance.ReportError(ex, "Constructor error.");
            }
        }

        /// <summary>
        /// Overrides the SetSite method. It calls the base one, initializes the client logic for site.
        /// </summary>
        /// <param name="pUnkSite">pointer to the</param>
        public override void SetSite(object pUnkSite) 
        {
            try 
            {
                base.SetSite(pUnkSite);
                //clientLogic.AddBrowser(Host);
                Host.DocumentComplete += new SHDocVw.DWebBrowserEvents2_DocumentCompleteEventHandler(Host_DocumentComplete);
            } 
            catch (Exception ex) 
            {
                ErrorHandler.Instance.ReportError(ex, "Error occurs while setting object.");
            }
        }

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        protected override void Dispose( bool disposing )
        {
            if( disposing )
            {
                if( components != null ) 
                {
                    components.Dispose();
                }
                
            }
            
            base.Dispose( disposing );
            clientLogic.RemoveBrowser(Host);
            clientLogic.EventsManager.RemoveEventHandler(Helper.EVENT_LOGGEDIN, loginDelegate);
            clientLogic.EventsManager.RemoveEventHandler(Helper.EVENT_LOGGEDOUT, loginDelegate);
            clientLogic.EventsManager.RemoveEventHandler(Helper.EVENT_GAME_CHANGED, gameChangedDelegate);
            clientLogic.EventsManager.RemoveEventHandler(Helper.EVENT_CORRECT_PAGE_LOADED, correctPageLoadedDelegate);
            clientLogic.EventsManager.RemoveEventHandler(Helper.EVENT_INCORRECT_PAGE_LOADED, incorrectPageLoadedDelegate);
            clientLogic.EventsManager.RemoveEventHandler(Helper.EVENT_NEW_TARGET_SET, newTargetSetDelegate);
        }

        #region Component Designer generated code
        /// <summary>
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(OrpheusToolbar));
            this.logoImg = new System.Windows.Forms.PictureBox();
            this.btnLogout = new System.Windows.Forms.Button();
            this.btnLogin = new System.Windows.Forms.Button();
            this.gamePanel = new System.Windows.Forms.Panel();
            this.btnTestDomain = new System.Windows.Forms.Button();
            this.btnLeaderboard = new System.Windows.Forms.Button();
            this.btnUpcoming = new System.Windows.Forms.Button();
            this.btnUnlocked = new System.Windows.Forms.Button();
            this.btnGames = new System.Windows.Forms.Button();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.correctPageImage = new System.Windows.Forms.PictureBox();
            this.incorrectPageImage = new System.Windows.Forms.PictureBox();
            this.gamePanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // logoImg
            // 
            this.logoImg.Image = ((System.Drawing.Image)(resources.GetObject("logoImg.Image")));
            this.logoImg.Location = new System.Drawing.Point(0, 0);
            this.logoImg.Name = "logoImg";
            this.logoImg.Size = new System.Drawing.Size(100, 27);
            this.logoImg.TabIndex = 0;
            this.logoImg.TabStop = false;
            // 
            // btnLogout
            // 
            this.btnLogout.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnLogout.BackColor = System.Drawing.Color.Transparent;
            this.btnLogout.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnLogout.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel, ((System.Byte)(238)));
            this.btnLogout.Image = ((System.Drawing.Image)(resources.GetObject("btnLogout.Image")));
            this.btnLogout.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnLogout.Location = new System.Drawing.Point(752, 0);
            this.btnLogout.Name = "btnLogout";
            this.btnLogout.Size = new System.Drawing.Size(64, 23);
            this.btnLogout.TabIndex = 5;
            this.btnLogout.TabStop = false;
            this.btnLogout.Text = "Logout";
            this.btnLogout.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnLogout.Visible = false;
            this.btnLogout.Click += new System.EventHandler(this.LogoutClick);
            // 
            // btnLogin
            // 
            this.btnLogin.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnLogin.BackColor = System.Drawing.Color.Transparent;
            this.btnLogin.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnLogin.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel, ((System.Byte)(238)));
            this.btnLogin.Image = ((System.Drawing.Image)(resources.GetObject("btnLogin.Image")));
            this.btnLogin.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnLogin.Location = new System.Drawing.Point(752, 0);
            this.btnLogin.Name = "btnLogin";
            this.btnLogin.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.btnLogin.Size = new System.Drawing.Size(64, 23);
            this.btnLogin.TabIndex = 6;
            this.btnLogin.TabStop = false;
            this.btnLogin.Text = "Login";
            this.btnLogin.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.toolTip.SetToolTip(this.btnLogin, "Dupa");
            this.btnLogin.Click += new System.EventHandler(this.LoingClick);
            // 
            // gamePanel
            // 
            this.gamePanel.BackColor = System.Drawing.Color.Transparent;
            this.gamePanel.Controls.Add(this.incorrectPageImage);
            this.gamePanel.Controls.Add(this.correctPageImage);
            this.gamePanel.Controls.Add(this.btnTestDomain);
            this.gamePanel.Controls.Add(this.btnLeaderboard);
            this.gamePanel.Controls.Add(this.btnUpcoming);
            this.gamePanel.Controls.Add(this.btnUnlocked);
            this.gamePanel.Controls.Add(this.btnGames);
            this.gamePanel.Location = new System.Drawing.Point(104, 0);
            this.gamePanel.Name = "gamePanel";
            this.gamePanel.Size = new System.Drawing.Size(575, 24);
            this.gamePanel.TabIndex = 7;
            this.gamePanel.Visible = false;
            // 
            // btnTestDomain
            // 
            this.btnTestDomain.BackColor = System.Drawing.Color.Transparent;
            this.btnTestDomain.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnTestDomain.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel, ((System.Byte)(238)));
            this.btnTestDomain.Image = ((System.Drawing.Image)(resources.GetObject("btnTestDomain.Image")));
            this.btnTestDomain.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnTestDomain.Location = new System.Drawing.Point(304, 0);
            this.btnTestDomain.Name = "btnTestDomain";
            this.btnTestDomain.Size = new System.Drawing.Size(104, 23);
            this.btnTestDomain.TabIndex = 6;
            this.btnTestDomain.TabStop = false;
            this.btnTestDomain.Text = "Domain Games";
            this.btnTestDomain.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnTestDomain.Click += new System.EventHandler(this.TestDomainClick);
            // 
            // btnLeaderboard
            // 
            this.btnLeaderboard.BackColor = System.Drawing.Color.Transparent;
            this.btnLeaderboard.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnLeaderboard.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel, ((System.Byte)(238)));
            this.btnLeaderboard.Image = ((System.Drawing.Image)(resources.GetObject("btnLeaderboard.Image")));
            this.btnLeaderboard.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnLeaderboard.Location = new System.Drawing.Point(416, 0);
            this.btnLeaderboard.Name = "btnLeaderboard";
            this.btnLeaderboard.Size = new System.Drawing.Size(96, 23);
            this.btnLeaderboard.TabIndex = 5;
            this.btnLeaderboard.TabStop = false;
            this.btnLeaderboard.Text = "Leaderboard";
            this.btnLeaderboard.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnLeaderboard.Click += new System.EventHandler(this.LeaderboardClick);
            // 
            // btnUpcoming
            // 
            this.btnUpcoming.BackColor = System.Drawing.Color.Transparent;
            this.btnUpcoming.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnUpcoming.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel, ((System.Byte)(238)));
            this.btnUpcoming.Image = ((System.Drawing.Image)(resources.GetObject("btnUpcoming.Image")));
            this.btnUpcoming.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnUpcoming.Location = new System.Drawing.Point(184, 0);
            this.btnUpcoming.Name = "btnUpcoming";
            this.btnUpcoming.Size = new System.Drawing.Size(112, 23);
            this.btnUpcoming.TabIndex = 4;
            this.btnUpcoming.TabStop = false;
            this.btnUpcoming.Text = "Upcoming Sites";
            this.btnUpcoming.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnUpcoming.Click += new System.EventHandler(this.UpcomingClick);
            // 
            // btnUnlocked
            // 
            this.btnUnlocked.BackColor = System.Drawing.Color.Transparent;
            this.btnUnlocked.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnUnlocked.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel, ((System.Byte)(238)));
            this.btnUnlocked.Image = ((System.Drawing.Image)(resources.GetObject("btnUnlocked.Image")));
            this.btnUnlocked.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnUnlocked.Location = new System.Drawing.Point(72, 0);
            this.btnUnlocked.Name = "btnUnlocked";
            this.btnUnlocked.Size = new System.Drawing.Size(104, 23);
            this.btnUnlocked.TabIndex = 3;
            this.btnUnlocked.TabStop = false;
            this.btnUnlocked.Text = "Unlocked Sites";
            this.btnUnlocked.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnUnlocked.Click += new System.EventHandler(this.UnlockedClick);
            // 
            // btnGames
            // 
            this.btnGames.BackColor = System.Drawing.Color.Transparent;
            this.btnGames.FlatStyle = System.Windows.Forms.FlatStyle.Popup;
            this.btnGames.Font = new System.Drawing.Font("Microsoft Sans Serif", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel, ((System.Byte)(238)));
            this.btnGames.ForeColor = System.Drawing.Color.Black;
            this.btnGames.Image = ((System.Drawing.Image)(resources.GetObject("btnGames.Image")));
            this.btnGames.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnGames.Location = new System.Drawing.Point(0, 0);
            this.btnGames.Name = "btnGames";
            this.btnGames.Size = new System.Drawing.Size(64, 23);
            this.btnGames.TabIndex = 2;
            this.btnGames.TabStop = false;
            this.btnGames.Text = "Games";
            this.btnGames.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnGames.Click += new System.EventHandler(this.GamesClick);
            // 
            // toolTip
            // 
            this.toolTip.ShowAlways = true;
            // 
            // correctPageImage
            // 
            this.correctPageImage.BackColor = System.Drawing.Color.Transparent;
            this.correctPageImage.Image = ((System.Drawing.Image)(resources.GetObject("correctPageImage.Image")));
            this.correctPageImage.Location = new System.Drawing.Point(536, 4);
            this.correctPageImage.Name = "correctPageImage";
            this.correctPageImage.Size = new System.Drawing.Size(16, 16);
            this.correctPageImage.TabIndex = 7;
            this.correctPageImage.TabStop = false;
            this.toolTip.SetToolTip(this.correctPageImage, "This is the right page to look for clue on");
            this.correctPageImage.Visible = false;
            // 
            // incorrectPageImage
            // 
            this.incorrectPageImage.BackColor = System.Drawing.Color.Transparent;
            this.incorrectPageImage.Image = ((System.Drawing.Image)(resources.GetObject("incorrectPageImage.Image")));
            this.incorrectPageImage.Location = new System.Drawing.Point(536, 4);
            this.incorrectPageImage.Name = "incorrectPageImage";
            this.incorrectPageImage.Size = new System.Drawing.Size(16, 16);
            this.incorrectPageImage.TabIndex = 8;
            this.incorrectPageImage.TabStop = false;
            this.toolTip.SetToolTip(this.incorrectPageImage, "This is not the right page to look for clue on");
            // 
            // OrpheusToolbar
            // 
            this.BackColor = System.Drawing.Color.WhiteSmoke;
            this.Controls.Add(this.gamePanel);
            this.Controls.Add(this.btnLogin);
            this.Controls.Add(this.btnLogout);
            this.Controls.Add(this.logoImg);
            this.IntegralSize = new System.Drawing.Size(5, 5);
            this.MaxSize = new System.Drawing.Size(1600, 26);
            this.MinSize = new System.Drawing.Size(620, 24);
            this.Name = "OrpheusToolbar";
            this.Size = new System.Drawing.Size(824, 24);
            this.gamePanel.ResumeLayout(false);
            this.ResumeLayout(false);

        }
        #endregion

        /// <summary>
        /// Loads the configuration. First, it gets the preload file path from the registry and stores it
        /// via reflection in ConfigManager "preload" field. Then it takes the path to the
        /// configuration file and load config.
        /// </summary>
        private static void ConfigurationLoad() 
        {
            try 
            {
                RegistryKey key = Registry.LocalMachine.OpenSubKey(@"Software\TopCoder\Orpheus");
                string path = key.GetValue("preload") as string;

                // invoke static initializer
                ConfigManager cm = null;

                Type type = typeof(ConfigManager);
                System.Reflection.FieldInfo field = type.GetField("preloadFile",
                    System.Reflection.BindingFlags.Static | System.Reflection.BindingFlags.NonPublic);
                field.SetValue(null, path);

                path = key.GetValue("config") as string;
                ConfigManager.GetInstance().LoadFile(path);
            } 
            catch (Exception e) 
            {
                MessageBox.Show("Error while initialization: " + e);
                throw;
            }
        }

        /// <summary>
        /// Handler for DocumentComplete event. It is used to customize the document behavior to be used with the plugin.
        /// </summary>
        /// <param name="pDisp">not used here</param>
        /// <param name="url">not used here</param>
        private void Host_DocumentComplete(object pDisp, ref object url)
        {
            try 
            {
                currentPageURL = url as string;
                clientLogic.CustomizeWebBrowser(Host);
                // fire the OnDocumentCompleted event if the current window is not popup
                if ((url != null) && (!"orpheus_popup".Equals(Host.GetProperty("window_name") + "" ) ))
                {
                    clientLogic.OnDocumentCompleted(pDisp, ref url);
                }
            } 
            catch (Exception ex)
            {
                ErrorHandler.Instance.ReportError(ex, "Browser customization error.");
            }
        }

        /// <summary>
        /// This method will open new pop-up window and load the given url into it.
        /// </summary>
        /// <param name="baseURL">the base url</param>
        /// <param name="parameters">the url parameters</param>
        private void NavigateToSite(string baseURL, params string[] parameters) 
        {
            try 
            {
                if ((clientLogic != null) && (Host != null)) 
                {
                    string url = string.Format(baseURL, parameters);
                    clientLogic.WebBrowserWindowNavigator.Navigate(Host, url, true);
                }
            } 
            catch (Exception ex) 
            {
                ErrorHandler.Instance.ReportError(ex, "Navigation error.");
            }
        }

        /// <summary>
        /// Games button clik action. Redirects to games page.
        /// </summary>
        /// <param name="sender">ignored</param>
        /// <param name="e">ignored</param>
        private void GamesClick(object sender, System.EventArgs e)
        {
            NavigateToSite(gamesURL);
        }

        /// <summary>
        /// Unlocked sites button clik action. Redirects to unlocked sites page.
        /// </summary>
        /// <param name="sender">ignored</param>
        /// <param name="e">ignored</param>
        private void UnlockedClick(object sender, System.EventArgs e)
        {
            NavigateToSite(unlockedURL, clientLogic.Persistence[Helper.KEY_GAME_ID]);
        }

        /// <summary>
        /// Upcoming sites button clik action. Redirects to upcoming sites page.
        /// </summary>
        /// <param name="sender">ignored</param>
        /// <param name="e">ignored</param>
        private void UpcomingClick(object sender, System.EventArgs e)
        {
            NavigateToSite(upcomingURL, clientLogic.Persistence[Helper.KEY_GAME_ID]);
        }

        /// <summary>
        /// Leaderboard button clik action. Redirects to leaderboard page.
        /// </summary>
        /// <param name="sender">ignored</param>
        /// <param name="e">ignored</param>
        private void LeaderboardClick(object sender, System.EventArgs e)
        {
            NavigateToSite(leaderboardURL, clientLogic.Persistence[Helper.KEY_GAME_ID]);
        }

        /// <summary>
        /// Logout button clik action. Redirects to logout page.
        /// </summary>
        /// <param name="sender">ignored</param>
        /// <param name="e">ignored</param>
        private void LogoutClick(object sender, System.EventArgs e)
        {
            NavigateToSite(logoutURL);
        }

        /// <summary>
        /// Login button clik action. Redirects to login page.
        /// </summary>
        /// <param name="sender">ignored</param>
        /// <param name="e">ignored</param>
        private void LoingClick(object sender, System.EventArgs e)
        {
            NavigateToSite(loginURL);
        }

        /// <summary>
        /// Login/logout event handler. It calls appropriate method (using control's Invoke if needed) to
        /// hide/show additional toolbar buttons.
        /// </summary>
        /// <param name="sender">ignored</param>
        /// <param name="args">the event arguments.</param>
        private void LoginLogoutEventHandler(object sender, ExtensionEventArgs args) 
        {
            bool loggedIn = args.EventName == Helper.EVENT_LOGGEDIN;
            if (InvokeRequired) 
            {
                Invoke(new ChangeLogoutStatus(LoginStatusChangeImpl), new object[] {loggedIn});
            } 
            else 
            {
                LoginStatusChangeImpl(loggedIn);
                if (isLoggedIn) 
                {
                    new Thread(new ThreadStart(((ScriptingObject) clientLogic.ScriptingObject).PollMessages)).Start();
                }
            }
        }

        /// <summary>
        /// Working game changed event handler. It enables buttons if active game is set.
        /// </summary>
        /// <param name="sender">ignored.</param>
        /// <param name="args">ignored.</param>
        private void GameChangedEventHandler(object sender, ExtensionEventArgs args) 
        {
            if (InvokeRequired) 
            {
                Invoke(new GameChangedDelegate(ModifyGameButtons));
            } 
            else 
            {
                ModifyGameButtons();
            }
        }

        /// <summary>
        /// This method is responsible for changing <c>visible</c> attribute of the buttons.
        /// </summary>
        /// <param name="loggedIn">indicates login state change.</param>
        private void LoginStatusChangeImpl(bool loggedIn) 
        {
            isLoggedIn = loggedIn;
            btnLogin.Visible = !loggedIn;
            btnLogout.Visible = loggedIn;
            gamePanel.Visible = loggedIn;

            if (loggedIn) 
            {
                ModifyGameButtons();
            } 
        }

        /// <summary>
        /// This method will disable buttons that require active game.
        /// </summary>
        private void ModifyGameButtons() 
        {
            bool enabled = ((ScriptingObject) clientLogic.ScriptingObject).GetWorkingGame() != -1;
            btnLeaderboard.Enabled = enabled;
            btnUnlocked.Enabled = enabled;
            btnUpcoming.Enabled = enabled;
        }

        /// <summary>
        /// On-click event handler. It fires the "TestDomain" event to registred handlers.
        /// </summary>
        /// <param name="sender">event sender</param>
        /// <param name="e">event arguments</param>
        private void TestDomainClick(object sender, System.EventArgs e)
        {
            ExtensionEventArgs args = new ExtensionEventArgs(TEST_DOMAIN_EVENT_NAME, clientLogic, 
                new object[] {Host.LocationURL});
            clientLogic.EventsManager.FireEvent(TEST_DOMAIN_EVENT_NAME, this, args);
        }

        /// <summary>
        /// The CORRECT_PAGE_LOADED event handler. It changes the icon of the image that indicates correct
        /// page with target on it.
        /// </summary>
        /// <param name="sender">the event sender.</param>
        /// <param name="args">the arguments of the event. The first parameter should be the URL
        /// of the correct target site.</param>
        private void CorrectPageEventHandler(object sender, ExtensionEventArgs args) 
        {
            if (currentPageURL.Equals(args.Parameters[0])) 
            {
                if (InvokeRequired)
                {
                    Invoke(new ChangeLogoutStatus(SetPageCorrectIcon), new object[] {true});
                } 
                else 
                {
                    SetPageCorrectIcon(true);
                }
            }
        }

        /// <summary>
        /// The INCORRECT_PAGE_LOADED event handler. It changes the icon of the image that indicates incorrect
        /// page.
        /// </summary>
        /// <param name="sender">the event sender.</param>
        /// <param name="args">the arguments of the event. The first parameter should be the URL
        /// of the correct target site.</param>
        private void IncorrectPageEventHandler(object sender, ExtensionEventArgs args) 
        {
            if (currentPageURL.Equals(args.Parameters[0])) 
            {
                if (InvokeRequired)
                {
                    Invoke(new ChangeLogoutStatus(SetPageCorrectIcon), new object[] {false});
                } 
                else 
                {
                    SetPageCorrectIcon(false);
                }
            }
        }
        
        /// <summary>
        /// The is the handler for the NEW_TARGET_SET event. It will force the check of target page.
        /// </summary>
        /// <param name="sender">ignored</param>
        /// <param name="args">ignored</param>
        private void NewTargetSetEventHandler(object sender, ExtensionEventArgs args) 
        {
            object url = currentPageURL;
            clientLogic.OnDocumentCompleted(null, ref url);
        }

        /// <summary>
        /// This method will set the correct image depending on the <c>isCorrect</c> value.
        /// </summary>
        /// <param name="isCorrect">indicates if the loaded page is correct or not.</param>
        private void SetPageCorrectIcon(bool isCorrect) 
        {
            correctPageImage.Visible = isCorrect;
            incorrectPageImage.Visible = !isCorrect;
        }

        /// <summary>
        /// This delegate is required by <c>Invoke</c> method to call other method.
        /// </summary>
        private delegate void ChangeLogoutStatus(bool loggedIn);
        
        /// <summary>
        /// This delegate is required by <c>Invoke</c> method to call other method.
        /// </summary>
        private delegate void GameChangedDelegate();
    }
}

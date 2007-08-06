pref("extension.orpheus.url.context", "http://www.theball.com");
pref("extension.orpheus.url.login", "/plugin/login.jsp");
pref("extension.orpheus.url.logout", "/server/plugin/logout.do");
pref("extension.orpheus.url.games", "/server/plugin/activeGames.do");
pref("extension.orpheus.url.unlocked", "/server/plugin/unlockedDomains.do?gameId=$0");
pref("extension.orpheus.url.upcoming", "/server/plugin/upcomingDomains.do?gameId=$0");
pref("extension.orpheus.url.leaderboard", "/server/plugin/leaders.do?gameId=$0");

pref("extension.orpheus.url.test_domain", "/server/plugin/testDomain.do?domain=$0");
//pref("extension.orpheus.url.test_domain", "/hasgame.html");
pref("extension.orpheus.url.test_domain_games", "/server/plugin/testedDomainGames.do?domain=$0");
pref("extension.orpheus.url.polling", "/server/plugin/poll.do?timestamp=$0");
//pref("extension.orpheus.url.polling", "/feed.xml");
pref("extension.orpheus.url.test_object", "/server/plugin/testTarget.do?gameId=$0&domain=$1&seq=$2&hash=$3&url=$4");
pref("extension.orpheus.url.brainteasure", "/server/plugin/startGamePlay.do?gameId=$0&domain=$1");

pref("extension.orpheus.poll_interval", 1);
pref("extension.orpheus.debug", true);
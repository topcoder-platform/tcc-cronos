function showDownload(ctx) {
    var userAgent = navigator.userAgent;
    var appName = navigator.appName;
    if (appName == 'Microsoft Internet Explorer') {
        window.location = ctx + '/public/download-ie.jsp';
    } else if (appName == 'Netscape') {
        window.location = ctx + '/public/download-firefox.jsp';
    } else {
        window.location = ctx + '/public/download.jsp';
    }
}

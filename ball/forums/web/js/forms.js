var SUBMITTED_FORM = null;

function submitOnEnter(e, f) {
    var keycode;
    if (window.event) {
        keycode = window.event.keyCode;
    } else if (e) {
        keycode = e.which;
    } else {
        return true;
    }
    if (keycode == 13) {
        if (SUBMITTED_FORM == null) {
            SUBMITTED_FORM = f;
            f.submit();
        }
        return false;
    } else {
        return true;
    }
}

function acceptDigit(e) {
    var keynum;
    var keychar;
    var numcheck;
    if (window.event) {
        keynum = e.keyCode;
    } else if (e.which) {
        keynum = e.which;
    }
    keychar = String.fromCharCode(keynum);
    numcheck = /\d/;
    var a = numcheck.test(keychar);
    a = a || keynum < 32;
    a = a || (keynum == null);
    return a;
}

function submitForm(f) {
    if (SUBMITTED_FORM == null) {
        SUBMITTED_FORM = f;
        f.submit();
    }
}
function submitGameBlocksForm(gameStart) {
    var form = document.GameForm;
    var elems = form.elements;
    var errors = '';
    var warnings = '';

    // buld JSON String for each block
    for (var i = 0; i < BLOCK_COUNT; i++) {
        for (var j = 0; j < elems.length; j++) {
            if (elems[j].id == ('blockInfo' + i)) {
                var v = '{';
                // slot count
                v += '"slotCount" : ' + BLOCK_SLOT_COUNTS[i];
                if (BLOCK_SLOT_COUNTS[i] == 0) {
                    errors += '\nNo slots for block ' + (i + 1) + ' have been set'; 
                }
                // auction start date
                var start = new Date();
                start.setTime(AUCTION_START_TIMES[i]);
                v += ', "auctionStartTime" : "' + (start.getMonth() + 1) + '/' + start.getDate() + '/' + start.getFullYear() + ' '
                                                + start.getHours() + ':' + start.getMinutes() + ':' + start.getSeconds() + '"';
                // auction end date
                var end = new Date();
                end.setTime(AUCTION_END_TIMES[i]);
                v += ', "auctionEndTime" : "' + (end.getMonth() + 1) + '/' + end.getDate() + '/' + end.getFullYear() + ' '
                                                + end.getHours() + ':' + end.getMinutes() + ':' + end.getSeconds() + '"';
                // check if auction end date does not exceed the game start time
                if (end.getTime() >= gameStart.getTime()) {
                    warnings += '\nThe auction end time for block ' + (i + 1) + ' is greater or equal to game start time';
                }
                // block duration
                v += ', "maxBlockTime" : ' + Math.floor(BLOCK_DURATIONS[i] / 60);
                v += '}';
                // Save the JSON string as form input value
                elems[j].value = v;
            }
        }
    }
    if (errors == '') {
        if (warnings != '') {
            warnings += '\n\nDo you still want to create the game?';
            if (confirm(warnings)) {
                submitForm(form);
            }
        } else {
            submitForm(form);
        }
    } else {
        alert(errors);
    }
}

function addSlot(blockNum, ctx) {
    var table = document.getElementById('slotList' + blockNum);
    var slotCount = table.rows.length - 1;
    var tr = table.insertRow(slotCount);

    var td0 = tr.insertCell(0);
    td0.style.width = '20%';
    td0.appendChild(document.createTextNode('Timeslot ' + (slotCount + 1) + ':'));

    var td1 = tr.insertCell(1);
    td1.style.width = '80%';

    var input1 = document.createElement('input');
    input1.id = 'slotHour_' + blockNum + '_' + slotCount;
    input1.name = 'slotHour_' + blockNum + '_' + slotCount;
    input1.className = 'inputBox';
    input1.style.width = '10px';
    input1.setAttribute('maxLength', '2');
    input1.onchange = new Function('recalcMaxDuration(' + blockNum + ');recalcLatestStarts();return true;');
    input1.onkeypress = new Function('return acceptDigit(event);');

    var input2 = document.createElement('input');
    input2.id = 'slotMin_' + blockNum + '_' + slotCount;
    input2.name = 'slotMin_' + blockNum + '_' + slotCount;
    input2.className = 'inputBox';
    input2.style.width = '10px';
    input2.setAttribute('maxLength', '2');
    input2.onchange = new Function('recalcMaxDuration(' + blockNum + ');recalcLatestStarts();return true;');
    input2.onkeypress = new Function('return acceptDigit(event);');

    var input3 = document.createElement('input');
    input3.id = 'slotSec_' + blockNum + '_' + slotCount;
    input3.name = 'slotSec_' + blockNum + '_' + slotCount;
    input3.className = 'inputBox';
    input3.style.width = '10px';
    input3.setAttribute('maxLength', '2');
    input3.onchange = new Function('recalcMaxDuration(' + blockNum + ');recalcLatestStarts();return true;');
    input3.onkeypress = new Function('return acceptDigit(event);');

    var a = document.createElement('a');
    a.title = 'Remove';
    a.href = '#';
    a.onclick = new Function('removeSlot(' + blockNum + ',' + slotCount + ');return false;');

    var img = document.createElement('img');
    img.src = ctx + '/i/b/btn_admin_remove.gif';
    img.style.width = '49px';
    img.style.height = '15px';
    img.className = 'btn';
    img.alt = 'Remove';
    img.style.align = 'absmiddle';

    a.appendChild(img);

    td1.appendChild(input1);
    td1.appendChild(document.createTextNode(' H'));
    td1.appendChild(input2);
    td1.appendChild(document.createTextNode(' M'));
    td1.appendChild(input3);
    td1.appendChild(document.createTextNode(' S'));
    td1.appendChild(document.createTextNode(' '));
    td1.appendChild(a);

    BLOCK_SLOT_COUNTS[blockNum]++;
}

function removeSlot(blockNum, slotNum) {
    var table = document.getElementById('slotList' + blockNum);
    table.deleteRow(slotNum);
    for (var i = 0; i < table.rows.length - 1; i++) {
        table.rows[i].cells[0].removeChild(table.rows[i].cells[0].firstChild);
        table.rows[i].cells[0].appendChild(document.createTextNode('Timeslot ' + (i + 1) + ':'));
        var a = table.rows[i].cells[1].lastChild;
        a.onclick = new Function('removeSlot(' + blockNum + ',' + i + ');return false;');
        // Update IDs for slot count inputs
        var childNodes = table.rows[i].cells[1].childNodes;
        for (var j = 0; j < childNodes.length; j++) {
            if (childNodes[j].name != null) {
                if (childNodes[j].name.substring(0, 9) == 'slotHour_') {
                    childNodes[j].name = 'slotHour_' + blockNum + '_' + i;
                    childNodes[j].id = 'slotHour_' + blockNum + '_' + i;
                } else if (childNodes[j].name.substring(0, 8) == 'slotMin_') {
                    childNodes[j].name = 'slotMin_' + blockNum + '_' + i;
                    childNodes[j].id = 'slotMin_' + blockNum + '_' + i;
                } else if (childNodes[j].name.substring(0, 8) == 'slotSec_') {
                    childNodes[j].name = 'slotSec_' + blockNum + '_' + i;
                    childNodes[j].id = 'slotSec_' + blockNum + '_' + i;
                }
            }
        }
    }
    BLOCK_SLOT_COUNTS[blockNum]--;
    recalcMaxDuration(blockNum);
    recalcLatestStarts();
}

function clickBlock(blockNum) {
    var li = document.getElementById('blockDetails' + blockNum);
    var li2 = document.getElementById('blockHeader' + blockNum);
    if (li != null) {
        if (li.style.display == 'none') {
            li.style.display = 'block';
            li2.className = 'open';
        } else {
            li.style.display = 'none';
            li2.className = 'closed';
        }
    }
}

// Calculates the maximum duration for specified block
function recalcMaxDuration(blockNum) {
    var dur = 0;
    var slotCount = BLOCK_SLOT_COUNTS[blockNum];
    for (var i = 0; i < slotCount; i++) {
        var slotH = document.getElementById("slotHour_" + blockNum + '_' + i);
        var slotM = document.getElementById("slotMin_" + blockNum + '_' + i);
        var slotS = document.getElementById("slotSec_" + blockNum + '_' + i);
        if (slotH.value.length > 0) {
            dur += parseIntValue(slotH.value) * 60 * 60;
        }
        if (slotM.value.length > 0) {
            dur += parseIntValue(slotM.value) * 60;
        }
        if (slotS.value.length > 0) {
            dur += parseIntValue(slotS.value);
        }
    }
    BLOCK_DURATIONS[blockNum] = dur;
    var h = Math.floor(dur / 3600);
    dur = dur % 3600;
    var m = Math.floor(dur / 60);
    var s = dur % 60;
    var span = document.getElementById('maxDuration' + blockNum);
    span.removeChild(span.firstChild);
    span.appendChild(document.createTextNode((h < 10 ? '0' : '') + h + 'h '+ (m < 10 ? '0' : '') + m + 'm ' + (s < 10 ? '0' : '') + s + 's'));
}

function recalcLatestStarts() {
    var prev = new Date();
    prev.setTime(AUCTION_END_TIMES[0]);
    var span = document.getElementById('latestStart0');
    if (span.firstChild) {
        span.removeChild(span.firstChild);
    }
    span.appendChild(document.createTextNode(formatAuctionTime(prev)));
    for (var i = 1; i < BLOCK_COUNT; i++) {
        var last = new Date();
        last.setTime(prev.getTime() + BLOCK_DURATIONS[i - 1] * 1000);
        span = document.getElementById('latestStart' + i);
        if (span.firstChild) {
            span.removeChild(span.firstChild);
        }
        span.appendChild(document.createTextNode(formatAuctionTime(last)));
        prev.setTime(last.getTime())
    }
}

function formatAuctionTime(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    var h = date.getHours();
    var mm = date.getMinutes();
    var s = (m < 10 ? '0' + m : m);
    s += '.';
    s += (d < 10 ? '0' + d : d);
    s += '.';
    s += y;
    s += ' ';
    var ampm = 'AM';
    if (h == 0) {
        h = 12;
    } else if (h == 12) {
        ampm = 'PM';
    } else if (h > 12) {
        h -= 12;
        ampm = 'PM';
    }
    s += (h < 10 ? '0' + h : h);
    s += ':';
    s += (mm < 10 ? '0' + mm : mm);
    s += ' ';
    s += ampm;

    return s;
}

function recalcAuctionStart(blockNum) {
    var form = document.GameForm;
    var d = new Date();
    d.setFullYear(parseIntValue(getValue(form, 'year', blockNum)));
    d.setMonth(parseIntValue(getValue(form, 'month', blockNum)) - 1);
    d.setDate(parseIntValue(getValue(form, 'day', blockNum)));
    var time = getValue(form, 'time', blockNum);
    var p = time.indexOf(':');
    var h = parseIntValue(time.substring(0, p));
    if (getValue(form, 'ampm', blockNum) == 'am') {
        if (h == 12) {
            h = 0;
        }
    } else {
        if (h != 12) {
            h += 12;
        }
    }
    d.setHours(h);
    d.setMinutes(parseIntValue(time.substring(p + 1)));
    d.setSeconds(0);
    d.setMilliseconds(0);
    AUCTION_START_TIMES[blockNum] = d.getTime();
    recalcLatestStarts();
}

function recalcAuctionEnd(blockNum) {
    var form = document.GameForm;
    var d = new Date();
    d.setFullYear(parseIntValue(getValue(form, 'year2', blockNum)));
    d.setMonth(parseIntValue(getValue(form, 'month2', blockNum)) - 1);
    d.setDate(parseIntValue(getValue(form, 'day2', blockNum)));
    var time = getValue(form, 'time2', blockNum);
    var p = time.indexOf(':');
    var h = parseIntValue(time.substring(0, p));
    if (getValue(form, 'ampm2', blockNum) == 'am') {
        if (h == 12) {
            h = 0;
        }
    } else {
        if (h != 12) {
            h += 12;
        }
    }
    d.setHours(h);
    d.setMinutes(parseIntValue(time.substring(p + 1)));
    d.setSeconds(0);
    d.setMilliseconds(0);
    AUCTION_END_TIMES[blockNum] = d.getTime();
    recalcLatestStarts();
}

function getValue(form, prefix, blockNum) {
    var el = form.elements;
    for (var i = 0; i < el.length; i++) {
        if (el[i].name == prefix + blockNum) {
            return el[i].value;
        }
    }
    return null;
}

function parseIntValue(v) {
    if (v.substring(0, 1) == '0') {
        v = v.substring(1);
    }
    return parseInt(v);
}

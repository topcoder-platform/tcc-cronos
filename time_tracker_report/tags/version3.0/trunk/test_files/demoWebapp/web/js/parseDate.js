function getDateString(dateString){
    if (dateString==null || dateString.length == 0)
        return '';
        
    var result = null;
    var returnValue = null;
    
    // mm-dd-yyyy
    var expression = /(\d{1,2})-(\d{1,2})-(\d{4})/;
    if (result = expression.exec(dateString)){
        return checkDateValues(result[2], result[1], result[3]);
    }

    // mmddyyyy
    expression = /(\d{2})(\d{2})(\d{4})/;
    if (result = expression.exec(dateString)){
        return checkDateValues(result[2], result[1], result[3]);
    }

    return 'Invalid';    
}

function checkDateValues(day, month, year){
    var d = new Date(year, month-1, day);
    var returnMonth = ""+(d.getMonth()+1);
    if (d.getMonth()+1 != month || d.getDate()!=day)
        return "Invalid";
    if (d.getMonth()+1 < 10)
        returnMonth = "0"+returnMonth;
    var returnDay = d.getDate();
    if (d.getDate() < 10)
        returnDay = "0"+returnDay;
    var returnYear = d.getFullYear();
    return returnMonth+"-"+returnDay+"-"+returnYear;
}

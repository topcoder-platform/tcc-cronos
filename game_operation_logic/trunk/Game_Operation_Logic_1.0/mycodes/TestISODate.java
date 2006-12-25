import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */

public class TestISODate {

    /**
     *@param args
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException {
//        String dateStr = "2006-10-10T12:33:32,000Z+05:00";
        String dateStr = "2006-10-10T12:33:32,000Z+05:00";
        TestISODate test = new TestISODate();
        System.out.println(test.getDate(dateStr));

    }
    
    private Date getISODate(String str){
        Pattern patter = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z?|(,\\d{3})?(-\\d{2}:\\d{2})?");
        Calendar cal = Calendar.getInstance();
        TimeZone.getDefault();
        TimeZone zone = TimeZone.getTimeZone("GMT-8:10");
       System.out.print(zone);
        return null;
        
    }
    
    private Date getDate2(String str) throws ParseException{
        String pattern ="yyyy-MM-dd'T'HH:mm:ss,SSSZ";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(str);
    }
    
    private Date getDate(String str) throws ParseException{
        StringBuffer pattern = new StringBuffer("yyyy-MM-dd'T'HH:mm:ss");
        String regExp = "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})(,\\d{3})?(Z)?([-|+]\\d{2}:\\d{2})?";
        Pattern datePattern = Pattern.compile(regExp);
        Matcher matcher = datePattern.matcher(str);
        StringBuffer source = new StringBuffer();
        boolean utc = false;
        String timeZoneValue=null;
        if (matcher.matches()){
            source.append(matcher.group(1));
            if (matcher.group(2)!=null){
                pattern.append(",SSS");
                source.append(matcher.group(2));
            }
            if (matcher.group(3)!=null){
                utc = true;
                timeZoneValue = "GMT0:00";
                pattern.append("'Z'");
                source.append(matcher.group(3));
            }
            if (matcher.group(4)!=null){
                pattern.append("Z");
                if (utc){
                    timeZoneValue = "GMT"+matcher.group(4);
                }
                source.append(matcher.group(4));
                source.deleteCharAt(source.length()-3);
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern.toString());
        
        Date date = sdf.parse(source.toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (utc){
            cal.setTimeZone(TimeZone.getTimeZone(timeZoneValue));
        }
               
        return cal.getTime();
    }

}

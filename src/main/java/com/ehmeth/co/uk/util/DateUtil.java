package com.ehmeth.co.uk.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class DateUtil {

    private final static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Util method that returns the appropriate
     * @param day ideally days between 1 and 31 (month days), but it can even take more
     * @return 'st', 'th', 'nd' where appropriate
     */
    public static String getDayNumberSuffix(final int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    /**
     * Util method to return today's date in pretty format e.g 29th Oct 2016, 5:22PM
     * @param date date Object
     * @return String
     */
    public static String getDateInPrettyFormat(final Date date){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy, h:mma");
        final StringBuilder prettyDateFormatBuilder = new StringBuilder();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        final int dayNumber = calendar.get(Calendar.DATE);
        prettyDateFormatBuilder.append(dayNumber).append(getDayNumberSuffix(dayNumber)).append(" ").append(simpleDateFormat.format(date));
        return prettyDateFormatBuilder.toString();
    }

    public static String getFriendlyFormat(final Date date) {
        return dateFormat.format(date);
    }

    public static Date addSecondsToDate(final int seconds, final Date date) {
        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);

        return cal.getTime();
    }

    public static Date getRandomDate(){
        final Random random = new Random();
        final long ms = -946771200000L + (Math.abs(random.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
        return new Date(ms);
    }

    public static Date addDaysToDate(final Date date, final int days) {
        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    public static Date deduceFrom(final Date date, final int days) {
        if (date != null)
            return date;

        Date today = Calendar.getInstance().getTime();
        return  DateUtil.addDaysToDate(today, -days);
    }

    public static Date deduceTo(final Date date) {
        if (date != null)
            return date;

        return Calendar.getInstance().getTime();
    }

    public static SimpleDateFormat ddMMYYYYFormatter() {
        return dateFormatter;
    }

    public static List<String> generateTimeIntervals(Date start, Date end, String precision) {
        int prec;
        switch (precision) {
            case "hour":
                prec = Calendar.HOUR_OF_DAY;
                break;
            case "minute":
                prec = Calendar.MINUTE;
                break;
            case "second":
                prec = Calendar.MILLISECOND;
                break;
            default:
                prec = Calendar.DAY_OF_MONTH;
                break;
        }

        Date startTime = truncateDatePrecision(start, prec);
        Date stopTime = truncateDatePrecision(end, prec);
        List<String> intervals = new ArrayList<>();

        //if its same interval i.e. start=end, return just start
        if (dateFormat.format(startTime).equals(dateFormat.format(stopTime))) {
            intervals.add(dateFormat.format(startTime));
            return intervals;
        }

        //ensure start is before stop
        if (startTime.after(stopTime)) {
            Date temp = stopTime;
            stopTime = startTime;
            startTime = temp;
        }

        Calendar cal = Calendar.getInstance();
        Date updated = startTime;
        while (true) {
            intervals.add(dateFormat.format(updated));
            cal.setTime(updated);

            cal.add(prec, 1);
            updated = cal.getTime();
            if (updated.after(stopTime))
                break;
        }
        return intervals;
    }

    public static Date truncateDatePrecision(Date date, int precision) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);

        switch (precision) {
            case Calendar.DAY_OF_MONTH:
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                break;
            case Calendar.HOUR_OF_DAY:
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                break;
            case Calendar.MINUTE:
                cal.set(Calendar.SECOND, 0);
                break;
        }
        return cal.getTime();

    }
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static LocalDateTime getStartOfDay(final LocalDateTime dateTime) {
        return dateTime.toLocalDate().atStartOfDay();
    }
    public static Date addHoursToDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static LocalDate convert(final Date date){
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}


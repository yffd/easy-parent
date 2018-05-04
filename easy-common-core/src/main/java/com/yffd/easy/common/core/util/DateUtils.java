package com.yffd.easy.common.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @Description  简单描述该类的功能（可选）.
 * @Date		 2017年9月12日 下午2:16:03 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class DateUtils {
    private static Logger logger = Logger.getLogger(DateUtils.class);
    
    public static final String TIME_WITH_MINUTE_PATTERN = "HH:mm";

    public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond

    public final static int LEFT_OPEN_RIGHT_OPEN = 1;
    public final static int LEFT_CLOSE_RIGHT_OPEN = 2;
    public final static int LEFT_OPEN_RIGHT_CLOSE = 3;
    public final static int LEFT_CLOSE_RIGHT_CLOSE = 4;
    /**
     * 比较日期的模式 --只比较日期，不比较时间
     */
    public final static int COMP_MODEL_DATE = 1;
    /**
     * 比较日期的模式 --只比较时间，不比较日期
     */
    public final static int COMP_MODEL_TIME = 2;
    /**
     * 比较日期的模式 --比较日期，也比较时间
     */
    public final static int COMP_MODEL_DATETIME = 3;

    /**
     * 要用到的DATE Format的定义
     */
    public static String DATE_FORMAT_DATEONLY = "yyyy-MM-dd"; // 年/月/日
    public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss"; // 年/月/日/时/分/秒
    public static SimpleDateFormat SDF_DATETIME = new SimpleDateFormat(DateUtils.DATE_FORMAT_DATETIME);
    // Global SimpleDateFormat object
    public static SimpleDateFormat SDF_DATEONLY = new SimpleDateFormat(DateUtils.DATE_FORMAT_DATEONLY);
    public static final SimpleDateFormat SDF_SHORTDATE = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat SDF_SHORT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SDF_LONG_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SDF_HMS = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat SDF_TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 
     * parseDate:根据日期格式字符串解析日期字符串. <br/>
     * @Date	2017年9月12日 下午2:24:37 <br/>
     * @author  zhangST
     * @param str               日期字符串
     * @param parsePatterns     日期格式字符串
     * @return                  解析后日期
     */
    public static Date parseDate(String str, String parsePatterns) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(parsePatterns);
            return sdf.parse(str);
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }
    
    /**
     * 
     * compareDate:根据单位字段比较两个日期. <br/>
     * @Date	2017年9月12日 下午2:25:33 <br/>
     * @author  zhangST
     * @param date          日期1
     * @param otherDate     日期2
     * @param withUnit      单位字段，从Calendar field取值
     * @return              等于返回0值, 大于返回大于0的值 小于返回小于0的值
     */
    public static int compareDate(Date date, Date otherDate, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(otherDate);

        switch (withUnit) {
        case Calendar.YEAR:
            dateCal.clear(Calendar.MONTH);
            otherDateCal.clear(Calendar.MONTH);
        case Calendar.MONTH:
            dateCal.set(Calendar.DATE, 1);
            otherDateCal.set(Calendar.DATE, 1);
        case Calendar.DATE:
            dateCal.set(Calendar.HOUR_OF_DAY, 0);
            otherDateCal.set(Calendar.HOUR_OF_DAY, 0);
        case Calendar.HOUR:
            dateCal.clear(Calendar.MINUTE);
            otherDateCal.clear(Calendar.MINUTE);
        case Calendar.MINUTE:
            dateCal.clear(Calendar.SECOND);
            otherDateCal.clear(Calendar.SECOND);
        case Calendar.SECOND:
            dateCal.clear(Calendar.MILLISECOND);
            otherDateCal.clear(Calendar.MILLISECOND);
        case Calendar.MILLISECOND:
            break;
        default:
            throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
        return dateCal.compareTo(otherDateCal);
    }
    
    /**
     * 
     * compareTime:根据单位字段比较两个时间. <br/>
     * @Date	2017年9月12日 下午2:28:04 <br/>
     * @author  zhangST
     * @param date          时间1
     * @param otherDate     时间2
     * @param withUnit      单位字段，从Calendar field取值
     * @return              等于返回0值, 大于返回大于0的值 小于返回小于0的值
     */
    public static int compareTime(Date date, Date otherDate, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(otherDate);

        dateCal.clear(Calendar.YEAR);
        dateCal.clear(Calendar.MONTH);
        dateCal.set(Calendar.DATE, 1);
        otherDateCal.clear(Calendar.YEAR);
        otherDateCal.clear(Calendar.MONTH);
        otherDateCal.set(Calendar.DATE, 1);
        switch (withUnit) {
        case Calendar.HOUR:
            dateCal.clear(Calendar.MINUTE);
            otherDateCal.clear(Calendar.MINUTE);
        case Calendar.MINUTE:
            dateCal.clear(Calendar.SECOND);
            otherDateCal.clear(Calendar.SECOND);
        case Calendar.SECOND:
            dateCal.clear(Calendar.MILLISECOND);
            otherDateCal.clear(Calendar.MILLISECOND);
        case Calendar.MILLISECOND:
            break;
        default:
            throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
        return dateCal.compareTo(otherDateCal);
    }
    
    /**
     * 
     * nowTimeMillis:获得当前的日期毫秒. <br/>
     * @Date	2017年9月12日 下午2:29:30 <br/>
     * @author  zhangST
     * @return
     */
    public static long nowTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 
     * nowTimeStamp:获得当前的时间戳. <br/>
     * @Date	2017年9月12日 下午2:29:45 <br/>
     * @author  zhangST
     * @return
     */
    public static Timestamp nowTimeStamp() {
        return new Timestamp(nowTimeMillis());
    }
    
    /**
     * 
     * getReqDate:当前日期，日期格式：yyyyMMdd. <br/>
     * @Date	2017年9月12日 下午2:31:03 <br/>
     * @author  zhangST
     * @return
     */
    public static String getReqDate() {
        return SDF_SHORTDATE.format(new Date());
    }

    /**
     * 
     * getReqDate:日期转换字符串，日期格式：yyyy-MM-dd. <br/>
     * @Date	2017年9月12日 下午2:33:21 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static String getReqDate(Date date) {
        return SDF_SHORT_DATE.format(date);
    }

    /**
     * 
     * getReqDateyyyyMMdd:日期转换字符串，日期格式：yyyyMMdd. <br/>
     * @Date	2017年9月12日 下午2:35:46 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static String getReqDateyyyyMMdd(Date date) {
        return SDF_SHORTDATE.format(date);
    }

    /**
     * 
     * TimestampToDateStr:日期转换字符串，日期格式：yyyy-MM-dd. <br/>
     * @Date	2017年9月12日 下午2:37:05 <br/>
     * @author  zhangST
     * @param tmp
     * @return
     */
    public static String TimestampToDateStr(Timestamp tmp) {
        return SDF_SHORT_DATE.format(tmp);
    }
    
    /**
     * 
     * getReqTime:当前时间，时间格式：HH:mm:ss. <br/>
     * @Date	2017年9月12日 下午2:38:25 <br/>
     * @author  zhangST
     * @return
     */
    public static String getReqTime() {
        return SDF_HMS.format(new Date());
    }

    /**
     * 
     * getTimeStampStr:日期转换字符串，日期格式：yyyy-MM-dd HH:mm:ss. <br/>
     * @Date	2017年9月12日 下午2:39:13 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static String getTimeStampStr(Date date) {
        return SDF_LONG_DATE.format(date);
    }

    /**
     * 
     * getLongDateStr:获取当前长日期格式字串，日期格式：yyyy-MM-dd HH:mm:ss. <br/>
     * @Date	2017年9月12日 下午2:40:38 <br/>
     * @author  zhangST
     * @return
     */
    public static String getLongDateStr() {
        return SDF_LONG_DATE.format(new Date());
    }

    /**
     * 
     * getLongDateStr:获取长日期格式字串，日期格式：yyyy-MM-dd HH:mm:ss. <br/>
     * @Date	2017年9月12日 下午2:41:25 <br/>
     * @author  zhangST
     * @param time
     * @return
     */
    public static String getLongDateStr(Timestamp time) {
        return SDF_LONG_DATE.format(time);
    }
    
    /**
     * 
     * getShortDateStr:获得当前短日期格式字串，日期格式：yyyy-MM-dd. <br/>
     * @Date	2017年9月12日 下午2:42:11 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static String getShortDateStr(Date date) {
        return SDF_SHORT_DATE.format(date);
    }

    /**
     * 
     * getShortDateStr:获得短日期格式字串，日期格式：yyyy-MM-dd. <br/>
     * @Date	2017年9月12日 下午2:42:48 <br/>
     * @author  zhangST
     * @return
     */
    public static String getShortDateStr() {
        return SDF_SHORT_DATE.format(new Date());
    }
    
    /**
     * 
     * addSecond:计算 second 秒后的时间. <br/>
     * @Date	2017年9月12日 下午2:43:35 <br/>
     * @author  zhangST
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 
     * addMinute:计算 minute 分钟后的时间. <br/>
     * @Date	2017年9月12日 下午2:43:48 <br/>
     * @author  zhangST
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 
     * addHour:计算 hour 小时后的时间. <br/>
     * @Date	2017年9月12日 下午2:43:58 <br/>
     * @author  zhangST
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }
    
    /**
     * 
     * getDayStart:得到day的起始时间点. <br/>
     * @Date	2017年9月12日 下午2:44:38 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 
     * getDayEnd:得到day的终止时间点. <br/>
     * @Date	2017年9月12日 下午2:44:50 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 
     * addDay:计算 day 天后的时间. <br/>
     * @Date	2017年9月12日 下午2:44:59 <br/>
     * @author  zhangST
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
    
    /**
     * 
     * getMonthEnd:得到month的终止时间点. <br/>
     * @Date	2017年9月12日 下午2:45:21 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }

    /**
     * 
     * addYear:计算 year 年后的时间. <br/>
     * @Date	2017年9月12日 下午2:45:51 <br/>
     * @author  zhangST
     * @param date
     * @param year
     * @return
     */
    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 365 * year);
        return calendar.getTime();
    }

    /**
     * 
     * strToTimestamp:日期字符串转化时间戳. <br/>
     * @Date	2017年9月12日 下午2:46:49 <br/>
     * @author  zhangST
     * @param dateStr
     * @return
     */
    public static Timestamp strToTimestamp(String dateStr) {
        return Timestamp.valueOf(dateStr);
    }

    /**
     * 
     * strToTimestamp:日期转换时间戳. <br/>
     * @Date	2017年9月12日 下午2:48:18 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Timestamp strToTimestamp(Date date) {
        return Timestamp.valueOf(SDF_TIMESTAMP.format(date));
    }

    /**
     * 
     * getCurTimestamp:获取当前时间的时间戳. <br/>
     * @Date	2017年9月12日 下午2:48:40 <br/>
     * @author  zhangST
     * @return
     */
    public static Timestamp getCurTimestamp() {
        return Timestamp.valueOf(SDF_TIMESTAMP.format(new Date()));
    }
    
    /**
     * 
     * daysBetween:取得两个日期之间的日数. <br/>
     * @Date	2017年9月12日 下午2:49:18 <br/>
     * @author  zhangST
     * @param t1
     * @param t2
     * @return      t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
     */
    public static long daysBetween(java.sql.Timestamp t1, java.sql.Timestamp t2) {
        return (t2.getTime() - t1.getTime()) / DAY_MILLI;
    }

    /**
     * 
     * getSysDateTimestamp:获取java.sql.Timestamp型的SYSDATE. <br/>
     * @Date	2017年9月12日 下午2:49:47 <br/>
     * @author  zhangST
     * @return  java.sql.Timestamp型的SYSDATE
     */
    public static java.sql.Timestamp getSysDateTimestamp() {
        return new java.sql.Timestamp(System.currentTimeMillis());
    }

    /**
     * 
     * toSqlTimestamp:利用缺省的Date格式(yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss)转换String到java.sql.Timestamp. <br/>
     * @Date	2017年9月12日 下午2:54:27 <br/>
     * @author  zhangST
     * @param sDate
     * @return
     */
    public static java.sql.Timestamp toSqlTimestamp(String sDate) {
        if (sDate == null) {
            return null;
        }
        if (sDate.length() != DateUtils.DATE_FORMAT_DATEONLY.length()
                && sDate.length() != DateUtils.DATE_FORMAT_DATETIME.length()) {
            return null;
        }
        return toSqlTimestamp(sDate, 
                sDate.length() == DateUtils.DATE_FORMAT_DATEONLY.length()
                ?DateUtils.DATE_FORMAT_DATEONLY
                :DateUtils.DATE_FORMAT_DATETIME);

    }
    
    /**
     * 
     * toSqlTimestamp:利用缺省的Date格式转化String到java.sql.Timestamp. <br/>
     * @Date	2017年9月12日 下午2:54:40 <br/>
     * @author  zhangST
     * @param sDate
     * @param sFmt      DATE_FORMAT_DATEONLY/DATE_FORMAT_DATETIME
     * @return
     */
    public static java.sql.Timestamp toSqlTimestamp(String sDate, String sFmt) {
        String temp = null;
        if (sDate == null || sFmt == null) {
            return null;
        }
        if (sDate.length() != sFmt.length()) {
            return null;
        }
        if (sFmt.equals(DateUtils.DATE_FORMAT_DATETIME)) {
            temp = sDate.replace('/', '-');
            temp = temp + ".000000000";
        } else if (sFmt.equals(DateUtils.DATE_FORMAT_DATEONLY)) {
            temp = sDate.replace('/', '-');
            temp = temp + " 00:00:00.000000000";
            // }else if( sFmt.equals (DateUtils.DATE_FORMAT_SESSION )){
            // //Format: 200009301230
            // temp =
            // sDate.substring(0,4)+"-"+sDate.substring(4,6)+"-"+sDate.substring(6,8);
            // temp += " " + sDate.substring(8,10) + ":" +
            // sDate.substring(10,12) + ":00.000000000";
        } else {
            return null;
        }
        // java.sql.Timestamp.value() 要求的格式必须为yyyy-mm-dd hh:mm:ss.fffffffff
        return java.sql.Timestamp.valueOf(temp);
    }
    
    /**
     * 
     * isBetween:判断一个时间是否在某个时间区间内--开区间.<br/>
     * @Date	2017年9月12日 下午2:58:53 <br/>
     * @author  zhangST
     * @param now       目标时间
     * @param start     时间区间开始
     * @param end       时间区间结束
     * @param compModel 比较的模式 <pre>
     *                      取值：
     *                      COMP_MODEL_DATE     只比较日期，不比较时间
     *                      COMP_MODEL_TIME     只比较时间，不比较日期
     *                      COMP_MODEL_DATETIME 比较日期，也比较时间
     *                      </pre>
     * @return          是否在区间内
     */
    public static boolean isBetween(Date now, Date start, Date end, int compModel) {
        return isBetween(now, start, end, LEFT_OPEN_RIGHT_OPEN, compModel);
    }

    /**
     * 
     * isBetween:判断时间是否在制定的时间段之类. <br/>
     * @Date	2017年9月12日 下午3:04:02 <br/>
     * @author  zhangST
     * @param date          需要判断的时间
     * @param start         时间段的起始时间
     * @param end           时间段的截止时间
     * @param rangeModel    区间模式<pre>
     *                      取值：
     *                      LEFT_OPEN_RIGHT_OPEN
     *                      LEFT_CLOSE_RIGHT_OPEN
     *                      LEFT_OPEN_RIGHT_CLOSE
     *                      LEFT_CLOSE_RIGHT_CLOSE
     *                      </pre>
     * @param compModel     比较的模式 <pre>
     *                      取值：
     *                      COMP_MODEL_DATE     只比较日期，不比较时间
     *                      COMP_MODEL_TIME     只比较时间，不比较日期
     *                      COMP_MODEL_DATETIME 比较日期，也比较时间
     *                      </pre>
     * </pre>
     * @return
     */
    public static boolean isBetween(Date date, Date start, Date end, int rangeModel, int compModel) {
        if (date == null || start == null || end == null) {
            throw new IllegalArgumentException("日期不能为空");
        }
        SimpleDateFormat format = null;
        switch (compModel) {
        case COMP_MODEL_DATE: {
            format = new SimpleDateFormat("yyyyMMdd");
            break;
        }
        case COMP_MODEL_TIME: {
            format = new SimpleDateFormat("HHmmss");
            break;
        }
        case COMP_MODEL_DATETIME: {
            format = new SimpleDateFormat("yyyyMMddHHmmss");
            break;
        }
        default: {
            throw new IllegalArgumentException(String.format("日期的比较模式[%d]有误", compModel));
        }
        }
        long dateNumber = Long.parseLong(format.format(date));
        long startNumber = Long.parseLong(format.format(start));
        long endNumber = Long.parseLong(format.format(end));
        switch (rangeModel) {
        case LEFT_OPEN_RIGHT_OPEN: {
            if (dateNumber <= startNumber || dateNumber >= endNumber) {
                return false;
            } else {
                return true;
            }
        }
        case LEFT_CLOSE_RIGHT_OPEN: {
            if (dateNumber < startNumber || dateNumber >= endNumber) {
                return false;
            } else {
                return true;
            }
        }
        case LEFT_OPEN_RIGHT_CLOSE: {
            if (dateNumber <= startNumber || dateNumber > endNumber) {
                return false;
            } else {
                return true;
            }
        }
        case LEFT_CLOSE_RIGHT_CLOSE: {
            if (dateNumber < startNumber || dateNumber > endNumber) {
                return false;
            } else {
                return true;
            }
        }
        default: {
            throw new IllegalArgumentException(String.format("日期的区间模式[%d]有误", rangeModel));
        }
        }
    }
    
    /**
     * 
     * getWeekStart:得到当前周起始时间. <br/>
     * @Date	2017年9月12日 下午3:10:19 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getWeekStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.WEEK_OF_YEAR);
        int firstDay = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, firstDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 
     * getWeekEnd:得到当前周截止时间. <br/>
     * @Date	2017年9月12日 下午3:10:27 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getWeekEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.WEEK_OF_YEAR);
        int firstDay = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, 8 - firstDay);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 
     * getMonthStart:得到当月起始时间. <br/>
     * @Date	2017年9月12日 下午3:10:35 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 
     * getYearStart:得到当前年起始时间. <br/>
     * @Date	2017年9月12日 下午3:10:43 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getYearStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 
     * getYearEnd:得到当前年最后一天. <br/>
     * @Date	2017年9月12日 下午3:10:52 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getYearEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 
     * getDayOfMonth:取得月天数. <br/>
     * @Date	2017年9月12日 下午3:10:59 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 
     * getFirstDateOfMonth:取得月第一天. <br/>
     * @Date	2017年9月12日 下午3:11:09 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 
     * getLastDateOfMonth:取得月最后一天. <br/>
     * @Date	2017年9月12日 下午3:11:18 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 
     * getSeasonStart:取得季度第一天. <br/>
     * @Date	2017年9月12日 下午3:11:25 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getSeasonStart(Date date) {
        return getDayStart(getFirstDateOfMonth(getSeasonDate(date)[0]));
    }

    /**
     * 
     * getSeasonEnd:取得季度最后一天. <br/>
     * @Date	2017年9月12日 下午3:11:32 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getSeasonEnd(Date date) {
        return getDayEnd(getLastDateOfMonth(getSeasonDate(date)[2]));
    }

    /**
     * 
     * getSeasonDate:取得季度月. <br/>
     * @Date	2017年9月12日 下午3:11:40 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 
     * getSeason:1 第一季度 2 第二季度 3 第三季度 4 第四季度. <br/>
     * @Date	2017年9月12日 下午3:11:56 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
        case Calendar.JANUARY:
        case Calendar.FEBRUARY:
        case Calendar.MARCH:
            season = 1;
            break;
        case Calendar.APRIL:
        case Calendar.MAY:
        case Calendar.JUNE:
            season = 2;
            break;
        case Calendar.JULY:
        case Calendar.AUGUST:
        case Calendar.SEPTEMBER:
            season = 3;
            break;
        case Calendar.OCTOBER:
        case Calendar.NOVEMBER:
        case Calendar.DECEMBER:
            season = 4;
            break;
        default:
            break;
        }
        return season;
    }

    /**
     * 
     * getWeekIndex:判断输入日期是一个星期中的第几天(星期天为一个星期第一天). <br/>
     * @Date	2017年9月12日 下午3:12:26 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static int getWeekIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 
     * subDays:当前时间的前几天，并且以例如2013-12-09 00:00:00 形式输出. <br/>
     * @Date	2017年9月12日 下午3:12:43 <br/>
     * @author  zhangST
     * @param days
     * @return
     */
    public static Date subDays(int days) {
        Date date = addDay(new Date(), -days);
        String dateStr = getReqDate(date);
        Date date1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e);
        }
        return date1;
    }

    /**
     * 
     * isOverIntervalLimit:判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制 如：开始时间和结束时间，不能超出距离当前时间90天. <br/>
     * @Date	2017年9月12日 下午3:14:41 <br/>
     * @author  zhangST
     * @param startDate     开始时间
     * @param endDate       结束时间按
     * @param interval      间隔数
     * @param dateUnit      单位(如：月，日),参照Calendar的时间单位
     * @return
     */
    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval, int dateUnit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(dateUnit, interval * (-1));
        Date curDate = getDayStart(cal.getTime());
        if (getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0) {
            return true;
        }
        return false;
    }

    /**
     * 
     * isOverIntervalLimit:判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天. <br/>
     * @Date	2017年9月12日 下午3:15:23 <br/>
     * @author  zhangST
     * @param startDate     开始时间
     * @param endDate       结束时间按
     * @param interval      间隔数
     * @return
     */
    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, interval * (-1));
        Date curDate = getDayStart(cal.getTime());
        if (getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0) {
            return true;
        }
        return false;
    }

    /**
     * 
     * isOverIntervalLimit:判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天. <br/>
     * @Date	2017年9月12日 下午3:16:39 <br/>
     * @author  zhangST
     * @param startDateStr      开始时间
     * @param endDateStr        结束时间
     * @param interval          间隔数
     * @return
     */
    public static boolean isOverIntervalLimit(String startDateStr, String endDateStr, int interval) {
        Date startDate = null;
        Date endDate = null;
        startDate = DateUtils.parseDate(startDateStr, DateUtils.DATE_FORMAT_DATEONLY);
        endDate = DateUtils.parseDate(endDateStr, DateUtils.DATE_FORMAT_DATEONLY);
        if (startDate == null || endDate == null){
            return true;
        }

        return isOverIntervalLimit(startDate, endDate, interval);
    }

    /**
     * 
     * getQuarter:取季度. <br/>
     * @Date	2017年9月12日 下午3:17:31 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getQuarter(Date date) {
        if (date.getMonth() == 0 || date.getMonth() == 1 || date.getMonth() == 2) {
            return 1;
        } else if (date.getMonth() == 3 || date.getMonth() == 4 || date.getMonth() == 5) {
            return 2;
        } else if (date.getMonth() == 6 || date.getMonth() == 7 || date.getMonth() == 8) {
            return 3;
        } else if (date.getMonth() == 9 || date.getMonth() == 10 || date.getMonth() == 11) {
            return 4;
        } else {
            return 0;

        }
    }

    /**
     * 
     * getYesterday:获取昨日的日期格式串. <br/>
     * @Date	2017年9月12日 下午3:20:22 <br/>
     * @author  zhangST
     * @return
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return DateUtils.SDF_SHORT_DATE.format(calendar.getTime());
    }

    /**
     * 
     * getDateByStr:字符转日期. <br/>
     * @Date	2017年9月12日 下午3:21:12 <br/>
     * @author  zhangST
     * @param dateStr
     * @return
     */
    public static Date getDateByStr(String dateStr) {
        SimpleDateFormat formatter = null;
        if (dateStr == null) {
            return null;
        } else if (dateStr.length() == 10) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        } else if (dateStr.length() == 16) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if (dateStr.length() == 19) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if (dateStr.length() > 19) {
            dateStr = dateStr.substring(0, 19);
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            return null;
        }
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * 
     * getMaxTime:日期最大时间. <br/>
     * @Date	2017年9月12日 下午3:21:57 <br/>
     * @author  zhangST
     * @param dt
     * @return
     */
    public static Date getMaxTime(Date dt) {

        Date dt1 = null;
        Calendar ca = Calendar.getInstance();
        ca.setTime(dt);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        dt1 = ca.getTime();
        dt1 = DateUtils.getMinTime(dt1);
        ca.setTime(dt1);
        ca.add(Calendar.SECOND, -1);
        dt1 = ca.getTime();
        return dt1;
    }

    /**
     * 
     * getMinTime:日期最小时间. <br/>
     * @Date	2017年9月12日 下午3:22:07 <br/>
     * @author  zhangST
     * @param dt
     * @return
     */
    public static Date getMinTime(Date dt) {
        Date dt1 = null;
        dt1 = DateUtils.getDateByStr(DateUtils.SDF_SHORT_DATE.format(dt));
        return dt1;
    }

    /**
     * 
     * getLastDayOfMonth:月的最后一天. <br/>
     * @Date	2017年9月12日 下午3:23:02 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Date getLastDayOfMonth(Date date) {
        Calendar cDay1 = Calendar.getInstance();
        cDay1.setTime(date);
        int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = cDay1.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }

    /**
     * 
     * getFirstDayOfMonth:月的第一天. <br/>
     * @Date	2017年9月12日 下午3:23:16 <br/>
     * @author  zhangST
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 
     * getPreviousMonthFirstDay:上月第一天. <br/>
     * @Date	2017年9月12日 下午3:23:24 <br/>
     * @author  zhangST
     * @return
     */
    public static Date getPreviousMonthFirstDay() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        return getMinTime(lastDate.getTime());
    }

    /**
     * 
     * getPreviousMonthLastDay:上月最后一天. <br/>
     * @Date	2017年9月12日 下午3:23:35 <br/>
     * @author  zhangST
     * @return
     */
    public static Date getPreviousMonthLastDay() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.DATE, -1);
        return getMinTime(lastDate.getTime());
    }

    /**
     * 
     * getDateDiff:两个日期相关天数. <br/>
     * @Date	2017年9月12日 下午3:23:50 <br/>
     * @author  zhangST
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDateDiff(String startDate, String endDate) {
        long diff = 0;
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

            diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime())
                    / (24 * 60 * 60 * 1000)
                    : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
        }
        return diff;
    }

    /**
     * 
     * getDateDiff:返回天数. <br/>
     * @Date	2017年9月12日 下午3:24:00 <br/>
     * @author  zhangST
     * @param date1
     * @param date2
     * @return
     */
    public static long getDateDiff(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0L;
        }
        long diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2
                .getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
        return diff;
    }

    /**
     * 
     * getYearDiff:判断两个时间的相差年数. <br/>
     * @Date	2017年9月12日 下午3:24:09 <br/>
     * @author  zhangST
     * @param date1
     * @param date2
     * @return
     */
    public static int getYearDiff(Date date1, Date date2){
        if (date1 == null || date2 == null) {
            return 0;
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        int year1 = calendar1.get(Calendar.YEAR);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int year2 = calendar2.get(Calendar.YEAR);

        return Math.abs( year1 - year2);
    }

    /**
     * 
     * getTimeDiff:获取两个时间的毫秒数. <br/>
     * @Date	2017年9月12日 下午3:24:18 <br/>
     * @author  zhangST
     * @param date1
     * @param date2
     * @return
     */
    public static long getTimeDiff(Date date1, Date date2){
        if (date1 == null || date1 == null) {
            return 0L;
        }
        long diff = (date1.getTime() - date2.getTime()) > 0 ? (date1.getTime() - date2
                .getTime())  : (date2.getTime() - date1.getTime()) ;
        return diff;
    }

    /**
     * 
     * isSameWeekWithToday:判断两个时间是不是在一个周中. <br/>
     * @Date    2017年9月12日 下午3:24:18 <br/>
     * @author  zhangST
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekWithToday(Date date) {
        if (date == null) {
            return false;
        }
        // 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);
        int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
        // subYear==0,说明是同一年
        if (subYear == 0) {
            if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == 1 && dateCal.get(Calendar.MONTH) == 11 && todayCal.get(Calendar.MONTH) == 0) {
            if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == -1 && todayCal.get(Calendar.MONTH) == 11 && dateCal.get(Calendar.MONTH) == 0) {
            if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }


    /**
     * 
     * getLastDays:获取几天内日期 return 2014-5-4、2014-5-3. <br/>
     * @Date	2017年9月12日 下午3:27:22 <br/>
     * @author  zhangST
     * @param countDay
     * @return
     */
    public static List<String> getLastDays(int countDay) {
        List<String> listDate = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < countDay; i++) {
            calendar.add(Calendar.DATE, -i);
            listDate.add(DateUtils.getReqDateyyyyMMdd(calendar.getTime()));
        }
        return listDate;
    }

    /**
     * 
     * isSameDayWithToday:判断两个时间是不是在一天中. <br/>
     * @Date    2017年9月12日 下午3:24:18 <br/>
     * @author  zhangST
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDayWithToday(Date date) {

        if (date == null) {
            return false;
        }

        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);
        int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
        int subMouth = todayCal.get(Calendar.MONTH) - dateCal.get(Calendar.MONTH);
        int subDay = todayCal.get(Calendar.DAY_OF_MONTH) - dateCal.get(Calendar.DAY_OF_MONTH);
        if (subYear == 0 && subMouth == 0 && subDay == 0) {
            return true;
        }
        return false;
    }
    
    
    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private DateUtils() {
    }
    
}


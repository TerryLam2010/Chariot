package cn.terrylam.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期操作类
 * 已继承了org.apache.commons.lang3.time.DateUtils，
 * 如需使用一些工具方法时请仔细查看其api，其已有的方法可直接调用避免重复造轮子
 * http://commons.apache.org/proper/commons-lang/javadocs/api-3.1/org/apache/commons/lang3/time/DateUtils.html
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils{
	
	/**
	 * 获取指定时间差的日期
	 * @param date 基准日期
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date date, int second){
		Date result = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
		result = new Date(cal.getTime().getTime());
		return result;
	}
	
	/**
	 * 获取指定时间差的日期
	 * @param date
	 * @param minute
	 * @return
     */
	public static Date addMinute(Date date, int minute){
		Date result = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		result = new Date(cal.getTime().getTime());
		return result;
	}
	
	/**
	 * 获取指定时间差的日期
	 * @param Date date 基准日期
	 * @param int day 相差天数
	 * @return Date, 返回的日期
	 */
	public static Date addHour(Date date, int hour) {
		Date result = null;
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.HOUR, hour);
	    result = new Date(cal.getTime().getTime());
	    return result;
	}
	
	/**
	 * 获取指定时间差的日期
	 * @param Date date 基准日期
	 * @param int day 相差天数
	 * @return Date, 返回的日期
	 */
	public static Date addDay(Date date, int day) {
		Date result = null;
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, day);
	    result = new Date(cal.getTime().getTime());
	    return result;
	}
	
	/**
	 * 获取指定时间差的日期
	 * @param Date date 基准日期
	 * @param int month 相差月数
	 * @return Date, 返回的日期
	 */
	public static Date addMonth(Date date, int month) {
		Date result = null;
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.MONTH, month);
	    result = new Date(cal.getTime().getTime());
	    return result;
	}

	/**
	 * 获取指定时间差的日期
     * @param Date date 基准日期
     * @param int year 相差年数
	 * @return Date, 返回的日期
	 */
	public static Date addYear(Date date, int year) {
		Date result = null;
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.YEAR, year);
	    result = new Date(cal.getTime().getTime());
	    return result;
	}
    
    /**
     * 当日凌晨时间，方便基于日期的比较
     * @return
     */
    public static Date getToday() {
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        return cld.getTime();
    }
    
    /**
     * 日期格式化
     * @param String v
     * @param String fm 格式化标示符 例如:yyyy-MM-dd 或者 yyyy-MM-dd HH:mm:ss
     * @param Date def
     * @return Date, 返回的日期
     */
    public static Date toDate(String v, String fm, Date def) {
       if (v == null || v.length() == 0)
         return def;
       try {
          return new SimpleDateFormat(fm).parse(v.trim());
       } catch (Exception e) {
          return def;
       }
    }

    /**
     * 生成格式化日期 yyyy-MM-dd
     * @param String v
     * @param Date def
     * @return Date, 返回的日期
     */
    public static Date dateValue(String v, Date def) {
       return toDate(v, "yyyy-MM-dd", def);
    }

    /**
     * 生成格式化日期 yyyy-MM-dd HH:mm:ss
     * @param String v
     * @param Date def
     * @return Date, 返回的日期
     */
    public static Date datetimeValue(String v, Date def) {
       return toDate(v, "yyyy-MM-dd HH:mm:ss", def);
    }

    /**
     * 输出格式化日期 yyyy-MM-dd
     * @param String fm 格式化标示符
     * @param Date def 要格式化输出的日期
     * @return String, 返回格式化的日期字符串
     */
    public static String dateString(String fm,Date def){
    	return new SimpleDateFormat(fm).format(def);
    }

    public static String dateString(String fm, String def){
		SimpleDateFormat dateFormater = new SimpleDateFormat(fm);
		try {
			Date date = dateFormater.parse(def);
			return dateString(fm, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    /**
     * 返回批定格式的时间字符串
     * @param date Date
     * @param pattern String
     * @return String
     */
    public static String formatDate(java.util.Date date, String pattern) {
    	return new SimpleDateFormat(pattern).format(date);
    }



    /**
     * 获取某天的开始时刻00:00:00.000   
     * @param date 需要获取天内的时间
     * @return Date java.util.Date
     */
    public static Date getStartOfDay(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.HOUR_OF_DAY, 0);
        cld.set(Calendar.MINUTE, 0);
        cld.set(Calendar.SECOND, 0);
        cld.set(Calendar.MILLISECOND, 0);
        return cld.getTime();
    }

    /**
     * 获取某天的最后时刻23:59:59.999    
     * @param date 需要获取天内的时间
     * @return Date java.util.Date
     */
    public static Date getEndOfDay(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.HOUR_OF_DAY, 23);
        cld.set(Calendar.MINUTE, 59);
        cld.set(Calendar.SECOND, 59);
        cld.set(Calendar.MILLISECOND, 999);
        return cld.getTime();
    }
    
    /**
     * 获取指定时间所在周的第一天的00:00:00.000    
     * @param date 需要获取周的某天
     * @return Date java.util.Date
     */
    public static Date getStartOfWeek(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getStartOfDay(cld.getTime());
    }

    /**
     * 获取指定时间所在周的最后一天的23:59:59.999    
     * @param date 需要获取周的某天
     * @return Date java.util.Date
     */
    public static Date getEndOfWeek(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return getEndOfDay(cld.getTime());
    }

    /**
     * 获取指定时间所在月的第一天的00:00:00.000
     *
     * @param date 需要获取月的某天
     * @return Date java.util.Date
     */
    public static Date getStartOfMonth(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.DAY_OF_MONTH, 1);
        return getStartOfDay(cld.getTime());
    }

    /**
     * 获取指定时间所在月的最后一天的23:59:59.999   
     * @param date 需要获取月的某天
     * @return Date java.util.Date
     */
    public static Date getEndOfMonth(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        int maxDay = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
        cld.set(Calendar.DAY_OF_MONTH, maxDay);
        return getEndOfDay(cld.getTime());
    }

    /**
     * 获取指定时间所在年的第一天的00:00:00.000   
     * @param date 需要获取年的某天
     * @return Date java.util.Date
     */
    public static Date getStartOfYear(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.DAY_OF_YEAR, 1);
        return getStartOfDay(cld.getTime());
    }

    /**
     * 获取指定时间所在年的最后一天的23:59:59.999    
     * @param date 需要获取年的某天
     * @return Date java.util.Date
     */
    public static Date getEndOfYear(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.set(Calendar.MONTH, Calendar.DECEMBER);
        cld.set(Calendar.DAY_OF_MONTH, 31);
        return getEndOfDay(cld.getTime());
	}
    
    /**
     * 获取当前时间的上n个周的第1天的00：00：00.000，方便基于日期的比较    
     * @param interval 间隔多少月，0表示当周，1表示上个周……
     * @return 指定周的第一天的00：00：00.000的java.util.Date
     */
    public static Date getPreWeek(int intervalWeek) {
        long dayMilliSecond = 24 * 60 * 60 * 1000;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        for (int i = 0; i < intervalWeek; i++) {
            long s1 = cal.getTimeInMillis();
            cal.setTimeInMillis(s1 - dayMilliSecond);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        return cal.getTime();
    }
    
    /**
     * 获取当前时间的上n个月的第1天的00：00：00.000，方便基于日期的比较    
     * @param interval 间隔多少月，0表示当月，1表示上个月……
     * @return 指定月份的第一天的00：00：00.000的java.util.Date
     */
    public static Date getPreMonth(int intervalMonth) {
        long dayMilliSecond = 24 * 60 * 60 * 1000;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < intervalMonth; i++) {
            long s1 = cal.getTimeInMillis();
            cal.setTimeInMillis(s1 - dayMilliSecond);
            cal.set(Calendar.DAY_OF_MONTH, 1);
        }
        return cal.getTime();
    }

    /**
     * 将"yyyy-MM-dd"格式的日期字符串转换为date对象
     * @param str
     * @return
     * @update date 2015年7月21日
     */
    public static Date parseDate(String str) {
		try {
			return parseDate(str, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * 将"yyyy-MM-dd HH:mm:ss"格式的日期字符串转换为date对象
     * @param str
     * @return
     * @update date 2015年7月21日
     */
    public static Date parseDateDetail(String str) {
		try {
			return parseDate(str, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}

package be.maximvdw.placeholderapi.internal.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String toString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static float nanosToSeconds(float nanos) {
		return (float) (Math.round((nanos / 1000000000) * 100.0) / 100.0);
	}

	public static float millisToSeconds(float millis) {
		return (float) (Math.round((millis / 1000) * 100.0) / 100.0);
	}

	public static String toString(long seconds, String format) {
		Date date = new Date(seconds);
		return toString(date, format);
	}

	public static int[] splitToHMS(BigDecimal biggy) {
		long longVal = biggy.longValue();
		int hours = (int) longVal / 3600;
		int remainder = (int) longVal - hours * 3600;
		int mins = remainder / 60;
		remainder = remainder - mins * 60;
		int secs = remainder;

		int[] ints = { hours, mins, secs };
		return ints;
	}

	public static int[] splitToMS(BigDecimal biggy) {
		long longVal = biggy.longValue();
		int mins = (int) (longVal / 60);
		int remainder = (int) (longVal - (mins * 60));
		int secs = remainder;

		int[] ints = { mins, secs };
		return ints;
	}
}

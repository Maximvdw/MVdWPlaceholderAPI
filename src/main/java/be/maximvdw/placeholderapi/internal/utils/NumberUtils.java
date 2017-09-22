package be.maximvdw.placeholderapi.internal.utils;

public class NumberUtils {
	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public static boolean isNumber(String s) {
		return isNumber(s, 10);
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public static int floor(double d1) {
		int i = (int) d1;
		return d1 >= i ? i : i - 1;
	}

	public static boolean isNumber(String s, int radix) {
		if (s.isEmpty())
			return false;
		s = s.replace(".", "").replace(",", "");
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

}

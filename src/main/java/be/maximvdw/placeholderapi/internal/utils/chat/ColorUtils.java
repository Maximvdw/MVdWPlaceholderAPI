package be.maximvdw.placeholderapi.internal.utils.chat;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * MVdW Software Color Utilities
 * 
 * @project BasePlugin
 * @version 1.0
 * @author Maxim Van de Wynckel (Maximvdw)
 * @site http://www.mvdw-software.be/
 */
public class ColorUtils {
	/**
	 * Convert string to colors
	 * 
	 * @param input
	 *            String
	 * @return Colored String
	 */
	public static String toColors(String input) {
		if (input == null) {
			return null;
		}
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public static String getLastColors(String input) {
		String result = "";
		int length = input.length();
		boolean foundColor = false;
		for (int index = length - 1; index > -1; --index) {
			char section = input.charAt(index);
			if (section == 167 && index < length - 1) {
				char c = input.charAt(index + 1);
				ChatColor color = ChatColor.getByChar(c);
				if (color != null) {
					result = color.toString() + result;
					if (color.isColor() || color.equals(ChatColor.RESET)) {
						foundColor = true;
						break;
					}
				}
			}
		}
		if (!foundColor) {
			result = ChatColor.WHITE + result;
		}
		return result;
	}

	/**
	 * Get last color
	 * 
	 * @param input
	 *            Input string
	 * @return Chat color
	 */
	public static ChatColor getLastColor(String input) {
		if (input == null)
			return ChatColor.RESET;
		int length = input.length();
		// Search backwards from the end as it is faster
		for (int index = length - 1; index > -1; index--) {
			char section = input.charAt(index);
			if (section == ChatColor.COLOR_CHAR && index < length - 1) {
				char c = input.charAt(index + 1);
				ChatColor color = ChatColor.getByChar(c);
				if (color.isColor())
					if (color != null) {
						return color;
					}
			}
		}
		return ChatColor.RESET;
	}

	public static ChatColor getLastEffect(String input) {
		if (input == null)
			return ChatColor.RESET;
		int length = input.length();
		// Search backwards from the end as it is faster
		for (int index = length - 1; index > -1; index--) {
			char section = input.charAt(index);
			if (section == ChatColor.COLOR_CHAR && index < length - 1) {
				char c = input.charAt(index + 1);
				ChatColor color = ChatColor.getByChar(c);
				if (color != null) {
					if (color.isColor())
						return ChatColor.RESET;
					if (color.isFormat())
						return color;
				}
			}
		}
		return ChatColor.RESET;
	}
	
	public static List<ChatColor> getLastEffects(String input) {
		List<ChatColor> effects = new ArrayList<ChatColor>();
		if (input == null){
			return effects;
		}
		int length = input.length();
		// Search backwards from the end as it is faster
		for (int index = length - 1; index > -1; index--) {
			char section = input.charAt(index);
			if (section == ChatColor.COLOR_CHAR && index < length - 1) {
				char c = input.charAt(index + 1);
				ChatColor color = ChatColor.getByChar(c);
				if (color != null) {
					if (color.isColor())
						return effects;
					if (color.isFormat())
						effects.add(color);
				}
			}
		}
		return effects;
	}

	/**
	 * Remove colors from string
	 * 
	 * @param input
	 *            String
	 * @return Cleared string
	 */
	public static String removeColors(String input) {
		if (input == null) {
			return null;
		}
		return ChatColor.stripColor(input.replaceAll("(&([A-FK-Oa-fk-or0-9]))", ""));
	}
}

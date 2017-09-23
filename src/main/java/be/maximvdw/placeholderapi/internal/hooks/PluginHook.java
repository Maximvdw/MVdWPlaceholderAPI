package be.maximvdw.placeholderapi.internal.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginHook {
	/**
	 * Check if a plugin is loaded
	 * 
	 * @param pluginName
	 *            Plugin name
	 * @return Plugin loaded
	 */
	public static boolean isLoaded(String pluginName) {
		Plugin pl = Bukkit.getPluginManager().getPlugin(pluginName);
		if (pl == null)
			return false;
		else
			return true;
	}

	/**
	 * Laod plugin
	 * 
	 * @param pluginName
	 *            Plugin name
	 * @return Plugin
	 */
	public static Plugin loadPlugin(String pluginName) {
		Plugin pl = Bukkit.getPluginManager().getPlugin(pluginName);
		return pl;
	}
}

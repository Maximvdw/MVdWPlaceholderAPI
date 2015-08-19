package be.maximvdw.placeholderapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import be.maximvdw.placeholderapi.internal.MVdWPlaceholderReplacer;
import be.maximvdw.placeholderapi.internal.PlaceholderAddedEvent;

/**
 * MVdWPlaceholderAPI
 * 
 * @author Maxim Van de Wynckel (Maximvdw)
 */
public class PlaceholderAPI extends JavaPlugin {
	/* MVdW plugin placeholder replacer for embedded placeholders */
	private static MVdWPlaceholderReplacer mvdwReplacer = null;
	/* Custom placeholders registered in the API */
	private static Map<String, PlaceholderReplacer> customPlaceholders = new ConcurrentHashMap<String, PlaceholderReplacer>();
	/* Placeholder change listeners */
	private static List<PlaceholderAddedEvent> placeholderAddedHandlers = new ArrayList<PlaceholderAddedEvent>();

	@Override
	public void onEnable() {
		super.onEnable();
		Bukkit.getLogger().info("[MVdWPlaceholderAPI] Initializing ...");
	}

	/**
	 * Register an MVdW Placeholder plugin to use to replace placeholders DO NOT
	 * USE THIS METHOD UNLESS YOU ARE ME
	 * 
	 * @param plugin
	 *            Maximvdw Plugin
	 * @param replacer
	 *            MVdW Placeholder replacer
	 */
	public void registerMVdWPlaceholderReplacer(Plugin plugin,
			MVdWPlaceholderReplacer replacer) {
		if (plugin == null)
			return;
		if (getMVdWReplacer() != null)
			return;
		PluginDescriptionFile desc = plugin.getDescription();
		if (desc.getAuthors().size() > 0) {
			if (desc.getAuthors().get(0).equals("Maximvdw")) {
				// Register MVdW Software plugin
				Bukkit.getLogger().info(
						"[MVdWPlaceholderAPI] Using " + desc.getName()
								+ " to get placeholders!");
				setMVdWReplacer(replacer);
			}
		} else {
			Bukkit.getLogger()
					.warning(
							"[MVdWPlaceholderAPI] Plugin "
									+ plugin.getName()
									+ " tried to register itself as an MVdW Placeholder plugin. Report this to Maximvdw!");
			return;
		}
	}

	/**
	 * Replace placeholders in input
	 * 
	 * @param offlinePlayer
	 *            Player to replace placeholders for
	 * @param input
	 *            Placeholder format {placeholder}
	 * @return Return result with replaced placeholders
	 */
	public static String replacePlaceholders(OfflinePlayer offlinePlayer,
			String input) {
		if (mvdwReplacer == null) {
			Bukkit.getLogger()
					.severe("[MVdWPlaceholderAPI] Unable to replace placeholders. No MVdW Placeholder plugin found!");
			return input;
		}

		return mvdwReplacer.replacePlaceholders(offlinePlayer, input);
	}

	/**
	 * Returns the amount of placeholders loaded into the memory
	 * 
	 * @return Placeholder count
	 */
	public static int getLoadedPlaceholderCount() {
		if (mvdwReplacer == null) {
			Bukkit.getLogger()
					.severe("[MVdWPlaceholderAPI] Unable to get placeholder count. No MVdW Placeholder plugin found!");
			return 0;
		}

		return mvdwReplacer.getLoadedPlaceholderCount();
	}

	/**
	 * Register a custom placeholder
	 * 
	 * @param plugin
	 *            Plugin that is registering the placeholder
	 * @param placeholder
	 *            Placeholder to be registered WITHOUT { }
	 * @return Returns if the placeholder is added or not
	 */
	public static boolean registerPlaceholder(Plugin plugin,
			String placeholder, PlaceholderReplacer replacer) {
		if (plugin == null)
			return false;
		if (placeholder == null)
			return false;
		if (placeholder.equals(""))
			return false;
		if (replacer == null)
			return false;
		if (customPlaceholders.containsKey(placeholder.toLowerCase()))
			return false;
		customPlaceholders.put(placeholder.toLowerCase(), replacer);
		Bukkit.getLogger().info(
				"[MVdWPlaceholderAPI] " + plugin.getName()
						+ " added custom placeholder {"
						+ placeholder.toLowerCase() + "}");
		for (PlaceholderAddedEvent event : placeholderAddedHandlers) {
			event.onPlaceholderAdded(plugin, placeholder, replacer);
		}
		return true; // Placeholder registered
	}

	public MVdWPlaceholderReplacer getMVdWReplacer() {
		return mvdwReplacer;
	}

	/**
	 * Set the MVdW Replacer DO NOT USE UNLESS YOU ARE ME
	 * 
	 * @param mvdwReplacer
	 *            MVdW Placeholder replacer
	 */
	public static void setMVdWReplacer(MVdWPlaceholderReplacer mvdwReplacer) {
		PlaceholderAPI.mvdwReplacer = mvdwReplacer;
	}

	/**
	 * Get all custom placeholder replacer
	 * 
	 * @return Map with placeholder and placeholder replacer
	 */
	public static Map<String, PlaceholderReplacer> getCustomPlaceholders() {
		return customPlaceholders;
	}

	public void setCustomPlaceholders(
			Map<String, PlaceholderReplacer> customPlaceholders) {
		PlaceholderAPI.customPlaceholders = customPlaceholders;
	}

	public void setPlaceholderListener(PlaceholderAddedEvent handler) {
		placeholderAddedHandlers.add(handler);
	}

}

package be.maximvdw.placeholderapi;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import be.maximvdw.placeholderapi.internal.MVdWPlaceholderReplacer;

/**
 * MVdWPlaceholderAPI
 * 
 * @author Maxim Van de Wynckel (Maximvdw)
 */
public class PlaceholderAPI extends JavaPlugin {
	/* MVdW plugin placeholder replacer for embedded placeholders */
	private MVdWPlaceholderReplacer mvdwReplacer = null;

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
			return;
		}
	}

	/**
	 * Replace placeholders in input
	 * 
	 * @param offlinePlayer
	 *            Player to replace placeholder for
	 * @param input
	 *            Placeholder format {placeholder}
	 * @return Return result with replaced placeholders
	 */
	public static String replacePlaceholders(OfflinePlayer offlinePlayer,
			String input) {
		String output = "";

		return output;
	}

	/**
	 * Returns the amount of placeholders loaded into the memory
	 * 
	 * @return Placeholder count
	 */
	public static int getLoadedPlaceholderCount() {
		int totalCount = 0;

		return totalCount;
	}

	public static boolean registerPlaceholder(Plugin plugin,
			String placeholder, String description) {
		if (plugin == null)
			return false;

		return true; // Placeholder registered
	}

	public MVdWPlaceholderReplacer getMVdWReplacer() {
		return mvdwReplacer;
	}

	public void setMVdWReplacer(MVdWPlaceholderReplacer mvdwReplacer) {
		this.mvdwReplacer = mvdwReplacer;
	}

}

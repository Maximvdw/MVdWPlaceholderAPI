package be.maximvdw.placeholderapi.internal;

import org.bukkit.OfflinePlayer;

public interface MVdWPlaceholderReplacer {
	public String replacePlaceholders(OfflinePlayer offlinePlayer, String input);

	public int getLoadedPlaceholderCount();
}

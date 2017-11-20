package be.maximvdw.placeholderapi.internal;

import org.bukkit.OfflinePlayer;

@Deprecated
public interface MVdWPlaceholderReplacer {
	String replacePlaceholders(OfflinePlayer offlinePlayer, String input);

	int getLoadedPlaceholderCount();
}

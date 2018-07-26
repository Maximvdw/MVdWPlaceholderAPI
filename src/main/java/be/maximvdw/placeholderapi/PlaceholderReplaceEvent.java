package be.maximvdw.placeholderapi;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Placeholder replace event
 * 
 * @author Maxim Van de Wynckel
 */
public class PlaceholderReplaceEvent {
	private OfflinePlayer offlinePlayer = null; // The offline player if player
	private String placeholder = ""; // The placeholder (with or without wildcards)

	public PlaceholderReplaceEvent(OfflinePlayer offlinePlayer,
			String placeholder) {
		this.offlinePlayer = offlinePlayer
		this.placeholder = placeholder;
	}

	/**
	 * Get the placeholder that is requested to be replaced
	 * 
	 * @return Placeholder without { }
	 */
	public String getPlaceholder() {
		return placeholder;
	}

	/**
	 * Get the offline player instance
	 * 
	 * @return Offline player
	 */
	public OfflinePlayer getOfflinePlayer() {
		return offlinePlayer;
	}

	/**
	 * Get the online player instance
	 * 
	 * @return Online player
	 */
	public Player getPlayer() {
		return isOnline() ? offlinePlayer.getPlayer() : null;
	}

	/**
	 * Check if the player is online
	 * 
	 * @return Player is online
	 */
	public boolean isOnline() {
		return offlinePlayer != null && offlinePlayer.isOnline();
	}
}

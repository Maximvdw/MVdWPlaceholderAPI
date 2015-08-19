package be.maximvdw.placeholderapi;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Placeholder replace event
 * 
 * @author Maxim Van de Wynckel
 */
public class PlaceholderReplaceEvent {
	private Player player = null; // The player if he is online
	private OfflinePlayer offlinePlayer = null; // The offline player if player
												// is not null
	private String placeholder = ""; // The placeholder (with or without
										// wildcards)

	public PlaceholderReplaceEvent(OfflinePlayer offlinePlayer,
			String placeholder) {
		setOfflinePlayer(offlinePlayer);
		if (offlinePlayer != null) {
			if (offlinePlayer.isOnline())
				setPlayer(getOfflinePlayer().getPlayer());
		}
		setPlaceholder(placeholder);
	}

	/**
	 * Get the placeholder that is requested to be replaced
	 * 
	 * @return Placeholder without { }
	 */
	public String getPlaceholder() {
		return placeholder;
	}

	private void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	/**
	 * Get the offline player instance
	 * 
	 * @return Offline player
	 */
	public OfflinePlayer getOfflinePlayer() {
		return offlinePlayer;
	}

	private void setOfflinePlayer(OfflinePlayer offlinePlayer) {
		this.offlinePlayer = offlinePlayer;
	}

	/**
	 * Get the online player instance
	 * 
	 * @return Online player
	 */
	public Player getPlayer() {
		return player;
	}

	private void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Check if the player is online
	 * 
	 * @return Player is online
	 */
	public boolean isOnline() {
		if (player == null)
			return false;
		return player.isOnline();
	}
}

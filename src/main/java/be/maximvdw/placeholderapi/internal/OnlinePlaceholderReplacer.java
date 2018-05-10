package be.maximvdw.placeholderapi.internal;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class OnlinePlaceholderReplacer<T> extends
		PlaceholderReplacer<T> {

	public OnlinePlaceholderReplacer(Class<T> type) {
		super(type);
	}

	public OnlinePlaceholderReplacer(Class<T> type, Object... args) {
		super(type, args);
	}

	/**
	 * Get placeholde result
	 * 
	 * @param player
	 *            Player
	 * @return placeholder result
	 */
	public abstract T getResult(String placeholder, Player player);

	/**
	 * Get placeholde result
	 * 
	 * @param player
	 *            Player
	 * @return placeholder result
	 */
	public T getResult(String placeholder, OfflinePlayer player) {
		return null;
	}

	/**
	 * Get placeholde result
	 *
	 * @param player
	 *            Player
	 * @return placeholder result
	 */
	public T getResult(String placeholder, Player player, Player viewingPlayer) {
		return getResult(placeholder,player);
	}

}

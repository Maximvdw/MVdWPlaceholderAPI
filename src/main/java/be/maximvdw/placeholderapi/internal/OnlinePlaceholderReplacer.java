package be.maximvdw.placeholderapi.internal;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.internal.ui.SendConsole;
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
	 * Get placeholder result
	 * 
	 * @param event Placeholder replace event
	 * @return placeholder result
	 */
	@Override
	public T getResult(PlaceholderReplaceEvent event){
		// Will be implemented by newer placeholder versions
		// Will not be called by older versions
		// Compensated by redirecting to older call
		// Because one of these three "getResult" functions will be implemented
		// it is not going to cause a recursive loop
		return this.getResult(event.getPlaceholder(),event.getPlayer());
	}

	/**
	 * Get placeholder result
	 *
	 * @param player
	 *            Player
	 * @return placeholder result
	 */
	@Deprecated
	@Override
	public T getResult(String placeholder, Player player){
		PlaceholderReplaceEvent replaceEvent = new PlaceholderReplaceEvent(player,placeholder);
		return this.getResult(replaceEvent);
	}

	/**
	 * Get placeholder result
	 *
	 * @param player
	 *            Player
	 * @return placeholder result
	 */
	@Deprecated
	@Override
	public T getResult(String placeholder, OfflinePlayer player) {
		if (player.isOnline()){
			return this.getResult(new PlaceholderReplaceEvent(player,placeholder));
		}
		return null;
	}
}

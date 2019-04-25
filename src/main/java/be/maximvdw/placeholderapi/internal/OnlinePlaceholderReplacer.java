package be.maximvdw.placeholderapi.internal;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
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
	public T getResult(PlaceholderReplaceEvent event){
		return getResult(event.getPlaceholder(),event.getPlayer());
	}

	/**
	 * Get placeholder result
	 *
	 * @param player
	 *            Player
	 * @return placeholder result
	 */
	@Deprecated
	public T getResult(String placeholder, Player player){
		return null;
	}
}

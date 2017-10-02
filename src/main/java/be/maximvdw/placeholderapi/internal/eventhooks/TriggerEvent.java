package be.maximvdw.placeholderapi.internal.eventhooks;

import org.bukkit.entity.Player;

public interface TriggerEvent {
	void enableEvent(Player player, String action);

	void disableEvent(Player player, String action);
}

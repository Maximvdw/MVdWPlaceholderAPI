package be.maximvdw.placeholderapi.internal.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class PlaceholdersLoadedEvent extends Event {
	private Plugin plugin = null;

	public PlaceholdersLoadedEvent(Plugin plugin) {
		setPlugin(plugin);
		Bukkit.getPluginManager().callEvent(this);
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}
}

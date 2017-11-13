package be.maximvdw.placeholderapi.events;

import org.bukkit.plugin.Plugin;

import be.maximvdw.placeholderapi.PlaceholderReplacer;

/**
 * Placeholder Added event
 *
 * @author Maxim Van de Wynckel
 */
public interface PlaceholderAddedEvent {
    /**
     * Called when a custom placeholder is added
     *
     * @param plugin      Plugin that added it
     * @param placeholder Placeholder that is added
     * @param replacer    Replacer for the plugin
     */
    void onPlaceholderAdded(Plugin plugin, String placeholder,
                            PlaceholderReplacer replacer);
}

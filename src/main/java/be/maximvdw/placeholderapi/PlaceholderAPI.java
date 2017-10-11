package be.maximvdw.placeholderapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

import be.maximvdw.placeholderapi.internal.CustomPlaceholdersPack;
import be.maximvdw.placeholderapi.internal.PlaceholderPack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import be.maximvdw.placeholderapi.internal.PlaceholderAddedEvent;

/**
 * MVdWPlaceholderAPI
 *
 * @author Maxim Van de Wynckel (Maximvdw)
 */
public class PlaceholderAPI extends JavaPlugin {
    /* Custom placeholders registered in the API */
    private static PlaceholderPack customPlaceholders = null;
    /* Placeholder change listeners */
    private static List<PlaceholderAddedEvent> placeholderAddedHandlers = new ArrayList<PlaceholderAddedEvent>();

    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getLogger().info("[MVdWPlaceholderAPI] Initializing ...");
        customPlaceholders = new CustomPlaceholdersPack(this);
    }

    @Override
    public void onDisable() {

    }

    /**
     * Replace placeholders in input
     *
     * @param offlinePlayer Player to replace placeholders for
     * @param input         Placeholder format {placeholder}
     * @return Return result with replaced placeholders
     */
    public static String replacePlaceholders(OfflinePlayer offlinePlayer, String input) {
        return PlaceholderPack.getPlaceholderResult(input,
                offlinePlayer);
    }

    /**
     * Returns the amount of placeholders loaded into the memory
     *
     * @return Placeholder count
     */
    public static int getLoadedPlaceholderCount() {
        return PlaceholderPack.getPlaceHolderCount();
    }

    /**
     * Register a custom placeholder
     *
     * @param plugin      Plugin that is registering the placeholder
     * @param placeholder Placeholder to be registered WITHOUT { }
     * @return Returns if the placeholder is added or not
     */
    public static boolean registerPlaceholder(Plugin plugin, String placeholder, PlaceholderReplacer replacer) {
        if (plugin == null)
            return false;
        if (placeholder == null)
            return false;
        if (placeholder.equals(""))
            return false;
        if (replacer == null)
            return false;
        Bukkit.getLogger().info("[MVdWPlaceholderAPI] " + plugin.getName() + " added custom placeholder {"
                + placeholder.toLowerCase() + "}");
        for (PlaceholderAddedEvent event : placeholderAddedHandlers) {
            event.onPlaceholderAdded(plugin, placeholder.toLowerCase(), replacer);
        }
        customPlaceholders.addOfflinePlaceholder(
                placeholder,
                "Custom MVdWPlaceholderAPI placeholder",
                false,
                new be.maximvdw.placeholderapi.internal.PlaceholderReplacer<String>(String.class,
                      replacer) {
                    @Override
                    public String getResult(String placeholder,
                                            OfflinePlayer player) {
                        be.maximvdw.placeholderapi.PlaceholderReplacer replacer = (be.maximvdw.placeholderapi.PlaceholderReplacer) getArguments()[0];
                        PlaceholderReplaceEvent event = new PlaceholderReplaceEvent(
                                player, placeholder);
                        return replacer.onPlaceholderReplace(event);
                    }
                });
        for (PlaceholderPack.PlaceholderRefreshHandler handler : PlaceholderPack
                .getRefreshHandles()) {
            handler.refreshPlaceholders(); // Refresh
        }
        return true; // Placeholder registered
    }

    /**
     * Register a static custom placeholder
     *
     * @param plugin      Plugin that is registering the placeholder
     * @param placeholder Placeholder to be registered WITHOUT { }
     * @param value       Placeholder value
     * @return Returns if the placeholder is added or not
     */
    public static boolean registerStaticPlaceholders(Plugin plugin, String placeholder, final String value) {
        if (plugin == null)
            return false;
        if (placeholder == null)
            return false;
        if (placeholder.equals(""))
            return false;
        PlaceholderReplacer replacer = new PlaceholderReplacer() {
            @Override
            public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
                return value;
            }
        };
        for (PlaceholderAddedEvent event : placeholderAddedHandlers) {
            event.onPlaceholderAdded(plugin, placeholder.toLowerCase(), replacer);
        }
        customPlaceholders.addOfflinePlaceholder(
                placeholder,
                "Custom MVdWPlaceholderAPI placeholder",
                false,
                new be.maximvdw.placeholderapi.internal.PlaceholderReplacer<String>(String.class,
                        replacer) {
                    @Override
                    public String getResult(String placeholder,
                                            OfflinePlayer player) {
                        be.maximvdw.placeholderapi.PlaceholderReplacer replacer = (be.maximvdw.placeholderapi.PlaceholderReplacer) getArguments()[0];
                        PlaceholderReplaceEvent event = new PlaceholderReplaceEvent(
                                player, placeholder);
                        return replacer.onPlaceholderReplace(event);
                    }
                });
        for (PlaceholderPack.PlaceholderRefreshHandler handler : PlaceholderPack
                .getRefreshHandles()) {
            handler.refreshPlaceholders(); // Refresh
        }
        return true; // Placeholder registered
    }

    public void setPlaceholderListener(PlaceholderAddedEvent handler) {
        placeholderAddedHandlers.add(handler);
    }

}

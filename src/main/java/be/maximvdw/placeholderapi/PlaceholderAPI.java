package be.maximvdw.placeholderapi;

import be.maximvdw.placeholderapi.events.PlaceholderAddedEvent;
import be.maximvdw.placeholderapi.internal.CustomPlaceholdersPack;
import be.maximvdw.placeholderapi.internal.PlaceholderPack;
import be.maximvdw.placeholderapi.internal.PlaceholderPlugin;
import be.maximvdw.placeholderapi.internal.ui.SendConsole;
import be.maximvdw.placeholderapi.internal.updater.MVdWUpdaterHook;
import be.maximvdw.placeholderapi.internal.utils.DateUtils;
import be.maximvdw.placeholderapi.internal.utils.NumberUtils;
import be.maximvdw.placeholderapi.internal.utils.bukkit.BukkitUtils;
import be.maximvdw.placeholderapi.internal.utils.chat.ColorUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * MVdWPlaceholderAPI
 *
 * @author Maxim Van de Wynckel (Maximvdw)
 */
public class PlaceholderAPI extends JavaPlugin{
    /* Placeholder container */
    private static List<PlaceholderPlugin> placeholderPlugins = new ArrayList<>();
    /* Custom placeholders registered in the API */
    private static PlaceholderPack customPlaceholders = null;
    /* Placeholder change listeners */
    private static List<PlaceholderAddedEvent> placeholderAddedHandlers = new ArrayList<PlaceholderAddedEvent>();

    @Override
    public void onEnable() {
        super.onEnable();
        new SendConsole(this);
        PlaceholderAPI.initialize(this);
        SendConsole.info("Initializing placeholders ...");

        // Prevent linkage errors
        new ColorUtils();
        new BukkitUtils();
        new DateUtils();
        new NumberUtils();

        int resource = 11182;
        try {
            if (Bukkit.getPluginManager().isPluginEnabled("MVdWUpdater"))
                new MVdWUpdaterHook(this, resource);
        } catch (Throwable ex) {
            // No updater
        }

        SendConsole.info("Sending metrics ...");
        try {
            new Metrics(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void initialize(PlaceholderAPI api) {
        customPlaceholders = new CustomPlaceholdersPack(api);
    }

    /**
     * Register an MVdW Plugin
     *
     * @param plugin            Plugin
     * @param placeholderPlugin Placeholder plugin container
     * @return success
     */
    public boolean registerMVdWPlugin(Plugin plugin, PlaceholderPlugin placeholderPlugin) {
        if (customPlaceholders == null){
            SendConsole.severe("Unable to hook into MVdW Placeholder plugin!");
        }
        if (!placeholderPlugins.contains(placeholderPlugin)) {
            placeholderPlugin.registerPlaceHolder(customPlaceholders);
            placeholderPlugins.add(placeholderPlugin);
            SendConsole.info("Hooked into MVdW plugin: " + plugin.getName());
            return true;
        } else {
            placeholderPlugin.registerPlaceHolder(customPlaceholders);
            SendConsole.info("Hooked into MVdW plugin again: " + plugin.getName());
            return false;
        }
    }

    /**
     * Replace placeholders in input
     *
     * @param offlinePlayer Player to replace placeholders for
     * @param input         Placeholder format {placeholder}
     * @return Return result with replaced placeholders
     */
    public static String replacePlaceholders(OfflinePlayer offlinePlayer, String input) {
        if (placeholderPlugins.size() == 0) {
           return input;
        }
        return placeholderPlugins.get(0).getPlaceholderResult(input,
                offlinePlayer);
    }

    /**
     * Returns the amount of placeholders loaded into the memory
     *
     * @return Placeholder count
     */
    public static int getLoadedPlaceholderCount() {
        if (placeholderPlugins.size() == 0) {
            return 0;
        }
        return placeholderPlugins.get(0).getPlaceHolderCount();
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
        SendConsole.info(plugin.getName() + " added custom placeholder {"
                + placeholder.toLowerCase() + "}");
        customPlaceholders.addOfflinePlaceholder(
                placeholder,
                "Custom MVdWPlaceholderAPI placeholder",
                false,
                new be.maximvdw.placeholderapi.internal.PlaceholderReplacer<String>(String.class,
                        replacer) {
                    @Override
                    public String getResult(PlaceholderReplaceEvent event) {
                        PlaceholderReplacer replacer = (PlaceholderReplacer) getArguments()[0];
                        return replacer.onPlaceholderReplace(event);
                    }
                });
        for (PlaceholderAddedEvent event : placeholderAddedHandlers) {
            if (event != null)
                event.onPlaceholderAdded(plugin, placeholder.toLowerCase(), replacer);
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
        customPlaceholders.addOfflinePlaceholder(
                placeholder,
                "Custom MVdWPlaceholderAPI placeholder",
                false,
                new be.maximvdw.placeholderapi.internal.PlaceholderReplacer<String>(String.class,
                        replacer) {
                    @Override
                    public String getResult(PlaceholderReplaceEvent replaceEvent) {
                        PlaceholderReplacer replacer = (PlaceholderReplacer) getArguments()[0];
                        return replacer.onPlaceholderReplace(replaceEvent);
                    }
                });
        for (PlaceholderAddedEvent event : placeholderAddedHandlers) {
            if (event != null)
                event.onPlaceholderAdded(plugin, placeholder.toLowerCase(), replacer);
        }
        return true; // Placeholder registered
    }

    /**
     * Add a placeholder listener
     *
     * @param handler placeholder added handler
     */
    public void addPlaceholderListener(PlaceholderAddedEvent handler) {
        placeholderAddedHandlers.add(handler);
    }
}

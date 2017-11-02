package be.maximvdw.placeholderapi.internal;

import org.bukkit.OfflinePlayer;

import java.util.List;

/**
 * PlaceholderPlugin
 * Created by Maxim on 2/11/2017.
 */
public interface PlaceholderPlugin {
    /**
     * Get placeholder result
     *
     * @param input
     * @param player
     * @return
     */
    String getPlaceholderResult(String input, OfflinePlayer player);

    /**
     * Get a list of all placeholders
     *
     * @return PlaceholderPack list
     */
    List<PlaceholderPack> getPlaceholderPacks();

    /**
     * Get enabled placeholder count
     *
     * @return amount
     */
    int getPlaceHolderEnabledCount();

    /**
     * Get placeholder count
     * @return placeholder count
     */
    int getPlaceHolderCount();

    /**
     * Register a placeholderPack
     *
     * @param placeholderPack Place holder
     */
    void registerPlaceHolder(PlaceholderPack placeholderPack);
}

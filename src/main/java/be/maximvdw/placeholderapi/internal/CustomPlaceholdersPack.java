package be.maximvdw.placeholderapi.internal;

import be.maximvdw.placeholderapi.internal.annotations.*;
import org.bukkit.plugin.Plugin;

/**
 * CustomPlaceholdersPack
 * Created by Maxim on 3/10/2017.
 */

@ModuleName("CustomPlaceholdersPack")
@ModuleAuthor("Maximvdw")
@ModuleVersion("2.0.0")
@ModuleDescription("MVdWPlaceholderAPI placeholders")
@ModulePermalink("https://www.spigotmc.org/resources/mvdwplaceholderapi.11182/")
public class CustomPlaceholdersPack extends PlaceholderPack {
    private static CustomPlaceholdersPack instance = null;

    public static CustomPlaceholdersPack getInstance() {
        return instance;
    }

    public CustomPlaceholdersPack(Plugin plugin) {
        super(plugin, 1);
        setEnabled(true);
        instance = this;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onEnable() {

    }
}

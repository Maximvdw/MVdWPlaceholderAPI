package be.maximvdw.placeholderapi;

import be.maximvdw.placeholderapi.internal.eventhooks.BaseEventHook;
import be.maximvdw.placeholderapi.internal.eventhooks.TriggerEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EventAPI {

    /**
     * Trigger an event on another plugin
     *
     * @param plugin Plugin instance (FeatherBoard, AnimatedNames,...) not your plugin
     * @param player Player to trigger the action for
     * @param action Action to trigger(scoreboard, group ,...)
     * @param enable Enable or disable event?
     * @return True when success
     */
    public static boolean triggerEvent(Plugin plugin, Player player, String action, boolean enable){
        TriggerEvent event = BaseEventHook.getHook(plugin.getClass().getProtectionDomain().getCodeSource().getLocation());
        if (event == null){
            return false; // Plugin is not found. Are you sure you have not passed your own plugin instance
        }
        if (enable){
            event.enableEvent(player,action);
        }else{
            event.disableEvent(player,action);
        }
        return true;
    }

}

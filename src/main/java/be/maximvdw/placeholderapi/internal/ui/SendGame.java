package be.maximvdw.placeholderapi.internal.ui;

import be.maximvdw.placeholderapi.internal.utils.bukkit.BukkitUtils;
import be.maximvdw.placeholderapi.internal.utils.chat.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import java.util.Set;

/**
 * SendGame
 * <p>
 * Send a message to ingame.
 *
 * @author Maxim Van de Wynckel (Maximvdw)
 * @version 1.0
 * @project BasePlugin
 * @site http://www.mvdw-software.be/
 */
public class SendGame {

    /**
     * Send a private message
     *
     * @param message Message to send
     */
    public static void toPlayer(String message, Player player) {
        message = ColorUtils.toColors(message); // Color the message
        player.sendMessage(message);
    }


    /**
     * Send a private message
     *
     * @param message Message to send
     */
    public static void toPlayers(String message, Set<Player> players) {
        for (Player player : players) {
            toPlayer(message, player);
        }
    }


    /**
     * Send a private message
     *
     * @param message    Message to send
     * @param permission Permission
     */
    public static void toPermission(String message, String permission) {
        message = ColorUtils.toColors(message); // Color the message
        Bukkit.broadcast(message, permission);
    }

    /**
     * Send a broadcast to the server
     *
     * @param message Message to broadcast
     */
    public static void toServer(String message) {
        message = ColorUtils.toColors(message); // Color the message
        Bukkit.broadcastMessage(message);
    }

    /**
     * Send a message to a specific world
     *
     * @param message Message
     * @param world   World
     */
    public static void toWorld(String message, World world) {
        message = ColorUtils.toColors(message); // Color the message
        for (Player player : BukkitUtils.getOnlinePlayers()) {
            if (player.getWorld().equals(world)) {
                player.sendMessage(message);
            }
        }
    }

    /**
     * Send a message to a specific world
     *
     * @param message Message
     * @param world   World
     */
    public static void toWorldGroup(String message, World world) {
        message = ColorUtils.toColors(message); // Color the message
        for (Player player : BukkitUtils.getOnlinePlayers()) {
            if (player.getWorld().equals(world)
                    || player.getWorld().equals(
                    Bukkit.getWorld(world.getName() + "_nether"))
                    || player.getWorld().equals(
                    Bukkit.getWorld(world.getName() + "_the_end"))) {
                player.sendMessage(message);
            }
        }
    }

}

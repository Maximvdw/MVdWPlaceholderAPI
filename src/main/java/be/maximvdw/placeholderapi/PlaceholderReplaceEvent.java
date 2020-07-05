package be.maximvdw.placeholderapi;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Placeholder replace event
 *
 * @author Maxim Van de Wynckel
 */
public class PlaceholderReplaceEvent {
    private OfflinePlayer offlinePlayer = null; // The offline player if player is not null
    private OfflinePlayer offlineViewingPlayer = null; // The offline player that is viewing the user. In most cases this is the user itself
    private String placeholder = ""; // The placeholder (with or without wildcards)

    public PlaceholderReplaceEvent(OfflinePlayer offlinePlayer,
                                   String placeholder) {
        setOfflineViewingPlayer(offlinePlayer);
        setOfflinePlayer(offlinePlayer);
        setPlaceholder(placeholder);
    }

    public PlaceholderReplaceEvent(OfflinePlayer offlinePlayer, OfflinePlayer viewingPlayer,
                                   String placeholder) {
        setOfflineViewingPlayer(viewingPlayer);
        setOfflinePlayer(offlinePlayer);
        setPlaceholder(placeholder);
    }


    /**
     * Get the placeholder that is requested to be replaced
     *
     * @return Placeholder without { }
     */
    public String getPlaceholder() {
        return placeholder;
    }

    private void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * Get the offline player instance
     *
     * @return Offline player
     */
    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    private void setOfflinePlayer(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    /**
     * Get the offline viewing player instance
     *
     * @return Offline player
     */
    public OfflinePlayer getOfflineViewingPlayer() {
        return offlineViewingPlayer;
    }

    private void setOfflineViewingPlayer(OfflinePlayer offlinePlayer) {
        this.offlineViewingPlayer = offlinePlayer;
    }

    /**
     * Get the online player instance
     *
     * @return Online player
     */
    public Player getPlayer() {
        if (offlinePlayer != null) {
            if (offlinePlayer.isOnline())
                return getOfflinePlayer().getPlayer();
        }
        return null;
    }

    /**
     * Get the online player instance
     *
     * @return Online viewing player
     */
    public Player getViewingPlayer() {
        if (offlineViewingPlayer != null) {
            if (offlineViewingPlayer.isOnline())
                return getOfflineViewingPlayer().getPlayer();
        }
        return null;
    }

    /**
     * Check if the player is online
     *
     * @return Player is online
     */
    public boolean isOnline() {
        if (offlinePlayer == null)
            return false;
        return offlinePlayer.isOnline();
    }
}

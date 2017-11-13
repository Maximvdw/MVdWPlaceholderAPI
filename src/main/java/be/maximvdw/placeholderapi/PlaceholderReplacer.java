package be.maximvdw.placeholderapi;

/**
 * PlaceholderReplacer interface
 *
 * @author Maxim Van de Wynckel
 */
public interface PlaceholderReplacer {
    /**
     * On placeholder replac event
     *
     * @param event PlaceholderReplaceEvent holding the player and placeholder
     * @return Return result of the placeholder
     */
    String onPlaceholderReplace(PlaceholderReplaceEvent event);
}

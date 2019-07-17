package be.maximvdw.placeholderapi.internal;

import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public abstract class PlaceholderReplacer<T> {
    private String description = "";
    private boolean online = true;
    private boolean requiresPlayer = false;
    protected Object[] args = null;
    private Class<T> returnType = null;
    private DataType dataType = DataType.NORMAL;
    private T defaultOutput = null;
    private String defaultTrueOutput = null;
    private String defaultFalseOutput = null;

    public PlaceholderReplacer(Class<T> type) {
        this.returnType = type;
    }

    public PlaceholderReplacer(Class<T> type, Object... args) {
        this.returnType = type;
        this.args = args;
    }

    /**
     * Get the return class type
     *
     * @return class type
     */
    public Class<T> getReturnType() {
        return returnType;
    }

    /**
     * Get description
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set placeholder description
     *
     * @param description Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get placeholder result
     *
     * @param event Placeholder replace event
     * @return placeholder result
     */
    public T getResult(PlaceholderReplaceEvent event){
        // Will be implemented by newer placeholder versions
        // Will not be called by older versions
        return null;
    }

    @Deprecated
    public T getResult(String placeholder, OfflinePlayer player){
        PlaceholderReplaceEvent replaceEvent = new PlaceholderReplaceEvent(player,placeholder);
        return getResult(replaceEvent);
    }

    /**
     * Get placeholder result
     *
     * @param player
     *            Player
     * @return placeholder result
     */
    @Deprecated
    public T getResult(String placeholder, Player player){
        return getResult(placeholder,(OfflinePlayer) player);
    }

    public Object[] getArguments() {
        return args;
    }

    /**
     * Check if a player is required
     *
     * @return requires player
     */
    public boolean isPlayerRequired() {
        return requiresPlayer;
    }

    public void setRequiresPlayer(boolean requiresPlayer) {
        this.requiresPlayer = requiresPlayer;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public PlaceholderReplacer<T> setDataType(DataType type) {
        this.dataType = type;
        return this;
    }

    public DataType getDataType() {
        return dataType;
    }

    public T getDefaultOutput() {
        return defaultOutput;
    }

    public PlaceholderReplacer<T> setDefaultOutput(T defaultOutput) {
        this.defaultOutput = defaultOutput;
        return this;
    }

    public String getDefaultTrueOutput() {
        return defaultTrueOutput;
    }

    public PlaceholderReplacer<T> setDefaultTrueOutput(String defaultTrueOutput) {
        this.defaultTrueOutput = defaultTrueOutput;
        return this;
    }

    public String getDefaultFalseOutput() {
        return defaultFalseOutput;
    }

    public PlaceholderReplacer<T> setDefaultFalseOutput(String defaultFalseOutput) {
        this.defaultFalseOutput = defaultFalseOutput;
        return this;
    }

    public enum DataType {
        NORMAL, BOOLEAN, UNIXTIME
    }
}

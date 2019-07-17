package be.maximvdw.placeholderapi.internal.eventhooks;

import be.maximvdw.placeholderapi.internal.storage.YamlBuilder;
import be.maximvdw.placeholderapi.internal.storage.YamlBuilder.YamlEmptyPart;
import be.maximvdw.placeholderapi.internal.storage.YamlStorage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseEventHook implements Listener {
    private static Map<Object, TriggerEvent> hooks = new HashMap<>();
    private Map<EventCondition, String> eventConditions = new HashMap<EventCondition, String>();
    /* Plugin information */
    private String name = "";
    private String url = "";
    private String description = "";
    private String shortName = "";
    private String when = "";
    private String who = "";
    private boolean enabled = true;

    private Map<Player, EventPlaceholderContainer> placeholders = new HashMap<Player, EventPlaceholderContainer>();

    private YamlBuilder configTemplate = null;
    private int configVersion = 1;
    private Plugin plugin = null;

    private YamlStorage storage = null;

    // TODO: Workaround legacy
    private Object legacyPlugin = null;

    public BaseEventHook(Plugin plugin, String shortName, int version) {
        setPlugin(plugin);
        this.legacyPlugin = plugin.getClass().getProtectionDomain().getCodeSource().getLocation();
        setShortName(shortName);
        this.configVersion = version;
    }

    public int getConfigVersion() {
        return configVersion;
    }

    public Map<EventCondition, String> getEventConditions() {
        return this.eventConditions;
    }

    public void loadConfig() {
        if (storage.getConfig().getBoolean("enabled")) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }

    public abstract void start();

    public void generateConfig() {
        if (getConfigTemplate() == null) {
            YamlBuilder builder = new YamlBuilder();
            builder.addPart("----------------------------------------");
            builder.addPart(" " + getName());
            builder.addPart(" " + getDescription());
            builder.addPart(" " + getUrl());
            builder.addPart("----------------------------------------");
            builder.addPart(new YamlEmptyPart());
            builder.addPart(" DO NOT EDIT THIS CONFIG VERSION!");
            builder.addPart("config", configVersion);
            builder.addPart(new YamlEmptyPart());
            builder.addPart(new YamlEmptyPart());
            builder.addPart(" Do you want to enable this event hook?");
            builder.addPart(" Enabling this hook will trigger");
            builder.addPart(" when " + getWhen());
            builder.addPart(" for " + getWho());
            builder.addPart("enabled", false);
            builder.addPart(new YamlEmptyPart());
            builder.addPart(" What do you want to trigger?");
            builder.addPart("trigger", "default");
            builder.addPart(new YamlEmptyPart());
            builder.addPart(new YamlEmptyPart());
            setConfigTemplate(builder);
        }
    }

    public void enableEvent(Player player) {
        TriggerEvent event = getHook(legacyPlugin);
        if (event != null)
            event.enableEvent(player, getConfig().getString("trigger"));
    }

    public void disableEvent(Player player) {
        TriggerEvent event = getHook(legacyPlugin);
        if (event != null)
            event.disableEvent(player, getConfig().getString("trigger"));
    }

    @Deprecated
    public static void registerTriggerEvent(TriggerEvent event) {
        // TODO: Workaround for backwards compatibility. Replace with plugin in the future
        Object plugin = event.getClass().getProtectionDomain().getCodeSource().getLocation();
        hooks.put(plugin, event);
    }

    public static TriggerEvent getHook(Object plugin) {
        return hooks.get(plugin);
    }

    /**
     * Get config
     *
     * @return Config
     */
    public YamlConfiguration getConfig() {
        return storage.getConfig();
    }

    /**
     * Get event hook name
     *
     * @return Hook name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public YamlStorage getStorage() {
        return storage;
    }

    public void setStorage(YamlStorage storage) {
        this.storage = storage;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String action) {
        this.when = action;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String playersAction) {
        this.who = playersAction;
    }

    public YamlBuilder getConfigTemplate() {
        return configTemplate;
    }

    public void setConfigTemplate(YamlBuilder configTemplate) {
        this.configTemplate = configTemplate;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Add a placeholder condition
     *
     * @param condition Condition
     * @param value     Value
     */
    public BaseEventHook addCondition(EventCondition condition, String value) {
        eventConditions.put(condition, value);
        return this;
    }

    public Map<Player, EventPlaceholderContainer> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Map<Player, EventPlaceholderContainer> placeholders) {
        this.placeholders = placeholders;
    }

    public static enum EventCondition {
        PLUGIN, VERSION, AUTHOR, OFFLINEPLAYER, PLAYER, MAIN, VERSION_IS_LOWER, VERSION_IS_HIGHER
    }

    public class EventPlaceholderContainer {
        private Map<String, Object> placeholders = new HashMap<String, Object>();

        public Map<String, Object> getPlaceholders() {
            return placeholders;
        }

        public void setPlaceholders(Map<String, Object> placeholders) {
            this.placeholders = placeholders;
        }

        public EventPlaceholderContainer addStaticPlaceholder(String key, Object value) {
            placeholders.put(key, value);
            return this;
        }
    }
}

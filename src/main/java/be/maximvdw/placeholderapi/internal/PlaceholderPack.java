package be.maximvdw.placeholderapi.internal;

import be.maximvdw.placeholderapi.internal.annotations.*;
import be.maximvdw.placeholderapi.internal.storage.YamlBuilder;
import be.maximvdw.placeholderapi.internal.storage.YamlStorage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InvalidClassException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * PlaceholderPack
 *
 * Created by Maxim on 2/10/2017.
 */

public abstract class PlaceholderPack {
    private String id = "";
    private String actionName = "";
    private String author = "";
    private String version = "";
    private boolean module = false;
    private File jarFile = null;

    /**
     * PlaceholderPack string
     */
    private Map<String, PlaceholderReplacer<?>> placeholders = new ConcurrentHashMap<>();
    /**
     * Plugin instance
     */
    protected Plugin plugin = null;
    /**
     * PlaceholderPack settings
     */
    private YamlStorage storage = null;
    private YamlBuilder configBuilder = null;
    private String name = "";
    private List<ModuleConstraint> placeholderConditions = new ArrayList<>();
    private boolean enabled = true;
    private boolean actionPlaceholder = false;
    private boolean containsWildcards = false;
    private String description = "";
    private String pluginURL = "";
    private int configVersion = 1;

    public PlaceholderPack(Plugin plugin, int version) {
        this.plugin = plugin;
        setConfigVersion(version);
        // Check annotations
        Class<? extends PlaceholderPack> componentClass = this.getClass().asSubclass(PlaceholderPack.class);
        // Load the module constraints
        Annotation[] annotations = componentClass.getAnnotations();
        if (annotations.length == 0) {
            new InvalidClassException("PlaceholderPack does not contain annotation information!").printStackTrace();
            return;
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof ModuleConstraint) {
                addCondition((ModuleConstraint) annotation);
            } else if (annotation instanceof ModuleVersion) {
                setVersion(((ModuleVersion)annotation).value());
            } else if (annotation instanceof ModuleConstraints) {
                ModuleConstraint[] subConstraints = ((ModuleConstraints) annotation).value();
                for (ModuleConstraint subConstraint : subConstraints) {
                    addCondition(subConstraint);
                }
            } else if (annotation instanceof ModuleName) {
                if (actionName.equals("")) {
                    actionName = ((ModuleName) annotation).value().toLowerCase();
                    name = actionName;
                }
            } else if (annotation instanceof ModuleActionName) {
                actionName = ((ModuleActionName)annotation).value().toLowerCase();
            }
        }
    }

    public PlaceholderPack() {
        this(null, 1);
    }


    /**
     * Triggers on disable
     */
    public void onDisable() {

    }

    /**
     * Triggers on delete
     */
    public void onDelete() {

    }

    /**
     * Triggers on enable
     */
    public void onEnable() {

    }

    /**
     * Get config
     *
     * @return Config
     */
    public YamlConfiguration getConfig() {
        if (storage == null)
            return null;
        return storage.getConfig();
    }

    public YamlStorage getStorage(){
        return storage;
    }

    public void setStorage(YamlStorage storage){
        this.storage = storage;
    }

    /**
     * Get plugin
     *
     * @return Plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }


    public PlaceholderReplacer<?> getPlaceholderReplacer(String placeholder) {
        if (placeholders.containsKey(placeholder.toLowerCase()))
            return placeholders.get(placeholder.toLowerCase());
        return null;
    }

    /**
     * Get placeholder
     *
     * @return placeholder
     */
    public Set<String> getOfflinePlaceholder() {
        Set<String> placeholdersList = new HashSet<String>();
        for (String placeholder : getPlaceholder()) {
            if (placeholders.containsKey(placeholder.toLowerCase()))
                if (!placeholders.get(placeholder.toLowerCase()).isOnline())
                    placeholdersList.add(placeholder);
        }
        return placeholdersList;
    }

    /**
     * Get placeholder
     *
     * @return placeholder
     */
    public Set<String> getOnlinePlaceholder() {
        Set<String> placeholdersList = new HashSet<String>();
        for (String placeholder : getPlaceholder()) {
            if (placeholders.containsKey(placeholder.toLowerCase()))
                if (placeholders.get(placeholder.toLowerCase()).isOnline())
                    placeholdersList.add(placeholder);
        }
        return placeholdersList;
    }

    /**
     * Get placeholder
     *
     * @return placeholder
     */
    public Set<String> getPlaceholder() {
        return placeholders.keySet();
    }

    public Map<String, String> getPlaceholderDescriptions() {
        Map<String, String> placeholdersList = new HashMap<String, String>();
        for (String placeholder : placeholders.keySet()) {
            String description = placeholders.get(placeholder).getDescription();
            placeholdersList.put(placeholder, description);
        }
        return placeholdersList;
    }

    /**
     * Add a placeholder condition
     *
     * @param condition Condition
     */
    private PlaceholderPack addCondition(ModuleConstraint condition) {
        placeholderConditions.add(condition);
        return this;
    }

    /**
     * Set placeholder
     *
     * @param placeholder Place holder
     * @param description Description
     */
    public PlaceholderPack addPlaceholder(String placeholder, String description, OnlinePlaceholderReplacer<?> replacer) {
        return addPlaceholder(placeholder, description, (PlaceholderReplacer<?>) replacer);
    }

    /**
     * Set placeholder
     *
     * @param placeholder Place holder
     * @param description Description
     */
    public PlaceholderPack addPlaceholder(String placeholder, String description, PlaceholderReplacer<?> replacer) {
        replacer.setDescription(description);
        replacer.setOnline(true);
        replacer.setRequiresPlayer(true);
        if (replacer.getReturnType().equals(Location.class)) {
            PlaceholderConversion.convertOnlineLocationPlaceholders(this, placeholder, description, replacer);
            return this;
        } else if (replacer.getReturnType().equals(OfflinePlayer.class)) {
            PlaceholderConversion.convertOnlineOfflinePlayerPlaceholders(this, placeholder, description, replacer);
            return this;
        } else if (replacer.getReturnType().equals(ItemStack.class)) {
            PlaceholderConversion.convertOnlineItemPlaceholders(this, placeholder, description, replacer);
            return this;
        } else if (replacer.getReturnType().equals(String[].class)) {
            PlaceholderConversion.convertOnlineStringListPlaceholders(this, placeholder, description, replacer);
            return this;
        } else if (replacer.getReturnType().equals(Integer[].class)) {
            PlaceholderConversion.convertOnlineIntegerListPlaceholders(this, placeholder, description, replacer);
            return this;
        } else if (replacer.getReturnType().equals(Boolean[].class)) {
            PlaceholderConversion.convertOnlineBooleanListPlaceholders(this, placeholder, description, replacer);
            return this;
        } else if (replacer.getReturnType().equals(World.class)) {
            PlaceholderConversion.convertOnlineWorldPlaceholder(this, placeholder, description, replacer);
            return this;
        } else if (replacer.getReturnType().equals(Calendar.class)) {
            PlaceholderConversion.convertOnlineCalendarPlaceholders(this, placeholder, description, replacer);
            return this;
        } else if (replacer.getReturnType().equals(Boolean.class)) {
            PlaceholderConversion.convertOnlineBooleanPlaceholders(this, placeholder, description, replacer);
            return this;
        }
        if (!placeholder.startsWith("{"))
            placeholder = "{" + placeholder;
        if (!placeholder.endsWith("}"))
            placeholder += "}";
        placeholders.put(placeholder.toLowerCase(), replacer);
        if (placeholder.contains("*"))
            setContainsWildcards(true);
        return this;
    }

    /**
     * Set placeholder
     *
     * @param placeholder Place holder
     * @param description Description
     */
    public PlaceholderPack addOfflinePlaceholder(String placeholder, String description, boolean requiresplayer,
                                                 PlaceholderReplacer<?> replacer) {
        replacer.setDescription(description);
        replacer.setOnline(false);
        replacer.setRequiresPlayer(requiresplayer);
        if (replacer.getReturnType().equals(Location.class)) {
            PlaceholderConversion.convertOfflineLocationPlaceholders(this, placeholder, description, requiresplayer, replacer);
            return this;
        } else if (replacer.getReturnType().equals(OfflinePlayer.class)) {
            PlaceholderConversion.convertOfflineOfflinePlayerPlaceholders(this, placeholder, description, requiresplayer, replacer);
            return this;
        } else if (replacer.getReturnType().equals(ItemStack.class)) {
            PlaceholderConversion.convertOfflineItemPlaceholders(this, placeholder, description, requiresplayer, replacer);
            return this;
        } else if (replacer.getReturnType().equals(String[].class)) {
            PlaceholderConversion.convertOfflineStringListPlaceholders(this, placeholder, description, requiresplayer, replacer);
            return this;
        } else if (replacer.getReturnType().equals(Integer[].class)) {
            PlaceholderConversion.convertOfflineIntegerListPlaceholders(this, placeholder, description, requiresplayer, replacer);
            return this;
        } else if (replacer.getReturnType().equals(Boolean[].class)) {
            PlaceholderConversion.convertOfflineBooleanListPlaceholders(this, placeholder, description, requiresplayer, replacer);
            return this;
        } else if (replacer.getReturnType().equals(World.class)) {
            PlaceholderConversion.convertOfflineWorldPlaceholder(this, placeholder, description, requiresplayer, replacer);
            return this;
        } else if (replacer.getReturnType().equals(Calendar.class)) {
            PlaceholderConversion.convertOfflineCalendarPlaceholders(this, placeholder, description, requiresplayer, replacer);
            return this;
        } else if (replacer.getReturnType().equals(Boolean.class)) {
            PlaceholderConversion.convertOfflineBooleanPlaceholders(this, placeholder, description, requiresplayer, replacer);
            return this;
        }
        if (!placeholder.startsWith("{"))
            placeholder = "{" + placeholder;
        if (!placeholder.endsWith("}"))
            placeholder += "}";
        placeholders.put(placeholder.toLowerCase(), replacer);
        if (placeholder.contains("*"))
            setContainsWildcards(true);
        return this;
    }

    public void generateConfig() {
        if (getConfigBuilder() != null)
            return;
        this.configBuilder = new YamlBuilder();
        getConfigBuilder().addPart("# --------------------------------------- ##");
        getConfigBuilder().addPart("# " + getDescription() + " Placeholders");
        if (!getPluginURL().equals(""))
            getConfigBuilder().addPart("# " + getPluginURL());
        getConfigBuilder().addPart("# About: This configuration allows you to");
        getConfigBuilder().addPart("#        configure what certain placeholders return.");
        getConfigBuilder().addPart("# --------------------------------------- ##");
        getConfigBuilder().addEmptyPart();
        getConfigBuilder().addPart(" DO NOT EDIT THE CONFIG VERSION");
        getConfigBuilder().addPart("config", getConfigVersion());
        getConfigBuilder().addEmptyPart();
        getConfigBuilder().addPart(" Enable/Disable the placeholder group");
        getConfigBuilder().addPart(" PlaceholderPack groups will not be loaded into the memory");
        getConfigBuilder().addPart(" when not used.");
        getConfigBuilder().addPart("enabled", true);
        getConfigBuilder().addEmptyPart();
        for (Map.Entry<String, PlaceholderReplacer<?>> entry : placeholders.entrySet()) {
            String placeholderString = entry.getKey().replace("{", "").replace("}", "")
                    .replace("*", "").replace("@", "").replace("#", "");
            PlaceholderReplacer<?> replacerVal = entry.getValue();
            if (replacerVal.getDefaultTrueOutput() != null) {
                // Create true or false
                getConfigBuilder().addPart(" Define what the boolean placeholder {" + placeholderString + "} returns");
                YamlBuilder.YamlSectionPart placeholderSection = new YamlBuilder.YamlSectionPart(placeholderString);
                if (replacerVal.getDefaultOutput() != null) {
                    placeholderSection.addPart(" Default return value");
                    placeholderSection.addPart("default", replacerVal.getDefaultOutput());
                }

                placeholderSection.addPart(" This will be shown when the placeholder is 'True'");
                placeholderSection.addPart("true",
                        replacerVal.getDefaultTrueOutput() == null ? "True" : replacerVal.getDefaultTrueOutput());
                placeholderSection.addPart(" This will be shown when the placeholder is 'False'");
                placeholderSection.addPart("false",
                        replacerVal.getDefaultFalseOutput() == null ? "False" : replacerVal.getDefaultFalseOutput());

                getConfigBuilder().addPart(placeholderSection);
                getConfigBuilder().addEmptyPart();
            } else if (replacerVal.getDefaultOutput() != null) {
                getConfigBuilder().addPart(" PlaceholderPack settings {" + placeholderString + "} returns");
                YamlBuilder.YamlSectionPart placeholderSection = new YamlBuilder.YamlSectionPart(placeholderString);
                placeholderSection.addPart(" Default return value");
                placeholderSection.addPart("default", replacerVal.getDefaultOutput());
                getConfigBuilder().addPart(placeholderSection);
                getConfigBuilder().addEmptyPart();
            }
        }
    }

    public abstract void initialize();

    public boolean isOffline(String placeholder) {
        return (!getPlaceholderReplacer(placeholder).isOnline());
    }

    public String getName() {
        return name;
    }

    /**
     * Set placeholder name
     *
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Remove a placeholder replacer
     *
     * @param placeholder PlaceholderPack
     */
    public void removePlaceholder(String placeholder) {
        placeholders.remove(placeholder);
    }

    public List<ModuleConstraint> getPlaceholderConditions() {
        return placeholderConditions;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isActionPlaceholder() {
        return actionPlaceholder;
    }

    public void setActionPlaceholder(boolean actionPlaceholder) {
        this.actionPlaceholder = actionPlaceholder;
    }

    public boolean hasWildcards() {
        return containsWildcards;
    }

    public void setContainsWildcards(boolean containsWildcards) {
        this.containsWildcards = containsWildcards;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getPluginURL() {
        return pluginURL;
    }

    private void setPluginURL(String pluginURL) {
        this.pluginURL = pluginURL;
    }

    public YamlBuilder getConfigBuilder() {
        return configBuilder;
    }

    public void setConfigBuilder(YamlBuilder configBuilder) {
        this.configBuilder = configBuilder;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public File getJarFile() {
        return jarFile;
    }

    public void setJarFile(File jarFile) {
        this.jarFile = jarFile;
    }

    public boolean isModule() {
        return module;
    }

    public void setModule(boolean module) {
        this.module = module;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(int configVersion) {
        this.configVersion = configVersion;
    }
}

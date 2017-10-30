package be.maximvdw.placeholderapi.internal;

import be.maximvdw.placeholderapi.internal.annotations.ModuleActionName;
import be.maximvdw.placeholderapi.internal.annotations.ModuleConstraint;
import be.maximvdw.placeholderapi.internal.annotations.ModuleConstraints;
import be.maximvdw.placeholderapi.internal.annotations.ModuleName;
import be.maximvdw.placeholderapi.internal.hooks.PluginHook;
import be.maximvdw.placeholderapi.internal.storage.YamlBuilder;
import be.maximvdw.placeholderapi.internal.storage.YamlStorage;
import be.maximvdw.placeholderapi.internal.ui.SendConsole;
import be.maximvdw.placeholderapi.internal.utils.bukkit.Version;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.InvalidClassException;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * PlaceholderPack list
     */
    private static List<PlaceholderPack> placeholderPackGroups = new ArrayList<PlaceholderPack>();
    private static List<PlaceholderRefreshHandler> refreshHandles = new ArrayList<PlaceholderRefreshHandler>();
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
    private static boolean ignoreProblems = false;
    private static Pattern pattern = Pattern
            .compile("\\{(.([^{}]+|[^{}])?)}",
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL);


    public PlaceholderPack(Plugin plugin, int version) {
        this.plugin = plugin;
        setConfigVersion(version);
        // Check annotations
        Class<? extends PlaceholderPack> componentClass = this.getClass().asSubclass(PlaceholderPack.class);
        // Load the module constraints
        String actionName = "";
        Annotation[] annotations = componentClass.getAnnotations();
        if (annotations.length == 0) {
            new InvalidClassException("PlaceholderPack does not contain annotation information!").printStackTrace();
            return;
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof ModuleConstraint) {
                addCondition((ModuleConstraint) annotation);
            } else if (annotation instanceof ModuleConstraints) {
                ModuleConstraint[] subConstraints = ((ModuleConstraints) annotation).value();
                for (ModuleConstraint subConstraint : subConstraints) {
                    addCondition(subConstraint);
                }
            } else if (annotation instanceof ModuleName) {
                if (actionName.equals("")) {
                    actionName = ((ModuleName) annotation).value().toLowerCase();
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

    public static void clear() {
        placeholderPackGroups.clear();
    }

    public static void clearUnused() {
        List<PlaceholderPack> placeholderPacks = new ArrayList<PlaceholderPack>(placeholderPackGroups);
        for (PlaceholderPack placeholderPack : placeholderPacks) {
            if (!placeholderPack.isEnabled())
                placeholderPackGroups.remove(placeholderPack);
        }
    }

    /**
     * Get a list of all placeholders
     *
     * @return PlaceholderPack list
     */
    public static List<PlaceholderPack> getPlaceholders() {
        return placeholderPackGroups;
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

    /**
     * Get plugin
     *
     * @return Plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }

    public void update() {
        List<PlaceholderPack> placeholderPacks = new ArrayList<>(PlaceholderPack.placeholderPackGroups);
        for (PlaceholderPack p : placeholderPacks) {
            if (p.getName().equalsIgnoreCase(getName())) {
                PlaceholderPack.placeholderPackGroups.remove(p);
                PlaceholderPack.placeholderPackGroups.add(this);
            }
        }
    }

    public static boolean containsPlaceholders(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static TreeMap<String, String> getPlaceholders(TreeMap<String, String> existing, String raw, Player player) {
        return getPlaceholders(existing, raw, (OfflinePlayer) player);
    }

    public static String getPlaceholderResult(String input, OfflinePlayer player) {
        String result = input;
        TreeMap<String, String> placeholders = PlaceholderPack.getPlaceholders(new TreeMap<String, String>(), result,
                player);
        for (String key : placeholders.keySet()) {
            result = result.replaceAll(key, placeholders.get(key));
        }
        return result;
    }

    public static List<PlaceholderPack> getPlaceholderReplacers(String raw) {
        List<PlaceholderPack> replacers = new ArrayList<PlaceholderPack>();
        List<PlaceholderPack> enabledPlaceholderPacks = getEnabledPlaceholderGroups();
        Matcher matcher = pattern.matcher(raw);
        while (matcher.find()) {
            String placeholder = matcher.group(0).toLowerCase();
            boolean found = false;
            for (PlaceholderPack ph : enabledPlaceholderPacks) {
                boolean containsPlaceholder = ph.getPlaceholder().contains(placeholder);
                if (ph.containsWildcards && !containsPlaceholder) {
                    for (String placeHolderString : ph.getPlaceholder()) {
                        String testPlaceholder = placeHolderString;
                        if (testPlaceholder.contains("*"))
                            testPlaceholder = testPlaceholder.substring(0, testPlaceholder.indexOf('*'));
                        if (placeholder.startsWith(testPlaceholder.toLowerCase())) {
                            replacers.add(ph);
                            found = true;
                            break;
                        }
                    }
                } else {
                    if (containsPlaceholder && !placeholder.contains("*")) {
                        replacers.add(ph);
                        break;
                    }
                }
                if (found)
                    break;
            }
        }

        return replacers;
    }

    public static TreeMap<String, String> getPlaceholders(List<PlaceholderPack> cachedPlaceholderPacks, String raw,
                                                          OfflinePlayer player) {
        TreeMap<String, String> results = new TreeMap<String, String>();
        Matcher matcher = pattern.matcher(raw);
        while (matcher.find()) {
            String placeholder = matcher.group(0).toLowerCase();
            boolean found = false;
            for (PlaceholderPack ph : cachedPlaceholderPacks) {
                boolean containsPlaceholder = ph.getPlaceholder().contains(placeholder);
                if (ph.containsWildcards && !containsPlaceholder) {
                    for (String placeHolderString : ph.getPlaceholder()) {
                        String testPlaceholder = placeHolderString;
                        if (testPlaceholder.contains("*"))
                            testPlaceholder = testPlaceholder.substring(0, testPlaceholder.indexOf('*'));
                        if (placeholder.startsWith(testPlaceholder.toLowerCase())) {
                            String result = "";
                            String group = matcher.group(1);
                            try {
                                PlaceholderReplacer<?> replacer = ph.getPlaceholderReplacer(placeHolderString);
                                if (replacer != null) {
                                    if (!placeholder.contains("*")) {
                                        if (player == null)
                                            if (replacer.isOnline()) {
                                                result = "";
                                            } else {
                                                if (!replacer.isRequiresPlayer())
                                                    result = String.valueOf(replacer.getResult(group, player));
                                            }
                                        else if (player.isOnline())
                                            result = String.valueOf(replacer.getResult(group, player.getPlayer()));
                                        else
                                            result = String.valueOf(replacer.getResult(group, player));
                                    }
                                }
                            } catch (Exception ex) {
                                if (!isIgnoreProblems()) {
                                    // Error
                                    SendConsole.severe("Error in placeholder: " + placeholder);
                                    SendConsole.stacktrace(ex);
                                    ph.removePlaceholder(placeHolderString);
                                }
                            }
                            if (result == null)
                                result = "";
                            results.put("(?i)\\{" + group + "\\}", result);
                            found = true;
                            break;
                        }
                    }
                } else {
                    if (containsPlaceholder && !placeholder.contains("*")) {
                        String result = "";
                        String group = matcher.group(1);
                        try {
                            PlaceholderReplacer<?> replacer = ph.getPlaceholderReplacer(placeholder);
                            if (replacer != null) {
                                if (player == null)
                                    if (replacer.isOnline()) {
                                        result = "";
                                    } else {
                                        if (!replacer.isRequiresPlayer())
                                            result = String.valueOf(replacer.getResult(group, player));
                                    }
                                else if (player.isOnline())
                                    result = String.valueOf(replacer.getResult(group, player.getPlayer()));
                                else
                                    result = String.valueOf(replacer.getResult(group, player));
                            }
                        } catch (Throwable ex) {
                            // Error
                            SendConsole.severe("Error in placeholder: " + placeholder);
                            SendConsole.stacktraceLog(ex);
                            ph.removePlaceholder(placeholder);
                        }
                        if (result == null)
                            result = "";
                        results.put("(?i)\\{" + group + "\\}", result);
                        break;
                    }
                }
                if (found)
                    break;
            }
        }

        return results;
    }

    public static TreeMap<String, String> getPlaceholders(TreeMap<String, String> existing, String raw,
                                                          OfflinePlayer player) {
        TreeMap<String, String> results = new TreeMap<String, String>();
        results.putAll(existing);
        List<PlaceholderPack> enabledPlaceholderPacks = getEnabledPlaceholderGroups();
        Matcher matcher = pattern.matcher(raw);
        while (matcher.find()) {
            String placeholder = matcher.group(0).toLowerCase();
            boolean found = false;
            for (PlaceholderPack ph : enabledPlaceholderPacks) {
                boolean containsPlaceholder = ph.getPlaceholder().contains(placeholder);
                if (ph.containsWildcards && !containsPlaceholder) {
                    for (String placeHolderString : ph.getPlaceholder()) {
                        if (!existing.containsKey(placeHolderString)) {
                            String testPlaceholder = placeHolderString;
                            if (testPlaceholder.contains("*"))
                                testPlaceholder = testPlaceholder.substring(0, testPlaceholder.indexOf('*'));
                            if (placeholder.startsWith(testPlaceholder.toLowerCase())) {
                                String result = "";
                                String group = matcher.group(1);
                                try {
                                    PlaceholderReplacer<?> replacer = ph.getPlaceholderReplacer(placeHolderString);
                                    if (replacer != null) {
                                        if (!placeholder.contains("*")) {
                                            if (player == null)
                                                if (replacer.isOnline()) {
                                                    result = "";
                                                } else {
                                                    if (!replacer.isRequiresPlayer())
                                                        result = String.valueOf(replacer.getResult(group, player));
                                                }
                                            else if (player.isOnline())
                                                result = String.valueOf(replacer.getResult(group, player.getPlayer()));
                                            else
                                                result = String.valueOf(replacer.getResult(group, player));
                                        }
                                    }
                                } catch (Exception ex) {
                                    // Error
                                    SendConsole.severe("Error in placeholder: " + placeholder);
                                    SendConsole.stacktrace(ex);
                                    ph.removePlaceholder(placeHolderString);
                                }
                                if (result == null)
                                    result = "";
                                results.put("(?i)\\{" + group + "\\}", result);
                                found = true;
                                break;
                            }
                        }
                    }
                } else {
                    if (containsPlaceholder && !placeholder.contains("*")) {
                        String result = "";
                        String group = matcher.group(1);
                        try {
                            PlaceholderReplacer<?> replacer = ph.getPlaceholderReplacer(placeholder);
                            if (replacer != null) {
                                if (player == null)
                                    if (replacer.isOnline()) {
                                        result = "";
                                    } else {
                                        if (!replacer.isRequiresPlayer())
                                            result = String.valueOf(replacer.getResult(group, player));
                                    }
                                else if (player.isOnline())
                                    result = String.valueOf(replacer.getResult(group, player.getPlayer()));
                                else
                                    result = String.valueOf(replacer.getResult(group, player));
                            }
                        } catch (Throwable ex) {
                            // Error
                            SendConsole.severe("Error in placeholder: " + placeholder);
                            SendConsole.stacktrace(ex);
                            ph.removePlaceholder(placeholder);
                        }
                        if (result == null)
                            result = "";
                        results.put("(?i)\\{" + group + "\\}", result);
                        break;
                    }
                }
                if (found)
                    break;
            }
        }

        return results;
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

    /**
     * Clear unused placeholders
     *
     * @param usedPlaceholderPacks List of unused placeholders
     */
    public static void filterUsedPlaceholder(List<PlaceholderPack> usedPlaceholderPacks) {
        List<PlaceholderPack> removingPlaceholderPacks = new ArrayList<PlaceholderPack>();
        for (PlaceholderPack placeholderPackGroup : getEnabledPlaceholderGroups()) {
            if (!usedPlaceholderPacks.contains(placeholderPackGroup)) {
                removingPlaceholderPacks.add(placeholderPackGroup);
            }
        }
        for (PlaceholderPack pl : removingPlaceholderPacks) {
            placeholderPackGroups.remove(pl);
        }
    }

    /**
     * Register a placeholderPack
     *
     * @param placeholderPack Place holder
     */
    public static void registerPlaceHolder(PlaceholderPack placeholderPack) {
        if (placeholderPack == null) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            SendConsole.warning("A plugin tried to register a placeholder without giving the placeholder pack...");
            StackTraceElement element = stackTraceElements[stackTraceElements.length - 1];
            SendConsole.warning(element.getClassName());
            return;
        }

        try {
            Plugin placeholderPlugin = null;
            for (ModuleConstraint condition : placeholderPack.getPlaceholderConditions()) {
                boolean passed = false;
                switch (condition.type()) {
                    case PLUGIN:
                        if (PluginHook.isLoaded(condition.value())) {
                            placeholderPlugin = PluginHook
                                    .loadPlugin(condition.value());
                            placeholderPack.setEnabled(true);
                            passed = true;
                        } else {
                            if (placeholderPlugin == null) {
                                placeholderPack.setEnabled(false);
                            }
                        }
                        break;
                    case PLUGIN_MAIN:
                        if (placeholderPlugin == null)
                            placeholderPack.setEnabled(false);
                        else if (placeholderPlugin.getDescription().getMain()
                                .contains(condition.value())) {
                            placeholderPack.setEnabled(true);
                            passed = true;
                        } else {
                            placeholderPack.setEnabled(false);
                        }
                        break;
                    case PLUGIN_VERSION:
                        if (placeholderPlugin == null)
                            placeholderPack.setEnabled(false);
                        else if (placeholderPlugin.getDescription().getVersion()
                                .startsWith(condition.value())) {
                            placeholderPack.setEnabled(true);
                            passed = true;
                        } else {
                            placeholderPack.setEnabled(false);
                        }
                        break;
                    case PLUGIN_VERSION_IS_HIGHER:
                        if (placeholderPlugin == null)
                            placeholderPack.setEnabled(false);
                        else if (new Version(condition.value())
                                .compare(new Version(placeholderPlugin.getDescription().getVersion())) >= 0) {
                            placeholderPack.setEnabled(true);
                            passed = true;
                        } else {
                            placeholderPack.setEnabled(false);
                        }
                        break;
                    case PLUGIN_VERSION_IS_LOWER:
                        if (placeholderPlugin == null)
                            placeholderPack.setEnabled(false);
                        else if (new Version(condition.value())
                                .compare(new Version(placeholderPlugin.getDescription().getVersion())) == -1) {
                            placeholderPack.setEnabled(true);
                            passed = true;
                        } else {
                            placeholderPack.setEnabled(false);
                        }
                        break;
                    default:
                        break;
                }
                if (!passed)
                    break;
            }
        } catch (Exception ex) {
            SendConsole.stacktrace(ex);
        }
        if (placeholderPack.isEnabled()) {
            // Generate config
            placeholderPack.generateConfig();
            if (placeholderPack.getPlugin() != null) {
                if (placeholderPack.getPlugin().getClass()
                        .getResourceAsStream("/placeholder_" + placeholderPack.getName() + ".yml") == null) {
                    placeholderPack.storage = new YamlStorage(placeholderPack.plugin, "placeholders",
                            "placeholder_" + placeholderPack.getName(), placeholderPack.getConfigVersion(),
                            placeholderPack.getConfigBuilder(), 1);
                } else {
                    placeholderPack.storage = new YamlStorage(placeholderPack.plugin, "placeholders",
                            "placeholder_" + placeholderPack.getName(), true, placeholderPack.getConfigVersion(), 1);
                }
                placeholderPack.storage.loadConfig(1);

                if (!placeholderPack.storage.getConfig().getBoolean("enabled")) {
                    placeholderPack.setEnabled(false);
                }
            }
            try {
                if (placeholderPack.isEnabled())
                    placeholderPack.initialize();
            } catch (Exception ex) {
                SendConsole.info("An error occured while initializing placeholders code=" + placeholderPack.getName());
                SendConsole.info("The placeholders have been disabled for use.");
                ex.printStackTrace();
                placeholderPack.setEnabled(false);
            }
        }
        placeholderPackGroups.add(placeholderPack);
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
        getConfigBuilder().addPart("config", getVersion());
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

    public static int getPlaceHolderCount() {
        int count = 0;
        for (PlaceholderPack placeholderPack : placeholderPackGroups) {
            count += placeholderPack.getPlaceholder().size();
        }
        return count;
    }

    public boolean isOffline(String placeholder) {
        return (!getPlaceholderReplacer(placeholder).isOnline());

    }

    public static int getPlaceHolderEnabledCount() {
        int count = 0;
        for (PlaceholderPack placeholderPack : placeholderPackGroups) {
            if (placeholderPack.isEnabled())
                count += placeholderPack.getPlaceholder().size();
        }
        return count;
    }

    public String getName() {
        return name;
    }

    public static List<PlaceholderPack> getEnabledPlaceholderGroups() {
        List<PlaceholderPack> placeholderPacks = new ArrayList<PlaceholderPack>();
        for (PlaceholderPack pl : placeholderPackGroups) {
            if (pl.isEnabled())
                placeholderPacks.add(pl);
        }
        return placeholderPacks;
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

    public static void generateBBCodeFile() {
        try {
            FileWriter fw = new FileWriter(new File("placeholders.txt"), false);
            fw.write("==========================================================\n");
            fw.write("Placeholders list generated on " + new Date().toString() + "\n");
            fw.write("Amount of placeholders: " + getPlaceHolderCount() + "\n");
            fw.write("==========================================================\n");
            fw.write("\n");
            fw.write("\n");
            fw.write("\n");
            for (PlaceholderPack placeholderPack : placeholderPackGroups) {
                fw.write("[h2]" + placeholderPack.getDescription() + "[/h2]\n");
                if (!placeholderPack.getPluginURL().equals("")) {
                    fw.write(placeholderPack.getPluginURL());
                    fw.write("\n");
                }
                fw.write("\n");
                fw.write("[B][U][SIZE=5]Placeholders[/SIZE][/U][/B]\n");
                for (String placeholderString : placeholderPack.getPlaceholder()) {
                    PlaceholderReplacer<?> replacer = placeholderPack.getPlaceholderReplacer(placeholderString);

                    fw.write("[B]" + placeholderString + "[/B]\n");
                    fw.write("[I]" + replacer.getDescription() + "[/I]\n");
                    String returnTypeString = "";
                    if (replacer.getReturnType().equals(String.class)) {
                        returnTypeString = "Text";
                    } else if (replacer.getReturnType().equals(Boolean.class)) {
                        returnTypeString = "True/False";
                    } else if (replacer.getReturnType().equals(Integer.class)
                            || replacer.getReturnType().equals(Short.class)
                            || replacer.getReturnType().equals(Double.class)
                            || replacer.getReturnType().equals(Float.class)
                            || replacer.getReturnType().equals(Long.class)
                            || replacer.getReturnType().equals(BigDecimal.class)) {
                        returnTypeString = "Number";
                    } else {
                        returnTypeString = "Unknown";
                    }
                    fw.write("Returns: " + returnTypeString);
                    fw.write("\n");
                    fw.write("\n");
                }
                fw.write("\n");
                fw.write("\n");
                fw.write("\n");
            }
            fw.close();
        } catch (Exception ex) {

        }
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

    public static List<PlaceholderRefreshHandler> getRefreshHandles() {
        return refreshHandles;
    }

    public static void setRefreshHandles(List<PlaceholderRefreshHandler> refreshHandles) {
        PlaceholderPack.refreshHandles = refreshHandles;
    }

    public static void registerPlaceholderRefreshHandler(PlaceholderRefreshHandler handler) {
        PlaceholderPack.refreshHandles.add(handler);
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

    public static boolean isIgnoreProblems() {
        return ignoreProblems;
    }

    public static void setIgnoreProblems(boolean ignoreProblems) {
        PlaceholderPack.ignoreProblems = ignoreProblems;
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

    public interface PlaceholderRefreshHandler {
        void refreshPlaceholders();
    }

}

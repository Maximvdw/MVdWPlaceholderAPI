package be.maximvdw.placeholderapi.internal.eventhooks;

import be.maximvdw.placeholderapi.internal.hooks.PluginHook;
import be.maximvdw.placeholderapi.internal.ui.SendConsole;
import be.maximvdw.placeholderapi.internal.utils.bukkit.Version;
import be.maximvdw.placeholderapi.internal.storage.YamlBuilder;
import be.maximvdw.placeholderapi.internal.storage.YamlBuilder.YamlEmptyPart;
import be.maximvdw.placeholderapi.internal.storage.YamlStorage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public abstract class BaseEventHook implements Listener {
	private static List<TriggerEvent> hooks = new ArrayList<TriggerEvent>();
	private static List<BaseEventHook> eventHooks = new ArrayList<BaseEventHook>();
	private Map<EventCondition, String> eventConditions = new HashMap<EventCondition, String>();
	private static String actionName = "";
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
	private int version = 1;
	private Plugin plugin = null;

	private YamlStorage storage = null;

	public BaseEventHook(Plugin plugin, String shortName, int version) {
		setPlugin(plugin);
		setShortName(shortName);
	}

	public static void stopAllListeners() {
		for (BaseEventHook hook : eventHooks) {
			if (hook.isEnabled()) {
				try {
					SendConsole.info("Unregistering event: " + hook.getName());
					HandlerList.unregisterAll(hook);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		eventHooks.clear();
	}

	public Map<EventCondition, String> getEventConditions() {
		return this.eventConditions;
	}

	public static void registerHook(BaseEventHook eventHook) {
		try {
			Plugin eventPlugin = null;
			for (EventCondition condition : eventHook.getEventConditions().keySet()) {
				boolean passed = false;
				switch (condition) {
				case PLUGIN:
					if (PluginHook.isLoaded(eventHook.getEventConditions().get(condition))) {
						eventPlugin = PluginHook.loadPlugin(eventHook.getEventConditions().get(condition));
						eventHook.setEnabled(true);
						passed = true;
					} else {
						eventHook.setEnabled(false);
					}
					break;
				case MAIN:
					if (eventPlugin == null)
						eventHook.setEnabled(false);
					else if (eventPlugin.getDescription().getMain()
							.contains(eventHook.getEventConditions().get(condition))) {
						eventHook.setEnabled(true);
						passed = true;
					} else {
						eventHook.setEnabled(false);
					}
					break;
				case VERSION:
					if (eventPlugin == null)
						eventHook.setEnabled(false);
					else if (eventPlugin.getDescription().getVersion()
							.contains(eventHook.getEventConditions().get(condition))) {
						eventHook.setEnabled(true);
						passed = true;
					} else {
						eventHook.setEnabled(false);
					}
					break;
				case VERSION_IS_HIGHER:
					if (eventPlugin == null)
						eventHook.setEnabled(false);
					else if (new Version(eventHook.getEventConditions().get(condition))
							.compare(new Version(eventPlugin.getDescription().getVersion())) >= 0) {
						eventHook.setEnabled(true);
						passed = true;
					} else {
						eventHook.setEnabled(false);
					}
					break;
				case VERSION_IS_LOWER:
					if (eventPlugin == null)
						eventHook.setEnabled(false);
					else if (new Version(eventHook.getEventConditions().get(condition))
							.compare(new Version(eventPlugin.getDescription().getVersion())) == -1) {
						eventHook.setEnabled(true);
						passed = true;
					} else {
						eventHook.setEnabled(false);
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
		eventHook.generateConfig();

		if (eventHook.isEnabled()) {
			SendConsole.info("Loading event hook: " + eventHook.getName() + " ...");
			eventHook.setStorage(new YamlStorage(eventHook.getPlugin(), "events",
					"event_" + eventHook.shortName.toLowerCase(), eventHook.version, eventHook.getConfigTemplate(),1));
			eventHook.loadConfig();
		}

		if (eventHook.isEnabled()) {
			SendConsole.info("Enabling event hook: " + eventHook.getName() + "!");
			Bukkit.getPluginManager().registerEvents(eventHook, eventHook.getPlugin());
			eventHook.start();
		}
		eventHooks.add(eventHook);
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
			builder.addPart("config", version);
			builder.addPart(new YamlEmptyPart());
			builder.addPart(new YamlEmptyPart());
			builder.addPart(" Do you want to enable this event hook?");
			builder.addPart(" Enabling this hook will trigger " + getActionName());
			builder.addPart(" when " + getWhen());
			builder.addPart(" for " + getWho());
			builder.addPart("enabled", false);
			builder.addPart(new YamlEmptyPart());
			builder.addPart(" " + getActionName() + " to trigger");
			builder.addPart(getActionName().toLowerCase(), "default");
			builder.addPart(new YamlEmptyPart());
			builder.addPart(new YamlEmptyPart());
			setConfigTemplate(builder);
		}
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

	public void enableEvent(Player player) {
		for (TriggerEvent event : getHooks())
			event.enableEvent(player, getConfig().getString(getActionName().toLowerCase()));
	}

	public void disableEvent(Player player) {
		for (TriggerEvent event : getHooks())
			event.disableEvent(player, getConfig().getString(getActionName().toLowerCase()));
	}

	public static void registerTriggerEvent(TriggerEvent event) {
		hooks.add(event);
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

	public static String getActionName() {
		return actionName;
	}

	public static void setActionName(String actionName) {
		BaseEventHook.actionName = actionName;
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

	public static List<TriggerEvent> getHooks() {
		return hooks;
	}

	public static void setHooks(List<TriggerEvent> hooks) {
		BaseEventHook.hooks = hooks;
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
	 * @param condition
	 *            Condition
	 * @param value
	 *            Value
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

	public static void generateBBCodeFile() {
		try {
			FileWriter fw = new FileWriter(new File("eventtriggers.txt"), false);
			fw.write("==========================================================\n");
			fw.write("Event Triggers list generated on " + new Date().toString() + "\n");
			fw.write("Amount of event triggers: " + eventHooks.size() + "\n");
			fw.write("==========================================================\n");
			fw.write("\n");
			fw.write("\n");
			fw.write("\n");
			for (BaseEventHook event : eventHooks) {
				fw.write("[h2]" + event.getDescription() + "[/h2]\n");
				if (!event.getUrl().equals("")) {
					fw.write(event.getUrl());
					fw.write("\n");
				}
				fw.write("\n");
				fw.write("[B][U][SIZE=5]Default Config[/SIZE][/U][/B]\n");
				fw.write("[CODE]\n");
				fw.write(event.getConfigTemplate().toString());
				fw.write("[/CODE]\n");
				fw.write("\n");
				fw.write("\n");
				fw.write("\n");
			}
			fw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

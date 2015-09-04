package be.maximvdw.basecore.spigot;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class SpigotUpdater implements Listener {
	private static int resource = 0;
	private static String latestVersion = "";
	private static boolean updateAvailable = false;
	private static String globalMessage = "";

	/**
	 * Initialize spigot updater
	 * 
	 * @param resource
	 *            Resource ID
	 */
	public SpigotUpdater(Plugin plugin, final int resource) {
		super(plugin);
		SpigotUpdater.resource = resource;
	}

	/**
	 * Asks spigot for the version
	 */
	private String getSpigotVersion() {
		try {
			HttpURLConnection con = (HttpURLConnection) new URL(
					"http://www.spigotmc.org/api/general.php").openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.getOutputStream()
					.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource)
							.getBytes("UTF-8"));
			String version = new BufferedReader(new InputStreamReader(
					con.getInputStream())).readLine();
			if (version.length() <= 7) {
				return version;
			}
		} catch (Exception ex) {
			SendConsole.info("Failed to check for a update on spigot.");
		}
		return null;
	}

	public boolean checkHigher(String currentVersion, String newVersion) {
		String current = toReadable(currentVersion);
		String newVers = toReadable(newVersion);
		return current.compareTo(newVers) < 0;
	}

	public boolean checkUpdate(String currentVersion) {
		if (getLatestVersion() != "")
			return true;
		String version = getSpigotVersion();
		if (version != null) {
			if (checkHigher(currentVersion, version)) {
				latestVersion = version;
				updateAvailable = true;
				return true;
			}
		}
		return false;
	}

	public static boolean updateAvailable() {
		return updateAvailable;
	}

	public static String getLatestVersion() {
		return latestVersion;
	}

	public String toReadable(String version) {
		String[] split = Pattern.compile(".", Pattern.LITERAL).split(
				version.replace("v", ""));
		version = "";
		for (String s : split)
			version += String.format("%4s", s);
		return version;
	}

	@EventHandler
	public void onLogin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if (player.isOp()) {
			if (!globalMessage.equals("")) {
				getPlugin().getServer().getScheduler()
						.runTaskLater(getPlugin(), new Runnable() {

							@Override
							public void run() {
								SendGame.toPlayer(
										Messages.getPrefix(false, player)
												+ "&c" + globalMessage, player);
							}

						}, 15L);
			}
			if (SpigotUpdater.updateAvailable()) {
				getPlugin().getServer().getScheduler()
						.runTaskLater(getPlugin(), new Runnable() {

							@Override
							public void run() {
								SendGame.toPlayer(
										Messages.getPrefix(false, player)
												+ "&cAn update for "
												+ getPlugin().getName()
												+ " is available! &4["
												+ SpigotUpdater
														.getLatestVersion()
												+ "]", player);
							}

						}, 15L);
			}
		}
	}
}

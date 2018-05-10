package be.maximvdw.placeholderapi.internal.updater;

import be.maximvdw.mvdwupdater.MVdWUpdater;
import be.maximvdw.mvdwupdater.spigotsite.api.resource.Resource;
import be.maximvdw.placeholderapi.internal.ui.SendConsole;
import be.maximvdw.placeholderapi.internal.utils.bukkit.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * MVdW Updater Hook
 *
 * @author Maxim Van de Wynckel
 */
public class MVdWUpdaterHook {
    private static boolean updated = false;

    public MVdWUpdaterHook(Plugin plugin, int resourceId) {
        try {
            MVdWUpdater updater = (MVdWUpdater) Bukkit.getPluginManager().getPlugin("MVdWUpdater");
            SendConsole.info("Hooked into MVdWUpdater!");

            // Resource id found
            SendConsole.info("Checking for new updates for: " + plugin.getName() + " ...");

            // Compare versions
            Version currentPluginVersion = new Version(plugin.getDescription().getVersion());
            Version newPluginVersion = new Version(updater.getResourceVersionString(resourceId));

            if (currentPluginVersion.compare(newPluginVersion) == 1) {
                SendConsole.info("An update for '" + plugin.getName() + "' is available");

                // Do not update if the update is a major version
                if (currentPluginVersion.getMajor() != newPluginVersion.getMajor()) {
                    SendConsole.info("Update is a major update! Manual updating is required!");
                    return;
                }

                SendConsole.info("Getting download link ...");
                Resource premiumResource = updater.getSpigotSiteAPI().getResourceManager()
                        .getResourceById(resourceId, updater.getSpigotUser());

                // Download the plugin to the update folder
                File pluginFile = null;
                try {
                    pluginFile = new File(URLDecoder.decode(
                            plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(),
                            "UTF-8"));
                } catch (UnsupportedEncodingException e) {

                }

                File outputFile = null;
                try {
                    outputFile = new File(Bukkit.getUpdateFolderFile(), pluginFile.getName());
                } catch (Exception ex) {

                }

                if (pluginFile != null && outputFile != null) {
                    SendConsole.info("Downloading '" + plugin.getName() + "' ...");
                    premiumResource.downloadResource(updater.getSpigotUser(), outputFile);

                    SendConsole.info("An new update for " + plugin.getName() + " is ready to be installed on next restart!");
                }
            }
            setUpdated(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        MVdWUpdaterHook.updated = updated;
    }
}

package be.maximvdw.placeholderapi.listeners;

import be.maximvdw.placeholderapi.internal.ui.SendConsole;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PluginChannelListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            String subchannel = in.readUTF();
            if(subchannel.equals("get")){
                // Someone requests a placeholder on this server
                String input = in.readUTF();

                notifyAll();
            }else if (subchannel.equals("post")){
                // Someone returned a placeholder for this server
                String input = in.readUTF();

                notifyAll();
            }
        } catch (IOException e) {
            SendConsole.severe("Unable to parse plugin message!");
            e.printStackTrace();
        }
    }
}

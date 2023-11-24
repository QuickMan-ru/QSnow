package me.qmaan;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class QSnow extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("qsnow").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length != 1) {
            sender.sendMessage("/qsnow <on|off>");
            return true;
        }

        if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off")) {
            sender.sendMessage("/qsnow <on|off>");
            return true;
        }

        int packet;

        if (args[0].equalsIgnoreCase("off")) {
            packet = 1;
        } else {
            packet = 2;
        }

        sendSnowPacketToAll(packet);
        return true;
    }

    public static void setPlayerWeather(Player player, int i) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        EntityPlayer entityPlayer = craftPlayer.getHandle();

        PlayerConnection playerConnection = entityPlayer.playerConnection;

        PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(i, 0.0F);
        playerConnection.sendPacket(packet);
    }

    public static void sendSnowPacketToAll(int i) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setPlayerWeather(player, i);
        }
    }


}

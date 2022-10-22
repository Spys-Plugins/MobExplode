package dev.cosmics.pigthingy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MobCommand implements CommandExecutor {
    private static final HashMap<String, MobGui> players = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return true;
        }

        if (players.get(sender.getName()) != null) {
            players.get(sender.getName()).openGui();
            return true;
        }

        Player player = (Player) sender;
        MobGui gui = new MobGui(player);
        players.put(player.getName(), gui);
        gui.openGui();
        return true;
    }
}
